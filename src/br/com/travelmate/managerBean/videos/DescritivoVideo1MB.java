package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Video1Facade;
import br.com.travelmate.model.Video1;


@Named
@ViewScoped
public class DescritivoVideo1MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Video1 video1;
	private boolean editar = false;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		video1 = (Video1) session.getAttribute("video1");
		editar = (boolean) session.getAttribute("editar");
		session.removeAttribute("video1");
		session.removeAttribute("editar");
		if (video1 == null) {
			video1 = new Video1();
		}
	}


	public Video1 getVideo1() {
		return video1;
	}


	public void setVideo1(Video1 video1) {
		this.video1 = video1;
	}


	public boolean isEditar() {
		return editar;
	}


	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Video1());
	}
	
	
	public void salvar(){
		Video1Facade video1Facade = new Video1Facade();
		video1 = video1Facade.salvar(video1);
		RequestContext.getCurrentInstance().closeDialog(video1);
	}

	
	public boolean somenteLeituraDescricao(){
		if (editar) {
			return false;
		}else{
			return true;
		}
	}


}
