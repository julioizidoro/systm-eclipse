package br.com.travelmate.managerBean.controleSolicitacoes;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.AvisosDao;

import br.com.travelmate.facade.TiSolicitacoesFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Tisolicitacoes;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class CadSolicitacoesMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AvisosDao avisosDao;
	private Tisolicitacoes tisolicitacoes;
	private Departamento departamento;
	private List<Departamento> listaDepartamento;
	private boolean habilitarCampos = true;
	private List<Usuario> listaUsuario;
	private Usuario usuario;
	
	
	@PostConstruct
	public void init(){
		tisolicitacoes = new Tisolicitacoes();
		tisolicitacoes.setDatasolicitacao(new Date());
		if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessogerencialsolicitacoes()) {
			habilitarCampos = false;
		}else{
			habilitarCampos = true;
		}
		usuario = usuarioLogadoMB.getUsuario();
		gerarListaUsuario();
	}



	public Tisolicitacoes getTisolicitacoes() {
		return tisolicitacoes;
	}



	public void setTisolicitacoes(Tisolicitacoes tisolicitacoes) {
		this.tisolicitacoes = tisolicitacoes;
	}
	
	
	
	public Departamento getDepartamento() {
		return departamento;
	}



	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}



	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}



	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}



	public boolean isHabilitarCampos() {
		return habilitarCampos;
	}



	public void setHabilitarCampos(boolean habilitarCampos) {
		this.habilitarCampos = habilitarCampos;
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



	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Tisolicitacoes());
	}
	
	
	
	public boolean validarDados(){
		if (tisolicitacoes.getDescricao() == null || tisolicitacoes.getDescricao().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe uma descrição", "");
			return false;
		}
		
		if (usuario == null) {
			Mensagem.lancarMensagemInfo("Informe o Usuário", "");
			return false;
		}
		
		return true;
	}
	
	
	
	public void salvar(){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		TiSolicitacoesFacade tiSolicitacoesFacade = new TiSolicitacoesFacade();
		if (validarDados()) {
			tisolicitacoes.setSituacao("Nova");
			tisolicitacoes.setUsuario(usuario);
			tisolicitacoes.setConcluido(false);
			tisolicitacoes.setLiberar(false);
			tisolicitacoes.setUsuarioExecutor(usuarioFacade.consultar(1));
			tisolicitacoes.setDatasolicitacao(new Date());
			tisolicitacoes = tiSolicitacoesFacade.salvar(tisolicitacoes);
			notificarNovaSolicitacao();
			RequestContext.getCurrentInstance().closeDialog(tisolicitacoes);
		}
	}
	
	
	
	public void gerarListaUsuario() {
		listaUsuario = GerarListas.listarUsuarios("Select u FROM Usuario u where u.situacao='Ativo' order by u.nome");
	}
	
	
	public void notificarNovaSolicitacao(){
		Avisos avisos = new Avisos();
		Avisousuario avisousuario = new Avisousuario();
		avisos.setData(new Date());
		avisos.setDepartamento(usuario.getDepartamento().getNome());
		avisos.setIdunidade(usuario.getUnidadenegocio().getIdunidadeNegocio());
		avisos.setImagem("aviso");
		avisos.setLiberar(true);
		avisos.setIdvenda(0);
		avisos.setUsuario(usuario);
		avisos.setTexto("Nova Solicitação de " + usuario.getNome() + " da unidade " + usuario.getUnidadenegocio().getNomerelatorio());
		avisos = avisosDao.salvar(avisos);
		List<Usuario> listaUsuario = GerarListas.listarUsuarios("SELECT u FROM Usuario u WHERE (u.idusuario=1 or u.idusuario=125 or u.idusuario=134)");
		for (int i = 0; i < listaUsuario.size(); i++) {
			avisousuario = new Avisousuario();
			avisousuario.setAvisos(avisos);
			avisousuario.setUsuario(listaUsuario.get(i));
			avisousuario.setVisto(false);
			avisosDao.salvar(avisousuario);
		}
	}
	
	

}
