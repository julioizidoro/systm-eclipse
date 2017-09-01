package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Pasta3Facade;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Pasta3;

@Named
@ViewScoped
public class CadPasta3MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pasta1 pasta1;
	private Pasta2 pasta2;
	private Pasta3 pasta3;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pasta2 = (Pasta2) session.getAttribute("pasta2");
		pasta3 = (Pasta3) session.getAttribute("pasta3");
		session.removeAttribute("pasta2");
		session.removeAttribute("pasta3");
		if (pasta2 != null) {
			pasta1 = pasta2.getPasta1();
		}
		if (pasta3 == null) {
			pasta3 = new Pasta3();
		}
	}

	
	

	public Pasta1 getPasta1() {
		return pasta1;
	}




	public void setPasta1(Pasta1 pasta1) {
		this.pasta1 = pasta1;
	}




	/**
	 * @return the pasta2
	 */
	public Pasta2 getPasta2() {
		return pasta2;
	}




	/**
	 * @param pasta2 the pasta2 to set
	 */
	public void setPasta2(Pasta2 pasta2) {
		this.pasta2 = pasta2;
	}




	/**
	 * @return the pasta3
	 */
	public Pasta3 getPasta3() {
		return pasta3;
	}




	/**
	 * @param pasta3 the pasta3 to set
	 */
	public void setPasta3(Pasta3 pasta3) {
		this.pasta3 = pasta3;
	}




	public String salvar(){
		Pasta3Facade pasta3Facade = new Pasta3Facade();
		pasta3.setPasta1(pasta1);
		pasta3.setPasta2(pasta2);
		pasta3 = pasta3Facade.salvar(pasta3);
		RequestContext.getCurrentInstance().closeDialog(pasta3);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Pasta3());
		return "";
	} 

}
