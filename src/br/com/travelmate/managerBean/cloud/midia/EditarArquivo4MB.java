package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Arquivo4Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class EditarArquivo4MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Arquivo4 arquivo4;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean habilitarCheckRestrito = true;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		arquivo4 = (Arquivo4) session.getAttribute("arquivo4");
		session.removeAttribute("arquivo4");
		if (arquivo4 == null) {
			RequestContext.getCurrentInstance().closeDialog(new Arquivo4());
		}
		verificacaoTipoUsuario();
	}

	
	
	


	public Arquivo4 getArquivo4() {
		return arquivo4;
	}






	public void setArquivo4(Arquivo4 arquivo4) {
		this.arquivo4 = arquivo4;
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
		RequestContext.getCurrentInstance().closeDialog(new Arquivo4());
	}
	
	public void salvar(){
		Arquivo4Facade arquivo4Facade = new Arquivo4Facade();
		arquivo4 = arquivo4Facade.salvar(arquivo4);
		Mensagem.lancarMensagemInfo("Alteração", " do nome do arquivo feita com sucesso");
		RequestContext.getCurrentInstance().closeDialog(arquivo4);
	}

	
	public void verificacaoTipoUsuario(){
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarCheckRestrito = false;
		}
	}

}
