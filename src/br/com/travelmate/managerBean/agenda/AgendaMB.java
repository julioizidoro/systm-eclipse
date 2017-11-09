package br.com.travelmate.managerBean.agenda;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.managerBean.UsuarioLogadoMB;


@Named
@ViewScoped
public class AgendaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String endereco = "";
	
	
	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario().getEmailagenda() != null) {
			String email = usuarioLogadoMB.getUsuario().getEmailagenda().substring(usuarioLogadoMB.getUsuario().getEmailagenda().indexOf("@"), usuarioLogadoMB.getUsuario().getEmailagenda().length());
	        int posicao = usuarioLogadoMB.getUsuario().getEmailagenda().length();
	        for (int i = 0; i <= email.length(); i++) {
	            posicao = posicao - 1;
	        }
	       endereco = usuarioLogadoMB.getUsuario().getEmailagenda().substring(0, posicao +1);
		}   
	}      


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	

}
