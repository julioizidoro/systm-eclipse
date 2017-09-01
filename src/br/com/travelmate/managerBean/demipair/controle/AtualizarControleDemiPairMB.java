package br.com.travelmate.managerBean.demipair.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.model.Controledemipair;
import br.com.travelmate.model.Demipair;

@Named
@ViewScoped
public class AtualizarControleDemiPairMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controledemipair controledemi;
	private Demipair demipair;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controledemi = (Controledemipair) session.getAttribute("controle");
        session.removeAttribute("controle");
        consultarDemi();
	}

	public Controledemipair getControledemi() {
		return controledemi;
	}

	public void setControledemi(Controledemipair controledemi) {
		this.controledemi = controledemi;
	}

	public Demipair getDemipair() {
		return demipair;
	}

	public void setDemipair(Demipair demipair) {
		this.demipair = demipair;
	}

	public void consultarDemi(){
		DemipairFacade demipairFacade = new DemipairFacade();
		demipair = demipairFacade.consultar(controledemi.getVendas().getIdvendas());
	}
	
	
	public String salvar(){
		DemipairFacade demipairFacade = new DemipairFacade();
		controledemi = demipairFacade.salvar(controledemi);
		RequestContext.getCurrentInstance().closeDialog(controledemi);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
