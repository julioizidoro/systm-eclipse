package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Video5Facade;
import br.com.travelmate.model.Video5;


@Named
@ViewScoped
public class DescritivoVideo5MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Video5 video5;
	private boolean editar = false;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		video5 = (Video5) session.getAttribute("video5");
		editar = (boolean) session.getAttribute("editar");
		session.removeAttribute("video5");
		session.removeAttribute("editar");
		if (video5 == null) {
			video5 = new Video5();
		}
	}

	
	
	
	public Video5 getVideo5() {
		return video5;
	}




	public void setVideo5(Video5 video5) {
		this.video5 = video5;
	}




	public boolean isEditar() {
		return editar;
	}




	public void setEditar(boolean editar) {
		this.editar = editar;
	}




	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Video5());
	}
	
	
	public void salvar(){
		Video5Facade video5Facade = new Video5Facade();
		video5 = video5Facade.salvar(video5);
		RequestContext.getCurrentInstance().closeDialog(video5);
	}

	
	public boolean somenteLeituraDescricao(){
		if (editar) {
			return false;
		}else{
			return true;
		}
	}


}
