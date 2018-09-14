package br.com.travelmate.managerBean.controleSolicitacoes;

import java.io.Serializable; 
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
 
import br.com.travelmate.model.Tisolicitacoes; 

@Named
@ViewScoped
public class RelatorioSolicitacoesFinalMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private List<Tisolicitacoes> listaSolicitacoes; 

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaSolicitacoes = (List<Tisolicitacoes>) session.getAttribute("listaSolicitacoes");
		session.removeAttribute("listaSolicitacoes");
	}

	public List<Tisolicitacoes> getListaSolicitacoes() {
		return listaSolicitacoes;
	}

	public void setListaSolicitacoes(List<Tisolicitacoes> listaSolicitacoes) {
		this.listaSolicitacoes = listaSolicitacoes;
	} 
 
	 
}
