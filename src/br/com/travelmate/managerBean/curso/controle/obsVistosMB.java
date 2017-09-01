package br.com.travelmate.managerBean.curso.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.model.Controlecurso;

@Named
@ViewScoped
public class obsVistosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controlecurso controlecurso;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controlecurso = (Controlecurso) session.getAttribute("controlecurso");
        session.removeAttribute("controlecurso");
	}


	public Controlecurso getControlecurso() {
		return controlecurso;
	}


	public void setControlecurso(Controlecurso controlecurso) {
		this.controlecurso = controlecurso;
	}
	
	
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Controlecurso());
	}
	
	
	
	public void salvar(){
		CursoFacade cursoFacade = new CursoFacade();
		if (controlecurso.getObsvisto() != null) {
			controlecurso = cursoFacade.salvar(controlecurso);
			RequestContext.getCurrentInstance().closeDialog(controlecurso);
		}
	}

}
