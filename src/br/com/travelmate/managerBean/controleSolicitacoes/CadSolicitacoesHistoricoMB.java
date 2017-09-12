package br.com.travelmate.managerBean.controleSolicitacoes;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.TiSolicitacoesHistoricoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Tisolicitacoes;
import br.com.travelmate.model.Tisolicitacoeshistorico;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadSolicitacoesHistoricoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Tisolicitacoes tisolicitacoes;
	private Tisolicitacoeshistorico tisolicitacoeshistorico;
	
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tisolicitacoes = (Tisolicitacoes) session.getAttribute("tisolicitacoes");
		tisolicitacoeshistorico = (Tisolicitacoeshistorico) session.getAttribute("tisolicitacoeshistorico");
		session.removeAttribute("tisolicitacoeshistorico");
		session.removeAttribute("tisolicitacoes");
		if (tisolicitacoeshistorico == null) {
			tisolicitacoeshistorico = new Tisolicitacoeshistorico();
		}else {
			tisolicitacoes = tisolicitacoeshistorico.getTisolicitacoes();
		}
	}




	public Tisolicitacoes getTisolicitacoes() {
		return tisolicitacoes;
	}




	public void setTisolicitacoes(Tisolicitacoes tisolicitacoes) {
		this.tisolicitacoes = tisolicitacoes;
	}




	public Tisolicitacoeshistorico getTisolicitacoeshistorico() {
		return tisolicitacoeshistorico;
	}




	public void setTisolicitacoeshistorico(Tisolicitacoeshistorico tisolicitacoeshistorico) {
		this.tisolicitacoeshistorico = tisolicitacoeshistorico;
	}
	
	
	
	public void salvarHistorico(){
		TiSolicitacoesHistoricoFacade tiSolicitacoesHistoricoFacade = new TiSolicitacoesHistoricoFacade();
		if (validarDados()) {
			tisolicitacoeshistorico.setData(new Date());
			tisolicitacoeshistorico.setTisolicitacoes(tisolicitacoes);
			tisolicitacoeshistorico.setUsuario(usuarioLogadoMB.getUsuario());
			if (tisolicitacoeshistorico.getIdtisolicitacoeshistorico() == null) {
				tisolicitacoeshistorico = tiSolicitacoesHistoricoFacade.salvar(tisolicitacoeshistorico);
				RequestContext.getCurrentInstance().closeDialog(tisolicitacoeshistorico);
			}else{
				tiSolicitacoesHistoricoFacade.salvar(tisolicitacoeshistorico);
				RequestContext.getCurrentInstance().closeDialog(new Tisolicitacoeshistorico());
			}
		}
		
	}
	
	
	public boolean validarDados(){
		if (tisolicitacoeshistorico.getTipo() == null || tisolicitacoeshistorico.getTipo().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe o tipo", "");
			return false;
		}
		
		if (tisolicitacoeshistorico.getDescricao() == null || tisolicitacoeshistorico.getDescricao().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe a descrição", "");
			return false;
		}
		return true;
	}
	
	
	

}
