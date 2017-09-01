package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Arquivo3Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class EditarArquivo3MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Arquivo3 arquivo3;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean habilitarCheckRestrito = true;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		arquivo3 = (Arquivo3) session.getAttribute("arquivo3");
		session.removeAttribute("arquivo3");
		if (arquivo3 == null) {
			RequestContext.getCurrentInstance().closeDialog(new Arquivo3());
		}
		verificacaoTipoUsuario();
	}

	
	
	/**
	 * @return the arquivo3
	 */
	public Arquivo3 getArquivo3() {
		return arquivo3;
	}



	/**
	 * @param arquivo3 the arquivo3 to set
	 */
	public void setArquivo3(Arquivo3 arquivo3) {
		this.arquivo3 = arquivo3;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	



	public boolean isHabilitarCheckRestrito() {
		return habilitarCheckRestrito;
	}



	public void setHabilitarCheckRestrito(boolean habilitarCheckRestrito) {
		this.habilitarCheckRestrito = habilitarCheckRestrito;
	}



	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Arquivo3());
	}
	
	public void salvar(){
		Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
		arquivo3 = arquivo3Facade.salvar(arquivo3);
		Mensagem.lancarMensagemInfo("Alteração", " do nome do arquivo feita com sucesso");
		RequestContext.getCurrentInstance().closeDialog(arquivo3);
	}
	

	public void verificacaoTipoUsuario(){
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarCheckRestrito = false;
		}
	}

}
