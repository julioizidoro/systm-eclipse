package br.com.travelmate.managerBean.videos;

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

import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.Video1Facade;
import br.com.travelmate.facade.Video3Facade;
import br.com.travelmate.facade.Video4Facade;
import br.com.travelmate.facade.Video5Facade;
import br.com.travelmate.facade.VideoPasta4Facade;
import br.com.travelmate.facade.VideoPasta5Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cloud.midia.ArquivoPastaBean;
import br.com.travelmate.managerBean.cloud.midia.Pastas2Arquivo1MB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Pastavideo;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Video2;
import br.com.travelmate.model.Video3;
import br.com.travelmate.model.Video4;
import br.com.travelmate.model.Video5;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.model.Videopasta4;
import br.com.travelmate.model.Videopasta5;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class Pastas5Videos4MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private Videopasta1 videopasta1;
	private List<Videopasta5> listaVideoPasta5;
	private Ftpdados ftpDados;
	private boolean semPastaArquivo = false;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean tabelaArquivoLista = false;
	private boolean tabelaArquivoQuadro = true;
	private boolean pesquisar;
	private String tituloPagina;
	private String tipoArquivo;
	private String nomeArquivo;
	private List<Video4> listaVideo4;
	private Videopasta2 videopasta2;
	private Videopasta3 videopasta3;
	private Videopasta4 videopasta4;
	private List<Video4Bean> listaVideo4Bean;
	private Video4Bean video4Bean;
	private ArquivoBean arquivoBean;
	private String urlArquivo = "";
	
	
	
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			videopasta4 = (Videopasta4) session.getAttribute("videopasta4");
			session.removeAttribute("videopasta4");
			if (videopasta4 != null) {
				videopasta1 = videopasta4.getVideopasta1();
				videopasta2 = videopasta4.getVideopasta2();
				videopasta3 = videopasta4.getVideopasta3();
				departamento = videopasta1.getDepartamento();
				gerarPastaVideos5();
				gerarVideos4();
				ftpDados = new Ftpdados();
				FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
				try {
					ftpDados = ftpDadosFacade.getFTPDados();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (ftpDados != null) {
					urlArquivo = ftpDados.getProtocolo() + "://" + ftpDados.getHost() +  ":82/videos";
				}
				// Verificar se existe pastas ou arquivos na tela
				semConteudo();
				verificarExibicao();
			}else{
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos1Videos.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos1Videos.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}




	
	public Departamento getDepartamento() {
		return departamento;
	}





	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}





	public Videopasta1 getVideopasta1() {
		return videopasta1;
	}





	public void setVideopasta1(Videopasta1 videopasta1) {
		this.videopasta1 = videopasta1;
	}





	public List<Videopasta5> getListaVideoPasta5() {
		return listaVideoPasta5;
	}





	public void setListaVideoPasta5(List<Videopasta5> listaVideoPasta5) {
		this.listaVideoPasta5 = listaVideoPasta5;
	}





	public Ftpdados getFtpDados() {
		return ftpDados;
	}





	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}





	public boolean isSemPastaArquivo() {
		return semPastaArquivo;
	}





	public void setSemPastaArquivo(boolean semPastaArquivo) {
		this.semPastaArquivo = semPastaArquivo;
	}





	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}





	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}





	public boolean isTabelaArquivoLista() {
		return tabelaArquivoLista;
	}





	public void setTabelaArquivoLista(boolean tabelaArquivoLista) {
		this.tabelaArquivoLista = tabelaArquivoLista;
	}





	public boolean isTabelaArquivoQuadro() {
		return tabelaArquivoQuadro;
	}





	public void setTabelaArquivoQuadro(boolean tabelaArquivoQuadro) {
		this.tabelaArquivoQuadro = tabelaArquivoQuadro;
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








	public List<Video4> getListaVideo4() {
		return listaVideo4;
	}





	public void setListaVideo4(List<Video4> listaVideo4) {
		this.listaVideo4 = listaVideo4;
	}





	public Videopasta2 getVideopasta2() {
		return videopasta2;
	}





	public void setVideopasta2(Videopasta2 videopasta2) {
		this.videopasta2 = videopasta2;
	}





	public Videopasta3 getVideopasta3() {
		return videopasta3;
	}





	public void setVideopasta3(Videopasta3 videopasta3) {
		this.videopasta3 = videopasta3;
	}





	public Videopasta4 getVideopasta4() {
		return videopasta4;
	}





	public void setVideopasta4(Videopasta4 videopasta4) {
		this.videopasta4 = videopasta4;
	}
	
	





	public List<Video4Bean> getListaVideo4Bean() {
		return listaVideo4Bean;
	}





	public void setListaVideo4Bean(List<Video4Bean> listaVideo4Bean) {
		this.listaVideo4Bean = listaVideo4Bean;
	}





	public Video4Bean getVideo4Bean() {
		return video4Bean;
	}





	public void setVideo4Bean(Video4Bean video4Bean) {
		this.video4Bean = video4Bean;
	}





	public String getUrlArquivo() {
		return urlArquivo;
	}





	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}





	public String cadastroPastaVideos5() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("videopasta4", videopasta4);
		RequestContext.getCurrentInstance().openDialog("cadPastaVideos5", options, null);
		return "";
	}
	
	
	public String cadastroVideos4() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		session.setAttribute("videopasta4", videopasta4);
		RequestContext.getCurrentInstance().openDialog("cadVideo4", options, null);
		return "";
	}
	
	
	public String videos5(Videopasta5 videopasta5) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("videopasta5", videopasta5);
		return "consVideo5";
	}
	
	
	public void gerarVideos4() {
		Video4Facade video4Facade = new Video4Facade();
		String sql = "Select c from Video4 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
				+ " and c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2() + " and c.videopasta3.idvideopasta3=" + + videopasta3.getIdvideopasta3()
				+ " and c.videopasta4.idvideopasta4=" + videopasta4.getIdvideopasta4();
		
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql  + " and c.ativo=true";
		}
		sql = sql + " order by c.nome";
		listaVideo4 = video4Facade.listar(sql);
		if (listaVideo4 == null) {
			listaVideo4 = new ArrayList<Video4>();
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;
			listaVideo4Bean = new ArrayList<>();
		}
