package br.com.travelmate.managerBean.crm;

import java.io.Serializable; 
import java.util.ArrayList;
import java.util.Date;
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

import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.LeadFacade; 
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Lead; 
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class LeadMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Usuario> listaConsultor;
	private Usuario consultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio; 
	private List<Lead> listaLead;
	private boolean acessomaster=false;
	private boolean acessounidade=true;

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


	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}


	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}


	public Usuario getConsultor() {
		return consultor;
	}


	public void setConsultor(Usuario consultor) {
		this.consultor = consultor;
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
	
	public void atualizarLead(Lead lead){
		if(unidadenegocio!=null){
			lead.setUnidadenegocio(unidadenegocio); 
		}
		if(consultor!=null){
			lead.setUsuario(consultor);
		}
		gerarListaConsultor();
	}

	public void gerarListaConsultor() { 
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaConsultor = usuarioFacade
				.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		if (listaConsultor == null) {
			listaConsultor = new ArrayList<Usuario>();
		}  
		consultor = usuarioFacade.consultar(unidadenegocio.getResponsavelcrm());
	}
	  
	public String enviar(Lead lead){
		if(validarDados()){ 
			LeadFacade leadFacade = new LeadFacade();   
			lead.setUnidadenegocio(unidadenegocio);
			lead.setUsuario(consultor);
			lead.setDataenvio(new Date()); 
			lead.setHoraenvio(Formatacao.foramtarHoraString()); 
			lead.setDataproximocontato(new Date());
			lead = leadFacade.salvar(lead);
			Mensagem.lancarMensagemInfo("", "Lead enviado com sucesso!"); 
			Cliente cliente = lead.getCliente();
			cliente.setUnidadenegocio(unidadenegocio);
			ClienteFacade clienteFacade = new ClienteFacade();
			clienteFacade.salvar(cliente);
			gerarListaLead();
		}else{
			Mensagem.lancarMensagemErro("Atenção", "Campos Obrigatorio não preenchidos!");
		}
		return "";
	}
	
	public boolean validarDados(){
		if(unidadenegocio==null || unidadenegocio.getIdunidadeNegocio()==null){
			return false;
		}
		if(consultor==null || consultor.getIdusuario()==null){
			return false;
		}
		return true;
	} 
	
	public void gerarListaLead(){
		if(usuarioLogadoMB.getUsuario().getUnidadenegocio().getResponsavelcrm()
				==usuarioLogadoMB.getUsuario().getIdusuario()){
			String sql;
			if (usuarioLogadoMB.getUsuario().getIdusuario()==212){
				sql = "select l from Lead l where l.dataenvio is null and (l.unidadenegocio.idunidadeNegocio="
						+usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() +
						" or  l.unidadenegocio.idunidadeNegocio=6)";
				
			}else if (usuarioLogadoMB.getUsuario().getIdusuario()==0){
				sql = "select l from Lead l where l.dataenvio is null and l.unidadenegocio.idunidadeNegocio=2";
			}else {
				sql = "select l from Lead l where l.dataenvio is null and l.unidadenegocio.idunidadeNegocio="
						+usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			}
			LeadFacade leadFacade = new LeadFacade();
			listaLead = leadFacade.lista(sql);
			if(usuarioLogadoMB.getUsuario().isPertencematriz()){
				acessomaster=true;
				acessounidade=false;
			}else{
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio(); 
				gerarListaConsultor();
			}
		}
	}
	
	
	public String novoLead() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth",550);
		RequestContext.getCurrentInstance().openDialog("cadLead", options, null);
		return "";
	}
	
	public void excluir(Lead lead){
		LeadFacade leadFacade = new LeadFacade();
		leadFacade.excluir(lead.getIdlead());
		gerarListaLead();
	}
	
	public void excluirSelecionados(){
		for (int i = 0; i < listaLead.size(); i++) {
			if(listaLead.get(i).isSelecionado()){
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
	
	public void dialogReturnEncaminharLead(SelectEvent event){
		if (event.getObject()!=null){
			if (event.getObject() instanceof Lead){
				Lead lead = (Lead) event.getObject();
				listaLead.remove(lead);
			}
		}
	}
}
