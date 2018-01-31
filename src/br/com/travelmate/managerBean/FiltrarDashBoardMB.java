package br.com.travelmate.managerBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;



@Named
@ViewScoped
public class FiltrarDashBoardMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mes;
	private int ano;
	
	
	@PostConstruct
	public void init(){
	}

  
	  
	
	
	public int getMes() {
		return mes;
	}





	public void setMes(int mes) {
		this.mes = mes;
	}





	public int getAno() {
		return ano;
	}





	public void setAno(int ano) {
		this.ano = ano;
	}

	

	public String gerarRelatorio(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("mes", mes);
		session.setAttribute("ano", ano);
		return "relatorioDashBoard";
	}  
	
	
	public void fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}

}
