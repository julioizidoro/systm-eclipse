package br.com.travelmate.managerBean.visto;

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
import br.com.travelmate.bean.ProductRunnersCalculosBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoVistoBean;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadPosVendaDao;
import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasComissaoFacade;

import br.com.travelmate.facade.VistosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.ProductRunnersMB;
import br.com.travelmate.managerBean.TmRaceMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Vistos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadVistosMB implements Serializable {

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
	
	private Cliente cliente;
	private Vistos vistos;
	private Vendas vendas;
	private Cambio cambio;
	private Pais pais;
	private List<Pais> listaPais;
	private float valorCambio;
	private float valorTotal = 0;
	private float valorJuros = 0;
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
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String habilitarUnidade = "false";
	private Float valorAlteradoVendas;
	private boolean novaFicha;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private Lead lead;
	private String voltarControleVendas = "";

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		vistos = (Vistos) session.getAttribute("vistos");
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("cliente");
		session.removeAttribute("lead");
		session.removeAttribute("vistos");
		String vendaMatriz;
		vendaMatriz = (String) session.getAttribute("vendaMatriz");
		session.removeAttribute("vendaMatriz");
		listaUnidadeNegocio = GerarListas.listarUnidade();
		if (vistos == null) {
			vistos = new Vistos();
			if (cliente == null) {
				cliente = new Cliente();
			}
			novaFicha = true;
			pais = new Pais();
			vendas = new Vendas();
			vendas.setVendasMatriz(vendaMatriz);
			vistos.setTaxaconsular(0.0f);
			vistos.setTaxaextra(0.0f);
			vistos.setTaxatm(0.0f);
			vistos.setDescontoloja(0.0f);
			vistos.setDescontomatriz(0.0f);
			vistos.setTaxaloja(0.0f);
			CambioFacade cambioFacade = new CambioFacade();
			cambio = cambioFacade.consultar(1);
			formaPagamento = new Formapagamento();
			unidadenegocio = new Unidadenegocio();
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
				listaUsuario = new ArrayList<Usuario>();
				listaUsuario.add(usuarioLogadoMB.getUsuario());
				usuario = usuarioLogadoMB.getUsuario();
				habilitarUnidade = "true";
			}
			valorAlteradoVendas = 0.0f;
			abrirtela();
			vendas.setVendasMatriz(vendaMatriz);
		} else {
			vendas = vistos.getVendas();
			novaFicha = false;
			valorAlteradoVendas = vendas.getValor();
			FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
			formaPagamento = formaPagamentoFacade.consultar(vendas.getIdvendas());
			calcularParcelamentoPagamento();
			cliente = vendas.getCliente();
			cambio = vendas.getCambio();
			PaisFacade paisFacade = new PaisFacade();
			pais = paisFacade.consultarNome(vistos.getPaisDestino());
			unidadenegocio = vendas.getUnidadenegocio();
			listaUsuario = new ArrayList<Usuario>();
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
				habilitarUnidade = "true";
				listaUsuario.add(usuarioLogadoMB.getUsuario());
				usuario = vistos.getUsuario();
			} else {
				gerarListaConsultor();
				usuario = vistos.getUsuario();
			}
		}

		carregarListaPais();

	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vistos getVistos() {
		return vistos;
	}

	public String getHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(String habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public void setVistos(Vistos vistos) {
		this.vistos = vistos;
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

	public float getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(float valorJuros) {
		this.valorJuros = valorJuros;
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

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	
	public Float getValorAlteradoVendas() {
		return valorAlteradoVendas;
	}

	public void setValorAlteradoVendas(Float valorAlteradoVendas) {
		this.valorAlteradoVendas = valorAlteradoVendas;
	}

	public boolean isNovaFicha() {
		return novaFicha;
	}

	public void setNovaFicha(boolean novaFicha) {
		this.novaFicha = novaFicha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
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

	public void carregarListaPais() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
	}

	public void adicionarFormaPagamento() {
		String msg = validarFormaPagamento();
		if (msg.length() < 5) {
			float saldoParcelar = this.valorParcelar;
			float valorParcela = this.valorParcela;
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
				if (!vendas.getSituacao().equalsIgnoreCase("PROCESSO") && vendas.getVendasMatriz().equalsIgnoreCase("S")) {
					ContasReceberBean contasReceberBean = new ContasReceberBean();
					parcelamento = contasReceberBean.gerarParcelasIndividuais(parcelamento,
							formaPagamento.getParcelamentopagamentoList().size(), vendas, usuarioLogadoMB, vistos.getDataembarque());
				}
			}
			if (parcelamento.getFormaPagamento().equalsIgnoreCase("Boleto")) {
				boolean horarioExcedido = false;
				int numeroAdicionar = 0;
				int diaSemana = Formatacao.diaSemana(parcelamento.getDiaVencimento());
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
						parcelamento.setDiaVencimento(Formatacao.SomarDiasDatas(parcelamento.getDiaVencimento(), numeroAdicionar));
						Mensagem.lancarMensagemInfo("Primeira parcela efetuada para o próximo dia útil", "");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			formaPagamento.getParcelamentopagamentoList().add(parcelamento);
			calcularParcelamentoPagamento();
			valorParcela = 0;
			saldoParcelar = 0;
			this.valorParcela = 0;
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
		}
	}

	public String validarFormaPagamento() {
		formaPagamento.setValorJuros(valorJuros);
		String msg = "";
		if (formaPagamento.getCondicao() == null) {
			msg = msg + "Campo forma de pagamento obrigatório";
		}

		if (formaPagamentoString.equalsIgnoreCase("sn")) {
			msg = msg + "Forma de pagamento não selecionada";
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
		if (valorParcela <= 0) {
			msg = msg + "Valor da parcela não pode ser 0";
		}
		if (valorParcelar > (valorSaltoParcelar + 0.01)) {
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
			valorSaltoParcelar = formaPagamento.getValorTotal() - valorParcelado;
			valorParcelar = valorSaltoParcelar;
		} else {
			if (formaPagamento.getValorJuros() != null) {
				valorSaltoParcelar = formaPagamento.getValorTotal() + formaPagamento.getValorJuros();
				valorParcelar = valorSaltoParcelar;
			} else {
				valorSaltoParcelar = formaPagamento.getValorTotal();
				valorParcelar = valorSaltoParcelar;
				valorParcela = valorParcelar;
				numeroParcelas = "01";
			}
		}
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
				if (!vendas.getSituacao().equalsIgnoreCase("PROCESSO") && vendas.getVendasMatriz().equalsIgnoreCase("S")) {
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

	public void calcularValorParcelas() {
		if (valorParcelar > 0) {
			int numero = Integer.parseInt(numeroParcelas);
			float vParcela = valorParcelar / numero;
			valorParcela = vParcela;
		}
	}

	public void calcularValorVisto() {
		vistos.setValorEmissao(vistos.getTaxaconsular() + vistos.getTaxaextra() + vistos.getTaxatm() + vistos.getTaxaloja());
		vistos.setValorEmissao(vistos.getValorEmissao() - vistos.getDescontoloja() - vistos.getDescontomatriz());
		formaPagamento.setValorOrcamento(vistos.getValorEmissao());
		if (formaPagamento.getPossuiJuros() != null) {
			if (formaPagamento.getPossuiJuros().equalsIgnoreCase("sim")) {
				if (formaPagamento.getValorJuros() != null) {
					formaPagamento.setValorTotal(formaPagamento.getValorJuros() + formaPagamento.getValorOrcamento());
				} else {
					formaPagamento.setValorTotal(formaPagamento.getValorOrcamento());
				}
			} else {
				formaPagamento.setValorTotal(formaPagamento.getValorOrcamento());
			}
		} else {
			formaPagamento.setValorTotal(formaPagamento.getValorOrcamento());
		}
		calcularParcelamentoPagamento();
	}

	public void calcularJuros() {
		if (formaPagamento.getPossuiJuros().equalsIgnoreCase("Sim")) {
			if (formaPagamento.getValorJuros() != null) {
				formaPagamento.setValorTotal(formaPagamento.getValorOrcamento() + formaPagamento.getValorJuros());
				if (formaPagamento.getParcelamentopagamentoList() != null) {
					valorSaltoParcelar = formaPagamento.getValorTotal();
					valorParcelar = valorSaltoParcelar;
					valorParcela = valorParcelar;
					numeroParcelas = "01";
					for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
						valorSaltoParcelar = formaPagamento.getValorTotal()
								- formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento();
						valorParcelar = valorSaltoParcelar;
						valorParcela = valorParcelar;
						numeroParcelas = "01";
					}
				} else {
					valorSaltoParcelar = formaPagamento.getValorTotal();
					valorParcelar = valorSaltoParcelar;
					valorParcela = valorParcelar;
					numeroParcelas = "01";
				}
			}
		}
	}

	public String cancelar() {
		if (voltarControleVendas != null) {
			if (voltarControleVendas.length() > 1) {
				return voltarControleVendas;
			}
		}
		return "consVistos";
	}

	public String salvar() throws SQLException {
		String titulo = "";
		String operacao = "";
		String imagemNotificacao = "";
		if (valorParcelar <=0) {
			if (vendas.getIdvendas() == null) {
				novaFicha = true;
				titulo = "Nova Ficha de Visto";
				operacao = "A";
				imagemNotificacao = "inserido";
				if (vendas.getVendasMatriz().equalsIgnoreCase("S")) {
					imagemNotificacao = "inseridoMatriz";
				} else if (vendas.getVendasMatriz().equalsIgnoreCase("N")) {
					imagemNotificacao = "inseridoNaoMatrizS";
				} else {
					imagemNotificacao = "inseridoMoema";
				}
			} else {
				titulo = "Ficha de Visto Alterada";
				operacao = "I";
				imagemNotificacao = "alterado";
			}
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			Fornecedorcidade fornecedorcidade = fornecedorCidadeFacade
					.getFornecedorCidade(aplicacaoMB.getParametrosprodutos().getFornecedorpacote());
			ProgramasBean programasBean = new ProgramasBean();
			Produtos produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getVisto());
			vendas.setUnidadenegocio(unidadenegocio);
			vendas.setUsuario(usuarioLogadoMB.getUsuario());
			vendas.setValorcambio(0.0f);
			vendas = programasBean.salvarVendas(vendas, usuarioLogadoMB, "FINALIZADA", cliente,
					formaPagamento.getValorTotal(), produto, fornecedorcidade, cambio, cambio.getValor(), lead, null, null, vendasDao, leadPosVendaDao, leadDao, leadSituacaoDao);
			vistos.setVendas(vendas);
			vistos.setControle("Processo");
			vistos.setPaisDestino(pais.getNome());
			vistos.setUsuario(usuario);
			VistosFacade vistosFacade = new VistosFacade();
			vistos = vistosFacade.salvar(vistos);
			formaPagamento = programasBean.salvarFormaPagamento(formaPagamento, vendas);
			if (novaFicha && vendas.getVendasMatriz().equalsIgnoreCase("S")) {
				ContasReceberBean contasReceberBean = new ContasReceberBean(vendas,
						formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, false, vistos.getDataembarque());
			}
			String vm = "Venda pela Loja";
			if (vendas.getVendasMatriz().equalsIgnoreCase("S")) {
				calcularComissao();
				vm = "Venda pela Matriz";
			} else if (vendas.getVendasMatriz().equalsIgnoreCase("M")) {
				vm = "Venda por Moema";
			}
			OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
			Orcamento orcamento = orcamentoFacade.consultar(vendas.getIdvendas());
			programasBean.salvarOrcamento(orcamento, cambio, vendas.getValor(), 0.0f, valorCambio, vendas, "Não");
			DepartamentoFacade departamentoFacade = new DepartamentoFacade();
			List<Departamento> departamento = departamentoFacade.listar(
					"select d From Departamento d where d.usuario.idusuario=" + vendas.getProdutos().getIdgerente());
			if (departamento != null && departamento.size() > 0) {
				if (novaFicha) {
					Formatacao.gravarNotificacaoVendas(titulo, vendas.getUnidadenegocio(), cliente.getNome(), "Visto",
							Formatacao.ConvercaoDataPadrao(vistos.getDataembarque()), vendas.getUsuario().getNome(), vm,
							vendas.getValor(), valorCambio, vendas.getCambio().getMoedas().getSigla(), operacao,
							departamento.get(0), imagemNotificacao, "I");
				} else {
					Formatacao.gravarNotificacaoVendas(titulo, vendas.getUnidadenegocio(), cliente.getNome(), "Visto",
							Formatacao.ConvercaoDataPadrao(vistos.getDataembarque()), vendas.getUsuario().getNome(), vm,
							vendas.getValor(), valorCambio, vendas.getCambio().getMoedas().getSigla(), operacao,
							departamento.get(0), imagemNotificacao, "A");
				}
			}
			if (novaFicha) {
				if (valorAlteradoVendas == 0) {
					if (vendas.getVendasMatriz().equalsIgnoreCase("S")) {
						
						DashBoardBean dashBoardBean = new DashBoardBean();
						dashBoardBean.calcularNumeroVendasProdutos(vendas, false);
						dashBoardBean.calcularMetaMensal(vendas, valorAlteradoVendas, false);
						dashBoardBean.calcularMetaAnual(vendas, valorAlteradoVendas, false);
						int[] pontos = dashBoardBean.calcularPontuacao(vistos.getUsuario(), vendas, 0, "", false);
						ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
						productRunnersCalculosBean.calcularPontuacao(vendas, pontos[0], 0, false, vendas.getUsuario());
						vendas.setPonto(pontos[0]);
						vendas.setPontoescola(pontos[1]);
						
						vendas = vendasDao.salvar(vendas);
						
					} else if (vendas.getVendasMatriz().equalsIgnoreCase("M")) {
						DashBoardBean dashBoardBean = new DashBoardBean();
						int[] pontos = dashBoardBean.calcularPontuacao(vistos.getUsuario(), vendas, 0, "", false);
						ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
						productRunnersCalculosBean.calcularPontuacao(vendas, pontos[0], 0, false, vendas.getUsuario());
						vendas.setPonto(pontos[0]);
						vendas.setPontoescola(pontos[1]);
						
						vendas = vendasDao.salvar(vendas);
						
					}
				}
			} else if (valorAlteradoVendas != vendas.getValor()) {
				int mes = Formatacao.getMesData(new Date()) + 1;
				int mesVenda = Formatacao.getMesData(vendas.getDataVenda()) + 1;
				if (mes == mesVenda) {
					if (vendas.getVendasMatriz().equalsIgnoreCase("S")) {
						
	
						DashBoardBean dashBoardBean = new DashBoardBean();
						dashBoardBean.calcularMetaMensal(vendas, valorAlteradoVendas, false);
						dashBoardBean.calcularMetaAnual(vendas, valorAlteradoVendas, false);
						int[] pontos = dashBoardBean.calcularPontuacao(vistos.getUsuario(), vendas, 0, "", false);
						int pontosremover = vendas.getPonto();
						ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
						productRunnersCalculosBean.calcularPontuacao(vendas, pontos[0], pontosremover, false, vendas.getUsuario());
						vendas.setPonto(pontos[0]);
						vendas.setPontoescola(pontos[1]);
						
						vendas = vendasDao.salvar(vendas);
						
					}
				}
			}
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consVistos";
		}else {
			Mensagem.lancarMensagemInfo("Saldo em Aberto na Forma de pagamento", "");
		}
		return "";
	}

	public String abrirtela() {
		return "cadVistos";
	}

	public Float calcularComissao() throws SQLException {
		VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
		Vendascomissao vendasComissao = vendasComissaoFacade.getVendaComissao(vendas.getIdvendas(),
				vendas.getProdutos().getIdprodutos());
		if (vendasComissao == null) {
			vendasComissao = new Vendascomissao();
			vendasComissao.setVendas(vendas);
			vendasComissao.setPaga("Não");
		}
		if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
			float valorJuros = 0.0f;
			if (vendas.getFormapagamento() != null) {
				if (vendas.getFormapagamento().getValorJuros() == null) {
					vendas.getFormapagamento().setValorJuros(0.0f);
				}
				valorJuros = vendas.getFormapagamento().getValorJuros();
			}
			ComissaoVistoBean cc = new ComissaoVistoBean(vendas, vendasComissao, aplicacaoMB, valorJuros,
					formaPagamento.getParcelamentopagamentoList(), vistos);
			return cc.getVendasComissao().getValorfornecedor();
		}
		return 0.0f;
	}

	public String dialogCalcularJuros() {
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
		calcularJuros();
	}

	public void gerarListaConsultor() {
		if (unidadenegocio != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaUsuario = usuarioFacade
					.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
			if (listaUsuario == null) {
				listaUsuario = new ArrayList<Usuario>();
			}
		}
	}

}
