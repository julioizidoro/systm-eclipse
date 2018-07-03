package br.com.travelmate.managerBean.cursoTeens;

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
import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoProgramasTeensBean;
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
import br.com.travelmate.facade.ValoresProgramasTeensFacade;
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
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Valoresprogramasteens;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadCursosTeensMB implements Serializable {

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
	private Programasteens programasTeens;
	private Programasteens programasTeensAlterado;
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
	private Vendas vendaAlterada;
	private float valorVendaAlterar = 0.0f;
	private Fornecedorcidade fornecedorCidadeAlterado;
	private boolean consultaCambio = false;
	private Moedas moeda;
	private float valorMoedaReal;
	private float valorMoedaEstrangeira;
	private float valorSaldoParcelar;
	private String camposAcomodacao = "true";
	private String camposVisto = "true";
	private Valoresprogramasteens valoresprogramasteens;
	private List<Valoresprogramasteens> listaValores;
	private List<String> listaTipoParcelamento;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga;
	private boolean digitosFoneContatoEmergencia;
	private Cancelamento cancelamento;
	private Lead lead;
	private String voltarControleVendas = "";
	private boolean habilitarAvisoCambio = false;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		programasTeens = (Programasteens) session.getAttribute("programasTeens");
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("cliente");
		session.removeAttribute("lead");
		session.removeAttribute("programasTeens");
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		carregarComboMoedas();
		gerarListaProdutos();
		if (programasTeens == null) {
			iniciarNovo();
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
		} else {
			iniciarAlteracao();
			if ((venda.getSituacao().equalsIgnoreCase("PROCESSO")) && (venda.isRestricaoparcelamento())) {
				verificarAlteracaoListaParcelamento();
			}
		}
		parcelamentopagamento.setFormaPagamento("sn");
		parcelamentopagamento.setNumeroParcelas(01);
		gerarListaTipoParcelamento();
		digitosFoneContatoEmergencia = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
				programasTeens.getFoneContatoEmergencia());
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Programasteens getProgramasTeens() {
		return programasTeens;
	}

	public void setProgramasTeens(Programasteens programasTeens) {
		this.programasTeens = programasTeens;
	}

	public Programasteens getProgramasTeensAlterado() {
		return programasTeensAlterado;
	}

	public void setProgramasTeensAlterado(Programasteens programasTeensAlterado) {
		this.programasTeensAlterado = programasTeensAlterado;
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

	public String getCamposAcomodacao() {
		return camposAcomodacao;
	}

	public void setCamposAcomodacao(String camposAcomodacao) {
		this.camposAcomodacao = camposAcomodacao;
	}

	public String getCamposVisto() {
		return camposVisto;
	}

	public void setCamposVisto(String camposVisto) {
		this.camposVisto = camposVisto;
	}

	public Valoresprogramasteens getValoresprogramasteens() {
		return valoresprogramasteens;
	}

	public void setValoresprogramasteens(Valoresprogramasteens valoresprogramasteens) {
		this.valoresprogramasteens = valoresprogramasteens;
	}

	public List<Valoresprogramasteens> getListaValores() {
		return listaValores;
	}

	public void setListaValores(List<Valoresprogramasteens> listaValores) {
		this.listaValores = listaValores;
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

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoAntiga() {
		return listaParcelamentoPagamentoAntiga;
	}

	public void setListaParcelamentoPagamentoAntiga(List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga) {
		this.listaParcelamentoPagamentoAntiga = listaParcelamentoPagamentoAntiga;
	}

	public boolean isHabilitarAvisoCambio() {
		return habilitarAvisoCambio;
	}

	public void setHabilitarAvisoCambio(boolean habilitarAvisoCambio) {
		this.habilitarAvisoCambio = habilitarAvisoCambio;
	}

	public void iniciarNovo() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		produto = new Produtos();
		fornecedorCidade = new Fornecedorcidade();
		cambio = new Cambio();
		programasTeens = new Programasteens();
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
		valoresprogramasteens = new Valoresprogramasteens();
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
		programasTeens.setEmailContatoEmergencia("");
		programasTeens.setObservacaoVistos("");
		programasTeens.setTipoSolicitacao("");
		programasTeens.setObstm("");
		programasTeens.setSolicitarVisto("Cliente Providenciará");
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
					+ aplicacaoMB.getParametrosprodutos().getProgramasTeens() + " and f.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.ativo=1 order by f.fornecedor.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
	}

	public void carregarValores() {
		if (fornecedorCidade != null && fornecedorCidade.getIdfornecedorcidade() != null) {
			String sql = "select v from Valoresprogramasteens v where v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade() + " and v.situacao='Ativo'";
			ValoresProgramasTeensFacade valoresProgramasTeensFacade = new ValoresProgramasTeensFacade();
			listaValores = valoresProgramasTeensFacade.listar(sql);
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
								formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB, programasTeens.getDataInicioCurso());
					}
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamentopagamento);
				if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")
						|| (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("cheque"))) {
					parcelamentopagamento.setVerificarParcelamento(
							Formatacao.calcularDataParcelamento(parcelamentopagamento.getDiaVencimento(),
									parcelamentopagamento.getNumeroParcelas(), programasTeens.getDataInicioCurso()));
				} else
					parcelamentopagamento.setVerificarParcelamento(false);
				if (parcelamentopagamento.isVerificarParcelamento()) {
					Mensagem.lancarMensagemWarn("Data Vencimento", "Data da ultima parcela dos "
							+ parcelamentopagamento.getFormaPagamento() + " é maior que data início do Curso");
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
			msg = msg + "Data do 1º Vencimento Obrigatorio";
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
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getProgramasTeens());
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
				+ aplicacaoMB.getParametrosprodutos().getProgramasTeens()
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void adicionarProdutos() {
		int idProdTx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		if (produtosorcamento.getIdprodutosOrcamento() != idProdTx) {
			if (orcamento.getValorCambio() > 0) {
				FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
				Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade.pesquisar(
						aplicacaoMB.getParametrosprodutos().getProgramasTeens(), "Curso / Programa / Pacote");
				int idProduto = produto.getProdutosorcamento().getIdprodutosOrcamento();
				if (produtosorcamento.getIdprodutosOrcamento() != idProduto) {
					orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
					orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
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
					Mensagem.lancarMensagemErro("", "Curso Teens já incluso");
			} else
				Mensagem.lancarMensagemErro("Cambio não selecionado", "");
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
		int tx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
				.getIdprodutosOrcamento() == tx) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Taxa TM não pode ser Excluída.", ""));
		} else {
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
					.pesquisar(aplicacaoMB.getParametrosprodutos().getProgramasTeens(), "Curso / Programa / Pacote");
			int idProduto = produto.getProdutosorcamento().getIdprodutosOrcamento();
			if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
					.getIdprodutosOrcamento() == idProduto) {
				Mensagem.lancarMensagemErro("Curso Teens não pode ser Excluído.", "");
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
		return "consCursosTeens";
	}

	public String naoEnviarficha() throws SQLException {
		enviarFicha = false;
		if (confirmarFicha()) {
			return "consCursosTeens";
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
			return "consCursosTeens";
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
			if (Formatacao.validarEmail(programasTeens.getEmailContatoEmergencia())) {
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
				this.produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getProgramasTeens());
				venda = programasBean.salvarVendas(venda, usuarioLogadoMB, nsituacao, cliente,
						formaPagamento.getValorTotal(), produto, fornecedorCidade, cambio, orcamento.getValorCambio(),
						lead, programasTeens.getDataInicioCurso(), programasTeens.getDataTerminoCurso());
				if (venda.getIdvendas() != null) {
					if (venda.getCambio() != cambio) {
						cambioAlterado = "Sim";
					}
				}
				programasTeens.setControle("Processo");
				programasTeens.setVendas(venda);
				programasTeens.setValoresprogramasteens(valoresprogramasteens);
				programasTeens.setFornecedor(fornecedorCidade.getFornecedor());
				CadCursosTeensBean cadCursosTeensBean = new CadCursosTeensBean(venda, formaPagamento, orcamento,
						usuarioLogadoMB);
				if (enviarFicha && !novaFicha) {
					if (listaParcelamentoPagamentoOriginal != null && listaParcelamentoPagamentoAntiga != null) {
						cadCursosTeensBean.SalvarAlteracaoFinanceiro(listaParcelamentoPagamentoAntiga,
								listaParcelamentoPagamentoOriginal);
					}
				}
				programasTeens = cadCursosTeensBean.salvarProgramaTeens(programasTeens);
				this.orcamento = cadCursosTeensBean.salvarOrcamento(cambio, orcamento.getTotalMoedaNacional(),
						orcamento.getTotalMoedaEstrangeira(), orcamento.getValorCambio(), venda, cambioAlterado);
				this.formaPagamento = cadCursosTeensBean.salvarFormaPagamento(cancelamento);
				this.cliente = cadCursosTeensBean.salvarCliente(cliente);
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
						ComissaoProgramasTeensBean cc = new ComissaoProgramasTeensBean(aplicacaoMB, venda,
								orcamento.getOrcamentoprodutosorcamentoList(), orcamento.getValorCambio(),
								programasTeens.getValoresprogramasteens(),
								formaPagamento.getParcelamentopagamentoList(), programasTeens.getDataInicioCurso(),
								vendasComissao, valorJuros);
						valorPrevisto = cc.getVendasComissao().getValorfornecedor();
					}
				}
				ControlerBean controlerBean = new ControlerBean();
				controlerBean.salvarControleProgramaTeens(venda, programasTeens, valorPrevisto);
				if (venda.getSituacao().equalsIgnoreCase("FINALIZADA")) {
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
							int[] pontos = dashBoardBean.calcularPontuacao(venda, programasTeens.getNumeroSemanas(), "",
									false);
							int pontoremover = vendaAlterada.getPonto();
							productRunnersMB.calcularPontuacao(venda, pontos[0], pontoremover, false);
							venda.setPonto(pontos[0]);
							venda.setPontoescola(pontos[1]);
							VendasFacade vendasFacade = new VendasFacade();
							venda = vendasFacade.salvar(venda);
							mateRunnersMB.carregarListaRunners();
						}
						String titulo = "Nova Ficha de Cursos Teens. " + venda.getIdvendas();
						String operacao = "A";
						String imagemNotificacao = "inserido";
						if (programasTeens.getIdprogramasTeens() != null) {
							if (vendaAlterada != null) {
								titulo = "Ficha de Curso Teens Alterada. " + venda.getIdvendas();
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
									Formatacao.ConvercaoDataPadrao(programasTeens.getDataInicioCurso()),
									venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
									venda.getCambio().getMoedas().getSigla(), operacao, departamento.get(0),
									imagemNotificacao, "A");
						}
					}
				}
				Mensagem.lancarMensagemInfo("Curso Teens Salvo com Sucesso", "");
				salvarOK = true;
			}
		} else {
			Mensagem.lancarMensagemInfo(msg, "");
		}
		return salvarOK;
	}

	public void iniciarAlteracao() {
		this.venda = programasTeens.getVendas();
		valorVendaAlterar = venda.getValor();
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			vendaAlterada = venda;
		}
		this.cliente = venda.getCliente();
		carregarprogramasTeensAlterado();
		fornecedorCidade = venda.getFornecedorcidade();
		cidade = fornecedorCidade.getCidade();
		pais = cidade.getPais();
		carregarValores();
		listarFornecedorCidade();
		fornecedorCidade = venda.getFornecedorcidade();
		valoresprogramasteens = programasTeens.getValoresprogramasteens();
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
			carregarCamposAcomodacao();
		}

		consultaCambio = true;
		parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
		// acomodacao
		if (programasTeens.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
		} else {
			camposAcomodacao = "false";
		}
	}

	public void carregarCamposFormaPagamento() {
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			calcularParcelamentoPagamento();
		}
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
		int codValores = aplicacaoMB.getParametrosprodutos().getProgramasTeens();
		for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
			int codigoLista = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
					.getIdprodutosOrcamento();
			if (codValores == codigoLista) {
				orcamento.getOrcamentoprodutosorcamentoList().remove(i);
			}
		}
		if (valoresprogramasteens != null) {
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			Filtroorcamentoproduto produto = filtroOrcamentoProdutoFacade
					.pesquisar(aplicacaoMB.getParametrosprodutos().getProgramasTeens(), "Curso / Programa / Pacote");
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
			if (valoresprogramasteens.getValorgross() > 0) {
				CambioFacade cambioFacade = new CambioFacade();
				Cambio cambioValor = new Cambio();
				cambioValor = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
						valoresprogramasteens.getMoedas().getIdmoedas());
				orcamentoprodutosorcamento
						.setValorMoedaNacional(valoresprogramasteens.getValorgross() * cambioValor.getValor());
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(valoresprogramasteens.getValorgross());
			} else {
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			}
			orcamentoprodutosorcamento.setPodeExcluir(false);
			orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			calcularValorTotalOrcamento();
			calcularParcelamentoPagamento();
			programasTeens.setCidadeCurso(fornecedorCidade.getCidade().getNome());
			programasTeens.setFornecedor(fornecedorCidade.getFornecedor());
			programasTeens.setPaisCurso(fornecedorCidade.getCidade().getPais().getNome());
			programasTeens.setNomeEscola(fornecedorCidade.getFornecedor().getNome());
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
		if (valoresprogramasteens != null) {
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
			if (programasTeens.getCpfmae() == null || programasTeens.getCpfmae().length() == 0) {
				msg = msg + "CPF mãe não informado!";
			}
			if (programasTeens.getCpfpai() == null || programasTeens.getCpfpai().length() == 0) {
				msg = msg + "CPF pai não informado!";
			}
			if (programasTeens.getDataInicioCurso() == null) {
				msg = msg + "Data de Início não informado!";
			}
			if (programasTeens.getNomeContatoEmergencia() == null
					|| programasTeens.getNomeContatoEmergencia().length() == 0) {
				msg = msg + "Nome contato de emergência não informado!";
			}
			if (programasTeens.getFoneContatoEmergencia() == null
					|| programasTeens.getFoneContatoEmergencia().length() == 0) {
				msg = msg + "Fone contato de emergência não informado!";
			}
			if (programasTeens.getNumeroSemanas() == null) {
				msg = msg + "Numero de semanas de curso não informado\r\n";
			}
			if (!programasTeens.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
				if (programasTeens.getDataChegadaAcomodacao() == null) {
					msg = msg + "Data chegada na acomodação inválida\r\n";
				}
				if (programasTeens.getDataPartidaAcomodacao() == null) {
					msg = msg + "Data partida acomodação inválida\r\n";
				}
				if (programasTeens.getNumeroSemanasAcomodacao() == null) {
					msg = msg + "Numero de semanas de acomodação não informado\r\n";
				}
			}
		} else {
			msg = msg + "Campo valores não está preenchido!\r\n";
		}
		return msg;
	}

	public void verificarDadosAlterado() {
		dadosAlterado = "";
		if (programasTeens.getCidadeCurso() != null) {
			if (!programasTeens.getCidadeCurso().equalsIgnoreCase(programasTeensAlterado.getCidadeCurso())) {
				dadosAlterado = dadosAlterado + "Cidade curso : " + programasTeens.getCidadeCurso() + " | "
						+ programasTeensAlterado.getCidadeCurso() + "<br></br>";
			}
		}
		if (programasTeens.getCpfmae() != null) {
			if (!programasTeens.getCpfmae().equalsIgnoreCase(programasTeensAlterado.getCpfmae())) {
				dadosAlterado = dadosAlterado + "CPF mãe : " + programasTeens.getCpfmae() + " | "
						+ programasTeensAlterado.getCpfmae() + "<br></br>";
			}
		}
		if (programasTeens.getCpfpai() != null) {
			if (!programasTeens.getCpfpai().equalsIgnoreCase(programasTeensAlterado.getCpfpai())) {
				dadosAlterado = dadosAlterado + "CPF pai : " + programasTeens.getCpfpai() + " | "
						+ programasTeensAlterado.getCpfpai() + "<br></br>";
			}
		}
		if (programasTeens.getDataInicioCurso() != null) {
			if (programasTeens.getDataInicioCurso() != programasTeensAlterado.getDataInicioCurso()) {
				dadosAlterado = dadosAlterado + "Data Início : " + programasTeens.getDataInicioCurso() + " | "
						+ programasTeensAlterado.getDataInicioCurso() + "<br></br>";
			}
		}
		if (programasTeens.getDatanascimentomae() != null) {
			if (programasTeens.getDatanascimentomae() != (programasTeensAlterado.getDatanascimentomae())) {
				dadosAlterado = dadosAlterado + "Data Nascimento mãe : "
						+ Formatacao.ConvercaoDataPadrao(programasTeens.getDatanascimentomae()) + " | "
						+ Formatacao.ConvercaoDataPadrao(programasTeensAlterado.getDatanascimentomae()) + "<br></br>";
			}
		}
		if (programasTeens.getDatanascimentopai() != null) {
			if (programasTeens.getDatanascimentopai() != (programasTeensAlterado.getDatanascimentopai())) {
				dadosAlterado = dadosAlterado + "Data Nascimento pai : "
						+ Formatacao.ConvercaoDataPadrao(programasTeens.getDatanascimentopai()) + " | "
						+ Formatacao.ConvercaoDataPadrao(programasTeensAlterado.getDatanascimentopai()) + "<br></br>";
			}
		}
		if (programasTeens.getNumeroSemanas() != null) {
			if (programasTeens.getNumeroSemanas() != programasTeensAlterado.getNumeroSemanas()) {
				dadosAlterado = dadosAlterado + "Número de Semanas curso : " + programasTeens.getNumeroSemanas() + " | "
						+ programasTeensAlterado.getNumeroSemanas() + "<br></br>";
			}
		}
		if (programasTeens.getEmailContatoEmergencia() != null) {
			if (!programasTeens.getEmailContatoEmergencia()
					.equalsIgnoreCase(programasTeensAlterado.getEmailContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Email Contato de Emergência : "
						+ programasTeens.getEmailContatoEmergencia() + " | "
						+ programasTeensAlterado.getEmailContatoEmergencia() + "<br></br>";
			}
		}
		if (programasTeens.getNomeEscola() != null) {
			if (!programasTeens.getNomeEscola().equalsIgnoreCase(programasTeensAlterado.getNomeEscola())) {
				dadosAlterado = dadosAlterado + "Escola : " + programasTeens.getNomeEscola() + " | "
						+ programasTeensAlterado.getNomeEscola() + "<br></br>";
			}
		}
		if (programasTeens.getFoneContatoEmergencia() != null) {
			if (!programasTeens.getFoneContatoEmergencia()
					.equalsIgnoreCase(programasTeensAlterado.getFoneContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Fone Contato de Emergência : "
						+ programasTeens.getFoneContatoEmergencia() + " | "
						+ programasTeensAlterado.getFoneContatoEmergencia() + "<br></br>";
			}
		}
		if (programasTeens.getNomeContatoEmergencia() != null) {
			if (!programasTeens.getNomeContatoEmergencia()
					.equalsIgnoreCase(programasTeensAlterado.getNomeContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Nome Contato de Emergência : "
						+ programasTeens.getNomeContatoEmergencia() + " | "
						+ programasTeensAlterado.getNomeContatoEmergencia() + "<br></br>";
			}
		}
		if (programasTeens.getObstm() != null) {
			if (!programasTeens.getObstm().equalsIgnoreCase(programasTeensAlterado.getObstm())) {
				dadosAlterado = dadosAlterado + "Obs TM : " + programasTeens.getObstm() + " | "
						+ programasTeensAlterado.getObstm() + "<br></br>";
			}
		}
		if (programasTeens.getNomeCurso() != null) {
			if (!programasTeens.getNomeCurso().equalsIgnoreCase(programasTeensAlterado.getNomeCurso())) {
				dadosAlterado = dadosAlterado + "Curso: " + programasTeens.getNomeCurso() + " | "
						+ programasTeensAlterado.getNomeCurso() + "<br></br>";
			}
		}
		if (programasTeens.getRelacaoContatoEmergencia() != null) {
			if (!programasTeens.getRelacaoContatoEmergencia()
					.equalsIgnoreCase(programasTeensAlterado.getRelacaoContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Relação Contato de Emergência : "
						+ programasTeens.getRelacaoContatoEmergencia() + " | "
						+ programasTeensAlterado.getRelacaoContatoEmergencia() + "<br></br>";
			}
		}
		if (programasTeens.getTipoPrograma() != null) {
			if (!programasTeens.getTipoPrograma().equalsIgnoreCase(programasTeensAlterado.getTipoPrograma())) {
				dadosAlterado = dadosAlterado + "Tipo programa : " + programasTeens.getTipoPrograma() + " | "
						+ programasTeensAlterado.getTipoPrograma() + "<br></br>";
			}
		}

		if (programasTeens.getTipoAcomodacao() != null) {
			if (!programasTeens.getTipoAcomodacao().equalsIgnoreCase(programasTeensAlterado.getTipoAcomodacao())) {
				dadosAlterado = dadosAlterado + "Tipo Acomodação: " + programasTeens.getTipoAcomodacao() + " | "
						+ programasTeensAlterado.getTipoAcomodacao() + "<br></br>";
			}
		}
		if (programasTeens.getNumeroSemanasAcomodacao() != null) {
			if (programasTeens.getNumeroSemanasAcomodacao() != programasTeensAlterado.getNumeroSemanasAcomodacao()) {
				dadosAlterado = dadosAlterado + "Nº Semanas Acomodação : "
						+ String.valueOf(programasTeens.getNumeroSemanasAcomodacao()) + " | "
						+ String.valueOf(programasTeensAlterado.getNumeroSemanasAcomodacao()) + "<br></br>";
			}
		}
		if (programasTeens.getTipoQuarto() != null) {
			if (!programasTeens.getTipoQuarto().equalsIgnoreCase(programasTeensAlterado.getTipoQuarto())) {
				dadosAlterado = dadosAlterado + "Quarto : " + programasTeens.getTipoQuarto() + " | "
						+ programasTeensAlterado.getTipoQuarto() + "<br></br>";
			}
		}
		if (programasTeens.getRefeicao() != null) {
			if (!programasTeens.getRefeicao().equalsIgnoreCase(programasTeensAlterado.getRefeicao())) {
				dadosAlterado = dadosAlterado + "Refeição : " + programasTeens.getRefeicao() + " | "
						+ programasTeensAlterado.getRefeicao() + "<br></br>";
			}
		}
		if (programasTeens.getDataChegadaAcomodacao() != null) {
			if (!programasTeens.getDataChegadaAcomodacao().equals(programasTeensAlterado.getDataChegadaAcomodacao())) {
				dadosAlterado = dadosAlterado + "Data Chegada : "
						+ Formatacao.ConvercaoDataPadrao(programasTeens.getDataChegadaAcomodacao()) + " | "
						+ Formatacao.ConvercaoDataPadrao(programasTeensAlterado.getDataChegadaAcomodacao())
						+ "<br></br>";
			}
		}
		if (programasTeens.getDataPartidaAcomodacao() != null) {
			if (!programasTeens.getDataPartidaAcomodacao().equals(programasTeensAlterado.getDataPartidaAcomodacao())) {
				dadosAlterado = dadosAlterado + "Data Partida : "
						+ Formatacao.ConvercaoDataPadrao(programasTeens.getDataPartidaAcomodacao()) + " |  "
						+ Formatacao.ConvercaoDataPadrao(programasTeensAlterado.getDataPartidaAcomodacao())
						+ "<br></br>";
			}
		}
	}

	public void carregarprogramasTeensAlterado() {
		programasTeensAlterado = new Programasteens();
		programasTeensAlterado.setCidadeCurso(programasTeens.getCidadeCurso());
		programasTeensAlterado.setCpfmae(programasTeens.getCpfmae());
		programasTeensAlterado.setCpfpai(programasTeens.getCpfpai());
		programasTeensAlterado.setDataInicioCurso(programasTeens.getDataInicioCurso());
		programasTeensAlterado.setDatanascimentomae(programasTeens.getDatanascimentomae());
		programasTeensAlterado.setDatanascimentopai(programasTeens.getDatanascimentopai());
		programasTeensAlterado.setNumeroSemanas(programasTeens.getNumeroSemanas());
		programasTeensAlterado.setEmailContatoEmergencia(programasTeens.getEmailContatoEmergencia());
		programasTeensAlterado.setNomeEscola(programasTeens.getNomeEscola());
		programasTeensAlterado.setFoneContatoEmergencia(programasTeens.getFoneContatoEmergencia());
		programasTeensAlterado.setNomeContatoEmergencia(programasTeens.getNomeContatoEmergencia());
		programasTeensAlterado.setObstm(programasTeens.getObstm());
		programasTeensAlterado.setRelacaoContatoEmergencia(programasTeens.getRelacaoContatoEmergencia());
		programasTeensAlterado.setDataChegadaAcomodacao(programasTeens.getDataChegadaAcomodacao());
		programasTeensAlterado.setDataPartidaAcomodacao(programasTeens.getDataPartidaAcomodacao());
		programasTeensAlterado.setNumeroSemanasAcomodacao(programasTeens.getNumeroSemanasAcomodacao());
		programasTeensAlterado.setTipoAcomodacao(programasTeens.getTipoAcomodacao());
		programasTeensAlterado.setTipoPrograma(programasTeens.getTipoPrograma());
		programasTeensAlterado.setTipoRefeicao(programasTeens.getTipoRefeicao());
		programasTeensAlterado.setTipoQuarto(programasTeens.getTipoQuarto());
	}

	public void carregarCamposAcomodacao() {
		if (programasTeens.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
			programasTeens.setTipoQuarto("Sem quarto");
			programasTeens.setRefeicao("Sem Refeição");
			programasTeens.setDataChegadaAcomodacao(null);
			programasTeens.setNumeroSemanasAcomodacao(null);
			programasTeens.setDataPartidaAcomodacao(null);
		} else {
			camposAcomodacao = "false";
		}
	}

	public void calcularDataTerminoAcomodacao() {
		if ((programasTeens.getDataChegadaAcomodacao() != null)
				&& (programasTeens.getNumeroSemanasAcomodacao() != null)) {
			if (programasTeens.getNumeroSemanasAcomodacao() > 0) {
				Date data = Formatacao.calcularDataFinal(programasTeens.getDataChegadaAcomodacao(),
						programasTeens.getNumeroSemanasAcomodacao());
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 6) {
						data = Formatacao.SomarDiasDatas(data, 2);
					}
					programasTeens.setDataPartidaAcomodacao(data);
				} catch (Exception ex) {
					Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
							.log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	public void calcularDataTerminoCurso() {
		if ((programasTeens.getDataInicioCurso() != null) && (programasTeens.getNumeroSemanas() != null)) {
			if (programasTeens.getNumeroSemanas() > 0) {
				Date data = Formatacao.calcularDataFinal(programasTeens.getDataInicioCurso(),
						programasTeens.getNumeroSemanas());
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
				programasTeens.setDataTerminoCurso(data);
			}
		}
	}

	public void carregarCamposVisto() {
		if (!programasTeens.getSolicitarVisto().equalsIgnoreCase("Através da TravelMate")) {
			camposVisto = "true";
		} else {
			camposVisto = "false";
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
									programasTeens.getDataInicioCurso()));
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
	
	public boolean habilitarTrocaCliente() {
		if(novaFicha) {
			return false;
		}else return true;
	}
	
	
	public void fecharNotificacao() {
		habilitarAvisoCambio = false;
	}
}
