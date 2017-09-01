package br.com.travelmate.managerBean.financeiro.crmcobranca;


import java.util.Date;
import java.util.List;

import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.CrmCobrancaContaFacade;
import br.com.travelmate.facade.CrmCobrancaFacade;
import br.com.travelmate.facade.CrmCobrancaHistoricoFacade;
import br.com.travelmate.facade.ParametrosFinanceiroFacade;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Crmcobranca;
import br.com.travelmate.model.Crmcobrancaconta;
import br.com.travelmate.model.Crmcobrancahistorico;
import br.com.travelmate.model.Parametrosfinanceiro;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

public class CrmCobrancaBean {
	
	public Crmcobrancaconta baixar(Contasreceber conta, Usuario usuario){
		CrmCobrancaContaFacade crmCobrancaContaFacade = new CrmCobrancaContaFacade();
		Crmcobrancaconta crmcobrancaconta = conta.getCrmcobrancaconta(); 
		crmcobrancaconta.setPaga(true);
		crmcobrancaconta = crmCobrancaContaFacade.salvar(crmcobrancaconta);
		verificarCobranca(crmcobrancaconta.getCrmcobranca(), usuario);
		return crmcobrancaconta;
	}
	
	public Crmcobrancaconta criar(Contasreceber conta){
		CrmCobrancaFacade crmCobrancaFacade = new CrmCobrancaFacade();
		String sql = "SELECT c FROM Crmcobranca c where c.vendas.idvendas=" + conta.getVendas().getIdvendas() + 
				" and c.situacao<>'FINALIZADA'";
		List<Crmcobranca> lista = crmCobrancaFacade.lista(sql);
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
			crmCobranca = crmCobrancaFacade.salvar(crmCobranca);
		}
		CrmCobrancaContaFacade crmCobrancaContaFacade = new CrmCobrancaContaFacade();
		Crmcobrancaconta crmcobrancaconta = new Crmcobrancaconta();
		crmcobrancaconta.setPaga(false);
		crmcobrancaconta.setContasreceber(conta);
		crmcobrancaconta.setCrmcobranca(crmCobranca);
		crmcobrancaconta.setDatainclusao(new Date());
		crmcobrancaconta = crmCobrancaContaFacade.salvar(crmcobrancaconta);
		return crmcobrancaconta;
	}
	
	public void verificarCobranca(Crmcobranca crmCobranca, Usuario usuario){
		CrmCobrancaContaFacade crmCobrancaContaFacade = new CrmCobrancaContaFacade();
		String sql = "SELECT c FROM Crmcobrancaconta c where c.paga=0 and c.crmcobranca.vendas.idvendas=" + crmCobranca.getVendas().getIdvendas() +
				" and c.crmcobranca.situacao<>'Finalizada'";
		List<Crmcobrancaconta> lista = crmCobrancaContaFacade.lista(sql);
		if (lista==null){
			CrmCobrancaFacade crmCobrancaFacade = new CrmCobrancaFacade();
			crmCobranca.setSituacao("FINALIZADA");
			crmCobranca.setDatafinalizada(new Date());
			crmCobranca = crmCobrancaFacade.salvar(crmCobranca);
			CrmCobrancaHistoricoFacade crmCobrancaHistoricoFacade = new CrmCobrancaHistoricoFacade();
			Crmcobrancahistorico crmCobrancaHistorico = new Crmcobrancahistorico();
			crmCobrancaHistorico.setCliente(crmCobranca.getVendas().getCliente());
			crmCobrancaHistorico.setData(new Date());
			crmCobrancaHistorico.setHistorico("Baixa da conbrança efetudada atras de liquidação/cancelamento do atraso");
			crmCobrancaHistorico.setProximocontato(new Date());
			crmCobrancaHistorico.setTipocontato("Telefone");
			crmCobrancaHistorico.setUsuario(usuario);
			crmCobrancaHistoricoFacade.salvar(crmCobrancaHistorico);
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

}
