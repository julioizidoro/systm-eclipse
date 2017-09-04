package br.com.travelmate.managerBean.financeiro.crmcobranca;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

@Named
@ViewScoped
public class CrmCobrancaBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private CrmCobrancaDao crmCobrancaDao;
	@Inject
	private CrmCobrancaContaDao crmCobrancaContaDao;
	@Inject
	private CrmCobrancaHistoricoDao crmCobrancaHistoricoDao;
	
	
	@PostConstruct
	public void init() {
	  
	}
	
	public CrmCobrancaBean() {
		
	}



	public Crmcobrancaconta baixar(Contasreceber conta, Usuario usuario){
		Crmcobrancaconta crmcobrancaconta = conta.getCrmcobrancaconta(); 
		if (conta.getCrmcobrancaconta() != null) {
			crmcobrancaconta.setPaga(true);
			crmcobrancaconta = crmCobrancaContaDao.salvar(crmcobrancaconta);
			verificarCobranca(crmcobrancaconta.getCrmcobranca(), usuario);
		}
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
			crmCobranca.setSituacao("NOVA");
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

}
