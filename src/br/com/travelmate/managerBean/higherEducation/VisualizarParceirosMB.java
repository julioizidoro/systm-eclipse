package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.model.Heparceiros;

@Named
@ViewScoped
public class VisualizarParceirosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Heparceiros> listaHeParceiros;
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaHeParceiros = (List<Heparceiros>) session.getAttribute("listaHeParceiros");
		session.removeAttribute("listaHeParceiros");
		if (listaHeParceiros != null) {
			for (int i = 0; i < listaHeParceiros.size(); i++) {
				if (listaHeParceiros.get(i).isPathway()) {
					listaHeParceiros.get(i).setTemPathWay("Sim");
				}else {
					listaHeParceiros.get(i).setTemPathWay("Não");
				}
			}
		}
	}


	public List<Heparceiros> getListaHeParceiros() {
		return listaHeParceiros;
	}


	public void setListaHeParceiros(List<Heparceiros> listaHeParceiros) {
		this.listaHeParceiros = listaHeParceiros;
	}
	
	
	public void fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	
}
