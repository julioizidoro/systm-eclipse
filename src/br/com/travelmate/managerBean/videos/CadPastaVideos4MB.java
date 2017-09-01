package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VideoPasta3Facade;
import br.com.travelmate.facade.VideoPasta4Facade;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.model.Videopasta4;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class CadPastaVideos4MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Videopasta3 videopasta3;
	private Videopasta2 videopasta2;
	private Videopasta1 videopasta1;
	private String mensagemValidarDados = "";
	private Videopasta4 videopasta4;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		videopasta3 = (Videopasta3) session.getAttribute("videopasta3");
		videopasta4 = (Videopasta4) session.getAttribute("videopasta4");
		session.removeAttribute("videopasta3");
		session.removeAttribute("videopasta4");
		if (videopasta3 != null) {
			videopasta2 = videopasta3.getVideopasta2();
			videopasta1 = videopasta3.getVideopasta1();
		}
		if (videopasta4 == null) {
			videopasta4 = new Videopasta4();
		}else{
			videopasta3 = videopasta4.getVideopasta3();
			videopasta2 = videopasta4.getVideopasta2();
			videopasta1  = videopasta4.getVideopasta1();
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
	
	


	public Videopasta4 getVideopasta4() {
		return videopasta4;
	}


	public void setVideopasta4(Videopasta4 videopasta4) {
		this.videopasta4 = videopasta4;
	}


	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Videopasta4());
	}
	
	
	public boolean validarDados(){
		if (videopasta4.getDescricao() == null || videopasta4.getDescricao().length() == 0) {
			mensagemValidarDados = mensagemValidarDados + " Informe um nome para pasta \r\n";
			return false;
		}
		return true;
	}
	
	
	public void salvar(){
		VideoPasta4Facade videoPasta4Facade = new VideoPasta4Facade();
		videopasta4.setVideopasta1(videopasta1);
		videopasta4.setVideopasta2(videopasta2);
		videopasta4.setVideopasta3(videopasta3);
		if (validarDados()) {
			videopasta4 = videoPasta4Facade.salvar(videopasta4);
			RequestContext.getCurrentInstance().closeDialog(videopasta4);
		}else{
			Mensagem.lancarMensagemInfo("", mensagemValidarDados);
		}
	}

}
