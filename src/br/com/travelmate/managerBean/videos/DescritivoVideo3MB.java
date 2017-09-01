package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Video2Facade;
import br.com.travelmate.facade.Video3Facade;
import br.com.travelmate.model.Video2;
import br.com.travelmate.model.Video3;


@Named
@ViewScoped
public class DescritivoVideo3MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Video3 video3;
	private boolean editar = false;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		video3 = (Video3) session.getAttribute("video3");
		editar = (boolean) session.getAttribute("editar");
		session.removeAttribute("video2");
		session.removeAttribute("editar");
		if (video3 == null) {
			video3 = new Video3();
		}
	}


	
	
	public Video3 getVideo3() {
		return video3;
	}




	public void setVideo3(Video3 video3) {
		this.video3 = video3;
	}




	public boolean isEditar() {
		return editar;
	}




	public void setEditar(boolean editar) {
		this.editar = editar;
	}




	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Video3());
	}
	
	
	public void salvar(){
		Video3Facade video3Facade = new Video3Facade();
		video3 = video3Facade.salvar(video3);
		RequestContext.getCurrentInstance().closeDialog(video3);
	}

	
	public boolean somenteLeituraDescricao(){
		if (editar) {
			return false;
		}else{
			return true;
		}
	}

}
