package br.com.travelmate.managerBean.higherEducation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import br.com.travelmate.bean.ListaHeBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class FichaFinalMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private List<He> listaHe;
	private int idvenda;
	private String nomeCliente = "";
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private boolean habilitarUnidade = true;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;
	private List<He> listaProcesso;
	private List<He> listaFinanceiro;
	private List<He> listaAndamento;
	private List<He> listaFinalizar;
	private List<He> listaCancelada;
	private Integer nFichasFinalizada;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private Integer nFichaFinanceiro;
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";
	private String obsTM = "";

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaProcesso = (List<He>) session.getAttribute("listaProcesso");
		listaFinanceiro = (List<He>) session.getAttribute("listaFinanceiro");
		listaAndamento = (List<He>) session.getAttribute("listaAndamento");
		listaFinalizar = (List<He>) session.getAttribute("listaFinalizar");
		listaCancelada = (List<He>) session.getAttribute("listaCancelada");
		nomePrograma = (String) session.getAttribute("nomePrograma");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		listaHe = (List<He>) session.getAttribute("listaHe");
		session.removeAttribute("listaAndamento");
		session.removeAttribute("listaFinalizar");
		session.removeAttribute("listaProcesso");
		session.removeAttribute("listaFinanceiro");
		session.removeAttribute("listaCancelada");
		session.removeAttribute("pesquisar");
		session.removeAttribute("nomePrograma");
		session.removeAttribute("chamadaTela");
		session.removeAttribute("listaHe");
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("He")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			listaUnidade = GerarListas.listarUnidade();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				gerarListaHe();
			}else {
				gerarQuantidadesFichas();
			}
		}
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	

	public Integer getnFichasFinalizada() {
		return nFichasFinalizada;
	}

	public void setnFichasFinalizada(Integer nFichasFinalizada) {
		this.nFichasFinalizada = nFichasFinalizada;
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

	public Integer getnFichaFinanceiro() {
		return nFichaFinanceiro;
	}

	public void setnFichaFinanceiro(Integer nFichaFinanceiro) {
		this.nFichaFinanceiro = nFichaFinanceiro;
	}

	

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
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

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
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

	public String novoQuestionario() {
		return "questionarioHe";
	}

	public List<He> getListaHe() {
		return listaHe;
	}

	public void setListaHe(List<He> listaHe) {
		this.listaHe = listaHe;
	}

	public List<He> getListaProcesso() {
		return listaProcesso;
	}

	public void setListaProcesso(List<He> listaProcesso) {
		this.listaProcesso = listaProcesso;
	}

	public List<He> getListaFinanceiro() {
		return listaFinanceiro;
	}

	public void setListaFinanceiro(List<He> listaFinanceiro) {
		this.listaFinanceiro = listaFinanceiro;
	}

	public List<He> getListaAndamento() {
		return listaAndamento;
	}

	public void setListaAndamento(List<He> listaAndamento) {
		this.listaAndamento = listaAndamento;
	}

	public List<He> getListaFinalizar() {
		return listaFinalizar;
	}

	public void setListaFinalizar(List<He> listaFinalizar) {
		this.listaFinalizar = listaFinalizar;
	}

	public List<He> getListaCancelada() {
		return listaCancelada;
	}

	public void setListaCancelada(List<He> listaCancelada) {
		this.listaCancelada = listaCancelada;
	}

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}

	public String editar(He he) {
		if (he != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("he", he);
			return "cadFichaHe2";
		}  else
			return "";
	}

	public void gerarListaHe() {
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()); 
		Calendar c = new GregorianCalendar(ano, mes, 1); 
		Date data = c.getTime();
		String dataConsulta = Formatacao.ConvercaoDataSql(data);
		
		// ficha inscricao
		String sqlFicha2 = "Select h From He h where h.vendas.dataVenda>='" + dataConsulta + "' and h.fichafinal=TRUE";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sqlFicha2 = sqlFicha2 + " and h.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sqlFicha2 = sqlFicha2 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sqlFicha2 = sqlFicha2 + " order by h.vendas.dataVenda desc";
		HeFacade heFacade = new HeFacade();
		listaHe = heFacade.listar(sqlFicha2);
		if (listaHe == null) {
			listaHe = new ArrayList<He>();
		}
		gerarQuantidadesFichas();
	}

	public String corNome(He he) {
		if (he != null) {
			if (he.getStatus().equals("CANCELADA")) {
				return "color:#808080;text-decoration: line-through;";
			}
		}
		return "color:#000000;";
	}

	public String cor(He he) {
		if (he != null) {
			if (he.getStatus().equals("CANCELADA")) {
				return "color:#808080;";
			}
		}
		return "color:#000000;";
	}

	public boolean habilitarBtnEditar(He he) {
		if (he != null) {
			if (!he.getStatus().equals("CANCELADA")) {
				return false;
			}
		}
		return true;
	}

	public void limpar() {
		dataInicio = null;
		dataTermino = null;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			unidadenegocio = null;
		} else {
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
		}
		nomeCliente = "";
		idvenda = 0;
		situacao = "sn";
		pesquisar = "Nao";
		gerarListaHe();
	}

	public void pesquisar() {
		// ficha final
			String sqlFicha2 = "Select h From He h where h.vendas.cliente.nome like '" + nomeCliente + "%'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
				if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
					if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
						sqlFicha2 = sqlFicha2 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio();
			}
			if (dataInicio != null && dataTermino != null) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio)
						+ "' and h.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
			} 
			if (idvenda > 0) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.idvendas=" + idvenda;
			}
			sqlFicha2 = sqlFicha2 + " and h.fichafinal=TRUE order by h.vendas.dataVenda desc";
			HeFacade heFacade = new HeFacade();
			listaHe = heFacade.listar(sqlFicha2);
			if (listaHe == null) {
				listaHe = new ArrayList<He>();
			}
		
		pesquisar = "Sim";
		gerarQuantidadesFichas();
	}

	

	public String retornarimagem(String status) {
		if (status.equalsIgnoreCase("FINALIZADO")) {
			return "../../resources/img/finalizadoFicha.png";
		}else if (status.equalsIgnoreCase("FINALIZADA")) {
			return "../../resources/img/finalizadoFicha.png";
		} else if (status.equalsIgnoreCase("ANDAMENTO")) {
			return "../../resources/img/amarelaFicha.png";
		} else if (status.equalsIgnoreCase("CANCELADA")) {
			return "../../resources/img/fichaCancelada.png";
		} else if(status.equalsIgnoreCase("FINANCEIRO")) {
			return "../../resources/img/ficharestricao.png";
		} else {
			return "../../resources/img/processoFicha.png";   
		}
	}
	
	public String retornarTituloFicha(String status) {
		if (status.equalsIgnoreCase("FINALIZADO")) {
			return "FICHA FINALIZADA";
		}else if (status.equalsIgnoreCase("FINALIZADA")) {
			return "FICHA FINALIZADA";
		} else if (status.equalsIgnoreCase("ANDAMENTO")) {
			return "ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)";
		} else if (status.equalsIgnoreCase("CANCELADA")) {
			return "FICHA CANCELADA";
		} else if(status.equalsIgnoreCase("FINANCEIRO")) {
			return "FINANCEIRO (FICHA EM ANÁLISE FINANCEIRA)";
		} else {
			return "PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)";   
		}
	}

	public String gerarRelatorioFicha(He he) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/higherEducation/FichaFinalHe1.jasper";
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
		parameters.put("idvendas", he.getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"Ficha de Inscrição-" + he.getVendas().getCliente().getNome() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioContrato(He he) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/higherEducation/contratoHePagina01.jasper";
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
		parameters.put("idvendas", he.getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"Contrato de Higher Education-" + he.getVendas().getIdvendas() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	public String fichaHE(He he){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("he", he);
		session.setAttribute("listaAndamento", listaAndamento);
		session.setAttribute("listaCancelada", listaCancelada);
		session.setAttribute("listaFinalizar", listaFinalizar);
		session.setAttribute("listaFinanceiro", listaFinanceiro);
		session.setAttribute("listaProcesso", listaProcesso);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "He");
		session.setAttribute("chamadaTela", "He");
		session.setAttribute("listaHe", listaHe);
		return "fichaHE";
	}
	
	public String contrato(He he){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("he", he);
		session.setAttribute("listaAndamento", listaAndamento);
		session.setAttribute("listaCancelada", listaCancelada);
		session.setAttribute("listaFinalizar", listaFinalizar);
		session.setAttribute("listaFinanceiro", listaFinanceiro);
		session.setAttribute("listaProcesso", listaProcesso);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "He");
		session.setAttribute("chamadaTela", "He");
		session.setAttribute("listaHe", listaHe);
		return "contratoHeFinal";
	}
	
