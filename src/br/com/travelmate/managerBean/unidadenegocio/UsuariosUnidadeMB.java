package br.com.travelmate.managerBean.unidadenegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class UsuariosUnidadeMB implements Serializable{

	/**
	 * 
	 */   
	private static final long serialVersionUID = 1L;
	private List<Usuario> listaUsuario;
	private List<Usuario> listaUsuarioAtivo;
	private List<Usuario> listaUsuarioInativo;
	private Unidadenegocio unidadenegocio;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		unidadenegocio = (Unidadenegocio) session.getAttribute("unidadenegocio");
		session.removeAttribute("unidadenegocio");
		if (unidadenegocio != null) {
			listaUsuario = GerarListas.listarUsuarios("Select u From Usuario u Where u.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio());
		}
		gerarListaUsuarioAtivoInativo();
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	public List<Usuario> getListaUsuarioAtivo() {
		return listaUsuarioAtivo;
	}


	public void setListaUsuarioAtivo(List<Usuario> listaUsuarioAtivo) {
		this.listaUsuarioAtivo = listaUsuarioAtivo;
	}


	public List<Usuario> getListaUsuarioInativo() {
		return listaUsuarioInativo;
	}


	public void setListaUsuarioInativo(List<Usuario> listaUsuarioInativo) {
		this.listaUsuarioInativo = listaUsuarioInativo;
	}
	
	
	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public void gerarListaUsuarioAtivoInativo(){
		listaUsuarioAtivo = new ArrayList<Usuario>();
		listaUsuarioInativo = new ArrayList<Usuario>();
		if (listaUsuario != null) {
			for (int i = 0; i < listaUsuario.size(); i++) {
				if (listaUsuario.get(i).getSituacao().equalsIgnoreCase("Ativo")) {
					listaUsuarioAtivo.add(listaUsuario.get(i));
				}else{
					listaUsuarioInativo.add(listaUsuario.get(i));
				}
			}
		}
	}
	
	

}
