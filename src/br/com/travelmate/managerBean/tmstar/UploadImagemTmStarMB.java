package br.com.travelmate.managerBean.tmstar;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.TmStarFacade;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Tmstar;
import br.com.travelmate.util.Ftp;

@Named
@ViewScoped
public class UploadImagemTmStarMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tmstar tmstar;
	private UploadedFile file;
	private FileUploadEvent ex;
	private String nomeArquivoFTP;
	private String nomeImagem;
	private Ftpdados ftpdados;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tmstar = (Tmstar) session.getAttribute("tmstar");
		session.removeAttribute("tmstar");
		pegarFtpDados();
	}


	public Tmstar getTmstar() {
		return tmstar;
	}


	public void setTmstar(Tmstar tmstar) {
		this.tmstar = tmstar;
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


	public String getNomeArquivoFTP() {
		return nomeArquivoFTP;
	}


	public void setNomeArquivoFTP(String nomeArquivoFTP) {
		this.nomeArquivoFTP = nomeArquivoFTP;
	}
	


	public Ftpdados getFtpdados() {
		return ftpdados;
	}


	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}
	
	


	public String getNomeImagem() {
		return nomeImagem;
	}


	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}


	public void salvar(){
		TmStarFacade tmStarFacade = new TmStarFacade();
		if (file != null) {
			tmstar.setNomeimagem(nomeImagem);
			tmstar = tmStarFacade.salvar(tmstar);
			RequestContext.getCurrentInstance().closeDialog(tmstar);
		}else{
			RequestContext.getCurrentInstance().closeDialog(new Tmstar());
		}
	}
	
	
	public boolean salvarArquivoFTP(){
		String msg = "";
        FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
        Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(UploadImagemTmStarMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
        if (dadosFTP==null){
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHostupload(),dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()){
                mostrarMensagem(null, "Erro conectar FTP", "");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(UploadImagemTmStarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
        	nomeArquivoFTP = "TMS0" + tmstar.getIdtmstar() + ".png";
        	msg = ftp.enviarArquivo(file, nomeArquivoFTP, "/systm/tmstar/");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(msg, ""));
            ftp.desconectar();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(UploadImagemTmStarMB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
        } 
        return false;
    }
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	
	
	public void fileUploadListener(FileUploadEvent e){
		this.file = e.getFile();
		salvarArquivoFTP();
		String nome = e.getFile().getFileName();
		try {
			nome = new String(nome.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		nomeImagem = nome;
	}
	
	
	public void pegarFtpDados(){
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			  
		}
	}

}