//	public String contrato(ListaHeBean listaHeBean){
//		LerArquivoTxt lerArquivoTxt = new LerArquivoTxt(listaHeBean.getHe().getVendas1(), "HE");
//		try {
//			String texto = lerArquivoTxt.ler();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect("http://systm.com.br:82/ftproot/systm/arquivos/Contrato" + listaHeBean.getHe().getVendas1().getUnidadenegocio().getIdunidadeNegocio() + 
//					listaHeBean.getHe().getVendas1().getUsuario().getIdusuario() + listaHeBean.getHe().getVendas1().getIdvendas() + ".html");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
	
	public String documentacao(He he) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("vendas", he.getVendas());
		session.setAttribute("cliente", he.getVendas().getCliente());
		session.setAttribute("pesquisar", "Sim");
		session.setAttribute("nomePrograma", "He");
		session.setAttribute("listaAndamento", listaAndamento);
		session.setAttribute("listaCancelada", listaCancelada);
		session.setAttribute("listaFinalizar", listaFinalizar);
		session.setAttribute("listaFinanceiro", listaFinanceiro);
		session.setAttribute("listaProcesso", listaProcesso);
		session.setAttribute("chamadaTela", "fichaHE");
		session.setAttribute("listaHe", listaHe);
		String voltar = "consHeFichaFinal";
		session.setAttribute("voltar", voltar);
		return "consArquivos"; 
	}
	
	public String informacoes(He he) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", he.getVendas());
		String voltar = "consHeFichaFinal";
		session.setAttribute("voltar", voltar);
		return "consLogVenda"; 
	}
	
