package br.com.travelmate.managerBean.OrcamentoCurso.pdf;

import java.util.ArrayList;
import java.util.List;
 
import br.com.travelmate.facade.EscolaPadraoFacade;
import br.com.travelmate.facade.OcClienteFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.model.Escolapadrao;
import br.com.travelmate.model.Occliente;
import br.com.travelmate.model.Ocrusoprodutos;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;


public class GerarOcamentoPDFBean {

	private List<OrcamentoPDFBean> lista;
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
	private Ocurso ocurso;
	private Ocrusoprodutos produtoCurso;
	private List<Ocrusoprodutos> listaAcomodacao;
	private List<Ocrusoprodutos> listaTaxas;
	private List<Ocrusoprodutos> listaAdicionais;
	private List<Ocrusoprodutos> listaDesconto;
	private List<Ocrusoprodutos> listaCustosExtras;
	private String composicao;
	private AplicacaoMB aplicacaoMB;

	public GerarOcamentoPDFBean(Ocurso ocurso, AplicacaoMB aplicacaoMB) {
		this.ocurso = ocurso;
		this.aplicacaoMB = aplicacaoMB;
		lista = new ArrayList<OrcamentoPDFBean>();
		listaAcomodacao = new ArrayList<Ocrusoprodutos>();
		listaAdicionais = new ArrayList<Ocrusoprodutos>();
		listaDesconto = new ArrayList<Ocrusoprodutos>();
		listaCustosExtras = new ArrayList<Ocrusoprodutos>();
		listaTaxas = new ArrayList<Ocrusoprodutos>();
		composicao = "CURSO";
		gerarListaProdutos();
		carregarTaxas();
		carregarAcomodacao();
		carregarAdicionais();
		carregarDesconto();
		carregarCustosExtras();
		calcularTotais();
	}

	public List<OrcamentoPDFBean> getLista() {
		return lista;
	}

	public void setLista(List<OrcamentoPDFBean> lista) {
		this.lista = lista;
	}

