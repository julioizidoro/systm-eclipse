package br.com.travelmate.managerBean.grupoAcesso;

import java.io.Serializable;  
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped; 
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.GrupoAcessoFacade;
import br.com.travelmate.model.Acesso; 
import br.com.travelmate.model.Grupoacesso; 
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadGrupoAcessoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private Grupoacesso grupoacesso;      

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		grupoacesso = (Grupoacesso) session.getAttribute("grupoacesso");
		session.removeAttribute("grupoacesso"); 
		if (grupoacesso == null) { 
			grupoacesso = new Grupoacesso(); 
			grupoacesso.setAcesso(new Acesso());
		} 
	} 

	
	public Grupoacesso getGrupoacesso() {
		return grupoacesso;
	}



	public void setGrupoacesso(Grupoacesso grupoacesso) {
		this.grupoacesso = grupoacesso;
	}



	public void validarDados(String msg) {  
		if (grupoacesso.getDescricao() == null && grupoacesso.getDescricao().length() < 2) {
			msg = msg + "\n" + "Descrição do grupo de acesso não informado.";
		}
	}

	public String salvar() {
		String msg = "";
		validarDados(msg);
		if (msg.length() < 2) { 
			GrupoAcessoFacade grupoAcessoFacade = new GrupoAcessoFacade();
			grupoacesso.setAcesso(grupoAcessoFacade.salvar(grupoacesso.getAcesso())); 
			grupoacesso = grupoAcessoFacade.salvar(grupoacesso);
			return "consAcesso";
		} else {
			Mensagem.lancarMensagemErro("Atenção!", msg);
			return "";
		}
	}
 
	public String cancelar() {
		return "consAcesso";
	}  
}
