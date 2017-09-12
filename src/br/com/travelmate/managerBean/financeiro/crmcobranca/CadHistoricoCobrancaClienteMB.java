package br.com.travelmate.managerBean.financeiro.crmcobranca;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CrmCobrancaFacade;
import br.com.travelmate.facade.CrmCobrancaHistoricoFacade;
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.LeadHistoricoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Crmcobranca;
import br.com.travelmate.model.Crmcobrancahistorico;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadHistoricoCobrancaClienteMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private Crmcobranca crmcobranca;
	private Crmcobrancahistorico crmcobrancahistorico;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		crmcobranca = (Crmcobranca) session.getAttribute("crmcobranca");
		crmcobrancahistorico = (Crmcobrancahistorico) session.getAttribute("crmcobrancahistorico");
		session.removeAttribute("crmcobranca");
		session.removeAttribute("crmcobrancahistorico");
		if (crmcobrancahistorico == null) {
			crmcobrancahistorico = new Crmcobrancahistorico();
			crmcobrancahistorico.setData(new Date());
		}
	}


	public Crmcobranca getCrmcobranca() {
		return crmcobranca;
	}


	public void setCrmcobranca(Crmcobranca crmcobranca) {
		this.crmcobranca = crmcobranca;
	}


	public Crmcobrancahistorico getCrmcobrancahistorico() {
		return crmcobrancahistorico;
	}


	public void setCrmcobrancahistorico(Crmcobrancahistorico crmcobrancahistorico) {
		this.crmcobrancahistorico = crmcobrancahistorico;
	}
	
	
	
	
	public void salvarHistorico() {
		if (crmcobrancahistorico.getTipocontato() != null) {
			if (crmcobrancahistorico.getHistorico() != null && !crmcobrancahistorico.getHistorico().equalsIgnoreCase(" ")) {
				if (crmcobrancahistorico.getTipocontato() != null) {
					// salvarHistorico
					crmcobrancahistorico.setUsuario(usuarioLogadoMB.getUsuario());
					crmcobrancahistorico.setCliente(crmcobranca.getVendas().getCliente());
					CrmCobrancaHistoricoFacade crmCobrancaHistoricoFacade = new CrmCobrancaHistoricoFacade();
					crmcobrancahistorico = crmCobrancaHistoricoFacade.salvar(crmcobrancahistorico);
	  
					// atualizarCrmcobranca
					CrmCobrancaFacade crmCobrancaFacade = new CrmCobrancaFacade();
					crmcobranca.setProximocontato(crmcobrancahistorico.getProximocontato());
					crmcobranca = crmCobrancaFacade.salvar(crmcobranca); 
					Mensagem.lancarMensagemInfo("Histórico salvo com sucesso", "");  
					RequestContext.getCurrentInstance().closeDialog(null);  
				} else
					Mensagem.lancarMensagemInfo("Tipo de próximo contato não preenchido!", "");
			} else
				Mensagem.lancarMensagemInfo("Histórico não preenchido!", "");
		} else
			Mensagem.lancarMensagemInfo("Tipo de contato não preenchido!", "");
	} 
	


	
	

}
