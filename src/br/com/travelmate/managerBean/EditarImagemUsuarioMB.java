/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean;
 
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.UploadAWSS3;

import java.io.File;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class EditarImagemUsuarioMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
    @Inject 
    private UsuarioLogadoMB usuarioLogadoMB;
    private String caminho; 
    private UploadedFile file;
    private FileUploadEvent ex;
    private String mensagemAviso;
    
    @PostConstruct
    public void init() { 
    	if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
    		getFotoUsuarioLogado();
    	} 
	} 
    
    public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	} 

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	} 


	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	   
    public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public FileUploadEvent getEx() {
		return ex;
	}

	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}

	public String getMensagemAviso() {
		return mensagemAviso;
	}

	public void setMensagemAviso(String mensagemAviso) {
		this.mensagemAviso = mensagemAviso;
	}

	public String getFotoUsuarioLogado() { 
		caminho = "http://local.systm.com.br/usuario/";
		if (usuarioLogadoMB.getUsuario().isFoto()) {
			caminho = caminho + usuarioLogadoMB.getUsuario().getIdusuario() + ".jpg";
		} else
			caminho = caminho + "0.png";
		return caminho;
	}
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	
	public void fileUploadListener(FileUploadEvent e){
		FacesContext facesContext = FacesContext.getCurrentInstance();  
        ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
		this.file = e.getFile();
		String caminho =  servletContext.getRealPath("/resources/aws.properties");
		UploadAWSS3 s3 = new UploadAWSS3("local", caminho);
		File novoArquivo = s3.getFile(this.file, usuarioLogadoMB.getUsuario().getIdusuario() + ".jpg");
		s3.uploadFile(novoArquivo, "usuario"); 
		getFotoUsuarioLogado();
		mensagemAviso = "Em questão de minutos sua foto será atualizada.";
	}
    
    
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
  	
  	
	  
  	public void salvarUsuario(){
  		Usuario usuario = usuarioLogadoMB.getUsuario();
  		usuario.setFoto(true);
  		UsuarioFacade usuarioFacade = new UsuarioFacade();
  		usuario = usuarioFacade.salvar(usuario);
  	}
  	
  	
  	public void salvarFotoUsuarioLogado(){
  		UsuarioFacade usuarioFacade = new UsuarioFacade();
  		Usuario usuario = usuarioFacade.consultar(usuarioLogadoMB.getUsuario().getIdusuario());
  		usuarioFacade.salvar(usuario);
		usuario.setSenha("");
  		usuarioLogadoMB.setUsuario(usuario);
  	}
}
