/*
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.bean.LeadSituacaoBean;
import br.com.travelmate.bean.NumeroParcelasBean;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.LeadHistoricoFacade;
import br.com.travelmate.facade.OCursoDescontoFacade;
import br.com.travelmate.facade.OCursoFacade;
import br.com.travelmate.facade.OCursoFormaPagamentoFacade;
import br.com.travelmate.facade.OCursoProdutoFacade;
import br.com.travelmate.facade.OcursoSeguroViagemFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.TipoContatoFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadhistorico;
import br.com.travelmate.model.Ocrusoprodutos;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Ocursoformapagamento;
import br.com.travelmate.model.Ocursoseguro;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class FinalizarOrcamentoCursoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private FiltrarEscolaBean filtrarEscolaBean;
	private List<Ocrusoprodutos> listaProdutos;
	private Ocursoformapagamento formaPagamento02;
	private Ocursoformapagamento formaPagamento03;
	private Ocursoformapagamento formaPagamento04;
	private Ocurso ocurso;
	private List<Ocursodesconto> listaOcursoDesconto;
	private ResultadoOrcamentoBean resultadoOrcamentoBean;
	private boolean habilitaFormaPagamento02 = true;
	private boolean habilitaFormaPagamento03 = true;
	private boolean habilitaFormaPagamento04 = true;
	private List<NumeroParcelasBean> listaNumeroParcelas;
	private NumeroParcelasBean numeroParcelaSelecionado;

	@PostConstruct
	public void init() throws SQLException {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaProdutos = (List<Ocrusoprodutos>) session.getAttribute("listaProdutos");
		filtrarEscolaBean = (FiltrarEscolaBean) session.getAttribute("filtrarEscolaBean");
		getUsuarioLogadoMB();
		ocurso = (Ocurso) session.getAttribute("ocurso");
		listaNumeroParcelas = new ArrayList<NumeroParcelasBean>();
		gerarListaNuneroParcelas(ocurso.getDatainicio());
		resultadoOrcamentoBean = (ResultadoOrcamentoBean) session.getAttribute("resultadoOrcamentoBean");
		if (resultadoOrcamentoBean.getOcurso() != null && resultadoOrcamentoBean.getOcurso().getIdocurso() != null) {
			ocurso = resultadoOrcamentoBean.getOcurso();
		}
		popularFormaPagamento();
		calcularParcelamento();
	}

	public List<NumeroParcelasBean> getListaNumeroParcelas() {
		return listaNumeroParcelas;
	}

	public void setListaNumeroParcelas(List<NumeroParcelasBean> listaNumeroParcelas) {
		this.listaNumeroParcelas = listaNumeroParcelas;
	}

	public NumeroParcelasBean getNumeroParcelaSelecionado() {
		return numeroParcelaSelecionado;
	}

	public void setNumeroParcelaSelecionado(NumeroParcelasBean numeroParcelaSelecionado) {
		this.numeroParcelaSelecionado = numeroParcelaSelecionado;
	}

	public ResultadoOrcamentoBean getResultadoOrcamentoBean() {
		return resultadoOrcamentoBean;
	}

	public void setResultadoOrcamentoBean(ResultadoOrcamentoBean resultadoOrcamentoBean) {
		this.resultadoOrcamentoBean = resultadoOrcamentoBean;
	}

	public List<Ocrusoprodutos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Ocrusoprodutos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public FiltrarEscolaBean getFiltrarEscolaBean() {
		return filtrarEscolaBean;
	}

	public void setFiltrarEscolaBean(FiltrarEscolaBean filtrarEscolaBean) {
		this.filtrarEscolaBean = filtrarEscolaBean;
	}

	public Ocursoformapagamento getFormaPagamento02() {
		return formaPagamento02;
	}

	public void setFormaPagamento02(Ocursoformapagamento formaPagamento02) {
		this.formaPagamento02 = formaPagamento02;
	}

	public Ocursoformapagamento getFormaPagamento03() {
		return formaPagamento03;
	}

	public void setFormaPagamento03(Ocursoformapagamento formaPagamento03) {
		this.formaPagamento03 = formaPagamento03;
	}

	public Ocursoformapagamento getFormaPagamento04() {
		return formaPagamento04;
	}

	public void setFormaPagamento04(Ocursoformapagamento formaPagamento04) {
		this.formaPagamento04 = formaPagamento04;
	}

	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public boolean isHabilitaFormaPagamento02() {
		return habilitaFormaPagamento02;
	}

	public void setHabilitaFormaPagamento02(boolean habilitaFormaPagamento02) {
		this.habilitaFormaPagamento02 = habilitaFormaPagamento02;
	}

	public boolean isHabilitaFormaPagamento03() {
		return habilitaFormaPagamento03;
	}

	public void setHabilitaFormaPagamento03(boolean habilitaFormaPagamento03) {
		this.habilitaFormaPagamento03 = habilitaFormaPagamento03;
	}

	public boolean isHabilitaFormaPagamento04() {
		return habilitaFormaPagamento04;
	}

	public void setHabilitaFormaPagamento04(boolean habilitaFormaPagamento04) {
		this.habilitaFormaPagamento04 = habilitaFormaPagamento04;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Ocursodesconto> getListaOcursoDesconto() {
		return listaOcursoDesconto;
	}

	public void setListaOcursoDesconto(List<Ocursodesconto> listaOcursoDesconto) {
		this.listaOcursoDesconto = listaOcursoDesconto;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public void calcularParcelamento() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (this.formaPagamento02.isSelecionado()) {
			if (formaPagamento02.getPercentualEntrada() != null) {
				valorEntrada = ocurso.getTotalmoedanacional() * (formaPagamento02.getPercentualEntrada() / 100);
				saldo = 100 - formaPagamento02.getPercentualEntrada();
				valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
				formaPagamento02.setValorEntrada(valorEntrada.floatValue());
				formaPagamento02.setValorSaldo(valorSaldo.floatValue());
				formaPagamento02.setPercentualSaldo(saldo);
			} else if (formaPagamento02.getValorEntrada() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = ocurso.getTotalmoedanacional().doubleValue();
				formaPagamento02.setValorEntrada(valorEntrada.floatValue());
				formaPagamento02.setValorSaldo(valorSaldo.floatValue());
				formaPagamento02.setPercentualSaldo(saldo);
			}
			valorSaldo = 0.0;
			if (formaPagamento02.getNumeroparcela() > 0) {
				valorSaldo = formaPagamento02.getValorSaldo().doubleValue();
				if (valorSaldo > 0) {
					valorSaldo = valorSaldo / formaPagamento02.getNumeroparcela();
					formaPagamento02.setValorparcela(valorSaldo.floatValue());
				}
			}
		}

		// Opção 03

		if (this.formaPagamento03.isSelecionado()) {
			valorSaldo = 0.0;
			saldo = 0.0;
			valorEntrada = 0.0;
			if (formaPagamento03.getPercentualEntrada() != null) {
				valorEntrada = ocurso.getTotalmoedanacional() * (formaPagamento03.getPercentualEntrada() / 100);
				saldo = 100 - formaPagamento03.getPercentualEntrada();
				valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
				formaPagamento03.setValorEntrada(valorEntrada.floatValue());
				formaPagamento03.setValorSaldo(valorSaldo.floatValue());
				formaPagamento03.setPercentualSaldo(saldo.doubleValue());
			} else if (formaPagamento03.getValorEntrada() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = ocurso.getTotalmoedanacional().doubleValue();
				formaPagamento03.setValorEntrada(valorEntrada.floatValue());
				formaPagamento03.setValorSaldo(valorSaldo.floatValue());
				formaPagamento03.setPercentualSaldo(saldo);
			}
			valorSaldo = 0.0;
			if (formaPagamento03.getNumeroparcela() > 0) {
				if (formaPagamento03.getValorSaldo() > 0) {
					valorSaldo = formaPagamento03.getValorSaldo().doubleValue();
					if (valorSaldo > 0) {
						CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
						Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento03.getNumeroparcela(),
								"Juros Cliente");
						valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
						formaPagamento03.setValorparcela(valorSaldo.floatValue());
					}
				}
			}
		}

		// opção 04
		if (this.formaPagamento04.isSelecionado()) {
			valorSaldo = 0.0;
			saldo = 0.0;
			valorEntrada = 0.0;
			if (formaPagamento04.getPercentualEntrada() > 0) {
				valorEntrada = ocurso.getTotalmoedanacional()
						* (formaPagamento04.getPercentualEntrada().doubleValue() / 100);
				saldo = 100 - formaPagamento04.getPercentualEntrada().doubleValue();
				valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
				formaPagamento04.setValorEntrada(valorEntrada.floatValue());
				formaPagamento04.setValorSaldo(valorSaldo.floatValue());
				formaPagamento04.setPercentualSaldo(saldo);
			} else if (formaPagamento04.getValorEntrada() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = ocurso.getTotalmoedanacional().doubleValue();
				formaPagamento04.setValorEntrada(valorEntrada.floatValue());
				formaPagamento04.setValorSaldo(valorSaldo.floatValue());
				formaPagamento04.setPercentualSaldo(saldo);
			}
			valorSaldo = 0.0;
			if (formaPagamento04.getNumeroparcela() > 0) {
				if (formaPagamento04.getValorSaldo() > 0) {
					CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
					Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento04.getNumeroparcela(),
							"Juros Banco");
					Double valor = formaPagamento04.getValorSaldo().doubleValue() * cf.getCoeficiente();
					formaPagamento04.setValorparcela(valor.floatValue());
				} else {
					CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
					Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento04.getNumeroparcela(),
							"Juros Banco");
					Double valor = ocurso.getTotalmoedanacional() * cf.getCoeficiente();
					formaPagamento04.setValorparcela(valor.floatValue());
				}
			}
		}
	}

	public void calcularParcelamentoValorEntrada() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (this.formaPagamento02.isSelecionado()) {
			if (formaPagamento02.getValorEntrada() != null) {
				valorEntrada = formaPagamento02.getValorEntrada().doubleValue();
				float percentualentrada = (formaPagamento02.getValorEntrada() / ocurso.getTotalmoedanacional()) * 100;
				String valor = Formatacao.formatarFloatString(percentualentrada);
				formaPagamento03.setPercentualEntrada(Formatacao.formatarStringDouble(valor));
				saldo = 100 - formaPagamento02.getPercentualEntrada();
				valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
				formaPagamento02.setValorEntrada(valorEntrada.floatValue());
				formaPagamento02.setValorSaldo(valorSaldo.floatValue());
				formaPagamento02.setPercentualSaldo(saldo);
			} else if (formaPagamento02.getPercentualEntrada() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = ocurso.getTotalmoedanacional().doubleValue();
				formaPagamento02.setValorEntrada(valorEntrada.floatValue());
				formaPagamento02.setValorSaldo(valorSaldo.floatValue());
				formaPagamento02.setPercentualSaldo(saldo);
			}
			valorSaldo = 0.0;
			if (formaPagamento02.getNumeroparcela() > 0) {
				valorSaldo = formaPagamento02.getValorSaldo().doubleValue();
				if (valorSaldo > 0) {
					valorSaldo = valorSaldo / formaPagamento02.getNumeroparcela();
					formaPagamento02.setValorparcela(valorSaldo.floatValue());
				}
			}
		}

		// Opção 03

		if (this.formaPagamento03.isSelecionado()) {
			valorSaldo = 0.0;
			saldo = 0.0;
			valorEntrada = 0.0;
			if (formaPagamento03.getValorEntrada() != null) {
				valorEntrada = formaPagamento03.getValorEntrada().doubleValue();
				float percentualentrada = (formaPagamento03.getValorEntrada() / ocurso.getTotalmoedanacional()) * 100;
				String valor = Formatacao.formatarFloatString(percentualentrada);
				formaPagamento03.setPercentualEntrada(Formatacao.formatarStringDouble(valor));
				saldo = 100 - formaPagamento03.getPercentualEntrada();
				valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
				formaPagamento03.setValorEntrada(valorEntrada.floatValue());
				formaPagamento03.setValorSaldo(valorSaldo.floatValue());
				formaPagamento03.setPercentualSaldo(saldo.doubleValue());
			} else if (formaPagamento03.getPercentualEntrada() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = ocurso.getTotalmoedanacional().doubleValue();
				formaPagamento03.setValorEntrada(valorEntrada.floatValue());
				formaPagamento03.setValorSaldo(valorSaldo.floatValue());
				formaPagamento03.setPercentualSaldo(saldo);
			}
			valorSaldo = 0.0;
			if (formaPagamento03.getNumeroparcela() > 0) {
				if (formaPagamento03.getValorSaldo() > 0) {
					valorSaldo = formaPagamento03.getValorSaldo().doubleValue();
					if (valorSaldo > 0) {
						CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
						Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento03.getNumeroparcela(),
								"Juros Cliente");
						valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
						formaPagamento03.setValorparcela(valorSaldo.floatValue());
					}
				}
			}
		}

		// opção 04
		if (this.formaPagamento04.isSelecionado()) {
			valorSaldo = 0.0;
			saldo = 0.0;
			valorEntrada = 0.0;
			if (formaPagamento04.getValorEntrada() != null) {
				valorEntrada = formaPagamento04.getValorEntrada().doubleValue();
				float percentualentrada = (formaPagamento04.getValorEntrada() / ocurso.getTotalmoedanacional()) * 100;
				String valor = Formatacao.formatarFloatString(percentualentrada);
				formaPagamento04.setPercentualEntrada(Formatacao.formatarStringDouble(valor));
				valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
				formaPagamento04.setValorEntrada(valorEntrada.floatValue());
				formaPagamento04.setValorSaldo(valorSaldo.floatValue());
				formaPagamento04.setPercentualSaldo(saldo);
			} else if (formaPagamento04.getPercentualEntrada() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = ocurso.getTotalmoedanacional().doubleValue();
				formaPagamento04.setValorEntrada(valorEntrada.floatValue());
				formaPagamento04.setValorSaldo(valorSaldo.floatValue());
				formaPagamento04.setPercentualSaldo(saldo);
			}
			valorSaldo = 0.0;
			if (formaPagamento04.getNumeroparcela() > 0) {
				if (formaPagamento04.getValorSaldo() > 0) {
					CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
					Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento04.getNumeroparcela(),
							"Juros Banco");
					Double valor = formaPagamento04.getValorSaldo().doubleValue() * cf.getCoeficiente();
					formaPagamento04.setValorparcela(valor.floatValue());
				} else {
					CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
					Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento04.getNumeroparcela(),
							"Juros Banco");
					Double valor = ocurso.getTotalmoedanacional() * cf.getCoeficiente();
					formaPagamento04.setValorparcela(valor.floatValue());
				}
			}
		}
	}

	public String finalziar(boolean modelo) {
		OCursoProdutoFacade oCursoProdutoFacade = new OCursoProdutoFacade();
		OCursoFacade orCursoFacade = new OCursoFacade();
		try {
			ocurso.setDataorcamento(new Date());
			for (int i = 0; i < ocurso.getOcursodescontoList().size(); i++) {
				listaOcursoDesconto = ocurso.getOcursodescontoList();
			}
			ocurso.setOcursodescontoList(null);
			ocurso.setIdioma(ocurso.getFornecedorcidadeidioma().getIdioma());
			ocurso.setValorpassagem(resultadoOrcamentoBean.getValorPassagemAerea());
			ocurso.setValorvisto(resultadoOrcamentoBean.getValorVistoConsular());
			ocurso.setValoroutros(resultadoOrcamentoBean.getValorOutros());
			ocurso.setValoravista(ocurso.getTotalmoedanacional());
			ocurso.setOcrusoprodutosList(null);
			ocurso.setOcursodescontoList(null);
			ocurso.setOcursoformapagamentoList(null);
			ocurso.setOcursoseguroList(null);
			ocurso.setModelo(modelo);
			ocurso = orCursoFacade.salvar(ocurso);
			if (ocurso.isModelo()) {
				PaisFacade paisFacade = new PaisFacade();
				Pais pais = ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais();
				pais.setModelo(pais.getModelo()+1);
				paisFacade.salvar(pais);
			}
			salvarFormaPagamento();
			if (resultadoOrcamentoBean.getListaOutrosProdutos() != null) {
				for (int i = 0; i < resultadoOrcamentoBean.getListaOutrosProdutos().size(); i++) {
					Ocrusoprodutos produto = new Ocrusoprodutos();
					produto.setNumerosemanas(0.0);
					produto.setValorcoprodutos(
							resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getValorcoprodutos());
					produto.setValororiginal(resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getValorOrigianl());
					produto.setValorpromocional(0.0f);
					produto.setNome(resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getProdutosorcamento()
							.getDescricao());
					produto.setDescricao(resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getDescricao());
					if(produto.getDescricao()==null) {
						produto.setDescricao("");
					} 
					produto.setTipo(6);
					produto.setSomavalortotal(
							resultadoOrcamentoBean.getListaOutrosProdutos().get(i).isSomarvalortotal());
					if (resultadoOrcamentoBean.getListaOutrosProdutos().get(i).isSomarvalortotal()) {
						produto.setNomegrupo("Adicionais");
					} else {
						produto.setNomegrupo("CustosExtras");
					}
					produto.setOcurso(ocurso);
					oCursoProdutoFacade.salvar(produto);
				}
			}
			for (int i = 0; i < listaOcursoDesconto.size(); i++) {
				listaOcursoDesconto.get(i).setOcurso(ocurso);
				OCursoDescontoFacade oCursoDescontoFacade = new OCursoDescontoFacade();
				oCursoDescontoFacade.salvar(listaOcursoDesconto.get(i));
				ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
				Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getCopromocaoescola());
				Ocrusoprodutos produto = new Ocrusoprodutos();
				produto.setNumerosemanas(0.0);
				produto.setValorcoprodutos(valorcoprodutos);
				produto.setValororiginal(listaOcursoDesconto.get(i).getValormoedaestrangeira());
				produto.setValorpromocional(0.0f);
				produto.setNome(valorcoprodutos.getCoprodutos().getProdutosorcamento().getDescricao());
				produto.setDescricao(listaOcursoDesconto.get(i).getProdutosorcamento().getDescricao());
				if(produto.getDescricao()==null) {
					produto.setDescricao("");
				}
				produto.setTipo(8);
				produto.setNomegrupo("Desconto");
				produto.setOcurso(ocurso);
				if (produto.getValororiginal() > 0) {
					oCursoProdutoFacade.salvar(produto);
				}
			}
			if (resultadoOrcamentoBean.isSeguroSelecionado()) {
				Ocursoseguro ocursoseguro = new Ocursoseguro();
				ocursoseguro.setOcurso(ocurso);
				ocursoseguro.setValoresseguro(resultadoOrcamentoBean.getValoresSeguro());
				ocursoseguro.setDatafinal(resultadoOrcamentoBean.getSeguroviagem().getDataTermino());
				ocursoseguro.setDatainicial(resultadoOrcamentoBean.getSeguroviagem().getDataInicio());
				ocursoseguro.setNumerodias(resultadoOrcamentoBean.getSeguroviagem().getNumeroSemanas());
				ocursoseguro.setSeguradora(resultadoOrcamentoBean.getFornecedorcidade().getFornecedor().getNome());
				ocursoseguro.setValor(resultadoOrcamentoBean.getSeguroviagem().getValorSeguro());
				ocursoseguro.setSomarvalortotal(resultadoOrcamentoBean.getSeguroviagem().isSomarvalortotal());
				ocursoseguro.setValorseguroorcamento(resultadoOrcamentoBean.getCambio().getValor());
				ocursoseguro.setSegurocancelamento(resultadoOrcamentoBean.getSeguroviagem().isSegurocancelamento());
				OcursoSeguroViagemFacade ocursoSeguroViagemFacade = new OcursoSeguroViagemFacade();
				ocursoseguro = ocursoSeguroViagemFacade.salvar(ocursoseguro);
				ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
				Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getCoseguroprivado());
				Ocrusoprodutos produto = new Ocrusoprodutos();
				produto.setNumerosemanas(0.0);
				produto.setValorcoprodutos(valorcoprodutos);
				produto.setValororiginal(ocursoseguro.getValor() / ocurso.getCambio().getValor());
				produto.setValorpromocional(0.0f);
				produto.setNome(valorcoprodutos.getCoprodutos().getProdutosorcamento().getDescricao());
				produto.setDescricao(ocursoseguro.getValoresseguro().getPlano());
				if(produto.getDescricao()==null) {
					produto.setDescricao("");
				}
				produto.setTipo(7);
				produto.setNomegrupo("Seguro Viagem Privado");
				produto.setOcurso(ocurso); 
				oCursoProdutoFacade.salvar(produto);
				if(resultadoOrcamentoBean.getSeguroviagem().isSegurocancelamento()) {
					produto = new Ocrusoprodutos();
					produto.setNumerosemanas(0.0);
					produto.setValorcoprodutos(valorcoprodutos);     
					CambioFacade cambioFacade = new CambioFacade();
					Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(ocurso.getCambio().getData()),
							resultadoOrcamentoBean.getSeguroviagem().getValoresseguro().getMoedas().getIdmoedas()); 
					produto.setValororiginal(   
							aplicacaoMB.getParametrosprodutos().getSegurocancelamentovalor());
					produto.setValorpromocional(0.0f);
					produto.setNome("Seguro Cancelamento");
					produto.setDescricao("Seguro Cancelamento"); 
					produto.setTipo(7);
					if(resultadoOrcamentoBean.getSeguroviagem().isSomarvalortotal()) {
						produto.setNomegrupo("Adicionais");
					}else {
						produto.setNomegrupo("CustosExtras");
					} 
					produto.setOcurso(ocurso);
					produto.setSomavalortotal(resultadoOrcamentoBean.getSeguroviagem().isSomarvalortotal());
					oCursoProdutoFacade.salvar(produto);
				}
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.removeAttribute("resultadoOrcamentoBean");
			session.removeAttribute("ocurso");
			session.removeAttribute("listaProdutos");
			FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Orçamento salvo.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);

		} catch (SQLException ex) {
			Logger.getLogger(FinalizarOrcamentoCursoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro Salvar Orçamento", "ERRO");
		}

		for (int i = 0; i < listaProdutos.size(); i++) {
			try {
				listaProdutos.get(i).setOcurso(ocurso);
				if(listaProdutos.get(i).getDescricao()==null) {
					listaProdutos.get(i).setDescricao("");
				}
				if (listaProdutos.get(i).getDescricao().length() > 200) {
					listaProdutos.get(i).setDescricao(listaProdutos.get(i).getDescricao().substring(0, 199));
				}
				if (listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipoproduto()
						.equalsIgnoreCase("C")) {
					if (resultadoOrcamentoBean.isClientelead()) {
						salvarHistoricoLead(listaProdutos.get(i));
						Lead lead = resultadoOrcamentoBean.getLead();
						if (lead != null) {
							LeadFacade leadFacade = new LeadFacade();
							lead.setDataultimocontato(new Date());
							if (lead.getSituacao() < 3) {
								LeadSituacaoBean leadSituacaoBean = new LeadSituacaoBean(lead, lead.getSituacao(), 3);
		            			lead.setSituacao(3);
							}
							lead = leadFacade.salvar(lead);
						}
					}
				}
				oCursoProdutoFacade.salvar(listaProdutos.get(i));
			} catch (SQLException ex) {
				Logger.getLogger(FinalizarOrcamentoCursoMB.class.getName()).log(Level.SEVERE, null, ex);
				mostrarMensagem(ex, "Erro Salvar Produto", "ERRO");
			}
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.removeAttribute("cliente");
		return "consultaorcamentocurso";
	}

	public String adicionarDestinarios() {
		finalziar(false);
		limparSessao();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("ocruso", ocurso);
		return "consultaorcamentocurso";
	}

	public String voltar() {
		return "orcamentoCurso";
	}

	public void verificarFormaPgamento02() {
		try {
			calcularParcelamento();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (formaPagamento02.isSelecionado()) {
			habilitaFormaPagamento02 = false;
		} else
			habilitaFormaPagamento02 = true;
	}

	public void verificarFormaPgamento03() {
		try {
			calcularParcelamento();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (formaPagamento03.isSelecionado()) {
			habilitaFormaPagamento03 = false;
		} else
			habilitaFormaPagamento03 = true;
	}

	public void verificarFormaPgamento04() {
		try {
			calcularParcelamento();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (formaPagamento04.isSelecionado()) {
			habilitaFormaPagamento04 = false;
		} else
			habilitaFormaPagamento04 = true;
	}

	public void salvarFormaPagamento() {
		OCursoFormaPagamentoFacade oCursoFormaPagamentoFacade = new OCursoFormaPagamentoFacade();
		try {
			formaPagamento02.setOcurso(ocurso);
			formaPagamento02 = oCursoFormaPagamentoFacade.salvar(formaPagamento02);
			formaPagamento03.setOcurso(ocurso);
			formaPagamento03 = oCursoFormaPagamentoFacade.salvar(formaPagamento03);
			formaPagamento04.setOcurso(ocurso);
			formaPagamento04 = oCursoFormaPagamentoFacade.salvar(formaPagamento04);
		} catch (SQLException ex) {
			Logger.getLogger(FinalizarOrcamentoCursoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro Salvar Forma de Pagamento", "ERRO");
		}
	}

	public String retornoDialog() {
		return "consultaorcamentocurso";
	}

	public void limparSessao() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.removeAttribute("listaProdutos");
		session.removeAttribute("filtrarEscolaBean");
	}

	//Mandar data invertida para não ficar negativo na subtração de datas
	public void gerarListaNuneroParcelas(Date dataInicio) {
		NumeroParcelasBean np = new NumeroParcelasBean();
		np.setNumero(0);
		np.setTitulo("0");
		listaNumeroParcelas.add(np);
		int dias = Formatacao.subtrairDatas(new Date(), dataInicio);
		if (dias > 30) {
			int diaInicio = Formatacao.getDiaData(dataInicio);
			int diaVenciamento = Formatacao.getDiaData(new Date());
			if (diaVenciamento < diaInicio) {
				dias = dias - 30;
			}
			dias = dias / 30;
			if (dias > 0) {
				for (int i = 0; i < dias; i++) {
					np = new NumeroParcelasBean();
					np.setNumero(i + 1);
					np.setTitulo(String.valueOf(i + 1));
					listaNumeroParcelas.add(np);
				}
			}
		}
	}

	public void salvarHistoricoLead(Ocrusoprodutos ocrusoprodutos) {
		LeadHistoricoFacade leadHistoricoFacade = new LeadHistoricoFacade();
		Leadhistorico leadhistorico = new Leadhistorico();
		leadhistorico.setCliente(ocurso.getCliente());
		leadhistorico.setDatahistorico(new Date());
		leadhistorico.setDataproximocontato(new Date());
		TipoContatoFacade tipoContatoFacade = new TipoContatoFacade();
		String sql = "Select t From Tipocontato t where t.tipo='Orçamento'";
		Tipocontato tipocontato = tipoContatoFacade.consultar(sql);
		leadhistorico.setTipocontato(tipocontato);
		leadhistorico.setHistorico("ORÇAMENTO TARIFÁRIO " + ocurso.getIdocurso() + ": "
				+ ocrusoprodutos.getValorcoprodutos().getCoprodutos().getProdutosorcamento().getDescricao() + " - "
				+ ocrusoprodutos.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade()
						.getCidade().getNome()
				+ ", " + ocrusoprodutos.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
						.getFornecedorcidade().getFornecedor().getNome()
				+ ".");
		leadhistorico.setTipoorcamento("t");
		leadhistorico.setIdorcamento(ocurso.getIdocurso());
		leadhistorico = leadHistoricoFacade.salvar(leadhistorico);
		resultadoOrcamentoBean.setClientelead(false);
	}
	
	
	public void popularFormaPagamento() throws SQLException{
		formaPagamento02 = new Ocursoformapagamento();
		formaPagamento02.setNumeroparcela(1);
		formaPagamento02.setPercentualEntrada(30.0);
		formaPagamento02.setPercentualSaldo(70.0);
		formaPagamento02.setValorEntrada(0.0f);
		formaPagamento02.setValorparcela(0.0f);
		formaPagamento02.setValorSaldo(0.0f);
		formaPagamento02.setSelecionado(false);
		formaPagamento03 = new Ocursoformapagamento();
		formaPagamento03.setNumeroparcela(2);
		formaPagamento03.setPercentualEntrada(30.0);
		formaPagamento03.setPercentualSaldo(70.0);
		formaPagamento03.setValorEntrada(0.0f);
		formaPagamento03.setValorparcela(0.0f);
		formaPagamento03.setValorSaldo(0.0f);
		formaPagamento03.setSelecionado(false);
		formaPagamento04 = new Ocursoformapagamento();
		formaPagamento04.setNumeroparcela(2);
		formaPagamento04.setPercentualEntrada(30.0);
		formaPagamento04.setPercentualSaldo(70.0);
		formaPagamento04.setValorEntrada(0.0f);
		formaPagamento04.setValorparcela(0.0f);
		formaPagamento04.setValorSaldo(0.0f);
		formaPagamento04.setSelecionado(false);
		formaPagamento02.setTitulo("Opção 02 - Parcelamento antes da viagem");
		formaPagamento03.setTitulo("Opção 03 - Parcelamento em cartão de crédito em até 12X* via TravelMate");
		formaPagamento04.setTitulo("Opção 4 - Parcelamento em cheque via financeira");
		formaPagamento02.setObservacao1("O valor da entrada não pode ser pago em cartão de crédito/débito");
		formaPagamento02.setObservacao2("Parcelado mensalmente via boleto até 30 dias antes do embarque");
		formaPagamento03.setObservacao1("O valor da entrada não pode ser pago em cartão de crédito/débito");
		formaPagamento03.setObservacao2("Parcelado no cartão de crédito MASTER, VISA OU AMEX");
		formaPagamento04.setObservacao1("Parcelamento em cheque via financeira");
		formaPagamento04.setObservacao2(" ");
		if (ocurso != null && ocurso.getIdocurso() != null) {
			for (int i = 0; i < ocurso.getOcursoformapagamentoList().size(); i++) {
				if (ocurso.getOcursoformapagamentoList().get(i).getTitulo()
						.equalsIgnoreCase("Opção 02 - Parcelamento antes da viagem")) {
					formaPagamento02 = ocurso.getOcursoformapagamentoList().get(i);
					if (formaPagamento02.isSelecionado()) {
						habilitaFormaPagamento02 = false;
					}
				} else if (ocurso.getOcursoformapagamentoList().get(i).getTitulo()
						.equalsIgnoreCase("Opção 03 - Parcelamento em cartão de crédito em até 12X* via TravelMate")) {
					formaPagamento03 = ocurso.getOcursoformapagamentoList().get(i);
					if (formaPagamento03.isSelecionado()) {
						habilitaFormaPagamento03 = false;
					}
				} else if (ocurso.getOcursoformapagamentoList().get(i).getTitulo()
						.equalsIgnoreCase("Opção 4 - Parcelamento em cheque via financeira")) {
					formaPagamento04 = ocurso.getOcursoformapagamentoList().get(i);
					if (formaPagamento04.isSelecionado()) {
						habilitaFormaPagamento04 = false;
					}
				}
			}
		}
	}
	
	public String salvarModelo() {
		finalziar(true);
		limparSessao();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("ocruso", ocurso);
		return "consultaorcamentocurso";
	}
	
	public boolean habiliitarSalvarModelo() {
		if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isModeloorcamento()) {
			return true;
		}
		return false;
	}
}
  