package br.com.travelmate.managerBean.usuario;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.GrupoAcessoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Grupoacesso;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Criptografia;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadUsuariosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private String situacao;
	private List<Unidadenegocio> listaUnidade;
	private List<Departamento> listaDepartamento;
	private List<Grupoacesso> listaGrupoAcesso;
	private String foto;
	private String vende;
	private boolean responsavelUnidade = false;
	private String sql;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		usuario = (Usuario) session.getAttribute("editarUsuario");
		sql = (String) session.getAttribute("sql");
		session.removeAttribute("sql");
		session.removeAttribute("editarUsuario");
		gerarlistaUnidade();
		gerarListaDepartamento();
		gerarListaGrupoAcesso();
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setDepartamento(new Departamento());
			usuario.setUnidadenegocio(new Unidadenegocio());
			usuario.setGrupoacesso(new Grupoacesso());
			usuario.setDataversao(new Date());
		} else {
			if (usuario.getUnidadenegocio().getResponsavelcrm() != null
					&& usuario.getUnidadenegocio().getResponsavelcrm() == usuario.getIdusuario()) {
				responsavelUnidade = true;
			}   
			if (usuario.isVende()) {
				vende = "Sim";
			} else
				vende = "Não";
			if (usuario.isFoto()) {
				foto = "Sim";
			} else
				foto = "Não";
		}
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Grupoacesso> getListaGrupoAcesso() {
		return listaGrupoAcesso;
	}

	public void setListaGrupoAcesso(List<Grupoacesso> listaGrupoAcesso) {
		this.listaGrupoAcesso = listaGrupoAcesso;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getVende() {
		return vende;
	}

	public void setVende(String vende) {
		this.vende = vende;
	}

	public boolean isResponsavelUnidade() {
		return responsavelUnidade;
	}

	public void setResponsavelUnidade(boolean responsavelUnidade) {
		this.responsavelUnidade = responsavelUnidade;
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

	public void gerarListaGrupoAcesso() {
		GrupoAcessoFacade grupoAcessoFacade = new GrupoAcessoFacade();
		listaGrupoAcesso = grupoAcessoFacade.listar("Select g from Grupoacesso g order by g.descricao");
		if (listaGrupoAcesso == null) {
			listaGrupoAcesso = new ArrayList<Grupoacesso>();
		}
	}

	public void validarDados(String msg) {
		if (usuario.getDepartamento() == null && usuario.getDepartamento().getIddepartamento() == null) {
			msg = "Departamento não selecionado.";
		}
		if (usuario.getUnidadenegocio() == null && usuario.getUnidadenegocio().getIdunidadeNegocio() == null) {
			msg = msg + "\n" + "Unidade não selecionada.";
		}
		if (usuario.getGrupoacesso() == null && usuario.getGrupoacesso().getIdgrupoAcesso() == null) {
			msg = msg + "\n" + "Grupo de acesso não selecionado.";
		}
		if (usuario.getEmail() == null && usuario.getEmail().length() < 2) {
			msg = msg + "\n" + "Email não informado.";
		} else {
			if (Formatacao.validarEmail(usuario.getEmail())) {
				msg = msg + "\n" + "Email invalido.";
			}
		}
		if (usuario.getNome() == null && usuario.getNome().length() < 2) {
			msg = msg + "\n" + "Nome não informado.";
		}
		if (usuario.getLogin() == null && usuario.getLogin().length() < 2) {
			msg = msg + "\n" + "Login não informado.";
		}
	}

	public String salvar() {
		String msg = "";
		validarDados(msg);
		if (msg.length() < 2) {
			if (validarLogin()) {
				UsuarioFacade usuarioFacade = new UsuarioFacade();
				if (foto.equalsIgnoreCase("Sim")) {
					usuario.setFoto(true);
				} else
					usuario.setFoto(false);
				if (vende.equalsIgnoreCase("Sim")) {
					usuario.setVende(true);
				} else
					usuario.setVende(false);
				if (usuario.getIdusuario() == null) {
					String senha = "senha";
					try {
						senha = Criptografia.encript(senha);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
					usuario.setSenha(senha);
				}
				usuario = usuarioFacade.salvar(usuario);
				if (responsavelUnidade) {
					UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
					Unidadenegocio unidadenegocio = usuario.getUnidadenegocio();
					unidadenegocio.setResponsavelcrm(usuario.getIdusuario());
					unidadeNegocioFacade.salvar(unidadenegocio);
				}
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
				session.setAttribute("sql", sql);
				return "consUsuario";
			} else {
				Mensagem.lancarMensagemInfo("Usuário já existe.", "Escolha uma novo Login.");
				return "";
			}
		} else {
			Mensagem.lancarMensagemErro("Atenção!", msg);
			return "";
		}
	}

	public boolean validarLogin() {
		if (usuario.getIdusuario() == null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			List<Usuario> listaUsuario;
			listaUsuario = usuarioFacade.listar(
					"select u from Usuario u where u.situacao='Ativo' and u.login='" + usuario.getLogin() + "'");
			if (listaUsuario == null) {
				return true;
			} else if (listaUsuario.size() == 0) {
				return true;
			} else
				return false;
		}
		return true;
	}

	public String cancelar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("sql", sql);
		return "consUsuario";
	}
}
