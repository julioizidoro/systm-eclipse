package br.com.travelmate.managerBean.curso;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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

import br.com.travelmate.bean.ConsultaBean;
import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.RegraVistoBean;
import br.com.travelmate.bean.comissao.ComissaoCursoBean;
import br.com.travelmate.bean.controleAlteracoes.ControleAlteracaoCursoBean;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CidadeFacade;
import br.com.travelmate.facade.DadosPaisFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.DepartamentoProdutoFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FornecedorPaisFacade;
import br.com.travelmate.facade.OcursoPacoteFacade;
import br.com.travelmate.facade.OrcamentoCursoFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.facade.SeguroPlanosFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.ValorSeguroFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.facade.VendasPacoteFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.ProductRunnersMB;
import br.com.travelmate.managerBean.TmRaceMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Controlealteracoes;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Cursospacote;
import br.com.travelmate.model.Dadospais;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Departamentoproduto;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Fornecedorpais;
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursopacote;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtoorcamentocurso;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Seguroplanos;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Vendaspacote;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadCursoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private DashBoardMB dashBoardMB;
	@Inject
	private MateRunnersMB metaRunnersMB;
	@Inject
	private ProductRunnersMB productRunnersMB;
	@Inject
	private TmRaceMB tmRaceMB;
	private Vendas venda;
	private Vendas vendaAlterada;
	private Formapagamento formaPagamento;
	private Produtos produto;
	private Cliente cliente;
	private Seguroviagem seguroViagem;
	private Seguroviagem seguroViagemAlterado;
	private Orcamento orcamento;
	private Curso curso;
	private String cambioAlterado = "Não";
	private float valorCambio = 0;
	private float valorTotal = 0;
	private float totalPagar = 0;
	private float valorParcelar = 0;
	private float valorParcela = 0;
	private float valorSaltoParcelar = 0;
	private float valorMoedaEstrangeira = 0;
	private float valorMoedaReal = 0;
	private float totalMoedaEstrangeira = 0;
	private float totalMoedaReal = 0;
	private Cambio cambio;
	private boolean consultaCambio = false;
	private Fornecedorcidade fornecedorCidade;
	private Fornecedorcidade fornecedorSeguro;
	private Fornecedorcidade fornecedorCidadeAlterado;
	private Date dataCambio;
	private boolean novaFicha = false; 
	private Curso cursoAlterado;
	private String dadosAlterado;
	private String situacao = "PROCESSO";
	private float valorVendaAlterar = 0.0f;
	private Fornecedorcomissaocurso fornecedorComissao;
	private Boolean CheckBoxSeguroViagem;
	private Boolean CheckBoxSegundoCurso;
	private Pais pais;
	private String formaPagamentoString;
	private Date dataPrimeiroPagamento;
	private String tipoParcelamento;
	private String numeroParcelas = "01";
	private Boolean vendaMatriz;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidadeSeguro;
	private Idioma idioma;
	private List<Paisproduto> listaPais;
	private Cidade cidade;
	private List<Filtroorcamentoproduto> listaProdutosOrcamento;
	private Produtosorcamento produtosorcamento;
	private List<Cidade> listaCidade;
	private boolean enviarFicha;
	private String camposSegundoCurso = "true";
	private String camposAcomodacao = "true";
	private String camposAcomodacaoCasaFamilia = "true";
	private String camposSeguroViagem = "true";
	private String camposSeguroGovernamental = "true";
	private String camposCartaoVtm = "true";
	private List<Valoresseguro> listaValoresSeguro;
	private Dadospais dadosPais;
	private String habilitarDadosPai = "false";
	private boolean visualizarDadosPais;
	private boolean clienteselecionado = true;
	private String editarEscola;
	private List<Moedas> listaMoedas;
	private Boolean habiltarSegundoCurso = false;
	private Moedas moeda;
	private Controlealteracoes controlealteracoes;
	private Departamentoproduto depPrograma;
	private Departamento depFinanceiro;
	private List<String> listaTipoParcelamento;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga;
	private boolean digitosTelefoneContatoEmergencia;
	private boolean digitosTelefonePai;
	private boolean digitosTelefoneMae;
	private Cancelamento cancelamento;
	private Lead lead;
	private String voltarControleVendas = "";
	private Cursospacote cursospacote;
	private boolean desabilitarSeguro = false;
	private List<Seguroplanos> listaSeguroPlanos;
	private Seguroplanos seguroplanos;
	private boolean segurocancelamento=false;
	private boolean desabilitarAlergiaAlimento = true;
	private float valorAlterarSeguro = 0.0f;
	private boolean habilitarAvisoCambio = false;

	@PostConstruct()
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
			curso = (Curso) session.getAttribute("curso");
			cliente = (Cliente) session.getAttribute("cliente");
			lead = (Lead) session.getAttribute("lead");
			session.removeAttribute("cliente");
			session.removeAttribute("lead");
			session.removeAttribute("curso");
			session.removeAttribute("voltarControleVendas");
			iniciarListaFornecedorSeguro();
			gerarListaCursos();
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			int idProduto = 0;
			idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
			listaPais = paisProdutoFacade.listar(idProduto);
			carregarComboMoedas();
			if (curso == null) {
				editarEscola = "false";
				iniciarNovoCurso();
				dadosPais = new Dadospais();
				dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
				if (cliente != null && cliente.getIdcliente() != null) {
					verificarMenorIdade();
				}
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
					editarEscola = "false";
				} else {
					editarEscola = "true";
				}
				iniciarAlteracaoCurso();
				controlealteracoes.setVendas(venda);
				if ((venda.getSituacao().equalsIgnoreCase("PROCESSO")) && (venda.isRestricaoparcelamento())) {
					verificarAlteracaoListaParcelamento();
				}
				if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
					desabilitarSeguro = true;
				}
				if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
					venda.setDataVenda(new Date());
				}
			}
			if (curso.getSCurso() == null) {
				curso.setSCurso("");
			}
			if (curso.getSTipoCargaHoraria() == null) {
				curso.setSTipoCargaHoraria("");
			}
			if (curso.getSeguradoraGovernamental() == null) {
				curso.setSeguradoraGovernamental("");
			}
			formaPagamentoString = "sn";
			gerarListaTipoParcelamento();
			digitosTelefoneContatoEmergencia = aplicacaoMB.checkBoxTelefone(
					usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
					curso.getFoneContatoEmergencia());
		}
	}

	public Moedas getMoeda() {
		return moeda;
	}

	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
	}

	public Boolean getHabiltarSegundoCurso() {
		return habiltarSegundoCurso;
	}

	public void setHabiltarSegundoCurso(Boolean habiltarSegundoCurso) {
		this.habiltarSegundoCurso = habiltarSegundoCurso;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public float getValorSaltoParcelar() {
		return valorSaltoParcelar;
	}

	public void setValorSaltoParcelar(float valorSaltoParcelar) {
		this.valorSaltoParcelar = valorSaltoParcelar;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public Vendas getVendaAlterada() {
		return vendaAlterada;
	}

	public void setVendaAlterada(Vendas vendaAlterada) {
		this.vendaAlterada = vendaAlterada;
	}

	public boolean isSegurocancelamento() {
		return segurocancelamento;
	}

	public void setSegurocancelamento(boolean segurocancelamento) {
		this.segurocancelamento = segurocancelamento;
	}

	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public boolean isEnviarFicha() {
		return enviarFicha;
	}

	public void setEnviarFicha(boolean enviarFicha) {
		this.enviarFicha = enviarFicha;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public boolean isVisualizarDadosPais() {
		return visualizarDadosPais;
	}

	public void setVisualizarDadosPais(boolean visualizarDadosPais) {
		this.visualizarDadosPais = visualizarDadosPais;
	}

	public String getHabilitarDadosPai() {
		return habilitarDadosPai;
	}

	public void setHabilitarDadosPai(String habilitarDadosPai) {
		this.habilitarDadosPai = habilitarDadosPai;
	}

	public List<Filtroorcamentoproduto> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public Dadospais getDadosPais() {
		return dadosPais;
	}

	public void setDadosPais(Dadospais dadosPais) {
		this.dadosPais = dadosPais;
	}

	public Fornecedorcidade getFornecedorSeguro() {
		return fornecedorSeguro;
	}

	public void setFornecedorSeguro(Fornecedorcidade fornecedorSeguro) {
		this.fornecedorSeguro = fornecedorSeguro;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Seguroviagem getSeguroViagem() {
		return seguroViagem;
	}

	public void setSeguroViagem(Seguroviagem seguroViagem) {
		this.seguroViagem = seguroViagem;
	}

	public Seguroviagem getSeguroViagemAlterado() {
		return seguroViagemAlterado;
	}

	public void setSeguroViagemAlterado(Seguroviagem seguroViagemAlterado) {
		this.seguroViagemAlterado = seguroViagemAlterado;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getCambioAlterado() {
		return cambioAlterado;
	}

	public void setCambioAlterado(String cambioAlterado) {
		this.cambioAlterado = cambioAlterado;
	}

	public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
	}

	public float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
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

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public boolean isConsultaCambio() {
		return consultaCambio;
	}

	public void setConsultaCambio(boolean consultaCambio) {
		this.consultaCambio = consultaCambio;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public Fornecedorcidade getFornecedorCidadeAlterado() {
		return fornecedorCidadeAlterado;
	}

	public void setFornecedorCidadeAlterado(Fornecedorcidade fornecedorCidadeAlterado) {
		this.fornecedorCidadeAlterado = fornecedorCidadeAlterado;
	}

	public Date getDataCambio() {
		return dataCambio;
	}

	public void setDataCambio(Date dataCambio) {
		this.dataCambio = dataCambio;
	}

	public boolean isNovaFicha() {
		return novaFicha;
	}

	public void setNovaFicha(boolean novaFicha) {
		this.novaFicha = novaFicha;
	}

	public String getDadosAlterado() {
		return dadosAlterado;
	}

	public void setDadosAlterado(String dadosAlterado) {
		this.dadosAlterado = dadosAlterado;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public float getValorVendaAlterar() {
		return valorVendaAlterar;
	}

	public void setValorVendaAlterar(float valorVendaAlterar) {
		this.valorVendaAlterar = valorVendaAlterar;
	}

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoAntiga() {
		return listaParcelamentoPagamentoAntiga;
	}

	public void setListaParcelamentoPagamentoAntiga(List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga) {
		this.listaParcelamentoPagamentoAntiga = listaParcelamentoPagamentoAntiga;
	}

	public Fornecedorcomissaocurso getFornecedorComissao() {
		return fornecedorComissao;
	}

	public void setFornecedorComissao(Fornecedorcomissaocurso fornecedorComissao) {
		this.fornecedorComissao = fornecedorComissao;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Curso getCursoAlterado() {
		return cursoAlterado;
	}

	public void setCursoAlterado(Curso cursoAlterado) {
		this.cursoAlterado = cursoAlterado;
	}

	public Boolean getCheckBoxSeguroViagem() {
		return CheckBoxSeguroViagem;
	}

	public void setCheckBoxSeguroViagem(Boolean checkBoxSeguroViagem) {
		CheckBoxSeguroViagem = checkBoxSeguroViagem;
	}

	public Pais getPais() {
		return pais;
	}

	public boolean isClienteselecionado() {
		return clienteselecionado;
	}

	public void setClienteselecionado(boolean clienteselecionado) {
		this.clienteselecionado = clienteselecionado;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getCamposSeguroGovernamental() {
		return camposSeguroGovernamental;
	}

	public void setCamposSeguroGovernamental(String camposSeguroGovernamental) {
		this.camposSeguroGovernamental = camposSeguroGovernamental;
	}

	public Boolean getVendaMatriz() {
		return vendaMatriz;
	}

	public void setVendaMatriz(Boolean vendaMatriz) {
		this.vendaMatriz = vendaMatriz;
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

	public List<Fornecedorcidade> getListaFornecedorCidadeSeguro() {
		return listaFornecedorCidadeSeguro;
	}

	public void setListaFornecedorCidadeSeguro(List<Fornecedorcidade> listaFornecedorCidadeSeguro) {
		this.listaFornecedorCidadeSeguro = listaFornecedorCidadeSeguro;
	}

	public Boolean getCheckBoxSegundoCurso() {
		return CheckBoxSegundoCurso;
	}

	public void setCheckBoxSegundoCurso(Boolean checkBoxSegundoCurso) {
		CheckBoxSegundoCurso = checkBoxSegundoCurso;
	}

	public String getCamposSeguroViagem() {
		return camposSeguroViagem;
	}

	public void setCamposSeguroViagem(String camposSeguroViagem) {
		this.camposSeguroViagem = camposSeguroViagem;
	}

	public String getCamposAcomodacao() {
		return camposAcomodacao;
	}

	public void setCamposAcomodacao(String camposAcomodacao) {
		this.camposAcomodacao = camposAcomodacao;
	}

	public String getCamposCartaoVtm() {
		return camposCartaoVtm;
	}

	public void setCamposCartaoVtm(String camposCartaoVtm) {
		this.camposCartaoVtm = camposCartaoVtm;
	}

	public String getCamposAcomodacaoCasaFamilia() {
		return camposAcomodacaoCasaFamilia;
	}

	public void setCamposAcomodacaoCasaFamilia(String camposAcomodacaoCasaFamilia) {
		this.camposAcomodacaoCasaFamilia = camposAcomodacaoCasaFamilia;
	}

	public String getCamposSegundoCurso() {
		return camposSegundoCurso;
	}

	public void setCamposSegundoCurso(String camposSegundoCurso) {
		this.camposSegundoCurso = camposSegundoCurso;
	}

	public List<Valoresseguro> getListaValoresSeguro() {
		return listaValoresSeguro;
	}

	public void setListaValoresSeguro(List<Valoresseguro> listaValoresSeguro) {
		this.listaValoresSeguro = listaValoresSeguro;
	}

	public String getEditarEscola() {
		return editarEscola;
	}

	public void setEditarEscola(String editarEscola) {
		this.editarEscola = editarEscola;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public List<String> getListaTipoParcelamento() {
		return listaTipoParcelamento;
	}

	public void setListaTipoParcelamento(List<String> listaTipoParcelamento) {
		this.listaTipoParcelamento = listaTipoParcelamento;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public boolean isDigitosTelefoneContatoEmergencia() {
		return digitosTelefoneContatoEmergencia;
	}

	public void setDigitosTelefoneContatoEmergencia(boolean digitosTelefoneContatoEmergencia) {
		this.digitosTelefoneContatoEmergencia = digitosTelefoneContatoEmergencia;
	}

	public boolean isDigitosTelefonePai() {
		return digitosTelefonePai;
	}

	public void setDigitosTelefonePai(boolean digitosTelefonePai) {
		this.digitosTelefonePai = digitosTelefonePai;
	}

	public boolean isDigitosTelefoneMae() {
		return digitosTelefoneMae;
	}

	public void setDigitosTelefoneMae(boolean digitosTelefoneMae) {
		this.digitosTelefoneMae = digitosTelefoneMae;
	}

	public DashBoardMB getDashBoardMB() {
		return dashBoardMB;
	}

	public void setDashBoardMB(DashBoardMB dashBoardMB) {
		this.dashBoardMB = dashBoardMB;
	}

	public MateRunnersMB getMetaRunnersMB() {
		return metaRunnersMB;
	}

	public void setMetaRunnersMB(MateRunnersMB metaRunnersMB) {
		this.metaRunnersMB = metaRunnersMB;
	}

	public Controlealteracoes getControlealteracoes() {
		return controlealteracoes;
	}

	public void setControlealteracoes(Controlealteracoes controlealteracoes) {
		this.controlealteracoes = controlealteracoes;
	}

	public Departamentoproduto getDepPrograma() {
		return depPrograma;
	}

	public void setDepPrograma(Departamentoproduto depPrograma) {
		this.depPrograma = depPrograma;
	}

	public Departamento getDepFinanceiro() {
		return depFinanceiro;
	}

	public void setDepFinanceiro(Departamento depFinanceiro) {
		this.depFinanceiro = depFinanceiro;
	}

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoOriginal() {
		return listaParcelamentoPagamentoOriginal;
	}

	public void setListaParcelamentoPagamentoOriginal(List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal) {
		this.listaParcelamentoPagamentoOriginal = listaParcelamentoPagamentoOriginal;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	public Cursospacote getCursospacote() {
		return cursospacote;
	}

	public void setCursospacote(Cursospacote cursospacote) {
		this.cursospacote = cursospacote;
	}
	
	

	public boolean isDesabilitarSeguro() {
		return desabilitarSeguro;
	}

	public void setDesabilitarSeguro(boolean desabilitarSeguro) {
		this.desabilitarSeguro = desabilitarSeguro;
	}

	public List<Seguroplanos> getListaSeguroPlanos() {
		return listaSeguroPlanos;
	}

	public void setListaSeguroPlanos(List<Seguroplanos> listaSeguroPlanos) {
		this.listaSeguroPlanos = listaSeguroPlanos;
	}

	public Seguroplanos getSeguroplanos() {
		return seguroplanos;
	}

	public void setSeguroplanos(Seguroplanos seguroplanos) {
		this.seguroplanos = seguroplanos;
	}

	public boolean isDesabilitarAlergiaAlimento() {
		return desabilitarAlergiaAlimento;
	}

	public void setDesabilitarAlergiaAlimento(boolean desabilitarAlergiaAlimento) {
		this.desabilitarAlergiaAlimento = desabilitarAlergiaAlimento;
	}

	public boolean isHabilitarAvisoCambio() {
		return habilitarAvisoCambio;
	}

	public void setHabilitarAvisoCambio(boolean habilitarAvisoCambio) {
		this.habilitarAvisoCambio = habilitarAvisoCambio;
	}

	public void carregarComboMoedas() {
		CambioFacade cambioFacade = new CambioFacade();
		listaMoedas = cambioFacade.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	}

	public void carregarCursoAlteracao() {
		cursoAlterado = new Curso();
		cursoAlterado.setAdicionais(curso.getAdicionais());
		cursoAlterado.setAulassemana(curso.getAulassemana());
		cursoAlterado.setCaratoVTM(curso.getCaratoVTM());
		cursoAlterado.setCidade(curso.getCidade());
		cursoAlterado.setControle(curso.getControle());
		cursoAlterado.setDataChegada(curso.getDataChegada());
		cursoAlterado.setDataEntregaDocumentosVistos(curso.getDataEntregaDocumentosVistos());
		cursoAlterado.setDataInicio(curso.getDataInicio());
		cursoAlterado.setDataInscricao(curso.getDataInscricao());
		cursoAlterado.setDataSaida(curso.getDataSaida());
		cursoAlterado.setDataTermino(curso.getDataTermino());
		cursoAlterado.setEmalContatoEmergencia(curso.getEmalContatoEmergencia());
		cursoAlterado.setEscola(curso.getEscola());
		cursoAlterado.setFamiliacomAnimais(curso.getFamiliacomAnimais());
		cursoAlterado.setFamiliacomCrianca(curso.getFamiliacomCrianca());
		cursoAlterado.setFoneContatoEmergencia(curso.getFoneContatoEmergencia());
		cursoAlterado.setFumante(curso.getFumante());
		cursoAlterado.setHabilitarSegundoCurso(curso.getHabilitarSegundoCurso());
		cursoAlterado.setHobbies(curso.getHobbies());
		cursoAlterado.setIdcursos(curso.getIdcursos());
		cursoAlterado.setIdioma(curso.getIdioma());
		cursoAlterado.setIndiomaEstudar(curso.getIndiomaEstudar());
		cursoAlterado.setMoedaCartaoVTM(curso.getMoedaCartaoVTM());
		cursoAlterado.setNivelIdioma(curso.getNivelIdioma());
		cursoAlterado.setNivelIdiomaEstudar(curso.getNivelIdiomaEstudar());
		cursoAlterado.setNomeContatoEmergencia(curso.getNomeContatoEmergencia());
		cursoAlterado.setNomeCurso(curso.getNomeCurso());
		cursoAlterado.setNumeroCartaoVTM(curso.getNumeroCartaoVTM());
		cursoAlterado.setNumeroMeses(curso.getNumeroMeses());
		cursoAlterado.setNumeroSemanasAcamodacao(curso.getNumeroSemanasAcamodacao());
		cursoAlterado.setNumeroSenamas(curso.getNumeroSenamas());
		cursoAlterado.setObservacaoPassagemAerea(curso.getObservacaoPassagemAerea());
		cursoAlterado.setObservacaoVisto(curso.getObservacaoVisto());
		cursoAlterado.setPais(curso.getPais());
		cursoAlterado.setPassagemAerea(curso.getPassagemAerea());
		cursoAlterado.setPossuiAlergia(curso.getPossuiAlergia());
		cursoAlterado.setPossuiSeguroGovernamental(curso.getPossuiSeguroGovernamental());
		cursoAlterado.setQuaisAlergias(curso.getQuaisAlergias());
		cursoAlterado.setRefeicoes(curso.getRefeicoes());
		cursoAlterado.setRelacaoContatoEmergencia(curso.getRelacaoContatoEmergencia());
		cursoAlterado.setSeguradoraGovernamental(curso.getSeguradoraGovernamental());
		cursoAlterado.setSolicitacoesEspeciais(curso.getSolicitacoesEspeciais());
		cursoAlterado.setTipoAcomodacao(curso.getTipoAcomodacao());
		cursoAlterado.setTipoDuracao(curso.getTipoDuracao());
		cursoAlterado.setTipoQuarto(curso.getTipoQuarto());
		cursoAlterado.setTransferin(curso.getTransferin());
		cursoAlterado.setTransferouto(curso.getTransferouto());
		cursoAlterado.setSTipoCargaHoraria(curso.getSTipoCargaHoraria());
		cursoAlterado.setValorSeguroGovernamental(curso.getValorSeguroGovernamental());
		cursoAlterado.setVegetariano(curso.getVegetariano());
		cursoAlterado.setVendas(curso.getVendas());
		cursoAlterado.setVistoConsular(curso.getVistoConsular());
		cursoAlterado.setSCargaHoraria(curso.getSCargaHoraria());
		cursoAlterado.setSCurso(curso.getSCurso());
		cursoAlterado.setSDataInicio(curso.getSDataInicio());
		cursoAlterado.setSDataTermino(curso.getSDataTermino());
		cursoAlterado.setSNumeroSemanas(curso.getSNumeroSemanas());
		cursoAlterado.setIdorcamento(curso.getIdorcamento());
	}

	public void carregarSeguro() {
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			CheckBoxSeguroViagem = true;
			fornecedorSeguro = seguroViagem.getValoresseguro().getFornecedorcidade();
		} else {
			CheckBoxSeguroViagem = false;
		}
	}

	public void carregarCamposFormaPagamento() {
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			calcularParcelamentoPagamento();
		}
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
		cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda);
		valorCambio = cambio.getValor();
		atualizarValoresProduto();
	}

	public void adicionarProdutos() {
		if (valorCambio > 0) {
			int idProdTx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
			int idProdTransfer = 4;
			int idProdSeguroObrigatorio = 242;
			int idProdSeguroGov = 21;
			int idProdSeguroOSHC = 85;
			boolean adicionar = true;
			if (produtosorcamento != null) {
				if (produtosorcamento.getIdprodutosOrcamento() == idProdTransfer) {
					if(curso.getTransferin().equalsIgnoreCase("Não") &&
							curso.getTransferouto().equalsIgnoreCase("Não")){
						adicionar = false;
						Mensagem.lancarMensagemErro("Atenção!", "Você deverá primeiro preenche as informações sobre o transfer em 'VTM/Transfer/Passagem/Visto'.");
					}
				}else if (produtosorcamento.getIdprodutosOrcamento() == idProdSeguroObrigatorio
						|| produtosorcamento.getIdprodutosOrcamento() == idProdSeguroGov ||
						produtosorcamento.getIdprodutosOrcamento() == idProdSeguroOSHC) {
					if(curso.getPossuiSeguroGovernamental()==null
							|| curso.getPossuiSeguroGovernamental().length()==0
							|| curso.getPossuiSeguroGovernamental().equalsIgnoreCase("Não")){
						adicionar = false;
						Mensagem.lancarMensagemErro("Atenção!", "Você deverá primeiro preenche as informações sobre o seguro obrigatorio em 'Seguro/Contato'.");
					}
				}
				if(adicionar){
					if (produtosorcamento.getIdprodutosOrcamento() != idProdTx) {
						if (produtosorcamento != null) {
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
							if (produtosorcamento.getValormaximo()==0) {
								orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
								orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
								orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
								calcularValorTotalOrcamento();
							}else if (produtosorcamento.getValormaximo()>=valorMoedaReal){
								orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
								orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
								orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
								calcularValorTotalOrcamento();
							}else {
								FacesContext fc = FacesContext.getCurrentInstance();
						        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
						        Map<String, Object> options = new HashMap<String, Object>();
								options.put("contentWidth", 230);
						        session.setAttribute("valorOriginal", 0f);
						        session.setAttribute("novoValor", 0f);
								RequestContext.getCurrentInstance().openDialog("validarTrocaCambioPIN", options, null);
								//Mensagem.lancarMensagemErro("", "Valor máximo permitido R$ "+ Formatacao.formatarFloatString(produtosorcamento.getValormaximo()));
								excluirDescontoTM = false;
							}  
							if (excluirDescontoTM) {
								if (produtosorcamento.getIdprodutosOrcamento() == 33) {
									Filtroorcamentoproduto filtro = null;
									for (int i = 0; i < listaProdutosOrcamento.size(); i++) {
										if (listaProdutosOrcamento.get(i).getProdutos().getIdprodutos()==aplicacaoMB.getParametrosprodutos().getCursos()) {
											if (listaProdutosOrcamento.get(i).getProdutosorcamento().getIdprodutosOrcamento() == 33) {
												filtro = listaProdutosOrcamento.get(i);
											}
										}
									}
									listaProdutosOrcamento.remove(filtro);
								}
							}
							produtosorcamento = null;
						}
					} else {
						FacesContext context = FacesContext.getCurrentInstance();
						context.addMessage(null, new FacesMessage("Taxa TM já esta inclusa", ""));
					}
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Produto não selecionado", ""));
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
				Filtroorcamentoproduto filtroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(aplicacaoMB.getParametrosprodutos().getCursos(), 33);
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
				orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
				orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
				calcularValorTotalOrcamento();
				if (filtroorcamentoproduto.getProdutosorcamento().getIdprodutosOrcamento() == 33) {
					Filtroorcamentoproduto filtro = null;
					for (int i = 0; i < listaProdutosOrcamento.size(); i++) {
						if (listaProdutosOrcamento.get(i).getProdutos().getIdprodutos()==aplicacaoMB.getParametrosprodutos().getCursos()) {
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
				if (idproduto == aplicacaoMB.getParametrosprodutos().getSeguroOrcamento()) {
					seguroViagem.setPossuiSeguro("Não");
					carregarCamposSeguroPrivado();
				}
				if (idproduto == aplicacaoMB.getParametrosprodutos().getVistoOrcamento()) {
					curso.setVistoConsular("clienteprovidencia");
				}
				if (idproduto == 33) {
					FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
					Filtroorcamentoproduto filtroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(aplicacaoMB.getParametrosprodutos().getCursos(), 33);
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
			if (venda.getIdvendas()!=null){
				if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
					contasReceberBean.apagarContasReceber(formaPagamento.getParcelamentopagamentoList().get(linha), venda.getIdvendas(), usuarioLogadoMB,
							formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
				}
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
		boolean horarioExcedido = false;
		gerarListaParcelamentoOriginal();
		String msg = validarFormaPagamento();
		if (msg.length() < 5) {
			int numeroParcelas = Integer.parseInt(this.numeroParcelas);
			float valorParcela = valorParcelar / numeroParcelas;
			if (formaPagamentoString.equalsIgnoreCase("Boleto")) {
				int numeroAdicionar = 0;
				int diaSemana = Formatacao.diaSemana(dataPrimeiroPagamento);
				String horaAtual = Formatacao.foramtarHoraString();
				String horaMaxima = "16:00:00";
				Time horatime = null;
				Time horaMaxTime = null;
				try {
					horatime = Formatacao.converterStringHora(horaAtual);
					horaMaxTime = Formatacao.converterStringHora(horaMaxima);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (horatime.after(horaMaxTime)) {
					numeroAdicionar = 1;
					horarioExcedido = true;
				}
	
				if (diaSemana == 1) {
					numeroAdicionar = 2;
					horarioExcedido = true;
				}else if(diaSemana == 7) {
					numeroAdicionar = 3;
					horarioExcedido = true;
				}else if(diaSemana == 6) {
					numeroAdicionar = 4;
					horarioExcedido = true;
				}
				if (horarioExcedido) {
					try {
						dataPrimeiroPagamento = Formatacao.SomarDiasDatas(dataPrimeiroPagamento, numeroAdicionar);
						Mensagem.lancarMensagemInfo("Primeira parcela efetuada para o próximo dia útil", "");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
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
				parcelamento.setVerificarParcelamento(Formatacao.calcularDataParcelamento(
						parcelamento.getDiaVencimento(), parcelamento.getNumeroParcelas(), curso.getDataInicio()));
			} else
				parcelamento.setVerificarParcelamento(false);
			if (parcelamento.isVerificarParcelamento()) {
				Mensagem.lancarMensagemWarn("Data Vencimento", "Data da ultima parcela dos "
						+ parcelamento.getFormaPagamento() + " é maior que data início do Curso");
			}

			if (venda.getIdvendas()!=null){
				if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
					ContasReceberBean contasReceberBean = new ContasReceberBean();
					parcelamento = contasReceberBean.gerarParcelasIndividuais(parcelamento, formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB, curso.getDataInicio());
				 }
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
		}else {
			if (formaPagamentoString.equalsIgnoreCase("Boleto")){
				try {
					msg = msg + Formatacao.validarDataBoleto(dataPrimeiroPagamento);
				} catch (Exception e) {
					e.printStackTrace();
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

	public String naoEnviarficha() throws SQLException {
		enviarFicha = false;
		if (confirmarFichaCurso()) {
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consCurso";
		}
		return "";
	}

	public String finalizarficha() throws SQLException {
		verificarRegraVisto();
		if (cliente.isLiberarficha()) {
			enviarFicha = true;
		} else {
			Mensagem.lancarMensagemFatal("Vistos",
					"Sua ficha ficará em com situação PROCESSO, até ser resolvido o prazo para emissão do Visto, junto ao Departamento de Curso");
			enviarFicha = false;
		}
		if (confirmarFichaCurso()) {
			return "consCurso";
		}
		return "";
	}

	public boolean confirmarFichaCurso() throws SQLException {
		boolean salvarOK = false;
		String msg = validarDados();
		String nsituacao = "";
		if (venda.getIdvendas() != null) {
			nsituacao = venda.getSituacao();
		}
		String msg2 = "";
		if (visualizarDadosPais) {
			msg2 = validarCamposDadosPais();
		}
		if ((msg.length() < 5) && (msg2.length() < 5)) {
			if (Formatacao.validarEmail(curso.getEmalContatoEmergencia())) {
				if (situacao.equalsIgnoreCase("PROCESSO")) {
					if (enviarFicha) {
						novaFicha = true;
					}
				} else {
					enviarFicha = true;
				}
				if (enviarFicha) {
					if ((nsituacao.equalsIgnoreCase("")) || (nsituacao.equalsIgnoreCase("PROCESSO"))) {
						boolean verificaParcelamento = false;
						verificaParcelamento = Formatacao
								.veririfcarParcelamento(formaPagamento.getParcelamentopagamentoList());
						venda.setRestricaoparcelamento(verificaParcelamento);
						if (verificaParcelamento) {
							Mensagem.lancarMensagemWarn("Data Vencimento",
									"As parcelas possuem data de vencimento após o inicio do programa. Entrar em contato com Financeiro");
						} 
						
					}
				} else {
					if (nsituacao.equalsIgnoreCase("")) {
						nsituacao = "PROCESSO";
					}
				}
				if (venda.getIdvendas() == null) {
					nsituacao = "PROCESSO";
				}
				ProgramasBean programasBean = new ProgramasBean();
				this.produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getCursos());

				venda = programasBean.salvarVendas(venda, usuarioLogadoMB, nsituacao, cliente, totalPagar, produto,
						fornecedorCidade, cambio, orcamento.getValorCambio(), lead, curso.getDataInicio(), curso.getDataTermino());
				CadCursoBean cadCursoBean = new CadCursoBean(venda, formaPagamento, orcamento, usuarioLogadoMB);
				if (enviarFicha && !novaFicha) {
					cadCursoBean.SalvarAlteracaoFinanceiro(listaParcelamentoPagamentoAntiga, listaParcelamentoPagamentoOriginal, venda.getUsuario());
				}
				if (visualizarDadosPais) {
					cadCursoBean.salvarDadosPais(dadosPais);
				}
				curso.setPais(pais.getNome());
				curso.setCidade(cidade.getNome());
				curso.setEscola(fornecedorCidade.getFornecedor().getNome());
				curso = cadCursoBean.salvarCurso(curso, vendaAlterada, CheckBoxSegundoCurso);
				this.orcamento = cadCursoBean.salvarOrcamento(cambio, totalMoedaReal, totalMoedaEstrangeira, valorCambio,
						cambioAlterado);
				formaPagamento = cadCursoBean.salvarFormaPagamento(cancelamento);  
				salvarSeguroViagem();
				curso.setVendas(venda);
				cadCursoBean.pegarCurso(curso, venda);
				cliente = cadCursoBean.salvarCliente(cliente);
				if(cursospacote!=null && cursospacote.getIdcursospacote()!=null){
					VendasPacoteFacade vendasPacoteFacade = new VendasPacoteFacade();
					Vendaspacote vendaspacote = new Vendaspacote();
					vendaspacote.setCursospacote(cursospacote);
					vendaspacote.setVendas(venda);
					vendaspacote = vendasPacoteFacade.salvar(vendaspacote);  
				}
				
				if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO"))  {
					int mes = Formatacao.getMesData(new Date()) + 1;
					int mesVenda = Formatacao.getMesData(venda.getDataVenda()) + 1;
						if (enviarFicha) {
							if (mes == mesVenda) {
								dashBoardMB.getMetamensal()
										.setValoralcancado((dashBoardMB.getMetamensal().getValoralcancado()
												- valorVendaAlterar) + venda.getValor());
								dashBoardMB.getMetamensal()
										.setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
												/ dashBoardMB.getMetamensal().getValormeta()) * 100);
								dashBoardMB.getMetaAnual().setMetaalcancada((dashBoardMB.getMetaAnual().getMetaalcancada()
										- valorVendaAlterar) + venda.getValor());
								dashBoardMB.getMetaAnual()
										.setPercentualalcancado((dashBoardMB.getMetaAnual().getMetaalcancada()
												/ dashBoardMB.getMetaAnual().getValormeta()) * 100);
								dashBoardMB.setMetaparcialsemana(
										dashBoardMB.getMetaparcialsemana() - valorVendaAlterar + venda.getValor());
								dashBoardMB.setPercsemana((dashBoardMB.getMetaparcialsemana()
										/ dashBoardMB.getMetamensal().getValormetasemana()) * 100);
								float valor = dashBoardMB.getMetamensal().getValoralcancado();
								dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));
								DashBoardBean dashBoardBean = new DashBoardBean();
								dashBoardBean.calcularMetaMensal(venda, valorVendaAlterar, false);
								dashBoardBean.calcularMetaAnual(venda, valorVendaAlterar, false);
								int[] pontos = dashBoardBean.calcularPontuacao(venda, curso.getNumeroSenamas(), "", false, venda.getUsuario());
								int pontosremover = vendaAlterada.getPonto();
								productRunnersMB.calcularPontuacao(venda, pontos[0], pontosremover, false, venda.getUsuario());
								venda.setPonto(pontos[0]);
								venda.setPontoescola(pontos[1]);
								VendasFacade vendasFacade = new VendasFacade();
								venda = vendasFacade.salvar(venda);
								metaRunnersMB.carregarListaRunners();
							}
							String titulo = "";
							String operacao = "";
							String imagemNotificacao = "";
							if (novaFicha) {
								titulo = "Nova Ficha de Curso. " + venda.getIdvendas();
								operacao = "I";
								imagemNotificacao = "inserido";
							} else {
								titulo = "Ficha de Curso Alterada. " + venda.getIdvendas();
								operacao = "A";
								imagemNotificacao = "alterado";
								verificarDadosAlterado();
							}
							verificarAlteracaoCambio();
							String vm = "Venda pela Matriz";
							if (venda.getVendasMatriz().equalsIgnoreCase("N")) {
								vm = "Venda pela Loja";
							}
							if (venda.getSituacao().equalsIgnoreCase("FINALIZADA")) {
								Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(), cliente.getNome(),
										venda.getFornecedorcidade().getFornecedor().getNome(),
										Formatacao.ConvercaoDataPadrao(curso.getDataInicio()), venda.getUsuario().getNome(),
										vm, venda.getValor(), valorCambio, venda.getCambio().getMoedas().getSigla(),
										operacao, depPrograma.getDepartamento(), imagemNotificacao, "A");
							}
						}
				}
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Curso Salvo com Sucesso", ""));
				salvarOK = true;
			}
		} else {
			if (msg.length() > 2) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(msg, ""));
			} else if (msg2.length() > 2) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(msg2, ""));
			}
		}
		return salvarOK;
	}

	public String validarDados() {
		String msg = "";
		if (cliente.getTipovisto() == null || cliente.getTipovisto().equalsIgnoreCase("sn")) {
			msg = msg + "Tipo de visto não informado\r\n";
		}
		if (fornecedorSeguro != null) {
			seguroViagem.setSeguradora(fornecedorSeguro.getFornecedor().getNome());
		}
		if (fornecedorCidade == null) {
			msg = msg + "Campo escola não selecionado     ";
		}
		if (cambio == null) {
			msg = msg + "Selecione câmbio da ficha     ";
		}
		if (cliente == null) {
			msg = msg + "Campo cliente não selecionado\r\n";
		}
		if (curso.getNomeCurso() == null) {
			msg = msg + "Curso não informado\r\n";
		}
		if (fornecedorCidade == null) {
			msg = msg + "Escola/Instituição não informada\r\n";
		}
		if (pais == null) {
			msg = msg + "País não informado\r\n";
		}
		if (curso.getAulassemana() == null) {
			msg = msg + "Aulas por semana não informada\r\n";
		}
		if (curso.getNumeroSenamas() == null) {
			msg = msg + "Numero de semanas não informado\r\n";
		}
		if (curso.getDataInicio() == null) {
			msg = msg + "Data início inválida\r\n";
		}
		if (curso.getDataTermino() == null) {
			msg = msg + "Data términio inválida\r\n";
		}
		if (!curso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			if (curso.getDataChegada() == null) {
				msg = msg + "Data chegada na acomodação inválida\r\n";
			}
			if (curso.getDataSaida() == null) {
				msg = msg + "Data partida acomodação inválida\r\n";
			}
			if (curso.getNumeroSemanasAcamodacao() == null) {
				msg = msg + "Numero de semanas de acomodação não informado\r\n";
			}
		}
		if (curso.getCaratoVTM().equalsIgnoreCase("Sim")) {
			if (curso.getNumeroCartaoVTM() == null) {
				msg = msg + "Nº Cartão VTM não informado\r\n";
			}
		}
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			if (seguroViagem.getSeguradora() == null) {
				msg = msg + "Seguradora não informada\r\n";
			}
			if (seguroViagem.getValorSeguro() == 0) {
				msg = msg + "Valor do seguro não informado\r\n";
			}
			if (seguroViagem.getValoresseguro() == null) {
				msg = msg + "Plano do seguro não informado\r\n";
			}
			if (seguroViagem.getDataInicio() == null) {
				msg = msg + "Data início seguro inválida\r\n";
			}
			if (seguroViagem.getDataTermino() == null) {
				msg = msg + "Data término seguro inválida\r\n";
			}
			if (this.fornecedorCidade == null) {
				msg = msg + "Escola inválida\r\n";
			}
		}
		if (curso.getNomeContatoEmergencia() == null || curso.getNomeContatoEmergencia().length() <= 0) {
			msg = msg + "Nome do contato de emergência não informado\r\n";
		}
		if (curso.getFoneContatoEmergencia() == null || curso.getFoneContatoEmergencia().length() <= 0) {
			msg = msg + "Nº telefone  do contato de emergência não informado\r\n";
		}
		if (curso.getRelacaoContatoEmergencia() == null  || curso.getRelacaoContatoEmergencia().length() <= 0) {
			msg = msg + "Relação do contato de emergência não informado\r\n";
		}
		if (formaPagamento.getParcelamentopagamentoList() == null) {
			msg = msg + "Forma de Pagamento com erro\r\n";
		} else {
			if (formaPagamento.getParcelamentopagamentoList().size() == 0) {
				msg = msg + "Forma de Pagamento com erro\r\n";
			}
		}

		double saldoParcelar = this.valorParcelar;
		if (saldoParcelar > 0) {
			msg = msg + "Forma de Pagamento possui saldo a parcelar em aberto\r\n";
		}
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			if (seguroViagem.getNumeroSemanas() == 0) {
				msg = msg + "Nº de semanas no seguro é obrigatório\r\n";
			}
		}
		if (cliente.getTipovisto() == null) {
			msg = msg + "Tipo de visto é obrigatório\r\n";
		} else if (cliente.getTipovisto().length() <= 0) {
			msg = msg + "Tipo de visto é obrigatório\r\n";
		}
		return msg;
	}

	public String validarCamposDadosPais() {
		String msg = "";
		if (visualizarDadosPais) {
			if (dadosPais.getBairromae() == null || dadosPais.getBairromae().length() == 0) {
				msg = msg + "Bairro mãe não informado     ";
			}
			if (dadosPais.getCepmae() == null || dadosPais.getCepmae().length() == 0) {
				msg = msg + "CEP mãe não informado\r\n";
			}
			if (dadosPais.getCidademae() == null || dadosPais.getCidademae().length() == 0) {
				msg = msg + "Cidade mãe não informada\r\n";
			}
			if (dadosPais.getDatanascimentomae() == null) {
				msg = msg + "Data nascimento mãe não informada\r\n";
			}
			if (dadosPais.getEmailmae() == null || dadosPais.getEmailmae().length() == 0) {
				msg = msg + "Email mãe não informado\r\n";
			}
			if (dadosPais.getEstadomae() == null || dadosPais.getEstadomae().length() == 0) {
				msg = msg + "Estado mãe não informado\r\n";
			}
			if (dadosPais.getLogradouromae() == null || dadosPais.getLogradouromae().length() == 0) {
				msg = msg + "Logradouro mãe não informado\r\n";
			}
			if (dadosPais.getNomemae() == null || dadosPais.getNomemae().length() == 0) {
				msg = msg + "Nome mãe não informado\r\n";
			}
			if (dadosPais.getNumerromae() == null || dadosPais.getNumerromae().length() == 0) {
				msg = msg + "Número mãe não informado\r\n";
			}
			if (dadosPais.getTelefonemae() == null || dadosPais.getTelefonemae().length() == 0) {
				msg = msg + "Telefone mãe não informado\r\n";
			}
			if (dadosPais.getTipologradouromae() == null || dadosPais.getTipologradouromae().length() == 0) {
				msg = msg + "Tipo logradouro mãe não informado\r\n";
			}
			if (!dadosPais.getNomepai().equalsIgnoreCase("Desconhecido")) {
				if (dadosPais.getBairropai() == null || dadosPais.getBairropai().length() == 0) {
					msg = msg + "Bairro mãe não informado\r\n";
				}
				if (dadosPais.getCeppai() == null || dadosPais.getCeppai().length() == 0) {
					msg = msg + "CEP pai não informado\r\n";
				}
				if (dadosPais.getCidadepai() == null || dadosPais.getCidadepai().length() == 0) {
					msg = msg + "Cidade pai não informada\r\n";
				}
				if (dadosPais.getDatanascimentopai() == null) {
					msg = msg + "Data nascimento pai não informada\r\n";
				}
				if (dadosPais.getEmailpai() == null || dadosPais.getEmailpai().length() == 0) {
					msg = msg + "Email pai não informado\r\n";
				}
				if (dadosPais.getEstadopai() == null || dadosPais.getEstadopai().length() == 0) {
					msg = msg + "Estado pai não informado\r\n";
				}
				if (dadosPais.getLogradouropai() == null || dadosPais.getLogradouropai().length() == 0) {
					msg = msg + "Logradouro pai não informado\r\n";
				}
				if (dadosPais.getNomepai() == null || dadosPais.getNomepai().length() == 0) {
					msg = msg + "Nome pai não informado\r\n";
				}
				if (dadosPais.getNumeropai() == null || dadosPais.getNumeropai().length() == 0) {
					msg = msg + "Número pai não informado\r\n";
				}
				if (dadosPais.getTelefonepai() == null || dadosPais.getTelefonepai().length() == 0) {
					msg = msg + "Telefone pai não informado\r\n";
				}
				if (dadosPais.getTipologradouropai() == null || dadosPais.getTipologradouropai().length() == 0) {
					msg = msg + "Tipo logradouro pai não informado\r\n";
				}
			}
		}
		return msg;
	}

	public void salvarSeguroViagem() {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		if (seguroViagem.getIdseguroViagem() == null) {
			seguroViagem.setControle("Curso");
		}
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			seguroViagem.setFornecedor(seguroViagem.getValoresseguro().getFornecedorcidade().getFornecedor());
			seguroViagem.setPlano(seguroViagem.getValoresseguro().getPlano());
			seguroViagem.setVendas(salvarVendaSeguroViagem());
			seguroViagem.setPaisDestino(pais.getNome());
			seguroViagem.setNomeContatoEmergencia(curso.getNomeContatoEmergencia());
			seguroViagem.setFoneContatoEmergencia(curso.getFoneContatoEmergencia());
			if (seguroViagem.getIdseguroViagem() == null) {
				float valorJuros = 0.0f;
				if (venda.getFormapagamento() != null) {
					valorJuros = venda.getFormapagamento().getValorJuros();
				}
			}
			seguroViagem = seguroViagemFacade.salvar(seguroViagem);
			
			
		} else {
			if (seguroViagem.getIdvendacurso() > 0) {
				Vendas vendasSeguro = new Vendas();
				VendasFacade vendasFacade = new VendasFacade();
				vendasSeguro = seguroViagem.getVendas();
				vendasSeguro.setSituacao("CANCELADA");
				vendasSeguro.setVendascomissao(venda.getVendascomissao());
				vendasFacade.salvar(vendasSeguro);
			} else {
				seguroViagem.setVendas(venda);
			}
			seguroViagem.setVendas(venda);
			Valoresseguro valoresSeguro = new Valoresseguro();
			FornecedorFacade fornecedorFacade = new FornecedorFacade();
			Fornecedor fornecedor = new Fornecedor();
			fornecedor = fornecedorFacade.consultar(5);
			seguroViagem.setFornecedor(fornecedor);
			ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
			valoresSeguro = valorSeguroFacade.consultar(1);
			seguroViagem.setValoresseguro(valoresSeguro);
			seguroViagem.setIdvendacurso(0);
			seguroViagem = seguroViagemFacade.salvar(seguroViagem);
		}
	
	}

	public Vendas salvarVendaSeguroViagem() {
		Vendas vendaSeguro = null;
		if ((seguroViagem != null) && (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim"))) {
			if ((seguroViagem.getVendas() == null) || (seguroViagem.getIdvendacurso() == 0)) {
				vendaSeguro = new Vendas();
				vendaSeguro
						.setProdutos(ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getSeguroPrivado()));
				vendaSeguro.setCliente(venda.getCliente());
				vendaSeguro.setUnidadenegocio(venda.getUnidadenegocio());
				vendaSeguro.setUsuario(venda.getUsuario());
			} else {
				vendaSeguro = seguroViagem.getVendas();
			}
			vendaSeguro.setCambio(venda.getCambio());
			vendaSeguro.setFormapagamento(venda.getFormapagamento());
			vendaSeguro.setFornecedorcidade(seguroViagem.getValoresseguro().getFornecedorcidade());
			vendaSeguro.setFornecedor(venda.getFornecedorcidade().getFornecedor());
			vendaSeguro.setOrcamento(venda.getOrcamento());
			vendaSeguro.setSituacao(venda.getSituacao());
			vendaSeguro.setValor(seguroViagem.getValorSeguro());
			vendaSeguro.setDataVenda(venda.getDataVenda());
			vendaSeguro.setVendasMatriz(venda.getVendasMatriz());
			vendaSeguro.setSituacaogerencia("P");
			VendasFacade vendasFacade = new VendasFacade();
			vendaSeguro = vendasFacade.salvar(vendaSeguro);
			float novaValorVenda = venda.getValor() - seguroViagem.getValorSeguro();
			venda.setValor(novaValorVenda);
			venda = vendasFacade.salvar(venda);
			seguroViagem.setIdvendacurso(venda.getIdvendas());
			DashBoardBean dashBoardBean = new DashBoardBean();
			if (vendaSeguro.getSituacao().equalsIgnoreCase("FINALIZADA"))  {
				int mes = Formatacao.getMesData(new Date()) + 1;
				int mesVenda = Formatacao.getMesData(vendaSeguro.getDataVenda()) + 1;
					if (enviarFicha) {
						if (mes == mesVenda) {
							dashBoardMB.getMetamensal()
									.setValoralcancado((dashBoardMB.getMetamensal().getValoralcancado()
											- valorAlterarSeguro) + vendaSeguro.getValor());
							dashBoardMB.getMetamensal()
									.setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
											/ dashBoardMB.getMetamensal().getValormeta()) * 100);
							dashBoardMB.getMetaAnual().setMetaalcancada((dashBoardMB.getMetaAnual().getMetaalcancada()
									- valorAlterarSeguro) + vendaSeguro.getValor());
							dashBoardMB.getMetaAnual()
									.setPercentualalcancado((dashBoardMB.getMetaAnual().getMetaalcancada()
											/ dashBoardMB.getMetaAnual().getValormeta()) * 100);
							dashBoardMB.setMetaparcialsemana((
									dashBoardMB.getMetaparcialsemana() - valorAlterarSeguro) + vendaSeguro.getValor());
							dashBoardMB.setPercsemana((dashBoardMB.getMetaparcialsemana()
									/ dashBoardMB.getMetamensal().getValormetasemana()) * 100);
							float valor = dashBoardMB.getMetamensal().getValoralcancado();
							dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));
							dashBoardBean.calcularMetaMensal(vendaSeguro, valorAlterarSeguro, false);
							dashBoardBean.calcularMetaAnual(vendaSeguro, valorAlterarSeguro, false);
							int[] pontos = dashBoardBean.calcularPontuacao(vendaSeguro, 0, "", false, vendaSeguro.getUsuario());
							vendaSeguro.setPonto(pontos[0]);
							vendaSeguro.setPontoescola(pontos[1]);
							vendaSeguro = vendasFacade.salvar(vendaSeguro);
							//metaRunnersMB.carregarListaRunners();
							//productRunnersMB.calcularPontuacao(vendaSeguro, pontos[0], 0, false);
						}
						
					}
			}
		}
		return vendaSeguro;
	}

	public void verificarDadosAlterado() {
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA")) {
			ControleAlteracaoCursoBean controleAlteracaoCursoBean = new ControleAlteracaoCursoBean();
			if (curso.getIndiomaEstudar() != null) {
				if (!curso.getIndiomaEstudar().equalsIgnoreCase(cursoAlterado.getIndiomaEstudar())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Idioma que irá Estudar", curso.getIndiomaEstudar(),
							cursoAlterado.getIndiomaEstudar());
				}
			}
			if (curso.getNivelIdioma() != null) {
				if (!curso.getNivelIdioma().equalsIgnoreCase(cursoAlterado.getNivelIdioma())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Idioma Nível Conhecimento", curso.getNivelIdioma(),
							cursoAlterado.getNivelIdioma());
				}
			}

			if (fornecedorCidade.getIdfornecedorcidade() != fornecedorCidadeAlterado.getIdfornecedorcidade()) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Escola", fornecedorCidade.getFornecedor().getNome(),
						fornecedorCidadeAlterado.getFornecedor().getNome());
			}
			
			if (!curso.getCidade().equalsIgnoreCase(cursoAlterado.getCidade())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Cidade", curso.getCidade(),
						cursoAlterado.getCidade());
			}
			
			if (!curso.getPais().equalsIgnoreCase(cursoAlterado.getPais())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Pais", curso.getPais(),
						cursoAlterado.getPais());
			}
			
			if (curso.getSCurso() != null) {
				if (!curso.getSCurso().equalsIgnoreCase(cursoAlterado.getSCurso())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Segundo Curso", curso.getSCurso(),
							cursoAlterado.getSCurso());
				}
			}
			if (curso.getSDataInicio() != null) {
				if (!curso.getSDataInicio().equals(cursoAlterado.getSDataInicio())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Segunda Data Inicio",
							Formatacao.ConvercaoDataPadrao(curso.getSDataInicio()),
							Formatacao.ConvercaoDataPadrao(cursoAlterado.getSDataInicio()));
				}
			}
			if (curso.getSNumeroSemanas() != null) {
				if (curso.getSNumeroSemanas() != cursoAlterado.getSNumeroSemanas()) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Nº Semanas Curso 2",
							String.valueOf(curso.getSNumeroSemanas()),
							String.valueOf(cursoAlterado.getSNumeroSemanas()));
				}
			}
			if (curso.getSDataTermino() != null) {
				if (!curso.getSDataTermino().equals(cursoAlterado.getSDataTermino())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Segunda Data Término",
							Formatacao.ConvercaoDataPadrao(curso.getSDataTermino()),
							Formatacao.ConvercaoDataPadrao(cursoAlterado.getSDataTermino()));
				}
			}
			if (curso.getSTipoCargaHoraria() != null) {
				if (!curso.getSTipoCargaHoraria().equalsIgnoreCase(cursoAlterado.getSTipoCargaHoraria())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Tipo Carga Horaria 2º curso", curso.getSTipoCargaHoraria(),
							cursoAlterado.getSTipoCargaHoraria());

				}
			}
			if (curso.getSCargaHoraria() != null) {
				if (!curso.getSCargaHoraria().equals(cursoAlterado.getSCargaHoraria())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Carga Horaria 2º curso",
							String.valueOf(curso.getSCargaHoraria()), String.valueOf(cursoAlterado.getSCargaHoraria()));

				}
			}
			if (curso.getNomeCurso() != null) {
				if (!curso.getNomeCurso().equalsIgnoreCase(cursoAlterado.getNomeCurso())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Curso", curso.getNomeCurso(), cursoAlterado.getNomeCurso());
				}
			}
			if (curso.getDataInicio() != null) {
				if (!curso.getDataInicio().equals(cursoAlterado.getDataInicio())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Data Inicio",
							Formatacao.ConvercaoDataPadrao(curso.getDataInicio()),
							Formatacao.ConvercaoDataPadrao(cursoAlterado.getDataInicio()));
				}
			}
			if (curso.getNumeroSenamas() != null) {
				if (curso.getNumeroSenamas() != cursoAlterado.getNumeroSenamas()) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Nº Semanas Curso", String.valueOf(curso.getNumeroSenamas()),
							String.valueOf(cursoAlterado.getNumeroSenamas()));
				}
			}
			if (curso.getDataTermino() != null) {
				if (!curso.getDataTermino().equals(cursoAlterado.getDataTermino())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Data Término",
							Formatacao.ConvercaoDataPadrao(curso.getDataTermino()),
							Formatacao.ConvercaoDataPadrao(cursoAlterado.getDataTermino()));
				}
			}
			if (curso.getTipoDuracao() != null) {
				if (!curso.getTipoDuracao().equalsIgnoreCase(cursoAlterado.getTipoDuracao())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Tipo Carga Horaria", curso.getTipoDuracao(),
							cursoAlterado.getTipoDuracao());

				}
			}
			if (curso.getAulassemana() != null) {
				if (!curso.getAulassemana().equals(cursoAlterado.getAulassemana())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Carga Horaria", String.valueOf(curso.getAulassemana()),
							String.valueOf(cursoAlterado.getAulassemana()));

				}
			}
			if (curso.getTipoAcomodacao() != null) {
				if (!curso.getTipoAcomodacao().equalsIgnoreCase(cursoAlterado.getTipoAcomodacao())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Tipo Acomodação", curso.getTipoAcomodacao(),
							cursoAlterado.getTipoAcomodacao());
				}
			}
			if (curso.getNumeroSemanasAcamodacao() != null) {
				if (curso.getNumeroSemanasAcamodacao() != cursoAlterado.getNumeroSemanasAcamodacao()) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Nº Semanas Acomodação",
							String.valueOf(curso.getNumeroSemanasAcamodacao()),
							String.valueOf(cursoAlterado.getNumeroSemanasAcamodacao()));
				}
			}
			if (curso.getTipoQuarto() != null) {
				if (!curso.getTipoQuarto().equalsIgnoreCase(cursoAlterado.getTipoQuarto())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Quarto", curso.getTipoQuarto(),
							cursoAlterado.getTipoQuarto());
				}
			}
			if (curso.getRefeicoes() != null) {
				if (!curso.getRefeicoes().equalsIgnoreCase(cursoAlterado.getRefeicoes())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Refeição", curso.getRefeicoes(),
							cursoAlterado.getRefeicoes());
				}
			}
			if (curso.getBanheiroprivativo() != null) {
				if (!curso.getBanheiroprivativo().equalsIgnoreCase(cursoAlterado.getBanheiroprivativo())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Banheiro Privado", curso.getBanheiroprivativo(),
							cursoAlterado.getBanheiroprivativo());
				}
			}
			if (curso.getDataChegada() != null) {
				if (!curso.getDataChegada().equals(cursoAlterado.getDataChegada())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Data Chegada Acomodação",
							Formatacao.ConvercaoDataPadrao(curso.getDataChegada()),
							Formatacao.ConvercaoDataPadrao(cursoAlterado.getDataChegada()));

				}
			}
			if (curso.getDataSaida() != null) {
				if (!curso.getDataSaida().equals(cursoAlterado.getDataSaida())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Data Saída Acomodação",
							Formatacao.ConvercaoDataPadrao(curso.getDataSaida()),
							Formatacao.ConvercaoDataPadrao(cursoAlterado.getDataSaida()));
				}
			}
			if (curso.getAdicionais() != null) {
				if (!curso.getAdicionais().equalsIgnoreCase(cursoAlterado.getAdicionais())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Adicionais", curso.getAdicionais(),
							cursoAlterado.getAdicionais());
				}
			}
			if (curso.getFamiliacomCrianca() != null) {
				if (!curso.getFamiliacomCrianca().equalsIgnoreCase(cursoAlterado.getFamiliacomCrianca())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Prefere familia com criança", curso.getFamiliacomCrianca(),
							cursoAlterado.getFamiliacomCrianca());
				}
			}
			if (curso.getFamiliacomAnimais() != null) {
				if (!curso.getFamiliacomAnimais().equalsIgnoreCase(cursoAlterado.getFamiliacomAnimais())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Familia com animais de estimação",
							curso.getFamiliacomAnimais(), cursoAlterado.getFamiliacomAnimais());
				}
			}
			if (curso.getFumante() != null) {
				if (!curso.getFumante().equalsIgnoreCase(cursoAlterado.getFumante())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Você é fumante", curso.getFumante(),
							cursoAlterado.getFumante());
				}
			}
			if (curso.getVegetariano() != null) {
				if (!curso.getVegetariano().equalsIgnoreCase(cursoAlterado.getVegetariano())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Você é vegetariano", curso.getVegetariano(),
							cursoAlterado.getVegetariano());
				}
			}
			if (curso.getHobbies() != null) {
				if (!curso.getHobbies().equalsIgnoreCase(cursoAlterado.getHobbies())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Quais seus interesses e  hobbies", curso.getHobbies(),
							cursoAlterado.getHobbies());
				}
			}
			if (curso.getPossuiAlergia() != null) {
				if (!curso.getPossuiAlergia().equalsIgnoreCase(cursoAlterado.getPossuiAlergia())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(),
							"Tem alergia a algum medicamento ou alimento? Se sim, especifique",
							curso.getPossuiAlergia(), cursoAlterado.getPossuiAlergia());
				}
			}
			if (curso.getSolicitacoesEspeciais() != null) {
				if (!curso.getSolicitacoesEspeciais().equalsIgnoreCase(cursoAlterado.getSolicitacoesEspeciais())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Solicitações Especiais", curso.getSolicitacoesEspeciais(),
							cursoAlterado.getSolicitacoesEspeciais());
				}
			}
			if (curso.getTransferin() != null) {
				if (!curso.getTransferin().equalsIgnoreCase(cursoAlterado.getTransferin())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Transfer in (Chegada)", curso.getTransferin(),
							cursoAlterado.getTransferin());
				}
			}
			if (curso.getTransferouto() != null) {
				if (!curso.getTransferouto().equalsIgnoreCase(cursoAlterado.getTransferouto())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Transfer out (Partida)", curso.getTransferouto(),
							cursoAlterado.getTransferouto());
				}
			}
			if (curso.getCaratoVTM() != null) {
				if (!curso.getCaratoVTM().equalsIgnoreCase(cursoAlterado.getCaratoVTM())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Cartão VTM", curso.getCaratoVTM(),
							cursoAlterado.getCaratoVTM());
				}
			}
			if (curso.getMoedaCartaoVTM() != null) {
				if (!curso.getMoedaCartaoVTM().equalsIgnoreCase(cursoAlterado.getMoedaCartaoVTM())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Moeda Cartão VTM", curso.getMoedaCartaoVTM(),
							cursoAlterado.getMoedaCartaoVTM());
				}
			}
			if (curso.getNumeroCartaoVTM() != null) {
				if (!curso.getNumeroCartaoVTM().equalsIgnoreCase(cursoAlterado.getNumeroCartaoVTM())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Número Cartão VTM", curso.getNumeroCartaoVTM(),
							cursoAlterado.getNumeroCartaoVTM());
				}
			}
			if (curso.getPassagemAerea() != null) {
				if (!curso.getPassagemAerea().equalsIgnoreCase(cursoAlterado.getPassagemAerea())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Passagem Aérea", curso.getPassagemAerea(),
							cursoAlterado.getPassagemAerea());
				}
			}
			if (curso.getObservacaoPassagemAerea() != null) {
				if (!curso.getObservacaoPassagemAerea().equalsIgnoreCase(cursoAlterado.getObservacaoPassagemAerea())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Observação Passagem", curso.getObservacaoPassagemAerea(),
							cursoAlterado.getObservacaoPassagemAerea());
				}
			}
			if (!seguroViagem.getPossuiSeguro().equalsIgnoreCase(seguroViagemAlterado.getPossuiSeguro())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Seguro Viagem", seguroViagem.getPossuiSeguro(),
						seguroViagemAlterado.getPossuiSeguro());
			}

			if (seguroViagem.getSeguradora() != null) {
				if (!seguroViagem.getSeguradora().equalsIgnoreCase(seguroViagemAlterado.getSeguradora())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Seguradora", seguroViagem.getSeguradora(),
							seguroViagemAlterado.getSeguradora());
				}
			}
			if (seguroViagem.getValorSeguro() != null) {
				if (seguroViagem.getValorSeguro() != seguroViagemAlterado.getValorSeguro()) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Valor Seguro",
							Formatacao.formatarFloatString(seguroViagem.getValorSeguro()),
							Formatacao.formatarFloatString(seguroViagemAlterado.getValorSeguro()));
				}
			}
			if (seguroViagem.getPlano() != null) {
				if (!seguroViagem.getPlano().equalsIgnoreCase(seguroViagemAlterado.getPlano())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Plano Seguro", seguroViagem.getPlano(),
							seguroViagemAlterado.getPlano());
				}
			}
			if (seguroViagem.getDataInicio() != null) {
				if (!seguroViagem.getDataInicio().equals(seguroViagemAlterado.getDataInicio())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Data Inicio Seguro Viagem",
							Formatacao.ConvercaoDataPadrao(seguroViagem.getDataInicio()),
							Formatacao.ConvercaoDataPadrao(seguroViagemAlterado.getDataInicio()));
				}
			}
			if (seguroViagem.getNumeroSemanas() != null) {
				if (seguroViagem.getNumeroSemanas() != seguroViagemAlterado.getNumeroSemanas()) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "No. Semanas Seguro",
							String.valueOf(seguroViagem.getNumeroSemanas()),
							String.valueOf(seguroViagemAlterado.getNumeroSemanas()));
				}
			}
			if (seguroViagem.getDataTermino() != null) {
				if (!seguroViagem.getDataTermino().equals(seguroViagemAlterado.getDataTermino())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Data Término Seguro Viagem",
							Formatacao.ConvercaoDataPadrao(seguroViagem.getDataTermino()),
							Formatacao.ConvercaoDataPadrao(seguroViagemAlterado.getDataTermino()));
				}
			}
			if (curso.getVistoConsular() != null) {
				if (!curso.getVistoConsular().equalsIgnoreCase(cursoAlterado.getVistoConsular())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Visto Consular", curso.getVistoConsular(),
							cursoAlterado.getVistoConsular());
				}
			}
			if (curso.getDataEntregaDocumentosVistos() != null) {
				if (!curso.getDataEntregaDocumentosVistos().equals(cursoAlterado.getDataEntregaDocumentosVistos())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Data Entrega Documentos",
							Formatacao.ConvercaoDataPadrao(curso.getDataEntregaDocumentosVistos()),
							Formatacao.ConvercaoDataPadrao(cursoAlterado.getDataEntregaDocumentosVistos()));
				}
			}
			if (curso.getObservacaoVisto() != null) {
				if (!curso.getObservacaoVisto().equalsIgnoreCase(curso.getObservacaoVisto())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Observações Visto", curso.getObservacaoVisto(),
							cursoAlterado.getObservacaoVisto());
				}
			}
			if (valorVendaAlterar != venda.getValor()) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes, depFinanceiro, "Valor Total",
						Formatacao.formatarFloatString(venda.getValor()),
						Formatacao.formatarFloatString(valorVendaAlterar));
			}
			if (curso.getNomeContatoEmergencia() != null) {
				if (!curso.getNomeContatoEmergencia().equalsIgnoreCase(cursoAlterado.getNomeContatoEmergencia())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Nome Contato de Emergência",
							curso.getNomeContatoEmergencia(), cursoAlterado.getNomeContatoEmergencia());
				}
			}
			if (curso.getRelacaoContatoEmergencia() != null) {
				if (!curso.getRelacaoContatoEmergencia()
						.equalsIgnoreCase(cursoAlterado.getRelacaoContatoEmergencia())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Relação Contato de Emergência",
							curso.getRelacaoContatoEmergencia(), cursoAlterado.getRelacaoContatoEmergencia());
				}
			}
			if (curso.getFoneContatoEmergencia() != null) {
				if (!curso.getFoneContatoEmergencia().equalsIgnoreCase(cursoAlterado.getFoneContatoEmergencia())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Telefone Contato de Emergência",
							curso.getFoneContatoEmergencia(), cursoAlterado.getFoneContatoEmergencia());
				}
			}
			if (curso.getEmalContatoEmergencia() != null) {
				if (!curso.getEmalContatoEmergencia().equalsIgnoreCase(cursoAlterado.getEmalContatoEmergencia())) {
					controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
							depPrograma.getDepartamento(), "Email Contato de Emergência",
							curso.getEmalContatoEmergencia(), cursoAlterado.getEmalContatoEmergencia());
				}
			}
			if (!curso.getPossuiSeguroGovernamental().equalsIgnoreCase(cursoAlterado.getPossuiSeguroGovernamental())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Seguro Governamental", curso.getPossuiSeguroGovernamental(),
						cursoAlterado.getPossuiSeguroGovernamental());
			}
			if (!curso.getSeguradoraGovernamental().equalsIgnoreCase(cursoAlterado.getSeguradoraGovernamental())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Seguradora Governamental", curso.getSeguradoraGovernamental(),
						cursoAlterado.getSeguradoraGovernamental());
			}
			if (curso.getValorSeguroGovernamental() != (cursoAlterado.getValorSeguroGovernamental())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Valor Seguro Governamental",
						Formatacao.formatarFloatString(curso.getValorSeguroGovernamental()),
						Formatacao.formatarFloatString(cursoAlterado.getValorSeguroGovernamental()));
			}
			if (!curso.getVendas().getFormapagamento().getObservacoes().equalsIgnoreCase(cursoAlterado.getVendas().getFormapagamento().getObservacoes())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Obs",
						curso.getVendas().getFormapagamento().getObservacoes(),
						cursoAlterado.getVendas().getFormapagamento().getObservacoes());
			}
			if (curso.getNumeroMeses() != cursoAlterado.getNumeroMeses()) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "N° Meses Seguro",
						"" + curso.getNumeroMeses(),
						"" + cursoAlterado.getNumeroMeses());
			}
		}
	}

	public void verificarAlteracaoCambio() {
		if (cambioAlterado.equalsIgnoreCase("sim")) {
			Formatacao.VerificarCambioalterado(valorCambio, "Curso", cliente.getNome(),
					usuarioLogadoMB.getUsuario().getUnidadenegocio().getNomeFantasia(),
					usuarioLogadoMB.getUsuario().getNome(), this.venda.getProdutos().getIdprodutos(),
					Formatacao.formatarFloatString(this.venda.getValor()));
		}
	}

	public String cancelarCadastro() {
		if (voltarControleVendas != null) {
			if (voltarControleVendas.length() > 1) {
				return voltarControleVendas;
			}
		}
		return "consCurso";
	}

	public void listarFornecedorCidade() {
		if (cidade != null) {
			String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade() +
					" and f.ativo=1 order by f.fornecedor.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
	}

	public void iniciarListaFornecedorSeguro() { 
		int idProduto = (int) aplicacaoMB.getParametrosprodutos().getCartao01(); 
		listaFornecedorCidadeSeguro = GerarListas.listarFornecedorSeguro(idProduto);
	}
	 
	public void gerarListaCursos() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getCursos()
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void gerarListaCidade() {
		CidadeFacade cidadeFacade = new CidadeFacade();
		listaCidade = cidadeFacade.listar(pais.getIdpais());
		if (listaCidade == null) {
			listaCidade = new ArrayList<Cidade>();
		}
	}

	public void calcularDataTermino() {
		if (cliente != null) {
			int idadeCliente = Formatacao.calcularIdade(cliente.getDataNascimento());
			if ((idadeCliente < seguroplanos.getIdademinima()) || (idadeCliente>= seguroplanos.getIdademaxima()) ) {
					Mensagem.lancarMensagemInfo("Atenção",
							"emissão não permitida para cliente com " + idadeCliente + " anos");
			} else {
				if (seguroViagem.getNumeroSemanas()>0) {
					if (seguroViagem.getNumeroSemanas()< seguroplanos.getDiasemissaominima()) {
						Mensagem.lancarMensagemInfo("Atenção",
								"Mínimo e dias para emissão : " + seguroplanos.getDiasemissaominima() + " dias");
					}
				}
				dataTermino();
			}
		} else 	Mensagem.lancarMensagemErro("Atenção", "Cliente não selecionado");
	}

	public void dataTermino() {
		if ((seguroViagem.getDataInicio() != null) && (seguroViagem.getNumeroSemanas() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					seguroViagem.getValoresseguro().getMoedas().getIdmoedas());
			if (cambioSeguro != null) {
				seguroViagem.setDataTermino(Formatacao.calcularDataFinalPorDias(seguroViagem.getDataInicio(),
						seguroViagem.getNumeroSemanas()));
				float valornacional = seguroViagem.getValoresseguro().getValorgross() * cambioSeguro.getValor();
				if (seguroViagem.getValoresseguro().getCobranca().equalsIgnoreCase("Fixo")) {
					seguroViagem.setValorSeguro(valornacional);
				} else
					seguroViagem.setValorSeguro(valornacional * seguroViagem.getNumeroSemanas());
				calcularValorSeguroPrivadoListaProdutos();
			}
		}
	}

	public void dataTermino70anos() {
		if ((seguroViagem.getDataInicio() != null) && (seguroViagem.getNumeroSemanas() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					seguroViagem.getValoresseguro().getMoedas().getIdmoedas());
			if (cambioSeguro != null) {
				seguroViagem.setDataTermino(Formatacao.calcularDataFinalPorDias(seguroViagem.getDataInicio(),
						seguroViagem.getNumeroSemanas()));
				float valornacional = seguroViagem.getValoresseguro().getValorgross() * cambioSeguro.getValor();
				valornacional = (float) (valornacional * 1.5);
				if (seguroViagem.getValoresseguro().getCobranca().equalsIgnoreCase("Fixo")) {
					seguroViagem.setValorSeguro(valornacional);
				} else
					seguroViagem.setValorSeguro(valornacional * seguroViagem.getNumeroSemanas());
				calcularValorSeguroPrivadoListaProdutos();
			}
		}
	}

	public void calcularNumeroDiasSeguro() {
		if ((seguroViagem.getDataInicio() != null) && (seguroViagem.getDataTermino() != null)) {
			int numeroDias = Formatacao.subtrairDatas(seguroViagem.getDataInicio(), seguroViagem.getDataTermino());
			if (numeroDias < 0) {
				numeroDias = numeroDias * -1;
			}
			seguroViagem.setNumeroSemanas(numeroDias + 1);
			calcularDataTermino();
		}
	}

	public void carregarCobrancaSeguro() {
		seguroViagem.getValoresseguro().setCobranca(seguroViagem.getValoresseguro().getCobranca());
		verificarSeguroCancelamento();
	}

	public void calcularValorParcelas() {
		if (valorParcelar > 0) {
			int numero = Integer.parseInt(numeroParcelas);
			float vParcela = valorParcelar / numero;
			valorParcela = vParcela;
		}
	}

	/// HABILITAR E VALIDAR CAMPOS

	public void carregarCamposSegundoCurso() {
		if (CheckBoxSegundoCurso) {
			camposSegundoCurso = "false";
			habiltarSegundoCurso = true;
		} else {
			camposSegundoCurso = "true";
			habiltarSegundoCurso = false;
		}
	}

	public void carregarCamposAcomodacao() {
		if (curso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
			camposAcomodacaoCasaFamilia = "true";
			curso.setTipoQuarto("Sem quarto");
			curso.setRefeicoes("Sem Refeição");
			curso.setDataChegada(null);
			curso.setNumeroSemanasAcamodacao(null);
			curso.setDataSaida(null);
		} else {
			camposAcomodacao = "false";
			camposAcomodacaoCasaFamilia = "true";
		}
		if (curso.getTipoAcomodacao().equalsIgnoreCase("Casa de família")) {
			camposAcomodacaoCasaFamilia = "false";
			camposAcomodacao = "false";
		}
	}

	public void carregarCamposSeguroPrivado() {
			if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
				camposSeguroViagem = "false";
				if (venda.getIdvendas() != null) {
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						camposSeguroViagem = "true";
					}
				}
				if (seguroViagem.getValoresseguro() == null) {
					seguroViagem.setValoresseguro(new Valoresseguro());
				}
			} else {
				seguroViagem.setValorSeguro(0.0f);
				seguroViagem.setPossuiSeguro("Não");
				seguroViagem.setDataInicio(null);
				seguroViagem.setDataTermino(null);
				seguroViagem.setNumeroSemanas(0);
				seguroViagem.setPlano(" ");
				seguroViagem.setSeguradora("");
				seguroViagem.setValorSeguro(0.0f);
				seguroViagem.setFoneContatoEmergencia("");
				seguroViagem.setNomeContatoEmergencia("");
				seguroViagem.setPaisDestino("");
				camposSeguroViagem = "true";
				seguroViagem.setValoresseguro(null);
				fornecedorSeguro = null;
				if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
					int idseguroViagem = aplicacaoMB.getParametrosprodutos().getSeguroOrcamento();
					for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
						int idProdutoOrcamento = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
								.getIdprodutosOrcamento();
						if (idseguroViagem == idProdutoOrcamento) {
							if (orcamento.getOrcamentoprodutosorcamentoList().get(i)
									.getIdorcamentoProdutosOrcamento() != null) {
								OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
								orcamentoFacade.excluirOrcamentoProdutoOrcamento(orcamento
										.getOrcamentoprodutosorcamentoList().get(i).getIdorcamentoProdutosOrcamento());
							}
							orcamento.getOrcamentoprodutosorcamentoList().remove(i);
							calcularValorTotalOrcamento();
							calcularParcelamentoPagamento();
							i = 1000;
						}
					}
				}
			}
	}

	public void carregarCamposSeguroGovernamental() {
		if (curso.getPossuiSeguroGovernamental().equalsIgnoreCase("S")) {
			camposSeguroGovernamental = "false";
		} else {
			curso.setPossuiSeguroGovernamental("N");
			camposSeguroGovernamental = "true";
		}
	}

	public void calcularDataTerminoCurso() {
		if ((curso.getDataInicio() != null) && (curso.getNumeroSenamas() != null)) {
			if (curso.getNumeroSenamas() > 0) {
				Date data = Formatacao.calcularDataFinal(curso.getDataInicio(), curso.getNumeroSenamas());
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
					Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
							.log(Level.SEVERE, null, ex);
				}
				curso.setDataTermino(data);
			}
		}
	}

	public void calcularNumeroSemanasCurso() {
		if ((curso.getDataInicio() != null) && (curso.getDataTermino() != null)) {
			curso.setNumeroSenamas(Formatacao.calcularNumeroSemanas(curso.getDataInicio(), curso.getDataTermino()));
		}
	}

	public void calcularNumeroSemanasCurso2() {
		if ((curso.getSDataInicio() != null) && (curso.getSDataTermino() != null)) {
			curso.setSNumeroSemanas(Formatacao.calcularNumeroSemanas(curso.getSDataInicio(), curso.getSDataTermino()));
		}
	}

	public void calcularDataTerminoCurso2() {
		if ((curso.getSDataInicio() != null) && (curso.getSNumeroSemanas() != null)) {
			if (curso.getSNumeroSemanas() > 0) {
				Date data = Formatacao.calcularDataFinal(curso.getSDataInicio(), curso.getSNumeroSemanas());
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
					Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
							.log(Level.SEVERE, null, ex);
				}
				curso.setSDataTermino(data);
			}
		}
	}

	public void calcularDataTerminoAcomodacao() {
		if ((curso.getDataChegada() != null) && (curso.getNumeroSemanasAcamodacao() != null)) {
			if (curso.getNumeroSemanasAcamodacao() > 0) {
				int diaSemana = Formatacao.diaSemana(curso.getDataChegada()); 
				if(diaSemana!=1) {
					Mensagem.lancarMensagemInfo("Atenção!", "O sistema não irá calcular automaticamente"
							+ " as datas de chegada e partida para acomodações que não iniciam no Domingo.");
					curso.setDataSaida(null);
					curso.setNumeroSemanasAcamodacao(null);
				} else {
					Date data = Formatacao.calcularDataFinalAcomodacao(curso.getDataChegada(), curso.getNumeroSemanasAcamodacao());
					curso.setDataSaida(data);
				}
			}
		}
	}

	public void carregarCamposCartaoVTM() {
		if (curso.getCaratoVTM().equalsIgnoreCase("Sim")) {
			camposCartaoVtm = "false";
		} else {
			camposCartaoVtm = "true";
			curso.setNumeroCartaoVTM(null);
		}
	}

	public void iniciarNovoCurso() {
		if (cliente == null) {
			cliente = new Cliente();
		} else {
			clienteselecionado = false;
		}
		fornecedorComissao = new Fornecedorcomissaocurso();
		produtosorcamento = new Produtosorcamento();
		produto = new Produtos();
		fornecedorCidade = new Fornecedorcidade();
		cambio = new Cambio();
		cambio.setMoedas(new Moedas());
		curso = new Curso();
		curso.setNumeroCartaoVTM("");
		curso.setMoedaCartaoVTM("");
		curso.setPassagemAerea("Cliente Providenciará");
		curso.setVistoConsular("Cliente Providenciará");
		curso.setBanheiroprivativo("Não");
		curso.setValorSeguroGovernamental(0.0f);
		curso.setsCargaHoraria(0.0);
		curso.setsTipoCargaHoraria("");
		curso.setsNumeroSemanas(0);
		curso.setsCurso("");
		curso.setVegetariano("");
		curso.setFumante("");
		curso.setFamiliacomAnimais("");
		curso.setFamiliacomCrianca("");
		curso.setAdicionais("");
		curso.setSeguradoraGovernamental("N");
		curso.setCaratoVTM("Não");
		orcamento = new Orcamento();
		orcamento.setOrcamentoprodutosorcamentoList(new ArrayList<Orcamentoprodutosorcamento>());
		venda = new Vendas();
		formaPagamento = new Formapagamento();
		formaPagamento.setValorJuros(0.0f);
		produto = new Produtos();
		cidade = new Cidade();
		seguroViagem = new Seguroviagem();
		seguroViagem.setValoresseguro(new Valoresseguro());
		seguroViagem.setPossuiSeguro("Não");
		seguroplanos = new Seguroplanos();
		Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento produtosorcamento = produtoOrcamentoFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
		orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
		orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
		orcamentoprodutosorcamento.setValorMoedaNacional(aplicacaoMB.getParametrosprodutos().getValorTaxaTM());
		orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
		consultaCambio = true;
		novaFicha = true;
	}

	public void iniciarAlteracaoCurso() {
		// Vendas
		this.venda = curso.getVendas();
		valorVendaAlterar = venda.getValor();
		situacao = venda.getSituacao();
		if (venda.getSituacao().equalsIgnoreCase("ANDAMENTO") || venda.getSituacao().equalsIgnoreCase("FINALIZADA")) {
			vendaAlterada = venda;
		} else {
			vendaAlterada = new Vendas();
			vendaAlterada.setSituacao(venda.getSituacao());
		}
		DepartamentoProdutoFacade departamentoProdutoFacade = new DepartamentoProdutoFacade();
		depPrograma = departamentoProdutoFacade.consultar(venda.getProdutos().getIdprodutos());
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		depFinanceiro = departamentoFacade.consultar(3);
		controlealteracoes = new Controlealteracoes();
		controlealteracoes.setUsuario(usuarioLogadoMB.getUsuario());
		controlealteracoes.setVendas(venda);
		controlealteracoes.setDepartamentoproduto(depPrograma);
		this.cliente = venda.getCliente();
		int idade = Formatacao.calcularIdade(cliente.getDataNascimento());
		if (idade < 18) {
			DadosPaisFacade dadosPaisFacade = new DadosPaisFacade();
			dadosPais = dadosPaisFacade.consultarVendas(venda.getIdvendas());
			if (dadosPais == null) {
				dadosPais = new Dadospais();
			}
			visualizarDadosPais = true;
		} else
			visualizarDadosPais = false;
		carregarCursoAlteracao();
		// acomodacao
		if (curso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
			camposAcomodacaoCasaFamilia = "true";
		} else {
			camposAcomodacao = "false";
			camposAcomodacaoCasaFamilia = "true";
		}
		if (curso.getTipoAcomodacao().equalsIgnoreCase("Casa de família")) {
			camposAcomodacaoCasaFamilia = "false";
			camposAcomodacao = "false";
		}
		// cartao vtm
		if (curso.getCaratoVTM().equalsIgnoreCase("Sim")) {
			camposCartaoVtm = "false";
		} else {
			camposCartaoVtm = "true";
		}
		if (curso.getPossuiSeguroGovernamental().equalsIgnoreCase("S")) {
			camposSeguroGovernamental = "false";
		} else {
			camposSeguroGovernamental = "true";
		}
		fornecedorCidade = venda.getFornecedorcidade();
		cidade = fornecedorCidade.getCidade();
		pais = cidade.getPais();
		listarFornecedorCidade();
		fornecedorCidade = venda.getFornecedorcidade();
		fornecedorCidadeAlterado = fornecedorCidade;
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		fornecedorComissao = fornecedorComissaoCursoFacade.consultar(fornecedorCidade.getFornecedor().getIdfornecedor(),
				fornecedorCidade.getCidade().getPais().getIdpais());
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		this.seguroViagem = seguroViagemController.consultarSeguroCurso(venda.getIdvendas());
		if (seguroViagem == null) {
			this.seguroViagem = seguroViagemController.consultar(venda.getIdvendas());
		}
		if (seguroViagem != null) {
			seguroViagemAlterado = seguroViagem;
			fornecedorSeguro = seguroViagem.getValoresseguro().getFornecedorcidade();
			listarPlanosSeguro();
			seguroplanos = seguroViagem.getValoresseguro().getSeguroplanos();
			listarValoresSeguro();
			valorAlterarSeguro = seguroViagem.getValorSeguro();
			segurocancelamento = seguroViagem.isSegurocancelamento();
			verificarSeguroCancelamento();
		} else {
			seguroViagem = new Seguroviagem();
			seguroViagem.setPossuiSeguro("Não");
			seguroViagem.setValoresseguro(null);
		}
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			carregarSeguro();
			camposSeguroViagem = "false";
			if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
				camposSeguroViagem = "true";
			}
		} else {
			camposSeguroViagem = "true";
		}
		// segundo Curso
		if (!curso.getHabilitarSegundoCurso().equalsIgnoreCase("N")) {
			CheckBoxSegundoCurso = true;
			carregarCamposSegundoCurso();
		}
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		this.formaPagamento = formaPagamentoFacade.consultar(venda.getIdvendas());
		if (formaPagamento != null) {
			carregarCamposFormaPagamento();
			listaParcelamentoPagamentoAntiga = new ArrayList<>();
			for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
				Parcelamentopagamento parcelamentopagamento = new Parcelamentopagamento();
				parcelamentopagamento.setDiaVencimento(formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento());
				parcelamentopagamento.setFormaPagamento(formaPagamento.getParcelamentopagamentoList().get(i).getFormaPagamento());
				parcelamentopagamento.setNumeroParcelas(formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas());
				parcelamentopagamento.setValorParcela(formaPagamento.getParcelamentopagamentoList().get(i).getValorParcela());
				parcelamentopagamento.setValorParcelamento(formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento());
				parcelamentopagamento.setTipoParcelmaneto(formaPagamento.getParcelamentopagamentoList().get(i).getTipoParcelmaneto());
				listaParcelamentoPagamentoAntiga.add(parcelamentopagamento);
			} 
		}
		OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
		orcamento = orcamentoFacade.consultar(venda.getIdvendas());
		if (orcamento != null) {
			if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
				for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
					if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() < 0) {
						orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(
								orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1);
					}
				}
			}

			dataCambio = orcamento.getCambio().getData();
			cambio = orcamento.getCambio();
			orcamento.setValorCambio(cambio.getValor());
			moeda = cambio.getMoedas();
			carregarCambio();
			//valorCambio = orcamento.getValorCambio();
			cambio.setValor(valorCambio);
			calcularValorTotalOrcamento();
			carregarCamposAcomodacao();
		}
		consultaCambio = true;
	}

	public void carregarCambio() {
		CambioFacade cambioFacade = new CambioFacade();
		if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			int dias = Formatacao.subtrairDatas(venda.getDatavalidade(), new Date());
			if (dias > 3) {
				int idMoedaVenda = venda.getCambio().getMoedas().getIdmoedas();
				for (int i = 0; i < aplicacaoMB.getListaCambio().size(); i++) {
					int idUltimaMoeda = aplicacaoMB.getListaCambio().get(i).getMoedas().getIdmoedas();
					if (idMoedaVenda == idUltimaMoeda) {
						cambio = aplicacaoMB.getListaCambio().get(i);
						i = 1000000;
					}
				}
				if (cambio != null) {
					habilitarAvisoCambio = true;
					valorCambio = cambio.getValor();
					cambioAlterado = "Não";
					atualizarValoresProduto();
				}
			} else {
				valorCambio = orcamento.getValorCambio();
				cambio.setValor(valorCambio);
				cambioAlterado = orcamento.getCambioAlterado();
			}
		} else {
			valorCambio = orcamento.getValorCambio();
			cambio.setValor(valorCambio);
			cambioAlterado = orcamento.getCambioAlterado();
		}
	}

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getCursos());
		} catch (Exception e) {
			e.printStackTrace();
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

	public void calcularValorSeguroPrivadoListaProdutos() {
		int codSeguroPrivado = aplicacaoMB.getParametrosprodutos().getSeguroOrcamento();
		for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
			int codigoLista = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
					.getIdprodutosOrcamento();
			if (codSeguroPrivado == codigoLista) {
				orcamento.getOrcamentoprodutosorcamentoList().remove(i);
			}
		}
		float valorEstrangeira = 0.0f;
		float valorReal = 0.0f;
		Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		if (seguroViagem != null) {
			if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produto = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getSeguroOrcamento());
				orcamentoprodutosorcamento.setProdutosorcamento(produto);
				orcamentoprodutosorcamento.setDescricao(produto.getDescricao());
				if (seguroViagem.getValorSeguro() > 0) {
					CambioFacade cambioFacade = new CambioFacade();
					Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
							seguroViagem.getValoresseguro().getMoedas().getIdmoedas());
					valorReal = seguroViagem.getValorSeguro();
					valorEstrangeira = seguroViagem.getValorSeguro() / cambioSeguro.getValor();
					orcamentoprodutosorcamento.setValorMoedaNacional(valorReal);
					orcamentoprodutosorcamento.setValorMoedaEstrangeira(valorEstrangeira);
				} else {
					orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
					valorReal = 0;
					valorEstrangeira = 0;
				}
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
				calcularValorTotalOrcamento();
				calcularParcelamentoPagamento();
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
		atualizarValoresProduto();
	}

	public void carregarFornecedorComissao() {
		if (fornecedorCidade != null) {
			FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
			fornecedorComissao = fornecedorComissaoCursoFacade.consultar(
					fornecedorCidade.getFornecedor().getIdfornecedor(),
					fornecedorCidade.getCidade().getPais().getIdpais());
			if (fornecedorComissao == null) {
				pais = null;
				cidade = null;
				fornecedorCidade = null;
			} else {
				buscarFornecedorPais();
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
	
	public void listarPlanosSeguro() {
		if (fornecedorSeguro != null) {
			SeguroPlanosFacade seguroPlanosFacade = new SeguroPlanosFacade();
			String sql = "SELECT s FROM Seguroplanos  s WHERE s.fornecedor.idfornecedor="
					+ fornecedorSeguro.getFornecedor().getIdfornecedor() + " AND s.ativo=TRUE ORDER BY s.nome";
			listaSeguroPlanos = seguroPlanosFacade.listar(sql);
		}
		if (listaSeguroPlanos == null) {
			listaSeguroPlanos = new ArrayList<Seguroplanos>();
		}
	}

	public void listarValoresSeguro() {
		if (fornecedorSeguro != null && seguroplanos!=null && seguroplanos.getIdseguroplanos()!=null) {
			ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
			String sql;
			sql = "SELECT v FROM Valoresseguro v WHERE v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorSeguro.getIdfornecedorcidade() + " AND v.situacao='Ativo'"
					+ " AND v.seguroplanos.idseguroplanos=" + seguroplanos.getIdseguroplanos();
			listaValoresSeguro = valorSeguroFacade.listar(sql);
		}
		if (listaValoresSeguro == null) {
			listaValoresSeguro = new ArrayList<Valoresseguro>();
		}
	}

	public String pesquisarCliente() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultarCliente", options, null);
		return "";
	}

	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
		if (cliente != null) {
			clienteselecionado = false;
			if (curso.getIdcursos() == null) {
				cliente.setLiberarficha(false);
			}
		}
		verificarMenorIdade();
	}

	public void importarOcamentoCurso(Ocurso ocurso) {
		curso.setTipoimportacaoorcamento("T");
		curso.setIndiomaEstudar(ocurso.getIdioma().getDescricao()); 
		curso.setNivelIdiomaEstudar(ocurso.getNivelidioma());
		curso.setNomeCurso(ocurso.getProdutosorcamento().getDescricao());
		curso.setEscola(ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome());
		curso.setIdorcamento(ocurso.getIdocurso());
		pais = ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais();
		cidade = ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade();
		listarFornecedorCidade();
		fornecedorCidade = ocurso.getFornecedorcidadeidioma().getFornecedorcidade();
		curso.setPais(fornecedorCidade.getCidade().getPais().getNome());
		curso.setCidade(fornecedorCidade.getCidade().getNome());
		carregarFornecedorComissao();
		if(ocurso.getNumerosemanasbrinde()>0){
			curso.setNumeroSenamas(ocurso.getNumerosemanas()+ocurso.getNumerosemanasbrinde());
		}else curso.setNumeroSenamas(ocurso.getNumerosemanas());
		curso.setDataInicio(ocurso.getDatainicio());
		curso.setDataTermino(ocurso.getDatatermino());
		CambioFacade cambioFacade = new CambioFacade();
		cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
				ocurso.getCambio().getMoedas().getIdmoedas());
		orcamento.setValorCambio(cambio.getValor());
		orcamento.setOrcamentoprodutosorcamentoList(new ArrayList<Orcamentoprodutosorcamento>());
		if (ocurso.getOcrusoprodutosList() != null) {
			//int idtxTM = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
			for (int i = 0; i < ocurso.getOcrusoprodutosList().size(); i++) {
				//int idproduto = ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
					//	.getProdutosorcamento().getIdprodutosOrcamento();
				//if (idtxTM != idproduto) {
					Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
					if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
							.getComplementocurso() != null && 
							!ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getProdutosuplemento()
								.equalsIgnoreCase("valor")) { 
						orcamentoprodutosorcamento.setDescricao("Suplemento de Curso");
					} else if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
							.getComplementocurso() != null) {
						if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
								.getComplementocurso().getCargahoraria() != null
								&& ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
										.getComplementocurso().getCargahoraria().length() > 0) {
							 Double cargaHoraria = Formatacao.formatarStringDouble(ocurso.getOcrusoprodutosList().get(i)
										.getValorcoprodutos().getCoprodutos().getComplementocurso().getCargahoraria());
							if (curso.getAulassemana() == null ) {
								curso.setAulassemana(cargaHoraria);
								
							}else if(curso.getAulassemana() < cargaHoraria){
								curso.setAulassemana(cargaHoraria);
							}
						}
						curso.setCodigo(ocurso.getOcrusoprodutosList().get(i).getCodigo());
						if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos().isPacote()) {
							venda.setObstm(ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos()
									.getCoprodutos().getDescricao());
						}
						if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
								.getComplementocurso().getTipocargahoraria().equalsIgnoreCase("Aulas por Semana")) {
							curso.setTipoDuracao("Aulas por semana");
						} else if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
								.getComplementocurso().getTipocargahoraria().equalsIgnoreCase("Horas por Semana")) {
							curso.setTipoDuracao("Horas por semana");
						}
						orcamentoprodutosorcamento.setDescricao(ocurso.getOcrusoprodutosList().get(i)
								.getValorcoprodutos().getCoprodutos().getProdutosorcamento().getDescricao() + " - " + ocurso.getOcrusoprodutosList().get(i)
								.getValorcoprodutos().getCoprodutos().getComplementocurso().getDescricao());
					} else if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
							.getComplementoacomodacao() != null && 
							!ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getProdutosuplemento()
								.equalsIgnoreCase("valor")) { 
						orcamentoprodutosorcamento.setDescricao("Suplemento de Acomodação");
					}  else if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
							.getComplementoacomodacao() != null) {
						curso.setTipoAcomodacao(ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos()
								.getCoprodutos().getComplementoacomodacao().getTipoacomodacao());
						carregarCamposAcomodacao();
						curso.setTipoQuarto(ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
								.getComplementoacomodacao().getTipoquarto());
						curso.setRefeicoes(ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos()
								.getComplementoacomodacao().getTiporefeicao());
						String tipoBanheiro = ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos()
								.getCoprodutos().getComplementoacomodacao().getTipobanheiro();
						if (tipoBanheiro.equalsIgnoreCase("Privado")) {
							curso.setBanheiroprivativo("Sim"); 
						} else{
							curso.setBanheiroprivativo("Não");
						}
						curso.setDataChegada(verificarDataInicioAcomodacaoOrcamento(ocurso.getDatainicio(), ocurso.getNumerosemanas()));
						if(ocurso.getOcrusoprodutosList().get(i).getNumerosemanas()>0){
							curso.setNumeroSemanasAcamodacao(ocurso.getOcrusoprodutosList().get(i).getNumerosemanas().intValue());
						} 
						calcularDataTerminoAcomodacao();
						orcamentoprodutosorcamento.setDescricao("Acomodação");
					} else if(ocurso.getOcrusoprodutosList().get(i).getNomegrupo().equalsIgnoreCase("Adicionais")
								|| ocurso.getOcrusoprodutosList().get(i).getNomegrupo().equalsIgnoreCase("CustosExtras")) {
						orcamentoprodutosorcamento.setDescricao(ocurso.getOcrusoprodutosList().get(i).getDescricao());
						orcamentoprodutosorcamento.setProdutosorcamento(ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento());
						orcamentoprodutosorcamento.setValorMoedaEstrangeira(ocurso.getOcrusoprodutosList().get(i).getValororiginal());
						orcamentoprodutosorcamento.setValorMoedaNacional(ocurso.getOcrusoprodutosList().get(i).getValororiginal() * cambio.getValor());
						orcamentoprodutosorcamento.setImportado(true);
					}else {
						orcamentoprodutosorcamento.setDescricao(ocurso.getOcrusoprodutosList().get(i)
								.getValorcoprodutos().getCoprodutos().getDescricao());
					}
					if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos().isPacote()) {
						ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
						Produtosorcamento produtosorcamento = produtoOrcamentoFacade.consultar(17);
						orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
					} else {
						ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
						if(ocurso.getOcrusoprodutosList().get(i).getDescricao().equalsIgnoreCase("Desconto TM")) {
							Produtosorcamento produtosorcamento = produtoOrcamentoFacade
									.consultar(aplicacaoMB.getParametrosprodutos().getDescontomatriz()); 
							orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
						}else if(ocurso.getOcrusoprodutosList().get(i).getDescricao().equalsIgnoreCase("Desconto Loja")) {
							Produtosorcamento produtosorcamento = produtoOrcamentoFacade
									.consultar(aplicacaoMB.getParametrosprodutos().getDescontoloja()); 
							orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
						}else {
							if (ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos().getIdcoprodutos() == 5043) {
								Produtosorcamento produtosorcamento = produtoOrcamentoFacade
										.consultar("SELECT p FROM Produtosorcamento p WHERE p.descricao='"+ ocurso.getOcrusoprodutosList().get(i).getDescricao() +"'"); 
								if (produtosorcamento == null || produtosorcamento.getIdprodutosOrcamento() == null) {
									produtosorcamento = ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento();
								}
								orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
							}else {
								orcamentoprodutosorcamento.setProdutosorcamento(ocurso.getOcrusoprodutosList().get(i)
									.getValorcoprodutos().getCoprodutos().getProdutosorcamento());
							}
						}
					}
					if(orcamentoprodutosorcamento.getDescricao()!=null && orcamentoprodutosorcamento.getDescricao().length()<=0
							&& !orcamentoprodutosorcamento.getDescricao().equalsIgnoreCase("Suplemento de Curso")
							&&  !orcamentoprodutosorcamento.getDescricao().equalsIgnoreCase("Suplemento de Acomodação")){
						orcamentoprodutosorcamento
							.setDescricao(orcamentoprodutosorcamento.getProdutosorcamento().getDescricao());
					}
					orcamentoprodutosorcamento
							.setValorMoedaEstrangeira(ocurso.getOcrusoprodutosList().get(i).getValororiginal());
					orcamentoprodutosorcamento.setValorMoedaNacional(
							orcamentoprodutosorcamento.getValorMoedaEstrangeira() * cambio.getValor());
					orcamentoprodutosorcamento.setTipo("A");
					orcamentoprodutosorcamento.setImportado(true);
					orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
					float valororiginal=0.0f;
					if (ocurso.getOcrusoprodutosList().get(i).getValororiginal()!=null){
						valororiginal = ocurso.getOcrusoprodutosList().get(i).getValororiginal();
					}
					if (ocurso.getOcrusoprodutosList().get(i).isPossuipromocao()
							&& ocurso.getOcrusoprodutosList().get(i).getValorpromocional() != null
							&& ocurso.getOcrusoprodutosList().get(i).getValorpromocional() >= 0
							&& ocurso.getOcrusoprodutosList().get(i).getValorpromocional() != valororiginal) {
						orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
						ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
						Produtosorcamento produtosorcamento = produtoOrcamentoFacade
									.consultar(aplicacaoMB.getParametrosprodutos().getPromocaoescola()); 
						if(ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos().getComplementocurso()==null
								|| ocurso.getOcrusoprodutosList().get(i).getValorcoprodutos().getCoprodutos().getComplementocurso().getIdcomplementocurso()==null) {
							produtosorcamento = produtoOrcamentoFacade
									.consultar(aplicacaoMB.getParametrosprodutos().getPromocaoescolaacomodacao()); 
						}
						orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
						orcamentoprodutosorcamento
								.setValorMoedaEstrangeira(ocurso.getOcrusoprodutosList().get(i).getValororiginal()
										- ocurso.getOcrusoprodutosList().get(i).getValorpromocional());
						orcamentoprodutosorcamento.setValorMoedaNacional(
								orcamentoprodutosorcamento.getValorMoedaEstrangeira() * cambio.getValor());
						orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
						orcamentoprodutosorcamento.setImportado(true);
						orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
					}
				//}
			}
		}
		if (ocurso.getOcursoseguroList() != null) {
			calcularValorSeguroPrivadoListaProdutos();
			if (ocurso.getOcursoseguroList().size() > 0) {
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produtoorcamento = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getSeguroOrcamento());
				Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
				orcamentoprodutosorcamento.setDescricao(produtoorcamento.getDescricao());
				orcamentoprodutosorcamento.setProdutosorcamento(produtoorcamento);
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(ocurso.getOcursoseguroList().get(0).getValor() / ocurso.getOcursoseguroList().get(0).getValorseguroorcamento());
				orcamentoprodutosorcamento.setValorMoedaNacional(
						orcamentoprodutosorcamento.getValorMoedaEstrangeira() * cambio.getValor());
				orcamentoprodutosorcamento.setTipo("S"); 
				seguroViagem = new Seguroviagem();
				fornecedorSeguro = ocurso.getOcursoseguroList().get(0).getValoresseguro().getFornecedorcidade();
				listarPlanosSeguro();
				seguroViagem.setDataInicio(ocurso.getOcursoseguroList().get(0).getDatainicial());
				seguroViagem.setDataTermino(ocurso.getOcursoseguroList().get(0).getDatafinal());
				seguroViagem.setNumeroSemanas(ocurso.getOcursoseguroList().get(0).getNumerodias());
				seguroViagem.setPlano(ocurso.getOcursoseguroList().get(0).getValoresseguro().getPlano());
				seguroplanos = ocurso.getOcursoseguroList().get(0).getValoresseguro().getSeguroplanos();
				listarValoresSeguro();
				seguroViagem.setPossuiSeguro("Sim");
				seguroViagem.setValorMoedaEstrangeira(ocurso.getOcursoseguroList().get(0).getValor() / ocurso.getOcursoseguroList().get(0).getValorseguroorcamento());
				seguroViagem.setValorSeguro(orcamentoprodutosorcamento.getValorMoedaNacional());
				seguroViagem.setValoresseguro(ocurso.getOcursoseguroList().get(0).getValoresseguro());
				seguroViagem.setSegurocancelamento(ocurso.getOcursoseguroList().get(0).isSegurocancelamento());
				orcamentoprodutosorcamento.setImportado(true);
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
				carregarCobrancaSeguro();
				//adicionarSeguroCancelamento();
				camposSeguroViagem = "false";
			}
		}
		calcularValorTotalOrcamento();
		OcursoPacoteFacade ocursoPacoteFacade = new OcursoPacoteFacade();
		Ocursopacote ocursopacote = ocursoPacoteFacade.consultar("select o From Ocursopacote o where o.ocurso.idocurso="+ocurso.getIdocurso());
		if(ocursopacote!=null){
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			Produtosorcamento produtoorcamento = produtoOrcamentoFacade.consultar(aplicacaoMB.getParametrosprodutos().getDescontomatriz());
		    if(valorTotal>ocursopacote.getOcurso().getTotalmoedanacional()){
				float valor = valorTotal - ocursopacote.getOcurso().getTotalmoedanacional();
				cursospacote  = ocursopacote.getCursospacote();
				Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
				orcamentoprodutosorcamento.setDescricao(produtoorcamento.getDescricao());
				orcamentoprodutosorcamento.setProdutosorcamento(produtoorcamento);
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(valor/cambio.getValor());
				orcamentoprodutosorcamento.setValorMoedaNacional(valor);
				orcamentoprodutosorcamento.setTipo("S");
				orcamentoprodutosorcamento.setImportado(true);
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
				if (listaProdutosOrcamento != null && listaProdutosOrcamento.size() > 0) {
					FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
					Filtroorcamentoproduto filtroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(1, produtoorcamento.getIdprodutosOrcamento());
					listaProdutosOrcamento.remove(filtroorcamentoproduto);
				}
				calcularValorTotalOrcamento();
				formaPagamento.setObservacoes("PACOTE PROMOCIONAL TRAVELMATE");
			} 
		}
	}

	public String importarOrcamento() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 700);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("nome", cliente.getNome());
		RequestContext.getCurrentInstance().openDialog("importarOrcamento", options, null);
		return "";
	}

	public void retornoDialogOrcamento(SelectEvent event) {
		Ocurso ocurso = (Ocurso) event.getObject();
		importarOcamentoCurso(ocurso);
	}

	public void verificarMenorIdade() {
		int idade = Formatacao.calcularIdade(cliente.getDataNascimento());
		if (idade < 18) {
			visualizarDadosPais = true;
			curso.setDadospais(true);
			dadosPais.setNomemae(cliente.getNomeMae());
			dadosPais.setTelefonemae(cliente.getFoneMae());
			dadosPais.setNomepai(cliente.getNomePai());
			dadosPais.setTelefonepai(cliente.getFonePai());
			digitosTelefonePai = aplicacaoMB.checkBoxTelefone(
					usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), dadosPais.getTelefonepai());
			digitosTelefoneMae = aplicacaoMB.checkBoxTelefone(
					usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), dadosPais.getTelefonemae());
		} else {
			curso.setDadospais(false);
			visualizarDadosPais = false;
		}
	}

	public void validarCampoPai() {
		if (dadosPais.getNomepai() != null) {
			if (dadosPais.getNomepai().equalsIgnoreCase("Desconhecido")) {
				habilitarDadosPai = "true";
			} else {
				habilitarDadosPai = "false";
			}
		} else {
			habilitarDadosPai = "false";
		}
	}

	public void repetirEnderecoMae() {
		habilitarDadosPai="false";
		dadosPais.setTipologradouropai(dadosPais.getTipologradouromae());
		dadosPais.setLogradouropai(dadosPais.getLogradouromae());
		dadosPais.setNumeropai(dadosPais.getNumerromae());
		dadosPais.setComplementopai(dadosPais.getComplementomae());
		dadosPais.setEstadopai(dadosPais.getEstadomae());
		dadosPais.setCidadepai(dadosPais.getCidademae());
		dadosPais.setCeppai(dadosPais.getCepmae());
		dadosPais.setBairropai(dadosPais.getBairromae());
	}

	public void verificarRegraVisto() {
		if ((venda.getIdvendas() == null) || (!cliente.isLiberarficha())) {
			if (!curso.getVistoConsular().equalsIgnoreCase("Cliente Providenciará")) {
				if (cliente.getTipovisto() != null && !cliente.getTipovisto().equalsIgnoreCase("sn")) {
					cliente.setLiberarficha(true);
					RegraVistoBean regra = new RegraVistoBean(curso.getDataInicio(),
							fornecedorCidade.getCidade().getPais().getIdpais(), cliente.getTipovisto());
					if (!regra.getMsg().equalsIgnoreCase("ok")) {
						Mensagem.lancarMensagemInfo("Vistos", regra.getMsg());
						cliente.setLiberarficha(false);
					} else {
						cliente.setLiberarficha(true);
					}
				} else {
					Mensagem.lancarMensagemErro("Visto", "Selecione tipo de Visto");
					cliente.setLiberarficha(false);
				}
			} else {
				cliente.setLiberarficha(true);
			}
		}
	}

	public void carregarOrcamentoCurso(Orcamentocurso orcamentoCurso) {
		curso.setTipoimportacaoorcamento("M");
		curso.setIndiomaEstudar(orcamentoCurso.getIdioma());
		curso.setNivelIdiomaEstudar(orcamentoCurso.getNivelIdioma());
		curso.setNomeCurso(orcamentoCurso.getCurso());
		pais = orcamentoCurso.getFornecedorcidade().getCidade().getPais();
		cidade = orcamentoCurso.getFornecedorcidade().getCidade();
		listarFornecedorCidade();
		fornecedorCidade = orcamentoCurso.getFornecedorcidade();
		curso.setEscola(fornecedorCidade.getFornecedor().getNome());
		curso.setCidade(fornecedorCidade.getCidade().getNome());
		curso.setPais(fornecedorCidade.getCidade().getPais().getNome());
		curso.setTipoDuracao(orcamentoCurso.getTipoDuracao());
		curso.setAulassemana(orcamentoCurso.getAulasSemana().doubleValue());
		curso.setNumeroSenamas(orcamentoCurso.getNumeroSemanas());
		curso.setDataInicio(orcamentoCurso.getDataInicio());
		curso.setDataTermino(orcamentoCurso.getDataTermino());
		curso.setTipoAcomodacao(orcamentoCurso.getTipoAcomodacao());
		carregarCamposAcomodacao();
		curso.setTipoQuarto(orcamentoCurso.getTipoQuarto());
		curso.setRefeicoes(orcamentoCurso.getRefeicoes());
		curso.setNumeroSemanasAcamodacao(orcamentoCurso.getDuracaoAcomodacao());
		curso.setsTipoCargaHoraria(orcamentoCurso.getTipoDuracao());
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		List<Produtoorcamentocurso> listaProdutosOrcamentoCurso = orcamentoCursoFacade
				.listarProdutoOrcamentoCurso(orcamentoCurso.getIdorcamentoCurso());
		CambioFacade cambioFacade = new CambioFacade();
		cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
				orcamentoCurso.getCambio().getMoedas().getIdmoedas());
		orcamento.setValorCambio(cambio.getValor());
		moeda = cambio.getMoedas();
		valorCambio = cambio.getValor();
		if (orcamento.getOrcamentoprodutosorcamentoList() == null) {
			orcamento.setOrcamentoprodutosorcamentoList(new ArrayList<Orcamentoprodutosorcamento>());
		}
		if (orcamentoCurso.getOrcamentomanualseguro() != null) {
			calcularValorSeguroPrivadoListaProdutos();
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produtoorcamento = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getSeguroOrcamento());
				Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
				orcamentoprodutosorcamento.setDescricao(produtoorcamento.getDescricao());
				orcamentoprodutosorcamento.setProdutosorcamento(produtoorcamento);
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(orcamentoCurso.getOrcamentomanualseguro().getValor() / orcamentoCurso.getOrcamentomanualseguro().getValoresseguro().getValorgross());
				orcamentoprodutosorcamento.setValorMoedaNacional(
						orcamentoprodutosorcamento.getValorMoedaEstrangeira() * cambio.getValor());
				orcamentoprodutosorcamento.setTipo("S"); 
				seguroViagem = new Seguroviagem();
				fornecedorSeguro = orcamentoCurso.getOrcamentomanualseguro().getValoresseguro().getFornecedorcidade();
				listarPlanosSeguro();
				seguroViagem.setDataInicio(orcamentoCurso.getOrcamentomanualseguro().getDatainicio());
				seguroViagem.setDataTermino(orcamentoCurso.getOrcamentomanualseguro().getDatatermino());
				seguroViagem.setNumeroSemanas(orcamentoCurso.getOrcamentomanualseguro().getNumerodias());
				seguroViagem.setPlano(orcamentoCurso.getOrcamentomanualseguro().getValoresseguro().getPlano());
				seguroplanos = orcamentoCurso.getOrcamentomanualseguro().getValoresseguro().getSeguroplanos();
				listarValoresSeguro();
				seguroViagem.setPossuiSeguro("Sim");
				seguroViagem.setValorMoedaEstrangeira(orcamentoCurso.getOrcamentomanualseguro().getValor() / orcamentoCurso.getOrcamentomanualseguro().getValoresseguro().getValorgross());
				seguroViagem.setValorSeguro(orcamentoprodutosorcamento.getValorMoedaNacional());
				seguroViagem.setValoresseguro(orcamentoCurso.getOrcamentomanualseguro().getValoresseguro());
				seguroViagem.setSegurocancelamento(orcamentoCurso.getOrcamentomanualseguro().isSegurocancelamento());
				carregarCobrancaSeguro();
				//adicionarSeguroCancelamento();
				camposSeguroViagem = "false";
		}
		int idTaxaTm = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		if (listaProdutosOrcamentoCurso != null) {
			for (int i = 0; i < listaProdutosOrcamentoCurso.size(); i++) {
				if (listaProdutosOrcamentoCurso.get(i).getProdutosOrcamento().getIdprodutosOrcamento() != idTaxaTm) {
					produtosorcamento = listaProdutosOrcamentoCurso.get(i).getProdutosOrcamento();
					Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
					orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
					orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
					orcamentoprodutosorcamento
							.setValorMoedaEstrangeira(listaProdutosOrcamentoCurso.get(i).getValorMoedaEstrangeira());
					orcamentoprodutosorcamento.setValorMoedaNacional(
							listaProdutosOrcamentoCurso.get(i).getValorMoedaEstrangeira() * cambio.getValor());
					orcamentoprodutosorcamento.setImportado(true);
					orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
					calcularValorTotalOrcamento();
					produtosorcamento = null;
				}
			}
			calcularValorTotalOrcamento();
		}
