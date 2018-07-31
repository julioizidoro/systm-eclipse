package br.com.travelmate.managerBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ConteudoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private DashBoardMB dashBoardMB;
	private String dados = "";
	
	
	@PostConstruct
	public void init() {
		
	}


	public String getDados() {
		return dados;
	}


	public void setDados(String dados) {
		this.dados = dados;
	}
	
	
	

}
