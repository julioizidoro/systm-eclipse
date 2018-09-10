package br.com.travelmate.managerBean.coprodutos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ComplementoAcomodacaoDiaSemanaFacade;
import br.com.travelmate.model.Complementoacomodacaodiasemana;
import br.com.travelmate.model.Coprodutos;

@Named
@ViewScoped
public class ComplementoAcamodacaoDiaSemanaMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Complementoacomodacaodiasemana complemento;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Coprodutos coproduto = (Coprodutos) session.getAttribute("coproduto");
        session.removeAttribute("coproduto");
        if (coproduto!=null){
        	ComplementoAcomodacaoDiaSemanaFacade complementoAcomodacaoDiaSemanaFacade = new ComplementoAcomodacaoDiaSemanaFacade();
        	complemento = complementoAcomodacaoDiaSemanaFacade.consultar("select c from Complementoacomodacaodiasemana c "
        			+ "where c.coprodutos.idcoprodutos="+coproduto.getIdcoprodutos());
        	if (complemento==null){
        		complemento = new Complementoacomodacaodiasemana();
        		complemento.setCoprodutos(coproduto);
        	}
        }
	}

	public Complementoacomodacaodiasemana getComplemento() {
		return complemento;
	}

	public void setComplemento(Complementoacomodacaodiasemana complemento) {
		this.complemento = complemento;
	}
	
	public String salvar(){
		ComplementoAcomodacaoDiaSemanaFacade complementoAcomodacaoDiaSemanaFacade = new ComplementoAcomodacaoDiaSemanaFacade();
		complemento = complementoAcomodacaoDiaSemanaFacade.salvar(complemento);
		RequestContext.getCurrentInstance().closeDialog(complemento);
		return null;
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return null;
	}

}
