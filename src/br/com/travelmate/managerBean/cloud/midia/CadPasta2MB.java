package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Pasta2Facade;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;

@Named
@ViewScoped
public class CadPasta2MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pasta1 pasta1;
	private Pasta2 pasta2;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pasta1 = (Pasta1) session.getAttribute("pasta1");
		pasta2 = (Pasta2) session.getAttribute("pasta2");
		session.removeAttribute("pasta2");
		session.removeAttribute("pasta1");
		if (pasta2 == null) {
			pasta2 = new Pasta2();
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





	public String salvar(){
		Pasta2Facade pasta2Facade = new Pasta2Facade();
		pasta2.setPasta1(pasta1);
		pasta2 = pasta2Facade.salvar(pasta2);
		RequestContext.getCurrentInstance().closeDialog(pasta2);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Pasta2());
		return "";
	} 

}
