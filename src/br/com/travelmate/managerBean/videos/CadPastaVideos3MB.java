package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VideoPasta2Facade;
import br.com.travelmate.facade.VideoPasta3Facade;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class CadPastaVideos3MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Videopasta3 videopasta3;
	private Videopasta2 videopasta2;
	private Videopasta1 videopasta1;
	private String mensagemValidarDados = "";
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		videopasta2 = (Videopasta2) session.getAttribute("videopasta2");
		videopasta3 = (Videopasta3) session.getAttribute("videopasta3");
		session.removeAttribute("videopasta2");
		session.removeAttribute("videopasta3");
		if (videopasta2 != null) {
			videopasta1 = videopasta2.getVideopasta1();
		}
		if (videopasta3 == null) {
			videopasta3 = new Videopasta3();
		}
	}


	public Videopasta2 getVideopasta2() {
		return videopasta2;
	}


	public void setVideopasta2(Videopasta2 videopasta2) {
		this.videopasta2 = videopasta2;
	}


	public Videopasta1 getVideopasta1() {
		return videopasta1;
	}


	public void setVideopasta1(Videopasta1 videopasta1) {
		this.videopasta1 = videopasta1;
	}
	
	
	
	
	
	public Videopasta3 getVideopasta3() {
		return videopasta3;
	}


	public void setVideopasta3(Videopasta3 videopasta3) {
		this.videopasta3 = videopasta3;
	}


	public String getMensagemValidarDados() {
		return mensagemValidarDados;
	}


	public void setMensagemValidarDados(String mensagemValidarDados) {
		this.mensagemValidarDados = mensagemValidarDados;
	}


	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Videopasta3());
	}
	
	
	public boolean validarDados(){
		if (videopasta3.getDescricao() == null || videopasta3.getDescricao().length() == 0) {
			mensagemValidarDados = mensagemValidarDados + " Informe um nome para pasta \r\n";
			return false;
		}
		return true;
	}
	
	
	public void salvar(){
		VideoPasta3Facade videoPasta3Facade = new VideoPasta3Facade();
		videopasta3.setVideopasta1(videopasta1);
		videopasta3.setVideopasta2(videopasta2);
		if (validarDados()) {
			videopasta3 = videoPasta3Facade.salvar(videopasta3);
			RequestContext.getCurrentInstance().closeDialog(videopasta3);
		}else{
			Mensagem.lancarMensagemInfo("", mensagemValidarDados);
		}
	}

}
