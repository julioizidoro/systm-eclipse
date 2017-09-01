package br.com.travelmate.managerBean.voluntariado.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.model.Controlevoluntariado;
import br.com.travelmate.model.Voluntariado;

@Named
@ViewScoped
public class AtualizarControleVoluntariadoMB implements Serializable{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controlevoluntariado controle;
	private Voluntariado voluntariado;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controle = (Controlevoluntariado) session.getAttribute("controle");
        session.removeAttribute("controle");
        consultarVoluntariado();
	}
	
	public Controlevoluntariado getControle() {
		return controle;
	}
	
	public void setControle(Controlevoluntariado controle) {
		this.controle = controle;
	}

	public Voluntariado getVoluntariado() {
		return voluntariado;
	}

	public void setVoluntariado(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
	}

	public void consultarVoluntariado(){
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		voluntariado = voluntariadoFacade.consultar(controle.getVendas().getIdvendas());
		controle.setDataembarque(voluntariado.getDataInicioVoluntariado());
	}
	
	public String salvar(){
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		controle = voluntariadoFacade.salvar(controle);
		RequestContext.getCurrentInstance().closeDialog(controle);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
