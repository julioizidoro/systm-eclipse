package br.com.travelmate.managerBean.funcao;

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
import br.com.travelmate.facade.FuncaoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Funcao;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class CadFuncaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Funcao funcao;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private Departamento departamento;
	private List<Departamento> listaDepartamento;
	private boolean habilitarDepartamento = false;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        funcao = (Funcao) session.getAttribute("funcao");
        session.removeAttribute("funcao");
        gerarListaDepartamento();
        if (funcao == null) {
			funcao = new Funcao();
		}else{
			departamento = funcao.getUsuario().getDepartamento();
			usuario = funcao.getUsuario();
			gerarListaUsuario();
		}
        if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1) {
			departamento = usuarioLogadoMB.getUsuario().getDepartamento();
			habilitarDepartamento = true;
			gerarListaUsuario();
		}
	}
   


	public Funcao getFuncao() {
		return funcao;
	}



	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
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
	
	
	
	

	public boolean isHabilitarDepartamento() {
		return habilitarDepartamento;
	}



	public void setHabilitarDepartamento(boolean habilitarDepartamento) {
		this.habilitarDepartamento = habilitarDepartamento;
	}



	public void gerarListaDepartamento(){
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamento = departamentoFacade.listar("Select d From Departamento d");
		if (listaDepartamento == null) {
			listaDepartamento = new ArrayList<>();
		}
	}
	
	
	public void gerarListaUsuario(){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		String sql = "Select u From Usuario u Where u.situacao='Ativo' ";
		if (departamento != null) {
			sql = sql + " and u.departamento.iddepartamento=" + departamento.getIddepartamento();
		}
		listaUsuario = usuarioFacade.listar(sql);
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<>();
		}
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Funcao());
	}
	
	
	public void salvar(){
		FuncaoFacade funcaoFacade = new FuncaoFacade();
		if (validarDados()) {
			funcao.setUsuario(usuario);
			funcao = funcaoFacade.salvar(funcao);
			RequestContext.getCurrentInstance().closeDialog(funcao);
		}
	}
	
	
	public boolean validarDados(){
		if (departamento == null) {
			Mensagem.lancarMensagemInfo("Informe o departamento", "");
			return false;
		}
		if (usuario == null) {
			Mensagem.lancarMensagemInfo("Informe o departamento", "");
			return false;
		}
		if (funcao.getDescricao() == null || funcao.getDescricao().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe a função", "");
		}
		return true;
	}
	
	
	

}
