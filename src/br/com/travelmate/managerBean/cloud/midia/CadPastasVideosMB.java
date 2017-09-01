package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.PastaVideoFacade;
import br.com.travelmate.model.Pastavideo;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadPastasVideosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Pastavideo pastavideo;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pastavideo = (Pastavideo) session.getAttribute("pastavideo");
		session.removeAttribute("pastavideo");
		if (pastavideo == null) {
			pastavideo = new Pastavideo();
		}
	}

	
	public Pastavideo getPastavideo() {
		return pastavideo;
	}

	public void setPastavideo(Pastavideo pastavideo) {
		this.pastavideo = pastavideo;
	}
	
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Pastavideo());
	}
	
	public void salvar(){
		PastaVideoFacade pastaVideoFacade = new PastaVideoFacade();
		String mensagem = validarDados();
		if (mensagem.length() == 0) {
			pastavideo = pastaVideoFacade.salvar(pastavideo);
			RequestContext.getCurrentInstance().closeDialog(pastavideo);
		}else{
			Mensagem.lancarMensagemInfo("", mensagem);
		}
	}
	
	public String validarDados(){
		String msg = "";
		if (pastavideo.getNome() == null || pastavideo.getNome().length() == 0) {
			msg = msg + " Informe o nome da pasta";
		}
		return msg;
	}

}
