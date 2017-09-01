package br.com.travelmate.managerBean.cloud.midia;

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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.Arquivo1Facade;
import br.com.travelmate.facade.Arquivo4Facade;
import br.com.travelmate.facade.Arquivo5Facade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.Pasta5Facade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Pasta4;
import br.com.travelmate.model.Pasta5;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class Pasta5Arquivo4MB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Arquivo1 arquivo1;
	private Pasta1 pasta1;
	private List<Arquivo1> listaArquivo1;
	private Departamento departamento;
	private List<Pasta2> listaPasta2;
	private Pasta2 pasta2;
	private List<Arquivo2> listaArquivo2;
	private Arquivo2 arquivo2;
	private Pasta3 pasta3;
	private Arquivo3 arquivo3;
	private List<Pasta3> listaPasta3;
	private List<Arquivo3> listaArquivo3;
	private List<Arquivo4Bean> listaArquivo4Bean;
	private Arquivo4Bean arquivo4Bean;
	private boolean semArquivos = false;
	private Ftpdados ftpDados;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Pasta4 pasta4;
	private List<Pasta4> listaPasta4;
	private List<Arquivo4> listaArquivo4;
	private List<Arquivo5> listaArquivo5;
	private List<Pasta5> listaPasta5;
	private boolean tabelaArquivoQuadro = true;
	private boolean tabelaArquivoLista = false;
	private boolean pesquisar;
	private String tituloPagina;
	private ArquivosVencidosBean arquivosVencidosBean;
	private List<ArquivosVencidosBean> listaArquivoVencidoBean;
	private String tipoArquivo;
	private String nomeArquivo;
	private ArquivoBean arquivosBean;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			pasta4 = (Pasta4) session.getAttribute("pasta4");
			session.removeAttribute("pasta4");
			if (pasta4 != null) {
				pasta3 = pasta4.getPasta3();
				pasta2 = pasta4.getPasta2();
				pasta1 = pasta4.getPasta1();
				departamento = pasta1.getDepartamento();
			}
			if (departamento != null && pasta1 != null && pasta2 != null && pasta3 != null && pasta4 != null) {
				gerarPasta5();
				gerarListaArquivo4();
			} else {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ftpDados = new Ftpdados();
			FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
			try {
				ftpDados = ftpDadosFacade.getFTPDados();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// Verificar se contém arquivos na tela
			semConteudo();
			verificarExibicao();
		}
	}

	public List<Arquivo4> getListaArquivo4() {
		return listaArquivo4;
	}

	public void setListaArquivo4(List<Arquivo4> listaArquivo4) {
		this.listaArquivo4 = listaArquivo4;
	}

	public Ftpdados getFtpDados() {
		return ftpDados;
	}

	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}

	public boolean isSemArquivos() {
		return semArquivos;
	}

	public void setSemArquivos(boolean semArquivos) {
		this.semArquivos = semArquivos;
	}

	public List<Arquivo4Bean> getListaArquivo4Bean() {
		return listaArquivo4Bean;
	}

	public void setListaArquivo4Bean(List<Arquivo4Bean> listaArquivo4Bean) {
		this.listaArquivo4Bean = listaArquivo4Bean;
	}

	public Arquivo4Bean getArquivo4Bean() {
		return arquivo4Bean;
	}

	public void setArquivo4Bean(Arquivo4Bean arquivo4Bean) {
		this.arquivo4Bean = arquivo4Bean;
	}

	/**
	 * @return the arquivo1
	 */
	public Arquivo1 getArquivo1() {
		return arquivo1;
	}

	/**
	 * @param arquivo1
	 *            the arquivo1 to set
	 */
	public void setArquivo1(Arquivo1 arquivo1) {
		this.arquivo1 = arquivo1;
	}

	/**
	 * @return the pasta1
	 */
	public Pasta1 getPasta1() {
		return pasta1;
	}

	/**
	 * @param pasta1
	 *            the pasta1 to set
	 */
	public void setPasta1(Pasta1 pasta1) {
		this.pasta1 = pasta1;
	}

	/**
	 * @return the listaArquivo1
	 */
	public List<Arquivo1> getListaArquivo1() {
		return listaArquivo1;
	}

	/**
	 * @param listaArquivo1
	 *            the listaArquivo1 to set
	 */
	public void setListaArquivo1(List<Arquivo1> listaArquivo1) {
		this.listaArquivo1 = listaArquivo1;
	}

	/**
	 * @return the listaPasta2
	 */
	public List<Pasta2> getListaPasta2() {
		return listaPasta2;
	}

	/**
	 * @param listaPasta2
	 *            the listaPasta2 to set
	 */
	public void setListaPasta2(List<Pasta2> listaPasta2) {
		this.listaPasta2 = listaPasta2;
	}

	/**
	 * @return the pasta2
	 */
	public Pasta2 getPasta2() {
		return pasta2;
	}

	/**
	 * @param pasta2
	 *            the pasta2 to set
	 */
	public void setPasta2(Pasta2 pasta2) {
		this.pasta2 = pasta2;
	}

	/**
	 * @return the listaArquivo2
	 */
	public List<Arquivo2> getListaArquivo2() {
		return listaArquivo2;
	}

	/**
	 * @param listaArquivo2
	 *            the listaArquivo2 to set
	 */
	public void setListaArquivo2(List<Arquivo2> listaArquivo2) {
		this.listaArquivo2 = listaArquivo2;
	}

	/**
	 * @return the arquivo2
	 */
	public Arquivo2 getArquivo2() {
		return arquivo2;
	}

	/**
	 * @param arquivo2
	 *            the arquivo2 to set
	 */
	public void setArquivo2(Arquivo2 arquivo2) {
		this.arquivo2 = arquivo2;
	}

	/**
	 * @return the pasta3
	 */
	public Pasta3 getPasta3() {
		return pasta3;
	}

	/**
	 * @param pasta3
	 *            the pasta3 to set
	 */
	public void setPasta3(Pasta3 pasta3) {
		this.pasta3 = pasta3;
	}

	/**
	 * @return the arquivo3
	 */
	public Arquivo3 getArquivo3() {
		return arquivo3;
	}

	/**
	 * @param arquivo3
	 *            the arquivo3 to set
	 */
	public void setArquivo3(Arquivo3 arquivo3) {
		this.arquivo3 = arquivo3;
	}

	/**
	 * @return the listaPasta3
	 */
	public List<Pasta3> getListaPasta3() {
		return listaPasta3;
	}

	/**
	 * @param listaPasta3
	 *            the listaPasta3 to set
	 */
	public void setListaPasta3(List<Pasta3> listaPasta3) {
		this.listaPasta3 = listaPasta3;
	}

	/**
	 * @return the listaArquivo3
	 */
	public List<Arquivo3> getListaArquivo3() {
		return listaArquivo3;
	}

	/**
	 * @param listaArquivo3
	 *            the listaArquivo3 to set
	 */
	public void setListaArquivo3(List<Arquivo3> listaArquivo3) {
		this.listaArquivo3 = listaArquivo3;
	}

	/**
	 * @return the usuarioLogadoMB
	 */
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	/**
	 * @param usuarioLogadoMB
	 *            the usuarioLogadoMB to set
	 */
	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Pasta4 getPasta4() {
		return pasta4;
	}

	public void setPasta4(Pasta4 pasta4) {
		this.pasta4 = pasta4;
	}

	public List<Pasta4> getListaPasta4() {
		return listaPasta4;
	}

	public void setListaPasta4(List<Pasta4> listaPasta4) {
		this.listaPasta4 = listaPasta4;
	}

	public List<Arquivo5> getListaArquivo5() {
		return listaArquivo5;
	}

	public void setListaArquivo5(List<Arquivo5> listaArquivo5) {
		this.listaArquivo5 = listaArquivo5;
	}

	public List<Pasta5> getListaPasta5() {
		return listaPasta5;
	}

	public void setListaPasta5(List<Pasta5> listaPasta5) {
		this.listaPasta5 = listaPasta5;
	}

	public boolean isTabelaArquivoQuadro() {
		return tabelaArquivoQuadro;
	}

	public void setTabelaArquivoQuadro(boolean tabelaArquivoQuadro) {
		this.tabelaArquivoQuadro = tabelaArquivoQuadro;
	}

	public boolean isTabelaArquivoLista() {
		return tabelaArquivoLista;
	}

	public void setTabelaArquivoLista(boolean tabelaArquivoLista) {
		this.tabelaArquivoLista = tabelaArquivoLista;
	}

	public boolean isPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(boolean pesquisar) {
		this.pesquisar = pesquisar;
	}

	public String getTituloPagina() {
		return tituloPagina;
	}

	public void setTituloPagina(String tituloPagina) {
		this.tituloPagina = tituloPagina;
	}

	public ArquivosVencidosBean getArquivosVencidosBean() {
		return arquivosVencidosBean;
	}

	public void setArquivosVencidosBean(ArquivosVencidosBean arquivosVencidosBean) {
		this.arquivosVencidosBean = arquivosVencidosBean;
	}

	public List<ArquivosVencidosBean> getListaArquivoVencidoBean() {
		return listaArquivoVencidoBean;
	}

	public void setListaArquivoVencidoBean(List<ArquivosVencidosBean> listaArquivoVencidoBean) {
		this.listaArquivoVencidoBean = listaArquivoVencidoBean;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String voltarConsPasta1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "consPasta1";
	}

	public String voltarConsPasta2Arquivo1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta1", pasta1);
		return "consPasta2Arquivo1";
	}

	public String voltarConsSubPasta3Arquivo2() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta2", pasta2);
		return "consPasta3Arquivo2";
	}

	public String voltarConsSubPasta4Arquivo3() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta3", pasta3);
		return "consPasta4Arquivo3";
	}

	public String voltarConsDepartamento() {
		return "consDepartamentos";
	}

	public void gerarListaArquivo4() {
		Arquivo4Facade arquivo4Facade = new Arquivo4Facade();
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			int iddepartamento = departamento.getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == iddepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select c from Arquivo4 c where c.pasta4.idpasta4=" + pasta4.getIdpasta4();
			} else {
				sql = "Select c from Arquivo4 c where c.pasta4.idpasta4=" + pasta4.getIdpasta4()
						+ " and c.datainicio<='" + Formatacao.ConvercaoDataSql(new Date()) + "' and (c.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or c.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			sql = sql + " order by c.nome";
			listaArquivo4 = arquivo4Facade.listar(sql);
			if (listaArquivo4 == null) {
				listaArquivo4 = new ArrayList<Arquivo4>();
			} else {
				listaArquivo4Bean = new ArrayList<Arquivo4Bean>();
			}
			for (int i = 0; i < listaArquivo4.size(); i++) {
				arquivo4Bean = new Arquivo4Bean();
				arquivo4Bean.setArquivo1(listaArquivo4.get(i));
				if ((i + 1) < listaArquivo4.size()) {
					arquivo4Bean.setArquivo2(listaArquivo4.get(i + 1));
					i++;
					if ((i + 1) < listaArquivo4.size()) {
						arquivo4Bean.setArquivo3(listaArquivo4.get(i + 1));
						i++;
					} else {
						arquivo4Bean.setArquivo3(null);
					}

				} else {
					arquivo4Bean.setArquivo2(null);
					arquivo4Bean.setArquivo3(null);
				}

				listaArquivo4Bean.add(arquivo4Bean);
			}
		}
	}
	
	
	public String retornaIconeArquivo(String nomeArquivo){
		if (nomeArquivo.equalsIgnoreCase("jpg") || nomeArquivo.equalsIgnoreCase("jpeg")
				|| nomeArquivo.equalsIgnoreCase("png")) {
			return "../../resources/img/icone_jpg.png";
		} else if (nomeArquivo.equalsIgnoreCase("pdf")) {
			return "../../resources/img/icone_pdf.png";
		} else if (nomeArquivo.equalsIgnoreCase("docx") || nomeArquivo.equalsIgnoreCase("doc")) {
			return "../../resources/img/icone_docx.png";
		} else if (nomeArquivo.equalsIgnoreCase("xls") || nomeArquivo.equalsIgnoreCase("xlsx")) {
			return "../../resources/img/icone_xls.png";
		} else if (nomeArquivo.equalsIgnoreCase("txt")) {
			return "../../resources/img/icone_txt.png";
		} else if (nomeArquivo.equalsIgnoreCase("cdr")) {
			return "../../resources/img/icone_cdr.png";
		} else if (nomeArquivo.equalsIgnoreCase("eps")) {
			return "../../resources/img/icone_eps.png";
		} else if (nomeArquivo.equalsIgnoreCase("bmp")) {
			return "../../resources/img/icone_bmp.png";
		} else if (nomeArquivo.equalsIgnoreCase("pptx")) {
			return "../../resources/img/icone_pptx.png";
		} else if (nomeArquivo.equalsIgnoreCase("wma")) {
			return "../../resources/img/iconewma.png";
		}else if(nomeArquivo.equalsIgnoreCase("ppsx")){
			return "../../resources/img/icone_ppsx.png";
		}else if(nomeArquivo.equalsIgnoreCase("gif")){
			return "../../resources/img/iconegif.png";
		} else{
			return "../../resources/img/iconeDefault.png";
		}
	}

	public String cadastroArquivo4() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta4", pasta4);
		RequestContext.getCurrentInstance().openDialog("cadArquivo4", options, null);
		return "";
	}

	public void retornoDialogNovoArquivo(SelectEvent event) {
		List<GerarAvisosDocsBean> listaAviso = (List<GerarAvisosDocsBean>) event.getObject();
		if (listaAviso.size() > 0) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			AvisoArquivoBean avisoArquivoBean = new AvisoArquivoBean(listaAviso);
		}
		gerarListaArquivo4();
		semConteudo();
	}

	// Verificar se contém arquivos na tela
	public void semConteudo() {
		if ((listaArquivo4 == null || listaArquivo4.isEmpty()) && (listaPasta5 == null || listaPasta5.isEmpty())) {
			semArquivos = true;
		} else {
			semArquivos = false;
		}
	}

	public boolean excluirArquivoFTP(Arquivo4 arquivo4) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = arquivo4.getNomeftp();
			msg = ftp.excluirArquivo(nomeArquivoFTP, "/cloud/departamentos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		return false;
	}

	public boolean excluirArquivo5FTP(Arquivo5 arquivo5) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHost(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = arquivo5.getNomeftp();
			msg = ftp.excluirArquivo(nomeArquivoFTP, "/cloud/departamentos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String excluirArquivo4(Arquivo4 arquivo4) {
		excluirArquivoFTP(arquivo4);
		Arquivo4Facade arquivo4Facade = new Arquivo4Facade();
		arquivo4Facade.excluir(arquivo4.getIdarquivo4());
		gerarListaArquivo4();
		return "";
	}

	public String alterarNomeArquivo(Arquivo4 arquivo4) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("arquivo4", arquivo4);
		RequestContext.getCurrentInstance().openDialog("editarArquivo4", options, null);
		return "";
	}

	public void retornoDialogAlteracaoNomeArquivo(SelectEvent event) {
		Arquivo4 arquivo4 = (Arquivo4) event.getObject();
		if (arquivo4.getIdarquivo4() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarListaArquivo4();
		semConteudo();
	}

	public boolean verificarAcesso() {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			if (departamento != null) {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
			}
		}
		return acesso;
	}

	public boolean verificarArquivo1(Arquivo4Bean arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (arquivo.getArquivo1() == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}

	public boolean verificarArquivo2(Arquivo4Bean arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (arquivo.getArquivo2() == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}

	public boolean verificarArquivo3(Arquivo4Bean arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (arquivo.getArquivo3() == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}

	// public boolean verificarArquivo4(Arquivo4Bean arquivo) {
	// Boolean acesso = false;
	// if (arquivo.getArquivo4() == null) {
	// return acesso;
	// } else {
	// if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() ==
	// departamento
	// .getIddepartamento()) {
	// acesso = true;
	// } else if
	// (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9)
	// {
	// acesso = true;
	// if (departamento.getIddepartamento() == 1) {
	// acesso = false;
	// }
	// }
	// return acesso;
	// }
	// }

	public String posicaoInteracaoUiRepeat(String posicao) {
		String classe = "";
		if (posicao.equalsIgnoreCase("0") || posicao.equalsIgnoreCase("4") || posicao.equalsIgnoreCase("8")
				|| posicao.equalsIgnoreCase("12") || posicao.equalsIgnoreCase("16") || posicao.equalsIgnoreCase("20")
				|| posicao.equalsIgnoreCase("24") || posicao.equalsIgnoreCase("28") || posicao.equalsIgnoreCase("32")
				|| posicao.equalsIgnoreCase("36") || posicao.equalsIgnoreCase("40")) {
			classe = "panel-stat3 bg-danger";
			return classe;
		} else if (posicao.equalsIgnoreCase("1") || posicao.equalsIgnoreCase("5") || posicao.equalsIgnoreCase("9")
				|| posicao.equalsIgnoreCase("13") || posicao.equalsIgnoreCase("17") || posicao.equalsIgnoreCase("21")
				|| posicao.equalsIgnoreCase("25") || posicao.equalsIgnoreCase("29") || posicao.equalsIgnoreCase("33")
				|| posicao.equalsIgnoreCase("37") || posicao.equalsIgnoreCase("41")) {
			classe = "panel-stat3 bg-info";
			return classe;
		} else if (posicao.equalsIgnoreCase("2") || posicao.equalsIgnoreCase("6") || posicao.equalsIgnoreCase("10")
				|| posicao.equalsIgnoreCase("14") || posicao.equalsIgnoreCase("18") || posicao.equalsIgnoreCase("22")
				|| posicao.equalsIgnoreCase("26") || posicao.equalsIgnoreCase("30") || posicao.equalsIgnoreCase("34")
				|| posicao.equalsIgnoreCase("38") || posicao.equalsIgnoreCase("42")) {
			classe = "panel-stat3 bg-warning";
			return classe;
		} else {
			classe = "panel-stat3 bg-success";
			return classe;
		}
	}

	public List<Arquivo5> gerar3UltimosArquivos5(Pasta5 pasta5) {
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		List<Arquivo5> listaArquivo5 = null;
		if (listaArquivo5 == null) {
			listaArquivo5 = new ArrayList<Arquivo5>();
		}
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return listaArquivo5;
		} else {
			int idDepartamento = departamento.getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == idDepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select c from Arquivo5 c where  c.pasta5.idpasta5=" + pasta5.getIdpasta5();
			} else {
				sql = "Select c from Arquivo5 c where c.pasta5.idpasta5=" + pasta5.getIdpasta5() + " and c.datainicio <='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "'" + "  and (c.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or c.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			sql = sql + " order by c.idarquivo5 DESC";
			List<Arquivo5> listaArquivos5 = arquivo5Facade.listar(sql);
			if (listaArquivos5 == null) {
				listaArquivos5 = new ArrayList<Arquivo5>();
				return listaArquivos5;
			} else {
				for (int i = 0; i < listaArquivos5.size(); i++) {
					if (listaArquivos5.size() < 3) {
						listaArquivo5.add(listaArquivos5.get(i));
					}
				}
				return listaArquivo5;
			}
		}
	}

	public Integer gerarTotalArquivosPasta5(Pasta5 pasta5) {
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		Integer numeroTotal = 0;
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return numeroTotal;
		} else {
			int iddepartamento = departamento.getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == iddepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select c from Arquivo5 c where c.pasta5.idpasta5=" + pasta5.getIdpasta5();
			} else {
				sql = "Select c from Arquivo5 c where c.pasta5.idpasta5=" + pasta5.getIdpasta5()
						+ " and c.datainicio <='" + Formatacao.ConvercaoDataSql(new Date()) + "' and (c.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or c.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			listaArquivo5 = arquivo5Facade.listar(sql);
			if (listaArquivo5 == null || listaArquivo5.isEmpty()) {
				listaArquivo5 = new ArrayList<Arquivo5>();
				numeroTotal = listaArquivo5.size();
				return numeroTotal;
			} else {
				numeroTotal = listaArquivo5.size();
				return numeroTotal;
			}
		}
	}

	public void excluirPasta(Pasta5 pasta5) {
		Pasta5Facade pasta5Facade = new Pasta5Facade();
		excluirIntens5(pasta5);
		pasta5Facade.excluir(pasta5.getIdpasta5());
		Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
		gerarPasta5();
	}
	
	public void excluirIntens5(Pasta5 pasta5){
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		List<Arquivo5> listaArquivo5 = arquivo5Facade.listar("Select c from Arquivo5 c Where c.pasta5.idpasta5="+ pasta5.getIdpasta5());
		if (listaArquivo5 != null) {
			for (int i = 0; i < listaArquivo5.size(); i++) {
				excluirArquivoSubFTP(listaArquivo5.get(i).getNomeftp());
				arquivo5Facade.excluir(listaArquivo5.get(i).getIdarquivo5());
			}
		}
	}
	
	public boolean excluirArquivoSubFTP(String nomeArquivo) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHost(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			msg = ftp.excluirArquivo(nomeArquivo, "/cloud/departamentos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void gerarPasta5() {
		Pasta5Facade pasta5Facade = new Pasta5Facade();
		String sql = "Select c from Pasta5 c where c.pasta4.idpasta4=" + pasta4.getIdpasta4();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and c.restrito=0 ";
		}
		sql = sql + " order by c.nome";
		listaPasta5 = pasta5Facade.listar(sql);
		if (listaPasta5 == null) {
			listaPasta5 = new ArrayList<Pasta5>();
		}
	}

	public String editarPasta5(Pasta5 pasta5) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("pasta4", pasta4);
		session.setAttribute("pasta5", pasta5);
		RequestContext.getCurrentInstance().openDialog("cadPasta5", options, null);
		return "";
	}

	public String cadastroPasta5() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("pasta4", pasta4);
		RequestContext.getCurrentInstance().openDialog("cadPasta5", options, null);
		return "";
	}

	public void retornoDialogNovaPasta(SelectEvent event) {
		Pasta5 pasta5 = (Pasta5) event.getObject();
		if (pasta5.getIdpasta5() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarPasta5();
		semConteudo();
	}

	public Integer gerarTotalPasta5(Pasta4 pasta4) {
		Pasta5Facade pasta5Facade = new Pasta5Facade();
		Integer numeroTotal = 0;
		List<Pasta5> listaPasta5 = pasta5Facade
				.listar("Select c from Pasta5 c where c.pasta4.idpasta4=" + pasta4.getIdpasta4());
		if (listaPasta5 == null) {
			listaPasta5 = new ArrayList<Pasta5>();
			numeroTotal = listaPasta5.size();
			return numeroTotal;
		} else {
			numeroTotal = listaPasta5.size();
			return numeroTotal;
		}
	}

	public String arquivo5(Pasta5 pasta5) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta5", pasta5);
		return "consArquivo5";
	}

	public void listarArquivoLista() {
		tabelaArquivoLista = true;
		tabelaArquivoQuadro = false;
	}

	public void listarArquivoQuadro() {
		tabelaArquivoQuadro = true;
		tabelaArquivoLista = false;
	}

	public boolean verificarArquivoLista(Arquivo4 arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (arquivo.getIdarquivo4() == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}

	public String trocarCorLinhaTabela(Arquivo4 arquivo4) {
		String cor = "background:#FFFFFF;";
		if (arquivo4 != null) {
			if ((arquivo4.getIdarquivo4() % 2) == 0) {
				cor = "background:#FFFFFF;";
			} else {
				cor = "background:#F3F3F3";
			}
		}
		return cor;
	}

	public void salvarModoExibicao(String nomeExibicao) {
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			Usuario usuario = usuarioFacade.consultar(usuarioLogadoMB.getUsuario().getIdusuario());
			usuario.setModoexibicao(nomeExibicao);
			usuarioFacade.salvar(usuario);
			usuarioLogadoMB.setUsuario(usuario);
		}
	}

	public void verificarExibicao() {
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			if (usuarioLogadoMB.getUsuario().getModoexibicao() == null) {
				tabelaArquivoQuadro = true;
				tabelaArquivoLista = false;
			} else {
				if (usuarioLogadoMB.getUsuario().getModoexibicao().equalsIgnoreCase("I")) {
					tabelaArquivoQuadro = true;
					tabelaArquivoLista = false;
				} else {
					tabelaArquivoLista = true;
					tabelaArquivoQuadro = false;
				}
			}
		}
	}

	public String pesquisar() {
		boolean arquivosNovos = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tituloPagina = "Pesquisar Arquivos";
		pesquisar = true;
		listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
		gerarListaArquivos(departamento.getPasta1List(), departamento);
		session.setAttribute("arquivosNovos", arquivosNovos);
		session.setAttribute("listaArquivoVencidoBean", listaArquivoVencidoBean);
		session.setAttribute("tituloPagina", tituloPagina);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("departamento", departamento);
		session.setAttribute("pasta1", pasta1);
		session.setAttribute("pasta2", pasta2);
		session.setAttribute("pasta3", pasta3);
		session.setAttribute("pasta4", pasta4);
		return "listarArquivos";
	}

	public void gerarListaArquivos(List<Pasta1> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			Object obj = lista.get(i);
			if (obj instanceof Pasta1) {
				gerarListaArquivo4(pasta4.getArquivo4List(), departamento);
				gerarListaArquivo5(pasta4.getArquivo5List(), departamento);

			}
		}
	}

	public void gerarListaArquivo4(List<Arquivo4> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (lista.get(i).getDatavalidade() != null) {
				if (lista.get(i).getDatavalidade().after(dataAtual)
						|| lista.get(i).getDatavalidade().equals(dataAtual)) {
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								}
							}
						}
					} else {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							}
						}
					}
				}
			} else {
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							}
						}
					}
				} else {
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
						} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
						}
					}
				}
			}
			if (arquivosVencidosBean != null) {
				listaArquivoVencidoBean.add(arquivosVencidosBean);
			}
		}
	}

	public void gerarListaArquivo5(List<Arquivo5> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (lista.get(i).getDatavalidade() != null) {
				if (lista.get(i).getDatavalidade().after(dataAtual)
						|| lista.get(i).getDatavalidade().equals(dataAtual)) {
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								}
							}
						}
					} else {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							}
						}
					}
				}
			} else {
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							}
						}
					}
				} else {
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
						} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
						}
					}
				}
			}
			if (arquivosVencidosBean != null) {
				listaArquivoVencidoBean.add(arquivosVencidosBean);
			}
		}
	}

	public ArquivosVencidosBean adicionarArquivo4(Arquivo4 arquivo4, Departamento departamento) {
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo4.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo4.getNome());
			arquivoBean.setData(arquivo4.getDatavalidade());
			arquivoBean.setPasta("4");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo4.getPasta1().getNome() + "\\"
					+ arquivo4.getPasta2().getNome() + "\\" + arquivo4.getPasta3().getNome() + "\\"
					+ arquivo4.getPasta4().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo4.getIdarquivo4());
			arquivoBean.setNomeFtp(arquivo4.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		} else {
			arquivoBean.setNome(arquivo4.getNome());
			arquivoBean.setData(arquivo4.getDatavalidade());
			arquivoBean.setPasta("4");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo4.getPasta1().getNome() + "\\"
					+ arquivo4.getPasta2().getNome() + "\\" + arquivo4.getPasta3().getNome() + "\\"
					+ arquivo4.getPasta4().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo4.getIdarquivo4());
			arquivoBean.setNomeFtp(arquivo4.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}

	public ArquivosVencidosBean adicionarArquivo5(Arquivo5 arquivo5, Departamento departamento) {
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo5.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo5.getNome());
			arquivoBean.setData(arquivo5.getDatavalidade());
			arquivoBean.setPasta("5");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo5.getPasta1().getNome() + "\\"
					+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\"
					+ arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
			arquivoBean.setNomeFtp(arquivo5.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		} else {
			arquivoBean.setNome(arquivo5.getNome());
			arquivoBean.setData(arquivo5.getDatavalidade());
			arquivoBean.setPasta("5");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo5.getPasta1().getNome() + "\\"
					+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\"
					+ arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
			arquivoBean.setNomeFtp(arquivo5.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}

	public void limparPesquisa() {
		nomeArquivo = "";
		tipoArquivo = "TODAS";
		listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
	}
	
	
	public String vincularArquivoFornecedor(Arquivo4 arquivo4){
		String urlDocs = "consPasta5Arquivo4";
		arquivosBean = new ArquivoBean();
		arquivosBean.setDatainicio(arquivo4.getDatainicio());
		arquivosBean.setDatavalidade(arquivo4.getDatavalidade());
		arquivosBean.setNome(arquivo4.getNome());
		arquivosBean.setNomeftp(arquivo4.getNomeftp());
		arquivosBean.setRestrito(arquivo4.isRestrito());
		arquivosBean.setTipo("Documento");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("arquivoBean", arquivosBean);
		session.setAttribute("pasta4", pasta4);
		session.setAttribute("urlDocs", urlDocs);
		session.setAttribute("arquivo4", arquivo4);
		return "vincularFonecedorDocs";
	}
	
	
	
	public boolean acessoVinculoFornecedor(Arquivo4 arquivo4){
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
			if (arquivo4 == null) {
				return acesso;
			}else{
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					return acesso;
				}else{
					acesso = true;
					return acesso;
				}
				
			}
		}
	}
	
	
	public String verificarvinculoFornecedor(Arquivo4 arquivo4){
		String cor = "";
		if (arquivo4 != null) {
			if (arquivo4.isCopiado()) {
				cor = "color:#6495ED;";
			}
		}
		return cor;
	}
	
	
	public String verificarvinculoFornecedorLista(Arquivo4 arquivo4){
		String cor = "../../resources/img/vincularUnidade.png";
		if (arquivo4 != null) {
			if (arquivo4.isCopiado()) {
				cor = "../../resources/img/iconeVinculadoFornecedor.png";
			}
		}
		return cor;
	}
}
