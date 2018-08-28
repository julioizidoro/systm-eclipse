package br.com.travelmate.managerBean.financeiro.crmcobranca;

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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.AupairFacade; 
import br.com.travelmate.facade.CrmCobrancaFacade;
import br.com.travelmate.facade.CrmCobrancaHistoricoFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.facade.HighSchoolFacade; 
import br.com.travelmate.facade.MotivoCancelamentoFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.TraineeFacade; 
import br.com.travelmate.facade.VistosFacade;
import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Crmcobranca;
import br.com.travelmate.model.Crmcobrancahistorico;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Highschool; 
import br.com.travelmate.model.Motivocancelamento;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vistos;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class HistoricoCobrancaClienteMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Motivocancelamento> listaMotivoCancelamento;
	private Motivocancelamento motivocancelamento;
	private Vendas venda;
	private String prioridade;
	private Crmcobranca crmcobranca;
	private Crmcobrancahistorico Crmcobrancahistorico;
	private List<Crmcobrancahistorico> listaCobrancaHistorico;
	private Curso curso;
	private Worktravel worktravel;
	private Voluntariado voluntariado;
	private Aupair aupair;
	private Trainee trainee;
	private Programasteens programateens;
	private Highschool highschool;
	private Vendas vendas;
	private Demipair demipair;
	private He he;
	private Seguroviagem seguroViagem;
	private Vistos vistos;
	private Questionariohe questionarioHe;
	private String iconeFicha = ""; 
	private List<Crmcobranca> listaCrmCobranca;
	private String voltarPagina = "";
	private Date dataInicioCobranca;
	private String nota = "";
	private boolean desabilitarCampos = false;
	private boolean habilitarPrioridade = true;
	private List<Crmcobranca> listaCrmCobrancaTodos;
	private String funcao;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		crmcobranca = (Crmcobranca) session.getAttribute("crmcobranca");
		venda = (Vendas) session.getAttribute("venda");
		voltarPagina = (String) session.getAttribute("voltarPagina");
		listaCrmCobrancaTodos = (List<Crmcobranca>) session.getAttribute("listaCrmCobrancaTodos");
		funcao = (String) session.getAttribute("funcao");
		session.removeAttribute("listaCrmCobrancaTodos");
		session.removeAttribute("voltarPagina");
		session.removeAttribute("listaCrmCobranca");
		session.removeAttribute("crmcobranca");
		session.removeAttribute("vendas");
		session.removeAttribute("funcao");
		if (crmcobranca != null && venda == null) {
			venda = crmcobranca.getVendas();
			nota = crmcobranca.getNota();
			dataInicioCobranca = crmcobranca.getDatainiciocobranca();
		}
		gerarListaMotivoCancelamento();
		gerarListaHistorico();
		retornarIconeFicha();
		verificarCobranca();
	}
	
	
	public List<Motivocancelamento> getListaMotivoCancelamento() {
		return listaMotivoCancelamento;
	}


	public void setListaMotivoCancelamento(List<Motivocancelamento> listaMotivoCancelamento) {
		this.listaMotivoCancelamento = listaMotivoCancelamento;
	}


	public Motivocancelamento getMotivocancelamento() {
		return motivocancelamento;
	} 

	public void setMotivocancelamento(Motivocancelamento motivocancelamento) {
		this.motivocancelamento = motivocancelamento;
	} 
	
	public Vendas getVenda() {
		return venda;
	}


	public void setVenda(Vendas venda) {
		this.venda = venda;
	}


	public String getPrioridade() {
		return prioridade;
	}


	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}


	public Crmcobranca getCrmcobranca() {
		return crmcobranca;
	}


	public void setCrmcobranca(Crmcobranca crmcobranca) {
		this.crmcobranca = crmcobranca;
	}


	public Crmcobrancahistorico getCrmcobrancahistorico() {
		return Crmcobrancahistorico;
	}


	public void setCrmcobrancahistorico(Crmcobrancahistorico crmcobrancahistorico) {
		Crmcobrancahistorico = crmcobrancahistorico;
	}


	public List<Crmcobrancahistorico> getListaCobrancaHistorico() {
		return listaCobrancaHistorico;
	}


	public void setListaCobrancaHistorico(List<Crmcobrancahistorico> listaCobrancaHistorico) {
		this.listaCobrancaHistorico = listaCobrancaHistorico;
	}


	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}


	public Worktravel getWorktravel() {
		return worktravel;
	}


	public void setWorktravel(Worktravel worktravel) {
		this.worktravel = worktravel;
	}


	public Voluntariado getVoluntariado() {
		return voluntariado;
	}


	public void setVoluntariado(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
	}


	public Aupair getAupair() {
		return aupair;
	}


	public void setAupair(Aupair aupair) {
		this.aupair = aupair;
	}


	public Trainee getTrainee() {
		return trainee;
	}


	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}


	public Programasteens getProgramateens() {
		return programateens;
	}


	public void setProgramateens(Programasteens programateens) {
		this.programateens = programateens;
	}


	public Highschool getHighschool() {
		return highschool;
	}


	public void setHighschool(Highschool highschool) {
		this.highschool = highschool;
	}


	public Vendas getVendas() {
		return vendas;
	}


	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}


	public Demipair getDemipair() {
		return demipair;
	}


	public void setDemipair(Demipair demipair) {
		this.demipair = demipair;
	}


	public He getHe() {
		return he;
	}


	public void setHe(He he) {
		this.he = he;
	}


	public Seguroviagem getSeguroViagem() {
		return seguroViagem;
	}


	public void setSeguroViagem(Seguroviagem seguroViagem) {
		this.seguroViagem = seguroViagem;
	}


	public Vistos getVistos() {
		return vistos;
	}


	public void setVistos(Vistos vistos) {
		this.vistos = vistos;
	}


	public Questionariohe getQuestionarioHe() {
		return questionarioHe;
	}


	public void setQuestionarioHe(Questionariohe questionarioHe) {
		this.questionarioHe = questionarioHe;
	}


	public String getIconeFicha() {
		return iconeFicha;
	}


	public void setIconeFicha(String iconeFicha) {
		this.iconeFicha = iconeFicha;
	}


	public Date getDataInicioCobranca() {
		return dataInicioCobranca;
	}


	public void setDataInicioCobranca(Date dataInicioCobranca) {
		this.dataInicioCobranca = dataInicioCobranca;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}


	public boolean isDesabilitarCampos() {
		return desabilitarCampos;
	}


	public void setDesabilitarCampos(boolean desabilitarCampos) {
		this.desabilitarCampos = desabilitarCampos;
	}


	public boolean isHabilitarPrioridade() {
		return habilitarPrioridade;
	}


	public void setHabilitarPrioridade(boolean habilitarPrioridade) {
		this.habilitarPrioridade = habilitarPrioridade;
	}


	public List<Crmcobranca> getListaCrmCobranca() {
		return listaCrmCobranca;
	}


	public void setListaCrmCobranca(List<Crmcobranca> listaCrmCobranca) {
		this.listaCrmCobranca = listaCrmCobranca;
	}


	public List<Crmcobranca> getListaCrmCobrancaTodos() {
		return listaCrmCobrancaTodos;
	}


	public void setListaCrmCobrancaTodos(List<Crmcobranca> listaCrmCobrancaTodos) {
		this.listaCrmCobrancaTodos = listaCrmCobrancaTodos;
	}


	public void gerarListaMotivoCancelamento(){
		MotivoCancelamentoFacade motivoCancelamentoFacade = new MotivoCancelamentoFacade();
		String sql = "select m from Motivocancelamento m order by m.motivo";
		listaMotivoCancelamento = motivoCancelamentoFacade.lista(sql);
		if(listaMotivoCancelamento==null){
			listaMotivoCancelamento = new ArrayList<Motivocancelamento>();
		}
	} 
	
	
	public String retornarCorHistorico(int idHistorico) {
		if (idHistorico % 2 == 0) {
			return "dataScrollerBranco";
		} else
			return "dataScrollerCinza";
	}
	
	
	public String voltar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaCrmCobrancaTodos", listaCrmCobrancaTodos);
		session.setAttribute("funcao", funcao);
		return voltarPagina;
	}
	
	
	public void cadHistoricoCobrancaCliente(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 350);
		options.put("modal", true);
		session.setAttribute("crmcobranca", crmcobranca);
		session.setAttribute("venda", venda);
		RequestContext.getCurrentInstance().openDialog("cadHistoricoCobrancaCliente", options, null);
	}
	
	
	public String visualizarContasReceber() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 950);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceberCobranca", options, null);
		return "";
	}
	
	
	public String enviarEmailCobrancaCliente() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("enviarEmailCobrancaCliente", options, null);
		return "";
	}
	
	
	public void pegarPrioridade(String prioridade){
		crmcobranca.setPrioridade(prioridade);
		CrmCobrancaFacade crmCobrancaFacade = new CrmCobrancaFacade();
		crmcobranca = crmCobrancaFacade.salvar(crmcobranca);
		this.prioridade = prioridade;
	}
	
	
	public String retornarCoresSituacao(int numeroPrioridade) {
		if (numeroPrioridade == 1) {
			return "#9ACD32;";
		} else if (numeroPrioridade == 2) {
			return "#023502;";
		} else if (numeroPrioridade == 3) {
			return "#1E90FF;";
		}else if (numeroPrioridade == 4) {
			return "#FF8C00;";
		} else if (numeroPrioridade == 5) {
			return "#B22222;";
		}
		return"";
	}
	
	
	public void gerarListaCobrancaHistorico(){
		CrmCobrancaHistoricoFacade cobrancaHistoricoFacade = new CrmCobrancaHistoricoFacade();
		listaCobrancaHistorico = cobrancaHistoricoFacade.lista("SELECT c FROM Crmcobrancahistorico c WHERE c.cliente.idcliente=" 
				+ venda.getCliente().getIdcliente());
		
		if (listaCobrancaHistorico == null) {
			listaCobrancaHistorico = new ArrayList<>();
		}
	}
	
	public void gerarListaHistorico() {
		CrmCobrancaHistoricoFacade cobrancaHistoricoFacade = new CrmCobrancaHistoricoFacade();
		String sql = "select c from Crmcobrancahistorico c where c.cliente.idcliente=" + venda.getCliente().getIdcliente()
				+ " order by c.data DESC, c.idcrmcobrancahistorico DESC";
		listaCobrancaHistorico = cobrancaHistoricoFacade.lista(sql);
		if (listaCobrancaHistorico == null) {
			listaCobrancaHistorico = new ArrayList<>();
		}
	}  
	
	public String gerarRelatorioFicha() throws IOException {
		Map parameters = new HashMap();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "";
		if (venda.getProdutos().getIdprodutos() == 1) {
			if (curso.getHabilitarSegundoCurso().equalsIgnoreCase("N")) {
				if (curso.isDadospais()) {
					caminhoRelatorio = "/reports/curso/FichaCursoDadosPaisPagina01.jasper";
				} else {
					caminhoRelatorio = "/reports/curso/FichaCursoPagina01.jasper";
				}
			} else {
				if (curso.isDadospais()) {
					caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
				} else {
					caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
				}
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//curso//"));
			parameters.put("idvendas", curso.getVendas().getIdvendas());
			parameters.put("sqlpagina2", gerarSqlSeguroViagems());
		} else if (venda.getProdutos().getIdprodutos() == 4) {
			caminhoRelatorio = "/reports/highSchool/FichaHighSchoolPagina01.jasper";
			parameters.put("idvendas", highschool.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//highSchool//"));
		} else if (venda.getProdutos().getIdprodutos() == 5) {
			caminhoRelatorio = "/reports/cursosTeens/FichaProgramasTeensPagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//cursosTeens//"));
			parameters.put("idvendas", programateens.getVendas().getIdvendas());
		} else if (venda.getProdutos().getIdprodutos() == 9) {
			caminhoRelatorio = "/reports/aupair/FichaAupairPagina01.jasper";
			parameters.put("idvendas", aupair.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//aupair//"));
		} else if (venda.getProdutos().getIdprodutos() == 13) {
			if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
				caminhoRelatorio = "/reports/trainee/FichaEstagioAustralia01.jasper";
			} else {
				caminhoRelatorio = "/reports/trainee/FichaTraineePagina01.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//trainee//"));
			parameters.put("idvendas", trainee.getVendas().getIdvendas());
		} else if (venda.getProdutos().getIdprodutos() == 16) {
			caminhoRelatorio = "/reports/voluntariado/FichaVoluntariadoPagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//voluntariado//"));
			parameters.put("idvendas", voluntariado.getVendas().getIdvendas());
			parameters.put("sqlpagina2", gerarSqlPagina1(voluntariado));
		} else if (venda.getProdutos().getIdprodutos() == 10) {
			caminhoRelatorio = "/reports/worktravel/FichaWorkPagina01.jasper";
			parameters.put("idvendas", worktravel.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//worktravel//"));
		} else if (venda.getProdutos().getIdprodutos() == 20) {
			caminhoRelatorio = "/reports/demipair/FichaDemipairPagina01.jasper";
			parameters.put("idvendas", demipair.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//demipair//"));
		} else if (venda.getProdutos().getIdprodutos() == 22) {
			if (he.isFichafinal()) {
				caminhoRelatorio = "/reports/higherEducation/FichaFinalHe1.jasper";
			} else {
				caminhoRelatorio = "/reports/higherEducation/FichaInscricaoHe1.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
			parameters.put("idvendas", he.getVendas().getIdvendas());
		} else if (venda.getProdutos().getIdprodutos() == 2) {
			if (seguroViagem.getIdvendacurso() > 0) {
				caminhoRelatorio = ("/reports/seguro/FichaSeguroCursoPagina01.jasper");
			} else {
				caminhoRelatorio = ("/reports/seguro/FichaSeguroPagina01.jasper");
			}
			if (seguroViagem.getIdvendacurso() > 0) {
				CursoFacade cursoFacade = new CursoFacade();
				Curso curso = cursoFacade.consultarCursos(seguroViagem.getIdvendacurso());
				if (curso != null) {
					seguroViagem.setNomeContatoEmergencia(curso.getNomeContatoEmergencia());
					seguroViagem.setFoneContatoEmergencia(curso.getFoneContatoEmergencia());
					seguroViagem.setPaisDestino(curso.getPais());
					SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
					seguroViagem = seguroViagemFacade.salvar(seguroViagem);
				}
			}
			parameters.put("idvendas", seguroViagem.getVendas().getIdvendas());
		}else if(vendas.getProdutos().getIdprodutos() == 3){
			caminhoRelatorio = "/reports/visto/FichaOrcamentoVistoPagina01.jasper";
			parameters.put("idvisto", vistos.getIdvistos());
		}
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"ficha-" + crmcobranca.getVendas().getIdvendas() + ".pdf", null);
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
	
	
	public void imprimirFicha() {
		if (venda.getProdutos().getIdprodutos() == 1) {
			buscarCurso();
		} else if (venda.getProdutos().getIdprodutos() == 4) {
			buscarHighSchool();
		} else if (venda.getProdutos().getIdprodutos() == 5) {
			buscarProgramasTeens();
		} else if (venda.getProdutos().getIdprodutos() == 9) {
			buscarAuPair();
		} else if (venda.getProdutos().getIdprodutos() == 13) {
			buscarTrainee();
		} else if (venda.getProdutos().getIdprodutos() == 16) {
			buscarVoluntariado();
		} else if (venda.getProdutos().getIdprodutos() == 20) {
			buscarDemiPair();
		} else if (venda.getProdutos().getIdprodutos() == 10) {
			buscarWorkTravel();
		}
		try {
			gerarRelatorioFicha();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public Curso buscarCurso() {
		CursoFacade cursoFacade = new CursoFacade();
		curso = cursoFacade.consultarCursos(venda.getIdvendas());
		return curso;
	}

	public Aupair buscarAuPair() {
		AupairFacade aupairFacade = new AupairFacade();
		aupair = aupairFacade.consultar(venda.getIdvendas());
		return aupair;
	}

	public Highschool buscarHighSchool() {
		HighSchoolFacade hiSchoolFacade = new HighSchoolFacade();
		highschool = hiSchoolFacade.consultarHighschool(venda.getIdvendas());
		return highschool;
	}

	public Programasteens buscarProgramasTeens() {
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		programateens = programasTeensFacede.find(venda.getIdvendas());
		return programateens;
	}

	public Trainee buscarTrainee() {
		TraineeFacade traineeFacade = new TraineeFacade();
		trainee = traineeFacade.consultar(venda.getIdvendas());
		return trainee;
	}

	public Voluntariado buscarVoluntariado() {
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		voluntariado = voluntariadoFacade.consultar(venda.getIdvendas());
		return voluntariado;
	}

	public Demipair buscarDemiPair() {
		DemipairFacade demipairFacade = new DemipairFacade();
		demipair = demipairFacade.consultar(venda.getIdvendas());
		return demipair;
	}

	public Worktravel buscarWorkTravel() {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		worktravel = workTravelFacade.consultarWork(venda.getIdvendas());
		return worktravel;
	}

	public He buscarHe() {
		HeFacade heFacade = new HeFacade();
			he = heFacade.consultarVenda(venda.getIdvendas());
		return he;   
	}    
	
	public Questionariohe buscarQuestionarioHe() {
		QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
		questionarioHe = questionarioHeFacade.consultarVenda(venda.getIdvendas());
		return questionarioHe;
	}

	public Seguroviagem buscarSeguro() {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		seguroViagem = seguroViagemFacade.consultar(venda.getIdvendas());
		return seguroViagem;
	}
	
	public Vistos buscarVistos(){
		VistosFacade vistosFacade = new VistosFacade();
		vistos = vistosFacade.consultarVistos(venda.getIdvendas());
		return vistos;
	}

	public String gerarSqlSeguroViagems() {
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemController.consultarSeguroCurso(curso.getVendas().getIdvendas());
		String sqlseguro = "";
		if (seguro == null) {
			seguro = seguroViagemController.consultar(curso.getVendas().getIdvendas());
			sqlseguro = " join seguroviagem on seguroviagem.vendas_idvendas = vendas.idvendas ";
		} else {
			sqlseguro = " join seguroviagem on seguroviagem.idvendacurso = vendas.idvendas";
		}
		String sql = "Select distinct vendas.dataVenda, vendas.valor as valorVenda, cursos.nomeCurso, cursos.escola,"
				+ "cursos.cidade, cursos.pais, cursos.aulassemana, cursos.numerosenamas, cursos.dataInicio, "
				+ "cursos.dataTermino, cursos.tipoAcomodacao, cursos.numeroSemanasAcamodacao, cursos.tipoquarto,"
				+ "cursos.refeicoes, cursos.adicionais, cursos.datachegada, cursos.dataSaida, cursos.familiacomcrianca,"
				+ "cursos.familiacomanimais, cursos.fumante, cursos.vegetariano, cursos.hobbies, cursos.possuiAlergia,"
				+ "cursos.quaisalergias, cursos.solicitacoesespeciais, cursos.caratovtm, cursos.numerocartaovtm,"
				+ "cursos.moedacartaovtm, cursos.transferin, cursos.transferouto, cursos.passagemaerea, cursos.observacaopassagemaerea,"
				+ "cursos.vistoconsular, cursos.dataEntregadocumentosvistos, cursos.observacaovisto, cursos.nomecontatoemergencia,"
				+ "cursos.fonecontatoemergencia, cursos.emalcontatoemergencia, cursos.relacaocontatoemergencia, cursos.datainscricao as dataInscricaCurso, cursos.idioma, cursos.nivelIdioma, cursos.possuiSeguroGovernamental, cursos.numeroMeses as numeromesesgovernamental, cursos.seguradoraGovernamental,"
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,"
				+ "usuario.nome as nomeUsuario,unidadeNegocio.nomeFantasia, orcamento.dataCambio, orcamento.valorCambio, orcamento.totalMoedaEstrangeira,"
				+ "orcamento.totalmoedanacional, orcamento.TaxaTm, orcamentoprodutosorcamento.valormoedaestrangeira, orcamentoprodutosorcamento.valormoedanacional,"
				+ "produtosorcamento.descricao as descricaoprodutosOrcamento,seguroviagem.idseguroViagem,seguroviagem.seguradora,seguroviagem.plano,seguroviagem.dataInicio as dataInicioSeguro,"
				+ "seguroviagem.dataTermino dataTerminoSeguro,seguroviagem.numeroSemanas as numeroSemanasSeguro,seguroviagem.valorSeguro,seguroviagem.possuiSeguro,"
				+ "seguroviagem.nomeContatoEmergencia,seguroviagem.paisDestino,seguroviagem.foneContatoEmergencia,seguroviagem.vendas_idvendas,seguroviagem.fornecedor_idfornecedor,orcamentoprodutosorcamento.idorcamentoprodutosorcamento"
				+ " from " + "vendas join cursos on vendas.idvendas = cursos.vendas_idvendas "
				+ "join usuario on vendas.usuario_idusuario = usuario.idusuario "
				+ "join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio "
				+ "join orcamento on vendas.idvendas = orcamento.vendas_idvendas "
				+ "join orcamentoprodutosorcamento on orcamento.idorcamento = orcamentoprodutosorcamento.orcamento_idorcamento "
				+ " join produtosorcamento on orcamentoprodutosorcamento.produtosorcamento_idprodutosorcamento = produtosorcamento.idprodutosorcamento ";
		sql = sql + sqlseguro;
		sql = sql + " where " + " vendas.idvendas = " + curso.getVendas().getIdvendas()
				+ " order by orcamentoprodutosorcamento.idorcamentoprodutosorcamento ";
		return sql;

	}

	public String gerarSqlPagina1(Voluntariado voluntariado) {
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemController.consultarSeguroCurso(voluntariado.getVendas().getIdvendas());
		String sqlseguro = "";
		if (seguro == null) {
			seguro = seguroViagemController.consultar(voluntariado.getVendas().getIdvendas());
			sqlseguro = " join seguroviagem on seguroviagem.vendas_idvendas = vendas.idvendas ";
		} else {
			sqlseguro = " join seguroviagem on seguroviagem.idvendacurso = vendas.idvendas";
		}
		String sql = "Select distinct " + "vendas.dataVenda, vendas.valor as valorVenda,vendas.idvendas,"
				+ "voluntariado.idvoluntariado,voluntariado.idiomaEstudar, voluntariado.nivelIdiomaEstudar,"
				+ "voluntariado.nomeContatoEmergencia,voluntariado.foneContatoEmergencia, voluntariado.emailContatoEmergencia,"
				+ "voluntariado.relacaoContatoEmergencia,voluntariado.instituicaoEstuda,voluntariado.cursoEstuda,"
				+ "voluntariado.duracaoCursoEstuda,voluntariado.periodoCursoEstuda,voluntariado.dataMatriculaCursoEstuda,voluntariado.dataEstimadaTerminoCursoEstuda,"
				+ "voluntariado.profissao,voluntariado.cargo,voluntariado.descricaoCargo,voluntariado.outrasHabilidade,"
				+ "voluntariado.curso,voluntariado.aulasporSemana,voluntariado.numeroSemanas,voluntariado.dataInicio,"
				+ "voluntariado.dataTermino,voluntariado.tipoAcomodacao,voluntariado.numeroSemanasAcomodacao,voluntariado.tipoQuarto,"
				+ "voluntariado.refeicoes,voluntariado.adicionais,voluntariado.dataChegadaAcomodacao,voluntariado.dataPartidaAcomodacao,"
				+ "voluntariado.familiaCrianca,voluntariado.familiaAnimais,voluntariado.fumante,voluntariado.vegetariano,"
				+ "voluntariado.hobbies,voluntariado.possuiAlergia,voluntariado.quaisAlergias,voluntariado.solicitacoesEspeciais,voluntariado.transferin,"
				+ "voluntariado.transferout,voluntariado.dataChegadaTransfer,"
				+ "voluntariado.voo,voluntariado.ciaerea,voluntariado.horario,voluntariado.projetoVoluntariado,"
				+ "voluntariado.dataInicioVoluntariado,voluntariado.dataTerminoVoluntariado,voluntariado.experienciaVoluntariado,voluntariado.motivoVoluntariado,"
				+ "voluntariado.deficienciaFisica,voluntariado.possuiProblemaSaude,voluntariado.descricaoProblemaSaude,voluntariado.tratamentoMedico,"
				+ "voluntariado.descricaoMedico,voluntariado.tratamentoDrogas,voluntariado.descricaoDrogas,"
				+ "voluntariado.fezCirurgia,voluntariado.descricaoCirurgia,voluntariado.dietaEspecifica,voluntariado.cartaoVTM,"
				+ "voluntariado.numerocartaoVTM,voluntariado.meodaCartaoVTM,voluntariado.seguroViagem,voluntariado.seguradora,voluntariado.planoSeguro,"
				+ "voluntariado.dataInicioSeguro,voluntariado.dataTerminoSeguro,voluntariado.numeroSemanasSeguro,voluntariado.passagemAerea,"
				+ "voluntariado.observacaoPassagem,voluntariado.vistoConsular,voluntariado.observacaoVistoConsultar,voluntariado.vendas_idvendas,"
				+ "voluntariado.dataEntregaDocumentoVisto,voluntariado.nivelFitness,voluntariado.controle, "
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,"
				+ "cliente.nome, cliente.dataNascimento, cliente.paisnascimento, cliente.cidadenascimento, cliente.rg,"
				+ "cliente.sexo, cliente.numeropassaporte, cliente.dataExpedicaoPassaporte,"
				+ "cliente.validadePassaporte, cliente.tipologradouro as clientetipologradouro, cliente.logradouro as clientelogradouro, cliente.numero as clientenumero,"
				+ "cliente.bairro as clientebairro, cliente.cidade as clientecidade, cliente.estado as clienteestado, cliente.cep as clientecep, cliente.cpf as clientecpf, cliente.pais as clientepais, cliente.foneresidencial,"
				+ "cliente.fonecelular, cliente.fonecomercial, cliente.profissao,"
				+ "cliente.email as emailcliente,cliente.escolaridade, cliente.nomePai, cliente.profissaopai, cliente.fonepai, cliente.nomemae,"
				+ "cliente.profissaomae, cliente.fonemae,"
				+ "usuario.nome as nomeUsuario, usuario.email as usuarioemail,"
				+ "unidadeNegocio.nomeFantasia, orcamento.dataCambio, orcamento.valorCambio, orcamento.totalMoedaEstrangeira,"
				+ "orcamento.totalmoedanacional, orcamento.TaxaTm, formapagamento.condicao, formapagamento.valortotal,"
				+ "formapagamento.valorjuros, orcamentoprodutosorcamento.valormoedaestrangeira, orcamentoprodutosorcamento.valormoedanacional,"
				+ "produtosorcamento.descricao as descricaoprodutosOrcamento, moedas.descricao as descricaoMoedas, cambio.idcambio, moedas.sigla,parcelamentopagamento.idparcemlamentoPagamento,"
				+ "parcelamentopagamento.formaPagamento," + "parcelamentopagamento.tipoParcelmaneto,"
				+ "parcelamentopagamento.valorParcelamento," + "parcelamentopagamento.diaVencimento,"
				+ "parcelamentopagamento.numeroParcelas," + "parcelamentopagamento.valorParcela,"
				+ "parcelamentopagamento.formaPagamento_idformaPagamento,orcamentoprodutosorcamento.idorcamentoprodutosorcamento,"
				+ "fornecedor.idfornecedor,seguroviagem.idseguroViagem,seguroviagem.seguradora,"
				+ "seguroviagem.plano,seguroviagem.dataInicio as dataInicioSeguro,"
				+ "seguroviagem.dataTermino dataTerminoSeguro,seguroviagem.numeroSemanas as numeroSemanasSeguro,"
				+ "seguroviagem.valorSeguro,seguroviagem.possuiSeguro,"
				+ "seguroviagem.nomeContatoEmergencia,seguroviagem.paisDestino,seguroviagem.foneContatoEmergencia,"
				+ "seguroviagem.vendas_idvendas,seguroviagem.fornecedor_idfornecedor, "
				+ "fornecedor.nome as nomeFornecedor, cidade.nome as nomeCidade, pais.nome nomePais" + " from "
				+ "  vendas join voluntariado on vendas.idvendas = voluntariado.vendas_idvendas"
				+ "  join usuario on vendas.usuario_idusuario = usuario.idusuario"
				+ "  join cliente on vendas.cliente_idcliente = cliente.idcliente"
				+ "  join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio"
				+ " join orcamento on vendas.idvendas = orcamento.vendas_idvendas"
				+ "  join orcamentoprodutosorcamento on orcamento.idorcamento = orcamentoprodutosorcamento.orcamento_idorcamento"
				+ " join produtosorcamento on orcamentoprodutosorcamento.produtosorcamento_idprodutosorcamento = produtosorcamento.idprodutosorcamento"
				+ " join formaPagamento on vendas.idvendas = formapagamento.vendas_idvendas"
				+ " join cambio on orcamento.cambio_idcambio = cambio.idcambio"
				+ "  join moedas on cambio.moedas_idmoedas = moedas.idmoedas"
				+ " join parcelamentopagamento on formapagamento.idformapagamento = parcelamentopagamento.formapagamento_idformaPagamento"
				+ " join fornecedorCidade on vendas.fornecedorcidade_idfornecedorcidade = fornecedorcidade.idfornecedorcidade"
				+ "   join fornecedor on fornecedorcidade.fornecedor_idfornecedor = fornecedor.idfornecedor"
				+ "  join cidade on fornecedorcidade.cidade_idcidade = cidade.idcidade"
				+ "  join pais on cidade.pais_idpais = pais.idpais";
		sql = sql + sqlseguro;
		sql = sql + " where " + "vendas.idvendas =" + voluntariado.getVendas().getIdvendas()
				+ " order by orcamentoprodutosorcamento.idorcamentoprodutosorcamento";
		return sql;
	}
	
	
	public void retornarIconeFicha(){
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA")) {
			iconeFicha = "finalizadoFicha.png";
		}else if (venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			iconeFicha = "amarelaFicha.png";
		}else if(venda.getSituacao().equalsIgnoreCase("PROCESSO")){
			iconeFicha = "processoFicha.png";
		}else{
			iconeFicha = "fichaCancelada.png";
		}
	}
	
	
	public void excluir(Crmcobrancahistorico crmcobrancahistorico){
		int idUsuarioLogado = usuarioLogadoMB.getUsuario().getIdusuario();
		if (idUsuarioLogado == crmcobrancahistorico.getUsuario().getIdusuario()) {
			listaCobrancaHistorico.remove(crmcobrancahistorico);
			CrmCobrancaHistoricoFacade cobrancaHistoricoFacade = new CrmCobrancaHistoricoFacade();
			cobrancaHistoricoFacade.excluir(crmcobrancahistorico.getIdcrmcobrancahistorico());
			gerarListaHistorico();
			Mensagem.lancarMensagemInfo("Histórico excluido com sucesso.", "");
		}else{
			Mensagem.lancarMensagemInfo("Atenção", "Operação negada");
		}
			
	}
	
	
	public void editar(Crmcobrancahistorico crmcobrancahistorico){ 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("crmcobrancahistorico", crmcobrancahistorico);
		session.setAttribute("crmcobranca", crmcobranca);
		session.setAttribute("venda", venda);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 350);
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("cadHistoricoCobrancaCliente", options, null);
	}
	
	
	public void salvarNotas() {
		CrmCobrancaFacade crmCobrancaFacade = new CrmCobrancaFacade();
		crmcobranca.setNota(nota);
		crmcobranca = crmCobrancaFacade.salvar(crmcobranca); 
		Mensagem.lancarMensagemInfo("Salvo com sucesso.", "");
	}
	
	
	public void verificarCobranca(){
		if (crmcobranca == null || crmcobranca.getIdcrmcobranca() == null) {
			desabilitarCampos = true;
			habilitarPrioridade = false;
		}
	}
	
	
	public void retornoDialogContas(SelectEvent event){
		gerarListaHistorico();
	}
	
	
	public String verificarTelefone(){
		if (venda.getCliente().getFoneCelular() != null  && venda.getCliente().getFoneCelular().length() > 0) {
			return venda.getCliente().getFoneCelular();
		}
		return venda.getCliente().getFoneResidencial();
	}
	

}
