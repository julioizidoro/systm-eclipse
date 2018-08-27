package br.com.travelmate.managerBean.cambio;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.managerBean.UsuarioLogadoMB;




@Named
@ViewScoped
public class EditarCambioMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB;
	private float valorOriginal;
	private float novoValor;
	
	@PostConstruct()
    public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        valorOriginal = (float) session.getAttribute("valorCambio");
	        session.removeAttribute("cambio");
	        novoValor = valorOriginal;
		}
	}

	
	
	public float getValorOriginal() {
		return valorOriginal;
	}
	
	public void setValorOriginal(float valorOriginal) {
		this.valorOriginal = valorOriginal;
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
	
	public String salvar(){
		if (novoValor > valorOriginal){
			valorOriginal = novoValor;
			RequestContext.getCurrentInstance().closeDialog(novoValor);
        }else {
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("valorOriginal", valorOriginal);
            session.setAttribute("novoValor", novoValor);
        	return "validarTrocaCambioPIN";
        }
		return "";
	}
	
	public String salvarCambioOrcamento(){
		valorOriginal = novoValor;
		RequestContext.getCurrentInstance().closeDialog(novoValor);
		return "";
	}

	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(0.0f);
		return "";
    }
}
