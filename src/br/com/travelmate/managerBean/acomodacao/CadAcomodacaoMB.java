package br.com.travelmate.managerBean.acomodacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.DataVencimentoBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.AcomodacaoFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorFeriasFacade;
import br.com.travelmate.facade.GrupoObrigatorioFacade;
import br.com.travelmate.facade.OrcamentoFacade;

import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.facade.PromocaoAcomodacaoCidadeFacade;
import br.com.travelmate.facade.PromocaoBrindeCursoCidadeFacade;
import br.com.travelmate.facade.PromocaoCursoFornecedorCidadeFacade;
import br.com.travelmate.facade.PromocaoTaxaCidadeFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutoFornecedorBean;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosOrcamentoBean;
import br.com.travelmate.model.Acomodacao;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Fornecedorferias;
import br.com.travelmate.model.Grupoobrigatorio;
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Promocaoacomodacao;
import br.com.travelmate.model.Promocaoacomodacaocidade;
import br.com.travelmate.model.Promocaobrindecurso;
import br.com.travelmate.model.Promocaobrindecursocidade;
import br.com.travelmate.model.Promocaocurso;
import br.com.travelmate.model.Promocaocursocidade;
import br.com.travelmate.model.Promocaotaxacidade;
import br.com.travelmate.model.Promocaotaxacurso;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadAcomodacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PaisDao paisDao;
	@Inject
	private CambioDao cambioDao;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Pais pais;
	private Idioma idioma;
	private List<Paisproduto> listaPais;
	private Cidade cidade;
	private List<Cidade> listaCidade;
	private Orcamento orcamento;
	private Moedas moeda;
	private float valorCambio;
	private Cambio cambio;
	private Cliente cliente;
	private Lead lead;
	private Formapagamento formaPagamento;
	private List<Produtos> listaProgramas;
	private Produtos programas;
	private Acomodacao acomodacao;
	private List<Acomodacao> listaAcomodacao;
	private List<ProdutosOrcamentoBean> listaAcomodacoesIndependente;
	private boolean desabilitarAlergiaAlimento = true;
	private String camposAcomodacao = "true";
	private String camposAcomodacaoCasaFamilia = "true";
	private boolean btnPesquisar = true;
	private boolean btnPais = true;
	private boolean btnCidade = true;
	private boolean btnDataInicio = true;
	private Vendas vendas;
	private List<Filtroorcamentoproduto> listaProdutosOrcamento;
	private Produtosorcamento produtosorcamento;
	private List<Moedas> listaMoedas;
	private float valorTotal = 0.0f;
	private float totalPagar = 0.0f;
	private float valorParcelar = 0.0f;
	private float valorParcela = 0.0f;
	private float valorSaltoParcelar = 0.0f;
	private float valorMoedaEstrangeira = 0.0f;
	private float valorMoedaReal = 0.0f;
	private float totalMoedaEstrangeira = 0.0f;
	private float totalMoedaReal = 0.0f;
	private String formaPagamentoString;
	private Date dataPrimeiroPagamento;
	private String tipoParcelamento;
	private String numeroParcelas = "01";
	private List<String> listaTipoParcelamento;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga;
	private float valorTotalRS;
	private float valorTotalAdicionais;
	private float valorTotalAdicionaisRS;
	private Cancelamento cancelamento;
	private boolean novoLancamento = true;
	private int fornecedor1;
	private int fornecedor2;
	private int fornecedor3;
	private List<ProdutosOrcamentoBean> listaAcomodacoesIndependente1;
	private List<ProdutosOrcamentoBean> listaAcomodacoesIndependente2;
	private List<ProdutosOrcamentoBean> listaAcomodacoesIndependente3;
	private String nomeFornecedor1 = "";
	private String nomeFornecedor2 = "";
	private String nomeFornecedor3 = "";

	@PostConstruct
	public void init() {
		listaProgramas = GerarListas.listarProdutos("");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		acomodacao = (Acomodacao) session.getAttribute("acomodacao");
		carregarComboMoedas();
		gerarListaProdutosOrcamento();
		session.removeAttribute("cliente");
		session.removeAttribute("lead");
		session.removeAttribute("acomodacao");
		if (acomodacao == null) {
			acomodacao = new Acomodacao();
			vendas = new Vendas();
			formaPagamento = new Formapagamento();
			orcamento = new Orcamento();
			orcamento.setOrcamentoprodutosorcamentoList(new ArrayList<Orcamentoprodutosorcamento>());
			novoLancamento = true;
		}else {
			vendas = acomodacao.getVendas();
			cambio = vendas.getCambio();
			valorCambio = vendas.getValorcambio();
			programas = vendas.getProdutos();
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			listaPais = paisProdutoFacade.listar(programas.getIdprodutos());
			btnCidade = false;
			btnDataInicio = false;
			btnPais = false;
			btnPesquisar = true;
			if (listaAcomodacao == null) {
				listaAcomodacao = new ArrayList<Acomodacao>();
			}
			listaAcomodacao.add(acomodacao);
			formaPagamento = vendas.getFormapagamento();
			orcamento = vendas.getOrcamento();
			pais = vendas.getFornecedorcidade().getCidade().getPais();
			cidade = vendas.getFornecedorcidade().getCidade();
			acomodacao.setCambio(vendas.getCambio());   
			calcularValorTotalOrcamento();
			novoLancamento = false;
		}
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public Moedas getMoeda() {
		return moeda;
	}

	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
	}

	public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public List<Produtos> getListaProgramas() {
		return listaProgramas;
	}

	public void setListaProgramas(List<Produtos> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	public Acomodacao getAcomodacao() {
		return acomodacao;
	}

	public void setAcomodacao(Acomodacao acomodacao) {
		this.acomodacao = acomodacao;
	}

	public List<Acomodacao> getListaAcomodacao() {
		return listaAcomodacao;
	}

	public void setListaAcomodacao(List<Acomodacao> listaAcomodacao) {
		this.listaAcomodacao = listaAcomodacao;
	}

	public List<ProdutosOrcamentoBean> getListaAcomodacoesIndependente() {
		return listaAcomodacoesIndependente;
	}

	public void setListaAcomodacoesIndependente(List<ProdutosOrcamentoBean> listaAcomodacoesIndependente) {
		this.listaAcomodacoesIndependente = listaAcomodacoesIndependente;
	}

	public boolean isDesabilitarAlergiaAlimento() {
		return desabilitarAlergiaAlimento;
	}

	public void setDesabilitarAlergiaAlimento(boolean desabilitarAlergiaAlimento) {
		this.desabilitarAlergiaAlimento = desabilitarAlergiaAlimento;
	}

	public String getCamposAcomodacao() {
		return camposAcomodacao;
	}

	public void setCamposAcomodacao(String camposAcomodacao) {
		this.camposAcomodacao = camposAcomodacao;
	}

	public String getCamposAcomodacaoCasaFamilia() {
		return camposAcomodacaoCasaFamilia;
	}

	public void setCamposAcomodacaoCasaFamilia(String camposAcomodacaoCasaFamilia) {
		this.camposAcomodacaoCasaFamilia = camposAcomodacaoCasaFamilia;
	}

	public Produtos getProgramas() {
		return programas;
	}

	public void setProgramas(Produtos programas) {
		this.programas = programas;
	}

	public boolean isBtnPesquisar() {
		return btnPesquisar;
	}

	public void setBtnPesquisar(boolean btnPesquisar) {
		this.btnPesquisar = btnPesquisar;
	}

	public boolean isBtnPais() {
		return btnPais;
	}

	public void setBtnPais(boolean btnPais) {
		this.btnPais = btnPais;
	}

	public boolean isBtnCidade() {
		return btnCidade;
	}

	public void setBtnCidade(boolean btnCidade) {
		this.btnCidade = btnCidade;
	}

	public boolean isBtnDataInicio() {
		return btnDataInicio;
	}

	public void setBtnDataInicio(boolean btnDataInicio) {
		this.btnDataInicio = btnDataInicio;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public List<Filtroorcamentoproduto> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public float getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(float totalPagar) {
		this.totalPagar = totalPagar;
	}

	public float getValorParcelar() {
		return valorParcelar;
	}

	public void setValorParcelar(float valorParcelar) {
		this.valorParcelar = valorParcelar;
	}

	public float getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(float valorParcela) {
		this.valorParcela = valorParcela;
	}

	public float getValorSaltoParcelar() {
		return valorSaltoParcelar;
	}

	public void setValorSaltoParcelar(float valorSaltoParcelar) {
		this.valorSaltoParcelar = valorSaltoParcelar;
	}

	public float getValorMoedaEstrangeira() {
		return valorMoedaEstrangeira;
	}

	public void setValorMoedaEstrangeira(float valorMoedaEstrangeira) {
		this.valorMoedaEstrangeira = valorMoedaEstrangeira;
	}

	public float getValorMoedaReal() {
		return valorMoedaReal;
	}

	public void setValorMoedaReal(float valorMoedaReal) {
		this.valorMoedaReal = valorMoedaReal;
	}

	public float getTotalMoedaEstrangeira() {
		return totalMoedaEstrangeira;
	}

	public void setTotalMoedaEstrangeira(float totalMoedaEstrangeira) {
		this.totalMoedaEstrangeira = totalMoedaEstrangeira;
	}

	public float getTotalMoedaReal() {
		return totalMoedaReal;
	}

	public void setTotalMoedaReal(float totalMoedaReal) {
		this.totalMoedaReal = totalMoedaReal;
	}

	public String getFormaPagamentoString() {
		return formaPagamentoString;
	}

	public void setFormaPagamentoString(String formaPagamentoString) {
		this.formaPagamentoString = formaPagamentoString;
	}

	public Date getDataPrimeiroPagamento() {
		return dataPrimeiroPagamento;
	}

	public void setDataPrimeiroPagamento(Date dataPrimeiroPagamento) {
		this.dataPrimeiroPagamento = dataPrimeiroPagamento;
	}

	public String getTipoParcelamento() {
		return tipoParcelamento;
	}

	public void setTipoParcelamento(String tipoParcelamento) {
		this.tipoParcelamento = tipoParcelamento;
	}

	public String getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(String numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public List<String> getListaTipoParcelamento() {
		return listaTipoParcelamento;
	}

	public void setListaTipoParcelamento(List<String> listaTipoParcelamento) {
		this.listaTipoParcelamento = listaTipoParcelamento;
	}

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoOriginal() {
		return listaParcelamentoPagamentoOriginal;
	}

	public void setListaParcelamentoPagamentoOriginal(List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal) {
		this.listaParcelamentoPagamentoOriginal = listaParcelamentoPagamentoOriginal;
	}

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoAntiga() {
		return listaParcelamentoPagamentoAntiga;
	}

	public void setListaParcelamentoPagamentoAntiga(List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga) {
		this.listaParcelamentoPagamentoAntiga = listaParcelamentoPagamentoAntiga;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public float getValorTotalRS() {
		return valorTotalRS;
	}

	public void setValorTotalRS(float valorTotalRS) {
		this.valorTotalRS = valorTotalRS;
	}

	public float getValorTotalAdicionais() {
		return valorTotalAdicionais;
	}

	public void setValorTotalAdicionais(float valorTotalAdicionais) {
		this.valorTotalAdicionais = valorTotalAdicionais;
	}

	public float getValorTotalAdicionaisRS() {
		return valorTotalAdicionaisRS;
	}

	public void setValorTotalAdicionaisRS(float valorTotalAdicionaisRS) {
		this.valorTotalAdicionaisRS = valorTotalAdicionaisRS;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	public void selecionarCambio() {
		if (pais != null && pais.getIdpais() != null) {
			moeda = pais.getMoedas();
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			valorCambio = cambio.getValor();
			vendas.setCambio(cambio);
			acomodacao.setCambio(cambio);
			btnCidade = false;
			if (pais.getCidadeList() != null) {
				List<Cidade> listaCidade = new ArrayList<Cidade>();
				for (int i = 0; i < pais.getCidadeList().size(); i++) {
					if (pais.getCidadeList().get(i).isAcomodacaoindepentende()) {
						listaCidade.add(pais.getCidadeList().get(i));
					}
				}
				pais.setCidadeList(listaCidade);
			}
		} else {
			btnCidade = true;
			cidade = null;
			btnDataInicio = true;
			btnPesquisar = true;
		}
	}

	public void selecionarAcomodacao() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cidade", cidade);
		session.setAttribute("acomodacaoInd", acomodacao);
		gerarListaAcomodacaoIndependente();

		session.setAttribute("listaAcomodacoesIndependente1", listaAcomodacoesIndependente1);
		session.setAttribute("listaAcomodacoesIndependente2", listaAcomodacoesIndependente2);
		session.setAttribute("listaAcomodacoesIndependente3", listaAcomodacoesIndependente3);
		session.setAttribute("fornecedor1", fornecedor1);
		session.setAttribute("fornecedor2", fornecedor2);
		session.setAttribute("fornecedor3", fornecedor3);
		session.setAttribute("nomeFornecedor1", nomeFornecedor1);
		session.setAttribute("nomeFornecedor2", nomeFornecedor2);
		session.setAttribute("nomeFornecedor3", nomeFornecedor3);
		RequestContext.getCurrentInstance().openDialog("selecionarAcomodacao");
	}

	public void retornoDialogoAcomodacao(SelectEvent event) {
		ProdutosOrcamentoBean po = (ProdutosOrcamentoBean) event.getObject();
		if (po != null) {
			listaAcomodacao = new ArrayList<>();
			vendas.setFornecedor(po.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor());
			vendas.setFornecedorcidade(po.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade());
			acomodacao = popularAcomodacao(po);
			listaAcomodacao.add(acomodacao);
			btnPesquisar = true;
			calcularDataTerminoAcomodacao(po);
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			orcamentoprodutosorcamento.setDescricao("Acomodação");
			orcamentoprodutosorcamento.setImportado(false);
			orcamentoprodutosorcamento.setOrcamento(orcamento);
			orcamentoprodutosorcamento.setObrigatorio(true); 
			orcamentoprodutosorcamento.setProdutosorcamento(po.getValorcoprodutos().getCoprodutos().getProdutosorcamento());
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(po.getValorOrigianl());
			orcamentoprodutosorcamento.setValorMoedaNacional(po.getValorOriginalRS());
			orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			mudarNumeroSemanaAcomodacao(po);
			calcularValorTotalOrcamento();
		}
	}  

	public Acomodacao popularAcomodacao(ProdutosOrcamentoBean po) {
		acomodacao.setTipoacomodacao(
				po.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTipoacomodacao());
		acomodacao
				.setNumerosemana(po.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getNumerosemanas());
		acomodacao.setTipoquarto(po.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTipoquarto());
		acomodacao
				.setTipobanheiro(po.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTipobanheiro());
		acomodacao
				.setTiporefeicao(po.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getTiporefeicao());
		acomodacao.setComplemento(po.getValorcoprodutos().getCoprodutos().getComplementoacomodacao().getComplemento());
		acomodacao.setValormoedaestrangeira(po.getValorOrigianl());
		acomodacao.setValormoedanacional(po.getValorOriginalRS());
		return acomodacao;
	}

	public void mudarNumeroSemanaAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		ProdutoFornecedorBean produtoFornecedorBean = new ProdutoFornecedorBean();
		produtoFornecedorBean.setListaCursoPrincipal(new ArrayList<ProdutosOrcamentoBean>());
		produtoFornecedorBean.setListaObrigaroerios(new ArrayList<ProdutosOrcamentoBean>());
		if (produtosOrcamentoBean.isSelecionado()) {
			calcularValorAcomodacao(produtosOrcamentoBean);
			ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
			Valorcoprodutos valorcoprodutos = null;
			Date dataconsulta = null;
			if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
					.isAcomodacaoindependente()) {
				dataconsulta = retornarDataConsultaOrcamento(acomodacao.getDatainicial(),
						produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
								.getFornecedorcidade().getFornecedor());
			}
			String sql = "Select v from  Valorcoprodutos v where v.numerosemanainicial<="
					+ produtosOrcamentoBean.getNumeroSemanas() + " and v.numerosemanafinal>="
					+ produtosOrcamentoBean.getNumeroSemanas() + " and v.coprodutos.idcoprodutos="
					+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos();
			if (produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DI")) {
				sql = sql + " AND v.datainicial<='" + Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='"
						+ Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			} else if (produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DS")) {
				sql = sql + " AND v.datainicial<='" + Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='"
						+ Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			}
			if (produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DM")) {
				sql = sql + " AND v.datainicial<='" + Formatacao.ConvercaoDataSql(new Date()) + "' and v.datafinal>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "'";
			}
			List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
			if (listaValorcoprodutoses != null) {
				for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
					if (valorcoprodutos == null) {
						valorcoprodutos = new Valorcoprodutos();
						valorcoprodutos = listaValorcoprodutoses.get(n);
					} else {
						valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
					}
				}
			}
			if (valorcoprodutos != null) {
				produtosOrcamentoBean.setValorcoprodutos(valorcoprodutos);
				int multiplicador = 1;
				if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
					multiplicador = (int) produtosOrcamentoBean.getNumeroSemanas();
				} else if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
					multiplicador = (int) (produtosOrcamentoBean.getNumeroSemanas() * 7);
				}
				produtosOrcamentoBean.setValorOrigianl(
						multiplicador * produtosOrcamentoBean.getValorcoprodutos().getValororiginal());
				produtosOrcamentoBean.setValorOriginalRS(produtosOrcamentoBean.getValorOrigianl() * valorCambio);
				gerarPromocaoAcomodacao(produtosOrcamentoBean);
				String sqlObrigatorio = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos="
						+ produtosOrcamentoBean.getIdproduto();
				GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
				List<Grupoobrigatorio> listaGrupoObrigatorio;
				listaGrupoObrigatorio = grupoObrigatorioFacade.listar(sqlObrigatorio);
				if (listaGrupoObrigatorio != null && listaGrupoObrigatorio.size() > 0) {
					for (int i = 0; i < listaGrupoObrigatorio.size(); i++) {
						boolean calcular = true;
						if (listaGrupoObrigatorio.get(i).isMenorobrigatorio()) {
							int idadeCliente = Formatacao.calcularIdade(lead.getCliente().getDataNascimento());
							if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma() != null) {
								if (idadeCliente < produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
										.getIdfornecedorcidadeidioma()) {
									calcular = true;
								} else {
									calcular = false;
								}
							}else {
								calcular = false;
							}
								
						}
						if (calcular) {
							ProdutosOrcamentoBean po = consultarValores("DI",
									listaGrupoObrigatorio.get(i).getProduto().getIdcoprodutos(), dataconsulta,
									"Obrigatorio");
							if (po != null) {
								produtoFornecedorBean.getListaObrigaroerios().add(po);
								produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(
										produtoFornecedorBean.getListaObrigaroerios().size() - 1);
							} else {
								po = consultarValores("DM", listaGrupoObrigatorio.get(i).getProduto().getIdcoprodutos(),
										new Date(), "Obrigatorio");
								if (po != null) {
									produtoFornecedorBean.getListaObrigaroerios().add(po);
									produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(
											produtoFornecedorBean.getListaObrigaroerios().size() - 1);
								} else {
									po = consultarValores("DS",
											listaGrupoObrigatorio.get(i).getProduto().getIdcoprodutos(), dataconsulta,
											"Obrigatorio");
									if (po != null) {
										produtoFornecedorBean.getListaObrigaroerios().add(po);
										produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(
												produtoFornecedorBean.getListaObrigaroerios().size() - 1);
									} else {
										produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(-1);
									}
								}
							}
						} else {
							produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(-1);
						}
					}
				}
				ProdutosOrcamentoBean po = consultarValoresSuplementoAcomodacqo(produtosOrcamentoBean,
						produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos(), dataconsulta);
				if (po != null) {
					po.getValorcoprodutos().getCoprodutos()
							.setDescricao("Suplemento de " + po.getValorcoprodutos().getTiposuplemento());
					produtoFornecedorBean.getListaObrigaroerios().add(po);
					produtosOrcamentoBean
							.setLinhaSuplementoAcomodacao(produtoFornecedorBean.getListaObrigaroerios().size() - 1);
				}
				gerarPromocaoCurso(produtoFornecedorBean.getListaCursoPrincipal());
				gerarPromocaoTaxas(produtoFornecedorBean.getListaObrigaroerios(),
						produtoFornecedorBean.getListaCursoPrincipal());
//				gerarPromocaoBrindes(produtoFornecedorBean.getListaObrigaroerios(),
//						produtoFornecedorBean.getListaCursoPrincipal().get(0));
				if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
						.isAcomodacaoindependente()) {
					gerarPromocaoTaxasAcomodacaoIndependente(produtoFornecedorBean.getListaObrigaroerios(),
							produtoFornecedorBean.getListaCursoPrincipal(),
							produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma());
				}
			} else {
				produtosOrcamentoBean.setValorOrigianl(0.0f);
				produtosOrcamentoBean.setValorOriginalRS(0.0f);
				produtosOrcamentoBean.setNumeroSemanas(0);
				gerarPromocaoCurso(produtoFornecedorBean.getListaCursoPrincipal());
			}
		} else {
			produtosOrcamentoBean.setValorOrigianl(0.0f);
			produtosOrcamentoBean.setValorOriginalRS(0.0f);
			produtosOrcamentoBean.setNumeroSemanas(0);
			gerarPromocaoCurso(produtoFornecedorBean.getListaCursoPrincipal());
			if (produtosOrcamentoBean.getLinhaSuplementoAcomodacao() >= 0) {
				produtoFornecedorBean.getListaObrigaroerios()
						.remove(produtosOrcamentoBean.getLinhaSuplementoAcomodacao());
			}
			if (produtosOrcamentoBean.getLinhaObrigatorioAcomodacao() >= 0) {
				produtoFornecedorBean.getListaObrigaroerios()
						.remove(produtosOrcamentoBean.getLinhaObrigatorioAcomodacao());
			}
		}

		for (int i = 0; i < produtoFornecedorBean.getListaObrigaroerios().size(); i++) {
				Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
				if (produtoFornecedorBean.getListaObrigaroerios().get(i).getValorcoprodutos().getCoprodutos()
						.getComplementoacomodacao() != null && 
						!produtoFornecedorBean.getListaObrigaroerios().get(i).getValorcoprodutos().getProdutosuplemento()
							.equalsIgnoreCase("valor")) {
					orcamentoprodutosorcamento.setDescricao("Suplemento de Acomodação");
				}else if(produtoFornecedorBean.getListaObrigaroerios().get(i).getValorcoprodutos().getCoprodutos()
							.getComplementocurso() != null && 
							!produtoFornecedorBean.getListaObrigaroerios().get(i).getValorcoprodutos().getProdutosuplemento()
								.equalsIgnoreCase("valor")) {
					orcamentoprodutosorcamento.setDescricao("Suplemento de Curso");
				}else {
					orcamentoprodutosorcamento.setDescricao(produtoFornecedorBean.getListaObrigaroerios().get(i).getValorcoprodutos().getCoprodutos()
							.getProdutosorcamento().getDescricao());
				}
				orcamentoprodutosorcamento.setImportado(false);
				orcamentoprodutosorcamento.setOrcamento(orcamento);
				orcamentoprodutosorcamento.setObrigatorio(true); 
				orcamentoprodutosorcamento.setProdutosorcamento(produtoFornecedorBean.getListaObrigaroerios().get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento());
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(produtoFornecedorBean.getListaObrigaroerios().get(i).getValorOrigianl());
				orcamentoprodutosorcamento.setValorMoedaNacional(produtoFornecedorBean.getListaObrigaroerios().get(i).getValorOriginalRS());
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
		}
	}

	public boolean renderedValorPromocionalAparecendo(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isPromocao() && produtosOrcamentoBean.getValorPromocional() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean renderedValorPromocionalEscondido(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isPromocao() && produtosOrcamentoBean.getValorPromocional() != null) {
			return false;
		} else {
			return true;
		}
	}

	public void excluirAcomodacao(Acomodacao acomodacao) {
		listaAcomodacao.remove(acomodacao);
		btnPesquisar = false;
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			List<Orcamentoprodutosorcamento> listaProdutoApaga = new ArrayList<Orcamentoprodutosorcamento>();
			List<Orcamentoprodutosorcamento> listaProdutoFica = new ArrayList<Orcamentoprodutosorcamento>();
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				if (orcamento.getOrcamentoprodutosorcamentoList().get(i).isObrigatorio()) {
					listaProdutoApaga.add(orcamento.getOrcamentoprodutosorcamentoList().get(i));
				}else {
					listaProdutoFica.add(orcamento.getOrcamentoprodutosorcamentoList().get(i));
				}
			}
			for (int i = 0; i < listaProdutoApaga.size(); i++) {
				OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
				if (listaProdutoApaga.get(i).getIdorcamentoProdutosOrcamento() != null) {
					orcamentoFacade.excluirOrcamentoProdutoOrcamento(listaProdutoApaga.get(i).getIdorcamentoProdutosOrcamento());
				}  
			}
			orcamento.setOrcamentoprodutosorcamentoList(listaProdutoFica);
		}
		calcularValorTotalOrcamento();
		Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
	}

	public void desabilitarAlergiaAlimento() {
		if (acomodacao.getPossuiAlergia().equalsIgnoreCase("Sim")) {
			desabilitarAlergiaAlimento = false;
		} else {
			desabilitarAlergiaAlimento = true;
		}
	}

	public void verificarProduto() {
		if (programas != null) {
			btnPais = false;
			acomodacao.setProdutos(programas);
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			listaPais = paisProdutoFacade.listar(programas.getIdprodutos());
		} else {
			btnPais = true;
			listaPais = new ArrayList<Paisproduto>();
			pais = null;
			cidade = null;
			btnCidade = true;
			btnDataInicio = true;
			btnPesquisar = true;
		}
	}

	public void verificarCidade() {
		if (cidade != null) {
			btnDataInicio = false;
		} else {
			btnDataInicio = true;
			cidade = null;
			btnCidade = true;
			btnPesquisar = true;
		}
	}

	public void verificarDataInicio() {
		if (acomodacao.getDatainicial() != null && (listaAcomodacao == null || listaAcomodacao.size() <= 0)) {
			btnPesquisar = false;
		} else {
			btnPesquisar = true;
		}
	}

	public void calcularDataTerminoAcomodacao(ProdutosOrcamentoBean po) {
		Integer nSemanas = Formatacao.formatarDouble(po.getNumeroSemanas());
		if ((acomodacao.getDatainicial() != null) && (nSemanas != null)) {
			if (po.getNumeroSemanas() > 0) {
				int diaSemana = Formatacao.diaSemana(acomodacao.getDatainicial());
				if (diaSemana != 1) {
					Mensagem.lancarMensagemInfo("Atenção!", "O sistema não irá calcular automaticamente"
							+ " as datas de chegada e partida para acomodações que não iniciam no Domingo.");
					acomodacao.setDatainicial(null);
					acomodacao.setNumerosemana(null);
				} else {
					Date data = Formatacao.calcularDataFinalAcomodacao(acomodacao.getDatainicial(), nSemanas);
					acomodacao.setDatatermino(data);
					acomodacao.setNumerosemana(nSemanas);
				}
			}
		}
	}

	public String tituloMoedaEstrangeira() {
		if (cambio != null) {
			if (cambio.getMoedas() != null) {
				return "Valor " + cambio.getMoedas().getSigla();
			} else {
				return "Moeda Estrangeira ";
			}
		} else
			return "Moeda Estrangeira";
	}

	public void carregarComboMoedas() {
		
		listaMoedas = cambioDao.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	}

	public void adicionarProdutos() {
		if (valorCambio > 0) {
			boolean adicionar = true;
			if (produtosorcamento != null) {
				if (adicionar) {
					Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
					orcamentoprodutosorcamento.setImportado(false);
					orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
					orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
					if ((valorMoedaEstrangeira > 0) && (valorCambio > 0)) {
						valorMoedaReal = valorMoedaEstrangeira * valorCambio;
					} else {
						if ((valorMoedaReal > 0) && (valorCambio > 0)) {
							valorMoedaEstrangeira = valorMoedaReal / valorCambio;
						}
					}
					boolean excluirDescontoTM = true;
					if (produtosorcamento.getValormaximo() == 0) {
						orcamentoprodutosorcamento.setValorMoedaEstrangeira(valorMoedaEstrangeira);
						orcamentoprodutosorcamento.setValorMoedaNacional(valorMoedaReal);
						orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
						calcularValorTotalOrcamento();
					} else if (produtosorcamento.getValormaximo() >= valorMoedaReal) {
						orcamentoprodutosorcamento.setValorMoedaEstrangeira(valorMoedaEstrangeira);
						orcamentoprodutosorcamento.setValorMoedaNacional(valorMoedaReal);
						orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
						calcularValorTotalOrcamento();
					} else {
						FacesContext fc = FacesContext.getCurrentInstance();
						HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
						Map<String, Object> options = new HashMap<String, Object>();
						options.put("contentWidth", 230);
						session.setAttribute("valorOriginal", 0f);
						session.setAttribute("novoValor", 0f);
						RequestContext.getCurrentInstance().openDialog("validarTrocaCambioPIN", options, null);
						// Mensagem.lancarMensagemErro("", "Valor máximo permitido R$ "+
						// Formatacao.formatarFloatString(produtosorcamento.getValormaximo()));
						excluirDescontoTM = false;
					}
					if (excluirDescontoTM) {
						if (produtosorcamento.getIdprodutosOrcamento() == 33) {
							Filtroorcamentoproduto filtro = null;
							for (int i = 0; i < listaProdutosOrcamento.size(); i++) {
								if (listaProdutosOrcamento.get(i).getProdutos().getIdprodutos() == aplicacaoMB
										.getParametrosprodutos().getCursos()) {
									if (listaProdutosOrcamento.get(i).getProdutosorcamento()
											.getIdprodutosOrcamento() == 33) {
										filtro = listaProdutosOrcamento.get(i);
									}
								}
							}
							listaProdutosOrcamento.remove(filtro);
						}
					}
					produtosorcamento = null;
				}
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio não selecionado", ""));
		}
	}

	public void retornoDialogProdutoOrcamento() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String adicionar = (String) session.getAttribute("adicionar");
		session.removeAttribute("adicionar");
		if (adicionar != null) {
			if (adicionar.equalsIgnoreCase("sim")) {
				FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
				Filtroorcamentoproduto filtroorcamentoproduto = filtroOrcamentoProdutoFacade
						.pesquisar(aplicacaoMB.getParametrosprodutos().getCursos(), 33);
				Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
				orcamentoprodutosorcamento.setImportado(false);
				orcamentoprodutosorcamento.setDescricao(filtroorcamentoproduto.getProdutosorcamento().getDescricao());
				orcamentoprodutosorcamento.setProdutosorcamento(filtroorcamentoproduto.getProdutosorcamento());
				if ((valorMoedaEstrangeira > 0) && (valorCambio > 0)) {
					valorMoedaReal = valorMoedaEstrangeira * valorCambio;
				} else {
					if ((valorMoedaReal > 0) && (valorCambio > 0)) {
						valorMoedaEstrangeira = valorMoedaReal / valorCambio;
					}
				}
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(valorMoedaEstrangeira);
				orcamentoprodutosorcamento.setValorMoedaNacional(valorMoedaReal);
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
				calcularValorTotalOrcamento();
				if (filtroorcamentoproduto.getProdutosorcamento().getIdprodutosOrcamento() == 33) {
					Filtroorcamentoproduto filtro = null;
					for (int i = 0; i < listaProdutosOrcamento.size(); i++) {
						if (listaProdutosOrcamento.get(i).getProdutos().getIdprodutos() == aplicacaoMB
								.getParametrosprodutos().getCursos()) {
							if (listaProdutosOrcamento.get(i).getProdutosorcamento().getIdprodutosOrcamento() == 33) {
								filtro = listaProdutosOrcamento.get(i);
							}
						}
					}
					listaProdutosOrcamento.remove(filtro);
				}
				produtosorcamento = null;
			}
		}
	}

	public void calcularValorTotalOrcamento() {
		if (aplicacaoMB.getParametrosprodutos().isRemessaativa()) {
			calcularImpostoRemessa();
		}
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			valorTotal = 0.0f;
			totalMoedaEstrangeira = 0.0f;
			totalMoedaReal = 0.0f;
			valorMoedaEstrangeira = 0.0f;
			valorMoedaReal = 0.0f;
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				valorMoedaReal = 0.0f;
				valorMoedaEstrangeira = 0.0f;
				int idProdutoOrcamento = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento();
				if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getDescontoloja()) {
					valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1;
					valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.getValorMoedaEstrangeira() * -1;
				} else if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getDescontomatriz()) {
					valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1;
					valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.getValorMoedaEstrangeira() * -1;
				} else if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getPromocaoescola()) {
					valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1;
					valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.getValorMoedaEstrangeira() * -1;
				} else if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getPromocaoescolaacomodacao()) {
					valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1;
					valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.getValorMoedaEstrangeira() * -1;
				} else {
					valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional();
					valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.getValorMoedaEstrangeira();
				}
				valorTotal = valorTotal + valorMoedaReal;
				totalMoedaEstrangeira = totalMoedaEstrangeira + valorMoedaEstrangeira;
				totalMoedaReal = totalMoedaReal + valorMoedaReal;
				if (formaPagamento == null) {
					formaPagamento = new Formapagamento();
				}
				formaPagamento.setValorOrcamento(valorTotal);
			}
			totalPagar = valorTotal + formaPagamento.getValorJuros();
			valorSaltoParcelar = totalPagar;
			valorParcelar = valorSaltoParcelar;
			valorMoedaEstrangeira = 0.0f;
			valorMoedaReal = 0.0f;
			calcularParcelamentoPagamento();
		}
	}

	public void excluirProdutoOrcamento(String linha) {
		int ilinha = Integer.parseInt(linha);
		int tx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		int seguro = aplicacaoMB.getParametrosprodutos().getSeguroOrcamento();
		if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
				.getIdprodutosOrcamento() == tx) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Taxa TM não pode ser Excluída.", ""));
		} else if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
				.getIdprodutosOrcamento() == seguro) {
			Mensagem.lancarMensagemInfo("Seguro Viagem", "Não pode ser excluido");
		} else {
			if (ilinha >= 0) {
				int idproduto = orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
						.getIdprodutosOrcamento();

				if (idproduto == 33) {
					FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
					Filtroorcamentoproduto filtroorcamentoproduto = filtroOrcamentoProdutoFacade
							.pesquisar(aplicacaoMB.getParametrosprodutos().getCursos(), 33);
					if (listaProdutosOrcamento != null) {
						listaProdutosOrcamento.add(filtroorcamentoproduto);
					}
				}
				if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha)
						.getIdorcamentoProdutosOrcamento() != null) {
					OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
					orcamentoFacade.excluirOrcamentoProdutoOrcamento(orcamento.getOrcamentoprodutosorcamentoList()
							.get(ilinha).getIdorcamentoProdutosOrcamento());
				}
				orcamento.getOrcamentoprodutosorcamentoList().remove(ilinha);
				calcularValorTotalOrcamento();
			}
		}
	}

	public void excluirFormaPagamento(String ilinha) {
		int linha = Integer.parseInt(ilinha);
		if (linha >= 0) {
			gerarListaParcelamentoOriginal();
			if (formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento() != null) {
				ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
				parcelamentoPagamentoFacade.excluir(
						formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
			}
			ContasReceberBean contasReceberBean = new ContasReceberBean();
			if (vendas.getIdvendas() != null) {
					contasReceberBean.apagarContasReceber(formaPagamento.getParcelamentopagamentoList().get(linha),
							vendas.getIdvendas(), usuarioLogadoMB,
							formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
			}
			formaPagamento.getParcelamentopagamentoList().remove(linha);
			if (contasReceberBean.getValorJaRecebido() > 0) {

				Parcelamentopagamento parcelamento = new Parcelamentopagamento();
				parcelamento.setDiaVencimento(new Date());
				parcelamento.setFormaPagamento("Saldo pago");

				parcelamento.setNumeroParcelas(01);
				parcelamento.setTipoParcelmaneto("Matriz");
				parcelamento.setValorParcela(contasReceberBean.getValorJaRecebido());
				parcelamento.setValorParcelamento(contasReceberBean.getValorJaRecebido());
				if (formaPagamento != null) {
					parcelamento.setFormapagamento(formaPagamento);
				}
				if (formaPagamento.getParcelamentopagamentoList() == null) {
					formaPagamento.setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamento);
			}
			calcularParcelamentoPagamento();
		}
	}

	public void adicionarFormaPagamento() {
		gerarListaParcelamentoOriginal();
		String msg = validarFormaPagamento();
		if (msg.length() < 5) {
			int numeroParcelas = Integer.parseInt(this.numeroParcelas);
			float valorParcela = valorParcelar / numeroParcelas;
			if (formaPagamentoString.equalsIgnoreCase("Boleto")) {
				DataVencimentoBean dataVencimentoBean = new DataVencimentoBean(dataPrimeiroPagamento);
				dataPrimeiroPagamento = dataVencimentoBean.validarDataVencimento();
			}
			Parcelamentopagamento parcelamento = new Parcelamentopagamento();
			parcelamento.setDiaVencimento(dataPrimeiroPagamento);
			parcelamento.setFormaPagamento(formaPagamentoString);
			parcelamento.setNumeroParcelas(numeroParcelas);
			parcelamento.setTipoParcelmaneto(tipoParcelamento);
			parcelamento.setValorParcela(valorParcela);
			parcelamento.setValorParcelamento(valorParcelar);
			if (formaPagamento.getParcelamentopagamentoList() == null) {
				formaPagamento.setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
			}
			if (formaPagamento != null) {
				parcelamento.setFormapagamento(formaPagamento);
			}
			if (parcelamento.getFormaPagamento().equalsIgnoreCase("Boleto")
					|| (parcelamento.getFormaPagamento().equalsIgnoreCase("cheque"))) {
				parcelamento
						.setVerificarParcelamento(Formatacao.calcularDataParcelamento(parcelamento.getDiaVencimento(),
								parcelamento.getNumeroParcelas(), acomodacao.getDatainicial()));
			} else
				parcelamento.setVerificarParcelamento(false);
			if (parcelamento.isVerificarParcelamento()) {
				Mensagem.lancarMensagemWarn("Data Vencimento", "Data da ultima parcela dos "
						+ parcelamento.getFormaPagamento() + " é maior que data início do Curso");
			}

			if (vendas.getIdvendas() != null) {
					ContasReceberBean contasReceberBean = new ContasReceberBean();
					parcelamento = contasReceberBean.gerarParcelasIndividuais(parcelamento,
							formaPagamento.getParcelamentopagamentoList().size(), vendas, usuarioLogadoMB,
							acomodacao.getDatainicial());
			}
			formaPagamento.getParcelamentopagamentoList().add(parcelamento);
			calcularParcelamentoPagamento();
			valorParcela = 0;
			this.valorParcela = 0;
		} else {
			Mensagem.lancarMensagemErro("Atenção!", msg);
		}
	}

	public String validarFormaPagamento() {
		String msg = "";
		if (formaPagamento.getCondicao() == null) {
			msg = msg + "Campo forma de pagamento obrigatório";
		}
		if (formaPagamento.getPossuiJuros().equalsIgnoreCase("sim")) {
			if (formaPagamento.getValorJuros() != null) {
				if (formaPagamento.getValorJuros() == 0) {
					msg = msg + "Informar valor do Juros";
				}
			}

		}
		if (dataPrimeiroPagamento == null) {
			msg = msg + "Data do 1º Vencimento Obrigatorio";
		} else {
			if (formaPagamentoString.equalsIgnoreCase("Boleto")) {
				String dataAtualString = Formatacao.ConvercaoDataPadrao(new Date());
				Date dataAtual = Formatacao.ConvercaoStringData(dataAtualString);
				if (dataPrimeiroPagamento.before(dataAtual)) {
					msg = msg + "Data deve ser num próximo dia util";
				}
			}
		}
		if (formaPagamentoString.equalsIgnoreCase("sn")) {
			msg = msg + "Forma de pagamento não selecionada";
		}
		if (valorParcela <= 0) {
			msg = msg + "Valor da parcela não pode ser 0";
		}
		float saldo = (valorSaltoParcelar + 0.01f);
		if (valorParcelar > saldo) {
			msg = msg + "Valor a parcelar mais alto que saldo em aberto.";
		}
		return msg;
	}

	public void calcularParcelamentoPagamento() {
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			Float valorParcelado = 0.0f;
			for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
				valorParcelado = valorParcelado
						+ formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento();
			}
			valorSaltoParcelar = (valorTotal + this.formaPagamento.getValorJuros()) - valorParcelado;
			valorParcelar = valorSaltoParcelar;
		}
	}

	public void carregarValorCambio() {
		cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
		valorCambio = cambio.getValor();
		atualizarValoresProduto();
	}

	public void atualizarValoresProduto() {
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			int codTxTM = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				int codProdutoTxTM = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento();
				if (codProdutoTxTM == codTxTM) {
					orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaEstrangeira(
							orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() / valorCambio);
				} else if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira() > 0) {
					orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(
							orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira()
									* valorCambio);
				}
				calcularValorTotalOrcamento();
			}
		}
	}

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getCursos());
		} catch (Exception e) {
			  
		}
		float valorremessa = 0.0f;
		if (listaProdutoRemessa != null) {
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				int idProduto = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento();
				for (int n = 0; n < listaProdutoRemessa.size(); n++) {
					int idRemessa = listaProdutoRemessa.get(n).getProdutosorcamento().getIdprodutosOrcamento();
					if (idProduto == idRemessa) {
						valorremessa = valorremessa
								+ orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional();
					}
				}
			}
		}
		if (valorremessa > 0) {
			boolean achou = false;
			valorremessa = valorremessa * (aplicacaoMB.getParametrosprodutos().getPercentualremessa() / 100);
			int idRemessa = aplicacaoMB.getParametrosprodutos().getProdutoremessa();
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				int idProduto = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento();
				if (idRemessa == idProduto) {
					orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(valorremessa);
					orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.setValorMoedaEstrangeira(valorremessa / cambio.getValor());
					achou = true;
					i = 10000;
				}
			}
			if (!achou) {
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produtosorcamento = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getProdutoremessa());
				Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
				orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
				orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(valorremessa / cambio.getValor());
				orcamentoprodutosorcamento.setValorMoedaNacional(valorremessa);
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			}
		}
	}

	public void gerarListaParcelamentoOriginal() {
		if (vendas.getIdvendas() != null) {
			if (formaPagamento.getParcelamentopagamentoList() != null) {
				listaParcelamentoPagamentoOriginal = new ArrayList<Parcelamentopagamento>();
				listaParcelamentoPagamentoOriginal = formaPagamento.getParcelamentopagamentoList();
			}
		}
	}

	public String editarcambio(Float valorCambio) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("valorCambio", valorCambio);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 230);
		RequestContext.getCurrentInstance().openDialog("editarcambio", options, null);
		return "";
	}

	public void retornoDialogEditarCambio(SelectEvent event) {
		this.valorCambio = (float) event.getObject();
		cambio.setValor(valorCambio);
		vendas.setValorcambio(valorCambio);
		orcamento.setValorCambio(valorCambio);
		atualizarValoresProduto();
	}

	public void gerarListaProdutosOrcamento() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=24"
				
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void calcularValorAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isSelecionado()) {
			if (produtosOrcamentoBean.getNumeroSemanas() != acomodacao.getNumerosemana()) {
				ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
				Valorcoprodutos valorcoprodutos = null;
				Date dataconsulta = null;
				if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
						.isAcomodacaoindependente()) {
					dataconsulta = retornarDataConsultaOrcamento(acomodacao.getDatainicial(),
							produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
									.getFornecedorcidade().getFornecedor());
				}
				String sql = "Select v from  Valorcoprodutos v where v.numerosemanainicial<="
						+ produtosOrcamentoBean.getNumeroSemanas() + " and v.numerosemanafinal>="
						+ produtosOrcamentoBean.getNumeroSemanas() + " and v.coprodutos.idcoprodutos="
						+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos();
				if (produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DI")) {
					sql = sql + " AND v.datainicial<='" + Formatacao.ConvercaoDataSql(dataconsulta)
							+ "' and v.datafinal>='" + Formatacao.ConvercaoDataSql(dataconsulta) + "'";
				} else if (produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DS")) {
					sql = sql + " AND v.datainicial<='" + Formatacao.ConvercaoDataSql(dataconsulta)
							+ "' and v.datafinal>='" + Formatacao.ConvercaoDataSql(dataconsulta) + "'";
				}
				if (produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DM")) {
					sql = sql + " AND v.datainicial<='" + Formatacao.ConvercaoDataSql(new Date())
							+ "' and v.datafinal>='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
				}
				List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
				if (listaValorcoprodutoses != null) {
					for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
						if (valorcoprodutos == null) {
							valorcoprodutos = new Valorcoprodutos();
							valorcoprodutos = listaValorcoprodutoses.get(n);
						} else {
							valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
						}
					}
				}
				if (valorcoprodutos != null) {
					produtosOrcamentoBean.setValorcoprodutos(valorcoprodutos);
				}
			}
			int multiplicador = 1;
			if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
				multiplicador = (int) produtosOrcamentoBean.getNumeroSemanas();
			} else if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
				multiplicador = (int) (produtosOrcamentoBean.getNumeroSemanas() * 7);
			}
			produtosOrcamentoBean
					.setValorOrigianl(multiplicador * produtosOrcamentoBean.getValorcoprodutos().getValororiginal());
			produtosOrcamentoBean.setValorOriginalRS(produtosOrcamentoBean.getValorOrigianl() * valorCambio);
			gerarPromocaoAcomodacao(produtosOrcamentoBean);
		} else {
			produtosOrcamentoBean.setValorOrigianl(0.0f);
			produtosOrcamentoBean.setValorOriginalRS(0.0f);
			produtosOrcamentoBean.setNumeroSemanas(0);
		}
	}

	public Date retornarDataConsultaOrcamento(Date dataInicio, Fornecedor fornecedor) {
		int anoFornecedor = fornecedor.getAnotarifario();
		Calendar c = new GregorianCalendar();
		c.setTime(dataInicio);
		int ano = Formatacao.getAnoData(dataInicio);
		if (anoFornecedor >= ano) {
			return dataInicio;
		} else if (anoFornecedor < ano) {
			String sData = Formatacao.ConvercaoDataPadrao(dataInicio);
			String dia = sData.substring(0, 2);
			String mes = sData.substring(3, 5);
			sData = dia + "/" + mes + "/" + String.valueOf(anoFornecedor);
			return Formatacao.ConvercaoStringDataBrasil(sData);
		}
		return dataInicio;
	}

	public Valorcoprodutos compararValores(Valorcoprodutos valorNovo, Valorcoprodutos valorAtual) {
		if (valorNovo.getPromocional()) {
			return valorNovo;
		} else
			return valorAtual;
	}

	public void gerarPromocaoAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		String sql = "";
		if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma() != null) {
			sql = "select p From Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date())
					+ "'  and p.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
					+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade().getCidade()
							.getIdcidade()

					+ " group by p.promocaoacomodacao.idpromoacaoacomodacao";
		} else {
			sql = "select p From Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date())
					+ "'  and p.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
							.getIdfornecedorcidadeidioma()
					+ " group by p.promocaoacomodacao.idpromoacaoacomodacao";
		}
		PromocaoAcomodacaoCidadeFacade cidadeFacade = new PromocaoAcomodacaoCidadeFacade();
		List<Promocaoacomodacaocidade> promocaoacomodacaocidade = cidadeFacade.listar(sql);
		Valorcoprodutos valorcoprodutos = null;
		if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getComplementoacomodacao() != null) {
			valorcoprodutos = produtosOrcamentoBean.getValorcoprodutos();
		}
		if (promocaoacomodacaocidade != null && valorcoprodutos != null) {
			for (int i = 0; i < promocaoacomodacaocidade.size(); i++) {
				boolean tempromocao = verificarPromocaoAcomodacaoValida(
						promocaoacomodacaocidade.get(i).getPromocaoacomodacao(), valorcoprodutos,
						produtosOrcamentoBean);
				if (tempromocao) {
					float valordesconto = 0.0f;

					if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getTipodesconto()
							.equalsIgnoreCase("P")) {
						if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() > 0) {
							valordesconto = valorcoprodutos.getValororiginal()
									* (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() / 100);
							valordesconto = (float) (valordesconto * produtosOrcamentoBean.getNumeroSemanas());
						} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() > 0) {
							valordesconto = (float) (valorcoprodutos.getValororiginal()
									* produtosOrcamentoBean.getNumeroSemanas());
							valordesconto = valordesconto
									* (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() / 100);
						}
					} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getTipodesconto()
							.equalsIgnoreCase("V")) {
						if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() > 0) {
							valordesconto = (float) (promocaoacomodacaocidade.get(i).getPromocaoacomodacao()
									.getValorsemana() * produtosOrcamentoBean.getNumeroSemanas());
						} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() > 0) {
							valordesconto = promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal();
						}
					} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getTipodesconto()
							.equalsIgnoreCase("T")) {
						if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() > 0) {
							valordesconto = valorcoprodutos.getValororiginal()
									- promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana();
							if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
								valordesconto = (float) (valordesconto * produtosOrcamentoBean.getNumeroSemanas());
							}
						} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() > 0) {
							valordesconto = (float) (valorcoprodutos.getValororiginal()
									* produtosOrcamentoBean.getNumeroSemanas());
							valordesconto = valordesconto
									- promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal();
						}
					}
					if ((promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto() != null)
							&& (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto() > 0)) {
						if (valordesconto > promocaoacomodacaocidade.get(i).getPromocaoacomodacao()
								.getValormaximodesconto()) {
							valordesconto = promocaoacomodacaocidade.get(i).getPromocaoacomodacao()
									.getValormaximodesconto();
						}
					}
				} else {
					produtosOrcamentoBean.setValorPromocional(0.0f);
					produtosOrcamentoBean.setValorPromocionalRS(0.0f);
					produtosOrcamentoBean.setPromocao(false);
				}
			}
		} else {
			produtosOrcamentoBean.setValorPromocional(0.0f);
			produtosOrcamentoBean.setValorPromocionalRS(0.0f);
			produtosOrcamentoBean.setPromocao(false);
		}
	}

	public boolean verificarPromocaoAcomodacaoValida(Promocaoacomodacao promocao, Valorcoprodutos valorcoprodutos,
			ProdutosOrcamentoBean produtosOrcamentoBean) {
		Boolean tempromocao = false;
		if (promocao.getDatainicioiniciocurso() != null && promocao.getDatainicioterminiocurso() != null) {
			if ((acomodacao.getDatainicial().after(promocao.getDatainicioiniciocurso())
					|| acomodacao.getDatainicial().equals(promocao.getDatainicioiniciocurso()))
					&& (acomodacao.getDatainicial().before(promocao.getDatainicioterminiocurso())
							|| acomodacao.getDatainicial().equals(promocao.getDatainicioterminiocurso()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatainicioacomodacao() != null && promocao.getDataterminioacodomodacao() != null) {
			if ((acomodacao.getDatainicial().after(promocao.getDatainicioacomodacao())
					|| acomodacao.getDatainicial().equals(promocao.getDatainicioacomodacao()))
					&& (acomodacao.getDatatermino().before(promocao.getDataterminioacodomodacao())
							|| acomodacao.getDatatermino().equals(promocao.getDataterminioacodomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminocurso() != null) {
			if (acomodacao.getDatatermino().before(promocao.getDataterminocurso())
					|| acomodacao.getDatatermino().equals(promocao.getDataterminocurso())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatamatricula() != null) {
			if (new Date().before(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}

		if (promocao.getNumerosemanainicio() != null && promocao.getNumerosemanainicio() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (produtosOrcamentoBean.getNumeroSemanas() >= promocao.getNumerosemanainicio()
					&& produtosOrcamentoBean.getNumeroSemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaacima() != null && promocao.getValorsemanaacima() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorsemanaacima()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaigual() != null && promocao.getValorsemanaigual() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorsemanaigual()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipoacomodacao() != null && promocao.getTipoacomodacao().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipoacomodacao()
					.equalsIgnoreCase(promocao.getTipoacomodacao())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipoquarto() != null && promocao.getTipoquarto().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipoquarto()
					.equalsIgnoreCase(promocao.getTipoquarto())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTiporefeicao() != null && promocao.getTiporefeicao().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTiporefeicao()
					.equalsIgnoreCase(promocao.getTiporefeicao())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipobanheiro() != null && promocao.getTipobanheiro().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipobanheiro()
					.equalsIgnoreCase(promocao.getTipobanheiro())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;
	}

	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos, Date dataConsulta, String tipo) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.numerosemanainicial<="
				+ acomodacao.getNumerosemana() + " and v.numerosemanafinal>=" + acomodacao.getNumerosemana()
				+ " and v.tipodata='" + tipoData + "' and v.coprodutos.idcoprodutos=" + idCoProdutos;

		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorcoprodutoses.get(n);
				} else {
					valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
				}

			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = acomodacao.getNumerosemana();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = acomodacao.getNumerosemana() * 7;
			}
			po.setValorOrigianl(valorcoprodutos.getValororiginal() * multiplicador);
			po.setValorOriginalRS(po.getValorOrigianl() * valorCambio);
			if (tipo.equalsIgnoreCase("Acomodacao")) {
				po.setValorOrigianl(0.0f);
				po.setValorOriginalRS(0.0f);
			}
			return po;
		}
		return null;
	}

	public void gerarPromocaoCurso(List<ProdutosOrcamentoBean> listaObrigatorios) {
		String sql = "select p From Promocaocursocidade p where p.promoacaocurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promoacaocurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'"
//				+ "  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
//				+ resultadoOrcamentoBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
//				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
//				+ resultadoOrcamentoBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promoacaocurso.idpromoacaocurso";
		PromocaoCursoFornecedorCidadeFacade cidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
		List<Promocaocursocidade> listaPromocaocursocidade = cidadeFacade.listar(sql);
		if (listaPromocaocursocidade != null) {
			int posicao = 0;
			float desconto = 0.0f;
			String descricao = "";
			for (int j = 0; j < listaPromocaocursocidade.size(); j++) {
				Valorcoprodutos valorcoprodutos = null;
				for (int i = 0; i < listaObrigatorios.size(); i++) {
					if (listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getComplementocurso() != null) {
						valorcoprodutos = listaObrigatorios.get(i).getValorcoprodutos();
						posicao = i;
						i = 1000;
					}
				}
				if (listaPromocaocursocidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoValida(listaPromocaocursocidade.get(j).getPromoacaocurso(),
							valorcoprodutos);
					if (tempromocao) {
						float valordesconto = 0.0f;

						if (listaPromocaocursocidade.get(j).getPromoacaocurso().getTipodesconto()
								.equalsIgnoreCase("P")) {
							if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() > 0) {
								valordesconto = valorcoprodutos.getValororiginal()
										* (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() / 100);
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									valordesconto = valordesconto * acomodacao.getNumerosemana();
								}
							} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = acomodacao.getNumerosemana();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = acomodacao.getNumerosemana() * 7;
								}
								float valorcurso = valorcoprodutos.getValororiginal() * multiplicador;
								if (listaPromocaocursocidade.get(j).getPromoacaocurso().isAcumulapromocao()) {
									valorcurso = (valorcoprodutos.getValororiginal() * multiplicador) - desconto;
								}
								valordesconto = valorcurso
										* (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() / 100);
							}
						} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getTipodesconto()
								.equalsIgnoreCase("V")) {
							if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() > 0) {
								valordesconto = listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana()
										* acomodacao.getNumerosemana();
							} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() > 0) {
								valordesconto = listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal();
							}
						} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getTipodesconto()
								.equalsIgnoreCase("T")) {
							if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() > 0) {
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									valordesconto = (valorcoprodutos.getValororiginal() * acomodacao.getNumerosemana());
								} else {
									valordesconto = (valorcoprodutos.getValororiginal());
								}
								valordesconto = valordesconto
										- (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana()
												* acomodacao.getNumerosemana());
							} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() > 0) {
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									valordesconto = (valorcoprodutos.getValororiginal() * acomodacao.getNumerosemana());
								} else {
									valordesconto = (valorcoprodutos.getValororiginal());
								}
								valordesconto = valordesconto
										- listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal();
							}
						}
						if ((listaPromocaocursocidade.get(j).getPromoacaocurso().getValormaximodesconto() != null)
								&& (listaPromocaocursocidade.get(j).getPromoacaocurso().getValormaximodesconto() > 0)) {
							if (valordesconto > listaPromocaocursocidade.get(j).getPromoacaocurso()
									.getValormaximodesconto()) {
								valordesconto = listaPromocaocursocidade.get(j).getPromoacaocurso()
										.getValormaximodesconto();
							}
						}
						if (valordesconto > 0) {
							desconto = desconto + valordesconto;
						}
						if (valorcoprodutos.getCoprodutos().isPacote()) {
							desconto = 0f;
						}
					}
				}
			}
			if (desconto > 0) {
				listaObrigatorios.get(posicao).setDescricaopromocao(descricao);
				listaObrigatorios.get(posicao)
						.setValorPromocional(listaObrigatorios.get(posicao).getValorOrigianl() - desconto);
				listaObrigatorios.get(posicao)
						.setValorPromocionalRS(listaObrigatorios.get(posicao).getValorPromocional() * valorCambio);
				listaObrigatorios.get(posicao).setPromocao(true);
			}
		}
	}

	public ProdutosOrcamentoBean consultarValoresSuplementoAcomodacqo(ProdutosOrcamentoBean produtosOrcamentoBean,
			int idCoProdutos, Date dataConsulta) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		/*
		 * String sql = "Select v from  Valorcoprodutos v where v.datainicial>='" +
		 * produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().
		 * getFornecedorcidadeidioma()
		 * .getFornecedorcidade().getFornecedor().getAnotarifario() +
		 * "-01-01' and v.numerosemanainicial<=" +
		 * produtosOrcamentoBean.getNumeroSemanas() + " and v.numerosemanafinal>=" +
		 * produtosOrcamentoBean.getNumeroSemanas() + " and v.coprodutos.idcoprodutos="
		 * + idCoProdutos + " and v.produtosuplemento='Acomodação'";
		 */
