package br.com.travelmate.managerBean.OrcamentoCurso.comparativo;

import java.util.ArrayList;
import java.util.List;

import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Ocrusoprodutos;
import br.com.travelmate.model.Ocurso;

import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;

public class GerarComparativoTarifarioBean {

	private List<OrcamentoComparativoBean> lista;
	private float totalCursoRs;
	private float totalCursoMe;
	private float totalAcRs;
	private float totalAcMe;
	private float totalTxRs;
	private float totalTxMe;
	private float totalAdRs;
	private float totalAdMe;
	private float totalCeRs;
	private float totalCeMe;
	private float totalDeRs;
	private float totalDeMe;
	List<Ocurso> listaocurso;
	private Ocrusoprodutos produtoCurso;
	private List<Ocrusoprodutos> listaAcomodacao;
	private List<Ocrusoprodutos> listaTaxas;
	private List<Ocrusoprodutos> listaAdicionais;
	private List<Ocrusoprodutos> listaDesconto;
	private List<Ocrusoprodutos> listaCustosExtras;
	private AplicacaoMB aplicacaoMB;

	public GerarComparativoTarifarioBean(List<Ocurso> listaocurso, AplicacaoMB aplicacaoMB) {
		this.listaocurso = listaocurso;
		this.aplicacaoMB = aplicacaoMB;
		lista = new ArrayList<OrcamentoComparativoBean>();
		inicarComparativo();
	}

	public List<OrcamentoComparativoBean> getLista() {
		return lista;
	}

	public void setLista(List<OrcamentoComparativoBean> lista) {
		this.lista = lista;
	}

	public float getTotalCursoRs() {
		return totalCursoRs;
	}

	public void setTotalCursoRs(float totalCursoRs) {
		this.totalCursoRs = totalCursoRs;
	}

	public float getTotalCursoMe() {
		return totalCursoMe;
	}

	public void setTotalCursoMe(float totalCursoMe) {
		this.totalCursoMe = totalCursoMe;
	}

	public float getTotalAcRs() {
		return totalAcRs;
	}

	public void setTotalAcRs(float totalAcRs) {
		this.totalAcRs = totalAcRs;
	}

	public float getTotalAcMe() {
		return totalAcMe;
	}

	public void setTotalAcMe(float totalAcMe) {
		this.totalAcMe = totalAcMe;
	}

	public float getTotalTxRs() {
		return totalTxRs;
	}

	public void setTotalTxRs(float totalTxRs) {
		this.totalTxRs = totalTxRs;
	}

	public float getTotalTxMe() {
		return totalTxMe;
	}

	public void setTotalTxMe(float totalTxMe) {
		this.totalTxMe = totalTxMe;
	}

	public float getTotalAdRs() {
		return totalAdRs;
	}

	public void setTotalAdRs(float totalAdRs) {
		this.totalAdRs = totalAdRs;
	}

	public float getTotalAdMe() {
		return totalAdMe;
	}

	public void setTotalAdMe(float totalAdMe) {
		this.totalAdMe = totalAdMe;
	}

	public float getTotalCeRs() {
		return totalCeRs;
	}

	public void setTotalCeRs(float totalCeRs) {
		this.totalCeRs = totalCeRs;
	}

	public float getTotalCeMe() {
		return totalCeMe;
	}

	public void setTotalCeMe(float totalCeMe) {
		this.totalCeMe = totalCeMe;
	}

	public float getTotalDeRs() {
		return totalDeRs;
	}

	public void setTotalDeRs(float totalDeRs) {
		this.totalDeRs = totalDeRs;
	}

	public float getTotalDeMe() {
		return totalDeMe;
	}

	public void setTotalDeMe(float totalDeMe) {
		this.totalDeMe = totalDeMe;
	}

	public List<Ocurso> getListaocurso() {
		return listaocurso;
	}

	public void setListaocurso(List<Ocurso> listaocurso) {
		this.listaocurso = listaocurso;
	}

	public Ocrusoprodutos getProdutoCurso() {
		return produtoCurso;
	}

	public void setProdutoCurso(Ocrusoprodutos produtoCurso) {
		this.produtoCurso = produtoCurso;
	}

	public List<Ocrusoprodutos> getListaAcomodacao() {
		return listaAcomodacao;
	}

