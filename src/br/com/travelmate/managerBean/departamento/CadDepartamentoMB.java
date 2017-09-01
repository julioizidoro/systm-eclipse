package br.com.travelmate.managerBean.departamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadDepartamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private Usuario usuario;
	private Unidadenegocio unidadenegocio;
	private List<Usuario> listaUsuario;
	private List<Unidadenegocio> listaUnidade;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean habilitarUnidade = false;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        departamento = (Departamento) session.getAttribute("departamento");
        session.removeAttribute("departamento");
        listaUnidade = GerarListas.listarUnidade();
        if (departamento == null) {
			departamento = new Departamento();
		}else{
			usuario = departamento.getUsuario();
			unidadenegocio = departamento.getUnidadenegocio();
			gerarListaUsuario();
		}
        if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarUnidade = true;
			if (unidadenegocio == null) {
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
			gerarListaUsuario();
		}
	}


	public Departamento getDepartamento() {
		return departamento;
	}


	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	
	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}
	
	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}


	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	
	
	
	
	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}


	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}


	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Departamento());
	}
	
	
	public void gerarListaUsuario(){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		String sql = "Select u From Usuario u Where u.situacao='Ativo' ";
		if (unidadenegocio != null) {
			sql = sql + " and u.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		listaUsuario = usuarioFacade.listar(sql);
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<>();
		}
	}
	
	
	public void salvar(){
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		if (validarDados()) {
			departamento.setUnidadenegocio(unidadenegocio);
			departamento.setUsuario(usuario);
			departamento.setLista(false);
			departamento = departamentoFacade.salvar(departamento);
			RequestContext.getCurrentInstance().closeDialog(departamento);
		}
	}
	
	
	public boolean validarDados(){
		if (unidadenegocio == null) {
			Mensagem.lancarMensagemInfo(" Informe a unidade", "");
			return false;
		}
		if (usuario == null) {
			Mensagem.lancarMensagemInfo("Informe o responsável", "");
			return false;
		}
		if (departamento.getNome() == null || departamento.getNome().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe o nome", "");
			return false;
		}
		return true;
	}
	
	
	
	
	

}
