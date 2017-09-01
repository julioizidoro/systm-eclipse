package br.com.travelmate.managerBean.OrcamentoCurso;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ComplementoCursoFacade;
import br.com.travelmate.model.Complementocurso;
import br.com.travelmate.model.Coprodutos;

@Named
@ViewScoped
public class ComplementoCursoMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Complementocurso complemento;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Coprodutos coproduto = (Coprodutos) session.getAttribute("coproduto");
        session.removeAttribute("coproduto");
        if (coproduto!=null){
        	ComplementoCursoFacade complementoCursoFacade = new ComplementoCursoFacade();
        	complemento = complementoCursoFacade.consultar("select c from Complementocurso c where"
        			+ " c.coprodutos.idcoprodutos="+coproduto.getIdcoprodutos());
        	if (complemento==null){
        		complemento = new Complementocurso();
        		complemento.setCoprodutos(coproduto);
        	}
        }
	}

	public Complementocurso getComplemento() {
		return complemento;
	}

	public void setComplemento(Complementocurso complemento) {
		this.complemento = complemento;
	}
	
	public String salvar(){
		ComplementoCursoFacade complementoCursoFacade = new ComplementoCursoFacade();
		complemento = complementoCursoFacade.salvar(complemento);
		RequestContext.getCurrentInstance().closeDialog(complemento);
		return null;
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return null;
	}

}
