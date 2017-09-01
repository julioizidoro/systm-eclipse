package br.com.travelmate.managerBean.funcao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.FuncaoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Funcao;
import br.com.travelmate.model.Usuario;

@Named
@ViewScoped
public class VerrFuncaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Funcao funcao;
	private Usuario usuario;
	private String descricao;
	
	
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        usuario = (Usuario) session.getAttribute("usuario");
	        if (usuario != null) {
				pegarInformacoesUsuario();
			}
		}
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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
	
	


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public void pegarInformacoesUsuario(){
		FuncaoFacade funcaoFacade = new FuncaoFacade();
		funcao = funcaoFacade.consultar("Select f From Funcao f Where f.usuario.idusuario=" + usuario.getIdusuario());
		if (funcao == null) {
			funcao = new Funcao();
			descricao = "Nenhuma função informada";
		}else{
			descricao = funcao.getDescricao();
		}
	}
	
	

}
