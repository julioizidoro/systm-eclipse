package br.com.travelmate.managerBean.aupair;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
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
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Controleaupair;
import br.com.travelmate.model.Credito;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class AuPairMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Aupair> listaAupair;
	private String numeroFichas;
	private String obsTM;
	private String nome;
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private boolean habilitarUnidade = true;
	private String motivoCancelamento;
	private Aupair aupair;
	private int idVenda;
	private String linkIdVenda;
	private String linkIdVendaFranquias;
	private String voltar;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;
	private Integer nFichasFinanceiro;
	private List<Aupair> listaVendasFinalizada;
	private List<Aupair> listaVendasAndamento;
	private List<Aupair> listaVendasCancelada;
	private List<Aupair> listaVendasProcesso;
	private List<Aupair> listaVendasFinanceiro;
	private String chamadaTela = "";

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaAupair = (List<Aupair>) session.getAttribute("listaAupair");
		session.removeAttribute("chamadaTela");
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if ((chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu")) || listaAupair == null || listaAupair.size() == 0) {
				carregarListaVendas();
			}else {
				gerarQuantidadesFichas();
			}
			listaUnidadeNegocio = GerarListas.listarUnidade();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
				linkIdVenda = "true";
				linkIdVendaFranquias = "false";
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
				linkIdVenda = "false";
				linkIdVendaFranquias = "true";
			}
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Aupair> getListaAupair() {
		return listaAupair;
	}

	public void setListaAupair(List<Aupair> listaAupair) {
		this.listaAupair = listaAupair;
	}

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Aupair getAupair() {
		return aupair;
	}

	public void setAupair(Aupair aupair) {
		this.aupair = aupair;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public String getLinkIdVenda() {
		return linkIdVenda;
	}

	public void setLinkIdVenda(String linkIdVenda) {
		this.linkIdVenda = linkIdVenda;
	}

	public String getLinkIdVendaFranquias() {
		return linkIdVendaFranquias;
	}

	public void setLinkIdVendaFranquias(String linkIdVendaFranquias) {
		this.linkIdVendaFranquias = linkIdVendaFranquias;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public Integer getnFichasFinalizadas() {
		return nFichasFinalizadas;
	}

	public void setnFichasFinalizadas(Integer nFichasFinalizadas) {
		this.nFichasFinalizadas = nFichasFinalizadas;
	}

	public Integer getnFichasProcesso() {
		return nFichasProcesso;
	}

	public void setnFichasProcesso(Integer nFichasProcesso) {
		this.nFichasProcesso = nFichasProcesso;
	}

	public Integer getnFichasAndamento() {
		return nFichasAndamento;
	}

	public void setnFichasAndamento(Integer nFichasAndamento) {
		this.nFichasAndamento = nFichasAndamento;
	}

	public Integer getnFichaCancelada() {
		return nFichaCancelada;
	}

	public void setnFichaCancelada(Integer nFichaCancelada) {
		this.nFichaCancelada = nFichaCancelada;
	}

	public boolean isExpandirOpcoes() {
		return expandirOpcoes;
	}

	public void setExpandirOpcoes(boolean expandirOpcoes) {
		this.expandirOpcoes = expandirOpcoes;
	}

	public boolean isEsconderFicha() {
		return esconderFicha;
	}

	public void setEsconderFicha(boolean esconderFicha) {
		this.esconderFicha = esconderFicha;
	}

	public Integer getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}

	public void setnFichasFinanceiro(Integer nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	}

	public List<Aupair> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Aupair> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Aupair> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Aupair> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Aupair> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Aupair> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Aupair> getListaVendasProcesso() {
		return listaVendasProcesso;
	}

	public void setListaVendasProcesso(List<Aupair> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}

	public List<Aupair> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Aupair> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}

	public String cadastrarFicha() {
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead = 0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("idlead", idlead);
			return "cadAuPair";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}

	public boolean imagemSituacaoUsuario(Aupair aupair) {
		if (aupair.getVendas().getSituacao().equals("FINALIZADA")) {
			aupair.setHabilitarImagemGerencial(false);
			aupair.setHabilitarImagemFranquia(true);
			aupair.setImagem("../../resources/img/finalizadoFicha.png");
			aupair.setTituloFicha("FICHA FINALIZADA");
		}else if (aupair.getVendas().getSituacao().equals("ANDAMENTO")
				&& !aupair.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
				aupair.setHabilitarImagemGerencial(true);
				aupair.setHabilitarImagemFranquia(false);
			} else {
				aupair.setHabilitarImagemGerencial(false);
				aupair.setHabilitarImagemFranquia(true);
			}
			aupair.setImagem("../../resources/img/ficharestricao.png");
			if (aupair.getVendas().getSituacaofinanceiro().equalsIgnoreCase("P")) {
				aupair.setTituloFicha("FINANCEIRO - PENDENTE (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}else {
				aupair.setTituloFicha("FINANCEIRO - AGUARDANDO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}
		}  else if (aupair.getVendas().getSituacao().equals("CANCELADA")) {
			aupair.setHabilitarImagemGerencial(false);
			aupair.setHabilitarImagemFranquia(true);
			aupair.setImagem("../../resources/img/fichaCancelada.png");
			aupair.setTituloFicha("FICHA CANCELADA");
		}  else {
			aupair.setHabilitarImagemGerencial(false);
			aupair.setHabilitarImagemFranquia(true);
			aupair.setImagem("../../resources/img/processoFicha.png");
			aupair.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
		}
		return aupair.isHabilitarImagemGerencial();
	}

	public String obsTMselecionar(Aupair aupair) {
		obsTM = aupair.getVendas().getObstm();
		return obsTM;
	}

	public String gerarRelatorioFicha(Aupair aupair) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/aupair/FichaAupairPagina01.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idvendas", aupair.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//aupair//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"fichaAupair-" + aupair.getIdaupair() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(AuPairMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(AuPairMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioContrato(Aupair aupair) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (!aupair.getPaisinteresse().equalsIgnoreCase("Caregiver")) {
			caminhoRelatorio = "/reports/aupair/contratoAupairPagina01.jasper";
		} else {
			caminhoRelatorio = "/reports/aupair/contratoCaregiverPagina01.jasper";
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idvendas", aupair.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//aupair//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"contratoAupair-" + aupair.getVendas().getIdvendas() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(AuPairMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(AuPairMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public void carregarListaVendas() {
		String sql = null;
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
		sql = "Select a from Aupair a where ";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " a.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and a.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
			sql=sql+ " and";
		}
		sql = sql + " a.vendas.dataVenda>='" + dataConsulta + "' order by a.vendas.dataVenda desc";
		AupairFacade aupairFacade = new AupairFacade();
		listaAupair = aupairFacade.lista(sql);
		if (listaAupair == null) {
			listaAupair = new ArrayList<Aupair>();
		}
		numeroFichas = "" + String.valueOf(listaAupair.size());
		gerarQuantidadesFichas();
	}

	public void limparPesquisa() {
		unidadenegocio = null;
		dataInicio = null;
		dataTermino = null;
		situacao = "TODAS";
		nome = "";
		idVenda = 0;
		carregarListaVendas();
	}

	public void pesquisarLista() {
		String sql = null;
		sql = "Select a from Aupair a where a.vendas.cliente.nome like '%" + nome + "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null) {
				sql = sql + " and a.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and a.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and a.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and a.vendas.idvendas=" + idVenda;
		}

		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and a.dataInscricao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and a.dataInscricao<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		}
		if (!situacao.equalsIgnoreCase("TODAS")) {
			sql = sql + " and a.vendas.situacao='" + situacao + "'";
		}
		sql = sql + " order by a.vendas.dataVenda, a.vendas.cliente.nome";
		AupairFacade aupairFacade = new AupairFacade();
		listaAupair = aupairFacade.lista(sql);
		if (listaAupair == null) {
			listaAupair = new ArrayList<Aupair>();
		}
		numeroFichas = "" + String.valueOf(listaAupair.size());
		gerarQuantidadesFichas();
	}

	public String gerarRelatorioRecibo(Aupair aupair) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(aupair.getVendas().getIdvendas());
		ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
		List<Parcelamentopagamento> listaParcelamento = parcelamentoPagamentoFacade.listar(forma.getIdformaPagamento());
		if (listaParcelamento != null) {
			for (int i = 0; i < listaParcelamento.size(); i++) {
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Dinheiro")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cheque")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Depósito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
			}
		}
		if (valorRecibo > 0.0f) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idvendas", aupair.getVendas().getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla());
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
		    String moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
		    parameters.put("moedaNacional", moedaNacional);
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"reciboPagamento-" + aupair.getVendas().getIdvendas() + ".pdf", null);
			} catch (JRException ex1) {
				Logger.getLogger(AuPairMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(AuPairMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
		return "";
	}

	public String gerarRelatorioTermoVisto(Aupair aupair) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idcliente", aupair.getVendas().getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoVisto.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(AuPairMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(AuPairMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String editar(Aupair aupair) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("aupair", aupair);
		session.setAttribute("idlead", aupair.getVendas().getIdlead());
		return "cadAuPair";
	}

	public void cancelar(Aupair aupair) {
		this.aupair = aupair;
	}

//	public String cancelarVenda(Vendas venda) {
//		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
//			Map<String, Object> options = new HashMap<String, Object>();
//			options.put("contentWidth", 400);
//			FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("venda", venda);
//			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
//		} else if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
//			
//			venda.setSituacao("CANCELADA");
//			vendasDao.salvar(venda);
//			carregarListaVendas();
//		}
//		return "";
//	}

	public void salvarControle() throws SQLException {
		Controleaupair controleaupair = new Controleaupair();
		AupairFacade cursoFacade = new AupairFacade();
		controleaupair = cursoFacade.consultarControle(aupair.getVendas().getIdvendas());
		if (controleaupair == null) {
			ControlerBean controlerBean = new ControlerBean();
			float valorPrevisto = 0.0f;
			if (aupair.getVendas().getVendascomissao() != null) {
				valorPrevisto = aupair.getVendas().getVendascomissao().getValorfornecedor();
			}
			controlerBean.salvarControleAupair(aupair.getVendas(), aupair, valorPrevisto);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Controle já existente.");
		}
	}

	public void dialogSalvarControle(Aupair aupair) {
		this.aupair = aupair;
	}

	public String documentacao(Aupair aupair) {
		boolean validar = true;
		if (aupair.getVendas().getSituacao().equalsIgnoreCase("PROCESSO") && usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1) {
			String dataStringValidade = Formatacao.ConvercaoDataPadrao(new Date());
			Date dataAtual = Formatacao.ConvercaoStringData(dataStringValidade);
			Date dataValidade = aupair.getVendas().getDatavalidade();
			if (dataValidade != null) {
				if (!dataAtual.after(dataValidade)) {
					validar = true;
				} else {
					validar = false;
				}
			}
		}
		if (!validar) {
			Mensagem.lancarMensagemInfo("Favor atualizar o câmbio desta ficha",
					"está ficha ultrapassou os 3 dias de validade");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", aupair.getVendas());
			voltar = "consultaAuPair";
			session.setAttribute("voltar", voltar);
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1) {
				return "consArquivos";
			}else {
				return "consControleArquivos";
			}
		}
	}

	public String corNome(Aupair aupair) {
		if (aupair.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Aupair aupair) {
		if (aupair.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public String botoesHabilitados(Aupair aupair) {
		if (aupair.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	}

	public String visualizarContasReceber(Vendas venda) {
		if ((venda.getOrcamento() != null)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", venda);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 620);
			RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		} else {
			FacesMessage msg = new FacesMessage("Venda não Possui Contas a Receber! ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "";
	}

	public void liberarFicha() {
		if ((aupair.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (aupair.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				Vendas venda = aupair.getVendas();
				venda.setRestricaoparcelamento(false);
				
				venda = vendasDao.salvar(venda);
				aupair.setVendas(venda);
				Formapagamento forma = aupair.getVendas().getFormapagamento();
				if (forma != null) {
					if (forma.getParcelamentopagamentoList() != null) {
						ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
						for (int i = 0; i < forma.getParcelamentopagamentoList().size(); i++) {
							forma.getParcelamentopagamentoList().get(i).setVerificarParcelamento(false);
							forma.getParcelamentopagamentoList().set(i,
									parcelamentoPagamentoFacade.salvar(forma.getParcelamentopagamentoList().get(i)));
						}
					}
				}
			}
		}
	}

	public String boletos(Aupair aupair) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(aupair.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + aupair.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(aupair.getVendas().getIdvendas()), true);
				} else {
					FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
					relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
				}
			} else {
				FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
				relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
			}
		} else {
			FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Dados do cliente não converefe " + validarCliente.getMsg());
		}

		return "";
	}

	public String informacoes(Aupair aupair) {
		if (aupair.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", aupair.getVendas());
			voltar = "consultaAuPair";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}

	public void gerarQuantidadesFichas() {
		if (listaAupair != null) {
			numeroFichas = "" + String.valueOf(listaAupair.size());
		}
		nFichaCancelada = 0;
		nFichasAndamento = 0;
		nFichasFinalizadas = 0;
		nFichasProcesso = 0;
		nFichasFinanceiro = 0;
		listaVendasFinalizada = new ArrayList<>();
		listaVendasAndamento = new ArrayList<>();
		listaVendasCancelada = new ArrayList<>();
		listaVendasProcesso = new ArrayList<>();
		listaVendasFinanceiro = new ArrayList<>();
		for (int i = 0; i < listaAupair.size(); i++) {
			if (listaAupair.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaAupair.get(i));
			} else if (listaAupair.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaAupair.get(i));
			}else if(listaAupair.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO") 
					&& !listaAupair.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaAupair.get(i));
			} else if (listaAupair.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaAupair.get(i));
			} else {
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaAupair.get(i));
			}
		}
	}
	
	public void expandirOpcoes(){
		if(expandirOpcoes){
			expandirOpcoes=false;
			esconderFicha=true;
		}else {
			expandirOpcoes=true;
			esconderFicha=false;
		}
	}

	public String retornarImgOpcoes() {
		if (expandirOpcoes) {
			return "../../resources/img/esconderOpcoes.png";
		} else
			return "../../resources/img/expandirOpcoes.png";
	}

	public String retornarTitleOpcoes() {
		if (expandirOpcoes) {
			return "Esconder Opções";
		} else
			return "Expandir Opções";
	}
	
	
	public String retornarIconeObsTM(Aupair aupair){
		if (aupair.getObstm() != null && aupair.getObstm().length() > 1) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}
	
	public String buscarObsTM(Aupair aupair) {
		obsTM = aupair.getObstm();
		return obsTM;
	}
	  
	
	public void buscarAupair(Aupair aupair) {
		this.aupair = aupair;
	}
	
	
	public String visualizarContasReceber(Aupair aupair) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", aupair.getVendas());
		Map<String, Object> options = new HashMap<String, Object>(); 
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
	
	
	public String notificarEfetuarFichaCrm(){
		return "followUp";
	}
	
	
	public String cancelarVenda(Vendas vendas) {
		if (vendas.getSituacao().equalsIgnoreCase("FINALIZADA")
				|| vendas.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", vendas);
			session.setAttribute("voltar", "consultaAuPair");
			return "emissaocancelamento";
		} else if (vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			vendas.setSituacao("CANCELADA");
			vendasDao.salvar(vendas);
			carregarListaVendas();
		}
		return "";
	}    
	
	
	
	public String fichaAupair(Aupair aupair){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("aupair", aupair);
		session.setAttribute("listaAupair", listaAupair);
		return "fichaAuPair";
	}
	
	
	public void verificarIdCredito(Aupair aupair) {
		if (aupair.getVendas().getCancelamento() != null) {
			if (aupair.getVendas().getCancelamento().getCancelamentocredito() != null) {
				if (aupair.getVendas().getCancelamento().getCancelamentocredito().getCredito().getTipocredito().equalsIgnoreCase("Crédito")) {
					Credito credito = aupair.getVendas().getCancelamento().getCancelamentocredito().getCredito();
					FacesContext fc = FacesContext.getCurrentInstance();
					HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
					session.setAttribute("credito", credito);
					Map<String, Object> options = new HashMap<String, Object>();
					options.put("contentWidth", 150);
					RequestContext.getCurrentInstance().openDialog("visualizarIdCredito", options, null);
				}else {
					Mensagem.lancarMensagemFatal("Não há crédito para está venda", "");
				}
			}else {
				Mensagem.lancarMensagemFatal("Não há crédito para está venda", "");
			}
		}else {
			Mensagem.lancarMensagemFatal("Não há crédito para está venda", "");
		}
	}
	
	public String relatorioTermoQuitacao(Aupair aupair) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		Cancelamento cancelamento = cancelamentoFacade.consultar(aupair.getVendas().getIdvendas());
		session.setAttribute("cancelamento", cancelamento);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("reciboTermoQuitacao", options, null);
		return "";
	}
	
	
	public String contratoAuPair(Aupair aupair){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("aupair", aupair);
		session.setAttribute("listaAupair", listaAupair);
		return "contratoAuPair";
	}
	

	
//	public String documentacao(Aupair aupair) {
//		FacesContext fc = FacesContext.getCurrentInstance();
//		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//		session.setAttribute("vendas", aupair.getVendas());
//		voltar = "consultaAuPair";
//		session.setAttribute("voltar", voltar);
//		return "consArquivo";
//	}
	
	
	
	public void dadosCancelamento(Aupair aupair) {
		if (aupair.getVendas().getSituacao().equalsIgnoreCase("CANCELADA") && aupair.getVendas().getCancelamento() != null) {
			Cancelamento cancelamento = aupair.getVendas().getCancelamento();
			if (cancelamento != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("cancelamento", cancelamento);
				Map<String, Object> options = new HashMap<String, Object>();
				options.put("contentWidth", 400);
				RequestContext.getCurrentInstance().openDialog("dadosCancelamento", options, null);
			}else {
				Mensagem.lancarMensagemInfo("Venda sem informações do cancelamento", "");
			}
		}
	}


}
