package br.com.travelmate.managerBean.workAndTravel.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.model.Controlework;
import br.com.travelmate.model.Worktravel;

@Named
@ViewScoped
public class AtualizarControleWorkMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controlework controlework;
	private Worktravel workTravel;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controlework = (Controlework) session.getAttribute("controle");
        session.removeAttribute("controle");
        consultarWork();
	}

	public Controlework getControlework() {
		return controlework;
	}

	public void setControlework(Controlework controlework) {
		this.controlework = controlework;
	}
	
	public Worktravel getWorkTravel() {
		return workTravel;
	}

	public void setWorkTravel(Worktravel workTravel) {
		this.workTravel = workTravel;
	}

	
	public void consultarWork(){
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		workTravel = workTravelFacade.consultarWork(controlework.getVendas().getIdvendas());
		controlework.setSkype(workTravel.getVendas().getCliente().getSkype());
		//controlework.setDataEmbarque(workTravel.getDataInicioPretendida01());
	}
	
	
	public String salvar(){
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		workTravel = workTravelFacade.salvar(workTravel);
		controlework = workTravelFacade.salvar(controlework);
		RequestContext.getCurrentInstance().closeDialog(controlework);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	
}
