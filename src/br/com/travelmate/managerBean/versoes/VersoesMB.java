package br.com.travelmate.managerBean.versoes;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.model.Versaousuario;

@Named
@ViewScoped
public class VersoesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Versaousuario> listaVersoes;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaVersoes = (List<Versaousuario>) session.getAttribute("listaVersoes");
		session.removeAttribute("listaVersoes");
	}

	public List<Versaousuario> getListaVersoes() {
		return listaVersoes;
	}

	public void setListaVersoes(List<Versaousuario> listaVersoes) {
		this.listaVersoes = listaVersoes;
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
