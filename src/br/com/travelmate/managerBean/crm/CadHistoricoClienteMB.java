package br.com.travelmate.managerBean.crm;

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

import br.com.travelmate.bean.LeadSituacaoBean;
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.LeadHistoricoFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadhistorico;
import br.com.travelmate.model.Leadsituacao;
import br.com.travelmate.model.Tipocontato; 
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadHistoricoClienteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private Leadhistorico leadHistorico;
	private Lead lead;
	private List<Tipocontato> listaTipoContato;
	private List<Leadhistorico> listaHistorico; 

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		leadHistorico = (Leadhistorico) session.getAttribute("leadhistorico");
		session.removeAttribute("leadhistorico"); 
		lead = (Lead) session.getAttribute("lead");
		session.removeAttribute("lead"); 
		listaTipoContato = GerarListas.listarTipoContato("select t from Tipocontato t order by t.tipo");
		if(leadHistorico==null){
			leadHistorico = new Leadhistorico();
			leadHistorico.setTipocontato(lead.getTipocontato());
			leadHistorico.setDatahistorico(new Date());
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public Leadhistorico getLeadHistorico() {
		return leadHistorico;
	}

	public void setLeadHistorico(Leadhistorico leadHistorico) {
		this.leadHistorico = leadHistorico;
	}

	public List<Tipocontato> getListaTipoContato() {
		return listaTipoContato;
	}

	public void setListaTipoContato(List<Tipocontato> listaTipoContato) {
		this.listaTipoContato = listaTipoContato;
	}

	public List<Leadhistorico> getListaHistorico() {
		return listaHistorico;
	}

	public void setListaHistorico(List<Leadhistorico> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}
   

	public void salvarHistorico() {
		if (validarDados()) {
			// salvarHistorico
			leadHistorico.setCliente(lead.getCliente());
			LeadHistoricoFacade leadHistoricoFacade = new LeadHistoricoFacade();
			leadHistorico = leadHistoricoFacade.salvar(leadHistorico);

			// atualizarLead
			LeadFacade leadFacade = new LeadFacade();
			lead.setDataproximocontato(leadHistorico.getDataproximocontato());
			lead.setHoraproximocontato(leadHistorico.getHoraporximocontato());
			lead.setDataultimocontato(leadHistorico.getDatahistorico());
			if (lead.getSituacao() < 2) {
				LeadSituacaoBean leadSituacaoBean = new LeadSituacaoBean(lead, lead.getSituacao(), 2);
				lead.setSituacao(2);
			}
			lead = leadFacade.salvar(lead);
			Mensagem.lancarMensagemInfo("Histórico salvo com sucesso", "");
			RequestContext.getCurrentInstance().closeDialog(null);
		}
	}
	
	
	public boolean validarDados() {
		if (leadHistorico.getTipocontato() == null && leadHistorico.getTipocontato().getIdtipocontato() == null) {
			Mensagem.lancarMensagemInfo("Tipo de contato não preenchido!", "");
			return false;
		}
		if (leadHistorico.getHistorico() == null && leadHistorico.getHistorico().equalsIgnoreCase(" ")) {
			Mensagem.lancarMensagemInfo("Histórico não preenchido!", "");
			return false;
		}
		if (lead.getTipocontato() == null && lead.getTipocontato().getIdtipocontato() == null) {
			Mensagem.lancarMensagemInfo("Tipo de próximo contato não preenchido!", "");
			return false;
		}
		if (leadHistorico.getDataproximocontato() == null) {
			Mensagem.lancarMensagemErro("Data do próximo contato não inserida!", "");
			return false;
		}
		return true;
	}
	
	
	 
}