//		Date dataInicial = retornarDataConsultaOrcamento(resultadoOrcamentoBean.getOcurso().getDatainicio(),
//				resultadoOrcamentoBean.getFornecedorcidadeidioma());

		String sql = "Select v from  Valorcoprodutos v where v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "' " + " and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "'  and v.numerosemanainicial<="
				+ produtosOrcamentoBean.getNumeroSemanas() + " and v.numerosemanafinal>="
				+ produtosOrcamentoBean.getNumeroSemanas() + " and v.coprodutos.idcoprodutos=" + idCoProdutos
				+ " and v.produtosuplemento='Acomodação' Order By v.idvalorcoprodutos DESC";

		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
		if (listaValorcoprodutoses == null) {
			sql = "Select v from  Valorcoprodutos v where v.datainicial>='" + Formatacao.ConvercaoDataSql(dataConsulta)
					+ "' " + " and v.datainicial<='" + Formatacao.ConvercaoDataSql(acomodacao.getDatatermino()) + "'"
					+ " and v.datafinal>='" + Formatacao.ConvercaoDataSql(dataConsulta)
					+ "'  and v.numerosemanainicial<=" + produtosOrcamentoBean.getNumeroSemanas()
					+ " and v.numerosemanafinal>=" + produtosOrcamentoBean.getNumeroSemanas()
					+ " and v.coprodutos.idcoprodutos=" + idCoProdutos
					+ " and v.produtosuplemento='Acomodação' Order By v.idvalorcoprodutos DESC";
			listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
		}
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorcoprodutoses.get(n);
				} else {
					valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
				}

			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = (int) produtosOrcamentoBean.getNumeroSemanas();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = (int) (produtosOrcamentoBean.getNumeroSemanas() * 7);
			}
			float valorSuplemento = calcularValorFracionadoSuplemento(produtosOrcamentoBean, multiplicador,
					produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma(), po);
			if (valorSuplemento == 0) {
				po = null;
			} else {
				po.setValorOrigianl(valorSuplemento);
				po.setValorOriginalRS(valorSuplemento * valorCambio);
			}
			return po;
		}
		return null;
	}

	public void gerarPromocaoTaxas(List<ProdutosOrcamentoBean> listaObrigatorios, List<ProdutosOrcamentoBean> curso) {
		String sql = "select p From Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'"
//				+ "  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
//				+ resultadoOrcamentoBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
//				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
//				+ resultadoOrcamentoBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promocaotaxacurso.idpromocaotaxacurso";
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		List<Promocaotaxacidade> listaPromocaotaxacidade = promocaoTaxaCidadeFacade.listar(sql);
		Valorcoprodutos valorcoprodutos = null;
		if (listaPromocaotaxacidade != null) {
			for (int j = 0; j < listaPromocaotaxacidade.size(); j++) {
				int idproduto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getProdutosorcamento()
						.getIdprodutosOrcamento();
				int posicao = 0;
				for (int i = 0; i < listaObrigatorios.size(); i++) {
					if (listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
							.getIdprodutosOrcamento() == idproduto) {
						valorcoprodutos = listaObrigatorios.get(i).getValorcoprodutos();
						posicao = i;
						i = 1005;
					}
				}
				if ((listaPromocaotaxacidade.get(j) != null && valorcoprodutos != null) && (curso != null && curso.size() > 0)) {
					boolean tempromocao = verificarPromocaoTaxasValida(curso,
							listaPromocaotaxacidade.get(j).getPromocaotaxacurso(), valorcoprodutos);
					if (tempromocao) {
						float valordesconto = 0.0f;
						if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("P")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = acomodacao.getNumerosemana();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = acomodacao.getNumerosemana() * 7;
								}
								valordesconto = (valorcoprodutos.getValororiginal() * multiplicador)
										* (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
												/ 100);
							}
						} else if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("V")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = acomodacao.getNumerosemana();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = acomodacao.getNumerosemana() * 7;
								}
								valordesconto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
										* multiplicador;
							}
						}
						if (valordesconto > 0) {
							listaObrigatorios.get(posicao).setDescricaopromocao(
									descricaoPromocaoTaxas(listaPromocaotaxacidade.get(j).getPromocaotaxacurso()));
							listaObrigatorios.get(posicao).setValorPromocional(
									listaObrigatorios.get(posicao).getValorOrigianl() - valordesconto);
							listaObrigatorios.get(posicao).setValorPromocionalRS(
									listaObrigatorios.get(posicao).getValorPromocional() * valorCambio);
							listaObrigatorios.get(posicao).setPromocao(true);
						}
					}
				}
			}
		}
	}

	public void gerarPromocaoTaxasAcomodacaoIndependente(List<ProdutosOrcamentoBean> listaObrigatorios,
			List<ProdutosOrcamentoBean> curso, Fornecedorcidadeidioma fornecedorcidadeidioma) {
		String sql = "select p From Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ fornecedorcidadeidioma.getIdfornecedorcidadeidioma();
//		if(!fornecedorcidadeidioma.isAcomodacaoindependente()) {
//			sql=sql	+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
//				+ resultadoOrcamentoBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento();
//		}
		sql = sql + " group by p.promocaotaxacurso.idpromocaotaxacurso";
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		List<Promocaotaxacidade> listaPromocaotaxacidade = promocaoTaxaCidadeFacade.listar(sql);
		Valorcoprodutos valorcoprodutos = null;
		if (listaPromocaotaxacidade != null) {
			for (int j = 0; j < listaPromocaotaxacidade.size(); j++) {
				int idproduto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getProdutosorcamento()
						.getIdprodutosOrcamento();
				int posicao = 0;
				for (int i = 0; i < listaObrigatorios.size(); i++) {
					if (listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
							.getIdprodutosOrcamento() == idproduto) {
						valorcoprodutos = listaObrigatorios.get(i).getValorcoprodutos();
						posicao = i;
						i = 1005;
					}
				}
				if (listaPromocaotaxacidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoTaxasValida(curso,
							listaPromocaotaxacidade.get(j).getPromocaotaxacurso(), valorcoprodutos);
					if (tempromocao) {
						float valordesconto = 0.0f;
						if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("P")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = acomodacao.getNumerosemana();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = acomodacao.getNumerosemana() * 7;
								}
								valordesconto = (valorcoprodutos.getValororiginal() * multiplicador)
										* (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
												/ 100);
							}
						} else if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("V")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = acomodacao.getNumerosemana();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = acomodacao.getNumerosemana() * 7;
								}
								valordesconto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
										* multiplicador;
							}
						}
						if (valordesconto > 0) {
							listaObrigatorios.get(posicao).setDescricaopromocao(
									descricaoPromocaoTaxas(listaPromocaotaxacidade.get(j).getPromocaotaxacurso()));
							listaObrigatorios.get(posicao).setValorPromocional(
									listaObrigatorios.get(posicao).getValorOrigianl() - valordesconto);
							listaObrigatorios.get(posicao).setValorPromocionalRS(
									listaObrigatorios.get(posicao).getValorPromocional() * valorCambio);
							listaObrigatorios.get(posicao).setPromocao(true);
						}
					}
				}
			}
		}
	}

	
	public void gerarPromocaoBrindes(List<ProdutosOrcamentoBean> listaObrigatorio, ProdutosOrcamentoBean produtosOrcamentoBean) {
		String sql = "select p From Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'"
//				+ "  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
//				+ resultadoOrcamentoBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
//				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
//				+ resultadoOrcamentoBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promocaobrindecurso.idpromocaobrindecurso";  
		PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
		List<Promocaobrindecursocidade> listaPromocaoBrindeCursoCidade = promocaoBrindeCursoCidadeFacade.listar(sql);
		if (listaPromocaoBrindeCursoCidade != null) {
			for (int j = 0; j < listaPromocaoBrindeCursoCidade.size(); j++) {
				Valorcoprodutos valorcoprodutos = produtosOrcamentoBean.getValorcoprodutos();
				if (listaPromocaoBrindeCursoCidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoBrindesValido(
							listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso(), valorcoprodutos,
							produtosOrcamentoBean);
					if (tempromocao) {

						float valordesconto = 0.0f;
						if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso().getGanhasemana() != null
								&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
										.getGanhasemana() > 0) {
							if (produtosOrcamentoBean.getDescricaobrinde() == null) {
								int numeroSemana = acomodacao.getNumerosemana()
										+ listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
												.getGanhasemana();
								produtosOrcamentoBean
										.setDescricaobrinde("Matricule-se até o dia "
												+ Formatacao.ConvercaoDataPadrao(listaPromocaoBrindeCursoCidade.get(j)
														.getPromocaobrindecurso().getDatamatricula())
												+ " pague " + acomodacao.getNumerosemana()
												+ " semanas e curse " + numeroSemana + ".");
								produtosOrcamentoBean.setPromocao(true);
								acomodacao.setNumerosemana(numeroSemana);
								int idtaxatm = aplicacaoMB.getParametrosprodutos().getTaxatmorcamento();
								for (int i = 0; i < listaObrigatorio.size(); i++) {
									if (idtaxatm != listaObrigatorio.get(i).getValorcoprodutos().getCoprodutos()
											.getProdutosorcamento().getIdprodutosOrcamento()) {
										ProdutosOrcamentoBean po = listaObrigatorio.get(i);
										int multiplicador = 1;
										if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
											multiplicador = acomodacao.getNumerosemana();
										} else if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
											multiplicador = acomodacao.getNumerosemana() * 7;
										}
										po.setValorOrigianl(po.getValorcoprodutos().getValororiginal() * multiplicador);
										po.setValorOriginalRS(po.getValorOrigianl()
												* valorCambio);
									}
								}
							}
						} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
								.getGanhadescontosemana() != null
								&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
										.getGanhadescontosemana() > 0) { 
								valordesconto = valorcoprodutos.getValororiginal() * listaPromocaoBrindeCursoCidade
										.get(j).getPromocaobrindecurso().getGanhadescontosemana(); 
								produtosOrcamentoBean.setPromocao(true); 
						} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
								.getGanhadescontosemanaacomodacao() != null
								&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
										.getGanhadescontosemanaacomodacao() > 0) {
							valordesconto = valorcoprodutos.getValororiginal() * listaPromocaoBrindeCursoCidade.get(j)
									.getPromocaobrindecurso().getGanhadescontosemanaacomodacao();
							int numeroSemanas = acomodacao.getNumerosemana()
									- listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescontosemanaacomodacao();
							produtosOrcamentoBean.setDescricaobrinde("Matricule-se até o dia "
									+ Formatacao.ConvercaoDataPadrao(listaPromocaoBrindeCursoCidade.get(j)
											.getPromocaobrindecurso().getDatamatricula())
									+ " pague " + numeroSemanas + " e ganhe mais "
									+ Formatacao.formatarDouble(produtosOrcamentoBean.getNumeroSemanas())
									+ " semana(s) de acomodação.");
							produtosOrcamentoBean.setPromocao(true);
						} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
								.getGanhadescricao() != null
								&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso().getGanhadescricao()
										.length() > 2) {
							if (produtosOrcamentoBean.getDescricaobrinde() == null) {
								produtosOrcamentoBean.setDescricaobrinde(listaPromocaoBrindeCursoCidade.get(j)
										.getPromocaobrindecurso().getGanhadescricao());
								produtosOrcamentoBean.setPromocao(true);
							}
						} 
						if (valordesconto > 0) { 
							if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
									.getGanhadescontosemana() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescontosemana() > 0) {
								if (produtosOrcamentoBean.getValorPromocional() == null
										|| produtosOrcamentoBean.getValorPromocional() == 0) {
									produtosOrcamentoBean
											.setValorPromocional(produtosOrcamentoBean.getValorOrigianl() - valordesconto);
									produtosOrcamentoBean.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
											* valorCambio);
								} else {
									produtosOrcamentoBean.setValorPromocional(
											produtosOrcamentoBean.getValorPromocional() - valordesconto);
									produtosOrcamentoBean.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
											* valorCambio);
								}
							} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
									.getGanhadescontosemanaacomodacao() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescontosemanaacomodacao() > 0) {
								if (produtosOrcamentoBean.getValorPromocional() == null
										|| produtosOrcamentoBean.getValorPromocional() == 0) {
									produtosOrcamentoBean.setValorPromocional(
											produtosOrcamentoBean.getValorOrigianl() - valordesconto);
									produtosOrcamentoBean
											.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
													* valorCambio);
								} else {
									produtosOrcamentoBean.setValorPromocional(
											produtosOrcamentoBean.getValorPromocional() - valordesconto);
									produtosOrcamentoBean
											.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
													* valorCambio);
								}
							}
						}
					}
				}
			}
		}
	}
	
	


	public boolean verificarPromocaoValida(Promocaocurso promocao, Valorcoprodutos valorcoprodutos) {
		Boolean tempromocao = false;
		if (promocao.getDatainicioiniciocurso() != null && promocao.getDatainicioterminiocurso() != null) {
			if ((acomodacao.getDatainicial().after(promocao.getDatainicioiniciocurso())
					|| acomodacao.getDatainicial().equals(promocao.getDatainicioiniciocurso()))
					&& (acomodacao.getDatainicial().before(promocao.getDatainicioterminiocurso())
							|| acomodacao.getDatainicial()
									.equals(promocao.getDatainicioterminiocurso()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if(promocao.getDatainicioacomodacao() != null && promocao.getDataterminioacodomodacao() != null) {
			if ((acomodacao.getDatainicial().after(promocao.getDatainicioacomodacao())
					|| acomodacao.getDatainicial().equals(promocao.getDatainicioacomodacao()))
					&& (acomodacao.getDatatermino().before(promocao.getDataterminioacodomodacao())
							|| acomodacao.getDatatermino()
									.equals(promocao.getDataterminioacodomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatamatricula() != null) {
			if (new Date().before(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminocurso() != null) {
			if (acomodacao.getDatatermino().before(promocao.getDataterminocurso())
					|| acomodacao.getDatatermino().equals(promocao.getDataterminocurso())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanainicio() != null && promocao.getNumerosemanainicio() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (acomodacao.getNumerosemana() >= promocao.getNumerosemanainicio()
					&& acomodacao.getNumerosemana() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaacima() != null && promocao.getValorsemanaacima() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorsemanaacima()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaigual() != null && promocao.getValorsemanaigual() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorsemanaigual()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTurno() != null && promocao.getTurno().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementocurso().getTurno()
					.equalsIgnoreCase(promocao.getTurno())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer
					.parseInt(valorcoprodutos.getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && valorcoprodutos.getCoprodutos().getComplementocurso()
					.getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;
	}
	
	public Date calcularDataTerminoCurso(Date dataInical, int numeroSemanas) {
		if ((dataInical != null) && (numeroSemanas > 0)) {
			if (numeroSemanas > 0) {
				Date data = Formatacao.calcularDataFinal(dataInical, numeroSemanas);
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
					Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.OrcamentoCursoMB.class.getName())
							.log(Level.SEVERE, null, ex);
				}
				String sql = "Select f from Fornecedorferias f where f.datafinal>='"
						+ Formatacao.ConvercaoDataSql(dataInical) + "' and f.datafinal<='"
						+ Formatacao.ConvercaoDataSql(data) + "'";
				FornecedorFeriasFacade fornecedorFeriasFacade = new FornecedorFeriasFacade();
				List<Fornecedorferias> listaFornecedorferias = fornecedorFeriasFacade.listar(sql);
				if (listaFornecedorferias == null) {
					listaFornecedorferias = new ArrayList<Fornecedorferias>();
				}
				if (listaFornecedorferias.size() > 0) {
					try {
						data = Formatacao.SomarDiasDatas(data, listaFornecedorferias.get(0).getNumerodias());
					} catch (Exception ex) {
						Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.OrcamentoCursoMB.class.getName())
								.log(Level.SEVERE, null, ex);
					}
				}
				return data;
			}
		}
		return null;
	}
	
	public float calcularValorFracionadoSuplemento(ProdutosOrcamentoBean produtosOrcamentoBean, int multiplicador,
			Fornecedorcidadeidioma fornecedorcidadeidioma, ProdutosOrcamentoBean po) {
		float valorSuplemento = 0.0f;
		Date dataInical = retornarDataConsultaOrcamento(acomodacao.getDatainicial(),
				produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor());
		int nSemana = (int) produtosOrcamentoBean.getNumeroSemanas();
		Date dataTermino = calcularDataTerminoCurso(dataInical, nSemana);
		int numeroDias = 0;  
	//	if (po.getValorcoprodutos().getDatainicial().after(dataInical) && po.getValorcoprodutos().getDatainicial().after(dataTermino)  ||
	//			(po.getValorcoprodutos().getDatafinal().before(dataInical) && po.getValorcoprodutos().getDatafinal().before(dataTermino))){
	//		calcular = false;
	//	}
	//	if (calcular){
		if ((po.getValorcoprodutos().getDatainicial().before(dataInical)
				|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical)))
				&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;
	
		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;
	
		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino))) {
	
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(),
					po.getValorcoprodutos().getDatafinal());
		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), dataTermino);
	
		} else if ((po.getValorcoprodutos().getDatainicial().before(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			if (Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical))) {
				numeroDias = 1;
			}else {
				numeroDias = Formatacao.subtrairDatas(dataInical, po.getValorcoprodutos().getDatafinal());
			}
		} else if ((po.getValorcoprodutos().getDatainicial().before(dataInical)
				|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical)))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), dataTermino);
	
		}else {
			valorSuplemento = -1;
			numeroDias=0;
		}
		if ((valorSuplemento == 0) && (numeroDias > 0)) {
			if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
				int resto = (numeroDias % 7);
				numeroDias = numeroDias / 7;
				if (resto > 0) {
					numeroDias = numeroDias + 1;
				}
			}
			valorSuplemento = po.getValorcoprodutos().getValororiginal();
			valorSuplemento = valorSuplemento * numeroDias;
		}
		if (valorSuplemento<0){
			valorSuplemento=0;
		}   
		return valorSuplemento;
	}
	
	public boolean verificarPromocaoTaxasValida(List<ProdutosOrcamentoBean> curso, Promocaotaxacurso promocao,
			Valorcoprodutos valorcoprodutos) {
		Boolean tempromocao = false;
		if (promocao.getDatainiciocursoinicial() != null && promocao.getDatainiciocursofinal() != null) {
			if ((acomodacao.getDatainicial().after(promocao.getDatainiciocursoinicial())
					|| acomodacao.getDatainicial().equals(promocao.getDatainiciocursoinicial()))
					&& (acomodacao.getDatainicial().before(promocao.getDatainiciocursofinal())
							|| acomodacao.getDatainicial()
									.equals(promocao.getDatainiciocursofinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataacomodacaoinicial() != null && promocao.getDatafinalacomodacao() != null) {
			if ((acomodacao.getDatainicial().after(promocao.getDataacomodacaoinicial())
					|| acomodacao.getDatainicial().equals(promocao.getDataacomodacaoinicial()))
					&& (acomodacao.getDatatermino().before(promocao.getDatafinalacomodacao())
							|| acomodacao.getDatatermino()
									.equals(promocao.getDatafinalacomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getAcomodacaoselecionada()) {
			return false;
		}
		if (promocao.getDatamatriculaenciadaate() != null) {
			String datatexto = Formatacao.ConvercaoDataPadrao(new Date());
			Date data = Formatacao.ConvercaoStringData(datatexto);
			if (data.before(promocao.getDatamatriculaenciadaate())
					|| data.equals(promocao.getDatamatriculaenciadaate())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminio() != null) {
			if (acomodacao.getDatatermino().before(promocao.getDataterminio())
					|| acomodacao.getDatatermino().equals(promocao.getDataterminio())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanasinicial() != null && promocao.getNumerosemanasinicial() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (acomodacao.getNumerosemana() >= promocao.getNumerosemanasinicial()
					&& acomodacao.getNumerosemana() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorporsemana() != null && promocao.getValorporsemana() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorporsemana()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer.parseInt(
					curso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && curso.get(0).getValorcoprodutos().getCoprodutos()
					.getComplementocurso().getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;
	
	}
	
	public String descricaoPromocaoTaxas(Promocaotaxacurso promocaotaxacurso) {
		String descricao = "";
		if (promocaotaxacurso.getAcomodacaoselecionada()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
		if (promocaotaxacurso.getCargahoraria() != null && promocaotaxacurso.getCargahoraria() > 0
				&& promocaotaxacurso.getTipocargahoraria() != null) {
			descricao = descricao + "Carga Horaria: " + promocaotaxacurso.getCargahoraria() + " "
					+ promocaotaxacurso.getTipocargahoraria() + " | ";
		}
		if (promocaotaxacurso.getDataacomodacaoinicial() != null
				&& promocaotaxacurso.getDatafinalacomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatafinalacomodacao()) + " | ";
		}
		if (promocaotaxacurso.getDatainiciocursoinicial() != null
				&& promocaotaxacurso.getDatainiciocursofinal() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursofinal()) + " | ";
		}
		if (promocaotaxacurso.getDatamatriculaenciadaate() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatamatriculaenciadaate()) + " | ";
		}
		if (promocaotaxacurso.getDataterminio() != null) {
			descricao = descricao + "Data termino de curso até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataterminio()) + " | ";
		}
		if ((promocaotaxacurso.getDatavalidadefinal() != null)
				&& (promocaotaxacurso.getDatavalidadeinicial() != null)) {
			descricao = descricao + "Data validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadeinicial()) + " a "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaotaxacurso.getNumerosemanafinal() != null && promocaotaxacurso.getNumerosemanasinicial() != null
				&& promocaotaxacurso.getNumerosemanasinicial() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaotaxacurso.getNumerosemanasinicial() + " até "
					+ promocaotaxacurso.getNumerosemanafinal() + " | ";
		}
		if (promocaotaxacurso.getTurno() != null && promocaotaxacurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaotaxacurso.getTurno() + " | ";
		}
		if (promocaotaxacurso.getValorporsemana() != null && promocaotaxacurso.getValorporsemana() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaotaxacurso.getValorporsemana()) + " | ";
		}
	
		if (promocaotaxacurso.getTipodesconto() != null) {
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + "% | ";
			}
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + " | ";
			}
		}
		return descricao;
	}
	
	public boolean verificarPromocaoBrindesValido(Promocaobrindecurso promocao, Valorcoprodutos valorcoprodutos,
			ProdutosOrcamentoBean produtosOrcamentoBean) {
		Boolean tempromocao = false;
		if (promocao.getDatainicio() != null && promocao.getDatafinal() != null) {
			if ((acomodacao.getDatainicial().after(promocao.getDatainicio())
					|| acomodacao.getDatainicial().equals(promocao.getDatainicio()))
					&& (acomodacao.getDatainicial().before(promocao.getDatafinal())
							|| acomodacao.getDatainicial().equals(promocao.getDatafinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataacomodacaoinicial() != null && promocao.getDataacomodacaofinal() != null) {
			if ((acomodacao.getDatainicial().after(promocao.getDataacomodacaoinicial())
					|| acomodacao.getDatainicial().equals(promocao.getDataacomodacaoinicial()))
					&& (acomodacao.getDatatermino().before(promocao.getDataacomodacaofinal())
							|| acomodacao.getDatatermino()
									.equals(promocao.getDataacomodacaofinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatamatricula() != null) {
			if (new Date().before(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanainicial() != null && promocao.getNumerosemanainicial() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (acomodacao.getNumerosemana() >= promocao.getNumerosemanainicial()
					&& acomodacao.getNumerosemana() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanacurso() != null && promocao.getNumerosemanacurso() > 0) {
			int nSemana = 0;
			for (int i = acomodacao.getNumerosemana(); i <= promocao
					.getNumerosemanacurso(); i++) {
				nSemana = nSemana + promocao.getNumerosemanacurso();
			} 
			tempromocao = true;
		}
		if (promocao.getNumerosemanaacomodacao() != null && promocao.getNumerosemanaacomodacao() > 0) {
			int nSemana = 0;
			for (int i = (int) produtosOrcamentoBean.getNumeroSemanas(); i <= promocao
					.getNumerosemanaacomodacao(); i++) {
				nSemana = nSemana + promocao.getNumerosemanaacomodacao();
			} 
			tempromocao = true;
		}
		if (promocao.getValorporsemana() != null && promocao.getValorporsemana() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorporsemana()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer
					.parseInt(valorcoprodutos.getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && valorcoprodutos.getCoprodutos().getComplementocurso()
					.getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;
	
	}
	
	
	public String calcularJuros() {
		if (formaPagamento.getValorOrcamento() != null && formaPagamento.getValorOrcamento() > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("total", formaPagamento.getValorOrcamento());
			RequestContext.getCurrentInstance().openDialog("calcularJuros");
		}
		return "";
	}
	
	public void retornoValorJuros() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		formaPagamento.setValorJuros((float) session.getAttribute("valorJuros"));
		session.removeAttribute("valorJuros");
		calcularValorTotalOrcamento();
	}
	
	public void gerarListaTipoParcelamento() {
		listaTipoParcelamento = Formatacao.gerarListaTipoParcelamento(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().isParcelamentojoja(), formaPagamentoString,
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
	}
	
	
	public void calcularValorParcelas() {
		if (valorParcelar > 0) {
			int numero = Integer.parseInt(numeroParcelas);
			float vParcela = valorParcelar / numero;
			valorParcela = vParcela;
		}
	}
	
	
	public String selecionarCreditoCancelamento() {
		if (formaPagamentoString.equalsIgnoreCase("Credito")) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			int idcliente = cliente.getIdcliente();
			session.setAttribute("idcliente", String.valueOf(idcliente));
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("closable", false);
			RequestContext.getCurrentInstance().openDialog("utilizarCredito", options, null);
		}else  {
			Mensagem.lancarMensagemInfo("", "Selecione a Forma de pagamento Credito");
		}
		return "";
	}
	
	public void retornoSelecionarCancelamento(SelectEvent event) {
		if (event.getObject() != null) {
			cancelamento = (Cancelamento) event.getObject();
			dataPrimeiroPagamento = new Date();
			valorParcelar = cancelamento.getTotalreembolso();
			numeroParcelas = "01";
			valorParcela = valorParcelar;
			adicionarFormaPagamento();
		}
	}
	
	
	//public String calcularComissaoFranquia() {
	//	Vendascomissao vendascomissao = new Vendascomissao();
	//	if (vendas.getIdvendas() == null) {
	//		vendas.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
	//		vendas.setValor(valorTotal);
	//		vendas.setCambio(cambio);
	////		PRproduto = ConsultaBean.getProdtuo(24);
	////		vendas.setProdutos(produto);
	//	}
	//	ComissaoCursoBean comissaoCursoBean = new ComissaoCursoBean(aplicacaoMB, venda,
	//			orcamento.getOrcamentoprodutosorcamentoList(), fornecedorComissao, formaPagamento.getParcelamentopagamentoList(),
	//			curso.getDataInicio(), vendascomissao, formaPagamento.getValorJuros(), false);
	//	FacesContext fc = FacesContext.getCurrentInstance();
	//	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	//	session.setAttribute("vendascomissao", vendascomissao);
	//	session.setAttribute("percentualcomissao", comissaoCursoBean.getPercentualComissao());
	//	Map<String, Object> options = new HashMap<String, Object>();
	//	options.put("contentWidth", 380);
	//	RequestContext.getCurrentInstance().openDialog("calcularcomissao", options, null);
	//	return "";
	//}
	
	public String validarDados() {
		String msg = "";
		if (listaAcomodacao == null || listaAcomodacao.size()<=0) {
			Mensagem.lancarMensagemInfo("Informe a Acomodação", "");
			msg = "Sim";
		}
		
		if (formaPagamento != null && (formaPagamento.getParcelamentopagamentoList() == null || formaPagamento.getParcelamentopagamentoList().size() <=0)) {
			Mensagem.lancarMensagemInfo("Informe a forma de pagamento", "");
			msg = "Sim";
		}
		
		if (valorParcelar > 0.0f) {
			Mensagem.lancarMensagemInfo("Saldo em aberto", "");
			msg = "Sim";
		}
		
		if (valorParcelar < 0.0f) {
			Mensagem.lancarMensagemInfo("Forma de pagamento maior que o valor da venda", "");
			msg = "Sim";
		}
		
		if (programas == null || programas.getIdprodutos() == null) {
			Mensagem.lancarMensagemInfo("Informe o produto vinculado a está venda;", "");
			msg = "Sim";
		}
		
		if (cidade == null || cidade.getIdcidade() == null) {
			Mensagem.lancarMensagemInfo("Informe a cidade;", "");
			msg = "Sim";
		}
		
		if (pais == null || pais.getIdpais() == null) {
			Mensagem.lancarMensagemInfo("Informe o pais;", "");
			msg = "Sim";
		}
		return msg;
	}
	
	public String salvar() {
		String msg = validarDados();
		if (msg == null || msg.length() == 0) {
			vendas.setValorpais(totalMoedaEstrangeira * cambio.getValor());
			Cambio cambioBrasil = null;
			if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getIdpais() != 5) {
				
				Pais pais = paisDao.consultar(5);
				cambioBrasil = cambioDao.consultarCambioMoedaPais(Formatacao.ConvercaoDataSql(pais.getDatacambio()), cambio.getMoedas().getIdmoedas(), pais);
				totalMoedaReal = totalMoedaEstrangeira * cambioBrasil.getValor();
			}
			vendas.setValor(totalMoedaReal);
			salvarVenda();
			acomodacao.setVendas(vendas);
			AcomodacaoFacade acomodacaoFacade = new AcomodacaoFacade();
			acomodacao = acomodacaoFacade.salvar(acomodacao);
			ProgramasBean programasBean = new ProgramasBean();
			formaPagamento = programasBean.salvarFormaPagamento(formaPagamento, vendas);
			float valorCambioBrasil = 0.0f;
			if (cambioBrasil != null) {
				valorCambioBrasil = cambioBrasil.getValor();
			}
			orcamento = programasBean.salvarOrcamento(orcamento, cambio, vendas.getValorpais(), totalMoedaEstrangeira, valorCambio, vendas, null, totalMoedaReal, valorCambioBrasil);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			if (novoLancamento) {
				new ContasReceberBean(acomodacao.getVendas(),
						formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, true,
						acomodacao.getDatainicial());
			}
			return "consAcomodacao";
		}
		return "";
	}
	
	
	public Vendas salvarVenda() {
		vendas.setDataVenda(new Date());
		vendas.setCambio(cambio);
		vendas.setCliente(lead.getCliente());
		vendas.setValor(valorTotal);
		vendas.setSituacao("PROCESSO");
		ProdutoFacade produtoFacade = new ProdutoFacade();
		Produtos produtos = produtoFacade.consultar(24);
		vendas.setProdutos(produtos);
		vendas.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
		vendas.setUsuario(usuarioLogadoMB.getUsuario());
		vendas.setVendasMatriz("S");
		vendas.setVendaimportada(0);
		vendas.setUsuariocancelamento(0);
		vendas.setValorcambio(valorCambio);
		vendas.setStatuscobranca("p");
		vendas.setRestricaoparcelamento(false);
		vendas.setPonto(0);
		vendas.setPontoescola(0);
		if (vendas.getIdvendas() == null) {
			vendas.setIdlead(lead.getIdlead());
		}
		vendas.setPontoextra(0);
		vendas.setIdregravenda(0);
		vendas.setSituacaogerencia("A");
		vendas.setSituacaofinanceiro("N");
		
		vendas = vendasDao.salvar(vendas);
		return vendas;
	}
	
	public String cancelar() {
		return "consAcomodacao";
	}
	
	
	public void gerarListaAcomodacaoIndependente() {
		listaAcomodacoesIndependente = new ArrayList<>();
		ValorCoProdutosFacade coProdutosFacade = new ValorCoProdutosFacade();
		String sql = "Select c from Valorcoprodutos c where c.coprodutos.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
				+ cidade.getIdcidade()
				+ " and c.coprodutos.fornecedorcidadeidioma.acomodacaoindependente=TRUE"
				+ " and c.coprodutos.tipo='Acomodacao" + "' and c.coprodutos.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoidade()
				+ " and c.coprodutos.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
				+ " and c.coprodutos.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao()
				+ " and c.coprodutos.apenaspacote=FALSE" + " GROUP BY c.coprodutos.idcoprodutos"
				+ " ORDER BY c.valororiginal";
		List<Valorcoprodutos> listaCoProdutos = coProdutosFacade.listar(sql);
		if (listaCoProdutos != null) {
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				Date dataconsulta = retornarDataConsultaOrcamento(acomodacao.getDatainicial(),
						listaCoProdutos.get(i).getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade()
								.getFornecedor());
				ProdutosOrcamentoBean po = consultarValores("DI",
						listaCoProdutos.get(i).getCoprodutos().getIdcoprodutos(), dataconsulta);
				if (po != null) {
					po.setListaSemanas(new ArrayList<Integer>());
					po.setListaSemanas(retornarNSemanas(listaCoProdutos.get(i)));
					listaAcomodacoesIndependente.add(po);
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getCoprodutos().getIdcoprodutos(), new Date());
					if (po != null) {
						po.setListaSemanas(new ArrayList<Integer>());
						po.setListaSemanas(retornarNSemanas(listaCoProdutos.get(i)));
						listaAcomodacoesIndependente.add(po);
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getCoprodutos().getIdcoprodutos(),
								dataconsulta);
						if (po != null) {
							po.setListaSemanas(new ArrayList<Integer>());
							po.setListaSemanas(retornarNSemanas(listaCoProdutos.get(i)));
							listaAcomodacoesIndependente.add(po);
						}
					}
				}
			}
			verificarAcomodacao();
		}
	}
	
	
	public void verificarAcomodacao() {
		listaAcomodacoesIndependente1 = new ArrayList<>();
		listaAcomodacoesIndependente2 = new ArrayList<>();
		listaAcomodacoesIndependente3 = new ArrayList<>();
		fornecedor1 = 0;
		fornecedor2 = 0;
		fornecedor3 = 0;
		ProdutosOrcamentoBean po;
		for (int i = 0; i < listaAcomodacoesIndependente.size(); i++) {
			if (fornecedor1 <=0) {
				fornecedor1 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor();
				nomeFornecedor1 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getNome();
			}else if(fornecedor2 <=0 && (fornecedor1 != fornecedor2)) {
				fornecedor2 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor();
				nomeFornecedor2 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getNome();
			}else if (fornecedor3 <=0 && (fornecedor2 != fornecedor3)) {
				fornecedor3 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor();
				nomeFornecedor3 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getNome();
			}
			if (fornecedor1 > 0) {
				if (fornecedor1 == listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor()) {
					po = new ProdutosOrcamentoBean();
					po = listaAcomodacoesIndependente.get(i);
					listaAcomodacoesIndependente1.add(po);
				}else if(fornecedor2 == listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor()) {
					po = new ProdutosOrcamentoBean();
					po = listaAcomodacoesIndependente.get(i);
					listaAcomodacoesIndependente2.add(po);
				}else if(fornecedor3 == listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor()) {
					po = new ProdutosOrcamentoBean();
					po = listaAcomodacoesIndependente.get(i);
					listaAcomodacoesIndependente3.add(po);
				}
			}
		}
	}
	
	
	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos, Date dataconsulta) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null; 
		String sql = "Select v from  Valorcoprodutos v where v.produtosuplemento='valor' and v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "'  and v.tipodata='" + tipoData 
				+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos + " ORDER BY v.valororiginal";
		List<Valorcoprodutos> listaValorCoprodutos = valorCoProdutosFacade.listar(sql);
		if (listaValorCoprodutos != null && listaValorCoprodutos.size() > 0) {
			valorcoprodutos = listaValorCoprodutos.get(0);
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			po.setValorOrigianl(0.0f);
			po.setValorOriginalRS(0.0f);
			return po;
		}
		return null;
	}
	
	
	public List<Integer> retornarNSemanas(Valorcoprodutos valorcoprodutos){
		int nSemana = 0;
		List<Integer> listaSemanas = new ArrayList<Integer>();
		for (int i = 0; i < 48; i++) {
			nSemana = 1 + i;
			listaSemanas.add(nSemana);
		}
		return listaSemanas;
	}

}
