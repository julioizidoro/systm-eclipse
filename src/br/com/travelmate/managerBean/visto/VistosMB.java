package br.com.travelmate.managerBean.visto;

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

import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;

import br.com.travelmate.facade.VistosFacade;
import br.com.travelmate.managerBean.LerArquivoTxt;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vistos;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class VistosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private List<Vistos> listaVistos;
	private List<Unidadenegocio> listaUnidade;
	private String voltar;
	private Unidadenegocio unidadeNegocio;
	private boolean librarComboUsuairo = true;
	private Date dataInicio;
	private Date dataFinal;
	private String vendaMatriz;
	private int idVenda;
	private String nome;
	private boolean habilitarUnidade = true;
	private boolean editarFicha = false;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private Integer nFichasFinanceiro;
	private List<Vistos> listaVendasFinalizada;
	private List<Vistos> listaVendasAndamento;
	private List<Vistos> listaVendasCancelada;
	private List<Vistos> listaVendasProcesso;
	private List<Vistos> listaVendasFinanceiro;
	private String numeroFichas;
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaVendasFinalizada = (List<Vistos>) session.getAttribute("listaVendasFinalizada");
		listaVendasAndamento = (List<Vistos>) session.getAttribute("listaVendasAndamento");
		listaVendasProcesso = (List<Vistos>) session.getAttribute("listaVendasProcesso");
		listaVendasFinanceiro = (List<Vistos>) session.getAttribute("listaVendasFinanceiro");
		listaVendasCancelada = (List<Vistos>) session.getAttribute("listaVendasCancelada");
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
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("Vistos")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				carregarListaVisto();
			}
			listarUnidade();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
				editarFicha = true;
				unidadeNegocio = new Unidadenegocio();
			} else {
				habilitarUnidade = true;
				editarFicha = false;  
				unidadeNegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
		}
	}

	public List<Vistos> getListaVistos() {
		return listaVistos;
	}

	public void setListaVistos(List<Vistos> listaVistos) {
		this.listaVistos = listaVistos;
	}

	public boolean isLibrarComboUsuairo() {
		return librarComboUsuairo;
	}

	public void setLibrarComboUsuairo(boolean librarComboUsuairo) {
		this.librarComboUsuairo = librarComboUsuairo;
	}

	public boolean isEditarFicha() {
		return editarFicha;
	}

	public void setEditarFicha(boolean editarFicha) {
		this.editarFicha = editarFicha;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Unidadenegocio getUnidadeNegocio() {
		return unidadeNegocio;
	}

	public void setUnidadeNegocio(Unidadenegocio unidadeNegocio) {
		this.unidadeNegocio = unidadeNegocio;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getVendaMatriz() {
		return vendaMatriz;
	}

	public void setVendaMatriz(String vendaMatriz) {
		this.vendaMatriz = vendaMatriz;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	
	

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
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

	public List<Vistos> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Vistos> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Vistos> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Vistos> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Vistos> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Vistos> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Vistos> getListaVendasProcesso() {
		return listaVendasProcesso;
	}

	public void setListaVendasProcesso(List<Vistos> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}

	public List<Vistos> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Vistos> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public String calculaJuros() {
		RequestContext.getCurrentInstance().openDialog("calculaJuros");
		return "";
	}

	public String editar(Vistos vistos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vistos", vistos); 
		session.setAttribute("idlead", vistos.getVendas().getIdlead()); 
		return "cadVistos";
	}

	public void listarUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar(true);
	}

	public void carregarListaVisto() {
		nome = "";
		idVenda = 0;
		dataFinal = null;
		dataInicio = null;
		unidadeNegocio = null;
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		VistosFacade vistosFacade = new VistosFacade();
		String sql = "";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = "select v from Vistos v where v.vendas.dataVenda>='" + dataConsulta
					+ "' order by v.vendas.dataVenda desc";
		} else {
			sql = "select v from Vistos v where v.usuario.idusuario="
					+ usuarioLogadoMB.getUsuario().getIdusuario() + " and v.vendas.dataVenda>='" + dataConsulta+ "'"; 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and v.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
			sql = sql + " order by v.vendas.dataVenda desc";
		}
		listaVistos = vistosFacade.listar(sql);
		if (listaVistos == null) {
			listaVistos = new ArrayList<Vistos>();
		}
		numeroFichas = "" + String.valueOf(listaVistos.size());
		pesquisar = "Nao";
		gerarQuantidadesFichas();
	}

	public void pesquisar() {
		String sql;
		sql = "select v from Vistos v where v.vendas.cliente.nome like '%" + nome + "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadeNegocio != null) {
				sql = sql + " and v.vendas.unidadenegocio.idunidadeNegocio=" + unidadeNegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and v.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and v.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and v.vendas.idvendas=" + idVenda;
		}
		if ((dataInicio != null) && (dataFinal != null)) {
			sql = sql + " and v.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and v.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		} else {
			if (nome.length() == 0) {
				String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
				sql = sql + " and v.vendas.dataVenda>='" + dataConsulta + "'";
			}
		}
		sql = sql + " order by v.vendas.dataVenda, v.vendas.cliente.nome";
		VistosFacade vistoFacade = new VistosFacade();
		listaVistos = vistoFacade.listar(sql);
		if (listaVistos == null) {
			listaVistos = new ArrayList<Vistos>();
		}
		numeroFichas = "" + String.valueOf(listaVistos.size());
		pesquisar = "Sim";
		gerarQuantidadesFichas();
	}

	public String gerarRelatorioVisto(Vistos vistos) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/visto/FichaOrcamentoVistoPagina01.jasper";
		Map parameters = new HashMap();
		parameters.put("idvisto", vistos.getIdvistos());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "OrcamentoVisto.pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String finalizarVisto(Vistos vistos) throws SQLException {
		vistos.setControle("Finalizado");
		VistosFacade vistosFacade = new VistosFacade();
		vistos = vistosFacade.salvar(vistos);
		return "consVistos";
	}

	public String vendaNaoMatriz() throws SQLException {
		vendaMatriz = "N";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0;
		session.setAttribute("idlead", idlead);
		return "cadVistos";
	}
	
	public String vendaMoema() throws SQLException {
		vendaMatriz = "M";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0;
		session.setAttribute("idlead", idlead);
		return "cadVistos";
	}

	public String vendaMatriz() throws SQLException {
		vendaMatriz = "S";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0;
		session.setAttribute("idlead", idlead);
		return "cadVistos";
	}

	public String novo() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 250);
		RequestContext.getCurrentInstance().openDialog("vendaMatriz", options, null);
		return "vendaMatriz";
	}

