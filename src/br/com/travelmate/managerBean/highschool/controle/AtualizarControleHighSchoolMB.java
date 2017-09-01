package br.com.travelmate.managerBean.highschool.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.model.Controlehighschool;

@Named
@ViewScoped
public class AtualizarControleHighSchoolMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controlehighschool controlehighschool;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controlehighschool = (Controlehighschool) session.getAttribute("controle");
        session.removeAttribute("controle");
	}

	public Controlehighschool getControlehighschool() {
		return controlehighschool;
	}

	public void setControlehighschool(Controlehighschool controlehighschool) {
		this.controlehighschool = controlehighschool;
	}

	public String salvar(){
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		controlehighschool.setHighschool(highSchoolFacade.salvar(controlehighschool.getHighschool()));
		controlehighschool = highSchoolFacade.salvar(controlehighschool); 
		RequestContext.getCurrentInstance().closeDialog(controlehighschool);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
