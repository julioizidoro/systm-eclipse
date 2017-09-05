package br.com.travelmate.managerBean.crm;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Contasreceber;
  

@Named
@ViewScoped
public class VisualizarContasReceberMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Contasreceber> listaContasReceber;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaContasReceber = (List<Contasreceber>) session.getAttribute("listaContasReceber");
		session.removeAttribute("listaContasReceber");
	}

	public List<Contasreceber> getListaContasReceber() {
		return listaContasReceber;
	}

	public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
	}

	
	

	
}
