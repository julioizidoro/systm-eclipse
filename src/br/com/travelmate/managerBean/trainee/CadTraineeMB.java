package br.com.travelmate.managerBean.trainee;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import br.com.travelmate.bean.DataVencimentoBean;
import br.com.travelmate.bean.ProductRunnersCalculosBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadPosVendaDao;
import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.facade.ProdutosTraineeFacade;
import br.com.travelmate.facade.ValoresTraineeFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcomissaocurso;
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
import br.com.travelmate.model.Produtostrainee;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Valorestrainee;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadTraineeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private CambioDao cambioDao;
	@Inject
	private LeadSituacaoDao leadSituacaoDao;
	@Inject
	private LeadDao leadDao;
	@Inject
	private LeadPosVendaDao leadPosVendaDao;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	
	private Trainee trainee;
	private Valorestrainee valorestrainee;
	private List<Valorestrainee> listaValoresTrainee;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Parcelamentopagamento parcelamentopagamento;
	private Orcamento orcamento;
	private Cambio cambio;
	private Date dataCambio;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private List<Paisproduto> listaPais;
	private Cidade cidade;
	private Cliente cliente;
	private Produtos produto;
	// private float saldoEmAberto;
	private List<Moedas> listaMoedas;
	private Orcamentoprodutosorcamento orcamentoprodutosorcamento;
	private Produtosorcamento produtosorcamento;
	private List<Filtroorcamentoproduto> listaProdutosOrcamento;
	private boolean enviarFicha;
	private boolean novaFicha = false;
	private Fornecedorcomissaocurso fornecedorComissao;
	private String cambioAlterado = "Não";
	private String dadosAlterado;
	private Trainee traineeAlterado;
	private Vendas vendaAlterada;
	private float valorVendaAlterar = 0.0f;
	private Fornecedorcidade fornecedorCidadeAlterado;
	private String tipo;
	private boolean consultaCambio = false;
	private String tipoaustralia = "";
	private String numeroSemanas;
	private Produtostrainee produtosTrainee;
	private String camposj1 = "true";
	private Moedas moeda;
	private float valorSaldoParcelar = 0;
	private float valorMoedaReal = 0;
	private float valorMoedaEstrangeira = 0;
	private float valorParcela = 0;
	private String numeroParcelas = "01";
	private List<String> listaTipoParcelamento;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga;
	private boolean digitosFoneContatoEmergencia;
	private Cancelamento cancelamento;
	private Lead lead;
	private String voltarControleVendas = "";
	private boolean todosnumeros;
	private boolean numero20;
	private boolean numero21;
	private boolean numero22;
	private boolean numero23;
	private boolean numero24;
	private boolean numero25;
	private boolean numero26;
	private boolean numero38;
	private boolean numero52;
	private boolean habilitarAvisoCambio = false;
	private String moedaNacional;
	private boolean mascara;
	private boolean semmascara;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		trainee = (Trainee) session.getAttribute("trainee");
		tipo = (String) session.getAttribute("tipo");
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("cliente");
		session.removeAttribute("lead");
		session.removeAttribute("tipo");
		session.removeAttribute("trainee");
		valorestrainee = new Valorestrainee();
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = 0;
		idProduto = aplicacaoMB.getParametrosprodutos().getTrainee();
		listaPais = paisProdutoFacade.listar(idProduto);
		carregarComboMoedas();
		gerarListaProdutos();
		if (trainee == null) {
			iniciarNovoTrainee();
			dataCambio = Formatacao.ConvercaoStringData(aplicacaoMB.retornarDataCambio());

			todosnumeros = true;
			numero20 = true;
			numero21 = true;
			numero22 = true;
			numero23 = true;
			numero24 = true;
			numero25 = true;
			numero26 = true;
			numero38 = true;
			numero52 = true;
			numeroSemanas = "0";
		} else {
			iniciarAlteracao();
		}
		parcelamentopagamento.setNumeroParcelas(01);
		parcelamentopagamento.setFormaPagamento("sn");
		gerarListaTipoParcelamento();
		digitosFoneContatoEmergencia = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
				trainee.getFoneContatoEmergencia());
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
		verificarPaisUnidade();
	}

	public float getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(float valorParcela) {
		this.valorParcela = valorParcela;
	}

	public String getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(String numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public float getValorSaldoParcelar() {
		return valorSaldoParcelar;
	}

	public void setValorSaldoParcelar(float valorSaldoParcelar) {
		this.valorSaldoParcelar = valorSaldoParcelar;
	}

	public float getValorMoedaReal() {
		return valorMoedaReal;
	}

	public void setValorMoedaReal(float valorMoedaReal) {
		this.valorMoedaReal = valorMoedaReal;
	}

	public float getValorMoedaEstrangeira() {
		return valorMoedaEstrangeira;
	}

	public void setValorMoedaEstrangeira(float valorMoedaEstrangeira) {
		this.valorMoedaEstrangeira = valorMoedaEstrangeira;
	}

	public Moedas getMoeda() {
		return moeda;
	}

	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public Valorestrainee getValorestrainee() {
		return valorestrainee;
	}

	public void setValorestrainee(Valorestrainee valorestrainee) {
		this.valorestrainee = valorestrainee;
	}

	public List<Valorestrainee> getListaValoresTrainee() {
		return listaValoresTrainee;
	}

	public void setListaValoresTrainee(List<Valorestrainee> listaValoresTrainee) {
		this.listaValoresTrainee = listaValoresTrainee;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public Date getDataCambio() {
		return dataCambio;
	}

	public void setDataCambio(Date dataCambio) {
		this.dataCambio = dataCambio;
	}

	public Vendas getVendaAlterada() {
		return vendaAlterada;
	}

	public void setVendaAlterada(Vendas vendaAlterada) {
		this.vendaAlterada = vendaAlterada;
	}

	public float getValorVendaAlterar() {
		return valorVendaAlterar;
	}

	public void setValorVendaAlterar(float valorVendaAlterar) {
		this.valorVendaAlterar = valorVendaAlterar;
	}

	public Fornecedorcomissaocurso getFornecedorComissao() {
		return fornecedorComissao;
	}

	public void setFornecedorComissao(Fornecedorcomissaocurso fornecedorComissao) {
		this.fornecedorComissao = fornecedorComissao;
	}

	public String getCambioAlterado() {
		return cambioAlterado;
	}

	public void setCambioAlterado(String cambioAlterado) {
		this.cambioAlterado = cambioAlterado;
	}

	public String getDadosAlterado() {
		return dadosAlterado;
	}

	public void setDadosAlterado(String dadosAlterado) {
		this.dadosAlterado = dadosAlterado;
	}

	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public boolean isConsultaCambio() {
		return consultaCambio;
	}

	public void setConsultaCambio(boolean consultaCambio) {
		this.consultaCambio = consultaCambio;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public List<Filtroorcamentoproduto> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public Trainee getTraineeAlterado() {
		return traineeAlterado;
	}

	public void setTraineeAlterado(Trainee traineeAlterado) {
		this.traineeAlterado = traineeAlterado;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Orcamentoprodutosorcamento getOrcamentoprodutosorcamento() {
		return orcamentoprodutosorcamento;
	}

	public void setOrcamentoprodutosorcamento(Orcamentoprodutosorcamento orcamentoprodutosorcamento) {
		this.orcamentoprodutosorcamento = orcamentoprodutosorcamento;
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

	public boolean isNovaFicha() {
		return novaFicha;
	}

	public void setNovaFicha(boolean novaFicha) {
		this.novaFicha = novaFicha;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public Parcelamentopagamento getParcelamentopagamento() {
		return parcelamentopagamento;
	}

	public void setParcelamentopagamento(Parcelamentopagamento parcelamentopagamento) {
		this.parcelamentopagamento = parcelamentopagamento;
	}

	public String getTipoaustralia() {
		return tipoaustralia;
	}

	public void setTipoaustralia(String tipoaustralia) {
		this.tipoaustralia = tipoaustralia;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public boolean isEnviarFicha() {
		return enviarFicha;
	}

	public void setEnviarFicha(boolean enviarFicha) {
		this.enviarFicha = enviarFicha;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Fornecedorcidade getFornecedorCidadeAlterado() {
		return fornecedorCidadeAlterado;
	}

	public void setFornecedorCidadeAlterado(Fornecedorcidade fornecedorCidadeAlterado) {
		this.fornecedorCidadeAlterado = fornecedorCidadeAlterado;
	}

	public String getNumeroSemanas() {
		return numeroSemanas;
	}

	public void setNumeroSemanas(String numeroSemanas) {
		this.numeroSemanas = numeroSemanas;
	}

	public Produtostrainee getProdutosTrainee() {
		return produtosTrainee;
	}

	public void setProdutosTrainee(Produtostrainee produtosTrainee) {
		this.produtosTrainee = produtosTrainee;
	}

	public String getCamposj1() {
		return camposj1;
	}

	public void setCamposj1(String camposj1) {
		this.camposj1 = camposj1;
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

	public boolean isDigitosFoneContatoEmergencia() {
		return digitosFoneContatoEmergencia;
	}

	public void setDigitosFoneContatoEmergencia(boolean digitosFoneContatoEmergencia) {
		this.digitosFoneContatoEmergencia = digitosFoneContatoEmergencia;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoOriginal() {
		return listaParcelamentoPagamentoOriginal;
	}

	public void setListaParcelamentoPagamentoOriginal(List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal) {
		this.listaParcelamentoPagamentoOriginal = listaParcelamentoPagamentoOriginal;
	}

	public boolean isTodosnumeros() {
		return todosnumeros;
	}

	public void setTodosnumeros(boolean todosnumeros) {
		this.todosnumeros = todosnumeros;
	}

	public boolean isNumero20() {
		return numero20;
	}

	public void setNumero20(boolean numero20) {
		this.numero20 = numero20;
	}

	public boolean isNumero21() {
		return numero21;
	}

	public void setNumero21(boolean numero21) {
		this.numero21 = numero21;
	}

	public boolean isNumero22() {
		return numero22;
	}

	public void setNumero22(boolean numero22) {
		this.numero22 = numero22;
	}

	public boolean isNumero23() {
		return numero23;
	}

	public void setNumero23(boolean numero23) {
		this.numero23 = numero23;
	}

	public boolean isNumero24() {
		return numero24;
	}

	public void setNumero24(boolean numero24) {
		this.numero24 = numero24;
	}

	public boolean isNumero25() {
		return numero25;
	}

	public void setNumero25(boolean numero25) {
		this.numero25 = numero25;
	}

	public boolean isNumero26() {
		return numero26;
	}

	public void setNumero26(boolean numero26) {
		this.numero26 = numero26;
	}

	public boolean isNumero38() {
		return numero38;
	}

	public void setNumero38(boolean numero38) {
		this.numero38 = numero38;
	}

	public boolean isNumero52() {
		return numero52;
	}

	public void setNumero52(boolean numero52) {
		this.numero52 = numero52;
	}

	public boolean isHabilitarAvisoCambio() {
		return habilitarAvisoCambio;
	}

	public void setHabilitarAvisoCambio(boolean habilitarAvisoCambio) {
		this.habilitarAvisoCambio = habilitarAvisoCambio;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}

	public boolean isMascara() {
		return mascara;
	}

	public void setMascara(boolean mascara) {
		this.mascara = mascara;
	}

	public boolean isSemmascara() {
		return semmascara;
	}

	public void setSemmascara(boolean semmascara) {
		this.semmascara = semmascara;
	}

	public void iniciarNovoTrainee() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		produto = new Produtos();
		fornecedorCidade = new Fornecedorcidade();
		pais = new Pais();
		cambio = new Cambio();
		trainee = new Trainee();
		carregarCamposTrainee();
		orcamento = new Orcamento();
		orcamento.setValorCambio(0.0f);
		orcamento.setTotalMoedaEstrangeira(0.0f);
		orcamento.setTotalMoedaNacional(0.0f);
		orcamento.setOrcamentoprodutosorcamentoList(new ArrayList<Orcamentoprodutosorcamento>());
		venda = new Vendas();
		venda.setDataVenda(new Date());
		venda.setSituacao("PROCESSO");
		formaPagamento = new Formapagamento();
		formaPagamento.setValorJuros(0.0f);
		formaPagamento.setValorOrcamento(0.0f);
		formaPagamento.setValorTotal(0.0f);
		formaPagamento.setPossuiJuros("Não");
		produto = new Produtos();
		cidade = new Cidade();
		parcelamentopagamento = new Parcelamentopagamento();
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		consultaCambio = true;
		trainee.setPassagemAerea("Cliente Providenciará");
	}

	public String pesquisarCliente() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultarCliente", options, null);
		return "";
	}

	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
	}

	public void listarFornecedorCidade() {
		if (cidade != null) {
			String sql = "select f from Fornecedorcidade f where f.produtos.idprodutos="
					+ aplicacaoMB.getParametrosprodutos().getTrainee() + " and f.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.ativo=1";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
	}

	public void carregarValores() {
		if (fornecedorCidade != null) {
			String sql = "select v from Valorestrainee v where v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade() + " and v.situacao='Ativo' and v.tipotrainee='EUA'";
			ValoresTraineeFacade valoresTraineeFacade = new ValoresTraineeFacade();
			listaValoresTrainee = valoresTraineeFacade.listar(sql);
		}
	}

	public void adicionarFormaPagamento() {
		gerarListaParcelamentoOriginal();
		String msg = validarFormaPagamento();
		if (msg.length() < 5) {
			if (parcelamentopagamento.getValorParcela() > parcelamentopagamento.getValorParcelamento()) {
				Mensagem.lancarMensagemErro("Atenção", "Valor das parcelas maior que o valor a parcelar");
			} else {
				if (formaPagamento.getParcelamentopagamentoList() == null) {
					formaPagamento.setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
				}
				if (formaPagamento != null) {
					parcelamentopagamento.setFormapagamento(formaPagamento);
				}
				parcelamentopagamento.setValorParcelamento(parcelamentopagamento.getValorParcelamento());
				if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")) {
					DataVencimentoBean dataVencimentoBean = new DataVencimentoBean(parcelamentopagamento.getDiaVencimento());
					parcelamentopagamento.setDiaVencimento(dataVencimentoBean.validarDataVencimento());
				}
				if (venda.getIdvendas() != null) {
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamentopagamento = contasReceberBean.gerarParcelasIndividuais(parcelamentopagamento,
								formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB, null);
					}
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamentopagamento);
				parcelamentopagamento = new Parcelamentopagamento();
				calcularParcelamentoPagamento();
				parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
				parcelamentopagamento.setNumeroParcelas(01);
				parcelamentopagamento.setValorParcela(parcelamentopagamento.getValorParcelamento());
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
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
		if (parcelamentopagamento.getDiaVencimento() == null) {
			msg = msg + "Data do 1º Vencimento Obrigatorio";
		}else {
			if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")) {
				String dataAtualString = Formatacao.ConvercaoDataPadrao(new Date());
				Date dataAtual = Formatacao.ConvercaoStringData(dataAtualString);
				if (parcelamentopagamento.getDiaVencimento().before(dataAtual)) {
					msg = msg + "Data deve ser num próximo dia util";
				}
			}
		}
		if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("sn")) {
			msg = msg + "Forma de pagamento não selecionada";
		}
		if (parcelamentopagamento.getValorParcela() <= 0) {
			msg = msg + "Valor da parcela não pode ser 0";
		}
		if (parcelamentopagamento.getValorParcelamento() > (valorSaldoParcelar + 0.01)) {
			msg = msg + "Valor a parcelar mais alto que saldo em aberto.";
		}
		return msg;
	}

	public void calcularParcelamentoPagamento() {
		Float valorParcelado = 0.0f;
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
				valorParcelado = valorParcelado
						+ formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento();
			}
		}
		valorSaldoParcelar = venda.getValor() - valorParcelado;
		parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
	}

	public void excluirFormaPagamento(String ilinha) {
		gerarListaParcelamentoOriginal();
		int linha = Integer.parseInt(ilinha);
		if (linha >= 0) {
			if (formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento() != null) {
				ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
				parcelamentoPagamentoFacade.excluir(
						formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
			}
			ContasReceberBean contasReceberBean = new ContasReceberBean();
			if (venda.getIdvendas() != null) {
				if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
					contasReceberBean.apagarContasReceber(formaPagamento.getParcelamentopagamentoList().get(linha),
							venda.getIdvendas(), usuarioLogadoMB, formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
				}
			}
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
			formaPagamento.getParcelamentopagamentoList().remove(linha);
			calcularParcelamentoPagamento();
			parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
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
		float valorCambio = (float) event.getObject();
		orcamento.setValorCambio(valorCambio);
		venda.setValorcambio(valorCambio);
		atualizarValoresProduto();
	}

	public void carregarComboMoedas() {
		
		listaMoedas = cambioDao.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	}

	public void consultarCambio() {
		if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			int dias = Formatacao.subtrairDatas(new Date(), venda.getDataVenda());
			if (dias > 3) {
				Mensagem.lancarMensagemInfo("", "Cambio alterado para o dia atual");
			}
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			orcamento.setValorCambio(cambio.getValor());
			atualizarValoresProduto();
		} else {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha já finalizada!");
		}
	}

	public void atualizarValoresProduto() {
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produto = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getSeguroOrcamento());
				int idSeguro = produto.getIdprodutosOrcamento();
				if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento() != idSeguro) {
					if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira() > 0) {
						orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(
								orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira()
										* orcamento.getValorCambio());
					}
				}
				calcularValorTotalOrcamento();
			}
		}
	}

	public void calcularValorTotalOrcamento() {
		if (aplicacaoMB.getParametrosprodutos().isRemessaativa()) {
			calcularImpostoRemessa();
		}
		formaPagamento.setValorTotal(0.0f);
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			float valorTotal = 0.0f;
			orcamento.setTotalMoedaEstrangeira(0.0f);
			orcamento.setTotalMoedaNacional(0.0f);
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				float valorMoedaEstrangeira = 0.0f;
				float valorMoedaReal = 0.0f;
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
				} else {
					valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional();
					valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.getValorMoedaEstrangeira();
				}
				valorTotal = valorTotal + valorMoedaReal;
				orcamento.setTotalMoedaEstrangeira(orcamento.getTotalMoedaEstrangeira() + valorMoedaEstrangeira);
				orcamento.setTotalMoedaNacional(orcamento.getTotalMoedaNacional() + valorMoedaReal);
				formaPagamento.setValorOrcamento(valorTotal);
				venda.setValor(valorTotal);
			}
			if (formaPagamento.getPossuiJuros().equalsIgnoreCase("Não")) {
				formaPagamento.setValorJuros(0.0f);
			}
		}
		if (venda.getValor() == null) {
			venda.setValor(0.0f);
		}
		venda.setValor(venda.getValor() + formaPagamento.getValorJuros());
		formaPagamento.setValorTotal(venda.getValor());
		valorSaldoParcelar = formaPagamento.getValorTotal();
		valorMoedaEstrangeira = 0.0f;
		valorMoedaReal = 0.0f;
		calcularParcelamentoPagamento();
	}

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getTrainee());
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

	public void gerarListaProdutos() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getTrainee()
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void adicionarProdutos() {
		if (orcamento.getValorCambio() > 0) {
			if (produtosorcamento != null) {
				if (tipo.equalsIgnoreCase("Australia")) {
					int application = produtosTrainee.getProdutoapplication().getIdprodutosOrcamento();
					int programa = produtosTrainee.getProdutoprograma().getIdprodutosOrcamento();
					int visto = produtosTrainee.getProdutovisto().getIdprodutosOrcamento();
					int seguro = produtosTrainee.getProdutoseguro().getIdprodutosOrcamento();
					if (produtosorcamento.getIdprodutosOrcamento() == application) {
						Mensagem.lancarMensagemErro("" + produtosorcamento.getDescricao() + " já esta incluso.", "");
					} else if (produtosorcamento.getIdprodutosOrcamento() == programa) {
						Mensagem.lancarMensagemErro("" + produtosorcamento.getDescricao() + " já esta incluso.", "");
					} else if (produtosorcamento.getIdprodutosOrcamento() == visto) {
						Mensagem.lancarMensagemErro("" + produtosorcamento.getDescricao() + " já esta incluso.", "");
					} else if (produtosorcamento.getIdprodutosOrcamento() == seguro) {
						Mensagem.lancarMensagemErro("" + produtosorcamento.getDescricao() + " já esta incluso.", "");
					} else {
						orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
						orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
						
						if ((valorMoedaEstrangeira > 0) && (orcamento.getValorCambio() > 0)) {
							valorMoedaReal = valorMoedaEstrangeira * orcamento.getValorCambio() ;
						} else {
							if ((valorMoedaReal > 0) && (orcamento.getValorCambio()  > 0)) {
								valorMoedaEstrangeira = valorMoedaReal / orcamento.getValorCambio() ;
							}
						}
						boolean excluirDescontoTM = true;
						if (produtosorcamento.getValormaximo()==0) {
							orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
							orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
							orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
							calcularValorTotalOrcamento();
							orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
						}else if (produtosorcamento.getValormaximo()>=valorMoedaReal){
							orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
							orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
							orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
							calcularValorTotalOrcamento();
							orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
						}else {
							FacesContext fc = FacesContext.getCurrentInstance();
					        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
					        Map<String, Object> options = new HashMap<String, Object>();
							options.put("contentWidth", 230);
					        session.setAttribute("valorOriginal", 0f);
					        session.setAttribute("novoValor", 0f);
							RequestContext.getCurrentInstance().openDialog("validarTrocaCambioPIN", options, null);
							//Mensagem.lancarMensagemErro("", "Valor máximo permitudo R$ "+ Formatacao.formatarFloatString(produtosorcamento.getValormaximo()));
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
					if ((valorMoedaEstrangeira > 0) && (orcamento.getValorCambio() > 0)) {
						valorMoedaReal = valorMoedaEstrangeira * orcamento.getValorCambio() ;
					} else {
						if ((valorMoedaReal > 0) && (orcamento.getValorCambio()  > 0)) {
							valorMoedaEstrangeira = valorMoedaReal / orcamento.getValorCambio() ;
						}
					}
					if (produtosorcamento.getValormaximo()==0) {
						orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
						orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
						orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
						orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
						calcularValorTotalOrcamento();
						orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
					}else if (produtosorcamento.getValormaximo()>=valorMoedaReal){
						orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
						orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
						orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
						orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
						calcularValorTotalOrcamento();
						orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
					}else {
						FacesContext fc = FacesContext.getCurrentInstance();
				        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				        Map<String, Object> options = new HashMap<String, Object>();
						options.put("contentWidth", 230);
				        session.setAttribute("valorOriginal", 0f);
				        session.setAttribute("novoValor", 0f);
						RequestContext.getCurrentInstance().openDialog("validarTrocaCambioPIN", options, null);
						//Mensagem.lancarMensagemErro("", "Valor máximo permitudo R$ "+ Formatacao.formatarFloatString(produtosorcamento.getValormaximo()));
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
				orcamentoprodutosorcamento.setDescricao(filtroorcamentoproduto.getProdutosorcamento().getDescricao());
				orcamentoprodutosorcamento.setProdutosorcamento(filtroorcamentoproduto.getProdutosorcamento());
				if ((valorMoedaEstrangeira > 0) && (orcamento.getValorCambio() > 0)) {
					valorMoedaReal = valorMoedaEstrangeira * orcamento.getValorCambio();
				} else {
					if ((valorMoedaReal > 0) && (orcamento.getValorCambio() > 0)) {
						valorMoedaEstrangeira = valorMoedaReal / orcamento.getValorCambio();
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

	public void excluirProdutoOrcamento(String linha) {
		int ilinha = Integer.parseInt(linha);
		if (!orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).isPodeExcluir()) {
			Mensagem.lancarMensagemErro("Produto não pode ser excluído.", "");
		} else {
			if (ilinha >= 0) {
				FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
				int idproduto = orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
						.getIdprodutosOrcamento();
				if (idproduto == 33) {
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

	public String cancelarCadastro() {
		if (voltarControleVendas != null) {
			if (voltarControleVendas.length() > 1) {
				return voltarControleVendas;
			}
		}
		return "consTrainee";
	}

	public String naoEnviarficha() throws SQLException {
		enviarFicha = false;
		if (confirmarFichaTrainee()) {
			return "consTrainee";
		}
		return "";
	}

	public String finalizarficha() throws SQLException {
		enviarFicha = true;
		if (confirmarFichaTrainee()) {
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consTrainee";
		}
		return "";
	}
	
	

	public boolean confirmarFichaTrainee() throws SQLException {
		boolean salvarOK = false;
		String msg = validarDados();
		String nsituacao = "";
		if (venda.getIdvendas() != null) {
			nsituacao = venda.getSituacao();
		}
		if (msg.length() < 5) {
			if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
				if (enviarFicha) {
					novaFicha = true;
				}
			} else {
				enviarFicha = true;
			}
			
			if (venda.getIdvendas() == null) {
				nsituacao = "PROCESSO";
			}
			ProgramasBean programasBean = new ProgramasBean();
			this.produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getTrainee());
			float totalMoedaEstrangeira = orcamento.getTotalMoedaEstrangeira();
			float totalMoedaReal = orcamento.getTotalMoedaNacional();
			venda.setValorpais(totalMoedaEstrangeira * cambio.getValor());
			Cambio cambioBrasil = null;
			if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getIdpais() != 5) {
				PaisFacade paisFacade = new PaisFacade();
				Pais pais = paisFacade.consultar(5);
				
				cambioBrasil = cambioDao.consultarCambioMoedaPais(Formatacao.ConvercaoDataSql(pais.getDatacambio()), cambio.getMoedas().getIdmoedas(), pais);
				totalMoedaReal = totalMoedaEstrangeira * cambioBrasil.getValor();
			}
			venda.setValor(totalMoedaReal);
			venda = programasBean.salvarVendas(venda, usuarioLogadoMB, nsituacao, cliente,
					formaPagamento.getValorTotal(), produto, fornecedorCidade, cambio, orcamento.getValorCambio(),
					lead, null, null, vendasDao, leadPosVendaDao, leadDao, leadSituacaoDao);
			CadTraineeBean cadTraineeBean = new CadTraineeBean(venda, formaPagamento, orcamento, usuarioLogadoMB);
			if (enviarFicha) {
				cadTraineeBean.SalvarAlteracaoFinanceiro(listaParcelamentoPagamentoAntiga,
						listaParcelamentoPagamentoOriginal);
			}
			trainee.setControle("Processo");
			trainee.setNumerosemanas(numeroSemanas);
			trainee.setVendas(venda);
			if (tipo.equalsIgnoreCase("Australia")) {
				trainee.setTipotrainee(tipo);
				trainee.setTipoaustralia(tipoaustralia);
			}
			trainee.setValorestrainee(valorestrainee);
			trainee = cadTraineeBean.salvarTrainee(trainee, vendaAlterada);
			float valorCambioBrasil = 0.0f;
			if (cambioBrasil != null) {
				valorCambioBrasil = cambioBrasil.getValor();
			}
			orcamento = cadTraineeBean.salvarOrcamento(cambio, venda.getValorpais(),
					totalMoedaEstrangeira, orcamento.getValorCambio(), cambioAlterado, totalMoedaReal, valorCambioBrasil);
			formaPagamento = cadTraineeBean.salvarFormaPagamento(cancelamento);
			Date data = Formatacao.calcularPrevisaoPagamentoFornecedor(new Date(), venda.getProdutos().getIdprodutos(),
					aplicacaoMB.getParametrosprodutos().getWork());
			cliente = cadTraineeBean.salvarCliente(cliente, Formatacao.ConvercaoDataPadrao(data), null, null);
			if (venda.getSituacao().equalsIgnoreCase("FINALIZADA")  || venda.getSituacao().equalsIgnoreCase("ANDAMENTO"))  {
				int mes = Formatacao.getMesData(new Date()) + 1;
				int mesVenda = Formatacao.getMesData(venda.getDataVenda()) + 1;
				if (enviarFicha) {
					if (mes == mesVenda) {
						
						DashBoardBean dashBoardBean = new DashBoardBean();
						dashBoardBean.calcularMetaMensal(venda, valorVendaAlterar, false);
						dashBoardBean.calcularMetaAnual(venda, valorVendaAlterar, false);
						int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, "", false, venda.getUsuario());
						int pontoremover = vendaAlterada.getPonto();
						ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
						productRunnersCalculosBean.calcularPontuacao(venda, pontos[0], pontoremover, false, venda.getUsuario());
						venda.setPonto(pontos[0]);
						venda.setPontoescola(pontos[1]);
						
						venda = vendasDao.salvar(venda);
						
					}
					String titulo = "";
					String operacao = "";
					String imagemNotificacao = "";
					if (novaFicha) {
						titulo = "Nova Ficha de Trainee. " + venda.getIdvendas();
						operacao = "A";
						imagemNotificacao = "inserido";
					} else {
						titulo = "Ficha de Trainee Alterado. " + venda.getIdvendas();
						operacao = "I";
						imagemNotificacao = "alterado";
					}

					verificarAlteracaoCambio();
					String vm = "Venda pela Matriz";
					if (venda.getVendasMatriz().equalsIgnoreCase("N")) {
						vm = "Venda pela Loja";
					}
					DepartamentoFacade departamentoFacade = new DepartamentoFacade();
					List<Departamento> departamento = departamentoFacade
							.listar("select d From Departamento d where d.usuario.idusuario="
									+ venda.getProdutos().getIdgerente());
					if (departamento != null && departamento.size() > 0) {
						Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(), cliente.getNome(),
								venda.getFornecedorcidade().getFornecedor().getNome(), trainee.getMesano(),
								venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
								venda.getCambio().getMoedas().getSigla(), operacao, departamento.get(0),
								imagemNotificacao, "A");
					}
					// }
					// }.start();
				}
			}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Trainee Salvo com Sucesso", ""));
			salvarOK = true;
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
		}
		return salvarOK;
	}

	public String validarDados() {
		String msg = "";
		if (valorestrainee != null) {
			if (fornecedorCidade == null) {
				msg = msg + "Campo escola não selecionado     ";
			}
			if (cambio == null) {
				msg = msg + "Selecione câmbio da ficha     ";
			}
			if (cliente == null) {
				msg = msg + "Campo cliente não selecionado\r\n";
			}
			if (trainee.getCursoEstuda() == null) {
				msg = msg + "Curso que estuda não informado\r\n";
			}
			if (fornecedorCidade == null) {
				msg = msg + "Escola/Instituição não informada\r\n";
			}
			if (pais == null) {
				msg = msg + "País não informado\r\n";
			}
			if (trainee.getMesano() == null) {
				msg = msg + "Início previsto inválida\r\n";
			}
			if (tipo == "Australia") {
				if (trainee.getNomeContatoEmergencia() == null) {
					msg = msg + "Nome do contato de emergência não informado\r\n";
				}
				if (trainee.getFoneContatoEmergencia() == null) {
					msg = msg + "Nº telefone  do contato de emergência não informado\r\n";
				}
				if (trainee.getRelacaoContatoEmergencia() == null) {
					msg = msg + "Relação do contato de emergência não informado\r\n";
				}
			}
			if (formaPagamento.getParcelamentopagamentoList() == null) {
				msg = msg + "Forma de Pagamento com erro\r\n";
			} else {
				if (formaPagamento.getParcelamentopagamentoList().size() == 0) {
					msg = msg + "Forma de Pagamento com erro\r\n";
				}
			}
			float saldoParcelar = this.valorSaldoParcelar;
			if (saldoParcelar > 0.01f) {
				msg = msg + "Forma de Pagamento possui saldo a parcelar em aberto\r\n";
			}
			
			if (saldoParcelar < -1f) {
				msg = msg + "Saldo a parcelar negativo";
			}
		} else {
			msg = msg + "Campo valores não está preenchido!\r\n";
		}
		return msg;
	}

	public void verificarAlteracaoCambio() {
		if (cambioAlterado.equalsIgnoreCase("sim")) {
			Formatacao.VerificarCambioalterado(orcamento.getValorCambio(), "Curso", cliente.getNome(),
					usuarioLogadoMB.getUsuario().getUnidadenegocio().getNomeFantasia(),
					usuarioLogadoMB.getUsuario().getNome(), this.venda.getProdutos().getIdprodutos(),
					Formatacao.formatarFloatString(this.venda.getValor()));
		}
	}

	public void iniciarAlteracao() {
		this.venda = trainee.getVendas();
		valorVendaAlterar = venda.getValor();
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			vendaAlterada = venda;
		}
		this.cliente = venda.getCliente();
		this.numeroSemanas = trainee.getNumerosemanas();
		carregarTraineeAlterado();
		fornecedorCidade = venda.getFornecedorcidade();
		cidade = fornecedorCidade.getCidade();
		pais = cidade.getPais();
		listarFornecedorCidade();
		fornecedorCidade = venda.getFornecedorcidade();
		carregarValores();
		tipoaustralia = trainee.getTipoaustralia();
		valorestrainee = trainee.getValorestrainee();
		liberarNuemroSemana();
		fornecedorCidadeAlterado = fornecedorCidade;
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		parcelamentopagamento = new Parcelamentopagamento();
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		fornecedorComissao = fornecedorComissaoCursoFacade.consultar(fornecedorCidade.getFornecedor().getIdfornecedor(),
				fornecedorCidade.getCidade().getPais().getIdpais());
		this.formaPagamento = venda.getFormapagamento();
		if (formaPagamento != null) {
			carregarCamposFormaPagamento();
			listaParcelamentoPagamentoAntiga = new ArrayList<>();
			listaParcelamentoPagamentoAntiga = formaPagamento.getParcelamentopagamentoList();
//			for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
//				Parcelamentopagamento parcelamentopagamento = new Parcelamentopagamento();
//				parcelamentopagamento
//						.setDiaVencimento(formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento());
//				parcelamentopagamento
//						.setFormaPagamento(formaPagamento.getParcelamentopagamentoList().get(i).getFormaPagamento());
//				parcelamentopagamento
//						.setNumeroParcelas(formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas());
//				parcelamentopagamento
//						.setValorParcela(formaPagamento.getParcelamentopagamentoList().get(i).getValorParcela());
//				parcelamentopagamento.setValorParcelamento(
//						formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento());
//				parcelamentopagamento.setTipoParcelmaneto(
//						formaPagamento.getParcelamentopagamentoList().get(i).getTipoParcelmaneto());
//				listaParcelamentoPagamentoAntiga.add(parcelamentopagamento);
//			}
		}
		tipo = trainee.getTipotrainee();
		if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
			for (int i = 0; i < valorestrainee.getProdutostraineeList().size(); i++) {
				produtosTrainee = valorestrainee.getProdutostraineeList().get(i);
			}
			numeroSemanas = String.valueOf(produtosTrainee.getNumerosemanas());
			liberarNuemroSemana();
		}
		orcamento = venda.getOrcamento();
		if (orcamento != null) {
//			if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
//				for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
//					if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() < 0) {
//						orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(
//								orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1);
//					}
//					if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
//						verificarProdutosExcluir(orcamento.getOrcamentoprodutosorcamentoList().get(i));
//					}
//				}
//			}
//			if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
//				adicionarProdutosTrainee();
//			}
			dataCambio = orcamento.getCambio().getData();
			cambio = orcamento.getCambio();
			moeda = orcamento.getCambio().getMoedas();
			carregarCambio();
			calcularValorTotalOrcamento();
		}
		tipo = trainee.getTipotrainee();

		consultaCambio = true;
		parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
	}

	public void carregarCamposFormaPagamento() {
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			calcularParcelamentoPagamento();
		}
	}

	public void carregarCambio() {
		if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			//int dias = Formatacao.subtrairDatas(venda.getDatavalidade(), new Date());
			String dataAtualString = Formatacao.ConvercaoDataPadrao(new Date());
			Date dataAtual = Formatacao.ConvercaoStringData(dataAtualString);
			String dataValidadeString = Formatacao.ConvercaoDataPadrao(venda.getDatavalidade());
			Date dataValidade = Formatacao.ConvercaoStringData(dataValidadeString);
			if (dataValidade.before(dataAtual)) {
				int idMoedaVenda = venda.getCambio().getMoedas().getIdmoedas();
				for (int i = 0; i < aplicacaoMB.getListaCambio().size(); i++) {
					int idUltimaMoeda = aplicacaoMB.getListaCambio().get(i).getMoedas().getIdmoedas();
					int idPaisUsuario = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getIdpais();
					int idPaisCambio = aplicacaoMB.getListaCambio().get(i).getPais().getIdpais();
					if ((idMoedaVenda == idUltimaMoeda) && (idPaisCambio == idPaisUsuario)) {
						cambio = aplicacaoMB.getListaCambio().get(i);
						i = 1000000;
					}
				}
				if (cambio != null) {
					habilitarAvisoCambio = true;
					orcamento.setValorCambio(cambio.getValor());
					cambioAlterado = "Não";
					dataCambio = cambio.getData();
					moeda = cambio.getMoedas();
					venda.setValorcambio(cambio.getValor());
					atualizarValoresProduto();
				}
			} else {
				cambioAlterado = orcamento.getCambioAlterado();
			}
		} else {
			cambioAlterado = orcamento.getCambioAlterado();
		}
	}

	public void calcularValorTraineeProdutos() {
		int codValoresTrainee = aplicacaoMB.getParametrosprodutos().getTrainee();
		for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
			int codigoLista = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
					.getIdprodutosOrcamento();
			if (codValoresTrainee == codigoLista) {
				orcamento.getOrcamentoprodutosorcamentoList().remove(i);
			}
		}
		if (valorestrainee != null) {
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
					.pesquisar(aplicacaoMB.getParametrosprodutos().getTrainee(), "Trainee");
			orcamentoprodutosorcamento.setProdutosorcamento(produto.getProdutosorcamento());
			orcamentoprodutosorcamento.setDescricao(produto.getProdutosorcamento().getDescricao());
			if (valorestrainee.getValorgross() > 0) {
				
				Cambio cambioValorTrainee = new Cambio();
				cambioValorTrainee = cambioDao.consultarCambioMoedaPais(Formatacao.ConvercaoDataSql(dataCambio),
						valorestrainee.getMoedas().getIdmoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
				moeda = valorestrainee.getMoedas();
				orcamento.setValorCambio(cambioValorTrainee.getValor());
				cambio = cambioValorTrainee;
				orcamentoprodutosorcamento
						.setValorMoedaNacional(valorestrainee.getValorgross() * cambioValorTrainee.getValor());
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(valorestrainee.getValorgross());
			} else {
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			}
			orcamentoprodutosorcamento.setPodeExcluir(false);
			orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			calcularValorTotalOrcamento();
			calcularParcelamentoPagamento();
		}
	}

	public void calcularValorParcelas() {
		if (parcelamentopagamento.getValorParcelamento() > 0) {
			parcelamentopagamento.setValorParcela(
					parcelamentopagamento.getValorParcelamento() / parcelamentopagamento.getNumeroParcelas());
		}
	}

	public void carregarTraineeAlterado() {
		traineeAlterado = new Trainee();
		traineeAlterado.setCargo(trainee.getCargo());
		traineeAlterado.setCursoEstuda(trainee.getCursoEstuda());
		traineeAlterado.setDescricaoAreaEstudo(trainee.getDescricaoAreaEstudo());
		traineeAlterado.setDescricaoProblemaSaude(trainee.getDescricaoProblemaSaude());
		traineeAlterado.setDuracaoCursoEstuda(trainee.getDuracaoCursoEstuda());
		traineeAlterado.setDuracaoProgramaTrabalho(trainee.getDuracaoProgramaTrabalho());
		traineeAlterado.setEmailContatoEmergencia(trainee.getEmailContatoEmergencia());
		traineeAlterado.setEscolheuPrograma(trainee.getEscolheuPrograma());
		traineeAlterado.setFoiCondebado(trainee.getFoiCondebado());
		traineeAlterado.setFoneContatoEmergencia(trainee.getFoneContatoEmergencia());
		traineeAlterado.setFuma(trainee.getFuma());
		traineeAlterado.setInspencaoPassado(trainee.getInspencaoPassado());
		traineeAlterado.setInstituicaoEstuda(trainee.getInstituicaoEstuda());
		traineeAlterado.setNivelEstudo(trainee.getNivelEstudo());
		traineeAlterado.setNivelIngles(trainee.getNivelIngles());
		traineeAlterado.setNomeContatoEmergencia(trainee.getNomeContatoEmergencia());
		traineeAlterado.setNotaSlepTest(trainee.getNotaSlepTest());
		traineeAlterado.setNumeroSocialSecurity(trainee.getNumeroSocialSecurity());
		traineeAlterado.setObjetivosPrograma(trainee.getObjetivosPrograma());
		traineeAlterado.setObservacaoPassagem(trainee.getObservacaoPassagem());
		traineeAlterado.setOcupacao(trainee.getOcupacao());
		traineeAlterado.setPassagemAerea(trainee.getPassagemAerea());
		traineeAlterado.setPeriodoCursoEstuda(trainee.getPeriodoCursoEstuda());
		traineeAlterado.setPossuiAlergias(trainee.getPossuiAlergias());
		traineeAlterado.setPossuiTatuagem(trainee.getPossuiTatuagem());
		traineeAlterado.setProblemaSaude(trainee.getProblemaSaude());
		traineeAlterado.setProgramaReponsavelEUA(trainee.getProgramaReponsavelEUA());
		traineeAlterado.setQuantoAnosEstudaIngles(trainee.getQuantoAnosEstudaIngles());
		traineeAlterado.setQuantoTrabalho(trainee.getQuantoTrabalho());
		traineeAlterado.setRelacaoContatoEmergencia(trainee.getRelacaoContatoEmergencia());
		traineeAlterado.setTesteDrogas(trainee.getTesteDrogas());
		traineeAlterado.setTrabalhoj1(trainee.getTrabalhoj1());
		traineeAlterado.setMesano(trainee.getMesano());
	}

	public void carregarCamposTrainee() {
		trainee.setCargo("");
		trainee.setCursoEstuda("");
		trainee.setDescricaoAreaEstudo("");
		trainee.setDescricaoProblemaSaude("");
		trainee.setDuracaoCursoEstuda("");
		trainee.setDuracaoProgramaTrabalho("");
		trainee.setEmailContatoEmergencia("");
		trainee.setEscolheuPrograma("");
		trainee.setFoiCondebado("");
		trainee.setFoneContatoEmergencia("");
		trainee.setFuma("");
		trainee.setInspencaoPassado("");
		trainee.setInstituicaoEstuda("");
		trainee.setNivelEstudo("");
		trainee.setNivelIngles("");
		trainee.setNomeContatoEmergencia("");
		trainee.setNotaSlepTest("");
		trainee.setNumeroSocialSecurity("");
		trainee.setObjetivosPrograma("");
		trainee.setObservacaoPassagem("");
		trainee.setOcupacao("");
		trainee.setPassagemAerea("");
		trainee.setPeriodoCursoEstuda("");
		trainee.setPossuiAlergias("");
		trainee.setPossuiTatuagem("");
		trainee.setProblemaSaude("");
		trainee.setProgramaReponsavelEUA("");
		trainee.setQuantoAnosEstudaIngles(0);
		trainee.setQuantoTrabalho("");
		trainee.setRelacaoContatoEmergencia("");
		trainee.setTesteDrogas("");
		trainee.setTrabalhoj1("");
		if (!tipo.equalsIgnoreCase("Australia")) {
			trainee.setTipoaustralia("");
		}
	}

	public void consultarValores() {
		if (fornecedorCidade != null && Integer.parseInt(numeroSemanas) > 0 && tipoaustralia != null
				&& tipoaustralia.length() > 3) {
			ProdutosTraineeFacade produtosTraineeFacade = new ProdutosTraineeFacade();
			String sql;
			sql = "select p from Produtostrainee p where p.valorestrainee.situacao='Ativo' and p.numerosemanas="
					+ numeroSemanas + " and p.tipoestagio='" + tipoaustralia + "'";
			produtosTrainee = produtosTraineeFacade.consultar(sql);
			if (produtosTrainee != null) {
				valorestrainee = produtosTrainee.getValorestrainee();
				adicionarProdutosTrainee();
				fornecedorCidade = valorestrainee.getFornecedorcidade();
				cidade = fornecedorCidade.getCidade();
				pais = cidade.getPais();
			} else {
				produtosTrainee = new Produtostrainee();
				produtosTrainee.setValortotal(0.0f);
				for (int i = orcamento.getOrcamentoprodutosorcamentoList().size() - 1; i >= 0; i--) {
					if (!orcamento.getOrcamentoprodutosorcamentoList().get(i).isPodeExcluir()) {
						orcamento.getOrcamentoprodutosorcamentoList().remove(i);
					}
				}
				calcularValorTotalOrcamento();
				Mensagem.lancarMensagemErro("Atenção", "Informações anteriores não possuem valor!");
			}
		}
	}

	public void adicionarProdutosTrainee() {
		cambio = new Cambio();
		cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(),
				produtosTrainee.getValorestrainee().getMoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
		moeda = produtosTrainee.getValorestrainee().getMoedas();
		orcamento.setValorCambio(cambio.getValor());
		for (int i = orcamento.getOrcamentoprodutosorcamentoList().size() - 1; i >= 0; i--) {
			if (!orcamento.getOrcamentoprodutosorcamentoList().get(i).isPodeExcluir()) {
				orcamento.getOrcamentoprodutosorcamentoList().remove(i);
			}
		}
		orcamentoprodutosorcamento.setProdutosorcamento(produtosTrainee.getProdutoapplication());
		orcamentoprodutosorcamento.setDescricao(produtosTrainee.getProdutoapplication().getDescricao());
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(produtosTrainee.getValorapplication());
		orcamentoprodutosorcamento.setPodeExcluir(false);
		orcamentoprodutosorcamento.setValorMoedaNacional(produtosTrainee.getValorapplication() * cambio.getValor());
		orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);

		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		orcamentoprodutosorcamento.setProdutosorcamento(produtosTrainee.getProdutoprograma());
		orcamentoprodutosorcamento.setDescricao(produtosTrainee.getProdutoprograma().getDescricao());
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(produtosTrainee.getValorprograma());
		orcamentoprodutosorcamento.setPodeExcluir(false);
		orcamentoprodutosorcamento.setValorMoedaNacional(produtosTrainee.getValorprograma() * cambio.getValor());
		orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);

		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		orcamentoprodutosorcamento.setProdutosorcamento(produtosTrainee.getProdutovisto());
		orcamentoprodutosorcamento.setDescricao(produtosTrainee.getProdutovisto().getDescricao());
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(produtosTrainee.getValorvisto());
		orcamentoprodutosorcamento.setValorMoedaNacional(produtosTrainee.getValorvisto() * cambio.getValor());
		orcamentoprodutosorcamento.setPodeExcluir(false);
		orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);

		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		orcamentoprodutosorcamento.setProdutosorcamento(produtosTrainee.getProdutoseguro());
		orcamentoprodutosorcamento.setDescricao(produtosTrainee.getProdutoseguro().getDescricao());
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(produtosTrainee.getValorseguro());
		orcamentoprodutosorcamento.setValorMoedaNacional(produtosTrainee.getValorseguro() * cambio.getValor());
		orcamentoprodutosorcamento.setPodeExcluir(false);
		orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		calcularValorTotalOrcamento();
	}

	public void verificarProdutosExcluir(Orcamentoprodutosorcamento orcamentoprodutosorcamento) {
		int application = produtosTrainee.getProdutoapplication().getIdprodutosOrcamento();
		int programa = produtosTrainee.getProdutoprograma().getIdprodutosOrcamento();
		int visto = produtosTrainee.getProdutovisto().getIdprodutosOrcamento();
		int seguro = produtosTrainee.getProdutoseguro().getIdprodutosOrcamento();
		if (orcamentoprodutosorcamento.getProdutosorcamento().getIdprodutosOrcamento() == application) {
			orcamentoprodutosorcamento.setPodeExcluir(false);
		} else if (orcamentoprodutosorcamento.getProdutosorcamento().getIdprodutosOrcamento() == programa) {
			orcamentoprodutosorcamento.setPodeExcluir(false);
		} else if (orcamentoprodutosorcamento.getProdutosorcamento().getIdprodutosOrcamento() == visto) {
			orcamentoprodutosorcamento.setPodeExcluir(false);
		} else if (orcamentoprodutosorcamento.getProdutosorcamento().getIdprodutosOrcamento() == seguro) {
			orcamentoprodutosorcamento.setPodeExcluir(false);
		}
	}

	public void habilitarJ1() {
		if (trainee.getTrabalhoj1().equalsIgnoreCase("Sim")) {
			camposj1 = "false";
		} else
			camposj1 = "true";
	}

	public void gerarListaTipoParcelamento() {
		listaTipoParcelamento = Formatacao.gerarListaTipoParcelamento(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().isParcelamentojoja(),
				parcelamentopagamento.getFormaPagamento(),
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
	}

	public void gerarListaParcelamentoOriginal() {
		if (venda.getIdvendas() != null) {
			if (listaParcelamentoPagamentoOriginal == null) {
				if (formaPagamento.getParcelamentopagamentoList() != null) {
					listaParcelamentoPagamentoOriginal = new ArrayList<Parcelamentopagamento>();
					listaParcelamentoPagamentoOriginal = formaPagamento.getParcelamentopagamentoList();
				}
			}
		}
	}

	public String validarMascaraFoneContatoEmergencia() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneContatoEmergencia);
	}

	public String selecionarCreditoCancelamento() {
		if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Credito")) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			int idcliente = cliente.getIdcliente();
			session.setAttribute("idcliente", String.valueOf(idcliente));
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("closable", false);
			RequestContext.getCurrentInstance().openDialog("utilizarCredito", options, null);
		}
		return "";
	}

	public void retornoSelecionarCancelamento(SelectEvent event) {
		if (event.getObject() != null) {
			cancelamento = (Cancelamento) event.getObject();
			parcelamentopagamento.setDiaVencimento(new Date());
			parcelamentopagamento.setValorParcelamento(cancelamento.getTotalreembolso());
			parcelamentopagamento.setNumeroParcelas(1);
			parcelamentopagamento.setValorParcela(parcelamentopagamento.getValorParcelamento());
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

	public void liberarNuemroSemana() {
		if (trainee == null  || trainee.getIdtrainee() == null) {
			this.numeroSemanas = "0";
		}
		if (tipoaustralia.equalsIgnoreCase("Todas as Áreas")) {
			todosnumeros = false;
			numero20 = false;
			numero21 = false;
			numero22 = false;
			numero23 = false;
			numero24 = false;
			numero25 = false;
			numero26 = false;
			numero38 = true;
			numero52 = true;
		} else if (tipoaustralia.equalsIgnoreCase("Hotelaria e Gastronomia")) {
			todosnumeros = true;
			numero20 = true;
			numero21 = true;
			numero22 = true;
			numero23 = true;
			numero24 = true;
			numero25 = true;
			numero26 = false;
			numero38 = false;
			numero52 = false;
		} else if (tipoaustralia.equalsIgnoreCase("Negócios")) {
			todosnumeros = true;
			numero20 = false;
			numero21 = false;
			numero22 = false;
			numero23 = false;
			numero24 = false;
			numero25 = false;
			numero26 = false;
			numero38 = true;
			numero52 = true;
		}
	}
	
	
	public void fecharNotificacao() {
		habilitarAvisoCambio = false;
	}
	
	

	public void verificarPaisUnidade() {
		if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getNome().equalsIgnoreCase("Paraguai")) {
			mascara = false;
			semmascara = true;
		}else {
			mascara = true;
			semmascara = false;
		}
	}
}
