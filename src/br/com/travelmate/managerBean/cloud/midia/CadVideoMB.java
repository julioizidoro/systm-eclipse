package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.VideoFacade;
import br.com.travelmate.model.Pastavideo;
import br.com.travelmate.model.Video;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadVideoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Video video;
	private Pastavideo pastavideo;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private String nomeArquivo;
	private String descricao = "";
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pastavideo = (Pastavideo) session.getAttribute("pastavideo");
		video = (Video) session.getAttribute("video");
		session.removeAttribute("pastavideo");
		if (video == null) {
			video = new Video();
		}else{
			descricao = video.getDescricao();
			nomeArquivo = video.getNome();
			pastavideo = video.getPastavideo();
		}
	}



	public Video getVideo() {
		return video;
	}



	public void setVideo(Video video) {
		this.video = video;
	}



	public Pastavideo getPastavideo() {
		return pastavideo;
	}



	public void setPastavideo(Pastavideo pastavideo) {
		this.pastavideo = pastavideo;
	}



	public String getNomeArquivoFTP() {
		return nomeArquivoFTP;
	}



	public void setNomeArquivoFTP(String nomeArquivoFTP) {
		this.nomeArquivoFTP = nomeArquivoFTP;
	}



	public UploadedFile getFile() {
		return file;
	}



	public void setFile(UploadedFile file) {
		this.file = file;
	}



	public FileUploadEvent getEx() {
		return ex;
	}



	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}



	public List<String> getListaNomeArquivo() {
		return listaNomeArquivo;
	}



	public void setListaNomeArquivo(List<String> listaNomeArquivo) {
		this.listaNomeArquivo = listaNomeArquivo;
	}



	public List<UploadedFile> getListaFile() {
		return listaFile;
	}



	public void setListaFile(List<UploadedFile> listaFile) {
		this.listaFile = listaFile;
	}
	
	
	
	
	
	public String getNomeArquivo() {
		return nomeArquivo;
	}



	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	


	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	

	
	
	public String salvar() {
		VideoFacade videoFacade = new VideoFacade();
		String msg = validarDados();
		if (msg.length() == 0) {
			video.setPastavideo(pastavideo);
			video.setDescricao(descricao);
			video.setNome(nomeArquivo);
			video = videoFacade.salvar(video);
			RequestContext.getCurrentInstance().closeDialog(video);
		}else{
			Mensagem.lancarMensagemInfo("", msg);
		}
		return "";
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Video());
	}
	
	
	public String validarDados(){
		String mensagem = "";
		if (nomeArquivo.length() == 0) {
			mensagem = mensagem + " Informe o nome do video \r\n";
		}
		if (descricao.length() == 0) {
			mensagem = mensagem + " Informe a descrição do video \r\n";
		}
		if (video.getUrl().length() == 0) {
			mensagem = mensagem + " Informe o link do video \r\n";
		}
		return mensagem;
	}
	


}
