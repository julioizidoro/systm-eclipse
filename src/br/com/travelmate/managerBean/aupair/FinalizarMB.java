package br.com.travelmate.managerBean.aupair;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.bean.ConsultaBean;
import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoAuPairBean;
import br.com.travelmate.facade.DepartamentoProdutoFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.ProductRunnersMB;
import br.com.travelmate.managerBean.TmRaceMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Departamentoproduto;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class FinalizarMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private DashBoardMB dashBoardMB;
	@Inject
	private ProductRunnersMB productRunnersMB;
	@Inject
	private TmRaceMB tmRaceMB;
	@Inject
	private MateRunnersMB mateRunnersMB;
	private String nome = "";
	
	
	@PostConstruct
	public void init() {
		nome = "";
		getNome();
	}
	
	public FinalizarMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}
	

	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public Vendas finalizar(Aupair aupair) {
		VendasFacade vendasFacade = new VendasFacade();
		aupair.setControle("Processo");
		float valorPrevisto = 0.0f;
		Vendascomissao vendasComissao = aupair.getVendas().getVendascomissao();
		if (vendasComissao == null) {
			vendasComissao = new Vendascomissao();
			vendasComissao.setVendas(aupair.getVendas());
			vendasComissao.setPaga("Não");
		}
		float valorJuros = 0.0f;
		if (aupair.getVendas().getFormapagamento() != null) {
			valorJuros = aupair.getVendas().getFormapagamento().getValorJuros();
		}
		aupair.setVendas(vendasFacade.salvar(aupair.getVendas()));
		if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
			ComissaoAuPairBean cc = new ComissaoAuPairBean(aplicacaoMB, aupair.getVendas(),
					aupair.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
					aupair.getVendas().getOrcamento().getValorCambio(), aupair.getValoresAupair(),
					aupair.getVendas().getFormapagamento().getParcelamentopagamentoList(),
					aupair.getDataInicioPretendida01(), vendasComissao, valorJuros);
			valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			aupair.getVendas().setVendascomissao(cc.getVendasComissao());
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleAupair(aupair.getVendas(), aupair, valorPrevisto);
//		dashBoardMB.getVendaproduto().setIntercambio(dashBoardMB.getVendaproduto().getIntercambio() + 1);
//		dashBoardMB.getMetamensal()
//				.setValoralcancado(dashBoardMB.getMetamensal().getValoralcancado() + aupair.getVendas().getValor());
//		dashBoardMB.getMetamensal().setPercentualalcancado(
//				(dashBoardMB.getMetamensal().getValoralcancado() / dashBoardMB.getMetamensal().getValormeta()) * 100);
//
//		dashBoardMB.getMetaAnual()
//				.setMetaalcancada(dashBoardMB.getMetaAnual().getMetaalcancada() + aupair.getVendas().getValor());
//		dashBoardMB.getMetaAnual().setPercentualalcancado(
//				(dashBoardMB.getMetaAnual().getMetaalcancada() / dashBoardMB.getMetaAnual().getValormeta()) * 100);
//
//		dashBoardMB.setMetaparcialsemana(dashBoardMB.getMetaparcialsemana() + aupair.getVendas().getValor());
//		dashBoardMB.setPercsemana(
//				(dashBoardMB.getMetaparcialsemana() / dashBoardMB.getMetamensal().getValormetasemana()) * 100);
//
//		float valor = dashBoardMB.getMetamensal().getValoralcancado();
//		dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(aupair.getVendas(), false);
		dashBoardBean.calcularMetaMensal(aupair.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(aupair.getVendas(), 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(aupair.getVendas(), 0, "", false);
		ProductRunnersMB productRunnersMB = new ProductRunnersMB();
		productRunnersMB.calcularPontuacao(aupair.getVendas(), pontos[0], false);
		aupair.getVendas().setPonto(pontos[0]);
		aupair.getVendas().setPontoescola(pontos[1]);
//		mateRunnersMB.carregarListaRunners();
//		tmRaceMB.gerarListaGold();
//		tmRaceMB.gerarListaSinze();
//		tmRaceMB.gerarListaBronze();
//		ContasReceberBean contasReceberBean = new ContasReceberBean(aupair.getVendas(),
//				aupair.getVendas().getFormapagamento().getParcelamentopagamentoList(), usuarioLogadoMB, null, false,
//				aupair.getDataInicioPretendida01());
		String titulo = "Nova Ficha de Au Pair";
		String operacao = "A";
		String imagemNotificacao = "inserido";

		String vm = "Venda pela Matriz";
		if (aupair.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		DepartamentoProdutoFacade departamentoProdutoFacade = new DepartamentoProdutoFacade();
		Departamentoproduto depPrograma = departamentoProdutoFacade
				.consultar(aupair.getVendas().getProdutos().getIdprodutos());
		if (depPrograma != null) {
			Formatacao.gravarNotificacaoVendas(titulo, aupair.getVendas().getUnidadenegocio(),
					aupair.getVendas().getCliente().getNome(),
					aupair.getVendas().getFornecedorcidade().getFornecedor().getNome(),
					Formatacao.ConvercaoDataPadrao(aupair.getDataInicioPretendida01()),
					aupair.getVendas().getUsuario().getNome(), vm, aupair.getVendas().getValor(),
					aupair.getVendas().getValorcambio(), aupair.getVendas().getCambio().getMoedas().getSigla(),
					operacao, depPrograma.getDepartamento(), imagemNotificacao, "I");
		}
		return aupair.getVendas();
	}

}
