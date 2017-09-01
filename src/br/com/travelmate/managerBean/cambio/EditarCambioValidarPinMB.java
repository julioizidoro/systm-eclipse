package br.com.travelmate.managerBean.cambio;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Pincambio;

@Named
@ViewScoped
public class EditarCambioValidarPinMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB;
	private String pinCambio;
	private float novoValor;
	private float valorOriginal;
	

	@PostConstruct()
    public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        valorOriginal = (float) session.getAttribute("valorOriginal");
	        novoValor = (float) session.getAttribute("novoValor");
	        session.removeAttribute("valorOriginal");
	        session.removeAttribute("novoValor");
		}
	}

	public String getPinCambio() {
		return pinCambio;
	}


	public void setPinCambio(String pinCambio) {
		this.pinCambio = pinCambio;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public float getNovoValor() {
		return novoValor;
	}

	public void setNovoValor(float novoValor) {
		this.novoValor = novoValor;
	}
	
	public float getValorOriginal() {
		return valorOriginal;
	}

	public void setValorOriginal(float valorOriginal) {
		this.valorOriginal = valorOriginal;
	}

	
	public void validarPin(){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
        Pincambio pincambio = usuarioFacade.consultar(pinCambio, usuarioLogadoMB.getUsuario().getIdusuario());
        if (pincambio == null) {
        	FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("PIN inv�lido", ""));
        } else {
            salvarPinCambio(pincambio);
            valorOriginal = novoValor;
            RequestContext.getCurrentInstance().closeDialog(novoValor);
        }
	}
	
	public void salvarPinCambio(Pincambio pinCambio){
        if (pinCambio!=null){
            pinCambio.setUtilizado("S");
            UsuarioFacade usuarioFacade = new UsuarioFacade();
            usuarioFacade.salvar(pinCambio);
        }
    }

	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
    }

}
