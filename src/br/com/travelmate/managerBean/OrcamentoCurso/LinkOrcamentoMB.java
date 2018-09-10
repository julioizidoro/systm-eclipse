package br.com.travelmate.managerBean.OrcamentoCurso;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@Named
@ViewScoped
public class LinkOrcamentoMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String texto;
	
	@PostConstruct
    public void init(){ 
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        texto =  (String) session.getAttribute("texto");
        session.removeAttribute("texto"); 
	}
	  
	public String getTexto() {
		return texto;
	}
 
	public void setTexto(String texto) {
		this.texto = texto;
	}  
}
