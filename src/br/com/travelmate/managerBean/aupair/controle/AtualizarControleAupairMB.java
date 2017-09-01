package br.com.travelmate.managerBean.aupair.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Controleaupair;

@Named
@ViewScoped
public class AtualizarControleAupairMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controleaupair controleAupair;
	private Aupair aupair;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controleAupair = (Controleaupair) session.getAttribute("controle");
        session.removeAttribute("controle");
        consultarAupair();
	}

	public Controleaupair getcontroleAupair() {
		return controleAupair;
	}

	public void setcontroleAupair(Controleaupair controleAupair) {
		this.controleAupair = controleAupair;
	}
	
	
	public Controleaupair getControleAupair() {
		return controleAupair;
	}

	public void setControleAupair(Controleaupair controleAupair) {
		this.controleAupair = controleAupair;
	}

	public Aupair getAupair() {
		return aupair;
	}

	public void setAupair(Aupair aupair) {
		this.aupair = aupair;
	}


	public void consultarAupair(){
		AupairFacade aupairFacade = new AupairFacade();
		aupair = aupairFacade.consultar(controleAupair.getVendas().getIdvendas());
		controleAupair.setDataembarque(aupair.getDataInicioPretendida01());
	}
	
	
	public String salvar(){
		AupairFacade aupairFacade = new AupairFacade();
		aupair = aupairFacade.salvar(aupair);
		controleAupair = aupairFacade.salvar(controleAupair);
		RequestContext.getCurrentInstance().closeDialog(controleAupair);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
