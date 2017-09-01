package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.Arquivo1Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class EditarArquivo1MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Arquivo1 arquivo1;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean habilitarCheckRestrito = true;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		arquivo1 = (Arquivo1) session.getAttribute("arquivo1");
		session.removeAttribute("arquivo1");
		if (arquivo1 == null) {
			RequestContext.getCurrentInstance().closeDialog(new Arquivo1());
		}
		verificacaoTipoUsuario();
	}

	

	/**
	 * @return the arquivo1
	 */
	public Arquivo1 getArquivo1() {
		return arquivo1;
	}



	/**
	 * @param arquivo1 the arquivo1 to set
	 */
	public void setArquivo1(Arquivo1 arquivo1) {
		this.arquivo1 = arquivo1;
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
		RequestContext.getCurrentInstance().closeDialog(new Arquivo1());
	}
	
	public void salvar(){
		Arquivo1Facade cloudFilesFacade = new Arquivo1Facade();
		arquivo1 = cloudFilesFacade.salvar(arquivo1);
		Mensagem.lancarMensagemInfo("Alteração", " do nome do arquivo feita com sucesso");
		RequestContext.getCurrentInstance().closeDialog(arquivo1);
	}
	
	
	public void verificacaoTipoUsuario(){
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarCheckRestrito = false;
		}
	}

}
