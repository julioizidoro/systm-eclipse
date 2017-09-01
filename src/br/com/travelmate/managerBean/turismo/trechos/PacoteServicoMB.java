/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo.trechos;


import br.com.travelmate.facade.PacotesHotelFacade;
import br.com.travelmate.facade.PacotesServicoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Pacoteservico;
import br.com.travelmate.model.Pacotetrecho;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class PacoteServicoMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Pacoteservico pacoteservico;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Pacotetrecho pacotetrecho = (Pacotetrecho) session.getAttribute("pacoteTrecho");
        session.removeAttribute("pacoteTrecho");
        PacotesServicoFacade pacoteServicoFacade = new PacotesServicoFacade();
        pacoteservico = pacoteServicoFacade.consultar(pacotetrecho.getIdpacotetrecho());
        if (pacoteservico == null) {
            pacoteservico = new Pacoteservico();
            pacoteservico.setPacotetrecho(pacotetrecho);
        } 
    }

   
    public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public Pacoteservico getPacoteservico() {
		return pacoteservico;
	}


	public void setPacoteservico(Pacoteservico pacoteservico) {
		this.pacoteservico = pacoteservico;
	}

    
    public String salvarServico(){
        PacotesServicoFacade pacoteservicoFacade = new PacotesServicoFacade();
        pacoteservico = pacoteservicoFacade.salvar(pacoteservico);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
        RequestContext.getCurrentInstance().closeDialog(pacoteservico);
        return "";
    }
    
    public String cancelar(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacoteservico.getPacotetrecho().getPacotes());
        RequestContext.getCurrentInstance().closeDialog(pacoteservico);
        return "";
    }
    
    public String excluir(){
    	PacotesServicoFacade pacoteservicoFacade = new PacotesServicoFacade();
        pacoteservicoFacade.excluir(pacoteservico.getIdpacoteservico());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacoteservico.getPacotetrecho().getPacotes());
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
}
