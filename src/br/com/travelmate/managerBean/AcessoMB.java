package br.com.travelmate.managerBean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AcessoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String acesso = "";
	
	
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("inicial.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public String getAcesso() {
		return acesso;
	}


	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}
	
	

}
