package br.com.travelmate.managerBean.crm;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
 
import br.com.travelmate.model.Vendas;

@Named
@ViewScoped
public class VisualizarVendasMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Vendas> listaVendas;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaVendas = (List<Vendas>) session.getAttribute("listaVendas");
		session.removeAttribute("listaVendas");
	}

	public List<Vendas> getListaVendas() {
		return listaVendas;
	}

	public void setListaVendas(List<Vendas> listaVendas) {
		this.listaVendas = listaVendas;
	}

	
}
