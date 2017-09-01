package br.com.travelmate.managerBean.guiaescola;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
 
import br.com.travelmate.model.Guiaescola;

@Named
@ViewScoped
public class GuiaEscolaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Guiaescola guiaescola;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		guiaescola = (Guiaescola) session.getAttribute("guiaescola"); 
	}

	public Guiaescola getGuiaescola() {
		return guiaescola;
	}

	public void setGuiaescola(Guiaescola guiaescola) {
		this.guiaescola = guiaescola;
	}
	
	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
