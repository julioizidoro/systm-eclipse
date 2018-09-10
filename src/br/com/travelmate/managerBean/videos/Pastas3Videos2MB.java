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
import br.com.travelmate.facade.Video2Facade;
import br.com.travelmate.facade.Video3Facade;
import br.com.travelmate.facade.Video4Facade;
import br.com.travelmate.facade.Video5Facade;
import br.com.travelmate.facade.VideoPasta3Facade;
import br.com.travelmate.facade.VideoPasta4Facade;
import br.com.travelmate.facade.VideoPasta5Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
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
public class Pastas3Videos2MB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private Videopasta1 videopasta1;
	private List<Videopasta3> listaVideoPasta3;
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
	private List<Video2> listaVideo2;
	private Videopasta2 videopasta2;
	private List<Video2Bean> listaVideo2Bean;
	private Video2Bean video2Bean;
	private ArquivoBean arquivoBean;
	private String urlArquivo = "";

	@PostConstruct
	public void init() {

		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			videopasta2 = (Videopasta2) session.getAttribute("videopasta2");
			session.removeAttribute("videopasta2");
			if (videopasta2 != null) {
				videopasta1 = videopasta2.getVideopasta1();
				departamento = videopasta1.getDepartamento();
				gerarPastaVideos3();
				gerarVideos2();
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
			}else {
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

	public List<Videopasta3> getListaVideoPasta3() {
		return listaVideoPasta3;
	}

	public void setListaVideoPasta3(List<Videopasta3> listaVideoPasta3) {
		this.listaVideoPasta3 = listaVideoPasta3;
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

	public List<Video2> getListaVideo2() {
		return listaVideo2;
	}

	public void setListaVideo2(List<Video2> listaVideo2) {
		this.listaVideo2 = listaVideo2;
	}

	public Videopasta2 getVideopasta2() {
		return videopasta2;
	}

	public void setVideopasta2(Videopasta2 videopasta2) {
		this.videopasta2 = videopasta2;
	}
	
	

	public List<Video2Bean> getListaVideo2Bean() {
		return listaVideo2Bean;
	}

	public void setListaVideo2Bean(List<Video2Bean> listaVideo2Bean) {
		this.listaVideo2Bean = listaVideo2Bean;
	}

	public Video2Bean getVideo2Bean() {
		return video2Bean;
	}

	public void setVideo2Bean(Video2Bean video2Bean) {
		this.video2Bean = video2Bean;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public String cadastroPastaVideos3() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("videopasta2", videopasta2);
		RequestContext.getCurrentInstance().openDialog("cadPastaVideos3", options, null);
		return "";
	}

	public String cadastroVideos2() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		session.setAttribute("videopasta2", videopasta2);
		RequestContext.getCurrentInstance().openDialog("cadVideo2", options, null);
		return "";
	}

	public String consPastas4Videos3(Videopasta3 videopasta3) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("videopasta3", videopasta3);
		return "consPastas4Videos3";
	}

	public void gerarVideos2() {
		Video2Facade video2Facade = new Video2Facade();
		String sql = "Select c from Video2 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
				+ " and c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2();
		
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and c.ativo=true";
		}
		sql = sql + " order by c.nome";
		listaVideo2 = video2Facade.listar(sql);
		if (listaVideo2 == null) {
			listaVideo2 = new ArrayList<Video2>();
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;
			listaVideo2Bean = new ArrayList<>();
		}
//		for (int i = 0; i < listaVideo2.size(); i++) {
//			video2Bean = new Video2Bean();
//			video2Bean.setVideo1(listaVideo2.get(i));
//			if ((i + 1) < listaVideo2.size()) {
//				video2Bean.setVideo2(listaVideo2.get(i + 1));
//				i++;
//				if ((i + 1) < listaVideo2.size()) {
//					video2Bean.setVideo3(listaVideo2.get(i + 1));
//					i++;
//				} else {
//					video2Bean.setVideo3(null);
//				}
//
//			} else {
//				video2Bean.setVideo2(null);
//				video2Bean.setVideo3(null);
//			}
//
//			listaVideo2Bean.add(video2Bean);
//		}
	}

	public void gerarPastaVideos3() {
		VideoPasta3Facade videoPasta3Facade = new VideoPasta3Facade();
		String sql = "Select c from Videopasta3 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
				+ " and c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2();

		listaVideoPasta3 = videoPasta3Facade.listar(sql);
		if (listaVideoPasta3 == null) {
			listaVideoPasta3 = new ArrayList<Videopasta3>();
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
		videopasta1 = null;
		videopasta2 = null;
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

	
	public String alterarDescricaoVideo(Video2 video2) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video2", video2);
		session.setAttribute("editar", true);
		RequestContext.getCurrentInstance().openDialog("consDescritivoVideo2", options, null);
		return "";
	}
	 
	public String descricaoVideo(Video2 video2) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video2", video2);
		session.setAttribute("editar", false);
		RequestContext.getCurrentInstance().openDialog("consDescritivoVideo2", options, null);
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

	public Integer gerarTotalNVideo3(Videopasta3 videopasta3) {
		Video3Facade video3Facade = new Video3Facade();
		Integer numeroTotal = 0;
		String sql = "";
		List<Video3> listaVideo3 = new ArrayList<>();
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return numeroTotal;
		} else {
			sql = "Select c from Video3 c where c.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.ativo=true";
			}
			listaVideo3 = video3Facade.listar(sql);
			if (listaVideo3 == null) {
				listaVideo3 = new ArrayList<>();
				numeroTotal = listaVideo3.size();
				return numeroTotal;
			} else {
				numeroTotal = listaVideo3.size();
				return numeroTotal;
			}
		}
	}

	public Integer gerarTotalPasta4(Videopasta3 videopasta3) {
		VideoPasta4Facade videoPasta4Facade = new VideoPasta4Facade();
		Integer numeroTotal = 0;
		String sql = "Select c from Videopasta4 c where c.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3();
		List<Videopasta4> listaVideoPasta4 = videoPasta4Facade.listar(sql);
		if (listaVideoPasta4 == null) {
			listaVideoPasta4 = new ArrayList<>();
			numeroTotal = listaVideoPasta4.size();
			return numeroTotal;
		} else {
			numeroTotal = listaVideoPasta4.size();
			return numeroTotal;
		}
	}

	public List<Video3> gerar3UltimosVideo3(Videopasta3 videopasta3) {
		Video3Facade video3Facade = new Video3Facade();
		List<Video3> listaVideo3 = null;
		if (listaVideo3 == null) {
			listaVideo3 = new ArrayList<Video3>();
		}
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return listaVideo3;
		} else {
			sql = "Select v from Video3 v where v.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2()
					+ " and v.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
					+ " and v.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and v.ativo=true";
			}
			List<Video3> listaVideos3 = video3Facade.listar(sql);
			if (listaVideos3 == null || listaVideos3.isEmpty()) {
				listaVideos3 = new ArrayList<Video3>();
				return listaVideos3;
			} else {
				for (int i = 0; i < listaVideos3.size(); i++) {
					if (listaVideo3.size() < 3) {
						listaVideo3.add(listaVideos3.get(i));
					}
				}
				return listaVideo3;
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
		Videopasta3 videopasta3 = (Videopasta3) event.getObject();
		if (videopasta3.getIdvideopasta3() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			listaVideoPasta3.add(videopasta3);
		}
		semConteudo();
	}

	public void retornoDialogAlteracaoDescricao(SelectEvent event) {
		Video2 video2 = (Video2) event.getObject();
		if (video2.getIdvideo2() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		semConteudo();
	}

	public void retornoDialogNovoVideo(SelectEvent event) {
		Video2 video2 = (Video2) event.getObject();
		if (video2.getIdvideo2() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			gerarVideos2();
		}
		semConteudo();
	}

	// Verificar se existe pastas ou arquivos na tela
	public void semConteudo() {
		if ((listaVideo2 == null || listaVideo2.isEmpty())
				&& (listaVideoPasta3 == null || listaVideoPasta3.isEmpty())) {
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;

		}
	}

	public boolean excluirArquivoFTP(Video2 video2) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pastas3Videos2MB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(Pastas3Videos2MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = video2.getHost();
			msg = ftp.excluirArquivo(nomeArquivoFTP, "/videos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pastas3Videos2MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(Pastas3Videos2MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String excluirArquivo(Video2 video2) {
		excluirArquivoFTP(video2);
		Video2Facade video2Facade = new Video2Facade();
		video2Facade.excluir(video2.getIdvideo2());
		gerarPastaVideos3();
		gerarVideos2();
		return "";
	}

	public void excluirPasta(Videopasta3 videopasta3) {
		VideoPasta3Facade videoPasta3Facade = new VideoPasta3Facade();
		excluirItens5(videopasta3);
		excluirItens4(videopasta3);
		excluirItens3(videopasta3);
		videoPasta3Facade.excluir(videopasta3.getIdvideopasta3());
		Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
		gerarPastaVideos3();
	}
	
	public void excluirItens5(Videopasta3 videopasta3){
		VideoPasta5Facade videoPasta5Facade = new VideoPasta5Facade();
		Video5Facade video5Facade = new Video5Facade();
		List<Video5> listaVideo5 = video5Facade.listar("Select c from Video5 c Where c.videopasta3.idvideopasta3="+ videopasta3.getIdvideopasta3());
		if (listaVideo5 != null) {
			for (int i = 0; i < listaVideo5.size(); i++) {
				excluirVideosSubFTP(listaVideo5.get(i).getHost());
				video5Facade.excluir(listaVideo5.get(i).getIdvideo5());
			}
		}
		List<Videopasta5> listaVideoPasta5 = videoPasta5Facade.listar("Select c From Videopasta5 c Where c.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3());
		if (listaVideoPasta5 != null) {
			for (int i = 0; i < listaVideoPasta5.size(); i++) {
				videoPasta5Facade.excluir(listaVideoPasta5.get(i).getIdvideopasta5());
			}
		}
	}
	
	public void excluirItens4(Videopasta3 videopasta3){
		Video4Facade video4Facade = new Video4Facade();
		VideoPasta4Facade videoPasta4Facade = new VideoPasta4Facade();
		List<Video4> listaVideo4 = video4Facade
				.listar("Select c from Video4 c where c.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3());
		if (listaVideo4 != null) {
			for (int i = 0; i < listaVideo4.size(); i++) {
				excluirVideosSubFTP(listaVideo4.get(i).getHost());
				video4Facade.excluir(listaVideo4.get(i).getIdvideo4());
			}
		}
		List<Videopasta4> listaPastaVideo4 = videoPasta4Facade
				.listar("Select c from Videopasta4 c where c.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3());
		if (listaPastaVideo4 != null) {
			for (int i = 0; i < listaPastaVideo4.size(); i++) {
				videoPasta4Facade.excluir(listaPastaVideo4.get(i).getIdvideopasta4());
			}
		}
	}
	
	public void excluirItens3(Videopasta3 videopasta3){
		Video3Facade video3Facade = new Video3Facade();
		
		List<Video3> listaVideo3 = video3Facade
				.listar("Select c from Video3 c where c.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3());
		if (listaVideo3 != null) {
			for (int i = 0; i < listaVideo3.size(); i++) {
				excluirVideosSubFTP(listaVideo3.get(i).getHost());
				video3Facade.excluir(listaVideo3.get(i).getIdvideo3());
			}
		}
		
	}

	public boolean excluirVideosSubFTP(String nomeArquivo) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pastas3Videos2MB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(Pastas3Videos2MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			msg = ftp.excluirArquivo(nomeArquivo, "/videos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pastas3Videos2MB.class.getName()).log(Level.SEVERE, null, ex);
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

	public boolean verificarArquivo1(Video2Bean video2Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video2Bean.getVideo1() == null) {
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

	public boolean verificarArquivo2(Video2Bean video2Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video2Bean.getVideo2() == null) {
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

	public boolean verificarArquivo3(Video2Bean video2Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video2Bean.getVideo3() == null) {
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
	
	public boolean verificarArquivoLista(Video2 video2) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video2 == null) {
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

	public String editarVideoPasta3(Videopasta3 videopasta3) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("videopasta2", videopasta2);
		session.setAttribute("videopasta3", videopasta3);
		RequestContext.getCurrentInstance().openDialog("cadPastaVideos3", options, null);
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
	
	
	public boolean verificarVideo(Video2 video2){
		if (video2 == null) {
			return false;
		}else{
			return true;
		}
	}
	
	
	public String vincularArquivoFornecedor(Video2 video2){
		String urlDocs = "consPastas3Videos2";
		arquivoBean = new ArquivoBean();
		arquivoBean.setDatainicio(new Date());
		arquivoBean.setDatavalidade(null);
		arquivoBean.setNome(video2.getNome());
		arquivoBean.setNomeftp(video2.getHost());
		arquivoBean.setRestrito(!video2.isAtivo());
		arquivoBean.setTipo("Video");
		arquivoBean.setDescricao(video2.getDescricao());
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("arquivoBean", arquivoBean);
		session.setAttribute("videopasta2", videopasta2);
		session.setAttribute("urlDocs", urlDocs);
		session.setAttribute("video2", video2);
		return "vincularFonecedorDocs";
	}
	
	
	public boolean acessoVinculoFornecedor(Video2 video2){
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
			if (video2 == null) {
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
	
	
	public String verificarvinculoFornecedor(Video2 video2){
		String cor = "";
		if (video2 != null) {
			if (video2.isCopiado()) {
				cor = "color:#6495ED;";
			}
		}
		return cor;
	}
	
	
	public String verificarvinculoFornecedorLista(Video2 video2){
		String cor = "../../resources/img/vincularUnidade.png";
		if (video2 != null) {
			if (video2.isCopiado()) {
				cor = "../../resources/img/iconeVinculadoFornecedor.png";
			}
		}
		return cor;
	}

}
