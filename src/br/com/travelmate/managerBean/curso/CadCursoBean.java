package br.com.travelmate.managerBean.curso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoCursoBean;
import br.com.travelmate.bean.comissao.ComissaoCursoPacoteBean;
import br.com.travelmate.bean.comissao.ComissaoSeguroBean;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.DadosPaisFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Controleseguro;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Dadospais;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;

public class CadCursoBean {

	private ProgramasBean programasBean;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Orcamento orcamento;
	private UsuarioLogadoMB usuarioLogadoMB;
	private Curso curso;

	public CadCursoBean(Vendas venda, Formapagamento formaPagamento, Orcamento orcamento,
			UsuarioLogadoMB usuarioLogadoMB) {
		this.programasBean = new ProgramasBean();
		this.venda = venda;
		this.formaPagamento = formaPagamento;
		this.orcamento = orcamento;
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void SalvarAlteracaoFinanceiro(List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga,
			List<Parcelamentopagamento> listaParcelamentoPagamentoNova, Usuario usuario) {  
		if (listaParcelamentoPagamentoAntiga!=null && listaParcelamentoPagamentoNova != null) {
			programasBean.salvarAlteracaoFinanceiro(venda, listaParcelamentoPagamentoAntiga,
					listaParcelamentoPagamentoNova, usuario);
		}  
	}

	public void salvarDadosPais(Dadospais dadosPais) {
		DadosPaisFacade dadosPaisFacade = new DadosPaisFacade();
		dadosPais.setVendas(venda);
		dadosPais = dadosPaisFacade.salvar(dadosPais);
	}

	public Curso salvarCurso(Curso curso, Vendas vendaAlterada, boolean CheckBoxSegundoCurso) {
		if (curso.getIdcursos() == null) {
			curso.setDataInscricao(new Date());
		} else {
			if (vendaAlterada != null && vendaAlterada.getSituacao().equalsIgnoreCase("PROCESSO")) {
				curso.setDataInscricao(new Date());
			}
		}
		if (CheckBoxSegundoCurso) {
			curso.setHabilitarSegundoCurso("S");
		} else {
			curso.setHabilitarSegundoCurso("N");
		}
		curso.setControle("ANDAMENTO");
		curso.setVendas(venda);
		curso.setIdioma(curso.getIndiomaEstudar());
		curso.setNivelIdioma(curso.getNivelIdiomaEstudar());
		CursoFacade cursoFacade = new CursoFacade();
		curso = cursoFacade.salvar(curso);
		this.curso = curso;
		return this.curso;
	}

	public void salvarControleSeguro(Seguroviagem seguroViagem) {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Controleseguro controle = seguroViagem.getControleseguro();
		if (controle == null) {
			controle = new Controleseguro();
			controle.setSeguroviagem(seguroViagem);
			controle.setEnvioVoucher("Não");
			controle.setObservacao(" ");
			controle.setFinalizado("ANDAMENTO");
			controle.setSituacao("ANDAMENTO");
			controle = seguroViagemFacade.salvarControle(controle);
		}
	}

	public Orcamento salvarOrcamento(Cambio cambio, float totalMoedaReal, float totalMoedaEstrangeira, float valorCambio,
			String cambioAlterado, Float valorMoedaNacional, float valorCambioBraisl) {
		orcamento = programasBean.salvarOrcamento(orcamento, cambio, totalMoedaReal, totalMoedaEstrangeira, valorCambio,
				venda, cambioAlterado, valorMoedaNacional, valorCambioBraisl);
		venda.setOrcamento(orcamento);
		return orcamento;
	}

	public Formapagamento salvarFormaPagamento(Cancelamento cancelamento) {
		formaPagamento = programasBean.salvarFormaPagamento(formaPagamento, venda);
		venda.setFormapagamento(formaPagamento);
		if (cancelamento != null) {
			programasBean.salvarCancelamentoVenda(venda, cancelamento);
		}
		return formaPagamento;
	}


	public Cliente salvarCliente(Cliente cliente) {
		cliente = programasBean.salvarCliente(cliente, Formatacao.ConvercaoDataPadrao(curso.getDataInicio()),
				curso.getDataInicio(), curso.getDataTermino());
		return cliente;

	}

	public void salvarNovaFichha(AplicacaoMB aplicacaoMB, Seguroviagem seguroViagem, Formapagamento forma) {
//		if (Formatacao.validarDataVenda(venda.getDataVenda())) {
//			ContasReceberBean contasReceberBean = new ContasReceberBean(venda,
//					 forma.getParcelamentopagamentoList(), usuarioLogadoMB, null, true);
//			
//		}
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
				vendasComissao.setProdutos(venda.getProdutos());
			}
			if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
				float valorJuros = 0.0f;
				if (venda.getFormapagamento() != null) {
					valorJuros = venda.getFormapagamento().getValorJuros();
				}
				if (venda.getVendaspacote()==null) {
					ComissaoCursoBean cc = new ComissaoCursoBean(aplicacaoMB, venda,
							orcamento.getOrcamentoprodutosorcamentoList(), fornecedorComissao,
							formaPagamento.getParcelamentopagamentoList(), curso.getDataInicio(), vendasComissao,
							valorJuros, true);
					valorPrevisto = cc.getVendasComissao().getValorfornecedor();
				}else {
					ComissaoCursoPacoteBean cc = new ComissaoCursoPacoteBean(aplicacaoMB, venda,
							orcamento.getOrcamentoprodutosorcamentoList(), fornecedorComissao,
							formaPagamento.getParcelamentopagamentoList(), curso.getDataInicio(), vendasComissao,
							valorJuros, true);
					valorPrevisto = cc.getVendasComissao().getValorfornecedor();
				}
				
			}
		}

		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleCurso(venda, curso, valorPrevisto);
		if (seguroViagem.getIdseguroViagem() != null) {
			FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
			Formapagamento formapagamento = formaPagamentoFacade.consultar(venda.getIdvendas());
			if (seguroViagem.getVendas().getVendascomissao() == null) {
				seguroViagem.getVendas().setVendascomissao(new Vendascomissao());
				if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
					ComissaoSeguroBean cc = new ComissaoSeguroBean(aplicacaoMB, seguroViagem.getVendas(),
							new ArrayList<Parcelamentopagamento>(), seguroViagem.getVendas().getVendascomissao(),
							seguroViagem.getDescontoloja(), seguroViagem.getDescontomatriz(), 0.0f, false,
							formapagamento,seguroViagem);
					seguroViagem.getVendas().setVendascomissao(cc.getVendasComissao());
					salvarControleSeguro(seguroViagem);
				}
			}
		}
	}
	
	public void pegarCurso(Curso curso, Vendas venda) {
		this.curso = curso;
		this.venda = venda;
	}

}
