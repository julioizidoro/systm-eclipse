package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Video4Facade;
import br.com.travelmate.model.Video4;


@Named
@ViewScoped
public class DescritivoVideo4MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Video4 video4;
	private boolean editar = false;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		video4 = (Video4) session.getAttribute("video4");
		editar = (boolean) session.getAttribute("editar");
		session.removeAttribute("video4");
		session.removeAttribute("editar");
		if (video4 == null) {
			video4 = new Video4();
		}
	}

	
	
	
	public Video4 getVideo4() {
		return video4;
	}




	public void setVideo4(Video4 video4) {
		this.video4 = video4;
	}




	public boolean isEditar() {
		return editar;
	}




	public void setEditar(boolean editar) {
		this.editar = editar;
	}




	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Video4());
	}
	
	
	public void salvar(){
		Video4Facade video4Facade = new Video4Facade();
		video4 = video4Facade.salvar(video4);
		RequestContext.getCurrentInstance().closeDialog(video4);
	}

	
	public boolean somenteLeituraDescricao(){
		if (editar) {
			return false;
		}else{
			return true;
		}
	}



}
