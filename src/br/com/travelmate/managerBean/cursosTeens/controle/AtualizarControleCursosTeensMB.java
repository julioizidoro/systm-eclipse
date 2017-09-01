package br.com.travelmate.managerBean.cursosTeens.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.model.Controleprogramasteen;

@Named
@ViewScoped
public class AtualizarControleCursosTeensMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controleprogramasteen controleprogramasteen;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controleprogramasteen = (Controleprogramasteen) session.getAttribute("controle");
        session.removeAttribute("controle");
	}

	

	public Controleprogramasteen getControleprogramasteen() {
		return controleprogramasteen;
	}



	public void setControleprogramasteen(Controleprogramasteen controleprogramasteen) {
		this.controleprogramasteen = controleprogramasteen;
	}



	public String salvar(){
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		controleprogramasteen = programasTeensFacede.salvar(controleprogramasteen);
		RequestContext.getCurrentInstance().closeDialog(controleprogramasteen);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