	public void setListaAcomodacao(List<Ocrusoprodutos> listaAcomodacao) {
		this.listaAcomodacao = listaAcomodacao;
	}

	public List<Ocrusoprodutos> getListaTaxas() {
		return listaTaxas;
	}

	public void setListaTaxas(List<Ocrusoprodutos> listaTaxas) {
		this.listaTaxas = listaTaxas;
	}

	public List<Ocrusoprodutos> getListaAdicionais() {
		return listaAdicionais;
	}

	public void setListaAdicionais(List<Ocrusoprodutos> listaAdicionais) {
		this.listaAdicionais = listaAdicionais;
	}

	public List<Ocrusoprodutos> getListaDesconto() {
		return listaDesconto;
	}

	public void setListaDesconto(List<Ocrusoprodutos> listaDesconto) {
		this.listaDesconto = listaDesconto;
	}

	public List<Ocrusoprodutos> getListaCustosExtras() {
		return listaCustosExtras;
	}

	public void setListaCustosExtras(List<Ocrusoprodutos> listaCustosExtras) {
		this.listaCustosExtras = listaCustosExtras;
	}

	public void inicarComparativo() {
		if (listaocurso != null) {
			OrcamentoComparativoBean o;
			for (int i = 0; i < listaocurso.size(); i++) {
				gerarListaProdutos(listaocurso.get(i));
				o = new OrcamentoComparativoBean();
				o = iniciarCarregarmento(listaocurso.get(i), o);
				ProdutosOrcamentoComparativoBean poTaxa = carregarTaxas(listaocurso.get(i), i, o);
				if (poTaxa != null && poTaxa.getListaProdutosOrcamento() != null
						&& poTaxa.getListaProdutosOrcamento().size() > 0) {
					poTaxa.setTotalmelista(listaocurso.get(i).getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalTxMe));
					poTaxa.setTotalrslista(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalTxRs));
					o.getListaProdutos().add(poTaxa);
				}
				ProdutosOrcamentoComparativoBean poAcomodacao = carregarAcomodacao(listaocurso.get(i), i, o);
				if (poAcomodacao != null && poAcomodacao.getListaProdutosOrcamento() != null
						&& poAcomodacao.getListaProdutosOrcamento().size() > 0) {
					poAcomodacao.setTotalmelista(listaocurso.get(i).getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAcMe));
					poAcomodacao.setTotalrslista(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAcRs));
					o.getListaProdutos().add(poAcomodacao);
				}
				ProdutosOrcamentoComparativoBean poAdicional = carregarAdicionais(listaocurso.get(i), i, o);
				if (poAdicional != null && poAdicional.getListaProdutosOrcamento() != null
						&& poAdicional.getListaProdutosOrcamento().size() > 0) {
					poAdicional.setTotalmelista(listaocurso.get(i).getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAdMe));
					poAdicional.setTotalrslista(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAdRs));
					o.getListaProdutos().add(poAdicional);
				}
				ProdutosOrcamentoComparativoBean poDesconto = carregarDesconto(listaocurso.get(i), i, o);
				if (poDesconto != null && poDesconto.getListaProdutosOrcamento() != null
						&& poDesconto.getListaProdutosOrcamento().size() > 0) {
					poDesconto.setTotalmelista(listaocurso.get(i).getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalDeMe));
					poDesconto.setTotalrslista(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalDeRs));
					o.getListaProdutos().add(poDesconto);
				}
				calcularTotais(listaocurso.get(i), i, o);
				lista.add(o);
			}
		}
	}

	public void gerarListaProdutos(Ocurso ocurso) {
		listaAcomodacao = new ArrayList<Ocrusoprodutos>();
		listaAdicionais = new ArrayList<Ocrusoprodutos>();
		listaDesconto = new ArrayList<Ocrusoprodutos>();
		listaTaxas = new ArrayList<Ocrusoprodutos>();
		listaCustosExtras = new ArrayList<Ocrusoprodutos>();
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getCoseguroprivado());
		List<Ocrusoprodutos> lista = ocurso.getOcrusoprodutosList();
		if (lista != null) {
			totalCursoRs = 0;
			totalCursoMe = 0;
			totalAcRs = 0;
			totalAcMe = 0;
			totalTxRs = 0;
			totalTxMe = 0;
			totalAdRs = 0;
			totalAdMe = 0;
			totalCeRs = 0;
			totalCeMe = 0;
			totalDeRs = 0;
			totalDeMe = 0;
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getNomegrupo().equalsIgnoreCase("curso")
						&& lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
								.getTipoproduto() != null
						&& lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipoproduto()
								.equalsIgnoreCase("C")
						&& lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
								.getIdprodutosOrcamento() != aplicacaoMB.getParametrosprodutos().getSuplementoidade()
						&& lista.get(i).getValorcoprodutos().getProdutosuplemento().equalsIgnoreCase("valor")) {
					produtoCurso = new Ocrusoprodutos();
					produtoCurso = lista.get(i);
					float totalme = lista.get(i).getValororiginal();
					totalCursoMe = totalCursoMe + totalme;
					totalCursoRs = totalCursoRs + (totalme * ocurso.getValorcambio());
					if (lista.get(i).getValorpromocional() != null && lista.get(i).getValorpromocional() > 0) {
						if (lista.get(i).isPossuipromocao()) {
							if (listaDesconto != null && listaDesconto.size() > 0) {
								Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
								ocrusoprodutos.setDescricao("Promoção Escola Curso");
								ocrusoprodutos.setNome("Promoção Escola Curso");
								ocrusoprodutos.setNomegrupo("Desconto");
								ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
								ocrusoprodutos.setOcurso(ocurso);
								ocrusoprodutos.setTipo(8);
								ocrusoprodutos.setValorcoprodutos(valorcoprodutos);
								ocrusoprodutos.setValororiginal(
										lista.get(i).getValororiginal() - lista.get(i).getValorpromocional());
								listaDesconto.add(ocrusoprodutos);
							} else {
								listaDesconto = new ArrayList<Ocrusoprodutos>();
								Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
								ocrusoprodutos.setDescricao("Promoção Escola Curso");
								ocrusoprodutos.setNome("Promoção Escola Curso");
								ocrusoprodutos.setNomegrupo("Desconto");
								ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
								ocrusoprodutos.setOcurso(ocurso);
								ocrusoprodutos.setTipo(8);
								ocrusoprodutos.setValorcoprodutos(valorcoprodutos);
								ocrusoprodutos.setValororiginal(
										lista.get(i).getValororiginal() - lista.get(i).getValorpromocional());
								listaDesconto.add(ocrusoprodutos);
							}
						}
					}

				} else if (lista.get(i).getNomegrupo().equalsIgnoreCase("Acomodação")) {
					listaAcomodacao.add(lista.get(i));
					// descontoAcomodacao
					if (lista.get(i).isPossuipromocao()) {
						if (listaDesconto != null && listaDesconto.size() > 0) {
							Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
							ocrusoprodutos.setDescricao("Promoção Escola Acomodação");
							ocrusoprodutos.setNome("Promoção Escola Acomodação");
							ocrusoprodutos.setNomegrupo("Desconto");
							ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
							ocrusoprodutos.setOcurso(ocurso);
							ocrusoprodutos.setTipo(8);
							ocrusoprodutos.setValorcoprodutos(valorcoprodutos);
							ocrusoprodutos.setValororiginal(
									lista.get(i).getValororiginal() - lista.get(i).getValorpromocional());
							listaDesconto.add(ocrusoprodutos);
						} else {
							listaDesconto = new ArrayList<Ocrusoprodutos>();
							Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
							ocrusoprodutos.setDescricao("Promoção Escola Acomodação");
							ocrusoprodutos.setNome("Promoção Escola Acomodação");
							ocrusoprodutos.setNomegrupo("Desconto");
							ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
							ocrusoprodutos.setOcurso(ocurso);
							ocrusoprodutos.setTipo(8);
							ocrusoprodutos.setValorcoprodutos(valorcoprodutos);
							ocrusoprodutos.setValororiginal(
									lista.get(i).getValororiginal() - lista.get(i).getValorpromocional());
							listaDesconto.add(ocrusoprodutos);
						}
					}
				} else if ((lista.get(i).getNomegrupo().equalsIgnoreCase("curso")
						&& lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
								.getTipoproduto() != null
						&& !lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipoproduto()
								.equalsIgnoreCase("C"))
						|| (lista.get(i).getNomegrupo().equalsIgnoreCase("curso") && lista.get(i).getValorcoprodutos()
								.getCoprodutos().getProdutosorcamento().getTipoproduto() == null)
						|| (lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
								.getIdprodutosOrcamento() == aplicacaoMB.getParametrosprodutos().getSuplementoidade())
						|| (!lista.get(i).getValorcoprodutos().getProdutosuplemento().equalsIgnoreCase("valor")
								&& lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
										.getTipoproduto().equalsIgnoreCase("C"))) {
					listaTaxas.add(lista.get(i));
					// descontoTaxas
					if (lista.get(i).isPossuipromocao()) {
						if (listaDesconto != null && listaDesconto.size() > 0) {
							Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
							ocrusoprodutos.setDescricao("Promoção Escola " + lista.get(i).getValorcoprodutos()
									.getCoprodutos().getProdutosorcamento().getDescricao());
							ocrusoprodutos.setNome("Promoção Escola " + lista.get(i).getValorcoprodutos()
									.getCoprodutos().getProdutosorcamento().getDescricao());
							ocrusoprodutos.setNomegrupo("Desconto");
							ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
							ocrusoprodutos.setOcurso(ocurso);
							ocrusoprodutos.setTipo(8);
							ocrusoprodutos.setValorcoprodutos(valorcoprodutos);
							ocrusoprodutos.setValororiginal(
									lista.get(i).getValororiginal() - lista.get(i).getValorpromocional());
							listaDesconto.add(ocrusoprodutos);
						} else {
							listaDesconto = new ArrayList<Ocrusoprodutos>();
							Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
							ocrusoprodutos.setDescricao("Promoção Escola " + lista.get(i).getValorcoprodutos()
									.getCoprodutos().getProdutosorcamento().getDescricao());
							ocrusoprodutos.setNome("Promoção Escola " + lista.get(i).getValorcoprodutos()
									.getCoprodutos().getProdutosorcamento().getDescricao());
							ocrusoprodutos.setNomegrupo("Desconto");
							ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
							ocrusoprodutos.setOcurso(ocurso);
							ocrusoprodutos.setTipo(8);
							ocrusoprodutos.setValorcoprodutos(valorcoprodutos);
							ocrusoprodutos.setValororiginal(
									lista.get(i).getValororiginal() - lista.get(i).getValorpromocional());
							listaDesconto.add(ocrusoprodutos);
						}
					}
				} else if (lista.get(i).getNomegrupo().equalsIgnoreCase("Desconto")) {
					listaDesconto.add(lista.get(i));
				} else if (lista.get(i).getNomegrupo().equalsIgnoreCase("Adicionais")) {
					listaAdicionais.add(lista.get(i));
				} else if (lista.get(i).getNomegrupo().equalsIgnoreCase("CustosExtras")) {
					listaCustosExtras.add(lista.get(i));
				} else if (lista.get(i).getNomegrupo().equalsIgnoreCase("Opcional")) {
					listaCustosExtras.add(lista.get(i));
				} else if (lista.get(i).getNomegrupo().equalsIgnoreCase("Transfer")) {
					listaCustosExtras.add(lista.get(i));
				}
			}
		}
	}

	public OrcamentoComparativoBean iniciarCarregarmento(Ocurso ocurso, OrcamentoComparativoBean o) {
		o = carregarDados1(ocurso, o);
		return o;
	}

	public OrcamentoComparativoBean carregarDados1(Ocurso ocurso, OrcamentoComparativoBean o) {
		// Dados Pagina 02
		o.setNomeCliente1(ocurso.getCliente().getNome());
		o.setNomeConsultor1(ocurso.getUsuario().getNome());
		o.setCidadeUnidade1(ocurso.getUsuario().getUnidadenegocio().getCidade().toUpperCase());
		o.setEmailConsultor1(ocurso.getUsuario().getEmail());
		o.setFoneUnidade1(ocurso.getUsuario().getUnidadenegocio().getFone());
		o.setPaisEscola1(
				ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getNome().toUpperCase());
		o.setNomeEscola1(
				ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome().toUpperCase());
		o.setCidadeEscola1(
				ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getNome().toUpperCase());
		// Dados Pagina 03
		o.setLocalCurso1(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getNome() + ", "
				+ ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getNome());
		o.setInstituicao1(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome() + " "
				+ ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getNome());
		if (produtoCurso.getValorcoprodutos().getCoprodutos().isPacote()) {
			o.setTipoCurso1("Pacote " + ocurso.getProdutosorcamento().getDescricao());
		} else
			o.setTipoCurso1(ocurso.getProdutosorcamento().getDescricao());

		o.setDataInicio1(Formatacao.ConvercaoDataPadrao(ocurso.getDatainicio()));
		o.setDataTermino1(Formatacao.ConvercaoDataPadrao(ocurso.getDatatermino()));
		if (ocurso.getNumerodiasferiado() > 0) {
			o.setDataTermino1(o.getDataTermino1() + "(" + ocurso.getNumerodiasferiado() + " dias de intervalo.)");
		}
		o.setDuracao1(ocurso.getNumerosemanas() + " semanas");
		o.setCargahoraria1(ocurso.getCargahoraria());
		o.setTurno1(ocurso.getTurno());
		o.setTotalCursome1(
				ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalCursoMe));
		o.setTotalcursors1(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalCursoRs));
		o.setDataOrcamento1(Formatacao.ConvercaoDataPadrao(ocurso.getDataorcamento()));
		o.setCambio1(ocurso.getCambio().getMoedas().getSigla() + " "
				+ Formatacao.formatarFloatString(ocurso.getValorcambio()));

		o.setNumeroorcamento1(String.valueOf(ocurso.getIdocurso()));
		o.setListaProdutos(new ArrayList<ProdutosOrcamentoComparativoBean>());
		return o;
	}

	public ProdutosOrcamentoComparativoBean carregarTaxas(Ocurso ocurso, int coluna, OrcamentoComparativoBean o) {
		if (listaTaxas != null) {
			ProdutosOrcamentoComparativoBean po = new ProdutosOrcamentoComparativoBean();
			po.setListaProdutosOrcamento(new ArrayList<ListaProdutosOrcamentoComparativoBean>());
			ListaProdutosOrcamentoComparativoBean listaPo = null;
			for (int i = 0; i < listaTaxas.size(); i++) {
				listaPo = new ListaProdutosOrcamentoComparativoBean();
				po.setTituloLista("TAXAS");
				po.setIdgrupo(1);
				listaPo.setDescricaolista(listaTaxas.get(i).getDescricao());
				listaPo.setValorme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(listaTaxas.get(i).getValororiginal()));
				listaPo.setValorrs(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(listaTaxas.get(i).getValororiginal() * ocurso.getValorcambio()));
				po.getListaProdutosOrcamento().add(listaPo);

				totalTxMe = totalTxMe + listaTaxas.get(i).getValororiginal();
				totalTxRs = totalTxRs + (listaTaxas.get(i).getValororiginal() * ocurso.getValorcambio());
			}
			if (po != null) {
				po.setTotalmelista(
						ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalTxMe));
				po.setTotalrslista(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalTxRs));
			}
			return po;
		}
		return null;
	}

	public ProdutosOrcamentoComparativoBean carregarAcomodacao(Ocurso ocurso, int coluna, OrcamentoComparativoBean o) {
		if (listaAcomodacao != null && listaAcomodacao.size() > 0) {
			ProdutosOrcamentoComparativoBean po = new ProdutosOrcamentoComparativoBean();
			po.setListaProdutosOrcamento(new ArrayList<ListaProdutosOrcamentoComparativoBean>());
			ListaProdutosOrcamentoComparativoBean listaPo = null;
			for (int i = 0; i < listaAcomodacao.size(); i++) {
				listaPo = new ListaProdutosOrcamentoComparativoBean();
				int numeroSemana = listaAcomodacao.get(i).getNumerosemanas().intValue();
				listaPo.setDescricaolista(listaAcomodacao.get(i).getValorcoprodutos().getCoprodutos()
						.getComplementoacomodacao().getTipoacomodacao()
						+ ", "
						+ listaAcomodacao.get(i).getValorcoprodutos().getCoprodutos().getComplementoacomodacao()
								.getTipoquarto()
						+ ", "
						+ listaAcomodacao.get(i).getValorcoprodutos().getCoprodutos().getComplementoacomodacao()
								.getTiporefeicao()
						+ ", Banheiro " + listaAcomodacao.get(i).getValorcoprodutos().getCoprodutos()
								.getComplementoacomodacao().getTipobanheiro()
						+ " - " + String.valueOf(numeroSemana) + " semanas");
				listaPo.setValorme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(listaAcomodacao.get(i).getValororiginal()));
				listaPo.setValorrs(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(listaAcomodacao.get(i).getValororiginal() * ocurso.getValorcambio()));
				po.setIdgrupo(2);
				po.setTituloLista("ACOMODAÇÃO");
				po.getListaProdutosOrcamento().add(listaPo);

				totalAcMe = totalAcMe + listaAcomodacao.get(i).getValororiginal();
				totalAcRs = totalAcRs + (listaAcomodacao.get(i).getValororiginal() * ocurso.getValorcambio());
			}
			if (po != null) {
				po.setTotalmelista(
						ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAcMe));
				po.setTotalrslista(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAcRs));
			}
			return po;
		} else if (produtoCurso.getValorcoprodutos().getCoprodutos().isAcomodacao()) {
			ProdutosOrcamentoComparativoBean po = new ProdutosOrcamentoComparativoBean();
			po.setListaProdutosOrcamento(new ArrayList<ListaProdutosOrcamentoComparativoBean>());
			ListaProdutosOrcamentoComparativoBean listaPo = new ListaProdutosOrcamentoComparativoBean();
			listaPo.setDescricaolista(produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao()
					.getTipoacomodacao() + ", "
					+ produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTipoquarto()
					+ ", "
					+ produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTiporefeicao()
					+ ", Banheiro "
					+ produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTipobanheiro()
					+ ", " + produtoCurso.getNumerosemanas() + " semanas.");
			po.setIdgrupo(2);
			po.setTituloLista("ACOMODAÇÃO");
			totalAcMe = 0.00f;
			totalAcRs = 0.00f;
			po.getListaProdutosOrcamento().add(listaPo);

			po.setListaProdutosOrcamento(new ArrayList<ListaProdutosOrcamentoComparativoBean>());
			listaPo = new ListaProdutosOrcamentoComparativoBean();
			listaPo.setDescricaolista("INCLUSO NO PACOTE");
			listaPo.setSubDescricaoLista("");
			listaPo.setValorme("");
			listaPo.setValorrs("");
			po.setIdgrupo(2);
			po.setTituloLista("ACOMODAÇÃO");
			po.getListaProdutosOrcamento().add(listaPo);
			return po;
		}
		return null;
	}

	public ProdutosOrcamentoComparativoBean carregarDesconto(Ocurso ocurso, int coluna, OrcamentoComparativoBean o) {
		if (listaDesconto != null) {
			ProdutosOrcamentoComparativoBean po = new ProdutosOrcamentoComparativoBean();
			po.setListaProdutosOrcamento(new ArrayList<ListaProdutosOrcamentoComparativoBean>());
			ListaProdutosOrcamentoComparativoBean listaPo = null;
			for (int i = 0; i < listaDesconto.size(); i++) {
				listaPo = new ListaProdutosOrcamentoComparativoBean();
				listaPo.setDescricaolista(listaDesconto.get(i).getDescricao());
				listaPo.setSubDescricaoLista("");
				listaPo.setValorme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(listaDesconto.get(i).getValororiginal()));
				listaPo.setValorrs(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(listaDesconto.get(i).getValororiginal() * ocurso.getValorcambio()));
				po.setIdgrupo(3);
				po.setTituloLista("DESCONTO");
				po.getListaProdutosOrcamento().add(listaPo);

				totalDeMe = totalDeMe + listaDesconto.get(i).getValororiginal();
				totalDeRs = totalDeRs + (listaDesconto.get(i).getValororiginal() * ocurso.getValorcambio());
			}
			if (po != null) {
				po.setTotalmelista(
						ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalDeMe));
				po.setTotalrslista(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalDeRs));
			}
			return po;
		}
		return null;
	}

	public ProdutosOrcamentoComparativoBean carregarAdicionais(Ocurso ocurso, int coluna, OrcamentoComparativoBean o) {
		totalAdMe = 0.0f;
		totalAdRs = 0.0f;
		if (listaAdicionais != null) {
			ProdutosOrcamentoComparativoBean po = new ProdutosOrcamentoComparativoBean();
			po.setListaProdutosOrcamento(new ArrayList<ListaProdutosOrcamentoComparativoBean>());
			ListaProdutosOrcamentoComparativoBean listaPo = null;
			for (int i = 0; i < listaAdicionais.size(); i++) {
				listaPo = new ListaProdutosOrcamentoComparativoBean();
				listaPo.setDescricaolista(listaAdicionais.get(i).getNome());
				listaPo.setSubDescricaoLista(listaAdicionais.get(i).getDescricao());
				listaPo.setValorme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(listaAdicionais.get(i).getValororiginal()));
				listaPo.setValorrs(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(listaAdicionais.get(i).getValororiginal() * ocurso.getValorcambio()));
				po.setIdgrupo(4);
				po.setTituloLista("ITENS ADICIONAIS");
				po.getListaProdutosOrcamento().add(listaPo);

				totalAdMe = totalAdMe + listaAdicionais.get(i).getValororiginal();
				totalAdRs = totalAdRs + (listaAdicionais.get(i).getValororiginal() * ocurso.getValorcambio());
			}
			if (ocurso.getOcursoseguroList() != null) {
				if (ocurso.getOcursoseguroList().size() > 0
						&& ocurso.getOcursoseguroList().get(0).isSomarvalortotal()) {
					listaPo = new ListaProdutosOrcamentoComparativoBean();
					listaPo.setDescricaolista("Seguro Viagem - "
							+ String.valueOf(ocurso.getOcursoseguroList().get(0).getNumerodias()) + " dias");
					listaPo.setSubDescricaoLista("");
					listaPo.setValorme(ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(
							ocurso.getOcursoseguroList().get(0).getValor() / ocurso.getValorcambio()));
					listaPo.setValorrs(
							listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(ocurso.getOcursoseguroList().get(0).getValor()));
					po.setIdgrupo(4);
					po.setTituloLista("ITENS ADICIONAIS");
					po.getListaProdutosOrcamento().add(listaPo);

					totalAdMe = totalAdMe + (ocurso.getOcursoseguroList().get(0).getValor() / ocurso.getValorcambio());
					totalAdRs = totalAdRs + ocurso.getOcursoseguroList().get(0).getValor();
				}
			}
			if (po != null) {
				po.setTotalmelista(
						ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAdMe));
				po.setTotalrslista(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAdRs));
			}
			return po;
		}
		return null;
	}

	public void calcularTotais(Ocurso ocurso, int coluna, OrcamentoComparativoBean o) { 
		if (o.getListaProdutos() != null) { 
			for (int j = 0; j < o.getListaProdutos().size(); j++) {
				if (o.getListaProdutos().get(j).getIdgrupo() == 1) {
					o.setTotaltxme(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalTxMe));
					o.setTotaltxrs(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalTxRs));
				} else if (o.getListaProdutos().get(j).getIdgrupo() == 2) {
					o.setTotalacme(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAcMe));
					o.setTotalacrs(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAcRs));
				} else if (o.getListaProdutos().get(j).getIdgrupo() == 3) {
					o.setTotalDeme(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalDeMe));
					o.setTotalDers(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalDeRs));
				} else if (o.getListaProdutos().get(j).getIdgrupo() == 6) {
					o.setTotaladicionalme(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAdMe));
					o.setTotaladicionalrs(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAdRs));
				}
				o.setSubPacoteme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(totalAcMe + totalTxMe + totalCursoMe + totalAdMe - totalDeMe));
				o.setSubPacoters(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(totalAcRs + totalTxRs + totalCursoRs + totalAdRs - totalDeRs));
				o.setTotalPacoteme(ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(totalAcMe + totalCeMe + totalTxMe + totalCursoMe + totalAdMe - totalDeMe));
				o.setTotalPacoters(listaocurso.get(0).getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(totalAcRs + totalCeRs + totalTxRs + totalCursoRs + totalAdRs - totalDeRs));
			}
		}
	}

}
