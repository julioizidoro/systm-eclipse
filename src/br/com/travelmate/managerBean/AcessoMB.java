package br.com.travelmate.managerBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AcessoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String acesso = "";
	
	
	@PostConstruct
	public void init(){
	}


	public String getAcesso() {
		return acesso;
	}


	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}
	
	

}
