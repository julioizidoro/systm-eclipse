package br.com.travelmate.managerBean.financeiro.recebimento;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.RecinternacionalFacade;
import br.com.travelmate.model.Recinternacional;


@Named
@ViewScoped
public class ObsRecebimentoMB implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Recinternacional recinternacional;



	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        recinternacional = (Recinternacional) session.getAttribute("recinternacional");
        session.removeAttribute("recinternacional");
	}

	
	
	public Recinternacional getRecinternacional() {
		return recinternacional;
	}



	public void setRecinternacional(Recinternacional recinternacional) {
		this.recinternacional = recinternacional;
	}



	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Recinternacional());
	}
	
	
	
	public void salvar(){
		RecinternacionalFacade recinternacionalFacade = new RecinternacionalFacade();
		if (recinternacional.getObservacao() != null) {
			recinternacional = recinternacionalFacade.salvar(recinternacional);
			RequestContext.getCurrentInstance().closeDialog(recinternacional);
		}
	}
}
