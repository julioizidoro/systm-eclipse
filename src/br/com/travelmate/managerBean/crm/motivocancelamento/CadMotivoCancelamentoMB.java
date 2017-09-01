package br.com.travelmate.managerBean.crm.motivocancelamento;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.MotivoCancelamentoFacade;
import br.com.travelmate.model.Motivocancelamento;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadMotivoCancelamentoMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Motivocancelamento motivocancelamento; 
	 
	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		motivocancelamento = (Motivocancelamento) session.getAttribute("motivocancelamento");
		session.removeAttribute("motivocancelamento"); 
        if(motivocancelamento==null){
        	motivocancelamento = new Motivocancelamento();
        }
	}
 

	public Motivocancelamento getMotivocancelamento() {
		return motivocancelamento;
	} 

	public void setMotivocancelamento(Motivocancelamento motivocancelamento) {
		this.motivocancelamento = motivocancelamento;
	}
 
	public String fechar(){
	    RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    } 
	 
	public void salvar(){
		MotivoCancelamentoFacade motivoCancelamentoFacade = new MotivoCancelamentoFacade();
		motivocancelamento = motivoCancelamentoFacade.salvar(motivocancelamento);
		Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
		RequestContext.getCurrentInstance().closeDialog(null); 
	} 
	
}
