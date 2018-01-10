package br.com.travelmate.managerBean.worktravel;

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
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.facade.ValoresWorkFacade;
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
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valoreswork;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadWorkTravelMB implements Serializable {

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
	private Worktravel work;
	private Valoreswork valoreswork;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Parcelamentopagamento parcelamentopagamento;
	private Orcamento orcamento;
	private Cambio cambio;
	private Date dataCambio;
	private Fornecedorcidade fornecedorCidade;
	private Pais pais;
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
	private Worktravel workAlterado;
	private Vendas vendaAlterada;
	private float valorVendaAlterar = 0.0f;
	private Fornecedorcidade fornecedorCidadeAlterado;
	private boolean consultaCambio = false;
	private int numeroSemanas;
	private String camposTrabalhoJ1 = "true";
	private String camposVisto = "true";
	private String camposPassagem = "true";
	private String camposVTM = "true";
	private Moedas moeda;
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
		work = (Worktravel) session.getAttribute("work");
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("cliente");
		session.removeAttribute("lead");
		session.removeAttribute("work");
		carregarComboMoedas();
		gerarListaProdutos();
		if (work == null) {
			iniciarNovo();
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
		} else {
			iniciarAlteracao();
			habilitarCamposJ1();
			habilitarCamposVisto();
			habilitarCamposPassagem();
			habilitarCamposVTM();
			if ((venda.getSituacao().equalsIgnoreCase("PROCESSO")) && (venda.isRestricaoparcelamento())) {
				verificarAlteracaoListaParcelamento();
			}
		}
		parcelamentopagamento.setNumeroParcelas(01);
		parcelamentopagamento.setFormaPagamento("sn");
		gerarListaTipoParcelamento();
		digitosFoneContatoEmergencia = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), work.getFoneContatoEmergencia());
	}

	public float getValorSaldoParcelar() {
		return valorSaldoParcelar;
	}

	public void setValorSaldoParcelar(float valorSaldoParcelar) {
		this.valorSaldoParcelar = valorSaldoParcelar;
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

	public Worktravel getWork() {
		return work;
	}

	public void setWork(Worktravel work) {
		this.work = work;
	}

	public Valoreswork getValoreswork() {
		return valoreswork;
	}

	public void setValoreswork(Valoreswork valoreswork) {
		this.valoreswork = valoreswork;
	}

	public String getCamposVisto() {
		return camposVisto;
	}

	public void setCamposVisto(String camposVisto) {
		this.camposVisto = camposVisto;
	}

	public String getCamposPassagem() {
		return camposPassagem;
	}

	public void setCamposPassagem(String camposPassagem) {
		this.camposPassagem = camposPassagem;
	}

	public String getCamposVTM() {
		return camposVTM;
	}

	public void setCamposVTM(String camposVTM) {
		this.camposVTM = camposVTM;
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

	public List<Filtroorcamentoproduto> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public Worktravel getWorkAlterado() {
		return workAlterado;
	}

	public void setWorkAlterado(Worktravel workAlterado) {
		this.workAlterado = workAlterado;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
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

	public int getNumeroSemanas() {
		return numeroSemanas;
	}

	public void setNumeroSemanas(int numeroSemanas) {
		this.numeroSemanas = numeroSemanas;
	}

	public String getCamposTrabalhoJ1() {
		return camposTrabalhoJ1;
	}

	public void setCamposTrabalhoJ1(String camposTrabalhoJ1) {
		this.camposTrabalhoJ1 = camposTrabalhoJ1;
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
		pais = new Pais();
		cidade = new Cidade();
		fornecedorCidade = new Fornecedorcidade();
		cambio = new Cambio();
		work = new Worktravel();
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
		produto = new Produtos();
		cidade = new Cidade();
		parcelamentopagamento = new Parcelamentopagamento();
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		consultaCambio = true;
		work.setCartaoVTM("Não");
		work.setTipoEmissaoPassagem("Cliente providenciará");
		work.setTipoEmissaoVisto("Cliente providenciará");
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

	public void carregarValores() {
		String sql = "select v from Valoreswork v where v.situacao='Ativo' and v.programa='" + work.getTipo()
				+ "' order by v.idvaloresWork DESC";
		ValoresWorkFacade valoresWorkFacade = new ValoresWorkFacade();
		List<Valoreswork> listaValoresWork = valoresWorkFacade.listar(sql);
		if (listaValoresWork != null && listaValoresWork.size() > 0) {
			valoreswork = listaValoresWork.get(0);
			fornecedorCidade = valoreswork.getFornecedorcidade();
			pais = fornecedorCidade.getCidade().getPais();
			cidade = fornecedorCidade.getCidade();
			moeda = valoreswork.getMoedas();
			consultarCambio();
		} else {
			listaValoresWork = new ArrayList<Valoreswork>();
			if (this.work.getIdworkTravel() != null) {
				if (valoreswork != null) {
					listaValoresWork.add(valoreswork);
				}
			}
		}
		if (listaValoresWork == null || listaValoresWork.size() == 0) {
			Mensagem.lancarMensagemInfo("Valores", "Programa não possui valor ativo. Entre em contato com a Gerencia");
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
				if (venda.getIdvendas() != null) {
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamentopagamento = contasReceberBean.gerarParcelasIndividuais(parcelamentopagamento,
								formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB);
					}
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamentopagamento);
				if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")
						|| (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("cheque"))) {
					parcelamentopagamento.setVerificarParcelamento(Formatacao.calcularDataParcelamento(
							parcelamentopagamento.getDiaVencimento(), parcelamentopagamento.getNumeroParcelas(),
							aplicacaoMB.getParametrosprodutos().getDatainiciowork()));
				} else
					parcelamentopagamento.setVerificarParcelamento(false);
				if (parcelamentopagamento.isVerificarParcelamento()) {
					Mensagem.lancarMensagemWarn("Data Vencimento", "Data da ultima parcela dos "
							+ parcelamentopagamento.getFormaPagamento() + " é maior que data início do Curso");
				}
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
			valorSaldoParcelar = formaPagamento.getValorTotal();
			calcularParcelamentoPagamento();
		}
	}

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getWork());
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
				+ aplicacaoMB.getParametrosprodutos().getWork()
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void adicionarProdutos() {
		if (orcamento.getValorCambio() > 0) {
			if (produtosorcamento != null) {
				orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
				orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
				if ((orcamentoprodutosorcamento.getValorMoedaEstrangeira() > 0) && (orcamento.getValorCambio() > 0)) {
					orcamentoprodutosorcamento.setValorMoedaNacional(
							orcamentoprodutosorcamento.getValorMoedaEstrangeira() * orcamento.getValorCambio());
				} else {
					if ((orcamentoprodutosorcamento.getValorMoedaNacional() > 0) && (orcamento.getValorCambio() > 0)) {
						orcamentoprodutosorcamento.setValorMoedaEstrangeira(
								orcamentoprodutosorcamento.getValorMoedaNacional() / orcamento.getValorCambio());
					}
				}
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
				calcularValorTotalOrcamento();
				produtosorcamento = null;
				orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Produto não selecionado", ""));
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio não selecionado", ""));
		}
	}

	public void excluirProdutoOrcamento(String linha) {
		int ilinha = Integer.parseInt(linha);
		if (!orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).isPodeExcluir()) {
			Mensagem.lancarMensagemErro("Produto não pode ser excluído.", "");
		} else {
			if (ilinha >= 0) {
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
		return "consWorkandTravel";
	}

	public String naoEnviarficha() throws SQLException {
		enviarFicha = false;
		if (confirmarFicha()) {
			return "consWorkandTravel";
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
			return "consWorkandTravel";
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
			if (Formatacao.validarEmail(work.getEmailContatoEmergencia())) {
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
				this.produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getWork());
				venda = programasBean.salvarVendas(venda, usuarioLogadoMB, nsituacao, cliente,
						formaPagamento.getValorTotal(), produto, fornecedorCidade, cambio, orcamento.getValorCambio(),
						lead, work.getDataInicioPretendida01(), work.getDataTerminoPretendida01());
				CadWorkTravelBean cadWorkTravelBean = new CadWorkTravelBean(venda, formaPagamento, orcamento,
						usuarioLogadoMB, work);
				if (enviarFicha) {
					cadWorkTravelBean.SalvarAlteracaoFinanceiro(listaParcelamentoPagamentoAntiga,
							listaParcelamentoPagamentoOriginal);
				}
				work.setValoreswork(valoreswork);
				work = cadWorkTravelBean.salvarWork(work, vendaAlterada);
				orcamento = cadWorkTravelBean.salvarOrcamento(cambio, orcamento.getTotalMoedaNacional(),
						orcamento.getTotalMoedaEstrangeira(), orcamento.getValorCambio(), cambioAlterado);
				formaPagamento = cadWorkTravelBean.salvarFormaPagamento(cancelamento);
				Date data = Formatacao.calcularPrevisaoPagamentoFornecedor(new Date(),
						venda.getProdutos().getIdprodutos(), aplicacaoMB.getParametrosprodutos().getWork());
				cliente = cadWorkTravelBean.salvarCliente(cliente, Formatacao.ConvercaoDataPadrao(data), null, null);
				if (enviarFicha) {
					cadWorkTravelBean.salvarNovaFichha(aplicacaoMB);
				}
				if (!novaFicha) {
					cadWorkTravelBean.verificarDadosAlterado(work, workAlterado, fornecedorCidade, vendaAlterada,
							valorVendaAlterar);
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
							dashBoardBean.calcularMetaMensal(venda, 0, false);
							dashBoardBean.calcularMetaAnual(venda, 0, false);
							int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, work.getTipo(), false);
							venda.setPonto(pontos[0]);
							venda.setPontoescola(pontos[1]);
							VendasFacade vendasFacade = new VendasFacade();
							venda = vendasFacade.salvar(venda);
							productRunnersMB.calcularPontuacao(venda, pontos[0], false);
							mateRunnersMB.carregarListaRunners();
							tmRaceMB.gerarListaGold();
							tmRaceMB.gerarListaSinze();
							tmRaceMB.gerarListaBronze();
							String titulo = "";
							String operacao = "";
							String imagemNotificacao = "";
							if (novaFicha) {
								titulo = "Nova Ficha de Work & Travel " + work.getTipo();
								operacao = "A";
								imagemNotificacao = "inserido";
							} else {
								titulo = "Ficha de Work & Travel Alterado " + work.getTipo();
								operacao = "I";
								imagemNotificacao = "alterado";
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
										Formatacao.ConvercaoDataPadrao(work.getDataInicioPretendida01()),
										venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
										venda.getCambio().getMoedas().getSigla(), operacao, departamento.get(0),
										imagemNotificacao, "I");
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
							int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, work.getTipo(), false);
							venda.setPonto(pontos[0]);
							venda.setPontoescola(pontos[1]);
							VendasFacade vendasFacade = new VendasFacade();
							venda = vendasFacade.salvar(venda);
							productRunnersMB.calcularPontuacao(venda, pontos[0], false);
							mateRunnersMB.carregarListaRunners();
							tmRaceMB.gerarListaGold();
							tmRaceMB.gerarListaSinze();
							tmRaceMB.gerarListaBronze();
						}
						String titulo = "";
						String operacao = "";
						String imagemNotificacao = "";
						if (novaFicha) {
							titulo = "Nova Ficha de Work and Travel";
							operacao = "A";
							imagemNotificacao = "inserido";
						} else {
							titulo = "Ficha de Work and Travel Alterado";
							operacao = "I";
							imagemNotificacao = "alterado";
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
									Formatacao.ConvercaoDataPadrao(work.getDataInicioPretendida01()),
									venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
									venda.getCambio().getMoedas().getSigla(), operacao, departamento.get(0),
									imagemNotificacao, "A");
						}
						// }
						// }.start();
					}
				}
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Work and Travel Salvo com Sucesso", ""));
				salvarOK = true;
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
		}
		return salvarOK;
	}

	public String validarDados() {
		String msg = "";
		if (valoreswork != null) {
			if (fornecedorCidade == null) {
				msg = msg + "Campo escola não selecionado     ";
			}
			if (cambio == null) {
				msg = msg + "Selecione câmbio da ficha     ";
			}
			if (cliente == null) {
				msg = msg + "Campo cliente não selecionado\r\n";
			}
			if (work.getCursoEstuda() == null) {
				msg = msg + "Curso que estuda não informado\r\n";
			}
			if (fornecedorCidade == null) {
				msg = msg + "Escola/Instituição não informada\r\n";
			}
			if (pais == null) {
				msg = msg + "País não informado\r\n";
			}
			if (work.getDataInicioPretendida01() == null) {
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
			msg = msg + "Campo Valores de Work and Travel não está preenchido!\r\n";
		}
		return msg;
	}

	public void iniciarAlteracao() {
		this.venda = work.getVendas();
		valorVendaAlterar = venda.getValor();
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			vendaAlterada = venda;
		}
		this.cliente = venda.getCliente();
		carregarworkAlterado();
		fornecedorCidade = venda.getFornecedorcidade();
		cidade = fornecedorCidade.getCidade();
		pais = cidade.getPais();
		valoreswork = work.getValoreswork();
		parcelamentopagamento = new Parcelamentopagamento();
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		this.formaPagamento = formaPagamentoFacade.consultar(venda.getIdvendas());
		OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
		orcamento = orcamentoFacade.consultar(venda.getIdvendas());
		carregarValores();
		fornecedorCidade = venda.getFornecedorcidade();
		valoreswork = work.getValoreswork();
		fornecedorCidadeAlterado = fornecedorCidade;
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		fornecedorComissao = fornecedorComissaoCursoFacade.consultar(fornecedorCidade.getFornecedor().getIdfornecedor(),
				fornecedorCidade.getCidade().getPais().getIdpais());
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
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Cambio alterado para o dia atual", ""));
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
		carregarValores();
		int codValores = aplicacaoMB.getParametrosprodutos().getWork();
		for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
			int codigoLista = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
					.getIdprodutosOrcamento();
			if (codValores == codigoLista) {
				orcamento.getOrcamentoprodutosorcamentoList().remove(i);
			}
		}
		if (valoreswork != null) {
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
					.pesquisar(aplicacaoMB.getParametrosprodutos().getWork(), "Work and Travel");
			orcamentoprodutosorcamento.setProdutosorcamento(produto.getProdutosorcamento());
			orcamentoprodutosorcamento.setDescricao(produto.getProdutosorcamento().getDescricao());
			if (valoreswork.getValorgross() > 0) {
				CambioFacade cambioFacade = new CambioFacade();
				Cambio cambioValor = new Cambio();
				cambioValor = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
						valoreswork.getMoedas().getIdmoedas());
				orcamentoprodutosorcamento.setValorMoedaNacional(valoreswork.getValorgross() * cambioValor.getValor());
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(valoreswork.getValorgross());
				moeda = valoreswork.getMoedas();
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

	public void carregarworkAlterado() {
		workAlterado = new Worktravel();
		workAlterado.setCursoEstuda(work.getCursoEstuda());
		workAlterado.setEmailContatoEmergencia(work.getEmailContatoEmergencia());
		workAlterado.setFoneContatoEmergencia(work.getFoneContatoEmergencia());
		workAlterado.setFuma(work.getFuma());
		workAlterado.setNomeContatoEmergencia(work.getNomeContatoEmergencia());
		workAlterado.setNumeroSocialSecurity(work.getNumeroSocialSecurity());
		workAlterado.setObservacaoPassagem(work.getObservacaoPassagem());
		workAlterado.setPassagemAerea(work.getPassagemAerea());
		workAlterado.setProblemaSaude(work.getProblemaSaude());
		workAlterado.setRelacaoContatoEmergencia(work.getRelacaoContatoEmergencia());
		workAlterado.setAdicionais(work.getAdicionais());
		workAlterado.setFoneContatoEmergencia(work.getFoneContatoEmergencia());
		workAlterado.setMoedaCartaoVTM(work.getMoedaCartaoVTM());
		workAlterado.setCartaoVTM(work.getCartaoVTM());
		workAlterado.setNivelidioma01(work.getNivelidioma01());
		workAlterado.setNivelidioma02(work.getNivelidioma02());
		workAlterado.setNivelidioma03(work.getNivelidioma03());
		workAlterado.setDuracaoCurso(work.getDuracaoCurso());
		workAlterado.setListaAlergias(work.getListaAlergias());
		workAlterado.setPossuiDeficienciaFisica(work.getPossuiDeficienciaFisica());
		workAlterado.setTratamentoUsoDrogas(work.getTratamentoUsoDrogas());
		workAlterado.setTrabalhoVistoJ1(work.getTrabalhoVistoJ1());
		workAlterado.setCampanheiroViagem01(work.getCampanheiroViagem01());
		workAlterado.setCompanheiroViagem02(work.getCompanheiroViagem02());
		workAlterado.setCompanheiroViagem03(work.getCompanheiroViagem03());
		workAlterado.setNumeroCartaoVTM(work.getNumeroCartaoVTM());
	}

	public void carregarCampos() {
		work.setCursoEstuda("");
		work.setFoneContatoEmergencia("");
		work.setFuma("");
		work.setNomeContatoEmergencia("");
		work.setNumeroSocialSecurity("");
		work.setObservacaoPassagem("");
		work.setPassagemAerea("");
		work.setProblemaSaude("");
		work.setRelacaoContatoEmergencia("");
		work.setObstm("");
		work.setObservacaoVisto("");
		work.setOpcaoIndependente("");
		work.setTempoidioma01("");
		work.setTempoidioma02("");
		work.setTempoidioma03("");
		work.setIdioma01("");
		work.setIdioma02("");
		work.setIdioma03("");
		work.setNivelidioma01("");
		work.setNivelidioma02("");
		work.setNivelidioma03("");
		work.setDataInscricao(new Date());
	}

	public void habilitarCamposJ1() {
		if (work.getTrabalhoVistoJ1().equalsIgnoreCase("Sim")) {
			camposTrabalhoJ1 = "false";
		} else {
			camposTrabalhoJ1 = "true";
		}
	}

	public void habilitarCamposVisto() {
		if (work.getTipoEmissaoVisto().equalsIgnoreCase("Através da TravelMate")) {
			camposVisto = "false";
		} else {
			camposVisto = "true";
		}
	}

	public void habilitarCamposPassagem() {
		if (work.getTipoEmissaoPassagem().equalsIgnoreCase("Através da TravelMate")) {
			camposPassagem = "false";
		} else {
			camposPassagem = "true";
		}
	}

	public void habilitarCamposVTM() {
		if (work.getCartaoVTM().equalsIgnoreCase("Sim")) {
			camposVTM = "false";
		} else {
			camposVTM = "true";
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

	public void verificarAlteracaoListaParcelamento() {
		if (formaPagamento != null) {
			if (formaPagamento.getParcelamentopagamentoList() != null) {
				for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
					formaPagamento.getParcelamentopagamentoList().get(i)
							.setVerificarParcelamento(Formatacao.calcularDataParcelamento(
									formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento(),
									formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas(),
									work.getDataInicioPretendida01()));
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
}
