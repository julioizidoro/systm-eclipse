package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Pasta1Facade;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Departamento;

@Named
@ViewScoped
public class CadPasta1MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pasta1 pasta1;
	private Departamento departamento;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		departamento = (Departamento) session.getAttribute("departamento");
		pasta1 = (Pasta1) session.getAttribute("pasta1");
		session.removeAttribute("pasta1");
		session.removeAttribute("departamento");
		if (pasta1 == null) {
			pasta1 = new Pasta1();
		}
	}


	


	/**
	 * @return the pasta1
	 */
	public Pasta1 getPasta1() {
		return pasta1;
	}





	/**
	 * @param pasta1 the pasta1 to set
	 */
	public void setPasta1(Pasta1 pasta1) {
		this.pasta1 = pasta1;
	}





	public Departamento getDepartamento() {
		return departamento;
	}


	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public String salvar(){
		Pasta1Facade cloudPastasFacade = new Pasta1Facade();
		pasta1.setDepartamento(departamento);
		pasta1 = cloudPastasFacade.salvar(pasta1);
		RequestContext.getCurrentInstance().closeDialog(pasta1);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Pasta1());
		return "";
	} 

}
