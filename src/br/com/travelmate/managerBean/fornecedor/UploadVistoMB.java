package br.com.travelmate.managerBean.fornecedor;
 
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.arquivo.CadArquivoMB; 
import br.com.travelmate.model.Ftpdados; 
import br.com.travelmate.model.Pais;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

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
public class UploadVistoMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB; 
    private Pais pais; 
    private UploadedFile file;
    private String nomeArquivoFTP;
    private FileUploadEvent ex;
    private String caminho;
    
    @PostConstruct
    public void init() {
    	 FacesContext fc = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
         pais = (Pais) session.getAttribute("pais");
         session.removeAttribute("pais");
         if(pais==null){
        	 pais = new Pais();
         }else{ 
        	 caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
     		 caminho = caminho + "/documentovisto/" + pais.getDocumentovisto(); 
         } 
    } 

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
 
    public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getNomeArquivoFTP() {
		return nomeArquivoFTP;
	}

	public void setNomeArquivoFTP(String nomeArquivoFTP) {
		this.nomeArquivoFTP = nomeArquivoFTP;
	}

	public FileUploadEvent getEx() {
		return ex;
	}

	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}

	public String salvar(){ 
        pais.setDocumentovisto(nomeArquivoFTP);
        PaisFacade paisFacade = new PaisFacade();
        pais = paisFacade.salvar(pais);
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Upload efetuado.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        RequestContext.getCurrentInstance().closeDialog(pais); 
        return "";
    } 
	
    public String cancelarCadastro(){
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
        	String extensao = file.getFileName().substring(file.getFileName().lastIndexOf("."), file.getFileName().length());
        	nomeArquivoFTP = pais.getIdpais()+extensao;
        	msg = ftp.enviarArquivo(file, nomeArquivoFTP, "/systm/documentovisto");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(msg, "")); 
            ftp.desconectar();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
        }  
        return false;
    }
    
    
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
  	
  	public boolean excluirArquivoFTP(){
  		if(pais.getDocumentovisto()!=null && pais.getDocumentovisto().length()>0){
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
	        	String nomeArquivoFTP = pais.getDocumentovisto();
	        	msg = ftp.excluirArquivo(nomeArquivoFTP, "/systm/documentovisto/");
	        	PaisFacade paisFacade = new PaisFacade();
	        	pais.setDocumentovisto(null);
	        	pais=paisFacade.salvar(pais);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(null, new FacesMessage(msg, "")); 
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
  		}else Mensagem.lancarMensagemErro("Atenção", "Não possui documentos inserido.");
        return false;
    }
  	
  	public boolean habilitarDocumentosVisto() { 
		if(pais.getDocumentovisto()!=null && pais.getDocumentovisto().length()>0){
			return false;
		}
		return true;
	}
	  
}
