package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VideoPasta2Facade;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class CadPastaVideos2MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Videopasta2 videopasta2;
	private Videopasta1 videopasta1;
	private String mensagemValidarDados = "";
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		videopasta1 = (Videopasta1) session.getAttribute("videopasta1");
		videopasta2 = (Videopasta2) session.getAttribute("videopasta2");
		session.removeAttribute("videopasta1");
		session.removeAttribute("videoapasta2");
		if (videopasta2 == null) {
			videopasta2 = new Videopasta2();
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
	
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Videopasta2());
	}
	
	
	public boolean validarDados(){
		if (videopasta2.getDescricao() == null || videopasta2.getDescricao().length() == 0) {
			mensagemValidarDados = mensagemValidarDados + " Informe um nome para pasta \r\n";
			return false;
		}
		return true;
	}
	
	
	public void salvar(){
		VideoPasta2Facade videoPasta2Facade = new VideoPasta2Facade();
		videopasta2.setVideopasta1(videopasta1);
		if (validarDados()) {
			videopasta2 = videoPasta2Facade.salvar(videopasta2);
			RequestContext.getCurrentInstance().closeDialog(videopasta2);
		}else{
			Mensagem.lancarMensagemInfo("", mensagemValidarDados);
		}
	}
	
	
	
	

}
