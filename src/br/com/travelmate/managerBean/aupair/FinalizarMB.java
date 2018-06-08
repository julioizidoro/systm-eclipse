package br.com.travelmate.managerBean.aupair;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import br.com.travelmate.bean.comissao.ComissaoTraineeBean;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.DepartamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.ProductRunnersMB;
import br.com.travelmate.managerBean.TmRaceMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Departamentoproduto;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Trainee;
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
	
	
	
	public Vendas finalizarTrainee(Trainee trainee) {
		VendasFacade vendasFacade = new VendasFacade();
		trainee.setControle("Processo");
		float valorPrevisto = 0.0f;
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		Fornecedorcomissaocurso fornecedorComissao = fornecedorComissaoCursoFacade.consultar(
				trainee.getVendas().getFornecedorcidade().getFornecedor().getIdfornecedor(),
				trainee.getVendas().getFornecedorcidade().getCidade().getPais().getIdpais());
		if (fornecedorComissao != null) {
			Vendascomissao vendasComissao = trainee.getVendas().getVendascomissao();
			if (vendasComissao == null) {
				vendasComissao = new Vendascomissao();
				vendasComissao.setVendas(trainee.getVendas());
				vendasComissao.setPaga("Não");
			}
			if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
				float valorJuros = 0.0f;
				if (trainee.getVendas().getFormapagamento() != null) {
					valorJuros = trainee.getVendas().getFormapagamento().getValorJuros();
				}
				ComissaoTraineeBean cc = new ComissaoTraineeBean(aplicacaoMB, trainee.getVendas(),
						trainee.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(), trainee.getVendas().getOrcamento().getValorCambio(),
						trainee.getValorestrainee(), trainee.getVendas().getFormapagamento().getParcelamentopagamentoList(), null,
						vendasComissao, valorJuros);
				valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			}
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControlTrainee(trainee.getVendas(), trainee, valorPrevisto);
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
		dashBoardBean.calcularNumeroVendasProdutos(trainee.getVendas(), false);
		dashBoardBean.calcularMetaMensal(trainee.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(trainee.getVendas(), 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(trainee.getVendas(), 0, "", false);
		ProductRunnersMB productRunnersMB = new ProductRunnersMB();
		productRunnersMB.calcularPontuacao(trainee.getVendas(), pontos[0], false);
		trainee.getVendas().setPonto(pontos[0]);
		trainee.getVendas().setPontoescola(pontos[1]);
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
		if (trainee.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		List<Departamento> departamento = departamentoFacade
				.listar("select d From Departamento d where d.usuario.idusuario="
						+ trainee.getVendas().getProdutos().getIdgerente());
		if (departamento != null && departamento.size() > 0) {
			Formatacao.gravarNotificacaoVendas(titulo, trainee.getVendas().getUnidadenegocio(), trainee.getVendas().getCliente().getNome(),
					trainee.getVendas().getFornecedorcidade().getFornecedor().getNome(), trainee.getMesano(),
					trainee.getVendas().getUsuario().getNome(), vm, trainee.getVendas().getValor(), trainee.getVendas().getValorcambio(),
					trainee.getVendas().getCambio().getMoedas().getSigla(), operacao, departamento.get(0),
					imagemNotificacao, "I");
		}
		return trainee.getVendas();
	}

}
