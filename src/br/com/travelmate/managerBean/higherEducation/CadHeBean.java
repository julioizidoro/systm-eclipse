package br.com.travelmate.managerBean.higherEducation;
 

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoCursoBean;
import br.com.travelmate.bean.comissao.ComissaoHEInscricaoBean;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Orcamento; 
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao; 

public class CadHeBean {
	
	private ProgramasBean programasBean;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Orcamento orcamento;
	private UsuarioLogadoMB usuarioLogadoMB;
	
	public CadHeBean(Vendas venda, Formapagamento formaPagamento, Orcamento orcamento, UsuarioLogadoMB usuarioLogadoMB) {
		this.programasBean =  new ProgramasBean();
		this.venda = venda;
		this.formaPagamento= formaPagamento;
		this.orcamento = orcamento;
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	public He salvarHe(He he, AplicacaoMB aplicacaoMB, String tipo) { 
		HeFacade heFacade = new HeFacade();
		he = heFacade.salvar(he);
		if (Formatacao.validarDataVenda(venda.getDataVenda())) {
			ContasReceberBean contasReceberBean = new ContasReceberBean(venda,
					formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, true, he.getDatainicio());
		}
		if (tipo.equalsIgnoreCase("I")){
			salvarComissaoInscricao(aplicacaoMB);
		}else if (tipo.equalsIgnoreCase("F")){
			//anda nao tem calculo de comissao
		}
		return he;
	}
	
	public void salvarFichaFinal(AplicacaoMB aplicacaoMB){
		
	}
	
	public void salvarComissaoInscricao(AplicacaoMB aplicacaoMB){
		float valorPrevisto = 0.0f;
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		Fornecedorcomissaocurso fornecedorComissao = fornecedorComissaoCursoFacade.consultar(
				venda.getFornecedorcidade().getFornecedor().getIdfornecedor(),
				venda.getFornecedorcidade().getCidade().getPais().getIdpais());
		if (fornecedorComissao != null) {
			Vendascomissao vendasComissao = venda.getVendascomissao();
			if (vendasComissao == null) {
				vendasComissao = new Vendascomissao();
				vendasComissao.setVendas(venda);
				vendasComissao.setPaga("Não");
			}
			if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
				float valorJuros = 0.0f;
				if (venda.getFormapagamento() != null) {
					valorJuros = venda.getFormapagamento().getValorJuros();
				}
				ComissaoHEInscricaoBean cc = new  ComissaoHEInscricaoBean(aplicacaoMB, venda, orcamento.getOrcamentoprodutosorcamentoList(), formaPagamento.getParcelamentopagamentoList(), vendasComissao, valorJuros, true);
				valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			}
		}
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
