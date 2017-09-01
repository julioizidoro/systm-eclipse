package br.com.travelmate.managerBean.cancelamento;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class EditarValoresCancelamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float valor;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        valor = (float) session.getAttribute("valorcampo");
        session.removeAttribute("valorcampo");
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public String confirmar(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("valorcampo", valor);
        session.setAttribute("operacao", "confirmada");
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
		
	}
	
	public String cancelar(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("operacao", "cancelada");
        session.setAttribute("valorcampo", 0.0f);
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
		
	}

}
