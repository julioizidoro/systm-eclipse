package br.com.travelmate.managerBean.OrcamentoCurso;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ComplementoAcomodacaoFacade;
import br.com.travelmate.model.Complementoacomodacao;
import br.com.travelmate.model.Coprodutos;

@Named
@ViewScoped
public class ComplementoAcamodacaoMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Complementoacomodacao complemento;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Coprodutos coproduto = (Coprodutos) session.getAttribute("coproduto");
        session.removeAttribute("coproduto");
        if (coproduto!=null){
        	ComplementoAcomodacaoFacade complementoAcomodacaoFacade =new ComplementoAcomodacaoFacade();
        	complemento = complementoAcomodacaoFacade.consultar("select c from Complementoacomodacao c "
        			+ "where c.coprodutos.idcoprodutos="+coproduto.getIdcoprodutos());
        	if (complemento==null){
        		complemento = new Complementoacomodacao();
        		complemento.setCoprodutos(coproduto);
        	}
        }
	}

	public Complementoacomodacao getComplemento() {
		return complemento;
	}

	public void setComplemento(Complementoacomodacao complemento) {
		this.complemento = complemento;
	}
	
	public String salvar(){
		ComplementoAcomodacaoFacade complementoAcomodacaoFacade = new ComplementoAcomodacaoFacade();
		complemento = complementoAcomodacaoFacade.salvar(complemento);
		RequestContext.getCurrentInstance().closeDialog(complemento);
		return null;
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return null;
	}

}