//	public String cancelarVenda(ListaHeBean listaHeBean) {
//		if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
//				|| listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
//			Map<String, Object> options = new HashMap<String, Object>();
//			options.put("contentWidth", 400);
//			FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("venda", listaHeBean.getQuestionariohe().getVendas());
//			if(listaHeBean.getHe()!=null && listaHeBean.getHe().getVendas1()!=null){
//				session.setAttribute("venda1", listaHeBean.getHe().getVendas1());
//			}
//			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
//		} else if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
//			VendasFacade vendasFacade = new VendasFacade();
//			listaHeBean.getQuestionariohe().getVendas().setSituacao("CANCELADA");
//			vendasFacade.salvar(listaHeBean.getQuestionariohe().getVendas());
//			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
//			listaHeBean.getQuestionariohe().setSituacao("CANCELADA");
//			listaHeBean.setQuestionariohe(questionarioHeFacade.salvar(listaHeBean.getQuestionariohe()));
//			gerarListaHe();
//		}
//		return "";
//	}
	
	public String boletos(He he) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(he.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r where r.vendas.idvendas=";
			sql = sql + he.getVendas().getIdvendas();
			sql = sql + " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
						gerarBoletoConsultorBean.gerarBoleto(listaContas,
								String.valueOf(he.getVendas().getIdvendas()));
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
	
	
	public String visualizarContasReceber(He he) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", he.getVendas());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
	
	public String notificarEfetuarFichaCrm(){
		return "followUp";
	}
	
	public void gerarQuantidadesFichas(){
		nFichaCancelada = 0;
		nFichasAndamento = 0;
		nFichasFinalizada = 0; 
		nFichaFinanceiro = 0;
		nFichasProcesso = 0;
		listaFinalizar = new ArrayList<He>();
		listaAndamento = new ArrayList<He>();
		listaProcesso = new ArrayList<He>(); 
		listaFinanceiro = new ArrayList<He>();
		listaCancelada = new ArrayList<He>();
		if (listaHe == null) {
			listaHe = new ArrayList<He>();
		}
		for (int i = 0; i < listaHe.size(); i++) {
			listaHe.get(i).setSituacao("Ficha Final");
			if (listaHe.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")
					&& !listaHe.get(i).isDesistencia()) {
				nFichasProcesso = nFichasProcesso + 1;
				listaHe.get(i).setStatus("PROCESSO");
				listaProcesso.add(listaHe.get(i));
			} else if (listaHe.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
					&& !listaHe.get(i).isDesistencia()) {
				nFichasFinalizada = nFichasFinalizada + 1;
				listaHe.get(i).setStatus("FINALIZADA");
				listaFinalizar.add(listaHe.get(i));
			} else if (listaHe.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
					&& !listaHe.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")
					&& !listaHe.get(i).isDesistencia()) {
				nFichaFinanceiro = nFichaFinanceiro + 1;
				listaHe.get(i).setStatus("FINANCEIRO");
				listaFinanceiro.add(listaHe.get(i));
			} else if (listaHe.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
					&& !listaHe.get(i).isDesistencia()) {
				nFichasAndamento = nFichasAndamento + 1;
				listaHe.get(i).setStatus("ANDAMENTO");
				listaAndamento.add(listaHe.get(i));
			} else {
				nFichaCancelada = nFichaCancelada + 1;
				listaHe.get(i).setStatus("CANCELADA");
				listaCancelada.add(listaHe.get(i));
			}
		}
	}
	
	public void desistenciaHE(He he) {
		if (he != null) {
			he.setDesistencia(true);
			he.setStatus("CANCELADA");
			HeFacade heFacade = new HeFacade();
			he = heFacade.salvar(he);
			Mensagem.lancarMensagemInfo("Ficha Final Cancelada!", "");
		} 
	}
	
	public boolean desabilitarBtnDesistencia(He he) {
		if (he != null) {
			if (he.isDesistencia()) {
				return true;
			} else {
				return false;
			}
		} else return true;
	}
	
	
	public String cancelarVenda(He he) {
	//	if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
	//			|| listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", he.getVendas());
			session.setAttribute("voltar", "consHeFichaFinal");
			return "emissaocancelamento";
	//	}  else if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("PROCESSO") 
		//		|| listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("CANCELADA")) {
	//		VendasFacade vendasFacade = new VendasFacade();
	//		listaHeBean.getQuestionariohe().getVendas().setSituacao("CANCELADA");
		//	vendasFacade.salvar(listaHeBean.getQuestionariohe().getVendas());
	//		QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
		//	listaHeBean.getQuestionariohe().setSituacao("CANCELADA");
		//	listaHeBean.setQuestionariohe(questionarioHeFacade.salvar(listaHeBean.getQuestionariohe()));
		//	gerarListaHe();
	//	}
	//	return "";
	}  
	
	public String obsTM(He he) {
		obsTM = he.getVendas().getObstm();
		return obsTM;
	}
	
	
	public String retornarIconeObsTM(He he){
		if (he.getVendas().getObstm() != null && he.getVendas().getObstm().length() > 0) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}
	

}
