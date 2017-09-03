package br.com.travelmate.managerBean.financeiro.crmcobranca;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.travelmate.dao.CrmCobrancaContaDao;
import br.com.travelmate.dao.CrmCobrancaDao;
import br.com.travelmate.dao.CrmCobrancaHistoricoDao;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.ParametrosFinanceiroFacade;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Crmcobranca;
import br.com.travelmate.model.Crmcobrancaconta;
import br.com.travelmate.model.Crmcobrancahistorico;
import br.com.travelmate.model.Parametrosfinanceiro;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

public class CrmCobrancaBean {
	
	@Inject
	private CrmCobrancaDao crmCobrancaDao;
	@Inject
	private CrmCobrancaContaDao crmCobrancaContaDao;
	@Inject
	private CrmCobrancaHistoricoDao crmCobrancaHistoricoDao;
	
	public Crmcobrancaconta baixar(Contasreceber conta, Usuario usuario){
		Crmcobrancaconta crmcobrancaconta = conta.getCrmcobrancaconta(); 
		if (conta.getCrmcobrancaconta() != null) {
			crmcobrancaconta.setPaga(true);
			crmcobrancaconta = crmCobrancaContaDao.salvar(crmcobrancaconta);
			verificarCobranca(crmcobrancaconta.getCrmcobranca(), usuario);
		}
		return crmcobrancaconta;
	}
	
	public Crmcobrancaconta criar(Contasreceber conta){
		String sql = "SELECT c FROM Crmcobranca c where c.vendas.idvendas=" + conta.getVendas().getIdvendas() + 
				" and c.situacao<>'FINALIZADA'";
		List<Crmcobranca> lista = crmCobrancaDao.lista(sql);
		Crmcobranca  crmCobranca = null;
		if (lista!=null){
			if (lista.size()>0){
				crmCobranca = lista.get(0);
			}
		}
		if (crmCobranca==null){
			crmCobranca = new Crmcobranca();
			crmCobranca.setPrioridade("1");
			crmCobranca.setSituacao("nova");
			crmCobranca.setVendas(conta.getVendas());
			crmCobranca = crmCobrancaDao.salvar(crmCobranca);
		}
		Crmcobrancaconta crmcobrancaconta = new Crmcobrancaconta();
		crmcobrancaconta.setPaga(false);
		crmcobrancaconta.setContasreceber(conta);
		crmcobrancaconta.setCrmcobranca(crmCobranca);
		crmcobrancaconta.setDatainclusao(new Date());
		crmcobrancaconta = crmCobrancaContaDao.salvar(crmcobrancaconta);
		return crmcobrancaconta;
	}
	
	public void verificarCobranca(Crmcobranca crmCobranca, Usuario usuario){
		String sql = "SELECT c FROM Crmcobrancaconta c where c.paga=0 and c.crmcobranca.vendas.idvendas=" + crmCobranca.getVendas().getIdvendas() +
				" and c.crmcobranca.situacao<>'Finalizada'";
		List<Crmcobrancaconta> lista = crmCobrancaContaDao.lista(sql);
		if (lista==null){
			crmCobranca.setSituacao("FINALIZADA");
			crmCobranca.setDatafinalizada(new Date());
			crmCobranca = crmCobrancaDao.salvar(crmCobranca);
			Crmcobrancahistorico crmCobrancaHistorico = new Crmcobrancahistorico();
			crmCobrancaHistorico.setCliente(crmCobranca.getVendas().getCliente());
			crmCobrancaHistorico.setData(new Date());
			crmCobrancaHistorico.setHistorico("Baixa da conbrança efetudada atras de liquidação/cancelamento do atraso");
			crmCobrancaHistorico.setProximocontato(new Date());
			crmCobrancaHistorico.setTipocontato("Telefone");
			crmCobrancaHistorico.setUsuario(usuario);
			crmCobrancaHistoricoDao.salvar(crmCobrancaHistorico);
		}
	}
	
	public void gerarListaInadiplentes(){
		ParametrosFinanceiroFacade parametrosFinanceiroFacade = new ParametrosFinanceiroFacade();
		Parametrosfinanceiro parametrosfinanceiro = parametrosFinanceiroFacade.consultar();
		String dataVencimento = Formatacao.SubtarirDatas(new Date(), 5, "yyyy-MM-dd");
		String sql = "SELECT c FROM Contasreceber c where c.datavencimento>'" +
		Formatacao.ConvercaoDataSql(parametrosfinanceiro.getDatacobranca()) + "' and c.datavencimento<='" + dataVencimento +
				"'  and c.situacao<>'cc' and c.datapagamento is NULL and c.valorpago=0";
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		List<Contasreceber> lista = contasReceberFacade.listar(sql);
		if (lista!=null){
			for (int i=0;i<lista.size();i++){
				if (lista.get(i).getCrmcobrancaconta()==null){
					criar(lista.get(i));
				}
			}
		}
		try {
			parametrosfinanceiro.setDatacobranca(Formatacao.SomarDiasDatas(new Date(), -5));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parametrosFinanceiroFacade.salvar(parametrosfinanceiro);
	}
	
	public void calcularAtrasos() {
		String sql = "SELECT c FROM Crmcobranca c where c.situacao<>'FINALIZADA'";
		List<Crmcobranca> lista = crmCobrancaDao.lista(sql);
		if (lista!=null) {
			for(int i=0;i<lista.size();i++) {
				if (!lista.get(i).getPrioridade().equals("5")){
					int dias = Formatacao.subtrairDatas(new Date(), lista.get(i).getDatavencimento());
					String prioridade = lista.get(i).getPrioridade();
					if (dias<=5) {
						prioridade = "1";
					}else if (dias<=15) {
						prioridade = "2";
					}else if (dias<=30) {
						prioridade = "3";
					}else if (dias<=45) {
						prioridade = "4";
					}else if (dias>=46) {
						prioridade = "5";
					}
					int diasEmbarque = 0;
					if (!prioridade.equals("5")) {
						if (lista.get(i).getVendas().getVendascomissao().getDatainicioprograma()!=null) {
							diasEmbarque = Formatacao.subtrairDatas(lista.get(i).getVendas().getVendascomissao().getDatainicioprograma(), new Date());
						}
						if (diasEmbarque<=30) {
							prioridade ="5";
						}
					}
					if (!lista.get(i).getPrioridade().equals(prioridade)) {
						Crmcobranca cobranca = lista.get(i);
						cobranca.setPrioridade(prioridade);
						cobranca = crmCobrancaDao.salvar(cobranca);
						lista.set(i, cobranca);
					}
				}
			}
		}
	}
	
	public void CrmCobrancaGerarDataVencimento() {
		String sql = "SELECT c FROM Crmcobranca c where c.situacao<>'FINALIZADA'";
		List<Crmcobranca> lista = crmCobrancaDao.lista(sql);
		if (lista!=null) {
			for(int i=0;i<lista.size();i++) {
				String sqlconta = "SELECT c FROM Crmcobrancaconta c where c.crmcobranca.idcrmcobranca=" + lista.get(i).getIdcrmcobranca();
				List<Crmcobrancaconta> listaConta = crmCobrancaContaDao.lista(sqlconta);
				if (listaConta!=null) {
					if (listaConta.size()>0) {
						try {
							lista.get(i).setDatainiciocobranca(Formatacao.SomarDiasDatas(listaConta.get(0).getContasreceber().getDatavencimento(), 6));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lista.get(i).setDatavencimento(listaConta.get(0).getContasreceber().getDatavencimento());
					}
				}
			}
		}
	}

}
