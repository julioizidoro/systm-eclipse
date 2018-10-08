package br.com.travelmate.managerBean.aupair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import javax.inject.Named;

import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProductRunnersCalculosBean;
import br.com.travelmate.bean.comissao.ComissaoAuPairBean;
import br.com.travelmate.bean.comissao.ComissaoCursoBean;
import br.com.travelmate.bean.comissao.ComissaoCursoPacoteBean;
import br.com.travelmate.bean.comissao.ComissaoDemiPairBean;
import br.com.travelmate.bean.comissao.ComissaoHEInscricaoBean;
import br.com.travelmate.bean.comissao.ComissaoHighSchoolBean;
import br.com.travelmate.bean.comissao.ComissaoProgramasTeensBean;
import br.com.travelmate.bean.comissao.ComissaoSeguroBean;
import br.com.travelmate.bean.comissao.ComissaoTraineeBean;
import br.com.travelmate.bean.comissao.ComissaoVoluntariadoBean;
import br.com.travelmate.bean.comissao.ComissaoWorkBean;
import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Controleseguro;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class FinalizarMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VendasDao vendasDao;
	private AplicacaoMB aplicacaoMB;

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

	public Vendas finalizar(Aupair aupair, VendasDao vendasDao) {
		this.vendasDao = vendasDao;
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
		aupair.setVendas(vendasDao.salvar(aupair.getVendas()));
		if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
			aupair.getVendas().getOrcamento();
			aupair.getVendas().getFormapagamento();
			ComissaoAuPairBean cc = new ComissaoAuPairBean(aplicacaoMB, aupair.getVendas(),
					aupair.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
					aupair.getVendas().getOrcamento().getValorCambio(), aupair.getValoresAupair(),
					aupair.getVendas().getFormapagamento().getParcelamentopagamentoList(),
					aupair.getDataInicioPretendida01(), vendasComissao, valorJuros, true);
			valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			aupair.getVendas().setVendascomissao(cc.getVendasComissao());
		}

		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleAupair(aupair.getVendas(), aupair, valorPrevisto);
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(aupair.getVendas(), false);
		dashBoardBean.calcularMetaMensal(aupair.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(aupair.getVendas(), 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(aupair.getVendas(), 0, "", false,
				aupair.getVendas().getUsuario());

		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(aupair.getVendas(), pontos[0], 0, false,
				aupair.getVendas().getUsuario());
		aupair.getVendas().setPonto(pontos[0]);
		aupair.getVendas().setPontoescola(pontos[1]);
		String hora = Formatacao.foramtarHoraString();
		aupair.getVendas().setHora(hora);
		aupair.getVendas().setDataVenda(new Date());
		String titulo = "Ficha de Au Pair Em Análise Financeira. " + aupair.getVendas().getIdvendas();
		String operacao = "A";
		String imagemNotificacao = "inserido";

		String vm = "Venda pela Matriz";
		if (aupair.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		Formatacao.gravarNotificacaoVendasFinanceiro(titulo, aupair.getVendas().getUnidadenegocio(),
				aupair.getVendas().getCliente().getNome(),
				aupair.getVendas().getFornecedorcidade().getFornecedor().getNome(),
				Formatacao.ConvercaoDataPadrao(aupair.getDataInicioPretendida01()),
				aupair.getVendas().getUsuario().getNome(), vm, aupair.getVendas().getValor(),
				aupair.getVendas().getValorcambio(), aupair.getVendas().getCambio().getMoedas().getSigla(), operacao,
				imagemNotificacao, "I");

		return aupair.getVendas();
	}

	public Vendas finalizarTrainee(Trainee trainee) {

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
				trainee.getVendas().getOrcamento();
				trainee.getVendas().getFormapagamento();
				float valorJuros = 0.0f;
				if (trainee.getVendas().getFormapagamento() != null) {
					valorJuros = trainee.getVendas().getFormapagamento().getValorJuros();
				}
				ComissaoTraineeBean cc = new ComissaoTraineeBean(aplicacaoMB, trainee.getVendas(),
						trainee.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
						trainee.getVendas().getOrcamento().getValorCambio(), trainee.getValorestrainee(),
						trainee.getVendas().getFormapagamento().getParcelamentopagamentoList(), null, vendasComissao,
						valorJuros, true);
				valorPrevisto = cc.getVendasComissao().getValorfornecedor();
				trainee.getVendas().setVendascomissao(cc.getVendasComissao());
			}
		}

		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControlTrainee(trainee.getVendas(), trainee, valorPrevisto);
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(trainee.getVendas(), false);
		dashBoardBean.calcularMetaMensal(trainee.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(trainee.getVendas(), 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(trainee.getVendas(), 0, "", false,
				trainee.getVendas().getUsuario());
		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(trainee.getVendas(), pontos[0], 0, false,
				trainee.getVendas().getUsuario());
		trainee.getVendas().setPonto(pontos[0]);
		trainee.getVendas().setPontoescola(pontos[1]);
		String hora = Formatacao.foramtarHoraString();
		trainee.getVendas().setHora(hora);
		trainee.getVendas().setDataVenda(new Date());
		String titulo = "Ficha de Trainee Em Análise Financeira. " + trainee.getVendas().getIdvendas();
		String operacao = "A";
		String imagemNotificacao = "inserido";

		String vm = "Venda pela Matriz";
		if (trainee.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		Formatacao.gravarNotificacaoVendasFinanceiro(titulo, trainee.getVendas().getUnidadenegocio(),
				trainee.getVendas().getCliente().getNome(),
				trainee.getVendas().getFornecedorcidade().getFornecedor().getNome(), trainee.getMesano(),
				trainee.getVendas().getUsuario().getNome(), vm, trainee.getVendas().getValor(),
				trainee.getVendas().getValorcambio(), trainee.getVendas().getCambio().getMoedas().getSigla(), operacao,
				imagemNotificacao, "I");
		return trainee.getVendas();
	}

	public Vendas finalizarWork(Worktravel worktravel) {

		worktravel.setControle("Processo");
		Float valorPrevisto = 0.0f;
		Vendascomissao vendasComissao = worktravel.getVendas().getVendascomissao();
		if (vendasComissao == null) {
			vendasComissao = new Vendascomissao();
			vendasComissao.setVendas(worktravel.getVendas());
			vendasComissao.setPaga("Não");
		}
		if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
			worktravel.getVendas().getOrcamento();
			worktravel.getVendas().getFormapagamento();
			float valorJuros = 0.0f;
			if (worktravel.getVendas().getFormapagamento() != null) {
				valorJuros = worktravel.getVendas().getFormapagamento().getValorJuros();
			}
			ComissaoWorkBean cc = new ComissaoWorkBean(aplicacaoMB, worktravel.getVendas(),
					worktravel.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
					worktravel.getVendas().getOrcamento().getValorCambio(), worktravel.getValoreswork(),
					worktravel.getVendas().getFormapagamento().getParcelamentopagamentoList(), vendasComissao,
					valorJuros, true);
			valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			worktravel.getVendas().setVendascomissao(cc.getVendasComissao());
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControlWork(worktravel.getVendas(), worktravel, valorPrevisto);
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(worktravel.getVendas(), false);
		dashBoardBean.calcularMetaMensal(worktravel.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(worktravel.getVendas(), 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(worktravel.getVendas(), 0,
				worktravel.getValoreswork().getPrograma(), false, worktravel.getVendas().getUsuario());
		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(worktravel.getVendas(), pontos[0], 0, false,
				worktravel.getVendas().getUsuario());
		worktravel.getVendas().setPonto(pontos[0]);
		worktravel.getVendas().setPontoescola(pontos[1]);
		String hora = Formatacao.foramtarHoraString();
		worktravel.getVendas().setHora(hora);
		worktravel.getVendas().setDataVenda(new Date());
		String titulo = "Ficha de Work and Travel Em Análise Financeira. " + worktravel.getVendas().getIdvendas();
		String operacao = "A";
		String imagemNotificacao = "inserido";

		String vm = "Venda pela Matriz";
		if (worktravel.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		Formatacao.gravarNotificacaoVendasFinanceiro(titulo, worktravel.getVendas().getUnidadenegocio(),
				worktravel.getVendas().getCliente().getNome(),
				worktravel.getVendas().getFornecedorcidade().getFornecedor().getNome(),
				Formatacao.ConvercaoDataPadrao(worktravel.getDataInicioPretendida01()),
				worktravel.getVendas().getUsuario().getNome(), vm, worktravel.getVendas().getValor(),
				worktravel.getVendas().getValorcambio(), worktravel.getVendas().getCambio().getMoedas().getSigla(),
				operacao, imagemNotificacao, "I");
		return worktravel.getVendas();
	}

	public Vendas finalizarVoluntariado(Voluntariado voluntariado, VendasDao vendasDao) {
		this.vendasDao = vendasDao;
		float valorPrevisto = 0.0f;
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		Fornecedorcomissaocurso fornecedorComissao = fornecedorComissaoCursoFacade.consultar(
				voluntariado.getVendas().getFornecedorcidade().getFornecedor().getIdfornecedor(),
				voluntariado.getVendas().getFornecedorcidade().getCidade().getPais().getIdpais());
		if (fornecedorComissao != null) {
			Vendascomissao vendasComissao = voluntariado.getVendas().getVendascomissao();
			if (vendasComissao == null) {
				vendasComissao = new Vendascomissao();
				vendasComissao.setVendas(voluntariado.getVendas());
				vendasComissao.setPaga("Não");
				vendasComissao.setDatainicioprograma(voluntariado.getDataInicioVoluntariado());
			}
			if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
				voluntariado.getVendas().getOrcamento();
				voluntariado.getVendas().getFormapagamento();
				float valorJuros = 0.0f;
				if (voluntariado.getVendas().getFormapagamento() != null) {
					valorJuros = voluntariado.getVendas().getFormapagamento().getValorJuros();
				}
				ComissaoVoluntariadoBean cc = new ComissaoVoluntariadoBean(aplicacaoMB, voluntariado.getVendas(),
						voluntariado.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(), fornecedorComissao,
						voluntariado.getVendas().getFormapagamento().getParcelamentopagamentoList(),
						voluntariado.getDataInicioVoluntariado(), vendasComissao, valorJuros, true);
				valorPrevisto = cc.getVendasComissao().getValorfornecedor();
				voluntariado.getVendas().setVendascomissao(cc.getVendasComissao());
			}
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleVoluntariado(voluntariado.getVendas(), voluntariado, valorPrevisto);
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Seguroviagem seguroViagem = seguroViagemFacade.consultarSeguroCurso(voluntariado.getVendas().getIdvendas());
		if (seguroViagem != null && seguroViagem.getIdseguroViagem() != null) {
			FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
			Formapagamento formapagamento = formaPagamentoFacade.consultar(voluntariado.getVendas().getIdvendas());
			if (seguroViagem.getVendas().getVendascomissao() == null) {
				seguroViagem.getVendas().setVendascomissao(new Vendascomissao());
				if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
					ComissaoSeguroBean cc = new ComissaoSeguroBean(aplicacaoMB, seguroViagem.getVendas(),
							new ArrayList<Parcelamentopagamento>(), seguroViagem.getVendas().getVendascomissao(),
							seguroViagem.getDescontoloja(), seguroViagem.getDescontomatriz(), 0.0f, false,
							formapagamento, seguroViagem);
					seguroViagem.getVendas().setVendascomissao(cc.getVendasComissao());
					salvarControleSeguro(seguroViagem);
				}
			}
			String titulo = "";
			String operacao = "";
			String imagemNotificacao = "";
			titulo = "Ficha de Seguro Em Análise Financeira. " + seguroViagem.getVendas().getIdvendas();
			if (seguroViagem.isSegurocancelamento()) {
				titulo = titulo + " Com Cancelamento";
			}
			operacao = "I";
			imagemNotificacao = "inserido";
			String vm = "Venda pela Matriz";
			if (seguroViagem.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
				vm = "Venda pela Loja";
			}
			gerarPontuacaoSeguro(seguroViagem.getVendas());
			Formatacao.gravarNotificacaoVendasFinanceiro(titulo, seguroViagem.getVendas().getUnidadenegocio(),
					seguroViagem.getVendas().getCliente().getNome(),
					seguroViagem.getVendas().getFornecedorcidade().getFornecedor().getNome(),
					Formatacao.ConvercaoDataPadrao(seguroViagem.getDataInicio()),
					seguroViagem.getVendas().getUsuario().getNome(), vm, seguroViagem.getVendas().getValor(),
					seguroViagem.getVendas().getCambio().getValor(),
					seguroViagem.getVendas().getCambio().getMoedas().getSigla(), operacao, imagemNotificacao, "I");
		}
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(voluntariado.getVendas(), false);
		dashBoardBean.calcularMetaMensal(voluntariado.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(voluntariado.getVendas(), 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(voluntariado.getVendas(), 0, "", false,
				voluntariado.getVendas().getUsuario());
		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(voluntariado.getVendas(), pontos[0], 0, false,
				voluntariado.getVendas().getUsuario());
		voluntariado.getVendas().setPonto(pontos[0]);
		voluntariado.getVendas().setPontoescola(pontos[1]);
		String hora = Formatacao.foramtarHoraString();
		voluntariado.getVendas().setHora(hora);
		voluntariado.getVendas().setDataVenda(new Date());
		String titulo = "Ficha de Voluntariado em Análise Financeira. " + voluntariado.getVendas().getIdvendas();
		String operacao = "A";
		String imagemNotificacao = "inserido";

		String vm = "Venda pela Matriz";
		if (voluntariado.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		Formatacao.gravarNotificacaoVendasFinanceiro(titulo, voluntariado.getVendas().getUnidadenegocio(),
				voluntariado.getVendas().getCliente().getNome(),
				voluntariado.getVendas().getFornecedorcidade().getFornecedor().getNome(),
				Formatacao.ConvercaoDataPadrao(voluntariado.getDataInicioVoluntariado()),
				voluntariado.getVendas().getUsuario().getNome(), vm, voluntariado.getVendas().getValor(),
				voluntariado.getVendas().getValorcambio(), voluntariado.getVendas().getCambio().getMoedas().getSigla(),
				operacao, imagemNotificacao, "I");
		return voluntariado.getVendas();
	}

	public Vendas finalizarDemipair(Demipair demipair) {

		float valorPrevisto = 0.0f;
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		Fornecedorcomissaocurso fornecedorComissao = fornecedorComissaoCursoFacade.consultar(
				demipair.getVendas().getFornecedorcidade().getFornecedor().getIdfornecedor(),
				demipair.getVendas().getFornecedorcidade().getCidade().getPais().getIdpais());
		if (fornecedorComissao != null) {
			Vendascomissao vendasComissao = demipair.getVendas().getVendascomissao();
			if (vendasComissao == null) {
				vendasComissao = new Vendascomissao();
				vendasComissao.setVendas(demipair.getVendas());
				vendasComissao.setPaga("Não");
			}
			if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
				demipair.getVendas().getOrcamento();
				demipair.getVendas().getFormapagamento();
				float valorJuros = 0.0f;
				if (demipair.getVendas().getFormapagamento() != null) {
					valorJuros = demipair.getVendas().getFormapagamento().getValorJuros();
				}
				ComissaoDemiPairBean cc = new ComissaoDemiPairBean(aplicacaoMB, demipair.getVendas(),
						demipair.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(), fornecedorComissao,
						demipair.getVendas().getFormapagamento().getParcelamentopagamentoList(),
						demipair.getDatainicio(), vendasComissao, valorJuros, true);
				valorPrevisto = cc.getVendasComissao().getValorfornecedor();
				demipair.getVendas().setVendascomissao(cc.getVendasComissao());
			}
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleDemipair(demipair.getVendas(), demipair, valorPrevisto);
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(demipair.getVendas(), false);
		dashBoardBean.calcularMetaMensal(demipair.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(demipair.getVendas(), 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(demipair.getVendas(), 0, "", false,
				demipair.getVendas().getUsuario());
		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(demipair.getVendas(), pontos[0], 0, false,
				demipair.getVendas().getUsuario());
		demipair.getVendas().setPonto(pontos[0]);
		demipair.getVendas().setPontoescola(pontos[1]);
		String hora = Formatacao.foramtarHoraString();
		demipair.getVendas().setHora(hora);
		demipair.getVendas().setDataVenda(new Date());
		String titulo = "Ficha de Demi pair Em Análise Financeira. " + demipair.getVendas().getIdvendas();
		String operacao = "A";
		String imagemNotificacao = "inserido";

		String vm = "Venda pela Matriz";
		if (demipair.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		Formatacao.gravarNotificacaoVendasFinanceiro(titulo, demipair.getVendas().getUnidadenegocio(),
				demipair.getVendas().getCliente().getNome(),
				demipair.getVendas().getFornecedorcidade().getFornecedor().getNome(),
				Formatacao.ConvercaoDataPadrao(demipair.getDatainicio()), demipair.getVendas().getUsuario().getNome(),
				vm, demipair.getVendas().getValor(), demipair.getVendas().getValorcambio(),
				demipair.getVendas().getCambio().getMoedas().getSigla(), operacao, imagemNotificacao, "I");
		return demipair.getVendas();
	}

	public Vendas finalizarHighSchool(Highschool highschool, CambioDao cambioDao) {
		float valorPrevisto = 0.0f;
		Vendascomissao vendasComissao = highschool.getVendas().getVendascomissao();
		if (vendasComissao == null) {
			vendasComissao = new Vendascomissao();
			vendasComissao.setVendas(highschool.getVendas());
			vendasComissao.setPaga("Não");
		}
		float valorJuros = 0.0f;
		if (highschool.getVendas().getFormapagamento() != null) {
			valorJuros = highschool.getVendas().getFormapagamento().getValorJuros();
		}
		if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
			highschool.getVendas().getOrcamento();
			highschool.getVendas().getFormapagamento();
			Cambio cambioComissao = cambioDao.consultarCambioMoeda(
					Formatacao.ConvercaoDataSql(highschool.getVendas().getDataVenda()),
					highschool.getVendas().getCambio().getMoedas().getIdmoedas());
			ComissaoHighSchoolBean cc = new ComissaoHighSchoolBean(aplicacaoMB, highschool.getVendas(),
					highschool.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
					highschool.getVendas().getCambio(), highschool.getValoreshighschool(),
					highschool.getVendas().getFormapagamento().getParcelamentopagamentoList(), vendasComissao,
					highschool.getValoreshighschool().getDatainicio(), valorJuros, true, cambioComissao);
			valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			highschool.getVendas().setVendascomissao(cc.getVendasComissao());
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleHighSchool(highschool.getVendas(), highschool, valorPrevisto);
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(highschool.getVendas(), false);
		dashBoardBean.calcularMetaMensal(highschool.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(highschool.getVendas(), 0, false);
		String programa = "";
		if (highschool.getValoreshighschool().getDuracao().equalsIgnoreCase("01 Semestre")
				|| highschool.getValoreshighschool().getDuracao().equalsIgnoreCase("1 Semestre")
				|| highschool.getValoreshighschool().getDuracao().equalsIgnoreCase("1 Semetre")
				|| highschool.getValoreshighschool().getDuracao().equalsIgnoreCase("01 Semetre")) {
			programa = "Semestre";
		} else {
			programa = "Ano";
		}
		int[] pontos = dashBoardBean.calcularPontuacao(highschool.getVendas(), 0, programa, false,
				highschool.getVendas().getUsuario());
		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(highschool.getVendas(), pontos[0], 0, false,
				highschool.getVendas().getUsuario());
		highschool.getVendas().setPonto(pontos[0]);
		highschool.getVendas().setPontoescola(pontos[1]);
		String hora = Formatacao.foramtarHoraString();
		highschool.getVendas().setHora(hora);
		highschool.getVendas().setDataVenda(new Date());
		String titulo = "Ficha de High School Em Análise Financeira. " + highschool.getVendas().getIdvendas();
		String operacao = "A";
		String imagemNotificacao = "inserido";

		String vm = "Venda pela Matriz";
		if (highschool.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		Formatacao.gravarNotificacaoVendasFinanceiro(titulo, highschool.getVendas().getUnidadenegocio(),
				highschool.getVendas().getCliente().getNome(),
				highschool.getVendas().getFornecedorcidade().getFornecedor().getNome(), highschool.getDataInicio(),
				highschool.getVendas().getUsuario().getNome(), vm, highschool.getVendas().getValor(),
				highschool.getVendas().getValorcambio(), highschool.getVendas().getCambio().getMoedas().getSigla(),
				operacao, imagemNotificacao, "I");
		return highschool.getVendas();
	}

	public Vendas finalizarTeens(Programasteens programasteens) {

		float valorPrevisto = 0.0f;
		valorPrevisto = 0.0f;
		Vendascomissao vendasComissao = programasteens.getVendas().getVendascomissao();
		if (vendasComissao == null) {
			vendasComissao = new Vendascomissao();
			vendasComissao.setVendas(programasteens.getVendas());
			vendasComissao.setPaga("Não");
		}
		float valorJuros = 0.0f;
		if (programasteens.getVendas().getFormapagamento() != null) {
			valorJuros = programasteens.getVendas().getFormapagamento().getValorJuros();
		}
		if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
			programasteens.getVendas().getOrcamento();
			programasteens.getVendas().getFormapagamento();
			ComissaoProgramasTeensBean cc = new ComissaoProgramasTeensBean(aplicacaoMB, programasteens.getVendas(),
					programasteens.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
					programasteens.getVendas().getOrcamento().getValorCambio(),
					programasteens.getValoresprogramasteens(),
					programasteens.getVendas().getFormapagamento().getParcelamentopagamentoList(),
					programasteens.getDataInicioCurso(), vendasComissao, valorJuros, true);
			valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			programasteens.getVendas().setVendascomissao(cc.getVendasComissao());
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleProgramaTeens(programasteens.getVendas(), programasteens, valorPrevisto);
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(programasteens.getVendas(), false);
		dashBoardBean.calcularMetaMensal(programasteens.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(programasteens.getVendas(), 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(programasteens.getVendas(), 0, "", false,
				programasteens.getVendas().getUsuario());
		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(programasteens.getVendas(), pontos[0], 0, false,
				programasteens.getVendas().getUsuario());
		programasteens.getVendas().setPonto(pontos[0]);
		programasteens.getVendas().setPontoescola(pontos[1]);
		String hora = Formatacao.foramtarHoraString();
		programasteens.getVendas().setHora(hora);
		programasteens.getVendas().setDataVenda(new Date());
		String titulo = "Ficha de Teens Em Análise Financeira. " + programasteens.getVendas().getIdvendas();
		String operacao = "A";
		String imagemNotificacao = "inserido";

		String vm = "Venda pela Matriz";
		if (programasteens.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		Formatacao.gravarNotificacaoVendasFinanceiro(titulo, programasteens.getVendas().getUnidadenegocio(),
				programasteens.getVendas().getCliente().getNome(),
				programasteens.getVendas().getFornecedorcidade().getFornecedor().getNome(),
				Formatacao.ConvercaoDataPadrao(programasteens.getDataInicioCurso()),
				programasteens.getVendas().getUsuario().getNome(), vm, programasteens.getVendas().getValor(),
				programasteens.getVendas().getValorcambio(),
				programasteens.getVendas().getCambio().getMoedas().getSigla(), operacao, imagemNotificacao, "I");
		return programasteens.getVendas();
	}

	public Vendas finalizarCurso(Curso curso, VendasDao vendasDao) {
		this.vendasDao = vendasDao;
		float valorPrevisto = 0.0f;
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		Fornecedorcomissaocurso fornecedorComissao = fornecedorComissaoCursoFacade.consultar(
				curso.getVendas().getFornecedorcidade().getFornecedor().getIdfornecedor(),
				curso.getVendas().getFornecedorcidade().getCidade().getPais().getIdpais());
		if (fornecedorComissao != null) {
			Vendascomissao vendasComissao = curso.getVendas().getVendascomissao();
			if (vendasComissao == null) {
				vendasComissao = new Vendascomissao();
				vendasComissao.setVendas(curso.getVendas());
				vendasComissao.setPaga("Não");
				vendasComissao.setProdutos(curso.getVendas().getProdutos());
			}
			if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
				curso.getVendas().getOrcamento();
				curso.getVendas().getFormapagamento();
				float valorJuros = 0.0f;
				if (curso.getVendas().getFormapagamento() != null) {
					valorJuros = curso.getVendas().getFormapagamento().getValorJuros();
				}
				if (curso.getVendas().getVendaspacote() == null) {
					ComissaoCursoBean cc = new ComissaoCursoBean(aplicacaoMB, curso.getVendas(),
							curso.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(), fornecedorComissao,
							curso.getVendas().getFormapagamento().getParcelamentopagamentoList(), curso.getDataInicio(),
							vendasComissao, valorJuros, true);
					valorPrevisto = cc.getVendasComissao().getValorfornecedor();
					curso.getVendas().setVendascomissao(cc.getVendasComissao());
				} else {
					ComissaoCursoPacoteBean cc = new ComissaoCursoPacoteBean(aplicacaoMB, curso.getVendas(),
							curso.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(), fornecedorComissao,
							curso.getVendas().getFormapagamento().getParcelamentopagamentoList(), curso.getDataInicio(),
							vendasComissao, valorJuros, true);
					valorPrevisto = cc.getVendasComissao().getValorfornecedor();
					curso.getVendas().setVendascomissao(cc.getVendasComissao());
				}

			}
		}

		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleCurso(curso.getVendas(), curso, valorPrevisto);
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Seguroviagem seguroViagem = seguroViagemFacade.consultarSeguroCurso(curso.getVendas().getIdvendas());
		if (seguroViagem != null && seguroViagem.getIdseguroViagem() != null) {
			FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
			Formapagamento formapagamento = formaPagamentoFacade.consultar(curso.getVendas().getIdvendas());
			if (seguroViagem.getVendas().getVendascomissao() == null) {
				seguroViagem.getVendas().setVendascomissao(new Vendascomissao());
				if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
					ComissaoSeguroBean cc = new ComissaoSeguroBean(aplicacaoMB, seguroViagem.getVendas(),
							new ArrayList<Parcelamentopagamento>(), seguroViagem.getVendas().getVendascomissao(),
							seguroViagem.getDescontoloja(), seguroViagem.getDescontomatriz(), 0.0f, false,
							formapagamento, seguroViagem);
					seguroViagem.getVendas().setVendascomissao(cc.getVendasComissao());
					salvarControleSeguro(seguroViagem);
				}
			}
			String titulo = "";
			String operacao = "";
			String imagemNotificacao = "";
			titulo = "Ficha de Seguro Em Análise Financeira. " + seguroViagem.getVendas().getIdvendas();
			if (seguroViagem.isSegurocancelamento()) {
				titulo = titulo + " Com Cancelamento";
			}
			operacao = "I";
			imagemNotificacao = "inserido";
			String vm = "Venda pela Matriz";
			if (seguroViagem.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
				vm = "Venda pela Loja";
			}
			gerarPontuacaoSeguro(seguroViagem.getVendas());
			Formatacao.gravarNotificacaoVendasFinanceiro(titulo, seguroViagem.getVendas().getUnidadenegocio(),
					seguroViagem.getVendas().getCliente().getNome(),
					seguroViagem.getVendas().getFornecedorcidade().getFornecedor().getNome(),
					Formatacao.ConvercaoDataPadrao(seguroViagem.getDataInicio()),
					seguroViagem.getVendas().getUsuario().getNome(), vm, seguroViagem.getVendas().getValor(),
					seguroViagem.getVendas().getCambio().getValor(),
					seguroViagem.getVendas().getCambio().getMoedas().getSigla(), operacao, imagemNotificacao, "I");
		}
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(curso.getVendas(), false);
		dashBoardBean.calcularMetaMensal(curso.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(curso.getVendas(), 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(curso.getVendas(), 0, "", false, curso.getVendas().getUsuario());
		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(curso.getVendas(), pontos[0], 0, false,
				curso.getVendas().getUsuario());
		curso.getVendas().setPonto(pontos[0]);
		curso.getVendas().setPontoescola(pontos[1]);
		String hora = Formatacao.foramtarHoraString();
		curso.getVendas().setHora(hora);
		curso.getVendas().setDataVenda(new Date());
		String titulo = "Ficha de Curso Em Análise Financeira. " + curso.getVendas().getIdvendas();
		String operacao = "A";
		String imagemNotificacao = "inserido";

		String vm = "Venda pela Matriz";
		if (curso.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}

		Formatacao.gravarNotificacaoVendasFinanceiro(titulo, curso.getVendas().getUnidadenegocio(),
				curso.getVendas().getCliente().getNome(),
				curso.getVendas().getFornecedorcidade().getFornecedor().getNome(),
				Formatacao.ConvercaoDataPadrao(curso.getDataInicio()), curso.getVendas().getUsuario().getNome(), vm,
				curso.getVendas().getValor(), curso.getVendas().getValorcambio(),
				curso.getVendas().getCambio().getMoedas().getSigla(), operacao, imagemNotificacao, "I");
		return curso.getVendas();
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

	public void gerarPontuacaoSeguro(Vendas vendaSeguro) {

		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(vendaSeguro, false);
		dashBoardBean.calcularMetaMensal(vendaSeguro, 0, false);
		dashBoardBean.calcularMetaAnual(vendaSeguro, 0, false);
		int[] pontos = dashBoardBean.calcularPontuacao(vendaSeguro, 0, "", false, vendaSeguro.getUsuario());
		vendaSeguro.setPonto(pontos[0]);
		vendaSeguro.setPontoescola(pontos[1]);
		String hora = Formatacao.foramtarHoraString();
		vendaSeguro.setHora(hora);
		vendaSeguro.setDataVenda(new Date());
		vendaSeguro = vendasDao.salvar(vendaSeguro);
		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(vendaSeguro, pontos[0], 0, false, vendaSeguro.getUsuario());
	}

	
	public Vendas finalizarHe(He he, VendasDao vendasDao) {
		new ComissaoHEInscricaoBean(aplicacaoMB, he.getVendas(),
				he.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
				he.getVendas().getFormapagamento().getParcelamentopagamentoList(), new Vendascomissao(), 0.0f, true);
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularMetaMensal(he.getVendas(), 0, false);
		dashBoardBean.calcularMetaAnual(he.getVendas(), 0, false);
		int[] pontos;
		String tipoHE = "";
		if (he.isFichafinal()) {
			tipoHE = "Final";
		}else {
			tipoHE = "Inscrição";
		}
		if (he.getNumerosemanas() != null && he.getNumerosemanas() > 0) {
			pontos = dashBoardBean.calcularPontuacao(he.getVendas(), he.getNumerosemanas(), tipoHE , false,
					he.getVendas().getUsuario());
		} else {
			pontos = dashBoardBean.calcularPontuacao(he.getVendas(), 0, tipoHE, false,
					he.getVendas().getUsuario());
		}
		ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
		productRunnersCalculosBean.calcularPontuacao(he.getVendas(), pontos[0], 0, false, he.getVendas().getUsuario());
		he.getVendas().setPonto(pontos[0]);
		he.getVendas().setPontoescola(pontos[1]);
		he.setVendas(vendasDao.salvar(he.getVendas()));
		String titulo = "Nova ficha de inscrição Higher Education";
		String operacao = "A";
		String imagemNotificacao = "inserido";
		String vm = "Venda pela Matriz";
		if (he.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		List<Departamento> departamento = departamentoFacade
				.listar("select d From Departamento d where d.usuario.idusuario="
						+ he.getVendas().getProdutos().getIdgerente());
		if (departamento != null && departamento.size() > 0) {
			if (he.getDatainicio() != null) {
				Formatacao.gravarNotificacaoVendas(titulo, he.getVendas().getUnidadenegocio(),
						he.getVendas().getCliente().getNome(),
						he.getVendas().getFornecedorcidade().getFornecedor().getNome(),
						Formatacao.ConvercaoDataPadrao(he.getDatainicio()), he.getVendas().getUsuario().getNome(), vm,
						he.getVendas().getValor(), he.getVendas().getValorcambio(),
						he.getVendas().getCambio().getMoedas().getSigla(), operacao, departamento.get(0),
						imagemNotificacao, "I");
			} else {
				Formatacao.gravarNotificacaoVendas(titulo, he.getVendas().getUnidadenegocio(),
						he.getVendas().getCliente().getNome(),
						he.getVendas().getFornecedorcidade().getFornecedor().getNome(), he.getMesano1(),
						he.getVendas().getUsuario().getNome(), vm, he.getVendas().getValor(),
						he.getVendas().getValorcambio(), he.getVendas().getCambio().getMoedas().getSigla(), operacao,
						departamento.get(0), imagemNotificacao, "I");
			}
		}
		return he.getVendas();
	}

}