//		for (int i = 0; i < listaVideo4.size(); i++) {
//			video4Bean = new Video4Bean();
//			video4Bean.setVideo1(listaVideo4.get(i));
//			if ((i + 1) < listaVideo4.size()) {
//				video4Bean.setVideo2(listaVideo4.get(i + 1));
//				i++;
//				if ((i + 1) < listaVideo4.size()) {
//					video4Bean.setVideo3(listaVideo4.get(i + 1));
//					i++;
//				} else {
//					video4Bean.setVideo3(null);
//				}
//
//			} else {
//				video4Bean.setVideo2(null);
//				video4Bean.setVideo3(null);
//			}
//
//			listaVideo4Bean.add(video4Bean);
//		}
	}

	public void gerarPastaVideos5() {
		VideoPasta5Facade videoPasta5Facade = new VideoPasta5Facade();
		String sql = "Select c from Videopasta5 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
				+ " and c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2() + " and c.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3()
				+ " and c.videopasta4.idvideopasta4=" + videopasta4.getIdvideopasta4();

		listaVideoPasta5 = videoPasta5Facade.listar(sql);
		if (listaVideoPasta5 == null) {
			listaVideoPasta5 = new ArrayList<Videopasta5>();
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;
		}
	}

	public String pegarNomeArquivo(Video2 video2) {
		if (video2 != null) {
			String nome = video2.getDescricao();
			return nome;
		}
		return "";
	}

	public String voltarConsVideoPasta1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "consPasta1Video";
	}

	public String voltarConsVideoPasta2() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		session.setAttribute("videopasta1", videopasta1);
		return "consPastas2Videos1";
	}
	
	public String voltarConsVideoPasta3() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("videopasta2", videopasta2);
		return "consPastas3Videos2";
	}
	
	public String voltarConsVideoPasta4() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("videopasta3", videopasta3);
		return "consPastas4Videos3";
	}


	public String alterarDescricaoVideo(Video4 video4) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video4", video4);
		session.setAttribute("editar", true);
		RequestContext.getCurrentInstance().openDialog("consDescritivoVideo4", options, null);
		return "";
	}
	 
	public String descricaoVideo(Video4 video4) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video4", video4);
		session.setAttribute("editar", false);
		RequestContext.getCurrentInstance().openDialog("consDescritivoVideo4", options, null);
		return "";
	}

	public String identificarExtensao(String nomeArquivo) {
		String extensao = "";
		for (int i = 0; i < nomeArquivo.length(); i++) {
			if (nomeArquivo.charAt(nomeArquivo.length() - i) != '.') {
				extensao = nomeArquivo.charAt(nomeArquivo.length() - i) + extensao;
			} else {
				i = nomeArquivo.length() + 100;
			}
		}
		if (extensao.length() > 4) {
			extensao = "pdf";
		}
		if (extensao.equalsIgnoreCase("jpg") || extensao.equalsIgnoreCase("jpeg") || extensao.equalsIgnoreCase("png")) {
			return "../../resources/img/icone_jpg.png";
		} else if (extensao.equalsIgnoreCase("pdf")) {
			return "../../resources/img/icone_pdf.png";
		} else if (extensao.equalsIgnoreCase("docx") || extensao.equalsIgnoreCase("doc")) {
			return "../../resources/img/icone_docx.png";
		} else if (extensao.equalsIgnoreCase("xls") || extensao.equalsIgnoreCase("xlsx")) {
			return "../../resources/img/icone_xls.png";
		} else if (extensao.equalsIgnoreCase("ai")) {
			return "../../resources/img/icone_ai.png";
		} else if (extensao.equalsIgnoreCase("psd")) {
			return "../../resources/img/icone_psd.png";
		}
		return "";
	}

	public String retornaIconeArquivo(String nomeArquivo) {
		if (nomeArquivo == "") {
			return "";
		}
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		if (extensao.length() > 5) {
			extensao = ".pdf";
		}
		if (extensao.equalsIgnoreCase(".jpg") || extensao.equalsIgnoreCase(".jpeg")
				|| extensao.equalsIgnoreCase(".png")) {
			return "../../resources/img/icone_jpg.png";
		} else if (extensao.equalsIgnoreCase(".pdf")) {
			return "../../resources/img/icone_pdf.png";
		} else if (extensao.equalsIgnoreCase(".docx") || extensao.equalsIgnoreCase(".doc")) {
			return "../../resources/img/icone_docx.png";
		} else if (extensao.equalsIgnoreCase(".xls") || extensao.equalsIgnoreCase(".xlsx")) {
			return "../../resources/img/icone_xls.png";
		} else if (extensao.equalsIgnoreCase(".txt")) {
			return "../../resources/img/icone_txt.png";
		} else if (extensao.equalsIgnoreCase(".cdr")) {
			return "../../resources/img/icone_cdr.png";
		} else if (extensao.equalsIgnoreCase(".eps")) {
			return "../../resources/img/icone_eps.png";
		} else if (extensao.equalsIgnoreCase(".bmp")) {
			return "../../resources/img/icone_bmp.png";
		} else if (extensao.equalsIgnoreCase(".pptx")) {
			return "../../resources/img/icone_pptx.png";
		} else if (extensao.equalsIgnoreCase(".wma")) {
			return "../../resources/img/iconewma.png";
		} else if (extensao.equalsIgnoreCase(".ppsx")) {
			return "../../resources/img/icone_ppsx.png";
		}
		return "";
	}

	public Integer gerarTotalNVideo5(Videopasta5 videopasta5) {
		Video5Facade video5Facade = new Video5Facade();
		Integer numeroTotal = 0;
		String sql = "";
		List<Video5> listaVideo5 = new ArrayList<>();
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return numeroTotal;
		} else {
			sql = "Select c from Video5 c where c.videopasta5.idvideopasta5=" + videopasta5.getIdvideopasta5();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.ativo=true";
			}
			listaVideo5 = video5Facade.listar(sql);
			if (listaVideo5 == null) {
				listaVideo5 = new ArrayList<>();
				numeroTotal = listaVideo5.size();
				return numeroTotal;
			} else {
				numeroTotal = listaVideo5.size();
				return numeroTotal;
			}
		}
	}


	public List<Video5> gerar3UltimosVideo5(Videopasta5 videopasta5) {
		Video5Facade video5Facade = new Video5Facade();
		List<Video5> listaVideo5 = null;
		if (listaVideo5 == null) {
			listaVideo5 = new ArrayList<Video5>();
		}
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return listaVideo5;
		} else {
			sql = "Select v from Video5 v where v.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2()
					+ " and v.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
					+ " and v.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3()
					+ " and v.videopasta4.idvideopasta4=" + videopasta4.getIdvideopasta4()
					+ " and v.videopasta5.idvideopasta5=" + videopasta5.getIdvideopasta5();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and v.ativo=true";
			}
			List<Video5> listaVideos5 = video5Facade.listar(sql);
			if (listaVideos5 == null || listaVideos5.isEmpty()) {
				listaVideos5 = new ArrayList<Video5>();
				return listaVideos5;
			} else {
				for (int i = 0; i < listaVideos5.size(); i++) {
					if (listaVideo5.size() < 3) {
						listaVideo5.add(listaVideos5.get(i));
					}
				}
				return listaVideo5;
			}
		}
	}

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

	public String voltarConsDepartamento() {
		return "consDepartamentos1Videos";
	}

	public void retornoDialogNovaPasta(SelectEvent event) {
		Videopasta5 videopasta5 = (Videopasta5) event.getObject();
		if (videopasta5.getIdvideopasta5() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			listaVideoPasta5.add(videopasta5);
		}
		semConteudo();
	}

	public void retornoDialogAlteracaoDescricao(SelectEvent event) {
		Video4 video4 = (Video4) event.getObject();
		if (video4.getIdvideo4() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarVideos4();
		semConteudo();
	}

	public void retornoDialogNovoVideo(SelectEvent event) {
		Video4 video4 = (Video4) event.getObject();
		if (video4.getIdvideo4() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			gerarVideos4();
		}
		semConteudo();
	}

	// Verificar se existe pastas ou arquivos na tela
	public void semConteudo() {
		if ((listaVideo4 == null || listaVideo4.isEmpty())
				&& (listaVideoPasta5 == null || listaVideoPasta5.isEmpty())) {
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;

		}
	}

	public boolean excluirArquivoFTP(Video4 video4) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pastas5Videos4MB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(Pastas5Videos4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = video4.getHost();
			msg = ftp.excluirArquivo(nomeArquivoFTP, "/videos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pastas5Videos4MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(Pastas5Videos4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String excluirArquivo(Video4 video4) {
		excluirArquivoFTP(video4);
		Video4Facade video4Facade = new Video4Facade();
		video4Facade.excluir(video4.getIdvideo4());
		gerarPastaVideos5();
		gerarVideos4();
		return "";
	}

	public void excluirPasta(Videopasta5 videopasta5) {
		VideoPasta5Facade videoPasta5Facade = new VideoPasta5Facade();
		Video5Facade video5Facade = new Video5Facade();
		List<Video5> listaVideo5 = video5Facade
				.listar("Select c from Video5 c where c.videopasta5.idvideopasta5=" + videopasta5.getIdvideopasta5());
		if (listaVideo5 == null) {
			videoPasta5Facade.excluir(videopasta5.getIdvideopasta5());
			Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
		} else {
			for (int i = 0; i < listaVideo5.size(); i++) {   
				excluirVideosPasta5FTP(listaVideo5.get(i));
				video5Facade.excluir(listaVideo5.get(i).getIdvideo5());
			}
			videoPasta5Facade.excluir(videopasta5.getIdvideopasta5());
			Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
		}
		gerarPastaVideos5();
	}
	

	public boolean excluirVideosPasta5FTP(Video5 video5) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pastas5Videos4MB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(Pastas5Videos4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = video5.getHost();
			msg = ftp.excluirArquivo(nomeArquivoFTP, "/videos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pastas5Videos4MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		return false;
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

	public boolean verificarArquivo1(Video4Bean video4Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video4Bean.getVideo1() == null) {
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

	public boolean verificarArquivo2(Video4Bean video4Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video4Bean.getVideo2() == null) {
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

	public boolean verificarArquivo3(Video4Bean video4Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video4Bean.getVideo3() == null) {
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
	
	public boolean verificarArquivoLista(Video4 video4) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video4 == null) {
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

	public String editarVideoPasta5(Videopasta5 videopasta5) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("videopasta4", videopasta4);
		session.setAttribute("videopasta5", videopasta5);
		RequestContext.getCurrentInstance().openDialog("cadPastaVideos5", options, null);
		return "";
	}

	public void listarArquivoLista() {
		tabelaArquivoLista = true;
		tabelaArquivoQuadro = false;
		salvarModoExibicao("L");
	}

	public void listarArquivoQuadro() {
		tabelaArquivoQuadro = true;
		tabelaArquivoLista = false;
		salvarModoExibicao("I");
	}

	public boolean verificarArquivoLista(Arquivo1 arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (arquivo.getIdarquivo1() == null) {
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

	public String trocarCorLinhaTabela(Arquivo1 arquivo1) {
		String cor = "background:#FFFFFF;";
		if (arquivo1 != null) {
			if ((arquivo1.getIdarquivo1() % 2) == 0) {
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
	
	
	public boolean verificarVideo(Video4 video4){
		if (video4 == null) {
			return false;
		}else{
			return true;
		}
	}
	
	
	public String vincularArquivoFornecedor(Video4 video4){
		String urlDocs = "consPastas5Videos4";
		arquivoBean = new ArquivoBean();
		arquivoBean.setDatainicio(new Date());
		arquivoBean.setDatavalidade(null);
		arquivoBean.setNome(video4.getNome());
		arquivoBean.setNomeftp(video4.getHost());
		arquivoBean.setRestrito(!video4.isAtivo());
		arquivoBean.setTipo("Video");
		arquivoBean.setDescricao(video4.getDescricao());
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("arquivoBean", arquivoBean);
		session.setAttribute("videopasta4", videopasta4);
		session.setAttribute("urlDocs", urlDocs);
		session.setAttribute("video4", video4);
		return "vincularFonecedorDocs";
	}
	
	
	public boolean acessoVinculoFornecedor(Video4 video4){
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
			if (video4 == null) {
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
	
	
	public String verificarvinculoFornecedor(Video4 video4){
		String cor = "";
		if (video4 != null) {
			if (video4.isCopiado()) {
				cor = "color:#6495ED;";
			}
		}
		return cor;
	}
	
	
	public String verificarvinculoFornecedorLista(Video4 video4){
		String cor = "../../resources/img/vincularUnidade.png";
		if (video4 != null) {
			if (video4.isCopiado()) {
				cor = "../../resources/img/iconeVinculadoFornecedor.png";
			}
		}
		return cor;
	}

}
