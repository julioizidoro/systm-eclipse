package br.com.travelmate.managerBean.cancelamento;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Credito;

@Named
@ViewScoped
public class VisualizarIdCreditoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Credito credito;
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		credito = (Credito) session.getAttribute("credito");
		session.removeAttribute("credito");
	}


	public Credito getCredito() {
		return credito;
	}


	public void setCredito(Credito credito) {
		this.credito = credito;
	}
	
	
	
	
	

}
