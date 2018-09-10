package br.com.travelmate.managerBean.trainee;

import java.util.Date;
import java.util.List;

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoTraineeBean;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.TraineeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;

public class CadTraineeBean {

	private ProgramasBean programasBean;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Orcamento orcamento;
	private UsuarioLogadoMB usuarioLogadoMB;
	private Trainee trainee;

	public CadTraineeBean(Vendas venda, Formapagamento formaPagamento, Orcamento orcamento,
			UsuarioLogadoMB usuarioLogadoMB) {
		this.programasBean = new ProgramasBean();
		this.venda = venda;
		this.formaPagamento = formaPagamento;
		this.orcamento = orcamento;
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void SalvarAlteracaoFinanceiro(List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga,
			List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal) {
		if (listaParcelamentoPagamentoOriginal != null) {
			programasBean.salvarAlteracaoFinanceiro(venda, listaParcelamentoPagamentoAntiga,
					listaParcelamentoPagamentoOriginal, venda.getUsuario());
		}
	}

	public Trainee salvarTrainee(Trainee trainee, Vendas vendaAlterada) {
		TraineeFacade traineeFacade = new TraineeFacade();
		trainee = traineeFacade.salvar(trainee);
		this.trainee = trainee;
		return this.trainee;
	}

	public Orcamento salvarOrcamento(Cambio cambio, float totalMoedaReal, float totalMoedaEstrangeira,
			float valorCambio, String cambioAlterado
			, Float valorMoedaNacional, float valorCambioBraisl) {
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

	public Cliente salvarCliente(Cliente cliente, String data, Date dataInicio, Date dataTermino) {
		cliente = programasBean.salvarCliente(cliente, data, dataInicio, dataTermino);
		return cliente;

	}

	public void salvarNovaFichha(AplicacaoMB aplicacaoMB) {
		if (Formatacao.validarDataVenda(venda.getDataVenda())) {
			new ContasReceberBean(venda,
					formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, false, null);
		}
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
				ComissaoTraineeBean cc = new ComissaoTraineeBean(aplicacaoMB, venda,
						orcamento.getOrcamentoprodutosorcamentoList(), orcamento.getValorCambio(),
						trainee.getValorestrainee(), formaPagamento.getParcelamentopagamentoList(), null,
						vendasComissao, valorJuros, true);
				valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			}
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControlTrainee(venda, trainee, valorPrevisto);
	}

	public String verificarDadosAlterado(Trainee trainee, Trainee traineeAlterado, Fornecedorcidade fornecedorCidade,
			Vendas vendaAlterada, float valorVendaAlterada) {
		String dadosAlterado = "";
		if (vendaAlterada != null) {
			if (trainee.getCursoEstuda() != null) {
				if (!trainee.getCursoEstuda().equalsIgnoreCase(traineeAlterado.getCursoEstuda())) {
					dadosAlterado = dadosAlterado + "Curso que estuda : " + trainee.getCursoEstuda() + " | "
							+ traineeAlterado.getCursoEstuda() + "<br></br>";
				}
			}
			if (trainee.getDescricaoAreaEstudo() != null) {
				if (!trainee.getDescricaoAreaEstudo().equalsIgnoreCase(traineeAlterado.getDescricaoAreaEstudo())) {
					dadosAlterado = dadosAlterado + "Descrição área de estudo : " + trainee.getDescricaoAreaEstudo()
							+ " | " + traineeAlterado.getDescricaoAreaEstudo() + "<br></br>";
				}
			}

			if (trainee.getDuracaoCursoEstuda() != null) {
				if (!trainee.getDuracaoCursoEstuda().equalsIgnoreCase(traineeAlterado.getDuracaoCursoEstuda())) {
					dadosAlterado = dadosAlterado + "Duração do curso que estuda : " + trainee.getDuracaoCursoEstuda()
							+ " | " + traineeAlterado.getDuracaoCursoEstuda() + "<br></br>";
				}
			}
			if (trainee.getDuracaoProgramaTrabalho() != null) {
				if (!trainee.getDuracaoProgramaTrabalho()
						.equalsIgnoreCase(traineeAlterado.getDuracaoProgramaTrabalho())) {
					dadosAlterado = dadosAlterado + "Duração do programa : " + trainee.getDuracaoProgramaTrabalho()
							+ " | " + traineeAlterado.getDuracaoProgramaTrabalho() + "<br></br>";
				}
			}
			if (trainee.getEmailContatoEmergencia() != null) {
				if (!trainee.getEmailContatoEmergencia()
						.equalsIgnoreCase(traineeAlterado.getEmailContatoEmergencia())) {
					dadosAlterado = dadosAlterado + "Email contato de emergencia : "
							+ trainee.getEmailContatoEmergencia() + " | " + traineeAlterado.getEmailContatoEmergencia()
							+ "<br></br>";
				}
			}

			if (trainee.getCargo() != null) {
				if (!trainee.getCargo().equalsIgnoreCase(traineeAlterado.getCargo())) {
					dadosAlterado = dadosAlterado + "Cargo : " + trainee.getCargo() + " | " + traineeAlterado.getCargo()
							+ "<br></br>";
				}
			}
			if (trainee.getEscolheuPrograma() != null) {
				if (!trainee.getEscolheuPrograma().equalsIgnoreCase(traineeAlterado.getEscolheuPrograma())) {
					dadosAlterado = dadosAlterado + "Por que escolheu o programa : " + trainee.getEscolheuPrograma()
							+ " | " + traineeAlterado.getEscolheuPrograma() + "<br></br>";
				}
			}
			if (trainee.getFoiCondebado() != null) {
				if (!trainee.getFoiCondebado().equalsIgnoreCase(traineeAlterado.getFoiCondebado())) {
					dadosAlterado = dadosAlterado + "Foi condenado : " + trainee.getFoiCondebado() + " | "
							+ traineeAlterado.getFoiCondebado() + "<br></br>";
				}
			}
			if (trainee.getFoneContatoEmergencia() != null) {
				if (!trainee.getFoneContatoEmergencia().equalsIgnoreCase(traineeAlterado.getFoneContatoEmergencia())) {
					dadosAlterado = dadosAlterado + "Telefone contato de emergencia : "
							+ trainee.getFoneContatoEmergencia() + " | " + traineeAlterado.getFoneContatoEmergencia()
							+ "<br></br>";
				}
			}
			if (trainee.getFuma() != null) {
				if (!trainee.getFuma().equalsIgnoreCase(traineeAlterado.getFuma())) {
					dadosAlterado = dadosAlterado + "Fuma : " + trainee.getFuma() + " | " + traineeAlterado.getFuma()
							+ "<br></br>";
				}
			}
			if (trainee.getInspencaoPassado() != null) {
				if (!trainee.getInspencaoPassado().equalsIgnoreCase(traineeAlterado.getInspencaoPassado())) {
					dadosAlterado = dadosAlterado + "Inspeção sobre o passado : " + trainee.getInspencaoPassado()
							+ " | " + traineeAlterado.getInspencaoPassado() + "<br></br>";
				}
			}
			if (trainee.getInstituicaoEstuda() != null) {
				if (!trainee.getInstituicaoEstuda().equalsIgnoreCase(traineeAlterado.getInstituicaoEstuda())) {
					dadosAlterado = dadosAlterado + "Instituição onde estuda : " + trainee.getInstituicaoEstuda()
							+ " | " + traineeAlterado.getInstituicaoEstuda() + "<br></br>";
				}
			}
			if (trainee.getNivelEstudo() != null) {
				if (!trainee.getNivelEstudo().equalsIgnoreCase(traineeAlterado.getNivelEstudo())) {
					dadosAlterado = dadosAlterado + "Nível estudo : " + trainee.getNivelEstudo() + " | "
							+ traineeAlterado.getNivelEstudo() + "<br></br>";
				}
			}
			if (trainee.getNivelIngles() != null) {
				if (!trainee.getNivelIngles().equalsIgnoreCase(traineeAlterado.getNivelIngles())) {
					dadosAlterado = dadosAlterado + "Nível inglês : " + trainee.getNivelIngles() + " | "
							+ traineeAlterado.getNivelIngles() + "<br></br>";
				}
			}
			if (trainee.getNomeContatoEmergencia() != null) {
				if (!trainee.getNomeContatoEmergencia().equalsIgnoreCase(traineeAlterado.getNomeContatoEmergencia())) {
					dadosAlterado = dadosAlterado + "Nome contato de emergencia : " + trainee.getNomeContatoEmergencia()
							+ " | " + traineeAlterado.getNomeContatoEmergencia() + "<br></br>";
				}
			}
			if (trainee.getNotaSlepTest() != null) {
				if (!trainee.getNotaSlepTest().equalsIgnoreCase(traineeAlterado.getNotaSlepTest())) {
					dadosAlterado = dadosAlterado + "Nota Slep Test : " + trainee.getNotaSlepTest() + " | "
							+ traineeAlterado.getNotaSlepTest() + "<br></br>";
				}
			}
			if (trainee.getNumeroSocialSecurity() != null) {
				if (!trainee.getNumeroSocialSecurity().equalsIgnoreCase(traineeAlterado.getNumeroSocialSecurity())) {
					dadosAlterado = dadosAlterado + "Número Social Security : " + trainee.getNumeroSocialSecurity()
							+ " | " + traineeAlterado.getNumeroSocialSecurity() + "<br></br>";
				}
			}
			if (trainee.getObjetivosPrograma() != null) {
				if (!trainee.getObjetivosPrograma().equalsIgnoreCase(traineeAlterado.getObjetivosPrograma())) {
					dadosAlterado = dadosAlterado + "Objetivo do programa : " + trainee.getObjetivosPrograma() + " | "
							+ traineeAlterado.getObjetivosPrograma() + "<br></br>";
				}
			}
			if (trainee.getOcupacao() != null) {
				if (!trainee.getOcupacao().equalsIgnoreCase(traineeAlterado.getOcupacao())) {
					dadosAlterado = dadosAlterado + "Ocupação : " + trainee.getOcupacao() + " | "
							+ traineeAlterado.getOcupacao() + "<br></br>";
				}
			}
			if (trainee.getPassagemAerea() != null) {
				if (!trainee.getPassagemAerea().equalsIgnoreCase(traineeAlterado.getPassagemAerea())) {
					dadosAlterado = dadosAlterado + "Passagem área : " + trainee.getPassagemAerea() + " | "
							+ traineeAlterado.getPassagemAerea() + "<br></br>";
				}
			}
			if (trainee.getPeriodoCursoEstuda() != null) {
				if (!trainee.getPeriodoCursoEstuda().equalsIgnoreCase(traineeAlterado.getPeriodoCursoEstuda())) {
					dadosAlterado = dadosAlterado + "Periodo do curso que estuda : " + trainee.getPeriodoCursoEstuda()
							+ " | " + traineeAlterado.getPeriodoCursoEstuda() + "<br></br>";
				}
			}
			if (trainee.getMesano() != null) {
				if (!trainee.getMesano().equalsIgnoreCase(traineeAlterado.getMesano())) {
					dadosAlterado = dadosAlterado + "Início previsto : " + trainee.getMesano() + " | "
							+ traineeAlterado.getMesano() + "<br></br>";
				}
			}
			if (trainee.getPossuiAlergias() != null) {
				if (!trainee.getPossuiAlergias().equalsIgnoreCase(traineeAlterado.getPossuiAlergias())) {
					dadosAlterado = dadosAlterado + "Possui alergia : " + trainee.getPossuiAlergias() + " | "
							+ traineeAlterado.getPossuiAlergias() + "<br></br>";
				}
			}
			if (trainee.getPossuiTatuagem() != null) {
				if (!trainee.getPossuiTatuagem().equalsIgnoreCase(traineeAlterado.getPossuiTatuagem())) {
					dadosAlterado = dadosAlterado + "Possui tatuagem : " + trainee.getPossuiTatuagem() + " | "
							+ traineeAlterado.getPossuiTatuagem() + "<br></br>";
				}
			}
			if (trainee.getProblemaSaude() != null) {
				if (!trainee.getProblemaSaude().equalsIgnoreCase(traineeAlterado.getProblemaSaude())) {
					dadosAlterado = dadosAlterado + "Possui problema de saúde : " + trainee.getProblemaSaude() + " | "
							+ traineeAlterado.getProblemaSaude() + "<br></br>";
				}
			}
			if (trainee.getProgramaReponsavelEUA() != null) {
				if (!trainee.getProgramaReponsavelEUA().equalsIgnoreCase(traineeAlterado.getProgramaReponsavelEUA())) {
					dadosAlterado = dadosAlterado + "Responsavel programa EUA : " + trainee.getProgramaReponsavelEUA()
							+ " | " + traineeAlterado.getProgramaReponsavelEUA() + "<br></br>";
				}
			}
			if (trainee.getQuantoAnosEstudaIngles() != null) {
				if (trainee.getQuantoAnosEstudaIngles() != traineeAlterado.getQuantoAnosEstudaIngles()) {
					dadosAlterado = dadosAlterado + dadosAlterado + "Quantos anos estuda inglês : "
							+ String.valueOf(trainee.getQuantoAnosEstudaIngles()) + " | "
							+ String.valueOf(traineeAlterado.getQuantoAnosEstudaIngles()) + "<br></br>";
				}
			}
			if (trainee.getQuantoTrabalho() != null) {
				if (!trainee.getQuantoTrabalho().equalsIgnoreCase(traineeAlterado.getQuantoTrabalho())) {
					dadosAlterado = dadosAlterado + "Quando foi o programa : " + trainee.getQuantoTrabalho() + " | "
							+ traineeAlterado.getQuantoTrabalho() + "<br></br>";
				}
			}
			if (trainee.getRelacaoContatoEmergencia() != null) {
				if (!trainee.getRelacaoContatoEmergencia()
						.equalsIgnoreCase(traineeAlterado.getRelacaoContatoEmergencia())) {
					dadosAlterado = dadosAlterado + "Relação contato de emergencia : "
							+ trainee.getRelacaoContatoEmergencia() + " | "
							+ traineeAlterado.getRelacaoContatoEmergencia() + "<br></br>";
				}
			}
			if (trainee.getTesteDrogas() != null) {
				if (!trainee.getTesteDrogas().equalsIgnoreCase(traineeAlterado.getTesteDrogas())) {
					dadosAlterado = dadosAlterado + "Teste drogas : " + trainee.getTesteDrogas() + " | "
							+ traineeAlterado.getTesteDrogas() + "<br></br>";
				}
			}

			if (trainee.getTrabalhoj1() != null) {
				if (!trainee.getTrabalhoj1().equalsIgnoreCase(traineeAlterado.getTrabalhoj1())) {
					dadosAlterado = dadosAlterado + "J1 : " + trainee.getTrabalhoj1() + " | "
							+ traineeAlterado.getTrabalhoj1() + "<br></br>";
				}
			}
			if (fornecedorCidade.getIdfornecedorcidade() != vendaAlterada.getFornecedorcidade()
					.getIdfornecedorcidade()) {
				dadosAlterado = dadosAlterado + "Fornecedor : " + fornecedorCidade.getCidade().getNome() + " | "
						+ vendaAlterada.getFornecedorcidade().getFornecedor().getNome() + "<br></br>";
			}
		}
		return dadosAlterado;
	}

}
