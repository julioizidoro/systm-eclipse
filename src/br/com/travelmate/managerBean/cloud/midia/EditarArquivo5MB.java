package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Arquivo5Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class EditarArquivo5MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Arquivo5 arquivo5;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean habilitarCheckRestrito = true;

	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		arquivo5 = (Arquivo5) session.getAttribute("arquivo5");
		session.removeAttribute("arquivo5");
		if (arquivo5 == null) {
			RequestContext.getCurrentInstance().closeDialog(new Arquivo5());
		}
		verificacaoTipoUsuario();
	}

	


	public Arquivo5 getArquivo5() {
		return arquivo5;
	}



	public void setArquivo5(Arquivo5 arquivo5) {
		this.arquivo5 = arquivo5;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	public boolean isHabilitarCheckRestrito() {
		return habilitarCheckRestrito;
	}




	public void setHabilitarCheckRestrito(boolean habilitarCheckRestrito) {
		this.habilitarCheckRestrito = habilitarCheckRestrito;
	}




	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Arquivo5());
	}
	
	public void salvar(){
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		arquivo5 = arquivo5Facade.salvar(arquivo5);
		Mensagem.lancarMensagemInfo("Alteração", " do nome do arquivo feita com sucesso");
		RequestContext.getCurrentInstance().closeDialog(arquivo5);
	}
	
	

	
	public void verificacaoTipoUsuario(){
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarCheckRestrito = false;
		}
	}
}