//		if (orcamentoCurso.getOrcamentomanualseguro() != null && orcamentoCurso.getOrcamentomanualseguro().getIdorcamentomanualseguro() != null) {
//			calcularValorSeguroPrivadoListaProdutos();
//			if (ocurso.getOcursoseguroList().size() > 0) {
//				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
//				Produtosorcamento produtoorcamento = produtoOrcamentoFacade
//						.consultar(aplicacaoMB.getParametrosprodutos().getSeguroOrcamento());
//				Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
//				orcamentoprodutosorcamento.setDescricao(produtoorcamento.getDescricao());
//				orcamentoprodutosorcamento.setProdutosorcamento(produtoorcamento);
//				orcamentoprodutosorcamento.setValorMoedaEstrangeira(orcamentoCurso.getOrcamentomanualseguro().getValor() /cambio.getValor());
//				orcamentoprodutosorcamento.setValorMoedaNacional(
//						orcamentoprodutosorcamento.getValorMoedaEstrangeira() * cambio.getValor());
//				orcamentoprodutosorcamento.setTipo("S"); 
//				seguroViagem = new Seguroviagem();
//				fornecedorSeguro = ocurso.getOcursoseguroList().get(0).getValoresseguro().getFornecedorcidade();
//				listarPlanosSeguro();
//				seguroViagem.setDataInicio(ocurso.getOcursoseguroList().get(0).getDatainicial());
//				seguroViagem.setDataTermino(ocurso.getOcursoseguroList().get(0).getDatafinal());
//				seguroViagem.setNumeroSemanas(ocurso.getOcursoseguroList().get(0).getNumerodias());
//				seguroViagem.setPlano(ocurso.getOcursoseguroList().get(0).getValoresseguro().getPlano());
//				seguroplanos = ocurso.getOcursoseguroList().get(0).getValoresseguro().getSeguroplanos();
//				listarValoresSeguro();
//				seguroViagem.setPossuiSeguro("Sim");
//				seguroViagem.setValorMoedaEstrangeira(ocurso.getOcursoseguroList().get(0).getValor() / ocurso.getOcursoseguroList().get(0).getValorseguroorcamento());
//				seguroViagem.setValorSeguro(orcamentoprodutosorcamento.getValorMoedaNacional());
//				seguroViagem.setValoresseguro(ocurso.getOcursoseguroList().get(0).getValoresseguro());
//				seguroViagem.setSegurocancelamento(ocurso.getOcursoseguroList().get(0).isSegurocancelamento());
//				orcamentoprodutosorcamento.setImportado(true);
//				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
//				carregarCobrancaSeguro();
//				//adicionarSeguroCancelamento();
//				camposSeguroViagem = "false";
//			}
//		}
	}

	public String importarOrcamentoManual() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 700);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("nome", cliente.getNome());
		RequestContext.getCurrentInstance().openDialog("importarOrcamentoManual", options, null);
		return "";
	}

	public void retornoDialogOrcamentoManual(SelectEvent event) {
		Orcamentocurso orcamentoCurso = (Orcamentocurso) event.getObject();
		carregarOrcamentoCurso(orcamentoCurso);
	}

	public void buscarFornecedorPais() {
		if ((pais != null) && (fornecedorCidade != null)) {
			String sql = "SELECT f FROM Fornecedorpais f where f.fornecedor.idfornecedor="
					+ fornecedorCidade.getFornecedor().getIdfornecedor() + " and f.pais.idpais=" + pais.getIdpais();
			FornecedorPaisFacade fornecedorPaisFacade = new FornecedorPaisFacade();
			Fornecedorpais fornecedorpais = fornecedorPaisFacade.buscar(sql);
			if (fornecedorpais != null) {
				moeda = fornecedorpais.getMoedas();
			} else {
				moeda = pais.getMoedas();
			}
			if (moeda != null) {
				carregarValorCambio();
			}
		}
	}

	public void gerarListaParcelamentoOriginal() {
		if (venda.getIdvendas() != null) { 
			if (formaPagamento.getParcelamentopagamentoList() != null) {
				listaParcelamentoPagamentoOriginal = new ArrayList<Parcelamentopagamento>();
				listaParcelamentoPagamentoOriginal = formaPagamento.getParcelamentopagamentoList();   
			} 
		}
	}

	public void verificarAlteracaoListaParcelamento() {
		if (formaPagamento != null) {
			if (formaPagamento.getParcelamentopagamentoList() != null) {
				for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
					formaPagamento.getParcelamentopagamentoList().get(i)
							.setVerificarParcelamento(Formatacao.calcularDataParcelamento(
									formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento(),
									formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas(),
									curso.getDataInicio()));
				}
			}
		}
	}

	public String validarMascaraFoneContatoEmergencia() {
		return aplicacaoMB.validarMascaraTelefone(digitosTelefoneContatoEmergencia);
	}

	public String validarMascaraFonePai() {
		return aplicacaoMB.validarMascaraTelefone(digitosTelefonePai);
	}

	public String validarMascaraFoneMae() {
		return aplicacaoMB.validarMascaraTelefone(digitosTelefoneMae);
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

	public void selecionarCambio() {
		if (pais != null && pais.getIdpais() != null) {
			moeda = pais.getMoedas();
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda);
			valorCambio = cambio.getValor();
			orcamento.setValorCambio(cambio.getValor());
		}
	}

	public String calcularComissaoFranquia() {
		Vendascomissao vendascomissao = new Vendascomissao();
		if (venda.getIdvendas() == null) {
			venda.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
			venda.setValor(valorTotal);
			venda.setCambio(cambio);
			produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getCursos());
			venda.setProdutos(produto);
		}
		ComissaoCursoBean comissaoCursoBean = new ComissaoCursoBean(aplicacaoMB, venda,
				orcamento.getOrcamentoprodutosorcamentoList(), fornecedorComissao, formaPagamento.getParcelamentopagamentoList(),
				curso.getDataInicio(), vendascomissao, formaPagamento.getValorJuros(), false);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendascomissao", vendascomissao);
		session.setAttribute("percentualcomissao", comissaoCursoBean.getPercentualComissao());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 380);
		RequestContext.getCurrentInstance().openDialog("calcularcomissao", options, null);
		return "";
	}
	
	public void adicionarSeguroCancelamento() { 
		if (seguroViagem.isSegurocancelamento() && seguroViagem.getValoresseguro().isSegurocancelamento()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento(); 
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			Produtosorcamento produto = produtoOrcamentoFacade
					.consultar(aplicacaoMB.getParametrosprodutos().getSegurocancelamentoid());
			orcamentoprodutosorcamento.setProdutosorcamento(produto);
			orcamentoprodutosorcamento.setDescricao(produto.getDescricao());
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					seguroViagem.getValoresseguro().getMoedas().getIdmoedas()); 
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			orcamentoprodutosorcamento.setValorMoedaNacional(
					seguroViagem.getValoresseguro().getValorsegurocancelamento()*cambioSeguro.getValor()); 
			orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			calcularValorTotalOrcamento();
			calcularParcelamentoPagamento();
		} else {
			seguroViagem.setSegurocancelamento(false);
			if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
				int idseguroCancelamento = aplicacaoMB.getParametrosprodutos().getSegurocancelamentoid();
				for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
					int idProdutoOrcamento = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
							.getIdprodutosOrcamento();
					if (idseguroCancelamento == idProdutoOrcamento) {
						if (orcamento.getOrcamentoprodutosorcamentoList().get(i)
								.getIdorcamentoProdutosOrcamento() != null) {
							OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
							orcamentoFacade.excluirOrcamentoProdutoOrcamento(orcamento
									.getOrcamentoprodutosorcamentoList().get(i).getIdorcamentoProdutosOrcamento());
						}
						orcamento.getOrcamentoprodutosorcamentoList().remove(i);
						calcularValorTotalOrcamento();
						calcularParcelamentoPagamento();
						i = 1000;
					}
				}
			}
		}
	}
	
	public boolean habilitarTrocaCliente() {
		if(novaFicha) {
			return false;
		}else return true;
	}
	
	public void verificarSeguroCancelamento() {
		if(seguroViagem.getValoresseguro().isSegurocancelamento()) {
			segurocancelamento = true; 
		} else {
			segurocancelamento = false; 
		}
	} 
	
	
	public void desabilitarAlergiaAlimento(){
		if (curso.getPossuiAlergia().equalsIgnoreCase("Sim")) {
			desabilitarAlergiaAlimento = false;
		}else{
			desabilitarAlergiaAlimento = true;
		}
	}
	
	public Date verificarDataInicioAcomodacaoOrcamento(Date datainicio, int numerosemana) {
		int diasemana = Formatacao.diaSemana(datainicio);
		if(diasemana==1) {
			return datainicio;
		}else {
			diasemana = 1 - diasemana; 
			Date data=null;
			try {
				data = Formatacao.SomarDiasDatas(datainicio, diasemana);
			} catch (Exception e) { 
				e.printStackTrace();
			}
			return data;
		}  
	} 
	
	
	public void fecharNotificacao() {
		habilitarAvisoCambio = false;
	}
	
}