	public void gerarListaProdutos() { 
		List<Ocrusoprodutos> lista = ocurso.getOcrusoprodutosList();
		if (lista != null) {
			totalCursoMe = 0;
			totalCursoRs = 0;
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
					float valororiginal = lista.get(i).getValororiginal();
					if (lista.get(i).getValorpromocional() != null && 
							lista.get(i).getValorpromocional()!=valororiginal) {
						if (lista.get(i).isPossuipromocao()) {
							if (listaDesconto != null && listaDesconto.size() > 0) {
								Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
								ocrusoprodutos.setDescricao("Promoção Escola Curso");
								ocrusoprodutos.setNome("Promoção Escola Curso");
								ocrusoprodutos.setNomegrupo("Desconto");
								ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
								ocrusoprodutos.setOcurso(ocurso);
								ocrusoprodutos.setTipo(8);
								ocrusoprodutos.setValorcoprodutos(lista.get(i).getValorcoprodutos());
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
								ocrusoprodutos.setValorcoprodutos(lista.get(i).getValorcoprodutos());
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
							if(lista.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().isAcomodacaoindependente()) {
								ocrusoprodutos.setDescricao("Promoção Acomodação");
								ocrusoprodutos.setNome("Promoção Acomodação");
							}else {
								ocrusoprodutos.setDescricao("Promoção Escola Acomodação");
								ocrusoprodutos.setNome("Promoção Escola Acomodação");
							}
							ocrusoprodutos.setNomegrupo("Desconto");
							ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
							ocrusoprodutos.setOcurso(ocurso);
							ocrusoprodutos.setTipo(8);
							ocrusoprodutos.setValorcoprodutos(lista.get(i).getValorcoprodutos());
							ocrusoprodutos.setValororiginal(
									lista.get(i).getValororiginal() - lista.get(i).getValorpromocional());
							listaDesconto.add(ocrusoprodutos);
						} else {
							listaDesconto = new ArrayList<Ocrusoprodutos>();
							Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
							if(lista.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().isAcomodacaoindependente()) {
								ocrusoprodutos.setDescricao("Promoção Acomodação");
								ocrusoprodutos.setNome("Promoção Acomodação");
							}else {
								ocrusoprodutos.setDescricao("Promoção Escola Acomodação");
								ocrusoprodutos.setNome("Promoção Escola Acomodação");
							}
							ocrusoprodutos.setNomegrupo("Desconto");
							ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
							ocrusoprodutos.setOcurso(ocurso);
							ocrusoprodutos.setTipo(8);
							ocrusoprodutos.setValorcoprodutos(lista.get(i).getValorcoprodutos());
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
							if(lista.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().isAcomodacaoindependente()) {
								ocrusoprodutos.setDescricao("Promoção " + lista.get(i).getValorcoprodutos()
										.getCoprodutos().getProdutosorcamento().getDescricao());
								ocrusoprodutos.setNome("Promoção " + lista.get(i).getValorcoprodutos()
										.getCoprodutos().getProdutosorcamento().getDescricao());
							}else {
								ocrusoprodutos.setDescricao("Promoção Escola " + lista.get(i).getValorcoprodutos()
										.getCoprodutos().getProdutosorcamento().getDescricao());
								ocrusoprodutos.setNome("Promoção Escola " + lista.get(i).getValorcoprodutos()
										.getCoprodutos().getProdutosorcamento().getDescricao());
							} 
							ocrusoprodutos.setNomegrupo("Desconto");
							ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
							ocrusoprodutos.setOcurso(ocurso);
							ocrusoprodutos.setTipo(8);
							ocrusoprodutos.setValorcoprodutos(lista.get(i).getValorcoprodutos());  
							ocrusoprodutos.setValororiginal(
									lista.get(i).getValororiginal() - lista.get(i).getValorpromocional());
							listaDesconto.add(ocrusoprodutos);
						} else {
							listaDesconto = new ArrayList<Ocrusoprodutos>();
							Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
							if(lista.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().isAcomodacaoindependente()) {
								ocrusoprodutos.setDescricao("Promoção " + lista.get(i).getValorcoprodutos()
										.getCoprodutos().getProdutosorcamento().getDescricao());
								ocrusoprodutos.setNome("Promoção " + lista.get(i).getValorcoprodutos()
										.getCoprodutos().getProdutosorcamento().getDescricao());
							}else {
								ocrusoprodutos.setDescricao("Promoção Escola " + lista.get(i).getValorcoprodutos()
										.getCoprodutos().getProdutosorcamento().getDescricao());
								ocrusoprodutos.setNome("Promoção Escola " + lista.get(i).getValorcoprodutos()
										.getCoprodutos().getProdutosorcamento().getDescricao());
							}  
							ocrusoprodutos.setNomegrupo("Desconto");
							ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
							ocrusoprodutos.setOcurso(ocurso);
							ocrusoprodutos.setTipo(8);
							ocrusoprodutos.setValorcoprodutos(lista.get(i).getValorcoprodutos());
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

	public OrcamentoPDFBean carregarDados() {
		OrcamentoPDFBean o = new OrcamentoPDFBean();
		Escolapadrao escolaPadrao=null;
		// Dados Pagina 02
		if(ocurso.getCliente()!=null && ocurso.getCliente().getIdcliente()>1){
			o.setNomeCliente(ocurso.getCliente().getNome());
		}else{
			OcClienteFacade ocClienteFacade = new OcClienteFacade();
			Occliente occliente = ocClienteFacade.consultar(ocurso.getOccliente());
			o.setNomeCliente(occliente.getNome());
		}    
		o.setNomeConsultor(ocurso.getUsuario().getNome());
		o.setCidadeUnidade(ocurso.getUsuario().getUnidadenegocio().getNomerelatorio().toUpperCase());
		o.setEmailConsultor(ocurso.getUsuario().getEmail());
		o.setFoneUnidade(ocurso.getUsuario().getUnidadenegocio().getFone());
		o.setPaisEscola(
				ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getNome().toUpperCase());
		if ((ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getTexto()!=null) 
				&&(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getTexto().length()>0)){
			o.setDescricaoPais(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getTexto());
		}else { 
			EscolaPadraoFacade escolaPadraoFacade = new EscolaPadraoFacade();
			escolaPadrao = escolaPadraoFacade.pesquisar(); 
			o.setDescricaoPais(escolaPadrao.getDescricaopais());
		}
		o.setNomeEscola(
				ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome().toUpperCase());
		o.setCidadeEscola(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getNome().toUpperCase());
		if ((ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getTexto()!=null) && (ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getTexto().length()>0)){
			o.setDescricaoEscolaCidade(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getTexto());
		}else {
			EscolaPadraoFacade escolaPadraoFacade = new EscolaPadraoFacade();
			escolaPadrao = escolaPadraoFacade.pesquisar();  
			o.setDescricaoEscolaCidade(escolaPadrao.getDescricaoescola());
		}
		o.setSiteescola(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getSite());

		// Dados Pagina 03
		o.setLocalCurso(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getNome() + ", "
				+ ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getNome());
		o.setInstituicao(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome() + " "
				+ ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getNome());
		if (produtoCurso != null && produtoCurso.getValorcoprodutos().getCoprodutos().isPacote()) {
			o.setTipCurso("Pacote " + ocurso.getProdutosorcamento().getDescricao());
			if (produtoCurso.getValorcoprodutos().getCoprodutos().getComplementocurso() != null) {
				o.setTipCurso(o.getTipCurso() + " - " + produtoCurso.getValorcoprodutos().getCoprodutos().getComplementocurso().getDescricao());
			}
		} else {
			o.setTipCurso(ocurso.getProdutosorcamento().getDescricao());
			if (produtoCurso != null && produtoCurso.getValorcoprodutos().getCoprodutos().getComplementocurso() != null) {
				o.setTipCurso(o.getTipCurso() + " - " + produtoCurso.getValorcoprodutos().getCoprodutos().getComplementocurso().getDescricao());
			}
		}
		o.setDataInicio(Formatacao.ConvercaoDataPadrao(ocurso.getDatainicio()));
		o.setDataTermino(Formatacao.ConvercaoDataPadrao(ocurso.getDatatermino()));
		if (ocurso.getNumerodiasferiado() > 0) {
			o.setDataTermino(o.getDataTermino() + "(" + ocurso.getNumerodiasferiado() + " dias de intervalo.)");
		}
		o.setDuracao(verificarNumeroSemanas(ocurso));
		o.setCargahoraria(ocurso.getCargahoraria());
		o.setTurno(ocurso.getTurno());
		o.setTotalCursome(
				ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalCursoMe));
		o.setTotalcursors(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalCursoRs));
		o.setDataOrcamento(Formatacao.ConvercaoDataPadrao(ocurso.getDataorcamento()));
		o.setCambio(ocurso.getCambio().getMoedas().getSigla() + " "
				+ Formatacao.formatarFloatString(ocurso.getValorcambio()));

		// Dados Pagina 04
		o.setComposicao(composicao);   
		o.setValorVista(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(ocurso.getValoravista()));
		if (ocurso.getOcursoformapagamentoList() != null && ocurso.getOcursoformapagamentoList().size() > 0) {
			o.setValorentradaboelto(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
					+ Formatacao.formatarFloatString(ocurso.getOcursoformapagamentoList().get(0).getValorEntrada()));
			o.setSaldoboleto(
					"Saldo em " + ocurso.getOcursoformapagamentoList().get(0).getNumeroparcela() + " parcelas fixas");
			o.setValorsaldoboleto(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
					+ Formatacao.formatarFloatString(ocurso.getOcursoformapagamentoList().get(0).getValorparcela()));
		}
		if (ocurso.getOcursoformapagamentoList() != null && ocurso.getOcursoformapagamentoList().size() > 1) {
			o.setValorentradacartao(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
					+ Formatacao.formatarFloatString(ocurso.getOcursoformapagamentoList().get(1).getValorEntrada()));
			o.setSaldocartao(
					"Saldo em " + ocurso.getOcursoformapagamentoList().get(1).getNumeroparcela() + " parcelas fixas");
			o.setValorsaldocartao(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
					+ Formatacao.formatarFloatString(ocurso.getOcursoformapagamentoList().get(1).getValorparcela()));
		}
		if (ocurso.getOcursoformapagamentoList() != null && ocurso.getOcursoformapagamentoList().size() > 2) {
			o.setValorentradafinanciamento(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
					+ Formatacao.formatarFloatString(ocurso.getOcursoformapagamentoList().get(2).getValorEntrada()));
			o.setDescricaofinanciamento(
					"Saldo em " + ocurso.getOcursoformapagamentoList().get(2).getNumeroparcela() + " parcelas fixas");
			o.setValorfinanciamento(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
					+ Formatacao.formatarFloatString(ocurso.getOcursoformapagamentoList().get(2).getValorparcela()));
		}
		o.setObservacao(ocurso.getObservacao());
		o.setNumeroorcamento(String.valueOf(ocurso.getIdocurso()));
		return o;
	}

	public void carregarCustosExtras() {
		OrcamentoPDFBean o = carregarDados();
		if (ocurso.getValorpassagem() != null && ocurso.getValorpassagem().length() > 1) {
			ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
			Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
					.consultar(aplicacaoMB.getParametrosprodutos().getCoseguroprivado());
			if (listaCustosExtras == null) {
				listaCustosExtras = new ArrayList<Ocrusoprodutos>();
			}
			Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
			ocrusoprodutos.setDescricao("*Passagem: " + ocurso.getValorpassagem());
			ocrusoprodutos.setNome("*Passagem: " + ocurso.getValorpassagem());
			ocrusoprodutos.setNomegrupo("CustosExtras");
			ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
			ocrusoprodutos.setOcurso(ocurso);
			ocrusoprodutos.setTipo(5);
			ocrusoprodutos.setValorcoprodutos(valorcoprodutos);
			listaCustosExtras.add(ocrusoprodutos);
		}
		if (ocurso.getValorvisto() != null && ocurso.getValorvisto().length() > 1) {
			ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
			Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
					.consultar(aplicacaoMB.getParametrosprodutos().getCoseguroprivado());
			if (listaCustosExtras == null) {
				listaCustosExtras = new ArrayList<Ocrusoprodutos>();
			}
			Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
			ocrusoprodutos.setDescricao("*Visto: " + ocurso.getValorvisto());
			ocrusoprodutos.setNome("*Visto: " + ocurso.getValorvisto());
			ocrusoprodutos.setNomegrupo("CustosExtras");
			ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
			ocrusoprodutos.setOcurso(ocurso);
			ocrusoprodutos.setTipo(5);
			ocrusoprodutos.setValorcoprodutos(valorcoprodutos);
			listaCustosExtras.add(ocrusoprodutos);
		}
		if (ocurso.getValoroutros() != null && ocurso.getValoroutros().length() > 1) {
			ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
			Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
					.consultar(aplicacaoMB.getParametrosprodutos().getCoseguroprivado());
			if (listaCustosExtras == null) {
				listaCustosExtras = new ArrayList<Ocrusoprodutos>();
			}
			Ocrusoprodutos ocrusoprodutos = new Ocrusoprodutos();
			ocrusoprodutos.setDescricao(ocurso.getValoroutros());
			ocrusoprodutos.setNome(ocurso.getValoroutros());
			ocrusoprodutos.setNomegrupo("CustosExtras");
			ocrusoprodutos.setNumerosemanas(ocurso.getNumerosemanas().doubleValue());
			ocrusoprodutos.setOcurso(ocurso);
			ocrusoprodutos.setTipo(5);
			ocrusoprodutos.setValorcoprodutos(valorcoprodutos);
			listaCustosExtras.add(ocrusoprodutos);
		}
		if (ocurso.getOcursoseguroList() != null) {
			if (ocurso.getOcursoseguroList().size() > 0 && !ocurso.getOcursoseguroList().get(0).isSomarvalortotal()) {
				o.setDescricaolista("Seguro Viagem - "+ocurso.getOcursoseguroList().get(0).getValoresseguro().getSeguroplanos().getNome());
				o.setSubDescricaoLista(String.valueOf(ocurso.getOcursoseguroList().get(0).getNumerodias()) + " dias");
				o.setValorme(ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(ocurso.getOcursoseguroList().get(0).getValor() / ocurso.getValorcambio()));
				o.setValorrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(ocurso.getOcursoseguroList().get(0).getValor()));
				o.setIdgrupo(5);
				lista.add(o);
				totalCeMe = totalCeMe + (ocurso.getOcursoseguroList().get(0).getValor() / ocurso.getValorcambio());
				totalCeRs = totalCeRs + ocurso.getOcursoseguroList().get(0).getValor(); 
			} 
		}

		if (listaCustosExtras != null) {
			for (int i = 0; i < listaCustosExtras.size(); i++) {
				o = carregarDados();
				o.setDescricaolista(listaCustosExtras.get(i).getDescricao());
				o.setSubDescricaoLista("");
				if (listaCustosExtras.get(i).getValororiginal() != null) {
					o.setValorme(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(listaCustosExtras.get(i).getValororiginal()));
					o.setValorrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
							.formatarFloatString(listaCustosExtras.get(i).getValororiginal() * ocurso.getValorcambio()));
				}
				o.setIdgrupo(5);
				lista.add(o);
				if (listaCustosExtras.get(i).getValororiginal() != null) {
					totalCeMe = totalCeMe + listaCustosExtras.get(i).getValororiginal();
					totalCeRs = totalCeRs + (listaCustosExtras.get(i).getValororiginal() * ocurso.getValorcambio());
				}
			}
		}
		boolean transferin = false;
		if (produtoCurso.getValorcoprodutos().getCoprodutos().isTransfer()) {
			o = carregarDados();
			String titulo = "Transfer Partida ";
			if (produtoCurso.getValorcoprodutos().getCoprodutos().isTransferout()) {
				titulo = titulo + " | Transfer Chegada";
			}
			o.setTituloLista(titulo);
			o.setDescricaolista("INCLUSO NO PACOTE");
			o.setSubDescricaoLista("");
			o.setValorme("");
			o.setValorrs("");
			o.setIdgrupo(1);
			lista.add(o);
			o.setTotalmelista("");
			o.setTotalrslista("");
			transferin = true;
		}
		
		if (!transferin) {
			if (produtoCurso.getValorcoprodutos().getCoprodutos().isTransferout()) {
				o = carregarDados();
				o.setTituloLista("Transfer Chegada");
				o.setDescricaolista("INCLUSO NO PACOTE");
				o.setSubDescricaoLista("");
				o.setValorme("");
				o.setValorrs("");
				o.setIdgrupo(1);
				lista.add(o);
				o.setTotalmelista("");
				o.setTotalrslista("");
			}
		}
	}

	public void carregarTaxas() {
		if (listaTaxas != null) {
			composicao = composicao + " + TAXAS";
			OrcamentoPDFBean o = carregarDados();
			for (int i = 0; i < listaTaxas.size(); i++) {
				o = carregarDados();
				o.setTituloLista("TAXAS");
				o.setDescricaolista(listaTaxas.get(i).getDescricao());
				o.setSubDescricaoLista("");
				o.setValorme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(listaTaxas.get(i).getValororiginal()));
				o.setValorrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(listaTaxas.get(i).getValororiginal() * ocurso.getValorcambio()));
				o.setIdgrupo(1);
				lista.add(o);
				totalTxMe = totalTxMe + listaTaxas.get(i).getValororiginal();
				totalTxRs = totalTxRs + (listaTaxas.get(i).getValororiginal() * ocurso.getValorcambio());
			}
			o.setTotalmelista(
					ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalTxMe));
			o.setTotalrslista(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalTxRs));
		} 
	}

	public void carregarAcomodacao() {
		if (listaAcomodacao != null && listaAcomodacao.size() > 0) {
			composicao = composicao + " + ACOMODAÇÃO";
			OrcamentoPDFBean o = carregarDados();
			for (int i = 0; i < listaAcomodacao.size(); i++) {
				o = carregarDados();
				int numeroSemana = listaAcomodacao.get(i).getNumerosemanas().intValue(); 
				o.setDescricaolista(listaAcomodacao.get(i).getValorcoprodutos().getCoprodutos()
						.getComplementoacomodacao().getTipoacomodacao()
						+ ", "
						+ listaAcomodacao.get(i).getValorcoprodutos().getCoprodutos().getComplementoacomodacao()
								.getTipoquarto()
						+ ", "
						+ listaAcomodacao.get(i).getValorcoprodutos().getCoprodutos().getComplementoacomodacao()
								.getTiporefeicao()
						+ ", WC " + listaAcomodacao.get(i).getValorcoprodutos().getCoprodutos()
								.getComplementoacomodacao().getTipobanheiro() + " - " + String.valueOf(numeroSemana) + " semana(s)");
				o.setValorme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(listaAcomodacao.get(i).getValororiginal()));
				o.setValorrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(listaAcomodacao.get(i).getValororiginal() * ocurso.getValorcambio()));
				o.setIdgrupo(2);
				o.setTituloLista("ACOMODAÇÃO - " + listaAcomodacao.get(0).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getNome());
				lista.add(o);
				totalAcMe = totalAcMe + listaAcomodacao.get(i).getValororiginal();
				totalAcRs = totalAcRs + (listaAcomodacao.get(i).getValororiginal() * ocurso.getValorcambio());
			}
			o.setTotalmelista(
					ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAcMe));
			o.setTotalrslista(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAcRs));
		} else if (produtoCurso.getValorcoprodutos().getCoprodutos().isAcomodacao()) {
			composicao = composicao + " + ACOMODAÇÃO";
			OrcamentoPDFBean o = carregarDados();
			int numero = produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getNumerosemanas();
			if(produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getNumerosemanas()==0) {
				numero = produtoCurso.getOcurso().getNumerosemanas();
			}
			o.setDescricaolista(produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao()
					.getTipoacomodacao() + ", "
					+ produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTipoquarto()
					+ ", "
					+ produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTiporefeicao()
					+ ", WC "
					+ produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTipobanheiro()
					+", "+produtoCurso.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getComplemento()
					+", "+numero+" Semana(s)");
			o.setIdgrupo(2);  
			o.setTituloLista("ACOMODAÇÃO");
			totalAcMe = 0.00f;
			totalAcRs = 0.00f;
			lista.add(o);
			o.setTotalmelista("");
			o.setTotalrslista("");
			o = carregarDados();
			o.setDescricaolista("INCLUSO NO PACOTE");
			o.setSubDescricaoLista("");
			o.setValorme("");
			o.setValorrs("");
			o.setIdgrupo(2);
			o.setTituloLista("ACOMODAÇÃO");
			lista.add(o);
		}

	}

	public void carregarDesconto() {
		if (listaDesconto != null) {
			composicao = composicao + " - DESCONTO";
			OrcamentoPDFBean o = carregarDados();
			for (int i = 0; i < listaDesconto.size(); i++) {
				o = carregarDados();
				o.setDescricaolista(listaDesconto.get(i).getDescricao());
				o.setSubDescricaoLista("");
				o.setValorme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(listaDesconto.get(i).getValororiginal()));
				o.setValorrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(listaDesconto.get(i).getValororiginal() * ocurso.getValorcambio()));
				o.setIdgrupo(3);
				o.setTituloLista("DESCONTO");
				lista.add(o);
				totalDeMe = totalDeMe + listaDesconto.get(i).getValororiginal();
				totalDeRs = totalDeRs + (listaDesconto.get(i).getValororiginal() * ocurso.getValorcambio());
			}
			o.setTotalmelista(
					ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalDeMe));
			o.setTotalrslista(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalDeRs));
		}
	}
	
	
	public void carregarAdicionais() {
		totalAdMe=0.0f;
		totalAdRs=0.0f;
		if (listaAdicionais != null) {
			composicao = composicao + " + ADICIONAIS";
			OrcamentoPDFBean o = carregarDados();
			for (int i = 0; i < listaAdicionais.size(); i++) {
				o = carregarDados();
				o.setDescricaolista(listaAdicionais.get(i).getDescricao());
				o.setSubDescricaoLista("");
				o.setValorme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(listaAdicionais.get(i).getValororiginal()));
				o.setValorrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao
						.formatarFloatString(listaAdicionais.get(i).getValororiginal() * ocurso.getValorcambio()));
				o.setIdgrupo(4);
				o.setTituloLista("ITENS ADICIONAIS");
				lista.add(o);
				totalAdMe = totalAdMe + listaAdicionais.get(i).getValororiginal();
				totalAdRs = totalAdRs + (listaAdicionais.get(i).getValororiginal() * ocurso.getValorcambio());
			}
			if (ocurso.getOcursoseguroList() != null) {
				if (ocurso.getOcursoseguroList().size() > 0 && ocurso.getOcursoseguroList().get(0).isSomarvalortotal()) {
					o = carregarDados();
					o.setDescricaolista("Seguro Viagem: "+ocurso.getOcursoseguroList().get(0).getValoresseguro().getSeguroplanos().getNome()
							+" - "+String.valueOf(ocurso.getOcursoseguroList().get(0).getNumerodias()) + " dias");
					o.setSubDescricaoLista("");
					o.setValorme(ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao
							.formatarFloatString(ocurso.getOcursoseguroList().get(0).getValor() / ocurso.getValorcambio()));
					o.setValorrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(ocurso.getOcursoseguroList().get(0).getValor()));
					o.setIdgrupo(4);
					o.setTituloLista("ITENS ADICIONAIS");
					lista.add(o);
					totalAdMe = totalAdMe + (ocurso.getOcursoseguroList().get(0).getValor() / ocurso.getValorcambio());
					totalAdRs = totalAdRs + ocurso.getOcursoseguroList().get(0).getValor(); 
				} 
			}
			o.setTotalmelista(
					ocurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAdMe));
			o.setTotalrslista(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAdRs));
		}
	}


	public void calcularTotais() {
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				lista.get(i).setComposicao(composicao);
				if (lista.get(i).getIdgrupo() == 1) {
					lista.get(i).setTotaltxme(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalTxMe));
					lista.get(i).setTotaltxrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalTxRs));
				} else if (lista.get(i).getIdgrupo() == 2) {
					lista.get(i).setTotalacme(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAcMe));
					lista.get(i).setTotalacrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAcRs));
				} else if (lista.get(i).getIdgrupo() == 3) {
					lista.get(i).setTotalDeme(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalDeMe));
					lista.get(i).setTotalDers(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalDeRs));
				} else if (lista.get(i).getIdgrupo() == 6) {
					lista.get(i).setTotaladicionalme(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAdMe));
					lista.get(i).setTotaladicionalrs(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAdRs));
				}
				lista.get(i).setSubPacoteme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(totalAcMe + totalTxMe + totalCursoMe + totalAdMe - totalDeMe));
				lista.get(i).setSubPacoters(
						ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAcRs + totalTxRs + totalCursoRs + totalAdRs - totalDeRs));
				lista.get(i).setTotalPacoteme(ocurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(totalAcMe + totalCeMe + totalTxMe + totalCursoMe + totalAdMe - totalDeMe));
				lista.get(i).setTotalPacoters(ocurso.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(totalAcRs + totalCeRs + totalTxRs + totalCursoRs + totalAdRs - totalDeRs));
			}
		}
	}

	
	public String verificarNumeroSemanas(Ocurso ocurso){
		String numeroSemanas;
		if(ocurso.getNumerosemanasbrinde()>0){
			numeroSemanas = ocurso.getNumerosemanas()+" semanas + "+ocurso.getNumerosemanasbrinde()+" semanas FREE";
		}else{
			numeroSemanas = ocurso.getNumerosemanas()+" semanas";
		}
		 return numeroSemanas;
	}
}
