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

import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadEncaminhadoDao;
import br.com.travelmate.dao.LeadResponsavelDao;
import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadencaminhado;
import br.com.travelmate.model.Leadresponsavel;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtos;
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
	private LeadResponsavelDao leadResponsavelDao;
	@Inject
	private LeadDao leadDao;
	@Inject
	private LeadEncaminhadoDao leadEncaminhadoDao;
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
	private boolean trocaUnidade = false;
	private List<Leadresponsavel> listaResponsavel;
	private boolean desabilitarUsuario = false;
	private Produtos produto;
	private List<Produtos> listaProdutos;
	private Pais pais;
	private List<Pais> listaPais;

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
		gerarListaPais();
		gerarListaProdutos();
		if(!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
			listaResponsavel = retornarResponsavelUsuario();
			if (listaResponsavel != null && listaResponsavel.size() > 0) {
				habilitarComboUnidade = false;
			}
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaUsuario();  
		}  else{
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaUsuario();  
			habilitarComboUnidade=false;
		}
		tipocontato = lead.getTipocontato();
		situacao = lead.getSituacao();
		pais = lead.getPais();
		produto = lead.getProdutos();
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

	public boolean isTrocaUnidade() {
		return trocaUnidade;
	}

	public void setTrocaUnidade(boolean trocaUnidade) {
		this.trocaUnidade = trocaUnidade;
	}

	public List<Leadresponsavel> getListaResponsavel() {
		return listaResponsavel;
	}

	public void setListaResponsavel(List<Leadresponsavel> listaResponsavel) {
		this.listaResponsavel = listaResponsavel;
	}

	public boolean isDesabilitarUsuario() {
		return desabilitarUsuario;
	}

	public void setDesabilitarUsuario(boolean desabilitarUsuario) {
		this.desabilitarUsuario = desabilitarUsuario;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
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
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		if(unidadenegocio!=null){
			List<Leadresponsavel> listaResponsavel = retornarResponsavelUnidade();
			boolean responsavel = false;
			if (listaResponsavel != null && listaResponsavel.size() > 0) {
				responsavel = true;
			}
			if(lead.getUnidadenegocio().getIdunidadeNegocio()==6 
					&& unidadenegocio.getIdunidadeNegocio()!=1
					&& unidadenegocio.getIdunidadeNegocio()!=2
					&& unidadenegocio.isLeadautomatica() && listaResponsavel!=null) {
				listaUsuario = new ArrayList<Usuario>();
				for (int i = 0; i < listaResponsavel.size(); i++) {
					listaUsuario.add(listaResponsavel.get(i).getUsuario());
				} 
			}else if(lead.getUnidadenegocio().getIdunidadeNegocio() != unidadenegocio.getIdunidadeNegocio()) {
				listaUsuario = new ArrayList<Usuario>();
				if (lead.getUnidadenegocio().getIdunidadeNegocio() == 6 && unidadenegocio.getIdunidadeNegocio() ==2) {
					listaUsuario = usuarioFacade.listar("SELECT u FROM Usuario u WHERE u.unidadenegocio.idunidadeNegocio=2 and u.situacao='Ativo'");
				}else {
					if (responsavel && (usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() == 1 ||  usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() == 18)
							&& (lead.getUnidadenegocio().getIdunidadeNegocio() == 1 || lead.getUnidadenegocio().getIdunidadeNegocio()==18)
							&& (unidadenegocio.getIdunidadeNegocio() == 1 || unidadenegocio.getIdunidadeNegocio()==18)) {
						listaUsuario = usuarioFacade.listar("SELECT u FROM Usuario u WHERE u.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio() +" and u.situacao='Ativo'");
						desabilitarUsuario = false;
					}else {
						for (int i = 0; i < listaResponsavel.size(); i++) {
							listaUsuario.add(listaResponsavel.get(i).getUsuario());
						} 
						if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()==1 || usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()==2) {
							desabilitarUsuario = false;
						}else desabilitarUsuario = true;
					}
					if (listaUsuario != null && listaUsuario.size() > 0) {
						lead.setUsuario(listaUsuario.get(0));
						usuario = listaUsuario.get(0);
					}
				}
				
			}else {
				listaUsuario = GerarListas.listarUsuarios("Select u FROM Usuario u where u.situacao='Ativo'"
					+ " and u.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
				desabilitarUsuario = false;
			}
		}
	}
	
	public void mudarSituacao(int situacao) {
		this.situacao = situacao;
	}
	
	public String salvar(){
		if (validarDados()) {
			leadencaminhado.setData(new Date());
			leadencaminhado.setHora(Formatacao.foramtarHoraString());
			leadencaminhado.setUsuariode(usuarioLogadoMB.getUsuario());
			leadencaminhado.setUsuariopara(usuario);
			leadEncaminhadoDao.salvar(leadencaminhado);
			lead.setUnidadenegocio(unidadenegocio);
			lead.setNomeunidade(unidadenegocio.getNomerelatorio());
			lead.setSituacao(situacao);
			lead.setTipocontato(tipocontato);
			lead.setUsuario(usuario); 
			lead.setPais(pais);
			lead.setProdutos(produto);
			if (lead.getDataenvio() == null) {
				lead.setDataenvio(new Date());
			}
			leadDao.salvar(lead);
			lead.getCliente().setUnidadenegocio(unidadenegocio);
			ClienteFacade clienteFacade = new ClienteFacade();
			lead.setCliente(clienteFacade.salvar(lead.getCliente()));
			gerarAviso();
			Mensagem.lancarMensagemInfo("Encaminhado com sucesso!", "");
			RequestContext.getCurrentInstance().closeDialog(lead);
		}
		return "";
	}
	
	public boolean validarDados() {
		if (usuario == null || usuario.getIdusuario() == null) {
			Mensagem.lancarMensagemInfo("Usuario não informado", "");
			return false;
		}
		
		if (tipocontato == null || tipocontato.getIdtipocontato() == null) {
			Mensagem.lancarMensagemInfo("Tipo de Contato não informado", "");
			return false;
		}
		
		if (unidadenegocio == null || unidadenegocio.getIdunidadeNegocio() == null) {
			Mensagem.lancarMensagemInfo("Unidade não informada", "");
			return false;
		}
		
		if (produto == null || produto.getIdprodutos() == null) {
			Mensagem.lancarMensagemInfo("Programa não informado", "");
			return false;
		}
		
		if (pais == null || pais.getIdpais() == null) {
			Mensagem.lancarMensagemInfo("Pais não informado", "");
			return false;
		}
		return true;
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
		if(lead.getUnidadenegocio().getIdunidadeNegocio()!=6 || !unidadenegocio.isLeadautomatica()) {
			avisos.setImagem("aviso");
		}else {
			avisos.setImagem("lead");
		}
		avisos.setLiberar(true);
		Date dataEnvio;
		if (lead.getDataenvio() == null) {
			dataEnvio = new Date();
		}else{
			dataEnvio = lead.getDataenvio();
		}
		avisos.setTexto("Você recebeu uma nova lead - Nome: "+lead.getCliente().getNome()+". Encaminhada por "+
				usuarioLogadoMB.getUsuario().getNome()+". Incluida no dia: " + Formatacao.ConvercaoDataPadrao(dataEnvio) + "; Distribuida no dia: " 
				+ Formatacao.ConvercaoDataPadrao(new Date()));
		avisos.setIdunidade(0); 
		if (trocaUnidade) {
			listaResponsavel = retornarResponsavelUnidade();
			if (listaResponsavel != null) {
				List<Avisousuario> lista = new ArrayList<Avisousuario>();
				for (int i = 0; i < listaResponsavel.size(); i++) {
					Avisousuario avisousuario = new Avisousuario();  
					avisousuario.setAvisos(avisos);
					avisousuario.setUsuario(listaResponsavel.get(i).getUsuario());
					avisousuario.setVisto(false); 
					lista.add(avisousuario);
				}
				avisos.setAvisousuarioList(lista);
				avisos = avisosFacade.salvar(avisos);
			}
		}else {
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
	
	public List<Leadresponsavel> retornarResponsavelUnidade() { 
		List<Leadresponsavel> lista = leadResponsavelDao
				.lista(unidadenegocio.getIdunidadeNegocio()); 
		if (lista == null) {
			lista = new ArrayList<Leadresponsavel>();
		}
		return lista;
	}
	
	
	public List<Leadresponsavel> retornarResponsavelUsuario() { 
		List<Leadresponsavel> lista = leadResponsavelDao
				.lista("SELECT l FROM Leadresponsavel l WHERE l.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario() + " and l.unidadenegocio.idunidadeNegocio=" + 
						usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()); 
		if (lista == null) {
			lista = new ArrayList<Leadresponsavel>();
		}
		return lista;
	}
	
	
	
	public void gerarListaProdutos() {
		listaProdutos = GerarListas.listarProdutos("");
		if (listaProdutos==null) {
			listaProdutos = new ArrayList<Produtos>();
		}
	}
	   
	
	public void gerarListaPais() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar("");
		if (listaPais==null) {
			listaPais = new ArrayList<Pais>();
		}
	}
	
	
}
