package br.com.travelmate.managerBean.cursoTeens;

import java.util.List;

import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Vendas;

public class CadCursosTeensBean {
	
	private ProgramasBean programasBean;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Orcamento orcamento;
	
	public CadCursosTeensBean(Vendas venda, Formapagamento formaPagamento, Orcamento orcamento, UsuarioLogadoMB usuarioLogadoMB) {
		this.programasBean =  new ProgramasBean();
		this.venda = venda;
		this.formaPagamento= formaPagamento;
		this.orcamento = orcamento;
	}
	
	public void SalvarAlteracaoFinanceiro(List<Parcelamentopagamento> listaParcelamentoPagamentoAntigo,List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal){
		if (listaParcelamentoPagamentoOriginal != null) {
			programasBean.salvarAlteracaoFinanceiro(venda, listaParcelamentoPagamentoAntigo,
					listaParcelamentoPagamentoOriginal, venda.getUsuario());
		} 
	}
	
	public Programasteens salvarProgramaTeens(Programasteens programa) {
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		programasTeensFacede.salvar(programa);
		return programa;
	}
	
	public Orcamento salvarOrcamento(Cambio cambio, Float totalMoedaNacional, Float totalMoedaEstrangeira, Float valorCambio, Vendas venda, String cambioAlterado
			, Float valorMoedaNacional, float valorCambioBraisl){
		orcamento = programasBean.salvarOrcamento(orcamento, cambio, orcamento.getTotalMoedaNacional(),
				orcamento.getTotalMoedaEstrangeira(), orcamento.getValorCambio(), venda, cambioAlterado, valorMoedaNacional, valorCambioBraisl);
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
