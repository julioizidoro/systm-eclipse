/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.voluntariadoprojeto;
 
import br.com.travelmate.facade.VoluntariadoProjetoValorFacade; 
import br.com.travelmate.model.Voluntariadoprojeto;
import br.com.travelmate.model.Voluntariadoprojetovalor;
import br.com.travelmate.util.Formatacao;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class CadVoluntariadoProjetoValorMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Voluntariadoprojetovalor voluntariadoprojetovalor;

    public CadVoluntariadoProjetoValorMB() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        voluntariadoprojetovalor = (Voluntariadoprojetovalor) session.getAttribute("voluntariadoprojetovalor");
        Voluntariadoprojeto voluntariadoprojeto = (Voluntariadoprojeto) session.getAttribute("voluntariadoprojeto");
        session.removeAttribute("voluntariadoprojetovalor"); 
        if (voluntariadoprojetovalor==null){
        	voluntariadoprojetovalor = new Voluntariadoprojetovalor();
        	voluntariadoprojetovalor.setDatainicial(Formatacao.ConvercaoStringData("01/01/2017"));
        	voluntariadoprojetovalor.setDatafinal(Formatacao.ConvercaoStringData("31/12/2017"));  
        	voluntariadoprojetovalor.setVoluntariadoprojeto(voluntariadoprojeto);
        }
        
    }
 
    public Voluntariadoprojetovalor getVoluntariadoprojetovalor() {
		return voluntariadoprojetovalor;
	}

	public void setVoluntariadoprojetovalor(Voluntariadoprojetovalor voluntariadoprojetovalor) {
		this.voluntariadoprojetovalor = voluntariadoprojetovalor;
	}

	public String salvar(){ 
        VoluntariadoProjetoValorFacade voluntariadoProjetoValorFacade = new VoluntariadoProjetoValorFacade();   
        voluntariadoprojetovalor = voluntariadoProjetoValorFacade.salvar(voluntariadoprojetovalor);
        RequestContext.getCurrentInstance().closeDialog(voluntariadoprojetovalor);
        return "";
    }
    
    public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
     
}
