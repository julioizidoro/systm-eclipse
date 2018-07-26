package br.com.travelmate.managerBean.crm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;

@Named
@ViewScoped
public class RelatorioClienteLeadMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LeadDao leadDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Usuario> listaUsuario;
	private Usuario usuario;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private boolean desabilitarUnidade = false;
	private List<Lead> listaLeads;
	
	
	
	
	@PostConstruct
	public void init() {
		gerarListaUnidadeNegocio();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			desabilitarUnidade = true;
			gerarListaConsultor();
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			usuario = usuarioLogadoMB.getUsuario();
		}
	}







	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}







	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}







	public Usuario getUsuario() {
		return usuario;
	}







	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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




	public boolean isDesabilitarUnidade() {
		return desabilitarUnidade;
	}




	public void setDesabilitarUnidade(boolean desabilitarUnidade) {
		this.desabilitarUnidade = desabilitarUnidade;
	}
	
	







	public List<Lead> getListaLeads() {
		return listaLeads;
	}







	public void setListaLeads(List<Lead> listaLeads) {
		this.listaLeads = listaLeads;
	}







	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}
	
	
	public void gerarListaConsultor() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaUsuario = usuarioFacade
					.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		}
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
	}
	
	
	public void gerarPesquisaLeads() {
		String sql = "SELECT l FROM Lead l WHERE l.cliente.nome like '%%'";
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and l.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario != null && usuario.getIdusuario() != null) {
			sql = sql + " and l.usuario.idusuario=" + usuario.getIdusuario();
		}
		listaLeads = leadDao.lista(sql);
	}
	
	
	public void limpar() {
		listaLeads = new ArrayList<>();
	}
	
	public String retornarCoresSituacao(int numeroSituacao) {
		if (numeroSituacao == 1) {
			return "#1E90FF;";
		} else if (numeroSituacao == 2) {
			return "#2E5495;";
		} else if (numeroSituacao == 3) {
			return "#9ACD32;";
		} else if (numeroSituacao == 4) {
			return "#FF8C00;";
		} else if (numeroSituacao == 5) {
			return "#B22222;";
		} else if (numeroSituacao == 6) {
			return "#228B22;";
		} else if (numeroSituacao == 7) {
			return "#8B8989;";
		} else if (numeroSituacao == 8) {
			return "#9400D3;";
		}
		return "";
	}

}
