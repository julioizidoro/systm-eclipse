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

import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadcontrole;
import br.com.travelmate.model.Leadresponsavel;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.dao.LeadControleDao;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadResponsavelDao;


@Named
@ViewScoped
public class LeadMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LeadControleDao leadControleDao;
	@Inject LeadDao leadDao;
	@Inject
	private LeadResponsavelDao leadResponsavelDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	@Inject
	private DashBoardMB dashBoardMB;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private List<Lead> listaLead;
	private boolean acessomaster = false;
	private boolean acessounidade = true;
	private Leadcontrole leadcontrole;
	private boolean habilitarDadosLead = false;

	@PostConstruct()
	public void init() {
		getUsuarioLogadoMB();
		unidadenegocio = new Unidadenegocio();
		gerarListaUnidadeNegocio();
		gerarListaLead();
		buscarUltimoLeadControle();
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
			habilitarDadosLead = true;
		}
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

	public Leadcontrole getLeadcontrole() {
		return leadcontrole;
	}

	public void setLeadcontrole(Leadcontrole leadcontrole) {
		this.leadcontrole = leadcontrole;
	}

	public boolean isHabilitarDadosLead() {
		return habilitarDadosLead;
	}

	public void setHabilitarDadosLead(boolean habilitarDadosLead) {
		this.habilitarDadosLead = habilitarDadosLead;
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
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==1  || usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialdistribuicaoleads()) {
				sql = "select l from Lead l where l.dataenvio is null";
			}else if (usuarioLogadoMB.getUsuario().getIdusuario() == 212) {
				sql = "select l from Lead l where l.dataenvio is null and (l.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()
						+ " or  l.unidadenegocio.idunidadeNegocio=6)";
			} else {
				sql = "select l from Lead l where l.dataenvio is null and l.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			}
			listaLead = leadDao.lista(sql);
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
		List<Leadresponsavel> lista = leadResponsavelDao
				.lista(usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getUsuario().getIdusuario() == idusuario) {
					return true;
				}
			}
		}
		if(usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==1 || usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialdistribuicaoleads()) {
			return true;
		}
		return false;
	}

	public String novoLead() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("cadLeadDistribuicao", options, null);
		return "";
	}

	public void excluir(Lead lead) {
		leadDao.excluir(lead.getIdlead());
		gerarListaLead();
	}

	public void excluirSelecionados() {
		for (int i = 0; i < listaLead.size(); i++) {
			if (listaLead.get(i).isSelecionado()) {
				leadDao.excluir(listaLead.get(i).getIdlead());
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
			if (listaLead != null && listaLead.size() == 0) {
				dashBoardMB.setFecharDistribuicao(false);
			}
		}
	}

	public String verificarTelefone(Cliente cliente) {
		if (cliente != null) {
			if (cliente.getFoneCelular() != null && cliente.getFoneCelular().length() > 0) {
				return cliente.getFoneCelular();
			}
			return cliente.getFoneResidencial();
		}
		return "";	
	}
	
	public void buscarUltimoLeadControle(){
		String sql = "SELECT l FROM Leadcontrole l WHERE l.idleadcontrole= (SELECT MAX(le.idleadcontrole) From Leadcontrole le)";
		leadcontrole = leadControleDao.consultar(sql);
		if (leadcontrole == null) {
			leadcontrole = new Leadcontrole();
		}   
	}  
	
	
	public void retornoDialogNovoLead(SelectEvent event){
		Lead lead = (Lead) event.getObject();
		if (lead.getIdlead() != null) {
			if (listaLead == null) {
				listaLead = new ArrayList<Lead>();
			}
			listaLead.add(lead);
			Mensagem.lancarMensagemInfo("Cadastro com sucesso", "");
		}
	}
	
	public String getNomeUnidade(Lead lead) {
		if (lead.getNomeunidade()!=null && lead.getNomeunidade().length()>0) {
			return lead.getNomeunidade();
		}else return lead.getUnidadenegocio().getNomerelatorio();
	}
	
	   
	   
	
}
