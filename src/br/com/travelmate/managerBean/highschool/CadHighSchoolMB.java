package br.com.travelmate.managerBean.highschool;

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
import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoHighSchoolBean;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.facade.ValoresHighSchoolFacade;
import br.com.travelmate.facade.VendasFacade;
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
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valoreshighschool;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadHighSchoolMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private DashBoardMB dashBoardMB;
	@Inject
	private MateRunnersMB mateRunnersMB;
	@Inject
	private ProductRunnersMB productRunnersMB;
	@Inject
	private TmRaceMB tmRaceMB;
	private Highschool highschool;
	private Valoreshighschool valoreshighschool;
	private List<Valoreshighschool> listaValores;
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
	private Highschool highschoolAlterado;
	private Vendas vendaAlterada;
	private float valorVendaAlterar = 0.0f;
	private Fornecedorcidade fornecedorCidadeAlterado;
	private boolean consultaCambio = false;
	private Moedas moeda;
	private float valorMoedaReal;
	private float valorMoedaEstrangeira;
	private float valorSaldoParcelar;
	private List<String> listaTipoParcelamento;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga;
	private boolean digitosFoneContatoEmergencia;
	private Cancelamento cancelamento;
	private Lead lead;
	private String voltarControleVendas = "";

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		highschool = (Highschool) session.getAttribute("highschool");
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("cliente");
		session.removeAttribute("lead");
		session.removeAttribute("highschool");
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		carregarComboMoedas();
		gerarListaProdutos();
		if (highschool == null) {
			iniciarNovo();
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
		} else {
			iniciarAlteracao();
			if ((venda.getSituacao().equalsIgnoreCase("PROCESSO")) && (venda.isRestricaoparcelamento())) {
				verificarAlteracaoListaParcelamento();
			}
		}
		parcelamentopagamento.setNumeroParcelas(01);
		parcelamentopagamento.setFormaPagamento("sn");
		gerarListaTipoParcelamento();
		digitosFoneContatoEmergencia = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
				highschool.getFoneContatoEmergencia());
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Highschool getHighschool() {
		return highschool;
	}

	public void setHighschool(Highschool highschool) {
		this.highschool = highschool;
	}

	public Valoreshighschool getValoreshighschool() {
		return valoreshighschool;
	}

	public void setValoreshighschool(Valoreshighschool valoreshighschool) {
		this.valoreshighschool = valoreshighschool;
	}

	public List<Valoreshighschool> getListaValores() {
		return listaValores;
	}

	public void setListaValores(List<Valoreshighschool> listaValores) {
		this.listaValores = listaValores;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Parcelamentopagamento getParcelamentopagamento() {
		return parcelamentopagamento;
	}

	public void setParcelamentopagamento(Parcelamentopagamento parcelamentopagamento) {
		this.parcelamentopagamento = parcelamentopagamento;
	}

	public Orcamento getOrcamento() {
		return orcamento;
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

	public Date getDataCambio() {
		return dataCambio;
	}

	public void setDataCambio(Date dataCambio) {
		this.dataCambio = dataCambio;
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

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
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

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public Orcamentoprodutosorcamento getOrcamentoprodutosorcamento() {
		return orcamentoprodutosorcamento;
	}

	public void setOrcamentoprodutosorcamento(Orcamentoprodutosorcamento orcamentoprodutosorcamento) {
		this.orcamentoprodutosorcamento = orcamentoprodutosorcamento;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public List<Filtroorcamentoproduto> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public boolean isEnviarFicha() {
		return enviarFicha;
	}

	public void setEnviarFicha(boolean enviarFicha) {
		this.enviarFicha = enviarFicha;
	}

	public boolean isNovaFicha() {
		return novaFicha;
	}

	public void setNovaFicha(boolean novaFicha) {
		this.novaFicha = novaFicha;
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

	public Highschool getHighschoolAlterado() {
		return highschoolAlterado;
	}

	public void setHighschoolAlterado(Highschool highschoolAlterado) {
		this.highschoolAlterado = highschoolAlterado;
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

	public Fornecedorcidade getFornecedorCidadeAlterado() {
		return fornecedorCidadeAlterado;
	}

	public void setFornecedorCidadeAlterado(Fornecedorcidade fornecedorCidadeAlterado) {
		this.fornecedorCidadeAlterado = fornecedorCidadeAlterado;
	}

	public boolean isConsultaCambio() {
		return consultaCambio;
	}

	public void setConsultaCambio(boolean consultaCambio) {
		this.consultaCambio = consultaCambio;
	}

	public Moedas getMoeda() {
		return moeda;
	}

	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
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

	public DashBoardMB getDashBoardMB() {
		return dashBoardMB;
	}

	public void setDashBoardMB(DashBoardMB dashBoardMB) {
		this.dashBoardMB = dashBoardMB;
	}

	public MateRunnersMB getMateRunnersMB() {
		return mateRunnersMB;
	}

	public void setMateRunnersMB(MateRunnersMB mateRunnersMB) {
		this.mateRunnersMB = mateRunnersMB;
	}

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoOriginal() {
		return listaParcelamentoPagamentoOriginal;
	}

	public void setListaParcelamentoPagamentoOriginal(List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal) {
		this.listaParcelamentoPagamentoOriginal = listaParcelamentoPagamentoOriginal;
	}

	public void iniciarNovo() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		produto = new Produtos();
		fornecedorCidade = new Fornecedorcidade();
		cambio = new Cambio();
		highschool = new Highschool();
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
		valoreshighschool = new Valoreshighschool();
		produto = new Produtos();
		cidade = new Cidade();
		parcelamentopagamento = new Parcelamentopagamento();
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento produtosorcamento = produtoOrcamentoFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
		orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
		orcamentoprodutosorcamento.setValorMoedaNacional(aplicacaoMB.getParametrosprodutos().getValorTaxaTM());
		orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
		parcelamentopagamento = new Parcelamentopagamento();
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		consultaCambio = true;
		novaFicha = true;
	}

	public void carregarCampos() {
		highschool.setEmailConatoEmergencia("");
		highschool.setEscolaIdioma01("");
		highschool.setEscolaIdioma02("");
		highschool.setEscolaIdioma03("");
		highschool.setIdioma01("");
		highschool.setIdioma02("");
		highschool.setIdioma03("");
		highschool.setNomeIrmao01("");
		highschool.setNomeIrmao02("");
		highschool.setNomeirmao03("");
		highschool.setNivelIdioma01("");
		highschool.setNivelIdioma02("");
		highschool.setNivelIdioma03("");
		highschool.setQuandoReprovado("");
		highschool.setTempoIdioma01("");
		highschool.setTempoIdioma02("");
		highschool.setTempoIdioma03("");
		highschool.setSerie("");
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
					+ aplicacaoMB.getParametrosprodutos().getHighSchool() + " and f.cidade.idcidade="
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
			String sql = "select v from Valoreshighschool v where v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade() + " and (v.situacao='Ativo' or v.situacao='Temporário')"
					+ " and v.datavalidade>='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
			ValoresHighSchoolFacade valoresHighSchoolFacade = new ValoresHighSchoolFacade();
			listaValores = valoresHighSchoolFacade.listar(sql);
			if (listaValores == null) {
				listaValores = new ArrayList<Valoreshighschool>();
				if (highschool.getIdhighschool() != null) {
					if (valoreshighschool != null) {
						listaValores.add(valoreshighschool);
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
				if (venda.getIdvendas() != null) {
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamentopagamento = contasReceberBean.gerarParcelasIndividuais(parcelamentopagamento,
								formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB, Formatacao.ConvercaoStringData(highschool.getDataInicio()));
					}
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamentopagamento);
				if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")
						|| (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("cheque"))) {
					parcelamentopagamento.setVerificarParcelamento(
							Formatacao.calcularDataParcelamento(parcelamentopagamento.getDiaVencimento(),
									parcelamentopagamento.getNumeroParcelas(), valoreshighschool.getDatainicio()));
				} else
					parcelamentopagamento.setVerificarParcelamento(false);
				if (parcelamentopagamento.isVerificarParcelamento()) {
					Mensagem.lancarMensagemWarn("Data Vencimento", "Data da ultima parcela dos "
							+ parcelamentopagamento.getFormaPagamento() + " é maior que data início do programa.");
				}
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
							parcelamentopagamento.setDiaVencimento(Formatacao.SomarDiasDatas(parcelamentopagamento.getDiaVencimento(), numeroAdicionar));
							Mensagem.lancarMensagemInfo("Primeira parcela efetuada para o próximo dia útil", "");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
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
			msg = msg + "Data do 1º Vencimento Obrigatório";
		}else {
			if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")){
				try {
					msg = msg + Formatacao.validarDataBoleto(parcelamentopagamento.getDiaVencimento());
				} catch (Exception e) {
					e.printStackTrace();
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
				int idProdTx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produto = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getSeguroOrcamento());
				int idSeguro = produto.getIdprodutosOrcamento();
				if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento() != idSeguro) {
					if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
							.getIdprodutosOrcamento() != idProdTx) {
						if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira() > 0) {
							orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(
									orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira()
											* orcamento.getValorCambio());
						}
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
				} else if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getDescontofornecedor()) {
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
			valorMoedaEstrangeira = 0.0f;
			valorMoedaReal = 0.0f;
			calcularParcelamentoPagamento();
			parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
		}
	}

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getHighSchool());
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
				+ aplicacaoMB.getParametrosprodutos().getHighSchool()
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void adicionarProdutos() {
		int idProdTx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		if (produtosorcamento != null) {
			if (produtosorcamento.getIdprodutosOrcamento() != idProdTx) {
				if (orcamento.getValorCambio() > 0) {
					FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
					Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
							.pesquisar(aplicacaoMB.getParametrosprodutos().getHighSchool(), "High School");
					int idProduto = produto.getProdutosorcamento().getIdprodutosOrcamento();
					if (produtosorcamento.getIdprodutosOrcamento() != idProduto) {
						orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
						orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
						
//						if ((orcamentoprodutosorcamento.getValorMoedaEstrangeira() > 0)
//								&& (orcamento.getValorCambio() > 0)) {
//							orcamentoprodutosorcamento.setValorMoedaNacional(
//									orcamentoprodutosorcamento.getValorMoedaEstrangeira() * orcamento.getValorCambio());
//						} else {
//							if ((orcamentoprodutosorcamento.getValorMoedaNacional() > 0)
//									&& (orcamento.getValorCambio() > 0)) {
//								orcamentoprodutosorcamento
//										.setValorMoedaEstrangeira(orcamentoprodutosorcamento.getValorMoedaNacional()
//												/ orcamento.getValorCambio());
//							}
//						}
						if ((valorMoedaEstrangeira > 0) && (orcamento.getValorCambio() > 0)) {
							valorMoedaReal = valorMoedaEstrangeira * orcamento.getValorCambio();
						} else {
							if ((valorMoedaReal > 0) && (orcamento.getValorCambio() > 0)) {
								valorMoedaEstrangeira = valorMoedaReal / orcamento.getValorCambio();
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
					} else
						Mensagem.lancarMensagemErro("", "High School já incluso");
				} else
					Mensagem.lancarMensagemErro("Cambio não selecionado", "");
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Taxa TM já inclusa.", ""));
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Produto não selecionado.", ""));
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
		int tx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
				.getIdprodutosOrcamento() == tx) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Taxa TM não pode ser Excluída.", ""));
		} else {
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
					.pesquisar(aplicacaoMB.getParametrosprodutos().getHighSchool(), "High School");
			int idProduto = produto.getProdutosorcamento().getIdprodutosOrcamento();
			if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
					.getIdprodutosOrcamento() == idProduto) {
				Mensagem.lancarMensagemErro("High School não pode ser Excluído.", "");
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
	}

	public String cancelarCadastro() {
		if (voltarControleVendas != null) {
			if (voltarControleVendas.length() > 1) {
				return voltarControleVendas;
			}
		}
		return "consHighSchool";
	}

	public String naoEnviarficha() throws SQLException {
		enviarFicha = false;
		if (confirmarFicha()) {
			return "consHighSchool";
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
			return "consHighSchool";
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
			if (Formatacao.validarEmail(highschool.getEmailConatoEmergencia())) {
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
				ProgramasBean programasBean = new ProgramasBean();
				this.produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getHighSchool());
				venda = programasBean.salvarVendas(venda, usuarioLogadoMB, nsituacao, cliente,
						formaPagamento.getValorTotal(), produto, fornecedorCidade, cambio, orcamento.getValorCambio(),
						lead, highschool.getValoreshighschool().getDatainicio(), null);
				highschool.setControle("Processo");
				highschool.setVendas(venda);
				highschool.setValoreshighschool(valoreshighschool);
				highschool.setFornecedor(fornecedorCidade.getFornecedor());
				CadHighSchoolBean cadHighSchoolBean = new CadHighSchoolBean(venda, formaPagamento, orcamento,
						usuarioLogadoMB);
				if (enviarFicha && !novaFicha) {
					if (listaParcelamentoPagamentoOriginal != null && listaParcelamentoPagamentoAntiga != null) {
						cadHighSchoolBean.SalvarAlteracaoFinanceiro(listaParcelamentoPagamentoAntiga,
								listaParcelamentoPagamentoOriginal);
					}
				}
				highschool = cadHighSchoolBean.salvarHighSchool(highschool);
				orcamento = cadHighSchoolBean.salvarOrcamento(cambio, orcamento.getTotalMoedaNacional(),
						orcamento.getTotalMoedaEstrangeira(), orcamento.getValorCambio(), venda, cambioAlterado);
				formaPagamento = cadHighSchoolBean.salvarFormaPagamento(cancelamento);
				cliente = cadHighSchoolBean.salvarCliente(cliente);
				float valorPrevisto = 0.0f;
				if (venda.getSituacao().equalsIgnoreCase("FINALIZADA")
						|| venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
					float valorVendaatual = venda.getValor();

					valorPrevisto = 0.0f;
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
						ComissaoHighSchoolBean cc = new ComissaoHighSchoolBean(aplicacaoMB, venda,
								orcamento.getOrcamentoprodutosorcamentoList(), cambio,
								highschool.getValoreshighschool(), formaPagamento.getParcelamentopagamentoList(),
								vendasComissao, valoreshighschool.getDatainicio(), valorJuros);
						valorPrevisto = cc.getVendasComissao().getValorfornecedor();
					}

				}
				if (enviarFicha) {
					ControlerBean controlerBean = new ControlerBean();
					controlerBean.salvarControleHighSchool(venda, highschool, valorPrevisto);
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

							// new Thread() {
							// @Override
							// public void run() {
							DashBoardBean dashBoardBean = new DashBoardBean();
							dashBoardBean.calcularNumeroVendasProdutos(venda, false);
							dashBoardBean.calcularMetaMensal(venda, valorVendaAlterar, false);
							dashBoardBean.calcularMetaAnual(venda, valorVendaAlterar, false);
							String programa;
							if (valoreshighschool.getDuracao().equalsIgnoreCase("1 Semestre") || valoreshighschool.getDuracao().equalsIgnoreCase("1 Termo")) {
								programa = "Semestre";
							} else {
								programa = "Ano";
							}
							int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, programa, false);
							productRunnersMB.calcularPontuacao(venda, pontos[0], false);
							venda.setPonto(pontos[0]);
							venda.setPontoescola(pontos[1]);
							VendasFacade vendasFacade = new VendasFacade();
							venda = vendasFacade.salvar(venda);
							mateRunnersMB.carregarListaRunners();
							tmRaceMB.gerarListaGold();
							tmRaceMB.gerarListaSinze();
							tmRaceMB.gerarListaBronze();
							ContasReceberBean contasReceber = new ContasReceberBean(venda,
									formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, false, null);
							String titulo = "Nova Ficha de High School";
							String operacao = "A";
							String imagemNotificacao = "inserido";

							if (highschool.getIdhighschool() != null) {
								if (vendaAlterada != null) {
									titulo = "Ficha de High School Alterada";
									operacao = "I";
									imagemNotificacao = "alterado";
									verificarDadosAlterado();
								}
							}
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
										venda.getFornecedorcidade().getFornecedor().getNome(),
										highschool.getDataInicio(), venda.getUsuario().getNome(), vm, venda.getValor(),
										venda.getValorcambio(), venda.getCambio().getMoedas().getSigla(), operacao,
										departamento.get(0), imagemNotificacao, "I");
							}
							// }
							// }.start();
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

							// new Thread() {
							// @Override
							// public void run() {
							DashBoardBean dashBoardBean = new DashBoardBean();
							dashBoardBean.calcularMetaMensal(venda, valorVendaAlterar, false);
							dashBoardBean.calcularMetaAnual(venda, valorVendaAlterar, false);
							String programa;
							if (valoreshighschool.getProdutosorcamento().getIdprodutosOrcamento() == 12) {
								programa = "Semestre";
							} else {
								programa = "Ano";
							}
							int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, programa, false);
							productRunnersMB.calcularPontuacao(venda, pontos[0], false);
							venda.setPonto(pontos[0]);
							venda.setPontoescola(pontos[1]);
							VendasFacade vendasFacade = new VendasFacade();
							venda = vendasFacade.salvar(venda);
							mateRunnersMB.carregarListaRunners();
							tmRaceMB.gerarListaGold();
							tmRaceMB.gerarListaSinze();
							tmRaceMB.gerarListaBronze();
						}
						String titulo = "Nova Ficha de High School";
						String operacao = "A";
						String imagemNotificacao = "inserido";
						if (highschool.getIdhighschool() != null) {
							if (vendaAlterada != null) {
								titulo = "Ficha de High School Alterada";
								operacao = "I";
								imagemNotificacao = "alterado";
								verificarDadosAlterado();
							}
						}
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
									venda.getFornecedorcidade().getFornecedor().getNome(), highschool.getDataInicio(),
									venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
									venda.getCambio().getMoedas().getSigla(), operacao, departamento.get(0),
									imagemNotificacao, "A");
						}
						// }
						// }.start();
					}
				}
				Mensagem.lancarMensagemInfo("High School Salvo com Sucesso", "");
				salvarOK = true;
			}
		} else {
			Mensagem.lancarMensagemInfo(msg, "");
		}
		return salvarOK;
	}

	public void iniciarAlteracao() {
		this.venda = highschool.getVendas();
		valorVendaAlterar = venda.getValor();
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			vendaAlterada = venda;
		}
		this.cliente = venda.getCliente();
		carregarHighSchoolAlterado();
		fornecedorCidade = venda.getFornecedorcidade();
		cidade = fornecedorCidade.getCidade();
		pais = cidade.getPais();
		valoreshighschool = highschool.getValoreshighschool();
		carregarValores();
		listarFornecedorCidade();
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
			listaParcelamentoPagamentoAntiga = new ArrayList<>();
			for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
				Parcelamentopagamento parcelamentopagamento = new Parcelamentopagamento();
				parcelamentopagamento
						.setDiaVencimento(formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento());
				parcelamentopagamento
						.setFormaPagamento(formaPagamento.getParcelamentopagamentoList().get(i).getFormaPagamento());
				parcelamentopagamento
						.setNumeroParcelas(formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas());
				parcelamentopagamento
						.setValorParcela(formaPagamento.getParcelamentopagamentoList().get(i).getValorParcela());
				parcelamentopagamento.setValorParcelamento(
						formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento());
				parcelamentopagamento.setTipoParcelmaneto(
						formaPagamento.getParcelamentopagamentoList().get(i).getTipoParcelmaneto());
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
			moeda = orcamento.getCambio().getMoedas();
			carregarCambio();
			calcularValorTotalOrcamento();
		}

		consultaCambio = true;
		parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
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
		int codValores = aplicacaoMB.getParametrosprodutos().getHighSchool();
		for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
			int codigoLista = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
					.getIdprodutosOrcamento();
			if (codValores == codigoLista) {
				orcamento.getOrcamentoprodutosorcamentoList().remove(i);
			}
		}
		if (valoreshighschool != null) {
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
					.pesquisar(aplicacaoMB.getParametrosprodutos().getHighSchool(), "High School");
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
			if (valoreshighschool.getValorgross() > 0) {
				CambioFacade cambioFacade = new CambioFacade();
				Cambio cambioValor = new Cambio();
				cambioValor = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
						valoreshighschool.getMoedas().getIdmoedas());
				orcamentoprodutosorcamento
						.setValorMoedaNacional(valoreshighschool.getValorgross() * cambioValor.getValor());
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(valoreshighschool.getValorgross());
			} else {
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			}
			orcamentoprodutosorcamento.setPodeExcluir(false);
			orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			calcularValorTotalOrcamento();
			calcularParcelamentoPagamento();
			highschool.setDuracaoIntercambio(valoreshighschool.getDuracao());
			highschool.setPaisIntercambio(pais.getNome());
			highschool.setCidadeEscola(fornecedorCidade.getCidade().getNome());
			highschool.setEscolaIntercambio(fornecedorCidade.getFornecedor().getNome());
			highschool.setDataInicio(valoreshighschool.getInicio());
			highschool.setValoreshighschool(valoreshighschool);
		}
	}

	public void calcularValorParcelas() {
		if (parcelamentopagamento.getValorParcelamento() > 0) {
			parcelamentopagamento.setValorParcela(
					parcelamentopagamento.getValorParcelamento() / parcelamentopagamento.getNumeroParcelas());
		}
	}

	public String validarDados() {
		String msg = "";
		if (valoreshighschool != null) {
			if (fornecedorCidade == null) {
				msg = msg + "Campo escola não selecionado \r\n";
			}
			if (cambio == null) {
				msg = msg + "Selecione câmbio da ficha \r\n ";
			}
			if (cliente == null) {
				msg = msg + "Campo cliente não selecionado\r\n";
			}
			if (pais == null) {
				msg = msg + "País não informado\r\n";
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
			if (highschool.getCpfmae() == null || highschool.getCpfmae().length() == 0) {
				msg = msg + "CPF mãe não informado!";
			}
			if (highschool.getCpfpai() == null || highschool.getCpfpai().length() == 0) {
				msg = msg + "CPF pai não informado!";
			}
			if (highschool.getDataInicio() == null || highschool.getDataInicio().length() == 0) {
				msg = msg + "Data de Início não informado!";
			}
			if (highschool.getNomeContatoEmergencia() == null || highschool.getNomeContatoEmergencia().length() == 0) {
				msg = msg + "Nome contato de emergência não informado!";
			}
			if (highschool.getFoneContatoEmergencia() == null || highschool.getFoneContatoEmergencia().length() == 0) {
				msg = msg + "Fone contato de emergência não informado!";
			}
		} else {
			msg = msg + "Campo valores não está preenchido!\r\n";
		}
		return msg;
	}

	public void verificarDadosAlterado() {
		dadosAlterado = "";
		if (highschool.getCidadeEscola() != null) {
			if (!highschool.getCidadeEscola().equalsIgnoreCase(highschoolAlterado.getCidadeEscola())) {
				dadosAlterado = dadosAlterado + "Cidade escola : " + highschool.getCidadeEscola() + " | "
						+ highschoolAlterado.getCidadeEscola() + "<br></br>";
			}
		}
		if (highschool.getCpfmae() != null) {
			if (!highschool.getCpfmae().equalsIgnoreCase(highschoolAlterado.getCpfmae())) {
				dadosAlterado = dadosAlterado + "CPF mãe : " + highschool.getCpfmae() + " | "
						+ highschoolAlterado.getCpfmae() + "<br></br>";
			}
		}
		if (highschool.getCpfpai() != null) {
			if (!highschool.getCpfpai().equalsIgnoreCase(highschoolAlterado.getCpfpai())) {
				dadosAlterado = dadosAlterado + "CPF pai : " + highschool.getCpfpai() + " | "
						+ highschoolAlterado.getCpfpai() + "<br></br>";
			}
		}
		if (highschool.getDataInicio() != null) {
			if (!highschool.getDataInicio().equalsIgnoreCase(highschoolAlterado.getDataInicio())) {
				dadosAlterado = dadosAlterado + "Data Início : " + highschool.getDataInicio() + " | "
						+ highschoolAlterado.getDataInicio() + "<br></br>";
			}
		}
		if (highschool.getDatanascimentomae() != null) {
			if (highschool.getDatanascimentomae() != (highschoolAlterado.getDatanascimentomae())) {
				dadosAlterado = dadosAlterado + "Data Nascimento mãe : "
						+ Formatacao.ConvercaoDataPadrao(highschool.getDatanascimentomae()) + " | "
						+ Formatacao.ConvercaoDataPadrao(highschoolAlterado.getDatanascimentomae()) + "<br></br>";
			}
		}
		if (highschool.getDatanascimentopai() != null) {
			if (highschool.getDatanascimentopai() != (highschoolAlterado.getDatanascimentopai())) {
				dadosAlterado = dadosAlterado + "Data Nascimento pai : "
						+ Formatacao.ConvercaoDataPadrao(highschool.getDatanascimentopai()) + " | "
						+ Formatacao.ConvercaoDataPadrao(highschoolAlterado.getDatanascimentopai()) + "<br></br>";
			}
		}
		if (highschool.getDuracaoIntercambio() != null) {
			if (!highschool.getDuracaoIntercambio().equalsIgnoreCase(highschoolAlterado.getDuracaoIntercambio())) {
				dadosAlterado = dadosAlterado + "Duração Intercâmbio : " + highschool.getDuracaoIntercambio() + " | "
						+ highschoolAlterado.getDuracaoIntercambio() + "<br></br>";
			}
		}
		if (highschool.getEmailConatoEmergencia() != null) {
			if (!highschool.getEmailConatoEmergencia()
					.equalsIgnoreCase(highschoolAlterado.getEmailConatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Email Contato de Emergência : " + highschool.getEmailConatoEmergencia()
						+ " | " + highschoolAlterado.getEmailConatoEmergencia() + "<br></br>";
			}
		}
		if (highschool.getEscolaIntercambio() != null) {
			if (!highschool.getEscolaIntercambio().equalsIgnoreCase(highschoolAlterado.getEscolaIntercambio())) {
				dadosAlterado = dadosAlterado + "Escola Intercâmbio : " + highschool.getEscolaIntercambio() + " | "
						+ highschoolAlterado.getEscolaIntercambio() + "<br></br>";
			}
		}
		if (highschool.getFoneContatoEmergencia() != null) {
			if (!highschool.getFoneContatoEmergencia()
					.equalsIgnoreCase(highschoolAlterado.getFoneContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Fone Contato de Emergência : " + highschool.getFoneContatoEmergencia()
						+ " | " + highschoolAlterado.getFoneContatoEmergencia() + "<br></br>";
			}
		}
		if (highschool.getNomeContatoEmergencia() != null) {
			if (!highschool.getNomeContatoEmergencia()
					.equalsIgnoreCase(highschoolAlterado.getNomeContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Nome Contato de Emergência : " + highschool.getNomeContatoEmergencia()
						+ " | " + highschoolAlterado.getNomeContatoEmergencia() + "<br></br>";
			}
		}
		if (highschool.getNomeEscola() != null) {
			if (!highschool.getNomeEscola().equalsIgnoreCase(highschoolAlterado.getNomeEscola())) {
				dadosAlterado = dadosAlterado + "Nome Escola : " + highschool.getNomeEscola() + " | "
						+ highschoolAlterado.getNomeEscola() + "<br></br>";
			}
		}
		if (highschool.getObstm() != null) {
			if (!highschool.getObstm().equalsIgnoreCase(highschoolAlterado.getObstm())) {
				dadosAlterado = dadosAlterado + "Obs TM : " + highschool.getObstm() + " | "
						+ highschoolAlterado.getObstm() + "<br></br>";
			}
		}
		if (highschool.getQuandoReprovado() != null) {
			if (!highschool.getQuandoReprovado().equalsIgnoreCase(highschoolAlterado.getQuandoReprovado())) {
				dadosAlterado = dadosAlterado + "Quando foi reprovado? " + highschool.getQuandoReprovado() + " | "
						+ highschoolAlterado.getQuandoReprovado() + "<br></br>";
			}
		}
		if (highschool.getRelacaoContatoEmergencia() != null) {
			if (!highschool.getRelacaoContatoEmergencia()
					.equalsIgnoreCase(highschoolAlterado.getRelacaoContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Relação Contato de Emergência : "
						+ highschool.getRelacaoContatoEmergencia() + " | "
						+ highschoolAlterado.getRelacaoContatoEmergencia() + "<br></br>";
			}
		}
		if (highschool.getReprovadoEscola() != null) {
			if (!highschool.getReprovadoEscola().equalsIgnoreCase(highschoolAlterado.getReprovadoEscola())) {
				dadosAlterado = dadosAlterado + "Já foi reprovado? " + highschool.getReprovadoEscola() + " | "
						+ highschoolAlterado.getReprovadoEscola() + "<br></br>";
			}
		}
		if (highschool.getSerie() != null) {
			if (!highschool.getSerie().equalsIgnoreCase(highschoolAlterado.getSerie())) {
				dadosAlterado = dadosAlterado + "Série : " + highschool.getSerie() + " | "
						+ highschoolAlterado.getSerie() + "<br></br>";
			}
		}
	}

	public void carregarHighSchoolAlterado() {
		highschoolAlterado = new Highschool();
		highschoolAlterado.setCidadeEscola(highschool.getCidadeEscola());
		highschoolAlterado.setCpfmae(highschool.getCpfmae());
		highschoolAlterado.setCpfpai(highschool.getCpfpai());
		highschoolAlterado.setDataInicio(highschool.getDataInicio());
		highschoolAlterado.setDatanascimentomae(highschool.getDatanascimentomae());
		highschoolAlterado.setDatanascimentopai(highschool.getDatanascimentopai());
		highschoolAlterado.setDuracaoIntercambio(highschool.getDuracaoIntercambio());
		highschoolAlterado.setEmailConatoEmergencia(highschool.getEmailConatoEmergencia());
		highschoolAlterado.setEscolaIntercambio(highschool.getEscolaIntercambio());
		highschoolAlterado.setFoneContatoEmergencia(highschool.getFoneContatoEmergencia());
		highschoolAlterado.setNomeContatoEmergencia(highschool.getNomeContatoEmergencia());
		highschoolAlterado.setNomeEscola(highschool.getNomeEscola());
		highschoolAlterado.setObstm(highschool.getObstm());
		highschoolAlterado.setQuandoReprovado(highschool.getQuandoReprovado());
		highschoolAlterado.setRelacaoContatoEmergencia(highschool.getRelacaoContatoEmergencia());
		highschoolAlterado.setReprovadoEscola(highschool.getReprovadoEscola());
		highschoolAlterado.setSerie(highschool.getSerie());
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

	public void verificarAlteracaoListaParcelamento() {
		if (formaPagamento != null) {
			if (formaPagamento.getParcelamentopagamentoList() != null) {
				for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
					formaPagamento.getParcelamentopagamentoList().get(i)
							.setVerificarParcelamento(Formatacao.calcularDataParcelamento(
									formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento(),
									formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas(),
									valoreshighschool.getDatainicio()));
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

	public void selecionarCambio() {
		if (pais != null && pais.getIdpais() != null) {
			moeda = pais.getMoedas();
			consultarCambio();
		}
	}
	
	public String juncaoInicio(Valoreshighschool valores){
		return valores.getInicio() + " " + valores.getAnoinicio() + " - " + valores.getDuracao();
	}
	
	public boolean habilitarTrocaCliente() {
		if(novaFicha) {
			return false;
		}else return true;
	}
}
