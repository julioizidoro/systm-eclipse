package br.com.travelmate.managerBean.financeiro.terceiros;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.model.Terceiros;

@Named
@ViewScoped
public class CadTerceirosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Terceiros terceiros;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		terceiros = (Terceiros) session.getAttribute("terceiros");
		session.removeAttribute("terceiros");
		if(terceiros == null){
			terceiros = new Terceiros();
		}
	}

	
	public Terceiros getTerceiros() {
		return terceiros;
	}


	public void setTerceiros(Terceiros terceiros) {
		this.terceiros = terceiros;
	}

	public String salvar(){
		TerceirosFacade terceirosFacade = new TerceirosFacade();
		terceirosFacade.salvar(terceiros);
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
