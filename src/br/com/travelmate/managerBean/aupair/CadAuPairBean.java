package br.com.travelmate.managerBean.aupair;

import java.util.List;

import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Vendas;

public class CadAuPairBean {
	
	private ProgramasBean programasBean;
	private Vendas venda;
	private Formapagamento formaPagamento;
	
	public CadAuPairBean(Vendas venda, Formapagamento formaPagamento, Orcamento orcamento, UsuarioLogadoMB usuarioLogadoMB) {
		this.programasBean =  new ProgramasBean();
		this.venda = venda;
		this.formaPagamento= formaPagamento;
	}
	
	public void SalvarAlteracaoFinanceiro(List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga,
			List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal){ 
		if (listaParcelamentoPagamentoOriginal != null) {
			programasBean.salvarAlteracaoFinanceiro(venda, listaParcelamentoPagamentoAntiga,
					listaParcelamentoPagamentoOriginal, venda.getUsuario());
		} 
	}
	
	public Aupair salvarAupair(Aupair aupair) {
		AupairFacade aupairFacade = new AupairFacade();
		aupair = aupairFacade.salvar(aupair); 
		return aupair;
	}
	
	public Orcamento salvarOrcamento(Orcamento orcamento, Cambio cambio, Float totalMoedaNacional, Float totalMoedaEstrangeira, Float valorCambio, Vendas venda, String cambioAlterado
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
	
	public Cliente salvarCliente(Cliente cliente, String dataEmbarque){
		cliente = programasBean.salvarCliente(cliente, dataEmbarque, null, null);
		return cliente;
	} 

}
