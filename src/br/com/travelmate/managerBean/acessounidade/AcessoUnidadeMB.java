package br.com.travelmate.managerBean.acessounidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.AcessoUnidadeFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Acessounidade;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class AcessoUnidadeMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private String nome;
	private List<Acessounidade> listaAcesso;
	private List<Unidadenegocio> listaUnidade;
	private List<Usuario> listaUsuario;
	private boolean desabilitarCombo = false;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	
	@PostConstruct
	public void init() {
		listaUnidade = GerarListas.listarUnidade();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			desabilitarCombo = true;
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			usuario = usuarioLogadoMB.getUsuario();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Acessounidade> getListaAcesso() {
		return listaAcesso;
	}

	public void setListaAcesso(List<Acessounidade> listaAcesso) {
		this.listaAcesso = listaAcesso;
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

	public boolean isDesabilitarCombo() {
		return desabilitarCombo;
	}

	public void setDesabilitarCombo(boolean desabilitarCombo) {
		this.desabilitarCombo = desabilitarCombo;
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

	public void gerarListaAcesso() {
		String sql ="SELECT a FROM Acessounidade a WHERE a.usuario.situacao='Ativo'";
		if(unidadenegocio!=null) {
			sql = sql + " AND a.usuario.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario != null && usuario.getIdusuario() != null) {
			sql = sql + " AND a.usuario.idusuario=" + usuario.getIdusuario();
		}
		sql = sql + " ORDER BY a.usuario.nome";
		AcessoUnidadeFacade acessoUnidadeFacade = new AcessoUnidadeFacade();
		listaAcesso = acessoUnidadeFacade.lista(sql);
		if(listaAcesso==null) {
			listaAcesso = new ArrayList<>();
		}
	}
	
	public String novo() {
		return "cadAcessoUnidade";
	}
	
	public String editar(Acessounidade acessounidade) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("acessounidade", acessounidade);
		return "cadAcessoUnidade";
	}
	
	public void limpar() {
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			usuario = null;
			unidadenegocio = null;
			gerarListaConsultor();
		}
		gerarListaAcesso();
	}
	
	public String retornarAcesso(boolean acesso) {
		if(acesso) {
			return "../../resources/img/confirmar.png";
		}else return "../../resources/img/cancelado.png";
	}
	
	
	public void gerarListaConsultor() {
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaUsuario = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo'" + " and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		}else {
			listaUsuario = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo' order by u.nome");
		}
		usuario = null;
	}

}
