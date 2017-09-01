package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Video2Facade;
import br.com.travelmate.model.Video2;

@Named
@ViewScoped
public class DescritivoVideo2MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Video2 video2;
	private boolean editar = false;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		video2 = (Video2) session.getAttribute("video2");
		editar = (boolean) session.getAttribute("editar");
		session.removeAttribute("video2");
		session.removeAttribute("editar");
		if (video2 == null) {
			video2 = new Video2();
		}
	}


	
	
	
	public Video2 getVideo2() {
		return video2;
	}





	public void setVideo2(Video2 video2) {
		this.video2 = video2;
	}





	public boolean isEditar() {
		return editar;
	}





	public void setEditar(boolean editar) {
		this.editar = editar;
	}





	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Video2());
	}
	
	
	public void salvar(){
		Video2Facade video2Facade = new Video2Facade();
		video2 = video2Facade.salvar(video2);
		RequestContext.getCurrentInstance().closeDialog(video2);
	}

	
	public boolean somenteLeituraDescricao(){
		if (editar) {
			return false;
		}else{
			return true;
		}
	}



}
