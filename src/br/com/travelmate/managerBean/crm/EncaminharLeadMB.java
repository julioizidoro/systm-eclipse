package br.com.travelmate.managerBean.crm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.LeadEncaminhadoFacade;
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadencaminhado;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem; 

@Named
@ViewScoped
public class EncaminharLeadMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;  
	private Lead lead;
	private Leadencaminhado leadencaminhado; 
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private Tipocontato tipocontato;
	private List<Tipocontato> listaTipoContato; 
	private List<Unidadenegocio> listaUnidade;
	private List<Usuario> listaUsuario;
	private int situacao;
	private boolean habilitarComboUnidade = true;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		lead = (Lead) session.getAttribute("lead");
		session.removeAttribute("lead"); 
		leadencaminhado = new Leadencaminhado();
		leadencaminhado.setLead(lead);
		listaUnidade = GerarListas.listarUnidade();
		listaTipoContato = GerarListas.listarTipoContato("select t from Tipocontato t order by t.tipo");
		if(!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaUsuario();  
		}  else{
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaUsuario();  
			habilitarComboUnidade=false;
		}
		tipocontato = lead.getTipocontato();
		situacao = lead.getSituacao();
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
 
	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Leadencaminhado getLeadencaminhado() {
		return leadencaminhado;
	}

	public void setLeadencaminhado(Leadencaminhado leadencaminhado) {
		this.leadencaminhado = leadencaminhado;
	}

	public Tipocontato getTipocontato() {
		return tipocontato;
	}

	public void setTipocontato(Tipocontato tipocontato) {
		this.tipocontato = tipocontato;
	}

	public List<Tipocontato> getListaTipoContato() {
		return listaTipoContato;
	}

	public void setListaTipoContato(List<Tipocontato> listaTipoContato) {
		this.listaTipoContato = listaTipoContato;
	}
 
	public boolean isHabilitarComboUnidade() {
		return habilitarComboUnidade;
	}

	public void setHabilitarComboUnidade(boolean habilitarComboUnidade) {
		this.habilitarComboUnidade = habilitarComboUnidade;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public String retornarCoresSituacao(int numeroSituacao) {
		if (numeroSituacao == 1) {
			return "#1E90FF;";
		} else if (numeroSituacao == 2) {
			return "#9ACD32;";
		} else if (numeroSituacao == 3) {
			return "#FF8C00;";
		} else if (numeroSituacao == 4) {
			return "#B22222;";
		} else if (numeroSituacao == 5) {
			return "#228B22;";
		} else if (numeroSituacao == 6) {
			return "#8B8989;";
		} else if (numeroSituacao == 7) {
			return "#9400D3;";
		}
		return "";
	}
	
	public void gerarListaUsuario(){
		if(unidadenegocio!=null){
			listaUsuario = GerarListas.listarUsuarios("Select u FROM Usuario u where u.situacao='Ativo'"
				+ " and u.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		}
	}
	
	public void mudarSituacao(int situacao) {
		this.situacao = situacao;
	}
	
	public String salvar(){
		leadencaminhado.setData(new Date());
		leadencaminhado.setHora(Formatacao.foramtarHoraString());
		leadencaminhado.setUsuariode(usuarioLogadoMB.getUsuario());
		leadencaminhado.setUsuariopara(usuario);
		LeadEncaminhadoFacade leadEncaminhadoFacade = new LeadEncaminhadoFacade();
		leadEncaminhadoFacade.salvar(leadencaminhado); 
		LeadFacade leadFacade = new LeadFacade();
		lead.setUnidadenegocio(unidadenegocio);
		lead.setSituacao(situacao);
		lead.setTipocontato(tipocontato);
		lead.setUsuario(usuario);
		lead.setDataenvio(new Date());
		lead.setHoraenvio(Formatacao.foramtarHoraString());
		leadFacade.salvar(lead);
		lead.getCliente().setUnidadenegocio(unidadenegocio);
		ClienteFacade clienteFacade = new ClienteFacade();
		lead.setCliente(clienteFacade.salvar(lead.getCliente()));
		gerarAviso();
		Mensagem.lancarMensagemInfo("Encaminhado com sucesso!", "");
		RequestContext.getCurrentInstance().closeDialog(lead);
		return "";
	}
	
	public String cancelar(){ 
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public void gerarAviso(){
		AvisosFacade avisosFacade = new AvisosFacade();
		Avisos avisos = new Avisos();
		avisos.setData(new Date());
		avisos.setUsuario(usuarioLogadoMB.getUsuario());
		avisos.setImagem("aviso");
		avisos.setLiberar(true);
		avisos.setTexto("Você recebeu uma nova lead - "+lead.getCliente().getNome()+".");
		avisos.setIdunidade(0); 
		List<Avisousuario> lista = new ArrayList<Avisousuario>();
		Avisousuario avisousuario = new Avisousuario();  
		avisousuario.setAvisos(avisos);
		avisousuario.setUsuario(usuario);
		avisousuario.setVisto(false); 
		lista.add(avisousuario);
		avisos.setAvisousuarioList(lista);
		avisos = avisosFacade.salvar(avisos);
	}
}