//	public String cancelarVenda(Vistos vistos) {
//		if (!vistos.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
//			Map<String, Object> options = new HashMap<String, Object>();
//			options.put("contentWidth", 400);
//			FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("venda", vistos.getVendas());
//			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
//		} else {
//			
//			vistos.getVendas().setSituacao("CANCELADA");
//			vendasDao.salvar(vistos.getVendas());
//			carregarListaVisto();
//		}
//		return "";
//	}
	
	public String cancelarVenda(Vistos vistos) {
		if (vistos.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
				|| vistos.getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", vistos.getVendas());
			session.setAttribute("voltar", "consultaVistos");
			return "emissaocancelamento";
		} else if (vistos.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			vistos.getVendas().setSituacao("CANCELADA");
			vendasDao.salvar(vistos.getVendas());
			carregarListaVisto();
		}
		return "";
	}   

	public String corNome(Vistos vistos) {
		if (vistos.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Vistos vistos) {
		if (vistos.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public String botoesHabilitados(Vistos vistos) {
		if (vistos.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	}

	public String boletos(Vistos vistos) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(vistos.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + vistos.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(vistos.getVendas().getIdvendas()));
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
	
	
	public String vendaMatriz(Vistos vistos){
		if(vistos.getVendas().getVendasMatriz().equalsIgnoreCase("S")){
			return "Matriz";
		}else if(vistos.getVendas().getVendasMatriz().equalsIgnoreCase("N")){
			return "Não Matriz";
		}else{
			return "Moema";
		}
	}
	
	
	public String gerarRelatorioDeclaracaoInsencao(Vistos vistos) throws SQLException, IOException { 
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/visto/declaracaoinsencao.jasper");
		Map parameters = new HashMap();
		parameters.put("idcliente", vistos.getVendas().getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoVisto.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(VistosMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(VistosMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	
	public String gerarRelatorioContrato(Vistos vistos) throws SQLException, IOException { 
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/visto/contratovisto.jasper");
		Map parameters = new HashMap();
		parameters.put("idvendas", vistos.getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo); 
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//visto//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "ContratoVisto.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(VistosMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(VistosMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	
	public String informacoes(Vistos vistos) {
		if (vistos.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";  
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", vistos.getVendas());
			String voltar = "consultaVistos";
			session.setAttribute("voltar", voltar);  
			return "consLogVenda";
		}  
	}
	
	public String documentacao(Vistos vistos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", vistos.getVendas());
		voltar = "consultaVistos";  
		session.setAttribute("voltar", voltar);
		return "consArquivos";
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
	
	
	public boolean imagemSituacaoUsuario(Vistos vistos) {
		if (vistos.getVendas().getSituacao().equals("FINALIZADA")) {
			vistos.setImagem("../../resources/img/finalizadoFicha.png");
			vistos.setTituloFicha("FICHA FINALIZADA");
		} else if (vistos.getVendas().getSituacao().equals("ANDAMENTO")
				&& !vistos.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			vistos.setImagem("../../resources/img/ficharestricao.png");
			if (vistos.getVendas().getSituacaofinanceiro().equalsIgnoreCase("P")) {
				vistos.setTituloFicha("FINANCEIRO - PENDENTE (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}else {
				vistos.setTituloFicha("FINANCEIRO - AGUARDANDO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}
		} else if (vistos.getVendas().getSituacao().equals("ANDAMENTO")) {
			vistos.setImagem("../../resources/img/amarelaFicha.png");
			vistos.setTituloFicha("ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)");
		} else if (vistos.getVendas().getSituacao().equals("CANCELADA")) {
			vistos.setImagem("../../resources/img/fichaCancelada.png");
			vistos.setTituloFicha("FICHA CANCELADA");
		} else if ((vistos.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (vistos.getVendas().isRestricaoparcelamento())) {
			vistos.setImagem("../../resources/img/ficharestricao.png");
			vistos.setTituloFicha("FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
		} else {
			vistos.setImagem("../../resources/img/processoFicha.png");
			vistos.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
		}
		return true;
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
		for (int i = 0; i < listaVistos.size(); i++) {
			if (listaVistos.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaVistos.get(i));
			}else if(listaVistos.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaVistos.get(i));
			}else if(listaVistos.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO") 
					&& !listaVistos.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaVistos.get(i));
			}else if(listaVistos.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaVistos.get(i));
			}else{
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaVistos.get(i));
			}
		}
	}
	
	public String fichasVistos(Vistos vistos){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vistos", vistos);
		session.setAttribute("listaVendasFinalizada", listaVendasFinalizada);
		session.setAttribute("listaVendasAndamento", listaVendasAndamento);
		session.setAttribute("listaVendasCancelada", listaVendasCancelada);
		session.setAttribute("listaVendasProcesso", listaVendasProcesso);
		session.setAttribute("listaVendasFinanceiro", listaVendasFinanceiro);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Trainee");
		session.setAttribute("chamadaTela", "Trainee");
		return "fichasVistos";
	}
	
	
	public String contrato(Vistos vistos){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vistos", vistos);
		session.setAttribute("listaVendasFinalizada", listaVendasFinalizada);
		session.setAttribute("listaVendasAndamento", listaVendasAndamento);
		session.setAttribute("listaVendasCancelada", listaVendasCancelada);
		session.setAttribute("listaVendasProcesso", listaVendasProcesso);
		session.setAttribute("listaVendasFinanceiro", listaVendasFinanceiro);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Vistos");
		session.setAttribute("chamadaTela", "Vistos");
		return "contratoVisto";
	}
	

}
