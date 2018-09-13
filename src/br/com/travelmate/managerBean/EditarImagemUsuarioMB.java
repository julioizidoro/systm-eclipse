/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean;
 
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.arquivo.CadArquivoMB; 
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Ftp;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException; 
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named; 
import javax.swing.JOptionPane;

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
	private AplicacaoMB AplicacaoMB;
    @Inject 
    private UsuarioLogadoMB usuarioLogadoMB;
    private String caminho; 
    private UploadedFile file;
    private String nomeArquivoFTP;
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
		caminho = AplicacaoMB.getParametrosprodutos().getCaminhoimagens();
		if (usuarioLogadoMB.getUsuario().isFoto()) {
			caminho = caminho + "/usuario/" + usuarioLogadoMB.getUsuario().getIdusuario() + ".jpg";
		} else
			caminho = caminho + "/usuario/0.png";
		return caminho;
	}
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	
	public void fileUploadListener(FileUploadEvent e){
		this.file = e.getFile();
		if(usuarioLogadoMB.getUsuario().isFoto()){
			excluirArquivoFTP();
		}
		salvarArquivoFTP(); 
		getFotoUsuarioLogado();
		//salvarFotoUsuarioLogado();
		mensagemAviso = "Em questão de minutos sua foto será atualizada.";
	}
    
    
    
    public boolean salvarArquivoFTP(){
		String msg = "";
        FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
        Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
        if (dadosFTP==null){
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()){
                mostrarMensagem(null, "Erro conectar FTP", "");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
        	nomeArquivoFTP = usuarioLogadoMB.getUsuario().getIdusuario()+".jpg";
        	msg = ftp.enviarArquivo(file, nomeArquivoFTP, "/systm/usuario");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(msg, "")); 
            if(!usuarioLogadoMB.getUsuario().isFoto()){
            	salvarUsuario();
            }
            ftp.desconectar();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
        } 
        try {
           ftp.desconectar();
        } catch (IOException ex) {
            Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
        }
        return false;
    }
    
    
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
  	
  	public boolean excluirArquivoFTP(){
		String msg = "";
        FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
        Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex); 
		}
        if (dadosFTP==null){
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()){
                mostrarMensagem(null, "Erro conectar FTP", "");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
        	String nomeArquivoFTP = usuarioLogadoMB.getUsuario().getIdusuario()+".jpg";
        	msg = ftp.excluirArquivo(nomeArquivoFTP, "/systm/usuario");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(msg, "")); 
            ftp.desconectar();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
        } 
        try {
           ftp.desconectar();
        } catch (IOException ex) {
            Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
        }
        return false;
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
