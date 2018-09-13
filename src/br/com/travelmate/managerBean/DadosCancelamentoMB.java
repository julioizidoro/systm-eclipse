package br.com.travelmate.managerBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Cancelamento;

@Named
@ViewScoped
public class DadosCancelamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cancelamento cancelamento;
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cancelamento = (Cancelamento) session.getAttribute("cancelamento");
		session.removeAttribute("cancelamento");
	
	}


	public Cancelamento getCancelamento() {
		return cancelamento;
	}


	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}
	
	
	

}
