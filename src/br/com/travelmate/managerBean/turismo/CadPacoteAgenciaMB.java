package br.com.travelmate.managerBean.turismo;

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

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.FinalizarPacoteOperadora;
import br.com.travelmate.bean.GerarPacotesFornecedorBean;
import br.com.travelmate.bean.ProductRunnersCalculosBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoPacotesBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PacoteSeguroFacade;
import br.com.travelmate.facade.PacoteTrechoFacade;
import br.com.travelmate.facade.PacotesFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.SeguroPlanosFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.ValorSeguroFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Pacotecarro;
import br.com.travelmate.model.Pacotecircuito;
import br.com.travelmate.model.Pacotecruzeiro;
import br.com.travelmate.model.Pacotehotel;
import br.com.travelmate.model.Pacotepassagem;
import br.com.travelmate.model.Pacotes;
import br.com.travelmate.model.Pacoteseguro;
import br.com.travelmate.model.Pacoteservico;
import br.com.travelmate.model.Pacotetransfer;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Pacotetrem;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Seguroplanos;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class CadPacoteAgenciaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	private Pacotes pacotes;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private List<Pacotetrecho> listaTrecho;
	private Pacotetrecho pacotetrecho;
	private Pacotetrecho pacotetrechoOficial;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	
	private boolean btniniciar = false;
	private boolean btnfinalizar = true;
	private Cliente cliente;
	private Parcelamentopagamento parcelamentopagamento;
	private List<Parcelamentopagamento> listaParcelamento;
	private float saldoParcelar = 0;
	private Vendas vendass;
	private Formapagamento formaPagamento;
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
	private float totalTarifa = 0;
	private float totalTaxas = 0;
	private float totalComissaoFornecedor = 0;
	private String idvenda;
	private Seguroviagem seguroviagem;
	private List<Pacoteseguro> listaSeguro;
	private Pacoteseguro pacoteseguro;
	private Fornecedorcidade fornecedorSeguro;
	private List<Fornecedorcidade> listaFornecedorSeguro;
	private List<Valoresseguro> listaValoresSeguro;
	private Valoresseguro valoresSeguro;
	private boolean novaFicha;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private float valorVendaAlterar = 0;
	private Lead lead;
	private float totalSeguro = 0;
	private Cambio cambio;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga;
	private List<Seguroplanos> listaSeguroPlanos;
	private Seguroplanos seguroplanos;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			pacotes = (Pacotes) session.getAttribute("pacote");
			cliente = (Cliente) session.getAttribute("cliente");
			lead = (Lead) session.getAttribute("lead");
			session.removeAttribute("cliente");
			session.removeAttribute("lead");
			session.removeAttribute("pacote");
			listaUnidadeNegocio = GerarListas.listarUnidade();
			seguroviagem = new Seguroviagem();
			carregarFornecedorSeguro();
			fornecedorSeguro = new Fornecedorcidade();
			valoresSeguro = new Valoresseguro();
			pacoteseguro = new Pacoteseguro();
			seguroplanos = new Seguroplanos();
			if (this.pacotes == null) {
				pacotes = new Pacotes();
				listaTrecho = new ArrayList<Pacotetrecho>();
				formaPagamento = new Formapagamento();
				if (cliente == null) {
					cliente = new Cliente();
				}
				pacotetrecho = new Pacotetrecho();
				vendass = new Vendas();
				vendass.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
				idvenda = "";
				listaSeguro = new ArrayList<Pacoteseguro>();
				novaFicha = true;
				CambioFacade cambioFacade = new CambioFacade();
				cambio =cambioFacade.consultar(1);
			} else {
				if (pacotes.getIdpacotes() != null) {
					cliente = pacotes.getCliente();
					listaTrecho = pacotes.getPacotetrechoList();
					vendass = pacotes.getVendas();
					valorVendaAlterar = vendass.getValor();
					if (vendass.getSituacao().equalsIgnoreCase("FINALIZADA")) {
						novaFicha = false;
					} else
						novaFicha = true;
					cambio = vendass.getCambio();
					if (formaPagamento == null) {
						formaPagamento = new Formapagamento();
					}
					FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
					this.formaPagamento = formaPagamentoFacade.consultar(pacotes.getVendas().getIdvendas()); 
					if(formaPagamento!=null){
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
					carregarListaSeguro();
					gerarListaConsultor();
					usuario = pacotes.getUsuario();
					PacoteTrechoFacade pacotesFacade = new PacoteTrechoFacade();
					pacotetrecho = pacotesFacade.consultarTrecho(pacotes.getIdpacotes());
					idvenda = "Nº " + pacotes.getVendas().getIdvendas();
					finalizar();
	
				} else {
					listaTrecho = pacotes.getPacotetrechoList();
					formaPagamento = new Formapagamento();
					cliente = pacotes.getCliente();
					PacoteTrechoFacade pacotesFacade = new PacoteTrechoFacade();
					String sql = "select p from Pacotetrecho p where p.pacotes.idpacotes=" + pacotes.getIdpacotes();
					listaTrecho = pacotesFacade.listar(sql);
				}
				if (pacotetrecho == null) {
					listaTrecho = new ArrayList<Pacotetrecho>();
				}
	
				for (int i = 0; i < listaTrecho.size(); i++) {
					carregarImagemCarro(listaTrecho.get(i));
					carregarImagemPassagem(listaTrecho.get(i));
					carregarImagemCruzeiro(listaTrecho.get(i));
					carregarImagemHotel(listaTrecho.get(i));
					carregarImagemTransfer(listaTrecho.get(i));
					carregarImagemTrem(listaTrecho.get(i));
					carregarImagemCircuito(listaTrecho.get(i));
					carregarImagemServico(listaTrecho.get(i));
				}
			}
		}
		if (cambio==null){
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambio =cambioFacade.consultar(1);
		}
	}

	public Pacotes getPacotes() {
		return pacotes;
	}

	public void setPacotes(Pacotes pacotes) {
		this.pacotes = pacotes;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public List<Pacotetrecho> getListaTrecho() {
		return listaTrecho;
	}

	public void setListaTrecho(List<Pacotetrecho> listaTrecho) {
		this.listaTrecho = listaTrecho;
	}

	public Pacotetrecho getPacotetrecho() {
		return pacotetrecho;
	}

	public void setPacotetrecho(Pacotetrecho pacotetrecho) {
		this.pacotetrecho = pacotetrecho;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(String idvenda) {
		this.idvenda = idvenda;
	}

	public boolean isBtniniciar() {
		return btniniciar;
	}

	public void setBtniniciar(boolean btniniciar) {
		this.btniniciar = btniniciar;
	}

	public boolean isBtnfinalizar() {
		return btnfinalizar;
	}

	public void setBtnfinalizar(boolean btnfinalizar) {
		this.btnfinalizar = btnfinalizar;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Parcelamentopagamento getParcelamentopagamento() {
		return parcelamentopagamento;
	}

	public void setParcelamentopagamento(Parcelamentopagamento parcelamentopagamento) {
		this.parcelamentopagamento = parcelamentopagamento;
	}

	public List<Parcelamentopagamento> getListaParcelamento() {
		return listaParcelamento;
	}

	public void setListaParcelamento(List<Parcelamentopagamento> listaParcelamento) {
		this.listaParcelamento = listaParcelamento;
	}

	public float getSaldoParcelar() {
		return saldoParcelar;
	}

	public void setSaldoParcelar(float saldoParcelar) {
		this.saldoParcelar = saldoParcelar;
	}

	public Vendas getVendas() {
		return vendass;
	}

	public void setVendas(Vendas vendass) {
		this.vendass = vendass;
	}

	public Vendas getVendass() {
		return vendass;
	}

	public void setVendass(Vendas vendass) {
		this.vendass = vendass;
	}

	public List<Pacoteseguro> getListaSeguro() {
		return listaSeguro;
	}

	public void setListaSeguro(List<Pacoteseguro> listaSeguro) {
		this.listaSeguro = listaSeguro;
	}

	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getFormaPagamentoString() {
		return formaPagamentoString;
	}

	public void setFormaPagamentoString(String formaPagamentoString) {
		this.formaPagamentoString = formaPagamentoString;
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

	public String getformaPagamentoString() {
		return formaPagamentoString;
	}

	public void setformaPagamentoString(String formaPagamentoString) {
		this.formaPagamentoString = formaPagamentoString;
	}

	public Pacotetrecho getPacotetrechoOficial() {
		return pacotetrechoOficial;
	}

	public void setPacotetrechoOficial(Pacotetrecho pacotetrechoOficial) {
		this.pacotetrechoOficial = pacotetrechoOficial;
	}

	public float getTotalTarifa() {
		return totalTarifa;
	}

	public void setTotalTarifa(float totalTarifa) {
		this.totalTarifa = totalTarifa;
	}

	public float getTotalTaxas() {
		return totalTaxas;
	}

	public void setTotalTaxas(float totalTaxas) {
		this.totalTaxas = totalTaxas;
	}

	public float getTotalComissaoFornecedor() {
		return totalComissaoFornecedor;
	}

	public void setTotalComissaoFornecedor(float totalComissaoFornecedor) {
		this.totalComissaoFornecedor = totalComissaoFornecedor;
	}

	public Seguroviagem getSeguroviagem() {
		return seguroviagem;
	}

	public void setSeguroviagem(Seguroviagem seguroviagem) {
		this.seguroviagem = seguroviagem;
	}

	public Pacoteseguro getPacoteseguro() {
		return pacoteseguro;
	}

	public void setPacoteseguro(Pacoteseguro pacoteseguro) {
		this.pacoteseguro = pacoteseguro;
	}

	public Fornecedorcidade getFornecedorSeguro() {
		return fornecedorSeguro;
	}

	public void setFornecedorSeguro(Fornecedorcidade fornecedorSeguro) {
		this.fornecedorSeguro = fornecedorSeguro;
	}

	public List<Fornecedorcidade> getListaFornecedorSeguro() {
		return listaFornecedorSeguro;
	}

	public void setListaFornecedorSeguro(List<Fornecedorcidade> listaFornecedorSeguro) {
		this.listaFornecedorSeguro = listaFornecedorSeguro;
	}

	public List<Valoresseguro> getListaValoresSeguro() {
		return listaValoresSeguro;
	}

	public void setListaValoresSeguro(List<Valoresseguro> listaValoresSeguro) {
		this.listaValoresSeguro = listaValoresSeguro;
	}

	public Valoresseguro getValoresSeguro() {
		return valoresSeguro;
	}

	public void setValoresSeguro(Valoresseguro valoresSeguro) {
		this.valoresSeguro = valoresSeguro;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
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

	public float getValorVendaAlterar() {
		return valorVendaAlterar;
	}

	public void setValorVendaAlterar(float valorVendaAlterar) {
		this.valorVendaAlterar = valorVendaAlterar;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public float getTotalSeguro() {
		return totalSeguro;
	}

	public void setTotalSeguro(float totalSeguro) {
		this.totalSeguro = totalSeguro;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoAntiga() {
		return listaParcelamentoPagamentoAntiga;
	}

	public void setListaParcelamentoPagamentoAntiga(List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga) {
		this.listaParcelamentoPagamentoAntiga = listaParcelamentoPagamentoAntiga;
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

	public String iniciarPacote() {
		Date dataCambio = cambio.getData();
		if (pacotes.getVendas() == null) {
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			Fornecedorcidade fornecedorcidade = fornecedorCidadeFacade
					.getFornecedorCidade(aplicacaoMB.getParametrosprodutos().getFornecedorpacote());
			ProdutoFacade produtoFacade = new ProdutoFacade();
			Produtos produto = produtoFacade.consultar(aplicacaoMB.getParametrosprodutos().getPacotes());
			vendass.setCliente(cliente);
			vendass.setDataVenda(new Date());
			vendass.setFornecedor(fornecedorcidade.getFornecedor());
			vendass.setFornecedorcidade(fornecedorcidade);
			vendass.setProdutos(produto);
			if (vendass.getIdvendas() == null) {
				vendass.setSituacao("Processo");
			}
			vendass.setUsuario(usuarioLogadoMB.getUsuario());
			vendass.setValor(0.0f);
			vendass.setVendasMatriz("S");
			vendass.setVendaimportada(0);
			vendass.setObsCancelar("");
			vendass.setCambio(cambio);
			vendass.setUsuariocancelamento(0);
			vendass.setObstm("");
			vendass.setValorcambio(0.0f);
			if (lead != null) {
				vendass.setIdlead(lead.getIdlead());
			} else
				vendass.setIdlead(0);
			
			vendass = vendasDao.salvar(vendass);
			pacotes.setVendas(vendass);
			pacotes.setCliente(cliente);
			formaPagamento.setVendas(vendass);
			FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
			formaPagamento = formaPagamentoFacade.salvar(formaPagamento);
		}
		pacotes.setOperacao("agencia");
		if (vendass.getIdvendas() == null) {
			pacotes.setControle("Processo");
		}
		pacotes.setCliente(cliente);
		pacotes.setUsuario(usuario);
		PacotesFacade pacotesFacade = new PacotesFacade();
		pacotes = pacotesFacade.salvar(pacotes);
		pacotetrecho = new Pacotetrecho();
		ProgramasBean programasBean = new ProgramasBean();
		programasBean.salvarCliente(pacotes.getCliente(), Formatacao.ConvercaoDataPadrao(pacotes.getDatainicio()),
				pacotes.getDatainicio(), pacotes.getDatetermino());
		if (listaParcelamentoPagamentoAntiga!=null && formaPagamento.getParcelamentopagamentoList() != null) {
			programasBean.salvarAlteracaoFinanceiro(vendass,listaParcelamentoPagamentoAntiga,
					formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB.getUsuario());
		}  
		return "";
	}

	public String adicionarTrecho() {
		if (pacotetrecho.getDescritivo() != null) {
			String descritivo = pacotetrecho.getDescritivo();
			pacotetrecho = new Pacotetrecho();
			pacotetrecho.setDescritivo(descritivo);
			if (!pacotetrecho.getDescritivo().equalsIgnoreCase("")) {
				pacotetrecho.setPacotes(pacotes);
				PacoteTrechoFacade pacoteTrechoFacade = new PacoteTrechoFacade();
				pacotetrecho = pacoteTrechoFacade.salvar(pacotetrecho);
				String sql = "Select p from Pacotetrecho p where p.pacotes.idpacotes=" + pacotes.getIdpacotes();
				listaTrecho = GerarListas.listarPacoteTrecho(sql);
				pacotetrecho = new Pacotetrecho();
				for (int i = 0; i < listaTrecho.size(); i++) {
					carregarImagemCarro(listaTrecho.get(i));
					carregarImagemPassagem(listaTrecho.get(i));
					carregarImagemCruzeiro(listaTrecho.get(i));
					carregarImagemHotel(listaTrecho.get(i));
					carregarImagemTransfer(listaTrecho.get(i));
					carregarImagemTrem(listaTrecho.get(i));
					carregarImagemCircuito(listaTrecho.get(i));
					carregarImagemServico(listaTrecho.get(i));
				}
			} else {
				FacesMessage mensagem = new FacesMessage("Atenção! ", "Adicione uma descrição.");
				FacesContext.getCurrentInstance().addMessage(null, mensagem);
			}
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Adicione uma descrição.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String finalizar() {  
		pacotes.setPacotetrechoList(listaTrecho);   
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.removeAttribute("pacoteTrecho");
		
		FinalizarPacoteOperadora finalizarPacoteOperadora = new FinalizarPacoteOperadora(listaTrecho);
		totalTarifa = finalizarPacoteOperadora.getTarifas();
		totalTaxas = finalizarPacoteOperadora.getTaxas();
		totalComissaoFornecedor = finalizarPacoteOperadora.getComissaoFornecedor();
		pacotes.setValornet(totalTarifa + totalTaxas - totalComissaoFornecedor);
		if (pacotes.getComissao() == null) {
			pacotes.setComissao(0.0f);
		}
		pacotes.setValorgross(totalTarifa + totalTaxas + pacotes.getComissao() + totalSeguro);
		if (pacotes.getComissaoloja() == null) {
			pacotes.setComissaoloja(0.0f);
		}
		mostrarValorTotal();
		PacotesFacade pacotesFacade = new PacotesFacade(); 
		pacotes = pacotesFacade.salvar(pacotes); 
		ProgramasBean programasBean = new ProgramasBean();
		OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
		Orcamento orcamento = orcamentoFacade.consultar(vendass.getIdvendas());
		programasBean.salvarOrcamento(orcamento, cambio, vendass.getValor(), 0.0f, 0.0f, vendass, "Não");
		adicionarValorSeguroTotal();
		calcularParcelamentoPagamento();
		return null;
	}

	public String novoCarro(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("pacotecarro", options, null);
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoCruzeiro(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			RequestContext.getCurrentInstance().openDialog("pacotecruzeiro");
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoHotel(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("pacotehotel", options, null);
			return "";
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoTrem(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			RequestContext.getCurrentInstance().openDialog("pacotetrem");
			return "";
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoTransfer(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			RequestContext.getCurrentInstance().openDialog("pacotetransfer");
			return "";
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoIngresso(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			RequestContext.getCurrentInstance().openDialog("pacoteingresso");
			return "";
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoPasseio(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			RequestContext.getCurrentInstance().openDialog("pacotepasseio");
			return "";
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoSeguro() {
		if (pacotes.getIdpacotes() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacote", pacotes);
			RequestContext.getCurrentInstance().openDialog("pacoteseguro");
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Pacote não iniciado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoPassagem(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			RequestContext.getCurrentInstance().openDialog("pacoteaereo");
			return "";
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoCircuito(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			RequestContext.getCurrentInstance().openDialog("pacoteCircuito");
			return "";
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public String novoServico(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacoteTrecho", pacotetrecho);
			RequestContext.getCurrentInstance().openDialog("pacoteServico");
			return "";
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "Trecho Não Localizado.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	// passagem
	public void retornoDialogNovoPassagem(SelectEvent event) {
		Pacotepassagem pacotepassagem = (Pacotepassagem) event.getObject();
		int id;
		if (pacotepassagem != null) {
			id = pacotepassagem.getPacotetrecho().getIdpacotetrecho();
		} else {
			id = 0;
		}
		for (int i = 0; i < listaTrecho.size(); i++) {
			if (id == listaTrecho.get(i).getIdpacotetrecho()) {
				listaTrecho.get(i).setImagempassagem("../../resources/img/aereoverde.png");
			} else {
				if (listaTrecho.get(i).getPacotepassagemList() != null
						&& listaTrecho.get(i).getPacotepassagemList().size() > 0) {
					listaTrecho.get(i).setImagempassagem("../../resources/img/aereoverde.png");
				} else {
					listaTrecho.get(i).setImagempassagem("../../resources/img/aereovermelho.png");
				}
			}
		}
	}

	public void carregarImagemPassagem(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			if (pacotetrecho.getPacotepassagemList() == null) {
				pacotetrecho.setImagempassagem("../../resources/img/aereovermelho.png");
			} else if (pacotetrecho.getPacotepassagemList().size() > 0) {
				pacotetrecho.setImagempassagem("../../resources/img/aereoverde.png");
			} else if (pacotetrecho.getPacotepassagemList().size() == 0) {
				pacotetrecho.setImagempassagem("../../resources/img/aereovermelho.png");
			}
		}
	}

	// carro
	public void carregarImagemCarro(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			if (pacotetrecho.getPacotecarroList() == null) {
				pacotetrecho.setImagemCarro("../../resources/img/carrovermelho.png");
			} else if (pacotetrecho.getPacotecarroList().size() > 0) {
				pacotetrecho.setImagemCarro("../../resources/img/carroverde.png");
			} else if (pacotetrecho.getPacotecarroList().size() == 0) {
				pacotetrecho.setImagemCarro("../../resources/img/carrovermelho.png");
			}
		}
	}

	public void retornoDialogNovoCarro(SelectEvent event) {
		Pacotecarro pacotecarro = (Pacotecarro) event.getObject();
		int id;
		if (pacotecarro != null) {
			id = pacotecarro.getPacotetrecho().getIdpacotetrecho();
		} else {
			id = 0;
		}
		for (int i = 0; i < listaTrecho.size(); i++) {
			if (id == listaTrecho.get(i).getIdpacotetrecho()) {
				listaTrecho.get(i).setImagemCarro("../../resources/img/carroverde.png");
			} else {
				if (listaTrecho.get(i).getPacotepassagemList() != null
						&& listaTrecho.get(i).getPacotepassagemList().size() > 0) {
					listaTrecho.get(i).setImagemCarro("../../resources/img/carroverde.png");
				} else {
					listaTrecho.get(i).setImagemCarro("../../resources/img/carrovermelho.png");
				}
			}
		}
	}

	// cruzeiro
	public void carregarImagemCruzeiro(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			if (pacotetrecho.getPacotecruzeiroList() == null) {
				pacotetrecho.setImagemcruzeiro("../../resources/img/cruzeirovermelho.png");
			} else if (pacotetrecho.getPacotecruzeiroList().size() > 0) {
				pacotetrecho.setImagemcruzeiro("../../resources/img/cruzeiroverde.png");
			} else if (pacotetrecho.getPacotecruzeiroList().size() == 0) {
				pacotetrecho.setImagemcruzeiro("../../resources/img/cruzeirovermelho.png");
			}
		}
	}

	public void retornoDialogNovoCruzeiro(SelectEvent event) {
		Pacotecruzeiro pacotecruzeiro = (Pacotecruzeiro) event.getObject();
		int id;
		if (pacotecruzeiro != null) {
			id = pacotecruzeiro.getPacotetrecho().getIdpacotetrecho();
		} else {
			id = 0;
		}
		for (int i = 0; i < listaTrecho.size(); i++) {
			if (id == listaTrecho.get(i).getIdpacotetrecho()) {
				listaTrecho.get(i).setImagemcruzeiro("../../resources/img/cruzeiroverde.png");
			} else {
				if (listaTrecho.get(i).getPacotepassagemList() != null
						&& listaTrecho.get(i).getPacotepassagemList().size() > 0) {
					listaTrecho.get(i).setImagemcruzeiro("../../resources/img/cruzeiroverde.png");
				} else {
					listaTrecho.get(i).setImagemcruzeiro("../../resources/img/cruzeirovermelho.png");
				}
			}
		}
	}

	// ingresso
	public String imagemIngresso(Pacotetrecho pacotetrecho) {
		boolean verdade = true;
		if (pacotetrecho.getPacoteingressoList() == null) {
			verdade = false;
		} else if (pacotetrecho.getPacoteingressoList().size() == 0) {
			verdade = false;
		}
		if (verdade) {
			return "../../resources/img/ingressoverde.png";
		} else {
			return "../../resources/img/ingressovermelho.png";
		}
	}

	public void retornoDialogNovo() {
		imagemPasseio(pacotetrecho);
	}

	public void retornoDialogNovoIngresso() {
		imagemIngresso(pacotetrecho);
	}

	// passeio
	public String imagemPasseio(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			boolean verdade = true;
			if (pacotetrecho.getPacotepasseioList() == null) {
				verdade = false;
			} else if (pacotetrecho.getPacotepasseioList().size() == 0) {
				verdade = false;
			}
			if (verdade) {
				return "../../resources/img/passeioverdeb.png";
			} else {
				return "../../resources/img/passeiovermelhob.png";
			}
		}
		return "";
	}

	// hotel
	public void carregarImagemHotel(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			if (pacotetrecho.getPacotehotelList() == null) {
				pacotetrecho.setImagemhotel("../../resources/img/hotelvermelho.png");
			} else if (pacotetrecho.getPacotehotelList().size() > 0) {
				pacotetrecho.setImagemhotel("../../resources/img/hotelverde.png");
			} else if (pacotetrecho.getPacotehotelList().size() == 0) {
				pacotetrecho.setImagemhotel("../../resources/img/hotelvermelho.png");
			}
		}
	}

	public void retornoDialogNovoHotel(SelectEvent event) {
		Pacotehotel pacotehotel = (Pacotehotel) event.getObject();
		int id;
		if (pacotehotel != null) {
			id = pacotehotel.getPacotetrecho().getIdpacotetrecho();
		} else {
			id = 0;
		}
		for (int i = 0; i < listaTrecho.size(); i++) {
			if (id == listaTrecho.get(i).getIdpacotetrecho()) {
				listaTrecho.get(i).setImagemhotel("../../resources/img/hotelverde.png");
			} else {
				if (listaTrecho.get(i).getPacotepassagemList() != null
						&& listaTrecho.get(i).getPacotepassagemList().size() > 0) {
					listaTrecho.get(i).setImagemhotel("../../resources/img/hotelverde.png");
				} else {
					listaTrecho.get(i).setImagemhotel("../../resources/img/hotelvermelho.png");
				}
			}
		}
	}

	// transfer
	public void carregarImagemTransfer(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			if (pacotetrecho.getPacotetransferList() == null) {
				pacotetrecho.setImagemtransfer("../../resources/img/transfervermelho.png");
			} else if (pacotetrecho.getPacotetransferList().size() > 0) {
				pacotetrecho.setImagemtransfer("../../resources/img/tranferverde.png");
			} else if (pacotetrecho.getPacotetransferList().size() == 0) {
				pacotetrecho.setImagemtransfer("../../resources/img/transfervermelho.png");
			}
		}
	}

	public void retornoDialogNovoTransfer(SelectEvent event) {
		Pacotetransfer pacotetransfer = (Pacotetransfer) event.getObject();
		int id;
		if (pacotetransfer != null) {
			id = pacotetransfer.getPacotetrecho().getIdpacotetrecho();
		} else {
			id = 0;
		}
		for (int i = 0; i < listaTrecho.size(); i++) {
			if (id == listaTrecho.get(i).getIdpacotetrecho()) {
				listaTrecho.get(i).setImagemtransfer("../../resources/img/tranferverde.png");
			} else {
				if (listaTrecho.get(i).getPacotepassagemList() != null
						&& listaTrecho.get(i).getPacotepassagemList().size() > 0) {
					listaTrecho.get(i).setImagemtransfer("../../resources/img/tranferverde.png");
				} else {
					listaTrecho.get(i).setImagemtransfer("../../resources/img/transfervermelho.png");
				}
			}
		}
	}

	// trem
	public void carregarImagemTrem(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			if (pacotetrecho.getPacotetremList() == null) {
				pacotetrecho.setImagemtrem("../../resources/img/tremvermelho.png");
			} else if (pacotetrecho.getPacotetremList().size() > 0) {
				pacotetrecho.setImagemtrem("../../resources/img/tremverde.png");
			} else if (pacotetrecho.getPacotetremList().size() == 0) {
				pacotetrecho.setImagemtrem("../../resources/img/tremvermelho.png");
			}
		}
	}

	public void retornoDialogNovoTrem(SelectEvent event) {
		Pacotetrem pacotetrem = (Pacotetrem) event.getObject();
		int id;
		if (pacotetrem != null) {
			id = pacotetrem.getPacotetrecho().getIdpacotetrecho();
		} else {
			id = 0;
		}
		for (int i = 0; i < listaTrecho.size(); i++) {
			if (id == listaTrecho.get(i).getIdpacotetrecho()) {
				listaTrecho.get(i).setImagemtrem("../../resources/img/tremverde.png");
			} else {
				if (listaTrecho.get(i).getPacotepassagemList() != null
						&& listaTrecho.get(i).getPacotepassagemList().size() > 0) {
					listaTrecho.get(i).setImagemtrem("../../resources/img/tremverde.png");
				} else {
					listaTrecho.get(i).setImagemtrem("../../resources/img/tremvermelho.png");
				}
			}
		}
	}

	// circuito
	public void carregarImagemCircuito(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			if (pacotetrecho.getPacotecircuitoList() == null) {
				pacotetrecho.setImagemcircuito("../../resources/img/circuitoNao.png");
			} else if (pacotetrecho.getPacotecircuitoList().size() > 0) {
				pacotetrecho.setImagemcircuito("../../resources/img/circuitoSim.png");
			} else if (pacotetrecho.getPacotecircuitoList().size() == 0) {
				pacotetrecho.setImagemcircuito("../../resources/img/circuitoNao.png");
			}
		}
	}

	public void retornoDialogNovoCircuito(SelectEvent event) {
		Pacotecircuito pacotecircuito = (Pacotecircuito) event.getObject();
		int id;
		if (pacotecircuito != null) {
			id = pacotecircuito.getPacotetrecho().getIdpacotetrecho();
		} else {
			id = 0;
		}
		for (int i = 0; i < listaTrecho.size(); i++) {
			if (id == listaTrecho.get(i).getIdpacotetrecho()) {
				listaTrecho.get(i).setImagemcircuito("../../resources/img/circuitoSim.png");
			} else {
				if (listaTrecho.get(i).getPacotecircuitoList() != null
						&& listaTrecho.get(i).getPacotecircuitoList().size() > 0) {
					listaTrecho.get(i).setImagemcircuito("../../resources/img/circuitoSim.png");
				} else {
					listaTrecho.get(i).setImagemcircuito("../../resources/img/circuitoNao.png");
				}
			}
		}
	}

	public void verificarBotoes() {
		if (btniniciar) {
			btniniciar = false;
			btnfinalizar = true;
		} else {
			btniniciar = true;
			btnfinalizar = false;
		}
	}
	
	public String validarDados() {
		String msg = "";
		if (valorParcelar > 0.0f) {
			msg = msg + "Saldo a parcelar em aberto \n";
		}
		
		if (valorParcelar <0.0f) {
			msg = msg + "Valor da forma de pagamento maior que o valor da venda";
		}
		
		return msg;
	}

	// salvar
	public String salvar() {
		if (pacotes.getVendas() == null) {
			FacesMessage mensagemAtencao = new FacesMessage("Atenção! ", "Pacote não Inicado/Finalizado");
			FacesContext.getCurrentInstance().addMessage(null, mensagemAtencao);
		} else {
				if (formaPagamento.getValorOrcamento() > 0) {
					if (formaPagamento.getParcelamentopagamentoList() != null && formaPagamento.getParcelamentopagamentoList().size() > 0) {
						if (listaTrecho != null && listaTrecho.size() > 0) {
							String msg = validarDados();
							if (msg == null || msg.length() == 0) {
								PacotesFacade pacotesFacade = new PacotesFacade();
								pacotes.setOperacao("agencia");
								pacotes.setControle("Concluido");
								pacotes.setCliente(cliente);
								pacotes.setUsuario(usuario);
								pacotes = pacotesFacade.salvar(pacotes);
								pacotetrecho.setPacotes(pacotes);
								String titulo = "";
								String operacao = "";
								String imagemNotificacao = "";
								if (novaFicha) {
									titulo = "Novo Pacote";
									operacao = "A";
									imagemNotificacao = "inserido";
								} else {
									titulo = "Pacote Alterado";
									operacao = "I";
									imagemNotificacao = "alterado";
								}
								vendass.setSituacao("FINALIZADA");
								vendass.setUnidadenegocio(pacotes.getUnidadenegocio());
								vendass.setVendasMatriz("S");
								vendass.setValor(valorJuros + formaPagamento.getValorOrcamento());
								vendass.setCliente(pacotes.getCliente());
								vendass.setUsuario(usuarioLogadoMB.getUsuario());
								
								if (vendass.getSituacaogerencia().equalsIgnoreCase("P")){
									vendass.setSituacaogerencia("F");
								}
								vendass = vendasDao.salvar(vendass);
								salvarSeguro();
								salvarFormaPagamento();
								try {
									float valorPrevisto = calcularComissao();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								if (novaFicha) {
									ContasReceberBean contasReceberBean = new ContasReceberBean(vendass,
											formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, false, pacotes.getDatainicio());
									GerarPacotesFornecedorBean gerarPacotesFornecedorBean = new GerarPacotesFornecedorBean(listaTrecho);
								} 
								FacesContext fc = FacesContext.getCurrentInstance();
								HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
								session.removeAttribute("pacote");
								String vm = "Venda pela Matriz";
								if (vendass.getVendasMatriz().equalsIgnoreCase("N")) {
									vm = "Venda pela Loja";
								}
								DepartamentoFacade departamentoFacade = new DepartamentoFacade();
								List<Departamento> departamento = departamentoFacade.listar("select d From Departamento d where d.usuario.idusuario="+vendass.getProdutos().getIdgerente());
								if(departamento!=null && departamento.size()>0){
									if (novaFicha) {
										Formatacao.gravarNotificacaoVendas(titulo, vendass.getUnidadenegocio(),
												cliente.getNome(), vendass.getFornecedorcidade().getFornecedor().getNome(),
												Formatacao.ConvercaoDataPadrao(pacotes.getDatainicio()), vendass.getUsuario().getNome(), vm,
												vendass.getValor(), 0.00f, "R$", operacao, departamento.get(0),
												imagemNotificacao, "I");
									}else{
										Formatacao.gravarNotificacaoVendas(titulo, vendass.getUnidadenegocio(),
												cliente.getNome(), vendass.getFornecedorcidade().getFornecedor().getNome(),
												Formatacao.ConvercaoDataPadrao(pacotes.getDatainicio()), vendass.getUsuario().getNome(), vm,
												vendass.getValor(), 0.00f, "R$", operacao, departamento.get(0),
												imagemNotificacao, "A");
									}
								}
								if (novaFicha) {
									
									DashBoardBean dashBoardBean = new DashBoardBean();
									dashBoardBean.calcularNumeroVendasProdutos(vendass, false);
									dashBoardBean.calcularMetaMensal(vendass, 0, false);
									dashBoardBean.calcularMetaAnual(vendass, 0, false);
									int[] pontos = dashBoardBean.calcularPontuacao(vendass, 0, "", false,pacotes.getUsuario());
									ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
									productRunnersCalculosBean.calcularPontuacao(vendass, pontos[0], 0, false, pacotes.getUsuario());
									vendass.setPonto(pontos[0]);
									vendass.setPontoescola(0);
									vendass = vendasDao.salvar(vendass);
									
								} else if (valorVendaAlterar != vendass.getValor()) {
									int mes = Formatacao.getMesData(new Date()) + 1;
									int mesVenda = Formatacao.getMesData(vendass.getDataVenda()) + 1;
									if (mes == mesVenda) {
										
										DashBoardBean dashBoardBean = new DashBoardBean();
										dashBoardBean.calcularMetaMensal(vendass, valorVendaAlterar, false);
										dashBoardBean.calcularMetaAnual(vendass, valorVendaAlterar, false);
										vendass = vendasDao.salvar(vendass);
										
									}
								}
								return "consultapacote";
							} else
								Mensagem.lancarMensagemErro(msg, "");
						}else Mensagem.lancarMensagemInfo("Adicione um Trecho", "");
					}else Mensagem.lancarMensagemInfo("Informe as Formas de Pagamento", "");
				} else
					Mensagem.lancarMensagemErro("Valor deve ser maior que zero!", "");
		}
		return "";
	}

	public void salvarFormaPagamento() {
		formaPagamento.setValorJuros(valorJuros);
		formaPagamento.setValorTotal(formaPagamento.getValorJuros() + formaPagamento.getValorOrcamento());
		formaPagamento.setVendas(this.pacotes.getVendas());
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		formaPagamento = formaPagamentoFacade.salvar(formaPagamento);
	}

	public String cancelar() {
		pacotetrecho = new Pacotetrecho();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.removeAttribute("pacote");
		return "consultapacote";
	}

	// Forma de pagamento
	public void calcularValorTotalOrcamento() {
		if (pacotes.getValorgross() != null) {
			valorTotal = 0.0f;
			valorTotal = pacotes.getValorgross();
			if (formaPagamento == null) {
				formaPagamento = new Formapagamento();
			}
			formaPagamento.setValorOrcamento(valorTotal);
			totalPagar = valorTotal;
			if (formaPagamento.getPossuiJuros().equalsIgnoreCase("sim")) {
				totalPagar = valorTotal + valorJuros;
			}
			valorSaltoParcelar = totalPagar;
			valorParcelar = valorSaltoParcelar;
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
			totalPagar = pacotes.getValorgross();
			valorSaltoParcelar = (totalPagar + valorJuros) - valorParcelado;
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
				if (vendass.getIdvendas() != null){
					if (!vendass.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamento = contasReceberBean.gerarParcelasIndividuais(parcelamento, formaPagamento.getParcelamentopagamentoList().size(), vendass, usuarioLogadoMB, pacotes.getDatainicio());
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
			}
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
		if (formaPagamento.getPossuiJuros().equalsIgnoreCase("sim")) {
			if (formaPagamento.getValorJuros() != null) {
				if (formaPagamento.getValorJuros() == 0) {
					msg = msg + "Informar valor do Juros";
				}
			}

		}
		if ((formaPagamentoString.equalsIgnoreCase("sn")) || (formaPagamentoString.length() <= 0)) {
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
		if(valorParcelar > (valorSaltoParcelar+0.1)){
			msg = msg + "Valor a parcelar mais alto que saldo em aberto.";
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
				ContasReceberBean contasReceberBean = new ContasReceberBean();
				if (vendass.getIdvendas()!=null){
					if (!vendass.getSituacao().equalsIgnoreCase("PROCESSO")) {
						contasReceberBean.apagarContasReceber(formaPagamento.getParcelamentopagamentoList().get(linha), vendass.getIdvendas(), usuarioLogadoMB,
								formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
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
			} else {
				formaPagamento.getParcelamentopagamentoList().remove(linha);
				calcularParcelamentoPagamento();
			}
		}
	}

	public void carregarCamposFormaPagamento() {
		if (formaPagamento.getValorJuros() != null) {
			valorJuros = formaPagamento.getValorJuros();
		}
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			calcularParcelamentoPagamento();
		}
	}

	// passar valor para valor total
	public void mostrarValorTotal() {
		formaPagamento.setValorOrcamento(pacotes.getValorgross());
		totalPagar = formaPagamento.getValorOrcamento();
		if (valorJuros > 0) {
			totalPagar = totalPagar + valorJuros;
		}
		valorSaltoParcelar = totalPagar;
		valorParcelar = valorSaltoParcelar;
	}

	public String pesquisarCliente() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("turismo", "turismo");
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultaCliente", options, null);
		return "";
	}

	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
	}

	public void carregarValorGross() {
		pacotes.setValorgross(totalTarifa + totalTaxas + pacotes.getComissao() + totalSeguro);
		calcularValorTotalOrcamento();
	}

	public float calcularComissao() throws SQLException {
		Vendascomissao vendasComissao = vendass.getVendascomissao();
		if (vendasComissao == null) {
			vendasComissao = new Vendascomissao();
			vendasComissao.setVendas(vendass);
			vendasComissao.setPaga("Não");
			vendasComissao.setUsuario(usuarioLogadoMB.getUsuario());
		}
		if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
			float valorJuros = 0.0f;
			if (vendass.getFormapagamento() != null && vendass.getFormapagamento().getValorJuros() != null) {
				valorJuros = vendass.getFormapagamento().getValorJuros();
			}
			vendasComissao.setUsuario(usuarioLogadoMB.getUsuario());
			ComissaoPacotesBean cc = new ComissaoPacotesBean(aplicacaoMB, vendass,
					formaPagamento.getParcelamentopagamentoList(), pacotes.getDatainicio(), vendasComissao, valorJuros,
					pacotes, totalTarifa, listaTrecho);
			return cc.getVendasComissao().getValorfornecedor();
		}
		return 0.0f;
	}

	public void carregarListaSeguro() {
		String sql = "select p from Pacoteseguro p where p.pacotes.idpacotes=" + pacotes.getIdpacotes();
		PacoteSeguroFacade pacoteSeguroFacade = new PacoteSeguroFacade();
		listaSeguro = pacoteSeguroFacade.consultar(sql);
		if (listaSeguro == null) {
			listaSeguro = new ArrayList<Pacoteseguro>();
		}
	}

	public void adicionarSeguro() {
		seguroviagem.setFornecedor(fornecedorSeguro.getFornecedor());
		seguroviagem.setVendas(vendass);
		seguroviagem.setValoresseguro(valoresSeguro);
		pacoteseguro.setSeguroviagem(seguroviagem);
		pacoteseguro.setPacotes(pacotes);
		pacoteseguro.setValornet(valoresSeguro.getValornet());
		pacoteseguro.setTotalvalornet(seguroviagem.getValorSeguro());
		listaSeguro.add(pacoteseguro);
		if (pacotes.getValornet() != null) {
			pacotes.setValornet(pacotes.getValornet() + pacoteseguro.getTotalvalornet());
			totalSeguro = totalSeguro + pacoteseguro.getTotalvalornet();
		} else {
			pacotes.setValornet(pacoteseguro.getTotalvalornet());
			totalSeguro = totalSeguro + pacoteseguro.getTotalvalornet();
		}
		carregarValorGross();
		pacoteseguro = new Pacoteseguro();
		fornecedorSeguro = new Fornecedorcidade();
		valoresSeguro = new Valoresseguro();
		seguroviagem = new Seguroviagem();
	}

	public void carregarFornecedorSeguro() {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = (int) aplicacaoMB.getParametrosprodutos().getCartao01();
		List<Paisproduto> listaPais = paisProdutoFacade.listar(idProduto);
		if (listaPais != null) {
			listaFornecedorSeguro = listaPais.get(0).getProdutos().getFornecedorcidadeList();
		}
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

	public void calcularNumeroDiasSeguro() {
		if ((seguroviagem.getDataInicio() != null) && (seguroviagem.getDataTermino() != null)) {
			int numeroDias = Formatacao.subtrairDatas(seguroviagem.getDataInicio(), seguroviagem.getDataTermino());
			if (numeroDias < 0) {
				numeroDias = numeroDias * -1;
			}
			seguroviagem.setNumeroSemanas(numeroDias + 1);
			calcularDataTermino();
		}
	}

	public void calcularDataTermino() {
		Date dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
		if ((seguroviagem.getDataInicio() != null) && (seguroviagem.getNumeroSemanas() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					valoresSeguro.getMoedas().getIdmoedas());
			if (cambioSeguro != null) {
				vendass.setCambio(cambioSeguro);
				seguroviagem.setDataTermino(Formatacao.calcularDataFinalPorDias(seguroviagem.getDataInicio(),
						seguroviagem.getNumeroSemanas()));
				float valornacional = valoresSeguro.getValornet() * cambioSeguro.getValor();
				valornacional = valornacional * pacoteseguro.getNumerosegurados();
				if (valoresSeguro.getCobranca().equalsIgnoreCase("Fixo")) {
					seguroviagem.setValorSeguro(valornacional);
				} else {
					seguroviagem.setValorSeguro(valornacional * seguroviagem.getNumeroSemanas());
				}
			}
		}

	}

	public void salvarSeguro() {
		for (int i = 0; i < listaSeguro.size(); i++) {
			SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
			listaSeguro.get(i).getSeguroviagem().setVendas(vendass);
			listaSeguro.get(i).getSeguroviagem().setControle("Pacote - "+vendass.getIdvendas());
			listaSeguro.get(i).getSeguroviagem().setPossuiSeguro("Sim");
			seguroViagemFacade.salvar(listaSeguro.get(i).getSeguroviagem());
			PacoteSeguroFacade pacoteSeguroFacade = new PacoteSeguroFacade();
			pacoteSeguroFacade.salvar(listaSeguro.get(i));
			formaPagamento
					.setValorOrcamento(formaPagamento.getValorOrcamento() + listaSeguro.get(i).getTotalvalornet());
		}
	}

	public void excluirSeguro(String ilinha) {
		int linha = Integer.parseInt(ilinha);
		if (linha >= 0) {
			if (listaSeguro.get(linha).getIdpacoteseguro() != null) {
				PacoteSeguroFacade pacoteSeguroFacade = new PacoteSeguroFacade();
				removerrValorSeguroTotal(listaSeguro.get(linha));
				pacoteSeguroFacade.excluir(listaSeguro.get(linha).getIdpacoteseguro());
				listaSeguro.remove(linha);
			} else {
				removerrValorSeguroTotal(listaSeguro.get(linha));
				listaSeguro.remove(linha);
			}
		}

	}

	public void adicionarValorSeguroTotal() {
		totalSeguro=0;
		for (int i = 0; i < listaSeguro.size(); i++) {
			if (pacotes.getValornet() != null) {
				pacotes.setValornet(pacotes.getValornet() + listaSeguro.get(i).getTotalvalornet());
				totalSeguro = totalSeguro + listaSeguro.get(i).getTotalvalornet();
			} else {
				pacotes.setValornet(listaSeguro.get(i).getTotalvalornet());
				totalSeguro = totalSeguro + listaSeguro.get(i).getTotalvalornet();
			}
		}
		carregarValorGross();
	}

	public void removerrValorSeguroTotal(Pacoteseguro pacoteseguro) { 
		carregarValorGross();
	}

	// servico
	public void carregarImagemServico(Pacotetrecho pacotetrecho) {
		if (pacotetrecho != null) {
			if (pacotetrecho.getPacoteservicoList() == null) {
				pacotetrecho.setImagemservico("../../resources/img/servicoSim.png");
			} else if (pacotetrecho.getPacoteservicoList().size() > 0) {
				pacotetrecho.setImagemservico("../../resources/img/servicoSim.png");
			} else if (pacotetrecho.getPacoteservicoList().size() == 0) {
				pacotetrecho.setImagemservico("../../resources/img/servicoNao.png");
			}
		}
	}

	public void retornoDialogNovoServico(SelectEvent event) {
		Pacoteservico pacoteservico = (Pacoteservico) event.getObject();
		int id;
		if (pacoteservico != null) {
			id = pacoteservico.getPacotetrecho().getIdpacotetrecho();
		} else {
			id = 0;
		}
		for (int i = 0; i < listaTrecho.size(); i++) {
			if (id == listaTrecho.get(i).getIdpacotetrecho()) {
				listaTrecho.get(i).setImagemservico("../../resources/img/servicoSim.png");
			} else {
				if (listaTrecho.get(i).getPacoteservicoList() != null
						&& listaTrecho.get(i).getPacoteservicoList().size() > 0) {
					listaTrecho.get(i).setImagemservico("../../resources/img/servicoSim.png");
				} else {
					listaTrecho.get(i).setImagemservico("../../resources/img/servicoNao.png");
				}
			}
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
		valorJuros = (float) session.getAttribute("valorJuros");
		session.removeAttribute("valorJuros");
		calcularValorTotalOrcamento();
	}

	public void gerarListaConsultor() {
		if (pacotes.getUnidadenegocio() != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaUsuario = usuarioFacade
					.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
							+ pacotes.getUnidadenegocio().getIdunidadeNegocio() + " order by u.nome");
			if (listaUsuario == null) {
				listaUsuario = new ArrayList<Usuario>();
			}
		}
	}

}
