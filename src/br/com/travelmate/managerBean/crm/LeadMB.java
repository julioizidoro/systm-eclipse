package br.com.travelmate.managerBean.crm;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
 
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.LeadResponsavelFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadresponsavel;
import br.com.travelmate.model.Unidadenegocio; 

@Named
@ViewScoped
public class LeadMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private List<Lead> listaLead;
	private boolean acessomaster = false;
	private boolean acessounidade = true;

	@PostConstruct()
	public void init() {
		getUsuarioLogadoMB();
		unidadenegocio = new Unidadenegocio();
		gerarListaUnidadeNegocio();
		gerarListaLead();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
 

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Lead> getListaLead() {
		return listaLead;
	}

	public void setListaLead(List<Lead> listaLead) {
		this.listaLead = listaLead;
	}

	public boolean isAcessomaster() {
		return acessomaster;
	}

	public void setAcessomaster(boolean acessomaster) {
		this.acessomaster = acessomaster;
	}

	public boolean isAcessounidade() {
		return acessounidade;
	}

	public void setAcessounidade(boolean acessounidade) {
		this.acessounidade = acessounidade;
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}  

	public void gerarListaLead() {
		boolean responsavel = retornarResponsavelUnidade();
		if (responsavel) {
			String sql;
			if (usuarioLogadoMB.getUsuario().getIdusuario() == 212) {
				sql = "select l from Lead l where l.dataenvio is null and (l.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()
						+ " or  l.unidadenegocio.idunidadeNegocio=6)";
			} else {
				sql = "select l from Lead l where l.dataenvio is null and l.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			}
			LeadFacade leadFacade = new LeadFacade();
			listaLead = leadFacade.lista(sql);
			if (usuarioLogadoMB.getUsuario().isPertencematriz()) {
				acessomaster = true;
				acessounidade = false;
			} else {
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio(); 
			}
		}
	}

	public boolean retornarResponsavelUnidade() {
		int idusuario = usuarioLogadoMB.getUsuario().getIdusuario();
		LeadResponsavelFacade leadResponsavelFacade = new LeadResponsavelFacade();
		List<Leadresponsavel> lista = leadResponsavelFacade
				.lista(usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getUsuario().getIdusuario() == idusuario) {
					return true;
				}
			}
		}
		return false;
	}

	public String novoLead() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("cadLead", options, null);
		return "";
	}

	public void excluir(Lead lead) {
		LeadFacade leadFacade = new LeadFacade();
		leadFacade.excluir(lead.getIdlead());
		gerarListaLead();
	}

	public void excluirSelecionados() {
		for (int i = 0; i < listaLead.size(); i++) {
			if (listaLead.get(i).isSelecionado()) {
				LeadFacade leadFacade = new LeadFacade();
				leadFacade.excluir(listaLead.get(i).getIdlead());
			}
		}
		gerarListaLead();
	}

	public String encaminharLead(Lead lead) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 700);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("lead", lead);
		RequestContext.getCurrentInstance().openDialog("encaminharLead", options, null);
		return "";
	}

	public void dialogReturnEncaminharLead(SelectEvent event) {
		if (event.getObject() != null) {
			if (event.getObject() instanceof Lead) {
				Lead lead = (Lead) event.getObject();
				listaLead.remove(lead);
			}
		}
	}

	public String verificarTelefone(Cliente cliente) {
		if (cliente.getFoneCelular() != null && cliente.getFoneCelular().length() > 0) {
			return cliente.getFoneCelular();
		}
		return cliente.getFoneResidencial();
	}
}
