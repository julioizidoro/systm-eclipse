package br.com.travelmate.managerBean.trainee;

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
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.TraineeFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.LerArquivoTxt;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Controletrainee;
import br.com.travelmate.model.Credito;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class TraineeMB implements Serializable {

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
	private List<Trainee> listaTrainee;
	private String numFichas;
	private String obsTM;
	private String nome;
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private boolean habilitarUnidade = true;
	private String motivoCancelamento;
	private Trainee trainee;
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
	private List<Trainee> listaVendasFinalizada;
	private List<Trainee> listaVendasAndamento;
	private List<Trainee> listaVendasCancelada;
	private List<Trainee> listaVendasProcesso;
	private List<Trainee> listaVendasFinanceiro;
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaVendasFinalizada = (List<Trainee>) session.getAttribute("listaVendasFinalizada");
		listaVendasAndamento = (List<Trainee>) session.getAttribute("listaVendasAndamento");
		listaVendasProcesso = (List<Trainee>) session.getAttribute("listaVendasProcesso");
		listaVendasFinanceiro = (List<Trainee>) session.getAttribute("listaVendasFinanceiro");
		listaVendasCancelada = (List<Trainee>) session.getAttribute("listaVendasCancelada");
		nomePrograma = (String) session.getAttribute("nomePrograma");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		session.removeAttribute("listaVendasFinalizada");
		session.removeAttribute("listaVendasAndamento");
		session.removeAttribute("listaVendasProcesso");
		session.removeAttribute("listaVendasFinanceiro");
		session.removeAttribute("listaVendasCancelada");
		session.removeAttribute("pesquisar");
		session.removeAttribute("nomePrograma");
		session.removeAttribute("chamadaTela");
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("Trainee")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				carregarListaVendasTrainee();
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

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Trainee> getListaTrainee() {
		return listaTrainee;
	}

	public void setListaTrainee(List<Trainee> listaTrainee) {
		this.listaTrainee = listaTrainee;
	}

	public String getNumFichas() {
		return numFichas;
	}

	public void setNumFichas(String numFichas) {
		this.numFichas = numFichas;
	}

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
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

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
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

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
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

	public Integer getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}

	public void setnFichasFinanceiro(Integer nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	}

	public List<Trainee> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Trainee> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Trainee> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Trainee> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Trainee> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Trainee> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Trainee> getListaVendasProcesso() {
		return listaVendasProcesso;
	}

	public void setListaVendasProcesso(List<Trainee> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}

	public List<Trainee> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Trainee> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}

	public String cadAustralia() throws SQLException {
		String tipo = "Australia";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipo", tipo);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "cadEstagioAustralia";
	}

	public String cadUsa() throws SQLException {
		String tipo = "EUA";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipo", tipo);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "cadTrainee";
	}

	public void carregarListaVendasTrainee() {
		String sql = null;
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		sql = "Select t from Trainee t where t.vendas.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getTrainee();
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " and  t.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and t.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sql = sql + " and t.vendas.dataVenda>='" + dataConsulta + "' order by t.vendas.dataVenda desc";
		TraineeFacade traineeFacade = new TraineeFacade();
		listaTrainee = traineeFacade.lista(sql);
		if (listaTrainee == null) {
			listaTrainee = new ArrayList<Trainee>();
		}
		numFichas = "" + String.valueOf(listaTrainee.size());
		gerarQuantidadesFichas();
	}

	public boolean imagemSituacaoUsuario(Trainee trainee) {
		if (trainee.getVendas().getSituacao().equals("FINALIZADA")) {
			trainee.setHabilitarImagemGerencial(false);
			trainee.setHabilitarImagemFranquia(true);
			trainee.setImagem("../../resources/img/finalizadoFicha.png");
			trainee.setTituloFicha("FICHA FINALIZADA");
		}  else if (trainee.getVendas().getSituacao().equals("ANDAMENTO")
				&& !trainee.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
				trainee.setHabilitarImagemGerencial(true);
				trainee.setHabilitarImagemFranquia(false);
			} else {
				trainee.setHabilitarImagemGerencial(false);
				trainee.setHabilitarImagemFranquia(true);
			}
			trainee.setImagem("../../resources/img/ficharestricao.png");
			if (trainee.getVendas().getSituacaofinanceiro().equalsIgnoreCase("P")) {
				trainee.setTituloFicha("FINANCEIRO - PENDENTE (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}else {
				trainee.setTituloFicha("FINANCEIRO - AGUARDANDO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}
		}else if (trainee.getVendas().getSituacao().equals("ANDAMENTO")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
				trainee.setHabilitarImagemGerencial(true);
				trainee.setHabilitarImagemFranquia(false);
			} else {
				trainee.setHabilitarImagemGerencial(false);
				trainee.setHabilitarImagemFranquia(true);
			}
			trainee.setImagem("../../resources/img/amarelaFicha.png");
			trainee.setTituloFicha("ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)");
		} else if (trainee.getVendas().getSituacao().equals("CANCELADA")) {
			trainee.setHabilitarImagemGerencial(false);
			trainee.setHabilitarImagemFranquia(true);
			trainee.setImagem("../../resources/img/fichaCancelada.png");
			trainee.setTituloFicha("FICHA CANCELADA");
		} else if ((trainee.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (trainee.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				trainee.setHabilitarImagemGerencial(true);
				trainee.setHabilitarImagemFranquia(false);
			} else {
				trainee.setHabilitarImagemGerencial(false);
				trainee.setHabilitarImagemFranquia(true);
			}
			trainee.setImagem("../../resources/img/ficharestricao.png");
			trainee.setTituloFicha("FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
		} else {
			trainee.setHabilitarImagemGerencial(false);
			trainee.setHabilitarImagemFranquia(true);
			trainee.setImagem("../../resources/img/processoFicha.png");
			trainee.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
		}
		return trainee.isHabilitarImagemGerencial();
	}

	public String corNome(Trainee trainee) {
		if (trainee.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Trainee trainee) {
		if (trainee.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public String botoesHabilitados(Trainee trainee) {
		if (trainee.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	}

	public String obsTM(Trainee trainee) {
		obsTM = trainee.getVendas().getObstm();
		return obsTM;
	}

	public void limparPesquisa() {
		unidadenegocio = null;
		dataInicio = null;
		dataTermino = null;
		situacao = "TODAS";
		nome = "";
		idVenda = 0;
		pesquisar = "Nao";
		carregarListaVendasTrainee();
	}

	public void pesquisar() {
		String sql = null;
		sql = "Select t from Trainee t where t.vendas.cliente.nome like '%" + nome + "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null) {
				sql = sql + " and t.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and t.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and t.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and t.vendas.idvendas=" + idVenda;
		}
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and t.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and t.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} 
		if (!situacao.equalsIgnoreCase("TODAS")) {
			sql = sql + " and t.vendas.situacao='" + situacao + "'";
		}
		sql = sql + " order by t.vendas.dataVenda, t.vendas.cliente.nome";
		TraineeFacade traineeFacade = new TraineeFacade();
		listaTrainee = traineeFacade.lista(sql);
		if (listaTrainee == null) {
			listaTrainee = new ArrayList<Trainee>();
		}
		numFichas = "" + String.valueOf(listaTrainee.size());
		pesquisar = "Sim";
		gerarQuantidadesFichas();
	}

	public String editar(Trainee trainee) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("trainee", trainee); 
		session.setAttribute("idlead", trainee.getVendas().getIdlead());
		if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
			return "cadEstagioAustralia";
		} else
			return "cadTrainee";
	}

	public String gerarRelatorioFicha(Trainee trainee) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
			caminhoRelatorio = "/reports/trainee/FichaEstagioAustralia01.jasper";
		} else {
			caminhoRelatorio = "/reports/trainee/FichaTraineePagina01.jasper";
		}
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//trainee//"));
		parameters.put("idvendas", trainee.getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"fichaEstagioAustralia-" + trainee.getIdtrainee() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(TraineeMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(TraineeMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioContrato(Trainee trainee) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
			caminhoRelatorio = "/reports/trainee/contratoEstagioAustralia01.jasper";
		} else {
			caminhoRelatorio = "/reports/trainee/contratoTraineePagina01.jasper";
		}
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//trainee//"));
		parameters.put("idvendas", trainee.getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"ContratoEstagioAustralia-" + trainee.getIdtrainee() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(TraineeMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(TraineeMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioRecibo(Trainee trainee) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(trainee.getVendas().getIdvendas());
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
			Map parameters = new HashMap();
			parameters.put("idvendas", trainee.getVendas().getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo);
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"reciboPagamento-" + trainee.getIdtrainee() + ".pdf", null);
			} catch (JRException ex1) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
		return "";
	}

	public String gerarRelatorioTermoVisto(Trainee trainee) throws SQLException, IOException {
		if (!trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
			Map parameters = new HashMap();
			parameters.put("idcliente", trainee.getVendas().getCliente().getIdcliente());
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"TermoVisto-" + trainee.getIdtrainee() + ".pdf", null);
			} catch (JRException ex1) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return "";
	}

	public void cancelar(Trainee trainee) {
		this.trainee = trainee;
	}

//	public String cancelarVenda(Trainee trainee) {
//		if (trainee.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
//			Map<String, Object> options = new HashMap<String, Object>();
//			options.put("contentWidth", 400);
//			FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("venda", trainee.getVendas());
//			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
//		} else if (trainee.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
//			
//			trainee.getVendas().setSituacao("CANCELADA");
//			vendasDao.salvar(trainee.getVendas());
//			carregarListaVendasTrainee();
//		}
//		return "";
//	}
	
	public String cancelarVenda(Trainee trainee) {
		if (trainee.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
				|| trainee.getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", trainee.getVendas());
			session.setAttribute("voltar", "consultaTrainee");
			return "emissaocancelamento";
		} else if (trainee.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			trainee.getVendas().setSituacao("CANCELADA");
			vendasDao.salvar(trainee.getVendas());
			carregarListaVendasTrainee();
		}
		return "";
	}   

	public void salvarControle() throws SQLException {
		Controletrainee controletrainee = new Controletrainee();
		TraineeFacade traineeFacade = new TraineeFacade();
		controletrainee = traineeFacade.consultarControle(trainee.getVendas().getIdvendas());
		if (controletrainee == null) {
			ControlerBean controlerBean = new ControlerBean();
			float valorPrevisto = 0.0f;
			if (trainee.getVendas().getVendascomissao() != null) {
				valorPrevisto = trainee.getVendas().getVendascomissao().getValorfornecedor();
			}
			controlerBean.salvarControlTrainee(trainee.getVendas(), trainee, valorPrevisto);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Controle já existente.");
		}
	}

	public void dialogSalvarControle(Trainee trainee) {
		this.trainee = trainee;
	}

	public String documentacao(Trainee trainee) {
		boolean validar = true;
		if (trainee.getVendas().getSituacao().equalsIgnoreCase("PROCESSO") && usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1) {
		String dataStringValidade = Formatacao.ConvercaoDataPadrao(new Date());
		Date dataAtual = Formatacao.ConvercaoStringData(dataStringValidade);
		Date dataValidade = trainee.getVendas().getDatavalidade();
		if (dataValidade != null) {
			if (!dataAtual.after(dataValidade)) {
				validar = true;
			}else {
				validar = false;
			}
		}
		}
		if (!validar) {
			Mensagem.lancarMensagemInfo("Favor atualizar o câmbio desta ficha", "está ficha ultrapassou os 3 dias de validade");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", trainee.getVendas());
			voltar = "consultaTrainee";
			session.setAttribute("voltar", voltar);
			return "consArquivos";
		}
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
		if ((trainee.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (trainee.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				Vendas venda = trainee.getVendas();
				venda.setRestricaoparcelamento(false);
				
				venda = vendasDao.salvar(venda);
				trainee.setVendas(venda);
				Formapagamento forma = trainee.getVendas().getFormapagamento();
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

	public String boletos(Trainee trainee) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(trainee.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + trainee.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas,
							String.valueOf(trainee.getVendas().getIdvendas()));
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

	public String informacoes(Trainee trainee) {
		if (trainee.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", trainee.getVendas());
			voltar = "consultaTrainee";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}
	
	public void gerarQuantidadesFichas(){
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
		for (int i = 0; i < listaTrainee.size(); i++) {
			if (listaTrainee.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaTrainee.get(i));
			}else if(listaTrainee.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaTrainee.get(i));
			}else if(listaTrainee.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO") 
					&& !listaTrainee.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaTrainee.get(i));
			}else if(listaTrainee.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaTrainee.get(i));
			}else{
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaTrainee.get(i));
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
	
	public String retornarImgOpcoes(){
		if(expandirOpcoes){ 
			return "../../resources/img/esconderOpcoes.png";
		}else return "../../resources/img/expandirOpcoes.png";
	} 
	
	public String retornarTitleOpcoes(){
		if(expandirOpcoes){ 
			return "Esconder Opções";     
		}else return "Expandir Opções";
	} 
	
	
	public String retornarIconeObsTM(Trainee trainee){
		if (trainee.getVendas().getObstm() != null && trainee.getVendas().getObstm().length() > 0) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}
	
	
	public String visualizarContasReceber(Trainee traineee) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", traineee.getVendas());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
	
	public String notificarEfetuarFichaCrm(){
		return "followUp";
	}
	

	
	
	public void verificarIdCredito(Trainee trainee) {
		if (trainee.getVendas().getCancelamento() != null) {
			if (trainee.getVendas().getCancelamento().getCancelamentocredito() != null) {
				if (trainee.getVendas().getCancelamento().getCancelamentocredito().getCredito().getTipocredito().equalsIgnoreCase("Crédito")) {
					Credito credito = trainee.getVendas().getCancelamento().getCancelamentocredito().getCredito();
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
	
	public String relatorioTermoQuitacao(Trainee trainee) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		Cancelamento cancelamento = cancelamentoFacade.consultar(trainee.getVendas().getIdvendas());
		session.setAttribute("cancelamento", cancelamento);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("reciboTermoQuitacao", options, null);
		return "";
	}
	
	public String fichaTrainee(Trainee trainee){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("trainee", trainee);
		session.setAttribute("listaVendasFinalizada", listaVendasFinalizada);
		session.setAttribute("listaVendasAndamento", listaVendasAndamento);
		session.setAttribute("listaVendasCancelada", listaVendasCancelada);
		session.setAttribute("listaVendasProcesso", listaVendasProcesso);
		session.setAttribute("listaVendasFinanceiro", listaVendasFinanceiro);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Trainee");
		session.setAttribute("chamadaTela", "Trainee");
		if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
			return "fichaTraineeAus";
		}
		return "fichaTraineeEUA";
	}
	
	
	public String contrato(Trainee trainee){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("trainee", trainee);
		session.setAttribute("listaVendasFinalizada", listaVendasFinalizada);
		session.setAttribute("listaVendasAndamento", listaVendasAndamento);
		session.setAttribute("listaVendasCancelada", listaVendasCancelada);
		session.setAttribute("listaVendasProcesso", listaVendasProcesso);
		session.setAttribute("listaVendasFinanceiro", listaVendasFinanceiro);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Trainee");
		session.setAttribute("chamadaTela", "Trainee");
		return "contratoTrainee";
	}
}
