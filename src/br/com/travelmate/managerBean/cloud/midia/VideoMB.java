package br.com.travelmate.managerBean.cloud.midia;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VideoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Pastavideo;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Video;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class VideoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Video video;
	private List<Video> listaVideo;
	private Pastavideo pastavideo;
	private Ftpdados ftpdados;
	private Ftp ftp;
	private StreamedContent midia;
	private List<VideosBean> listaVideosBean;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean tabelaVideoQuadro = true;
	private boolean tabelaVideoLista = false;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pastavideo = (Pastavideo) session.getAttribute("pastavideo");
		session.removeAttribute("pastavideo");
		if (pastavideo != null) {
			gerarListaVideos();
		}
		pegarDadosFtp();
		verificarExibicao();
	}
  
  
	public Video getVideo() {
		return video;
	} 


	public void setVideo(Video video) {
		this.video = video;
	}


	public List<Video> getListaVideo() {
		return listaVideo;
	}


	public void setListaVideo(List<Video> listaVideo) {
		this.listaVideo = listaVideo;
	}
	
	
	
	
	
	public Pastavideo getPastavideo() {
		return pastavideo;
	}


	public void setPastavideo(Pastavideo pastavideo) {
		this.pastavideo = pastavideo;
	}


	public Ftpdados getFtpdados() {
		return ftpdados;
	}


	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}

	public Ftp getFtp() {
		return ftp;
	}


	public void setFtp(Ftp ftp) {
		this.ftp = ftp;
	}
	
	


	public StreamedContent getMidia() {
		return midia;
	}


	public void setMidia(StreamedContent midia) {
		this.midia = midia;
	}

	public List<VideosBean> getListaVideosBean() {
		return listaVideosBean;
	}


	public void setListaVideosBean(List<VideosBean> listaVideosBean) {
		this.listaVideosBean = listaVideosBean;
	} 
	
	


	public boolean isTabelaVideoQuadro() {
		return tabelaVideoQuadro;
	}


	public void setTabelaVideoQuadro(boolean tabelaVideoQuadro) {
		this.tabelaVideoQuadro = tabelaVideoQuadro;
	}


	public boolean isTabelaVideoLista() {
		return tabelaVideoLista;
	}


	public void setTabelaVideoLista(boolean tabelaVideoLista) {
		this.tabelaVideoLista = tabelaVideoLista;
	}


	public void gerarListaVideos(){
		VideoFacade videoFacade = new VideoFacade();
		listaVideo = videoFacade.listar("Select v From Video v Where v.pastavideo.idpastavideo=" + pastavideo.getIdpastavideo() + " order by v.nome");
		if (listaVideo == null || listaVideo.isEmpty()) {
			listaVideo = new ArrayList<Video>();
		}
	}
	
	public void pegarDadosFtp(){
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public String adicionarVideo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		session.setAttribute("pastavideo", pastavideo);
		RequestContext.getCurrentInstance().openDialog("cadVideo", options, null);
		return "";
	}
	
	public void retornoDialogNovo(SelectEvent event){
		Video video = (Video) event.getObject();
		if (video.getIdvideo() != null) {
			Mensagem.lancarMensagemInfo("Adicionado video com sucesso", "");
			gerarListaVideos();
		}
	}
	
	
	public StreamedContent procurarVideo(){
		InputStream is = null;
		ftp = new Ftp(ftpdados.getHost(), ftpdados.getUser(), ftpdados.getPassword());
		try {
			is = ftp.receberArquivo("", "primeiroVideo.avi", "/videos/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		midia = new DefaultStreamedContent(is, "video/quicktime");
		return midia;
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
		} else if (extensao.equalsIgnoreCase(".pdf") || extensao.equalsIgnoreCase(".flv") || extensao.equalsIgnoreCase(".mp4") || extensao.equalsIgnoreCase(".avi")) {
			return "../../resources/img/icone_pdf.png";
		} else if (extensao.equalsIgnoreCase(".docx") || extensao.equalsIgnoreCase(".doc") || extensao.equalsIgnoreCase(".swf")) {
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
		}
		return "";
	}
	
	
	
	public String retornaPlayrer(String nomeArquivo) {
		if (nomeArquivo == "") {
			return "";
		}
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		if (extensao.length() > 5) {
			extensao = ".pdf";
		}
		if (extensao.equalsIgnoreCase(".pdf")) {
			return "pdf";
		} else if (extensao.equalsIgnoreCase(".swf") || extensao.equalsIgnoreCase(".mp3") || extensao.equalsIgnoreCase(".flv")) {
			return "flash";
		} else if (extensao.equalsIgnoreCase(".ra") || extensao.equalsIgnoreCase(".ram") || extensao.equalsIgnoreCase(".rm") || extensao.equalsIgnoreCase(".rpm")
				 || extensao.equalsIgnoreCase(".rv") || extensao.equalsIgnoreCase(".smi") || extensao.equalsIgnoreCase(".smill")) {
			return "real";
		}else if (extensao.equalsIgnoreCase(".aif") || extensao.equalsIgnoreCase(".aiff") || extensao.equalsIgnoreCase(".aac") || extensao.equalsIgnoreCase(".au")
				 || extensao.equalsIgnoreCase(".mov") || extensao.equalsIgnoreCase(".gsm") || extensao.equalsIgnoreCase(".bmp") || extensao.equalsIgnoreCase(".mid")
				 || extensao.equalsIgnoreCase(".midi") || extensao.equalsIgnoreCase(".mpg") || extensao.equalsIgnoreCase(".mpeg") || extensao.equalsIgnoreCase(".mp4")
				 || extensao.equalsIgnoreCase(".m4a") || extensao.equalsIgnoreCase(".psd") || extensao.equalsIgnoreCase(".qt") || extensao.equalsIgnoreCase(".qtif")
				 || extensao.equalsIgnoreCase(".qti") || extensao.equalsIgnoreCase(".snd") || extensao.equalsIgnoreCase(".tif") || extensao.equalsIgnoreCase(".tiff")
				 || extensao.equalsIgnoreCase(".wav") || extensao.equalsIgnoreCase(".3g2") || extensao.equalsIgnoreCase(".3pg")) {
			return "quicktime";
		} else if (extensao.equalsIgnoreCase(".asx") || extensao.equalsIgnoreCase(".asf") || extensao.equalsIgnoreCase(".avi") || extensao.equalsIgnoreCase(".wma")
				 || extensao.equalsIgnoreCase(".wmv")) {
			return "windows";
		}
		return "";
	}
	
	
	public String voltarPastaVideos(){
		return "consPastasVideos";
	}
	
	
	public String consultarDescricao(Video video) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		session.setAttribute("video", video);
		RequestContext.getCurrentInstance().openDialog("consDescritivoVideo", options, null);
		return "";
	}
	
	
	public void excluirVideo(Video video){
		VideoFacade videoFacade = new VideoFacade();
		if (video != null) {
			videoFacade.excluir(video.getIdvideo());
			Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
			listaVideo.remove(video);
		}
	}
	
	
	public void retornoDialogEdicao(SelectEvent event){
		Video video = (Video) event.getObject();
		if (video.getIdvideo() != null) {
			Mensagem.lancarMensagemInfo("Editado com sucesso", "");
			gerarListaVideos();
		}
	}
	
	
	public boolean verificarAcesso() {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
			acesso = true;
		}else{ 
			acesso = false;
		}
		return acesso;
	}
	
	
	public String trocarCorLinhaTabela(Video video){
		String cor = "background:#FFFFFF;";
		if (video != null) {
			if ((video.getIdvideo() % 2) == 0) {
				cor = "background:#FFFFFF;";
			}else{
				cor = "background:#F3F3F3";
			}	
		}
		return cor;
	}
	
	
	public void verificarExibicao(){
		if (usuarioLogadoMB.getUsuario().getModoexibicao() == null) {
			tabelaVideoQuadro = true;
			tabelaVideoLista = false;
		}else{
			if (usuarioLogadoMB.getUsuario().getModoexibicao().equalsIgnoreCase("I")) {
				tabelaVideoQuadro = true;
				tabelaVideoLista = false;
			}else{
				tabelaVideoLista = true;
				tabelaVideoQuadro = false;
			}
		}
	}
	
	
	public void salvarModoExibicao(String nomeExibicao){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		Usuario usuario = usuarioFacade.consultar(usuarioLogadoMB.getUsuario().getIdusuario());
		usuario.setModoexibicao(nomeExibicao);
		usuarioFacade.salvar(usuario);
		usuarioLogadoMB.setUsuario(usuario);
	}
	
	
	public void listarArquivoLista(){
		tabelaVideoLista = true;
		tabelaVideoQuadro = false;
		salvarModoExibicao("L");
	}
	
	public void listarArquivoQuadro(){
		tabelaVideoQuadro = true;
		tabelaVideoLista = false;
		salvarModoExibicao("I");
	}
	
	
	public String pegarLinkYoutube(Video video){
		String extensao = video.getUrl().substring(video.getUrl().lastIndexOf("/"), video.getUrl().length());
		String novoLink = extensao.substring(1, extensao.length());
		return novoLink;
	}
}
