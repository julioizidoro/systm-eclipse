package br.com.travelmate.managerBean.versoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.GrupoAcessoFacade;
import br.com.travelmate.facade.VersaoUsuarioFacade;
import br.com.travelmate.facade.VersoesFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Grupoacesso;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Versaousuario;
import br.com.travelmate.model.Versoes;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadVersoesMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Versoes versoes;
	private List<Usuario> listaUsuario;
	private Grupoacesso grupoacesso;
	private List<Grupoacesso> listaGrupoAcesso;
	private Versaousuario versaousuario;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	
	
	@PostConstruct
	public void init(){
		gerarListaGrupoAcesso();
		if (versoes == null) {
			versoes = new Versoes();
		}
	}


	public Versoes getVersoes() {
		return versoes;
	}


	public void setVersoes(Versoes versoes) {
		this.versoes = versoes;
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	public Grupoacesso getGrupoacesso() {
		return grupoacesso;
	}


	public void setGrupoacesso(Grupoacesso grupoacesso) {
		this.grupoacesso = grupoacesso;
	}


	public List<Grupoacesso> getListaGrupoAcesso() {
		return listaGrupoAcesso;
	}


	public void setListaGrupoAcesso(List<Grupoacesso> listaGrupoAcesso) {
		this.listaGrupoAcesso = listaGrupoAcesso;
	}
	
	
	public Versaousuario getVersaousuario() {
		return versaousuario;
	}


	public void setVersaousuario(Versaousuario versaousuario) {
		this.versaousuario = versaousuario;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public void gerarListaGrupoAcesso(){
		GrupoAcessoFacade grupoAcessoFacade = new GrupoAcessoFacade();
		listaGrupoAcesso = grupoAcessoFacade.listar("Select g From Grupoacesso g");
		if (listaGrupoAcesso == null) {
			listaGrupoAcesso = new ArrayList<>();
		}
	}
	
	
	public void gerarListaUsuario(){
		String sql = "Select u From Usuario u Where u.situacao='Ativo' ";
		if (grupoacesso != null) {
			sql = sql + " and u.grupoacesso.idgrupoAcesso=" + grupoacesso.getIdgrupoAcesso();
		}
		listaUsuario = GerarListas.listarUsuarios(sql);
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<>();
		}
	}
	 
	
	public void salvar(){
		if (validarDados()) {
			gerarListaUsuario();
			VersoesFacade versoesFacade = new VersoesFacade();
			versoes.setUsuario(usuarioLogadoMB.getUsuario());
			versoes = versoesFacade.salvar(versoes);
			salvarVersoesUsuario();
			RequestContext.getCurrentInstance().closeDialog(versoes);
		}
	}
	
	
	
	public void salvarVersoesUsuario(){
		VersaoUsuarioFacade versaoUsuarioFacade = new VersaoUsuarioFacade();
		for (int i = 0; i < listaUsuario.size(); i++) {
			versaousuario = new Versaousuario();
			versaousuario.setVersoes(versoes);
			versaousuario.setUsuario(listaUsuario.get(i));
			versaoUsuarioFacade.salvar(versaousuario);
		}
	}
	
	
	public boolean validarDados(){
		if (versoes.getDescricao() == null || versoes.getDescricao().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe a descrição \r\n", "");
			return false;
		}
		if (versoes.getData() == null) {
			Mensagem.lancarMensagemInfo("Informe a data \r\n", "");
			return false;
		}
		if (versoes.getModulo() == null || versoes.getModulo().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe o Modulo \r\n", "");
			return false;
		}
		return true;
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Versoes());
	}
	

}
