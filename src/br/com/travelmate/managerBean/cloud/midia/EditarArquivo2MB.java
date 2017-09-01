package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Arquivo2Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class EditarArquivo2MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Arquivo2 arquivo2;
	private boolean habilitarCheckRestrito = true;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		arquivo2 = (Arquivo2) session.getAttribute("arquivo2");
		session.removeAttribute("arquivo2");
		if (arquivo2 == null) {
			RequestContext.getCurrentInstance().closeDialog(new Arquivo2());
		}
		verificacaoTipoUsuario();
	}
	
	

	/**
	 * @return the arquivo2
	 */
	public Arquivo2 getArquivo2() {
		return arquivo2;
	}



	/**
	 * @param arquivo2 the arquivo2 to set
	 */
	public void setArquivo2(Arquivo2 arquivo2) {
		this.arquivo2 = arquivo2;
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
		RequestContext.getCurrentInstance().closeDialog(new Arquivo2());
	}
	
	public void salvar(){
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		arquivo2 = arquivo2Facade.salvar(arquivo2);
		Mensagem.lancarMensagemInfo("Alteração", " do nome do arquivo feita com sucesso");
		RequestContext.getCurrentInstance().closeDialog(arquivo2);
	}
	
	
	
	public void verificacaoTipoUsuario(){
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarCheckRestrito = false;
		}
	}


}
