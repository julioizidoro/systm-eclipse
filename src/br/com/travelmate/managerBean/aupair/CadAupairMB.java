package br.com.travelmate.managerBean.aupair;

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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.bean.ConsultaBean;
import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoAuPairBean;
import br.com.travelmate.bean.controleAlteracoes.ControleAlteracaoCursoBean;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.DepartamentoProdutoFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.facade.ValorAupairFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.ProductRunnersMB;
import br.com.travelmate.managerBean.TmRaceMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Controlealteracoes;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Departamentoproduto;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valoresaupair;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadAupairMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private DashBoardMB dashBoardMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private MateRunnersMB metaRunnersMB;
	@Inject
	private ProductRunnersMB productRunnersMB;
	@Inject
	private TmRaceMB tmRaceMB;
	private Aupair aupair;
	private Valoresaupair valoresAupair;
	private List<Valoresaupair> listaValores;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Parcelamentopagamento parcelamentopagamento;
	private Orcamento orcamento;
	private Cambio cambio;
	private Date dataCambio;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private List<Pais> listaPais;
	private Cidade cidade;
	private Cliente cliente;
	private Produtos produto;
	private List<Moedas> listaMoedas;
	private Orcamentoprodutosorcamento orcamentoprodutosorcamento;
	private Produtosorcamento produtosorcamento;
	private List<Filtroorcamentoproduto> listaProdutosOrcamento;
	private boolean enviarFicha;
	private boolean novaFicha = false;
	private Fornecedorcomissaocurso fornecedorComissao;
	private String cambioAlterado = "Não";
	private String dadosAlterado;
	private Aupair aupairAlterado;
	private Vendas vendaAlterada;
	private float valorVendaAlterar = 0.0f;
	private Fornecedorcidade fornecedorCidadeAlterado;
	private boolean consultaCambio = false;
	private String camposPassagem = "true";
	private String camposAmigo = "true";
	private Moedas moeda;
	private float valorMoedaReal;
	private float valorMoedaEstrangeira;
	private float valorSaldoParcelar;
	private Controlealteracoes controlealteracoes;
	private Departamentoproduto depPrograma;
	private Departamento depFinanceiro;
	private List<String> listaTipoParcelamento;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal;
	private List<Parcelamentopagamento> listaParcelamantoPagamentoAntiga;
	private boolean digitosTelefoneAmigo;
	private boolean digitosTelefoneContatoEmergencia;
	private Cancelamento cancelamento;
	private Lead lead;
	private String voltarControleVendas = "";
	private boolean edicaoFicha;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		aupair = (Aupair) session.getAttribute("aupair");
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("cliente");
		session.removeAttribute("lead");
		session.removeAttribute("aupair");
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		carregarComboMoedas();
		gerarListaProdutos();
		if (aupair == null) {
			iniciarNovo();
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
		} else {
			iniciarAlteracao();
			controlealteracoes.setVendas(venda);
		}
		parcelamentopagamento.setNumeroParcelas(01);
		parcelamentopagamento.setFormaPagamento("sn");
		gerarListaTipoParcelamento();
		digitosTelefoneAmigo = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), aupair.getFoneAmigo());
		digitosTelefoneContatoEmergencia = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
				aupair.getFoneContatoEmergencia());
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

	public float getValorSaldoParcelar() {
		return valorSaldoParcelar;
	}

	public void setValorSaldoParcelar(float valorSaldoParcelar) {
		this.valorSaldoParcelar = valorSaldoParcelar;
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

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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

	public Aupair getAupair() {
		return aupair;
	}

	public void setAupair(Aupair aupair) {
		this.aupair = aupair;
	}

	public Valoresaupair getValoresAupair() {
		return valoresAupair;
	}

	public void setValoresAupair(Valoresaupair valoresAupair) {
		this.valoresAupair = valoresAupair;
	}

	public List<Valoresaupair> getListaValores() {
		return listaValores;
	}

	public void setListaValores(List<Valoresaupair> listaValores) {
		this.listaValores = listaValores;
	}

	public Aupair getAupairAlterado() {
		return aupairAlterado;
	}

	public void setAupairAlterado(Aupair aupairAlterado) {
		this.aupairAlterado = aupairAlterado;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public List<Parcelamentopagamento> getListaParcelamantoPagamentoAntiga() {
		return listaParcelamantoPagamentoAntiga;
	}

	public void setListaParcelamantoPagamentoAntiga(List<Parcelamentopagamento> listaParcelamantoPagamentoAntiga) {
		this.listaParcelamantoPagamentoAntiga = listaParcelamantoPagamentoAntiga;
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

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
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

	public Fornecedorcidade getFornecedorCidadeAlterado() {
		return fornecedorCidadeAlterado;
	}

	public void setFornecedorCidadeAlterado(Fornecedorcidade fornecedorCidadeAlterado) {
		this.fornecedorCidadeAlterado = fornecedorCidadeAlterado;
	}

	public String getCamposPassagem() {
		return camposPassagem;
	}

	public void setCamposPassagem(String camposPassagem) {
		this.camposPassagem = camposPassagem;
	}

	public String getCamposAmigo() {
		return camposAmigo;
	}

	public void setCamposAmigo(String camposAmigo) {
		this.camposAmigo = camposAmigo;
	}

	public Moedas getMoeda() {
		return moeda;
	}

	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
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

	public boolean isDigitosTelefoneAmigo() {
		return digitosTelefoneAmigo;
	}

	public void setDigitosTelefoneAmigo(boolean digitosTelefoneAmigo) {
		this.digitosTelefoneAmigo = digitosTelefoneAmigo;
	}

	public boolean isDigitosTelefoneContatoEmergencia() {
		return digitosTelefoneContatoEmergencia;
	}

	public void setDigitosTelefoneContatoEmergencia(boolean digitosTelefoneContatoEmergencia) {
		this.digitosTelefoneContatoEmergencia = digitosTelefoneContatoEmergencia;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
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

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoOriginal() {
		return listaParcelamentoPagamentoOriginal;
	}

	public void setListaParcelamentoPagamentoOriginal(List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal) {
		this.listaParcelamentoPagamentoOriginal = listaParcelamentoPagamentoOriginal;
	}

	public boolean isEdicaoFicha() {
		return edicaoFicha;
	}

	public void setEdicaoFicha(boolean edicaoFicha) {
		this.edicaoFicha = edicaoFicha;
	}

	public void iniciarNovo() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		edicaoFicha=false;
		produto = new Produtos();
		fornecedorCidade = new Fornecedorcidade();
		cambio = new Cambio();
		aupair = new Aupair();
		carregarCampos();
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
		valoresAupair = new Valoresaupair();
		produto = new Produtos();
		cidade = new Cidade();
		parcelamentopagamento = new Parcelamentopagamento();
		parcelamentopagamento.setFormaPagamento("sn");
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		consultaCambio = true;
		aupair.setTipoPassagem("Cliente Providenciará");
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
					+ aplicacaoMB.getParametrosprodutos().getAupair() + " and f.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.ativo=1";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
	}

	public void carregarValores() {
		if (fornecedorCidade != null && fornecedorCidade.getIdfornecedorcidade() != null) {
			String sql = "select v from Valoresaupair v where v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade() + " and v.situacao='Ativo'";
			ValorAupairFacade valorAupairFacade = new ValorAupairFacade();
			listaValores = valorAupairFacade.listar(sql);
			if (listaValores == null) {
				listaValores = new ArrayList<Valoresaupair>();
				if (this.aupair.getIdaupair() != null) {
					if (valoresAupair != null) {
						listaValores.add(valoresAupair);
					}
				}
			}
			if (listaValores.size() == 0) {
				Mensagem.lancarMensagemInfo("Valores",
						"Programa não possui valor ativo. Entre em contato com a Gerencia");
			}

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
					boolean horarioExcedido = false;
					int numeroAdicionar = 0;
					int diaSemana = Formatacao.diaSemana(parcelamentopagamento.getDiaVencimento());
					String horaAtual = Formatacao.foramtarHoraString();
					String horaMaxima = "16:00:00";
					Time horatime = null;
					Time horaMaxTime = null;
					try {
						horatime = Formatacao.converterStringHora(horaAtual);
						horaMaxTime = Formatacao.converterStringHora(horaMaxima);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (horatime.after(horaMaxTime)) {
						numeroAdicionar = 1;
						horarioExcedido = true;
					}
		
					if (diaSemana == 1) {
						numeroAdicionar = 1;
						horarioExcedido = true;
					}else if(diaSemana == 7) {
						numeroAdicionar = 2;
						horarioExcedido = true;
					}
					if (horarioExcedido) {
						try {
							parcelamentopagamento.setDiaVencimento(Formatacao.SomarDiasDatas(parcelamentopagamento.getDiaVencimento(), numeroAdicionar));
							Mensagem.lancarMensagemInfo("Primeira parcela efetuada para o próximo dia útil", "");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if (venda.getIdvendas()!=null){
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
					parcelamentopagamento = contasReceberBean.gerarParcelasIndividuais(parcelamentopagamento, formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB);
					}
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamentopagamento);
				parcelamentopagamento = new Parcelamentopagamento();
				calcularParcelamentoPagamento();
				parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
				parcelamentopagamento.setNumeroParcelas(01);
				parcelamentopagamento.setValorParcela(parcelamentopagamento.getValorParcelamento());
			}
		} else
			Mensagem.lancarMensagemErro(msg, "");
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
		}
		if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("sn")) {
			msg = msg + "Forma de pagamento não selecionada";
		}
		if (parcelamentopagamento.getValorParcela() <= 0) {
			msg = msg + "Valor da parcela não pode ser 0";
		}
		if(parcelamentopagamento.getValorParcelamento() > (valorSaldoParcelar+0.01)){ 
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
			if (venda.getIdvendas()!=null){
				if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
					contasReceberBean.apagarContasReceber(formaPagamento.getParcelamentopagamentoList().get(linha), venda.getIdvendas(), usuarioLogadoMB
							, formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
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
			parcelamentopagamento.setValorParcela(0.0f); 
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
		atualizarValoresProduto();
	}

	public void carregarComboMoedas() {
		CambioFacade cambioFacade = new CambioFacade();
		listaMoedas = cambioFacade.listaMoedas();
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
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda);
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
				float valorMoedaReal = 0.0f;
				float valorMoedaEstrangeira = 0.0f;
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
				venda.setValor(valorTotal);
				formaPagamento.setValorOrcamento(valorTotal);
			}
			if (formaPagamento.getPossuiJuros().equalsIgnoreCase("Não")) {
				formaPagamento.setValorJuros(0.0f);
			}
			venda.setValor(venda.getValor() + formaPagamento.getValorJuros());
			formaPagamento.setValorTotal(venda.getValor());
			calcularParcelamentoPagamento();
			parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
		}
	}

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getAupair());
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
				+ aplicacaoMB.getParametrosprodutos().getAupair()
				+ " and f.listar='S' and f.datavalidade>='"  + Formatacao.ConvercaoDataSql(new Date())
				+ "' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void adicionarProdutos() {
		if (orcamento.getValorCambio() > 0) {
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
					.pesquisar(aplicacaoMB.getParametrosprodutos().getAupair(), "Au Pair");
			int idProduto = produto.getProdutosorcamento().getIdprodutosOrcamento();
			if (produtosorcamento != null) {
				if (produtosorcamento.getIdprodutosOrcamento() != idProduto) {
					orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
					orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
					if ((orcamentoprodutosorcamento.getValorMoedaEstrangeira() > 0)
							&& (orcamento.getValorCambio() > 0)) {
						orcamentoprodutosorcamento.setValorMoedaNacional(
								orcamentoprodutosorcamento.getValorMoedaEstrangeira() * orcamento.getValorCambio());
					} else {
						if ((orcamentoprodutosorcamento.getValorMoedaNacional() > 0)
								&& (orcamento.getValorCambio() > 0)) {
							orcamentoprodutosorcamento.setValorMoedaEstrangeira(
									orcamentoprodutosorcamento.getValorMoedaNacional() / orcamento.getValorCambio());
						}
					}
					boolean excluirDescontoTM = true;
					if (produtosorcamento.getValormaximo()==0) {
						orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
						calcularValorTotalOrcamento();
						orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
					}else if (produtosorcamento.getValormaximo()>=orcamentoprodutosorcamento.getValorMoedaNacional()){
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
				} else
					Mensagem.lancarMensagemErro("", "Au Pair já incluso");
			} else
				Mensagem.lancarMensagemErro("Produto não selecionado", "");
		} else
			Mensagem.lancarMensagemErro("Cambio não selecionado", "");
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
				if ((orcamentoprodutosorcamento.getValorMoedaEstrangeira() > 0)
						&& (orcamento.getValorCambio() > 0)) {
					orcamentoprodutosorcamento.setValorMoedaNacional(
							orcamentoprodutosorcamento.getValorMoedaEstrangeira() * orcamento.getValorCambio());
				} else {
					if ((orcamentoprodutosorcamento.getValorMoedaNacional() > 0)
							&& (orcamento.getValorCambio() > 0)) {
						orcamentoprodutosorcamento
								.setValorMoedaEstrangeira(orcamentoprodutosorcamento.getValorMoedaNacional()
										/ orcamento.getValorCambio());
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
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
				.pesquisar(aplicacaoMB.getParametrosprodutos().getAupair(), "Au Pair");
		int idProduto = produto.getProdutosorcamento().getIdprodutosOrcamento();
		if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
				.getIdprodutosOrcamento() == idProduto) {
			Mensagem.lancarMensagemErro("Au Pair não pode ser Excluído.", "");
		} else {
			if (ilinha >= 0) {
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
		return "consAuPair";
	}

	public String naoEnviarficha() throws SQLException {
		enviarFicha = false;
		if (confirmarFicha()) {
			return "consAuPair";
		}
		return "";
	}

	public String finalizarficha() throws SQLException {
		enviarFicha = true;
		if (confirmarFicha()) {
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consAuPair";
		}
		return "";
	}

	public boolean confirmarFicha() throws SQLException {
		boolean salvarOK = false;
		String msg = validarDados();
		String nsituacao = "";
		if (venda.getIdvendas() != null) {
			nsituacao = venda.getSituacao();
		}
		if (msg.length() < 5) {
			if (Formatacao.validarEmail(aupair.getEmailContatoEmergencia())) {
				if (!venda.getSituacao().equalsIgnoreCase("FINALIZADA")) {
					if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
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
								nsituacao = "PROCESSO";
								Mensagem.lancarMensagemWarn("Data Vencimento",
										"As parcelas possuem data de vencimento após o inicio do programa. Entrar em contato com Financeiro");
							} else
								nsituacao = "ANDAMENTO";
						}
					} else {
						if (nsituacao.equalsIgnoreCase("")) {
							nsituacao = "PROCESSO";
						}
					}
				} else {
					enviarFicha = true;
					nsituacao = "FINALIZADA";
				}
				ProgramasBean programasBean = new ProgramasBean();
				this.produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getAupair());
				venda = programasBean.salvarVendas(venda, usuarioLogadoMB, nsituacao, cliente,
						formaPagamento.getValorTotal(), produto, fornecedorCidade, cambio, orcamento.getValorCambio(),
						lead, null, null);
				aupair.setControle("Processo");
				aupair.setVendas(venda);
				aupair.setValoresAupair(valoresAupair);
				if (venda.getIdvendas() != null) {
					if (venda.getCambio() != cambio) {
						cambioAlterado = "Sim";
					}
				}
				CadAuPairBean cadAuPairBean = new CadAuPairBean(venda, formaPagamento, orcamento, usuarioLogadoMB);
				if (enviarFicha && !novaFicha) {
					cadAuPairBean.SalvarAlteracaoFinanceiro(listaParcelamantoPagamentoAntiga, listaParcelamentoPagamentoOriginal);
				}
				aupair = cadAuPairBean.salvarAupair(aupair);
				this.orcamento = cadAuPairBean.salvarOrcamento(orcamento, cambio, orcamento.getTotalMoedaNacional(),
						orcamento.getTotalMoedaEstrangeira(), orcamento.getValorCambio(), venda, cambioAlterado);
				this.formaPagamento = cadAuPairBean.salvarFormaPagamento(cancelamento);
				String inicioAupir = "SEM DATA";
				if (aupair.getDataInicioPretendida01() != null) {
					inicioAupir = Formatacao.ConvercaoDataPadrao(aupair.getDataInicioPretendida01());
				}
				cliente = cadAuPairBean.salvarCliente(cliente, inicioAupir);
				float valorPrevisto = 0.0f;
				if (venda.getSituacao().equalsIgnoreCase("FINALIZADA")
						|| venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
					float valorVendaatual = venda.getValor();
					Vendascomissao vendasComissao = venda.getVendascomissao();
					if (vendasComissao == null) {
						vendasComissao = new Vendascomissao();
						vendasComissao.setVendas(venda);
						vendasComissao.setPaga("Não");
					}
					float valorJuros = 0.0f;
					if (venda.getFormapagamento() != null) {
						valorJuros = venda.getFormapagamento().getValorJuros();
					}
					if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
						ComissaoAuPairBean cc = new ComissaoAuPairBean(aplicacaoMB, venda,
								orcamento.getOrcamentoprodutosorcamentoList(), orcamento.getValorCambio(),
								aupair.getValoresAupair(), formaPagamento.getParcelamentopagamentoList(),
								aupair.getDataInicioPretendida01(), vendasComissao, valorJuros);
						valorPrevisto = cc.getVendasComissao().getValorfornecedor();
					}
				}
				if (enviarFicha) {
					ControlerBean controlerBean = new ControlerBean();
					controlerBean.salvarControleAupair(venda, aupair, valorPrevisto);
				}
				if (novaFicha) {
					if (enviarFicha) {
						if (vendaAlterada == null || vendaAlterada.getIdvendas() == null
								|| vendaAlterada.getSituacao().equalsIgnoreCase("PROCESSO")) {
							dashBoardMB.getVendaproduto()
									.setIntercambio(dashBoardMB.getVendaproduto().getIntercambio() + 1);
							dashBoardMB.getMetamensal().setValoralcancado(
									dashBoardMB.getMetamensal().getValoralcancado() + venda.getValor());
							dashBoardMB.getMetamensal()
									.setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
											/ dashBoardMB.getMetamensal().getValormeta()) * 100);

							dashBoardMB.getMetaAnual()
									.setMetaalcancada(dashBoardMB.getMetaAnual().getMetaalcancada() + venda.getValor());
							dashBoardMB.getMetaAnual()
									.setPercentualalcancado((dashBoardMB.getMetaAnual().getMetaalcancada()
											/ dashBoardMB.getMetaAnual().getValormeta()) * 100);

							dashBoardMB.setMetaparcialsemana(dashBoardMB.getMetaparcialsemana() + venda.getValor());
							dashBoardMB.setPercsemana((dashBoardMB.getMetaparcialsemana()
									/ dashBoardMB.getMetamensal().getValormetasemana()) * 100);

							float valor = dashBoardMB.getMetamensal().getValoralcancado();
							dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));
							DashBoardBean dashBoardBean = new DashBoardBean();
							dashBoardBean.calcularNumeroVendasProdutos(venda, false);
							dashBoardBean.calcularMetaMensal(venda, 0, false);
							dashBoardBean.calcularMetaAnual(venda, 0, false);
							int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, "", false);
							productRunnersMB.calcularPontuacao(venda, pontos[0], false);
							venda.setPonto(pontos[0]);
							venda.setPontoescola(pontos[1]);
							VendasFacade vendasFacade = new VendasFacade();
							venda = vendasFacade.salvar(venda);
							metaRunnersMB.carregarListaRunners();
							tmRaceMB.gerarListaGold();
							tmRaceMB.gerarListaSinze();
							tmRaceMB.gerarListaBronze();
							ContasReceberBean contasReceberBean = new ContasReceberBean(venda,
									formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, false);
							String titulo = "Nova Ficha de Au Pair";
							String operacao = "A";
							String imagemNotificacao = "inserido";

							if (aupair.getIdaupair() != null) {
								if (vendaAlterada != null) {
									titulo = "Ficha de Au Pair Alterada";
									operacao = "I";
									imagemNotificacao = "alterado";
									verificarDadosAlterado();
								}
							}
							String vm = "Venda pela Matriz";
							if (venda.getVendasMatriz().equalsIgnoreCase("N")) {
								vm = "Venda pela Loja";
							}
							
							DepartamentoProdutoFacade departamentoProdutoFacade = new DepartamentoProdutoFacade();
							depPrograma = departamentoProdutoFacade.consultar(venda.getProdutos().getIdprodutos());
							if (depPrograma != null) {
								Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(), cliente.getNome(),
										venda.getFornecedorcidade().getFornecedor().getNome(),
										Formatacao.ConvercaoDataPadrao(aupair.getDataInicioPretendida01()),
										venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
										venda.getCambio().getMoedas().getSigla(), operacao, depPrograma.getDepartamento(),
										imagemNotificacao, "I");
							}
						}
					}
				} else {
					int mes = Formatacao.getMesData(new Date()) + 1;
					int mesVenda = Formatacao.getMesData(venda.getDataVenda()) + 1;
						if (enviarFicha) {
							if (mes == mesVenda) {
								dashBoardMB.getMetamensal()
										.setValoralcancado(dashBoardMB.getMetamensal().getValoralcancado()
												- valorVendaAlterar + venda.getValor());
								dashBoardMB.getMetamensal()
										.setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
												/ dashBoardMB.getMetamensal().getValormeta()) * 100);
	
								dashBoardMB.getMetaAnual().setMetaalcancada(dashBoardMB.getMetaAnual().getMetaalcancada()
										- valorVendaAlterar + venda.getValor());
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
								int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, "", false);
								productRunnersMB.calcularPontuacao(venda, pontos[0], false);
								venda.setPonto(pontos[0]);
								venda.setPontoescola(pontos[1]);
								VendasFacade vendasFacade = new VendasFacade();
								venda = vendasFacade.salvar(venda);
								metaRunnersMB.carregarListaRunners();
							}
							String titulo = "Nova Ficha de Au Pair";
							String operacao = "A";
							String imagemNotificacao = "inserido";

							if (aupair.getIdaupair() != null) {
								if (vendaAlterada != null) {
									titulo = "Ficha de Au Pair Alterada";
									operacao = "I";
									imagemNotificacao = "alterado";
									verificarDadosAlterado();
								}
							}
							String vm = "Venda pela Matriz";
							if (venda.getVendasMatriz().equalsIgnoreCase("N")) {
								vm = "Venda pela Loja";
							}
							Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(), cliente.getNome(),
									venda.getFornecedorcidade().getFornecedor().getNome(),
									Formatacao.ConvercaoDataPadrao(aupair.getDataInicioPretendida01()),
									venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
									venda.getCambio().getMoedas().getSigla(), operacao, depPrograma.getDepartamento(),
									imagemNotificacao, "A");
						}
				}
				Mensagem.lancarMensagemInfo("Aupair Salvo com Sucesso", "");
				salvarOK = true;
			}
		} else {
			Mensagem.lancarMensagemInfo(msg, "");
		}
		return salvarOK;
	}

	public String validarDados() {
		String msg = "";
		if (valoresAupair != null) {
			if (fornecedorCidade == null) {
				msg = msg + "Campo escola não selecionado";
			}
			if (cambio == null && cambio.getIdcambio() != null) {
				msg = msg + "Selecione câmbio da ficha";
			}
			if (cliente == null) {
				msg = msg + "Campo cliente não selecionado\r\n";
			}
			if (aupair.getCurso() == null) {
				msg = msg + "Curso que estuda não informado\r\n";
			}
			if (fornecedorCidade == null) {
				msg = msg + "Escola/Instituição não informada\r\n";
			}
			if (pais == null) {
				msg = msg + "País não informado\r\n";
			}
			if (aupair.getDataInicioPretendida01() == null) {
				msg = msg + "Início previsto inválida\r\n";
			}

			if (formaPagamento.getParcelamentopagamentoList() == null) {
				msg = msg + "Forma de Pagamento com erro\r\n";
			} else {
				if (formaPagamento.getParcelamentopagamentoList().size() == 0) {
					msg = msg + "Forma de Pagamento com erro\r\n";
				}
			}
			double saldoParcelar = this.valorSaldoParcelar;
			if (saldoParcelar > 0.01f) {
				msg = msg + "Forma de Pagamento possui saldo a parcelar em aberto\r\n";
			}
		} else {
			msg = msg + "Campo valores não está preenchido!\r\n";
		}
		return msg;
	}

	public void verificarDadosAlterado() {
		ControleAlteracaoCursoBean controleAlteracaoCursoBean = new ControleAlteracaoCursoBean();
		if (aupair.getCurso() != null) {
			if (!aupair.getCurso().equalsIgnoreCase(aupairAlterado.getCurso())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Curso que estuda", aupair.getCurso(),
						aupairAlterado.getCurso());
			}
		}
		if (aupair.getCartaoVTM() != null) {
			if (!aupair.getCartaoVTM().equalsIgnoreCase(aupairAlterado.getCartaoVTM())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Cartão VTM", aupair.getCartaoVTM(),
						aupairAlterado.getCartaoVTM());
			}
		}
		if (aupair.getCursandoPeriodo() != null) {
			if (!aupair.getCursandoPeriodo().equalsIgnoreCase(aupairAlterado.getCursandoPeriodo())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Período que está cursando", aupair.getCursandoPeriodo(),
						aupairAlterado.getCursandoPeriodo());
			}
		}
		if (aupair.getDuracaoCurso() != null) {
			if (!aupair.getDuracaoCurso().equalsIgnoreCase(aupairAlterado.getDuracaoCurso())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Duração Curso", aupair.getDuracaoCurso(),
						aupairAlterado.getDuracaoCurso());
			}
		}
		if (aupair.getDataEntregaDocumentosVistos() != null) {
			if (!aupair.getDataEntregaDocumentosVistos().equals(aupairAlterado.getDataEntregaDocumentosVistos())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Data Entrega Documentos",
						Formatacao.ConvercaoDataPadrao(aupair.getDataEntregaDocumentosVistos()),
						Formatacao.ConvercaoDataPadrao(aupairAlterado.getDataEntregaDocumentosVistos()));
			}
		}
		if (aupair.getDataInicioPretendida01() != null) {
			if (!aupair.getDataInicioPretendida01().equals(aupairAlterado.getDataInicioPretendida01())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Data Inicio Pretendida",
						Formatacao.ConvercaoDataPadrao(aupair.getDataInicioPretendida01()),
						Formatacao.ConvercaoDataPadrao(aupairAlterado.getDataInicioPretendida01()));
			}
		}
		if (aupair.getDataInicioPretendida02() != null) {
			if (!aupair.getDataInicioPretendida02().equals(aupairAlterado.getDataInicioPretendida02())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Data Inicio Pretendida Maxima",
						Formatacao.ConvercaoDataPadrao(aupair.getDataInicioPretendida02()),
						Formatacao.ConvercaoDataPadrao(aupairAlterado.getDataInicioPretendida02()));
			}
		}
		if (aupair.getDataTerminoPretendida01() != null) {
			if (!aupair.getDataTerminoPretendida01().equals(aupairAlterado.getDataTerminoPretendida01())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Data Termino Pretendida",
						Formatacao.ConvercaoDataPadrao(aupair.getDataTerminoPretendida01()),
						Formatacao.ConvercaoDataPadrao(aupairAlterado.getDataTerminoPretendida01()));
			}
		}
		if (aupair.getEmailContatoEmergencia() != null) {
			if (!aupair.getEmailContatoEmergencia().equalsIgnoreCase(aupairAlterado.getEmailContatoEmergencia())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Email Contato de Emergência",
						aupair.getEmailContatoEmergencia(), aupairAlterado.getEmailContatoEmergencia());
			}
		}
		if (aupair.getEndercoAmigo() != null) {
			if (!aupair.getEndercoAmigo().equalsIgnoreCase(aupairAlterado.getEndercoAmigo())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Endereço amigo - USA", aupair.getEndercoAmigo(),
						aupairAlterado.getEndercoAmigo());
			}
		}
		if (aupair.getFoneAmigo() != null) {
			if (!aupair.getFoneAmigo().equalsIgnoreCase(aupairAlterado.getFoneAmigo())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Fone amigo - USA", aupair.getFoneAmigo(),
						aupairAlterado.getFoneAmigo());
			}
		}
		if (aupair.getFoneContatoEmergencia() != null) {
			if (!aupair.getFoneContatoEmergencia().equalsIgnoreCase(aupairAlterado.getFoneContatoEmergencia())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Fone contato de emergência", aupair.getFoneContatoEmergencia(),
						aupairAlterado.getFoneContatoEmergencia());
			}
		}
		if (aupair.getIdioma01() != null) {
			if (!aupair.getIdioma01().equalsIgnoreCase(aupairAlterado.getIdioma01())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Idioma", aupair.getIdioma01(), aupairAlterado.getIdioma01());
			}
		}
		if (aupair.getIdioma02() != null) {
			if (!aupair.getIdioma02().equalsIgnoreCase(aupairAlterado.getIdioma02())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Idioma 2", aupair.getIdioma02(), aupairAlterado.getIdioma02());
			}
		}
		if (aupair.getIdioma03() != null) {
			if (!aupair.getIdioma03().equalsIgnoreCase(aupairAlterado.getIdioma03())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Idioma 3", aupair.getIdioma03(), aupairAlterado.getIdioma03());
			}
		}
		if (aupair.getInituicaoEstuda() != null) {
			if (!aupair.getInituicaoEstuda().equalsIgnoreCase(aupairAlterado.getInituicaoEstuda())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Instituição onde estuda", aupair.getInituicaoEstuda(),
						aupairAlterado.getInituicaoEstuda());
			}
		}
		if (aupair.getMoedaCartao() != null) {
			if (!aupair.getMoedaCartao().equalsIgnoreCase(aupairAlterado.getMoedaCartao())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Moeda Cartão VTM", aupair.getMoedaCartao(),
						aupairAlterado.getMoedaCartao());
			}
		}
		if (aupair.getMoedaCartao() != null) {
			if (!aupair.getMoedaCartao().equalsIgnoreCase(aupairAlterado.getMoedaCartao())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Moeda Cartão VTM", aupair.getMoedaCartao(),
						aupairAlterado.getMoedaCartao());
			}
		}
		if (aupair.getNivelEstudo() != null) {
			if (!aupair.getNivelEstudo().equalsIgnoreCase(aupairAlterado.getNivelEstudo())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Nivel Estudo", aupair.getNivelEstudo(),
						aupairAlterado.getNivelEstudo());
			}
		}
		if (aupair.getNivelIdioma01() != null) {
			if (!aupair.getNivelIdioma01().equalsIgnoreCase(aupairAlterado.getNivelIdioma01())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Nivel Idioma 1", aupair.getNivelIdioma01(),
						aupairAlterado.getNivelIdioma01());
			}
		}
		if (aupair.getNivelIdioma02() != null) {
			if (!aupair.getNivelIdioma02().equalsIgnoreCase(aupairAlterado.getNivelIdioma02())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Nivel Idioma 2", aupair.getNivelIdioma02(),
						aupairAlterado.getNivelIdioma02());
			}
		}
		if (aupair.getNivelIdioma03() != null) {
			if (!aupair.getNivelIdioma03().equalsIgnoreCase(aupairAlterado.getNivelIdioma03())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Nivel Idioma 3", aupair.getNivelIdioma03(),
						aupairAlterado.getNivelIdioma03());
			}
		}
		if (aupair.getNomeAmigo() != null) {
			if (!aupair.getNomeAmigo().equalsIgnoreCase(aupairAlterado.getNomeAmigo())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Nome amigo - USA", aupair.getNomeAmigo(),
						aupairAlterado.getNomeAmigo());
			}
		}
		if (aupair.getNomeContatoEmergencia() != null) {
			if (!aupair.getNomeContatoEmergencia().equalsIgnoreCase(aupairAlterado.getNomeContatoEmergencia())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Nome contato de emergência", aupair.getNomeContatoEmergencia(),
						aupairAlterado.getNomeContatoEmergencia());
			}
		}
		if (aupair.getNumeroCartao() != null) {
			if (!aupair.getNumeroCartao().equalsIgnoreCase(aupairAlterado.getNumeroCartao())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Número Cartão VTM", aupair.getNumeroCartao(),
						aupairAlterado.getNumeroCartao());
			}
		}
		if (aupair.getObservacaoPassagem() != null) {
			if (!aupair.getObservacaoPassagem().equalsIgnoreCase(aupairAlterado.getObservacaoPassagem())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Observação Passagem", aupair.getObservacaoPassagem(),
						aupairAlterado.getObservacaoPassagem());
			}
		}
		if (aupair.getObservacaoVisto() != null) {
			if (!aupair.getObservacaoVisto().equalsIgnoreCase(aupairAlterado.getObservacaoVisto())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Observação Visto", aupair.getObservacaoVisto(),
						aupairAlterado.getObservacaoVisto());
			}
		}
		if (aupair.getObstm() != null) {
			if (!aupair.getObstm().equalsIgnoreCase(aupairAlterado.getObstm())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Observação TM", aupair.getObstm(), aupairAlterado.getObstm());
			}
		}
		if (aupair.getOcupacao() != null) {
			if (!aupair.getOcupacao().equalsIgnoreCase(aupairAlterado.getOcupacao())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Ocupação", aupair.getOcupacao(), aupairAlterado.getOcupacao());
			}
		}
		if (aupair.getPaisinteresse() != null) {
			if (!aupair.getPaisinteresse().equalsIgnoreCase(aupairAlterado.getPaisinteresse())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Pais Interesse", aupair.getPaisinteresse(),
						aupairAlterado.getPaisinteresse());
			}
		}
		if (aupair.getPossuicnh() != null) {
			if (!aupair.getPossuicnh().equalsIgnoreCase(aupairAlterado.getPossuicnh())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Possui CNH", aupair.getPossuicnh(),
						aupairAlterado.getPossuicnh());
			}
		}
		if (aupair.getRelacaoAmigo() != null) {
			if (!aupair.getRelacaoAmigo().equalsIgnoreCase(aupairAlterado.getRelacaoAmigo())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Relação amigo - USA", aupair.getRelacaoAmigo(),
						aupairAlterado.getRelacaoAmigo());
			}
		}
		if (aupair.getRelacaoContatoEmergencia() != null) {
			if (!aupair.getRelacaoContatoEmergencia().equalsIgnoreCase(aupairAlterado.getRelacaoContatoEmergencia())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Relação contato de emergência",
						aupair.getRelacaoContatoEmergencia(), aupairAlterado.getRelacaoContatoEmergencia());
			}
		}
		if (aupair.getTempocnh() != null) {
			if (!aupair.getTempocnh().equalsIgnoreCase(aupairAlterado.getTempocnh())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Tempo CNH", aupair.getTempocnh(), aupairAlterado.getTempocnh());
			}
		}
		if (aupair.getTipoPassagem() != null) {
			if (!aupair.getTipoPassagem().equalsIgnoreCase(aupairAlterado.getTipoPassagem())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Passagem", aupair.getTipoPassagem(),
						aupairAlterado.getTipoPassagem());
			}
		}
		if (aupair.getTipoSolicitacaoVisto() != null) {
			if (!aupair.getTipoSolicitacaoVisto().equalsIgnoreCase(aupairAlterado.getTipoSolicitacaoVisto())) {
				controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes,
						depPrograma.getDepartamento(), "Visto", aupair.getTipoSolicitacaoVisto(),
						aupairAlterado.getTipoSolicitacaoVisto());
			}
		}
		if (valorVendaAlterar != venda.getValor()) {
			controlealteracoes = controleAlteracaoCursoBean.salvar(controlealteracoes, depFinanceiro, "Valor Total",
					Formatacao.formatarFloatString(venda.getValor()),
					Formatacao.formatarFloatString(valorVendaAlterar));
		}
	}

	public void iniciarAlteracao() {
		this.venda = aupair.getVendas();
		valorVendaAlterar = venda.getValor();
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			vendaAlterada = venda;
		}
		this.cliente = venda.getCliente();
		carregarAupairAlterado();
		fornecedorCidade = venda.getFornecedorcidade();
		cidade = fornecedorCidade.getCidade();
		pais = cidade.getPais();
		valoresAupair = aupair.getValoresAupair();
		carregarValores();
		listarFornecedorCidade();
		edicaoFicha=true;
		fornecedorCidade = venda.getFornecedorcidade();
		fornecedorCidadeAlterado = fornecedorCidade;
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		parcelamentopagamento = new Parcelamentopagamento();
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		fornecedorComissao = fornecedorComissaoCursoFacade.consultar(fornecedorCidade.getFornecedor().getIdfornecedor(),
				fornecedorCidade.getCidade().getPais().getIdpais());
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		this.formaPagamento = formaPagamentoFacade.consultar(venda.getIdvendas());
		if (formaPagamento != null) {
			carregarCamposFormaPagamento();
			listaParcelamantoPagamentoAntiga = new ArrayList<>();
			for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
				Parcelamentopagamento parcelamentopagamento = new Parcelamentopagamento();
				parcelamentopagamento.setDiaVencimento(formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento());
				parcelamentopagamento.setFormaPagamento(formaPagamento.getParcelamentopagamentoList().get(i).getFormaPagamento());
				parcelamentopagamento.setNumeroParcelas(formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas());
				parcelamentopagamento.setValorParcela(formaPagamento.getParcelamentopagamentoList().get(i).getValorParcela());
				parcelamentopagamento.setValorParcelamento(formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento());
				parcelamentopagamento.setTipoParcelmaneto(formaPagamento.getParcelamentopagamentoList().get(i).getTipoParcelmaneto());
				listaParcelamantoPagamentoAntiga.add(parcelamentopagamento);
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
			moeda = orcamento.getCambio().getMoedas();
			carregarCambio();
			calcularValorTotalOrcamento();
		}

		consultaCambio = true;
		parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
		parcelamentopagamento.setFormaPagamento("sn");
		DepartamentoProdutoFacade departamentoProdutoFacade = new DepartamentoProdutoFacade();
		depPrograma = departamentoProdutoFacade.consultar(venda.getProdutos().getIdprodutos());
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		depFinanceiro = departamentoFacade.consultar(3);
		controlealteracoes = new Controlealteracoes();
		controlealteracoes.setUsuario(usuarioLogadoMB.getUsuario());
		controlealteracoes.setVendas(venda);
		controlealteracoes.setDepartamentoproduto(depPrograma);
	}

	public void carregarCamposFormaPagamento() {
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			calcularParcelamentoPagamento();
		}
	}

	public void carregarCambio() {
		CambioFacade cambioFacade = new CambioFacade();
		if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			int dias = Formatacao.subtrairDatas(new Date(), venda.getDataVenda());
			if (dias > 3) {
				Mensagem.lancarMensagemErro("Cambio alterado para o dia atual", "");
				cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
						cambio.getMoedas().getIdmoedas());
				if (cambio != null) {
					orcamento.setValorCambio(cambio.getValor());
					cambioAlterado = "Não";
					atualizarValoresProduto();
				}
			} else {
				cambioAlterado = orcamento.getCambioAlterado();
			}
		} else {
			cambioAlterado = orcamento.getCambioAlterado();
		}
	}

	public void calcularValorProdutos() {
		int codValores = aplicacaoMB.getParametrosprodutos().getAupair();
		for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
			int codigoLista = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
					.getIdprodutosOrcamento();
			if (codValores == codigoLista) {
				orcamento.getOrcamentoprodutosorcamentoList().remove(i);
			}
		}
		if (valoresAupair != null) {
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
					.pesquisar(aplicacaoMB.getParametrosprodutos().getAupair(), "Au Pair");
			int idProduto = produto.getProdutosorcamento().getIdprodutosOrcamento();
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento() == idProduto) {
					if (orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.getIdorcamentoProdutosOrcamento() != null) {
						OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
						orcamentoFacade.excluirOrcamentoProdutoOrcamento(
								orcamento.getOrcamentoprodutosorcamentoList().get(i).getIdorcamentoProdutosOrcamento());
					}
					orcamento.getOrcamentoprodutosorcamentoList().remove(i);
				}
			}
			orcamentoprodutosorcamento.setProdutosorcamento(produto.getProdutosorcamento());
			orcamentoprodutosorcamento.setDescricao(produto.getProdutosorcamento().getDescricao());
			if (valoresAupair.getValorgross() > 0) {
				CambioFacade cambioFacade = new CambioFacade();
				Cambio cambioValor = new Cambio();
				cambioValor = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
						valoresAupair.getMoedas().getIdmoedas());
				orcamentoprodutosorcamento
						.setValorMoedaNacional(valoresAupair.getValorgross() * cambioValor.getValor());
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(valoresAupair.getValorgross());
				moeda = valoresAupair.getMoedas();
				consultarCambio();
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

	public void carregarAupairAlterado() {
		aupairAlterado = new Aupair();
		aupairAlterado.setCurso(aupair.getCurso());
		aupairAlterado.setEmailContatoEmergencia(aupair.getEmailContatoEmergencia());
		aupairAlterado.setFoneContatoEmergencia(aupair.getFoneContatoEmergencia());
		aupairAlterado.setNomeContatoEmergencia(aupair.getNomeContatoEmergencia());
		aupairAlterado.setObservacaoPassagem(aupair.getObservacaoPassagem());
		aupairAlterado.setTipoPassagem(aupair.getTipoPassagem());
		aupairAlterado.setRelacaoContatoEmergencia(aupair.getRelacaoContatoEmergencia());
		aupairAlterado.setFoneContatoEmergencia(aupair.getFoneContatoEmergencia());
		aupairAlterado.setMoedaCartao(aupair.getMoedaCartao());
		aupairAlterado.setCartaoVTM(aupair.getCartaoVTM());
		aupairAlterado.setNivelIdioma01(aupair.getNivelIdioma01());
		aupairAlterado.setNivelIdioma02(aupair.getNivelIdioma02());
		aupairAlterado.setNivelIdioma03(aupair.getNivelIdioma03());
		aupairAlterado.setDuracaoCurso(aupair.getDuracaoCurso());
		aupairAlterado.setIdioma01(aupair.getIdioma01());
		aupairAlterado.setIdioma02(aupair.getIdioma02());
		aupairAlterado.setIdioma03(aupair.getIdioma03());
		aupairAlterado.setNumeroCartao(aupair.getNumeroCartao());
		aupairAlterado.setFoneAmigo(aupair.getFoneAmigo());
		aupairAlterado.setEndercoAmigo(aupair.getEndercoAmigo());
	}

	public void carregarCampos() {
		aupair.setCurso("");
		aupair.setFoneContatoEmergencia("");
		aupair.setNomeContatoEmergencia("");
		aupair.setObservacaoPassagem("");
		aupair.setTipoPassagem("");
		aupair.setRelacaoContatoEmergencia("");
		aupair.setObstm("");
		aupair.setNomeAmigo("");
		aupair.setFoneAmigo("");
		aupair.setEndercoAmigo("");
		aupair.setIdioma01("");
		aupair.setIdioma02("");
		aupair.setIdioma03("");
		aupair.setNivelIdioma01("");
		aupair.setNivelIdioma02("");
		aupair.setNivelIdioma03("");
		aupair.setRelacaoAmigo("");
		aupair.setControle("Processo");
		aupair.setObservacaoVisto("");
		aupair.setDataInscricao(new Date());
	}

	public void habilitarPassagem() {
		if (aupair.getTipoPassagem().equalsIgnoreCase("Através da TravelMate")) {
			camposPassagem = "false";
		} else {
			camposPassagem = "true";
		}
	}

	public void habilitarAmigo() {
		if (aupair.getPossuiAmigosPais().equalsIgnoreCase("Sim")) {
			camposAmigo = "false";
		} else {
			camposAmigo = "true";
		}
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

	public String validarMascaraFoneAmigo() {
		return aplicacaoMB.validarMascaraTelefone(digitosTelefoneAmigo);
	}

	public String validarMascaraFoneContatoEmergencia() {
		return aplicacaoMB.validarMascaraTelefone(digitosTelefoneContatoEmergencia);
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

	public void selecionarCambio() {
		if (pais != null && pais.getIdpais() != null) {
			moeda = pais.getMoedas();
			consultarCambio();
		}
	}
}