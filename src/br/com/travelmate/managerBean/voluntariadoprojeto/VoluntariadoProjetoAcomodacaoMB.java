package br.com.travelmate.managerBean.voluntariadoprojeto;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
 
import br.com.travelmate.facade.VoluntariadoProjetoAcomodacaoFacade; 
import br.com.travelmate.model.Voluntariadoprojeto;
import br.com.travelmate.model.Voluntariadoprojetoacomodacao;

@Named
@ViewScoped
public class VoluntariadoProjetoAcomodacaoMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Voluntariadoprojetoacomodacao voluntariadoprojetoacomodacao;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Voluntariadoprojeto voluntariadoprojeto = (Voluntariadoprojeto) session.getAttribute("voluntariadoprojeto");
        session.removeAttribute("voluntariadoprojeto");
        if (voluntariadoprojeto!=null){
        	VoluntariadoProjetoAcomodacaoFacade voluntariadoProjetoAcomodacaoFacade =new VoluntariadoProjetoAcomodacaoFacade();
        	voluntariadoprojetoacomodacao = voluntariadoProjetoAcomodacaoFacade.consultar("select v from Voluntariadoprojetoacomodacao v"
        			+ " where v.voluntariadoprojeto.idvoluntariadoprojeto="+voluntariadoprojeto.getIdvoluntariadoprojeto());
        	if (voluntariadoprojetoacomodacao==null){
        		voluntariadoprojetoacomodacao = new Voluntariadoprojetoacomodacao();
        		voluntariadoprojetoacomodacao.setVoluntariadoprojeto(voluntariadoprojeto);
        	}
        }
	}
 
	
	public Voluntariadoprojetoacomodacao getVoluntariadoprojetoacomodacao() {
		return voluntariadoprojetoacomodacao;
	}
 

	public void setVoluntariadoprojetoacomodacao(Voluntariadoprojetoacomodacao voluntariadoprojetoacomodacao) {
		this.voluntariadoprojetoacomodacao = voluntariadoprojetoacomodacao;
	} 

	public String salvar(){
		VoluntariadoProjetoAcomodacaoFacade voluntariadoProjetoAcomodacaoFacade = new VoluntariadoProjetoAcomodacaoFacade();
		voluntariadoprojetoacomodacao = voluntariadoProjetoAcomodacaoFacade.salvar(voluntariadoprojetoacomodacao);
		RequestContext.getCurrentInstance().closeDialog(voluntariadoprojetoacomodacao);
		return null;
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return null; 
	}

}
