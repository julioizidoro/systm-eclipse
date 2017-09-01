package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;
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
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.PastaVideoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Pastavideo;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class PastaVideosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pastavideo pastavideo;
	private List<Pastavideo> listaPastaVideo;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<String> listaVideo;
	private UploadedFile file;
	
	
	@PostConstruct
	public void init(){
		gerarListaPastaVideo();
	}


	public Pastavideo getPastavideo() {
		return pastavideo;
	}


	public void setPastavideo(Pastavideo pastavideo) {
		this.pastavideo = pastavideo;
	}


	public List<Pastavideo> getListaPastaVideo() {
		return listaPastaVideo;
	}


	public void setListaPastaVideo(List<Pastavideo> listaPastaVideo) {
		this.listaPastaVideo = listaPastaVideo;
	}
	
	
	
	
	
	public List<String> getListaVideo() {
		return listaVideo;
	}


	public void setListaVideo(List<String> listaVideo) {
		this.listaVideo = listaVideo;
	}
	
	


	public UploadedFile getFile() {
		return file;
	}


	public void setFile(UploadedFile file) {
		this.file = file;
	}


	public void gerarListaPastaVideo(){
		PastaVideoFacade pastaVideoFacade = new PastaVideoFacade();
		listaPastaVideo = pastaVideoFacade.listar("Select p From Pastavideo p order by p.nome");
		if (listaPastaVideo == null || listaPastaVideo.isEmpty()) {
			listaPastaVideo = new ArrayList<Pastavideo>();
		}
	}
	
	public String videos(Pastavideo pastavideo){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("pastavideo", pastavideo);
		return "consVideo";
	}
	
	public String posicaoInteracaoUiRepeat(String posicao){
		String classe = "";
		if (posicao.equalsIgnoreCase("0") || posicao.equalsIgnoreCase("4") || posicao.equalsIgnoreCase("8") || posicao.equalsIgnoreCase("12") || posicao.equalsIgnoreCase("16") || posicao.equalsIgnoreCase("20") || posicao.equalsIgnoreCase("24") || posicao.equalsIgnoreCase("28") || posicao.equalsIgnoreCase("32") || posicao.equalsIgnoreCase("36") || posicao.equalsIgnoreCase("40")  ) {
			classe = "panel-stat3 bg-danger";
			return classe;
		}else if(posicao.equalsIgnoreCase("1") || posicao.equalsIgnoreCase("5") || posicao.equalsIgnoreCase("9") || posicao.equalsIgnoreCase("13") || posicao.equalsIgnoreCase("17") || posicao.equalsIgnoreCase("21") || posicao.equalsIgnoreCase("25") || posicao.equalsIgnoreCase("29") || posicao.equalsIgnoreCase("33") || posicao.equalsIgnoreCase("37") || posicao.equalsIgnoreCase("41") ){
			classe = "panel-stat3 bg-info";
			return classe;
		}else if (posicao.equalsIgnoreCase("2") || posicao.equalsIgnoreCase("6") || posicao.equalsIgnoreCase("10") || posicao.equalsIgnoreCase("14") || posicao.equalsIgnoreCase("18") || posicao.equalsIgnoreCase("22") || posicao.equalsIgnoreCase("26") || posicao.equalsIgnoreCase("30") || posicao.equalsIgnoreCase("34") || posicao.equalsIgnoreCase("38") || posicao.equalsIgnoreCase("42") ){
			classe = "panel-stat3 bg-warning";
			return classe;
		}else{
			classe = "panel-stat3 bg-success";
			return classe;
		}
	}
	
	
	public Integer retornarNumeroVideos(Pastavideo pastavideo){
		Integer nVideos = null;
		if (pastavideo.getVideoList() == null) {
			nVideos = 0;
		}else{
			nVideos = pastavideo.getVideoList().size();
		}
		return nVideos;
	}
	
	public void retornoDialogNovo(SelectEvent event){
		Pastavideo pastavideo = (Pastavideo) event.getObject();
		if (pastavideo.getIdpastavideo() == null) {
			gerarListaPastaVideo();
		}else{
			Mensagem.lancarMensagemInfo("Pasta criada com sucesso", "");
			gerarListaPastaVideo();
		}
	}
	
	public void retornoDialogEdicao(SelectEvent event){
		Pastavideo pastavideo = (Pastavideo) event.getObject();
		if (pastavideo.getIdpastavideo() == null) {
			gerarListaPastaVideo();
		}else{
			Mensagem.lancarMensagemInfo("Pasta alterada com sucesso", "");
			gerarListaPastaVideo();
		}
	}
	
	
	public String cadastroPastaVideos() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("cadPastasVideos", options, null);
		return "";
	}
	
	public String editarPastaVideo(Pastavideo pastavideo) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("pastavideo", pastavideo);
		RequestContext.getCurrentInstance().openDialog("cadPastasVideos", options, null);
		return "";
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
	
	    
	
	
	
	
  
}
