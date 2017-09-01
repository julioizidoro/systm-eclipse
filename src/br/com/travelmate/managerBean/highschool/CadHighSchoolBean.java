package br.com.travelmate.managerBean.highschool;

import java.util.List;

import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

public class CadHighSchoolBean {
	
	private ProgramasBean programasBean;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Orcamento orcamento;
	private UsuarioLogadoMB usuarioLogadoMB;
	
	public CadHighSchoolBean(Vendas venda, Formapagamento formaPagamento, Orcamento orcamento, UsuarioLogadoMB usuarioLogadoMB) {
		this.programasBean =  new ProgramasBean();
		this.venda = venda;
		this.formaPagamento= formaPagamento;
		this.orcamento = orcamento;
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	public void SalvarAlteracaoFinanceiro(List<Parcelamentopagamento> listaParcelamentoPagamentoAntigo,List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal){
		if (listaParcelamentoPagamentoOriginal != null) {
			programasBean.salvarAlteracaoFinanceiro(venda, listaParcelamentoPagamentoAntigo, listaParcelamentoPagamentoOriginal,
					venda.getUsuario());
		} 
	}
	
	public Highschool salvarHighSchool(Highschool highschool) { 
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		highschool = highSchoolFacade.salvar(highschool);
		return highschool;
	}
	
	public Orcamento salvarOrcamento(Cambio cambio, Float totalMoedaNacional, Float totalMoedaEstrangeira, Float valorCambio, Vendas venda, String cambioAlterado){
		orcamento = programasBean.salvarOrcamento(orcamento, cambio, orcamento.getTotalMoedaNacional(),
				orcamento.getTotalMoedaEstrangeira(), orcamento.getValorCambio(), venda, cambioAlterado);
		venda.setOrcamento(orcamento);
		return orcamento;
	}
	
	public Formapagamento salvarFormaPagamento(Cancelamento cancelamento){		
		formaPagamento = programasBean.salvarFormaPagamento(formaPagamento, venda);
		venda.setFormapagamento(formaPagamento);
		if (cancelamento != null) {
			programasBean.salvarCancelamentoVenda(venda, cancelamento);
		}
		return formaPagamento;
	}
	
	
	public Cliente salvarCliente(Cliente cliente){
		cliente = programasBean.salvarCliente(cliente, "SEM DATA", null, null);
		return cliente;
	}
	
	
	


}
