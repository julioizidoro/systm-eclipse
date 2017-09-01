package br.com.travelmate.managerBean.fornecedor;

import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.arquivo.CadArquivoMB;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Ftpdados;
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
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class EditarFornecedorCidadeMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
    private Fornecedorcidade fornecedorcidade;
    private UploadedFile file;
    private String caminho;
    
    
    @PostConstruct
    public void init() {
    	 FacesContext fc = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
         fornecedorcidade = (Fornecedorcidade) session.getAttribute("fornecedorcidade");
         session.removeAttribute("fornecedorcidade");
         if(fornecedorcidade==null){
        	 fornecedorcidade = new Fornecedorcidade();
         }else{
        	 caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
     		 caminho = caminho + "/fornecedorcidade/" + fornecedorcidade.getIdfornecedorcidade() + ".png"; 
         }
    }

    public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String salvar(){
        FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
        fornecedorcidade = fornecedorCidadeFacade.salvar(fornecedorcidade);
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        RequestContext.getCurrentInstance().closeDialog(fornecedorcidade);
        return "";
    }
    
    
    public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    
    public void fileUploadListener(FileUploadEvent e){
		this.file = e.getFile();
		salvarArquivoFTP();
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
        	fornecedorcidade.setImagem(fornecedorcidade.getIdfornecedorcidade()+".png");
        	msg = ftp.enviarArquivo(file, fornecedorcidade.getImagem(), "/systm/fornecedorcidade");
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
        	msg = ftp.excluirArquivo(fornecedorcidade.getImagem(), "/systm/fornecedorcidade/");
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
	  
}
