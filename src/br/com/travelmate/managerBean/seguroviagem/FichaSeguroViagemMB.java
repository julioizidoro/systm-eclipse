package br.com.travelmate.managerBean.seguroviagem;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
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
import br.com.travelmate.bean.comissao.ComissaoSeguroBean;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadPosVendaDao;
import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.SeguroPlanosFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.ValorSeguroFacade;
import br.com.travelmate.facade.VendasComissaoFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Controleseguro;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Seguroplanos;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class FichaSeguroViagemMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	private Seguroviagem seguro;
	private Seguroviagem seguroAlterado;
	private Cliente cliente;
	private Vendas vendas;
	private Cambio cambio;
	private String cambioAlterado = "Não";
	private float valorCambio;
	private float valorTotal = 0;
	private float totalPagar = 0;
	private float valorEntrada = 0;
	private float valorParcelar = 0;
	private float valorParcela = 0;
	private float valorSaltoParcelar = 0;
	private String numeroParcelas;
	private Date dataPrimeiroPagamento;
	private String tipoParcelamento;
	private String formaPagamentoString;
	private Formapagamento formaPagamento;
	private String situacao = "PROCESSO";
	private Fornecedorcidade fornecedorcidade;
	private Fornecedorcidade fornecedorCidadeAlterado;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Valoresseguro valoresseguro;
	private Vendas vendaAlterada;
	private Seguroviagem seguroViagemAlterado;
	private boolean novaFicha = false;
	private float valorVendaAlterar = 0.0f;
	private boolean enviarFicha;
	private Orcamento orcamento;
	private String dadosAlterado;
	private List<Valoresseguro> listaValoresSeguro;
	private Date dataCambio;
	private int idade;
	float valorSemDesconto;
	private boolean digitosFoneContatoEmergencia;
	private Cancelamento cancelamento;
	private Lead lead;
	private String voltarControleVendas = "";
	private List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga;
	private List<Seguroplanos> listaSeguroPlanos;
	private Seguroplanos seguroplanos;
	private List<String> listaTipoParcelamento;
	private boolean segurocancelamento=false;
	private String numero="3";

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		seguro = (Seguroviagem) session.getAttribute("seguro");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("seguro");
		String vendaMatriz = (String) session.getAttribute("vendaMatriz");
		session.removeAttribute("vendaMatriz");
		iniciarListaFornecedorCidade(); 
		if (seguro == null) {
			seguro = new Seguroviagem();
			seguro.setDescontoloja(0.0f);
			seguro.setDescontoloja(0.0f);
			vendas = new Vendas();
			formaPagamento = new Formapagamento();
			formaPagamento.setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
			cambio = new Cambio();
			cliente = new Cliente();
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
			orcamento = new Orcamento();
			vendas.setVendasMatriz(vendaMatriz);
			CambioFacade cambioFacade = new CambioFacade();
			cambio = cambioFacade.consultarSigla(Formatacao.ConvercaoDataSql(dataCambio), "USD");
			valorSemDesconto = 0.0f;
			seguroplanos = new Seguroplanos();
			novaFicha = true;
		} else {
			novaFicha = false;
			vendas = seguro.getVendas();
			valorVendaAlterar = seguro.getVendas().getValor();
			FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
			this.formaPagamento = formaPagamentoFacade.consultar(vendas.getIdvendas());
			if (formaPagamento != null) {
				carregarCamposFormaPagamento();
				listaParcelamentoPagamentoAntiga = new ArrayList<>();
				for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
					Parcelamentopagamento parcelamentopagamento = new Parcelamentopagamento();
					parcelamentopagamento
							.setDiaVencimento(formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento());
					parcelamentopagamento.setFormaPagamento(
							formaPagamento.getParcelamentopagamentoList().get(i).getFormaPagamento());
					parcelamentopagamento.setNumeroParcelas(
							formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas());
					parcelamentopagamento
							.setValorParcela(formaPagamento.getParcelamentopagamentoList().get(i).getValorParcela());
					parcelamentopagamento.setValorParcelamento(
							formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento());
					parcelamentopagamento.setTipoParcelmaneto(
							formaPagamento.getParcelamentopagamentoList().get(i).getTipoParcelmaneto());
					listaParcelamentoPagamentoAntiga.add(parcelamentopagamento);
				}
			}
			cliente = vendas.getCliente();
			fornecedorcidade = vendas.getFornecedorcidade();
			listarPlanosSeguro();
			seguroplanos = seguro.getValoresseguro().getSeguroplanos();
			listarValoresSeguro();
			valoresseguro = seguro.getValoresseguro();
			OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
			orcamento = orcamentoFacade.consultar(seguro.getVendas().getIdvendas());
			if (orcamento != null) {
				if (orcamento.getDataCambio() != null) {
					dataCambio = orcamento.getCambio().getData();
				}
			} else {
				orcamento = new Orcamento();
			}
			if (dataCambio == null) {
				dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
			}
			valorSemDesconto = seguro.getValorSeguro() + seguro.getDescontoloja() + seguro.getDescontomatriz();
			verificarSeguroCancelamento();
			seguroAlterado = seguro;
		}
		CambioFacade cambioFacade = new CambioFacade();
		cambio = cambioFacade.consultarSigla(Formatacao.ConvercaoDataSql(dataCambio), "USD");
		digitosFoneContatoEmergencia = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
				seguro.getFoneContatoEmergencia());
		valorCambio = cambio.getValor();
		orcamento.setValorCambio(valorCambio);
		gerarListaTipoParcelamento();
	}

	public float getValorSemDesconto() {
		return valorSemDesconto;
	}

	public void setValorSemDesconto(float valorSemDesconto) {
		this.valorSemDesconto = valorSemDesconto;
	}

	public List<Valoresseguro> getListaValoresSeguro() {
		return listaValoresSeguro;
	}

	public void setListaValoresSeguro(List<Valoresseguro> listaValoresSeguro) {
		this.listaValoresSeguro = listaValoresSeguro;
	}

	public Date getDataCambio() {
		return dataCambio;
	}

	public void setDataCambio(Date dataCambio) {
		this.dataCambio = dataCambio;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Seguroviagem getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguroviagem seguro) {
		this.seguro = seguro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Seguroviagem getSeguroAlterado() {
		return seguroAlterado;
	}

	public void setSeguroAlterado(Seguroviagem seguroAlterado) {
		this.seguroAlterado = seguroAlterado;
	}

	public String getCambioAlterado() {
		return cambioAlterado;
	}

	public void setCambioAlterado(String cambioAlterado) {
		this.cambioAlterado = cambioAlterado;
	}

	public Fornecedorcidade getFornecedorCidadeAlterado() {
		return fornecedorCidadeAlterado;
	}

	public void setFornecedorCidadeAlterado(Fornecedorcidade fornecedorCidadeAlterado) {
		this.fornecedorCidadeAlterado = fornecedorCidadeAlterado;
	}

	public String getDadosAlterado() {
		return dadosAlterado;
	}

	public void setDadosAlterado(String dadosAlterado) {
		this.dadosAlterado = dadosAlterado;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public String getVoltarControleVendas() {
		return voltarControleVendas;
	}

	public void setVoltarControleVendas(String voltarControleVendas) {
		this.voltarControleVendas = voltarControleVendas;
	}

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoAntiga() {
		return listaParcelamentoPagamentoAntiga;
	}

	public void setListaParcelamentoPagamentoAntiga(List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga) {
		this.listaParcelamentoPagamentoAntiga = listaParcelamentoPagamentoAntiga;
	}

	public boolean isSegurocancelamento() {
		return segurocancelamento;
	}

	public void setSegurocancelamento(boolean segurocancelamento) {
		this.segurocancelamento = segurocancelamento;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Valoresseguro getValoresseguro() {
		return valoresseguro;
	}

	public void setValoresseguro(Valoresseguro valoresseguro) {
		this.valoresseguro = valoresseguro;
	}

	public float getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(float totalPagar) {
		this.totalPagar = totalPagar;
	}

	public float getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(float valorEntrada) {
		this.valorEntrada = valorEntrada;
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

	public float getValorSaltoParcelar() {
		return valorSaltoParcelar;
	}

	public void setValorSaltoParcelar(float valorSaltoParcelar) {
		this.valorSaltoParcelar = valorSaltoParcelar;
	}

	public String getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(String numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
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

	public String getFormaPagamentoString() {
		return formaPagamentoString;
	}

	public void setFormaPagamentoString(String formaPagamentoString) {
		this.formaPagamentoString = formaPagamentoString;
	}

	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Vendas getVendaAlterada() {
		return vendaAlterada;
	}

	public void setVendaAlterada(Vendas vendaAlterada) {
		this.vendaAlterada = vendaAlterada;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public Seguroviagem getSeguroViagemAlterado() {
		return seguroViagemAlterado;
	}

	public void setSeguroViagemAlterado(Seguroviagem seguroViagemAlterado) {
		this.seguroViagemAlterado = seguroViagemAlterado;
	}

	public boolean isNovaFicha() {
		return novaFicha;
	}

	public void setNovaFicha(boolean novaFicha) {
		this.novaFicha = novaFicha;
	}

	public float getValorVendaAlterar() {
		return valorVendaAlterar;
	}

	public void setValorVendaAlterar(float valorVendaAlterar) {
		this.valorVendaAlterar = valorVendaAlterar;
	}

	public boolean isEnviarFicha() {
		return enviarFicha;
	}

	public void setEnviarFicha(boolean enviarFicha) {
		this.enviarFicha = enviarFicha;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
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

	public List<String> getListaTipoParcelamento() {
		return listaTipoParcelamento;
	}

	public void setListaTipoParcelamento(List<String> listaTipoParcelamento) {
		this.listaTipoParcelamento = listaTipoParcelamento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String pesquisarCliente() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultarCliente", options, null);
		return "";
	}

	public void calcularDataTermino() {
		if (cliente != null) {
			if ((seguroplanos.getIdademinima() > 0 || seguroplanos.getIdademaxima() > 0)) {
				int idadeCliente = Formatacao.calcularIdade(cliente.getDataNascimento());
				if ((idadeCliente < seguroplanos.getIdademinima()) || (idadeCliente>= seguroplanos.getIdademaxima()) ) {
						Mensagem.lancarMensagemInfo("Atenção",
								"emissão não permitida para cliente com " + idadeCliente + " anos");
				} else {
						if (seguro.getNumeroSemanas() != null && (seguro.getNumeroSemanas()< seguroplanos.getDiasemissaominima())) {
							Mensagem.lancarMensagemInfo("Atenção",
									"Mínimo e dias para emissão : " + seguroplanos.getDiasemissaominima() + " dias");
						}
					dataTermino();
				}
			}else{
				if (seguro.getNumeroSemanas() != null && (seguro.getNumeroSemanas()< seguroplanos.getDiasemissaominima())) {
					Mensagem.lancarMensagemInfo("Atenção",
							"Mínimo e dias para emissão : " + seguroplanos.getDiasemissaominima() + " dias");
				}else{
					dataTermino();
				}
			}
		} else 	Mensagem.lancarMensagemErro("Atenção", "Cliente não selecionado");
	}

	public void dataTermino() {
		seguro.setValoresseguro(valoresseguro);
		if ((seguro.getDataInicio() != null) && (seguro.getNumeroSemanas() != null && seguro.getNumeroSemanas() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					valoresseguro.getMoedas().getIdmoedas());
			if (cambioSeguro != null) {
				seguro.setDataTermino(
						Formatacao.calcularDataFinalPorDias(seguro.getDataInicio(), seguro.getNumeroSemanas()));
				float valornacional = seguro.getValoresseguro().getValorgross() * cambioSeguro.getValor();
				if (valoresseguro.getCobranca().equalsIgnoreCase("Fixo")) {
					seguro.setValorSeguro(valornacional);
				} else {
					seguro.setValorSeguro(valornacional * seguro.getNumeroSemanas());
				}
				valorSemDesconto = seguro.getValorSeguro();
				aplicarDesconto();
				seguroCancelamento();
				receberValorTotal();
			}
		}
	}

	public void dataTermino70Anos() {
		if ((seguro.getDataInicio() != null) && (seguro.getNumeroSemanas() != null && seguro.getNumeroSemanas() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					valoresseguro.getMoedas().getIdmoedas());
			if (cambioSeguro != null) {
				seguro.setDataTermino(
						Formatacao.calcularDataFinalPorDias(seguro.getDataInicio(), seguro.getNumeroSemanas()));  
				seguro.setDataTermino(
						Formatacao.calcularDataFinalPorDias(seguro.getDataInicio(), seguro.getNumeroSemanas()));
				float valornacional = seguro.getValoresseguro().getValorgross() * cambioSeguro.getValor(); 
				if (valoresseguro.getCobranca().equalsIgnoreCase("Fixo")) {
					float adicional = valornacional * 0.5f;
					seguro.setValorSeguro(valornacional+adicional);
				} else { 
					float total = (valornacional * seguro.getNumeroSemanas());
					float adicional = total * 0.5f;
					seguro.setValorSeguro(total+adicional);
				}
				valorSemDesconto = seguro.getValorSeguro();
				aplicarDesconto();
				seguroCancelamento();
				receberValorTotal();
			}
		}
	}

	public void calcularNumeroDiasSeguro() {
		if ((seguro.getDataInicio() != null) && (seguro.getDataTermino() != null)) {
			int numeroDias = Formatacao.subtrairDatas(seguro.getDataInicio(), seguro.getDataTermino());
			if (numeroDias < 0) {
				numeroDias = numeroDias * -1;
			}
			seguro.setNumeroSemanas(numeroDias + 1);
			calcularDataTermino();
		}
	}

	public void aplicarDesconto() {
		seguro.setValorSeguro(valorSemDesconto - (seguro.getDescontoloja() + seguro.getDescontomatriz())); 
	}

	public void iniciarListaFornecedorCidade() {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = (int) aplicacaoMB.getParametrosprodutos().getCartao01();
		List<Paisproduto> listaPais = paisProdutoFacade.listar(idProduto);
		if (listaPais != null) {
			listaFornecedorCidade = listaPais.get(0).getProdutos().getFornecedorcidadeList();
		}
	}

	public void receberValorTotal() {
		if (formaPagamento == null) {
			formaPagamento = new Formapagamento();
			formaPagamento.setValorJuros(0.0f);
		}
		if (seguro.getValorSeguro() != null) {
			totalPagar = seguro.getValorSeguro();
			formaPagamento.setValorOrcamento(seguro.getValorSeguro());
			if (formaPagamento.getValorJuros() != null) {
				totalPagar = totalPagar + formaPagamento.getValorJuros();
			}
			valorSaltoParcelar = totalPagar;
			valorParcelar = valorSaltoParcelar;
			calcularParcelamentoPagamento();
		}

	}

	// Forma de pagamento
	public void calcularValorTotalOrcamento() {
		if (seguro.getValorSeguro() != null) {
			valorTotal = 0.0f;
			valorTotal = seguro.getValorSeguro();
			if (formaPagamento == null) {
				formaPagamento = new Formapagamento();
			}
			formaPagamento.setValorOrcamento(valorTotal);
			totalPagar = valorTotal;
			if (formaPagamento.getPossuiJuros().equalsIgnoreCase("Sim")) {
				totalPagar = valorTotal + formaPagamento.getValorJuros();
			} else {
				formaPagamento.setValorJuros(0.0f);
			}
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
			valorSaltoParcelar = totalPagar - valorParcelado;
			valorParcelar = valorSaltoParcelar;
		}
	}

	public void calcularValorParcelas() {
		if (valorParcelar > 0) {
			int numero = Integer.parseInt(numeroParcelas);
			float vParcela = valorParcelar / numero;
			valorParcela = vParcela;
		}
	}

	public void adicionarFormaPagamento() {
		String msg = validarFormaPagamento();
		if (msg.length() < 5) {
			float saldoParcelar = this.valorParcelar;
			float valorParcela = this.valorParcela;
			if (valorParcela > saldoParcelar) {
			} else {
				Parcelamentopagamento parcelamento = new Parcelamentopagamento();
				parcelamento.setDiaVencimento(dataPrimeiroPagamento);
				parcelamento.setFormaPagamento(formaPagamentoString);
				int numeroParcelas = Integer.parseInt(this.numeroParcelas);
				parcelamento.setNumeroParcelas(numeroParcelas);
				parcelamento.setTipoParcelmaneto(tipoParcelamento);
				parcelamento.setValorParcela(valorParcela);
				parcelamento.setValorParcelamento(saldoParcelar);
				if (formaPagamento.getParcelamentopagamentoList() == null) {
					formaPagamento.setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
				}
				if (formaPagamento != null) {
					parcelamento.setFormapagamento(formaPagamento);
				}
				if (vendas.getIdvendas() != null) {
					if (!vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamento = contasReceberBean.gerarParcelasIndividuais(parcelamento,
								formaPagamento.getParcelamentopagamentoList().size(), vendas, usuarioLogadoMB, seguro.getDataInicio());
					}
				}
				if (parcelamento.getFormaPagamento().equalsIgnoreCase("Boleto")) {
					DataVencimentoBean dataVencimentoBean = new DataVencimentoBean(parcelamento.getDiaVencimento());
					parcelamento.setDiaVencimento(dataVencimentoBean.validarDataVencimento());
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamento);
				calcularParcelamentoPagamento();
				valorParcela = 0;
				saldoParcelar = 0;
				this.valorParcela = 0;
				this.dataPrimeiroPagamento = null;
				this.formaPagamentoString = "sn";
				this.numeroParcelas = "00";
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
		if (numeroParcelas.equalsIgnoreCase("00")) {
			msg = msg + "Informe o número de parcelas";
		}
		if (valorParcela == 0) {
			msg = msg + "Informe o valor das parcelas";
		}
		if (formaPagamento.getPossuiJuros().equalsIgnoreCase("Sim")) {
			if (formaPagamento.getValorJuros() != null) {
				if (formaPagamento.getValorJuros() == 0) {
					msg = msg + "Informar valor do Juros";
				}
			}

		}
		if (valorParcelar > (valorSaltoParcelar + 0.01)) {
			msg = msg + "Valor a parcelar mais alto que saldo em aberto.";
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
		if (formaPagamentoString == null || formaPagamentoString.equalsIgnoreCase("sn")) {
			msg = msg + " Informa a forma de pagamento.";
		}
		return msg;
	}

	public void excluirformaPagamento(String ilinha) {
		int linha = Integer.parseInt(ilinha);
		if (linha >= 0) {
			if (formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento() != null) {
				ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
				parcelamentoPagamentoFacade.excluir(
						formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
			}
			ContasReceberBean contasReceberBean = new ContasReceberBean();
			if (vendas.getIdvendas() != null) {
				if (!vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
					contasReceberBean.apagarContasReceber(formaPagamento.getParcelamentopagamentoList().get(linha),
							vendas.getIdvendas(), usuarioLogadoMB, formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
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
		}
	}

	public String naoEnviarficha() throws SQLException {
		enviarFicha = false;
		if (confirmarFicha()) {
			return "consultaSeguro";
		}
		return "";
	}

	public String finalizarficha() throws SQLException {
		enviarFicha = true;
		enviarFicha = true;
		if (confirmarFicha()) {
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consultaSeguro";
		}
		return "";
	}

	public boolean confirmarFicha() throws SQLException {
		boolean salvarOK = false;
		String msg = validarDados();
		String nsituacao;
		if (msg.length() < 5) {
			if (!situacao.equalsIgnoreCase("PROCESSO")) {
				enviarFicha = true;
			} else {
			}
			if (enviarFicha) {
				nsituacao = "FINALIZADA";
			} else {
				nsituacao = "PROCESSO";
			}
			ProgramasBean programasBean = new ProgramasBean();
			Produtos produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getSeguroPrivado());
			vendas.setValor(totalPagar);
			vendas = programasBean.salvarVendas(vendas, usuarioLogadoMB, nsituacao, cliente, vendas.getValor(), produto,
					valoresseguro.getFornecedorcidade(), cambio, orcamento.getValorCambio(), lead, seguro.getDataInicio(), seguro.getDataTermino(), 
					vendasDao, leadPosVendaDao, leadDao, leadSituacaoDao);
			salvarSeguro();
			orcamento = programasBean.salvarOrcamento(orcamento, cambio, totalPagar, 0.0f, valorCambio, vendas,
					cambioAlterado);
			formaPagamento = programasBean.salvarFormaPagamento(formaPagamento, vendas);
			salvarCliente();
			if (cancelamento != null) {
				programasBean.salvarCancelamentoVenda(vendas, cancelamento);
			}
			calcularComissao();
			if (novaFicha) {
				ContasReceberBean contasReceberBean = new ContasReceberBean(vendas,
						formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, false, seguro.getDataInicio());
			} else {
				if (nsituacao.equalsIgnoreCase("FINALIZADA")) {
					calcularComissao();
				}
			}
			if (enviarFicha && !novaFicha) {
				if (listaParcelamentoPagamentoAntiga != null && formaPagamento.getParcelamentopagamentoList() != null) {
					programasBean.salvarAlteracaoFinanceiro(vendas, listaParcelamentoPagamentoAntiga,
							formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB.getUsuario());
				}
			}
			if (novaFicha) {
				if (enviarFicha) {
					if (vendaAlterada == null || vendaAlterada.getIdvendas() == null) {
						if (vendas.getVendasMatriz().equalsIgnoreCase("S")) {
							

							DashBoardBean dashBoardBean = new DashBoardBean();
							dashBoardBean.calcularNumeroVendasProdutos(vendas, false);
							dashBoardBean.calcularMetaMensal(vendas, 0, false);
							dashBoardBean.calcularMetaAnual(vendas, 0, false);
							int[] pontos = dashBoardBean.calcularPontuacao(vendas, 0, "", false, vendas.getUsuario());
							int pontoremover = 0;
							if (vendaAlterada!=null) {
								pontoremover = vendaAlterada.getPonto();
							};
							ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
							productRunnersCalculosBean.calcularPontuacao(vendas, pontos[0], pontoremover, false, vendas.getUsuario());
							vendas.setPonto(pontos[0]);
							vendas.setPontoescola(pontos[1]);
							
							vendas = vendasDao.salvar(vendas);
							String titulo = "Nova Ficha de Seguro";
							String operacao = "A";
							String imagemNotificacao = "inserido";

							if (novaFicha) {
								titulo = "Nova Ficha de Seguro";
								operacao = "I";
								imagemNotificacao = "inserido";
							} else {
								titulo = "Ficha de Seguro Alterada";
								operacao = "A";
								imagemNotificacao = "alterado";
								verificarDadosAlterado();
							}
							if (seguro.isSegurocancelamento()) {
								titulo = titulo + " Com Cancelamento";
							}
							String vm = "Venda pela Matriz";
							if (vendas.getVendasMatriz().equalsIgnoreCase("N")) {
								vm = "Venda pela Loja";
							}
							DepartamentoFacade departamentoFacade = new DepartamentoFacade();
							List<Departamento> departamento = departamentoFacade
									.listar("select d From Departamento d where d.usuario.idusuario="
											+ vendas.getProdutos().getIdgerente());
							if (departamento != null && departamento.size() > 0) {
								Formatacao.gravarNotificacaoVendas(titulo, vendas.getUnidadenegocio(), cliente.getNome(),
										vendas.getFornecedorcidade().getFornecedor().getNome(),
										Formatacao.ConvercaoDataPadrao(seguro.getDataInicio()), vendas.getUsuario().getNome(), vm, vendas.getValor(),
										vendas.getValorcambio(), vendas.getCambio().getMoedas().getSigla(), operacao,
										departamento.get(0), imagemNotificacao, "I");
							}
						}
					}
				}
			} else if (valorVendaAlterar != vendas.getValor()) {
				int mes = Formatacao.getMesData(new Date()) + 1;
				int mesVenda = Formatacao.getMesData(vendas.getDataVenda()) + 1;
				if (mes == mesVenda) {
					if (enviarFicha) {
						

						DashBoardBean dashBoardBean = new DashBoardBean();
						dashBoardBean.calcularMetaMensal(vendas, valorVendaAlterar, false);
						dashBoardBean.calcularMetaAnual(vendas, valorVendaAlterar, false);
						int[] pontos = dashBoardBean.calcularPontuacao(vendas, 0, "", false, vendas.getUsuario());
						int pontoremover = vendaAlterada.getPonto();
						ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
						productRunnersCalculosBean.calcularPontuacao(vendas, pontos[0], pontoremover, false, vendas.getUsuario());
						vendas.setPonto(pontos[0]);
						vendas.setPontoescola(pontos[1]);
						
						vendas = vendasDao.salvar(vendas);
						
						String titulo = "Nova Ficha de High School";
						String operacao = "A";
						String imagemNotificacao = "inserido";

						if (novaFicha) {
							titulo = "Nova Ficha de Curso";
							operacao = "I";
							imagemNotificacao = "inserido";
						} else {
							titulo = "Ficha de Curso Alterada";
							operacao = "A";
							imagemNotificacao = "alterado";
							verificarDadosAlterado();
						}
						String vm = "Venda pela Matriz";
						if (vendas.getVendasMatriz().equalsIgnoreCase("N")) {
							vm = "Venda pela Loja";
						}
						DepartamentoFacade departamentoFacade = new DepartamentoFacade();
						List<Departamento> departamento = departamentoFacade
								.listar("select d From Departamento d where d.usuario.idusuario="
										+ vendas.getProdutos().getIdgerente());
						if (departamento != null && departamento.size() > 0) {
							Formatacao.gravarNotificacaoVendas(titulo, vendas.getUnidadenegocio(), cliente.getNome(),
									vendas.getFornecedorcidade().getFornecedor().getNome(),
									Formatacao.ConvercaoDataPadrao(seguro.getDataInicio()), vendas.getUsuario().getNome(), vm, vendas.getValor(),
									vendas.getValorcambio(), vendas.getCambio().getMoedas().getSigla(), operacao,
									departamento.get(0), imagemNotificacao, "A");
						}
					}
				}
			}
			Mensagem.lancarMensagemInfo("Seguro Viagem Salvo", "");
			salvarOK = true;
		} else {
			Mensagem.lancarMensagemInfo(msg, "");
		}
		return salvarOK;
	}

	public String validarDados() {
		if (fornecedorcidade != null) {
			seguro.setSeguradora(fornecedorcidade.getFornecedor().getNome());
		}
		String msg = "";
		if (fornecedorcidade == null) {
			msg = msg + "Campo Seguradora não selecionado     ";
		}
		if (cliente == null) {
			msg = msg + "Campo cliente não selecionado\r\n";
		}
		if (seguro.getValorSeguro() == 0) {
			msg = msg + "Valor do seguro não informado\r\n";
		}
		if (valoresseguro == null) {
			msg = msg + "Plano do seguro não informado\r\n";
		}
		if (seguro.getDataInicio() == null) {
			msg = msg + "Data início seguro inválida\r\n";
		}
		if (seguro.getDataTermino() == null) {
			msg = msg + "Data término seguro inválida\r\n";
		}
		if (seguro.getNomeContatoEmergencia() == null || seguro.getNomeContatoEmergencia().length() <= 0) {
			msg = msg + "Nome do contato de emergência não informado\r\n";
		}
		if (seguro.getFoneContatoEmergencia() == null || seguro.getFoneContatoEmergencia().length() <= 0) {
			msg = msg + "Nº telefone  do contato de emergência não informado\r\n";
		}
		if (seguro.getPaisDestino() == null || seguro.getPaisDestino().length() <= 0) {
			msg = msg + "Pais destino não informado\r\n";
		}
		if (formaPagamento.getParcelamentopagamentoList() == null) {
			msg = msg + "Forma de Pagamento com erro\r\n";
		} else {
			if (formaPagamento.getParcelamentopagamentoList().size() == 0) {
				msg = msg + "Forma de Pagamento com erro\r\n";
			}
		}

		double saldoParcelar = this.valorParcelar;
		if (saldoParcelar > 0.01) {
			msg = msg + "Forma de Pagamento possui saldo a parcelar em aberto\r\n";
		}
		
		if (saldoParcelar < -1f) {
			msg = msg + "Saldo a parcelar negativo";
		}
		return msg;
	}

	public void salvarSeguro() {
		boolean controle = false;
		SeguroViagemFacade seguroFacade = new SeguroViagemFacade();
		if (seguro.getIdseguroViagem() == null) {
			seguro.setControle("Avulso");
			controle = true;
		}
		seguro.setPlano(valoresseguro.getPlano());
		seguro.setSeguradora(fornecedorcidade.getFornecedor().getNome());
		seguro.setPossuiSeguro("Sim");
		seguro.setFornecedor(valoresseguro.getFornecedorcidade().getFornecedor());
		seguro.setValoresseguro(valoresseguro);
		seguro.setVendas(vendas);
		seguro = seguroFacade.salvar(seguro);
		if (controle) {
			Controleseguro controleseguro = new Controleseguro();
			controleseguro.setSeguroviagem(seguro);
			controleseguro.setEnvioVoucher("Não");
			controleseguro.setObservacao(" ");
			controleseguro.setFinalizado("ANDAMENTO");
			controleseguro.setSituacao("ANDAMENTO");
			controleseguro = seguroFacade.salvarControle(controleseguro);
		}
	}

	public void salvarCliente() {
		cliente.setTipoCliente("Fechado");
		cliente.setDataInicio(seguro.getDataInicio());
		cliente.setDataTermino(seguro.getDataTermino());
		ClienteFacade clienteFacade = new ClienteFacade();
		cliente = clienteFacade.salvar(cliente);
	}

	public void verificarDadosAlterado() {
		dadosAlterado = "";

		
		if (!seguro.getPossuiSeguro().equalsIgnoreCase(seguroAlterado.getPossuiSeguro())) {
			dadosAlterado = dadosAlterado + "Seguro Viagem : " + seguro.getPossuiSeguro() + " | "
					+ seguroAlterado.getPossuiSeguro() + "<br></br>";
		}

		if (seguro.getSeguradora() != null) {
			if (!seguro.getSeguradora().equalsIgnoreCase(seguroAlterado.getSeguradora())) {
				dadosAlterado = dadosAlterado + "Fornecedor Seguro : " + seguro.getSeguradora() + " | "
						+ seguroAlterado.getSeguradora() + "<br></br>";
			}
		}
		if (seguro.getValorSeguro() != null) {
			if (seguro.getValorSeguro() != seguroAlterado.getValorSeguro()) {
				dadosAlterado = dadosAlterado + "Valor Seguro : "
						+ Formatacao.formatarFloatString(seguro.getValorSeguro())
						+ Formatacao.formatarFloatString(seguroAlterado.getValorSeguro()) + "<br></br>";
			}
		}
		if (seguro.getPlano() != null) {
			if (!seguro.getPlano().equalsIgnoreCase(seguroAlterado.getPlano())) {
				dadosAlterado = dadosAlterado + "Plano : " + seguro.getPlano() + " | " + seguroAlterado.getPlano()
						+ "<br></br>";
			}
		}
		if (seguro.getDataInicio() != null) {
			if (!seguro.getDataInicio().equals(seguroAlterado.getDataInicio())) {
				dadosAlterado = dadosAlterado + "Data Início : " + seguro.getDataInicio() + " | "
						+ seguroAlterado.getDataInicio() + "<br></br>";
			}
		}
		if (seguro.getNumeroSemanas() != null) {
			if (seguro.getNumeroSemanas() != seguroAlterado.getNumeroSemanas()) {
				dadosAlterado = dadosAlterado + "No. Semanas : " + String.valueOf(seguro.getNumeroSemanas()) + " | "
						+ String.valueOf(seguroAlterado.getNumeroSemanas()) + "<br></br>";
			}
		}
		if (seguro.getDataTermino() != null) {
			if (!seguro.getDataTermino().equals(seguroAlterado.getDataTermino())) {
				dadosAlterado = dadosAlterado + "Data Termino : "
						+ Formatacao.ConvercaoDataPadrao(seguro.getDataTermino()) + "| "
						+ Formatacao.ConvercaoDataPadrao(seguroAlterado.getDataTermino()) + "<br></br>";
			}
		}
		if (seguro.getNomeContatoEmergencia() != null) {
			if (!seguro.getNomeContatoEmergencia().equals(seguroAlterado.getNomeContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Nome Contato de Emergencia : " + seguro.getNomeContatoEmergencia()
						+ "| " + seguroAlterado.getNomeContatoEmergencia() + "<br></br>";
			}
		}
		if (seguro.getFoneContatoEmergencia() != null) {
			if (!seguro.getFoneContatoEmergencia().equals(seguroAlterado.getFoneContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Telefone Contato de Emergencia : " + seguro.getFoneContatoEmergencia()
						+ "| " + seguroAlterado.getFoneContatoEmergencia() + "<br></br>";
			}
		}
		if (seguro.getObstm() != null) {
			if (!seguro.getObstm().equalsIgnoreCase(seguro.getObstm())) {
				dadosAlterado = dadosAlterado + "Observações : " + seguro.getObstm() + " | " + seguroAlterado.getObstm()
						+ "<br></br>";
			}
		}
		if (valorVendaAlterar != vendas.getValor()) {
			dadosAlterado = dadosAlterado + "Valor Total : " + Formatacao.formatarFloatString(vendas.getValor()) + " | "
					+ Formatacao.formatarFloatString(valorVendaAlterar) + "<br></br>";
		}
	}

	public void verificarAlteracaoCambio() {
		if (cambioAlterado.equalsIgnoreCase("sim")) {
			Formatacao.VerificarCambioalterado(valorCambio, "Seguro Viagem", cliente.getNome(),
					usuarioLogadoMB.getUsuario().getUnidadenegocio().getNomeFantasia(),
					usuarioLogadoMB.getUsuario().getNome(), this.vendas.getProdutos().getIdprodutos(),
					Formatacao.formatarFloatString(this.vendas.getValor()));
		}
	}

	public void carregarCamposFormaPagamento() {
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			calcularValorTotalOrcamento();
			calcularParcelamentoPagamento();
		}
	}

	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
		if (cliente.getDataNascimento() != null) {
			idade = Formatacao.calcularIdade(cliente.getDataNascimento());
		}
	}

	public String cancelar() {
		if (voltarControleVendas != null) {
			if (voltarControleVendas.length() > 1) {
				return voltarControleVendas;
			}
		}
		return "consSeguro";
	}

	public void listarPlanosSeguro() {
		if (fornecedorcidade != null) {
			SeguroPlanosFacade seguroPlanosFacade = new SeguroPlanosFacade();
			String sql = "SELECT s FROM Seguroplanos  s WHERE s.fornecedor.idfornecedor="
					+ fornecedorcidade.getFornecedor().getIdfornecedor() + " AND s.ativo=TRUE ORDER BY s.nome";
			listaSeguroPlanos = seguroPlanosFacade.listar(sql);
		}
		if (listaSeguroPlanos == null) {
			listaSeguroPlanos = new ArrayList<Seguroplanos>();
		}
	}

	public void listarValoresSeguro() {
		if (fornecedorcidade != null && seguroplanos!=null && seguroplanos.getIdseguroplanos()!=null) {
			ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
			String sql;
			sql = "SELECT v FROM Valoresseguro v WHERE v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorcidade.getIdfornecedorcidade() + " AND v.situacao='Ativo'"
					+ " AND v.seguroplanos.idseguroplanos=" + seguroplanos.getIdseguroplanos();
			listaValoresSeguro = valorSeguroFacade.listar(sql);
		}
		if (listaValoresSeguro == null) {
			listaValoresSeguro = new ArrayList<Valoresseguro>();
		}
	}

	public void calcularComissao() {
		VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
		if (seguro.getValoresseguro() != null) {
			Vendascomissao vendasComissao = vendasComissaoFacade.getVendaComissao(vendas.getIdvendas(),
					aplicacaoMB.getParametrosprodutos().getSeguroPrivado());
			float valorJuros = 0.0f;
			if (vendas.getFormapagamento() != null && vendas.getFormapagamento().getValorJuros() != null) {
				valorJuros = vendas.getFormapagamento().getValorJuros();
			}
			if (vendasComissao != null) {
				if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
					ComissaoSeguroBean cs = new ComissaoSeguroBean(aplicacaoMB, vendas,
							formaPagamento.getParcelamentopagamentoList(), vendasComissao, seguro.getDescontoloja(),
							seguro.getDescontomatriz(), valorJuros, true, formaPagamento, this.seguro);
				}
			} else {
				vendasComissao = new Vendascomissao();
				ComissaoSeguroBean cs = new ComissaoSeguroBean(aplicacaoMB, vendas,
						formaPagamento.getParcelamentopagamentoList(), null, seguro.getDescontoloja(),
						seguro.getDescontomatriz(), valorJuros, true, formaPagamento, this.seguro);
			}
		}
	}

	public String validarMascaraFoneContatoEmergencia() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneContatoEmergencia);
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
		listaTipoParcelamento = new ArrayList<>();
		if (vendas.getVendasMatriz()!=null && vendas.getVendasMatriz().equalsIgnoreCase("S")) {
			String tipoparcelamento = "Matriz";
			listaTipoParcelamento.add(tipoparcelamento);
			tipoparcelamento = "Fornecedor";
			listaTipoParcelamento.add(tipoparcelamento); 
			tipoparcelamento = "Loja";
			listaTipoParcelamento.add(tipoparcelamento); 
		} else {
			String tipoparcelamento = "Loja";
			listaTipoParcelamento.add(tipoparcelamento);
		} 
	}
	
	public void seguroCancelamento() {
		if(seguro.isSegurocancelamento() && valoresseguro.isSegurocancelamento()) {
			float valorsegurocancelamento = valoresseguro.getValorsegurocancelamento()
					* cambio.getValor();
			seguro.setValorSeguro(seguro.getValorSeguro()+valorsegurocancelamento);
		} 
	}
	
	public void selecionarSeguroCancelamento() {
		if(seguro.isSegurocancelamento() && valoresseguro.isSegurocancelamento()) {
			float valorsegurocancelamento = valoresseguro.getValorsegurocancelamento()
					* cambio.getValor();
			seguro.setValorSeguro(seguro.getValorSeguro()+valorsegurocancelamento); 
			receberValorTotal();
		} else if(valoresseguro.isSegurocancelamento()) {
			float valorsegurocancelamento = valoresseguro.getValorsegurocancelamento()
					* cambio.getValor();
			seguro.setValorSeguro(seguro.getValorSeguro()-valorsegurocancelamento); 
			receberValorTotal();
		} 
	}
	
	public void verificarSeguroCancelamento() {
		if(valoresseguro.isSegurocancelamento()) {
			segurocancelamento = true;
			numero="4";
		} else {
			segurocancelamento = false;
			numero="3";
		}
	} 

}
