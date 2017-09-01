package br.com.travelmate.managerBean.trainee.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.TraineeFacade;
import br.com.travelmate.model.Controletrainee;
import br.com.travelmate.model.Trainee;

@Named
@ViewScoped
public class AtualizarControleTraineeMB implements Serializable{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controletrainee controle;
	private Trainee trainee;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controle = (Controletrainee) session.getAttribute("controle");
        session.removeAttribute("controle");
	}
	

	public Controletrainee getControle() {
		return controle;
	}


	public void setControle(Controletrainee controle) {
		this.controle = controle;
	}


	public Trainee getTrainee() {
		return trainee;
	}


	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public String salvar(){
		TraineeFacade traineeFacade = new TraineeFacade();
		controle = traineeFacade.salvar(controle);
		RequestContext.getCurrentInstance().closeDialog(controle);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
