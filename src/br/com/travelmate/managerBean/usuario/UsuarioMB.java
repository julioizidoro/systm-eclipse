package br.com.travelmate.managerBean.usuario;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Criptografia;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class UsuarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome = "";
	private Unidadenegocio unidadenegocio;
	private Departamento departamento;
	private String situacao;
	private List<Unidadenegocio> listaUnidade;
	private List<Departamento> listaDepartamento;
	private List<Usuario> listaUsuario;
	private String sql;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		sql = (String) session.getAttribute("sql");
		session.removeAttribute("sql");
		unidadenegocio = new Unidadenegocio();
		departamento = new Departamento();
		gerarListaUsuario();
		gerarlistaUnidade();
		gerarListaDepartamento();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}

	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public String novoUsuario() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("sql", sql);
		return "cadUsuario";
	}

	public void gerarListaUsuario() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		if(sql==null || sql.length()==0){
			sql = "select u from Usuario u where u.situacao='Ativo' order by u.nome";
		}
		listaUsuario = usuarioFacade.listar(sql);
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
	}

	public void gerarlistaUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar();
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<Unidadenegocio>();
		}
	}

	public void gerarListaDepartamento() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamento = departamentoFacade.listar("Select d from Departamento d order by d.nome");
		if (listaDepartamento == null) {
			listaDepartamento = new ArrayList<Departamento>();
		}
	}

	public String editarUsuario(Usuario usuario) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("editarUsuario", usuario);
		session.setAttribute("sql", sql);
		return "cadUsuario";
	}

	public void pesquisar() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		sql = "select u from Usuario u where u.nome like '%" + nome + "%'";
		if (situacao.length() > 1) {
			sql = sql + " and u.situacao='" + situacao + "'";
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and u.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (departamento != null && departamento.getIddepartamento() != null) {
			sql = sql + " and u.departamento.iddepartamento=" + departamento.getIddepartamento();
		}
		sql = sql + " order by u.nome, u.unidadenegocio.nomerelatorio";
		listaUsuario = usuarioFacade.listar(sql);
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
	}

	public void limpar() {
		unidadenegocio = null;
		departamento = null;
		situacao = null;
		nome = "";
		sql = "select u from Usuario u where u.situacao='Ativo' order by u.nome";
		gerarListaUsuario();
	}

	public void resetarSenhaUsuario(Usuario usuario) {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		String senhaResetada;
		try {
			senhaResetada = Criptografia.encript("senha");
			if (usuario != null) {
				usuario.setSenha(senhaResetada);
				usuarioFacade.salvar(usuario);
				Mensagem.lancarMensagemInfo("Senha alterada com sucesso", "");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
