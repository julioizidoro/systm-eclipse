package br.com.travelmate.managerBean.acessounidade;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.AcessoUnidadeFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Acessounidade;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadAcessoUnidadeMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB; 
	private Acessounidade acessoUnidade;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		acessoUnidade = (Acessounidade) session.getAttribute("acessounidade");
		session.removeAttribute("acessounidade");
		gerarListaUsuario();
		if(acessoUnidade!=null) {
			usuario = acessoUnidade.getUsuario();
		}
	}

	
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public Acessounidade getAcessoUnidade() {
		return acessoUnidade;
	}


	public void setAcessoUnidade(Acessounidade acessoUnidade) {
		this.acessoUnidade = acessoUnidade;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	} 

	public String cancelar() {
		return "consAcessoUnidade";
	}
	
	public String salvar() {
		if(usuario!=null && usuario.getIdusuario()!=null) {
			acessoUnidade.setUsuario(usuario);
			AcessoUnidadeFacade acessoUnidadeFacade = new AcessoUnidadeFacade();
			acessoUnidade = acessoUnidadeFacade.salvar(acessoUnidade);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			return "consAcessoUnidade";
		}else Mensagem.lancarMensagemErro("Atenção!", "Usuário não informado.");
		return "";
	}
	
	public void gerarListaUsuario() {
		String sql = "SELECT u FROM Usuario u WHERE u.situacao='Ativo'";
		if(!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " AND u.unidadenegocio.idunidadeNegocio="+usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql + " ORDER BY u.nome";
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaUsuario = usuarioFacade.listar(sql);
	}

}
