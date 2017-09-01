package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FornecedorDocsFacade;
import br.com.travelmate.model.Fornecedordocs;

@Named
@ViewScoped
public class EditarDocumentosFornecedorMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fornecedordocs fornecedordocs;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fornecedordocs = (Fornecedordocs) session.getAttribute("fornecedordocs");
		session.removeAttribute("fornecedordocs");
	}



	public Fornecedordocs getFornecedordocs() {
		return fornecedordocs;
	}



	public void setFornecedordocs(Fornecedordocs fornecedordocs) {
		this.fornecedordocs = fornecedordocs;
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Fornecedordocs());
	}
	
	
	public void salvar(){
		FornecedorDocsFacade fornecedorDocsFacade = new FornecedorDocsFacade();
		if (fornecedordocs.getNome() != null) {
			fornecedordocs = fornecedorDocsFacade.salvar(fornecedordocs);
			RequestContext.getCurrentInstance().closeDialog(fornecedordocs);
		}
	}
	
	
	
	
	
	

}
