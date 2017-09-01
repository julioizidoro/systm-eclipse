package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VideoPasta4Facade;
import br.com.travelmate.facade.VideoPasta5Facade;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.model.Videopasta4;
import br.com.travelmate.model.Videopasta5;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class CadPastaVideos5MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Videopasta3 videopasta3;
	private Videopasta2 videopasta2;
	private Videopasta1 videopasta1;
	private String mensagemValidarDados = "";
	private Videopasta4 videopasta4;
	private Videopasta5 videopasta5;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		videopasta4 = (Videopasta4) session.getAttribute("videopasta4");
		videopasta5 = (Videopasta5) session.getAttribute("videopasta5");
		session.removeAttribute("videopasta4");
		session.removeAttribute("videopasta5");
		if (videopasta4 != null) {
			videopasta2 = videopasta4.getVideopasta2();
			videopasta1 = videopasta4.getVideopasta1();
			videopasta3 = videopasta4.getVideopasta3();
		}
		if (videopasta5 == null) {
			videopasta5 = new Videopasta5();
		}else{
			videopasta3 = videopasta5.getVideopasta3();
			videopasta2 = videopasta5.getVideopasta2();
			videopasta1  = videopasta5.getVideopasta1();
			videopasta4 = videopasta5.getVideopasta4();
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
	
	


	public Videopasta5 getVideopasta5() {
		return videopasta5;
	}


	public void setVideopasta5(Videopasta5 videopasta5) {
		this.videopasta5 = videopasta5;
	}


	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Videopasta5());
	}
	
	
	public boolean validarDados(){
		if (videopasta5.getDescricao() == null || videopasta5.getDescricao().length() == 0) {
			mensagemValidarDados = mensagemValidarDados + " Informe um nome para pasta \r\n";
			return false;
		}
		return true;
	}
	
	
	public void salvar(){
		VideoPasta5Facade videoPasta5Facade = new VideoPasta5Facade();
		videopasta5.setVideopasta1(videopasta1);
		videopasta5.setVideopasta2(videopasta2);
		videopasta5.setVideopasta3(videopasta3);
		videopasta5.setVideopasta4(videopasta4);
		if (validarDados()) {
			videopasta5 = videoPasta5Facade.salvar(videopasta5);
			RequestContext.getCurrentInstance().closeDialog(videopasta5);
		}else{
			Mensagem.lancarMensagemInfo("", mensagemValidarDados);
		}
	}

}
