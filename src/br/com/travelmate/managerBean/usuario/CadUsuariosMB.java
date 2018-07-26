package br.com.travelmate.managerBean.usuario;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.dao.LeadResponsavelDao;
import br.com.travelmate.facade.AcessoUnidadeFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.GrupoAcessoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Acessounidade;
import br.com.travelmate.model.Cargo;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Grupoacesso;
import br.com.travelmate.model.Leadresponsavel;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Criptografia;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadUsuariosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	@Inject
	private LeadResponsavelDao leadResponsavelDao;
	private Usuario usuario;
	private String situacao;
	private List<Unidadenegocio> listaUnidade;
	private List<Departamento> listaDepartamento;
	private List<Grupoacesso> listaGrupoAcesso;
	private List<Cargo> listaCargo;
	private String foto;
	private String vende;
	private boolean responsavelUnidade = false;
	private String sql;
	private boolean loginexistente = false;

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
		listaCargo = GerarListas.listarCargo();
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setDepartamento(new Departamento());
			usuario.setUnidadenegocio(new Unidadenegocio());
			usuario.setGrupoacesso(new Grupoacesso());
			usuario.setDataversao(new Date());
			usuario.setCargo(null);
		} else {
			responsavelUnidade = retornarResponsavelUnidade(); 
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
	
	public List<Cargo> getListaCargo() {
		return listaCargo;
	}

	public void setListaCargo(List<Cargo> listaCargo) {
		this.listaCargo = listaCargo;
	}

	public boolean isLoginexistente() {
		return loginexistente;
	}

	public void setLoginexistente(boolean loginexistente) {
		this.loginexistente = loginexistente;
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
		if (usuario.getCargo() == null && usuario.getCargo().getIdcargo() == null) {
			msg = "Cargo não selecionado.";
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
			if (verificarNovoLogin()) {
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
				verificarResponsavelUnidade();
				salvarAcessoUnidade();
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
				session.setAttribute("sql", sql);
				return "consUsuario";
			} else {
				Mensagem.lancarMensagemInfo("Usuário já existe.", "Escolha um novo Login.");
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
	
	public boolean retornarResponsavelUnidade() {
		int idusuario = usuario.getIdusuario();
		List<Leadresponsavel> lista = leadResponsavelDao
				.lista(usuario.getUnidadenegocio().getIdunidadeNegocio());
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getUsuario().getIdusuario() == idusuario) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void verificarResponsavelUnidade() {
		if (responsavelUnidade) {
			boolean possui = retornarResponsavelUnidade();
			if(!possui) {
				salvarLeadResponsavel();
			}
		}else {
			boolean possui = retornarResponsavelUnidade();
			if(possui) {
				excluirLeadResponsavel();
			}
		}
	}
	
	public void salvarLeadResponsavel() {
		Leadresponsavel leadresponsavel = new Leadresponsavel();
		leadresponsavel.setUnidadenegocio(usuario.getUnidadenegocio());
		leadresponsavel.setUsuario(usuario);
		leadResponsavelDao.salvar(leadresponsavel);
	}
	
	public void excluirLeadResponsavel() {
		Leadresponsavel leadresponsavel = leadResponsavelDao.consultar
				("SELECT l FROM Leadresponsavel l WHERE l.unidadenegocio.idunidadeNegocio="+usuario.getUnidadenegocio().getIdunidadeNegocio()
						+ " AND l.usuario.idusuario="+usuario.getIdusuario());
		leadResponsavelDao.excluir(leadresponsavel.getIdleadresponsavel());
	}
	
	public void salvarAcessoUnidade() {
		AcessoUnidadeFacade acessoUnidadeFacade = new AcessoUnidadeFacade();
		Acessounidade acessounidade = acessoUnidadeFacade.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
				+usuario.getIdusuario());
		if(acessounidade==null || acessounidade.getIdacessounidade() == null) {
			acessounidade = new Acessounidade();
			acessounidade.setComissaoparceiros(true);
			acessounidade.setConsultaorcamento(false);
			acessounidade.setCrm(false);
			acessounidade.setDashboard(true);
			acessounidade.setEmissaoconsulta(true); 
			acessounidade.setUsuario(usuario);
			acessounidade.setMargemfinanceira(true);
			acessounidade.setPosvendaunidade(false);
			acessounidade = acessoUnidadeFacade.salvar(acessounidade);
		}
	} 
	
	
	public boolean verificarNovoLogin(){
		if (usuario.getIdusuario() == null) {
			if (usuario.getLogin().length() > 0) {
				UsuarioFacade usuarioFacade = new UsuarioFacade();
				List<Usuario> listaUsuario = usuarioFacade.listar("SELECT u FROM Usuario u WHERE u.login like '%" + usuario.getLogin() + "%'");
				if (listaUsuario == null || listaUsuario.isEmpty()) {
					return true;
				}else{
					Mensagem.lancarMensagemInfo("Login já existente", "");
					return false;
				}
			}
			
		}
		return true;
	}
	
}
