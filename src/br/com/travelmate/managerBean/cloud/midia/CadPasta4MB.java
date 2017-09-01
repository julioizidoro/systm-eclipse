package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Pasta4Facade;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Pasta4;


@Named
@ViewScoped
public class CadPasta4MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Pasta1 pasta1;
	private Pasta2 pasta2;
	private Pasta3 pasta3;
	private Pasta4 pasta4;
	private Departamento departamento;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pasta3 = (Pasta3) session.getAttribute("pasta3");
		pasta4 = (Pasta4) session.getAttribute("pasta4");
		session.removeAttribute("pasta3");
		session.removeAttribute("pasta4");
		if (pasta3 != null) {
			pasta2 = pasta3.getPasta2();
			pasta1 = pasta3.getPasta1();
			departamento = pasta1.getDepartamento();
		}
		if (pasta4 == null) {
			pasta4 = new Pasta4();
		}
	}

	public Pasta4 getPasta4() {
		return pasta4;
	}


	public void setPasta4(Pasta4 pasta4) {
		this.pasta4 = pasta4;
	}





	public Pasta2 getPasta2() {
		return pasta2;
	}





	public void setPasta2(Pasta2 pasta2) {
		this.pasta2 = pasta2;
	}





	public Pasta3 getPasta3() {
		return pasta3;
	}





	public void setPasta3(Pasta3 pasta3) {
		this.pasta3 = pasta3;
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
		Pasta4Facade pasta4Facade = new Pasta4Facade();
		pasta4.setPasta1(pasta1);
		pasta4.setPasta2(pasta2);
		pasta4.setPasta3(pasta3);
		pasta4 = pasta4Facade.salvar(pasta4);
		RequestContext.getCurrentInstance().closeDialog(pasta4);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Pasta4());
		return "";
	} 

}
