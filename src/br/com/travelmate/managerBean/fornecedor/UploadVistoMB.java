package br.com.travelmate.managerBean.fornecedor;
 

import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Pais;
import br.com.travelmate.util.Mensagem;
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
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.amazonaws.services.s3.model.S3ObjectSummary;

@Named
@ViewScoped
public class UploadVistoMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PaisDao paisDao;
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
        
        pais = paisDao.salvar(pais);
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
    
    public boolean salvarArquivoFTP() {
		String msg = "";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String extensao = file.getFileName().substring(file.getFileName().lastIndexOf("."), file.getFileName().length());
    	nomeArquivoFTP = pais.getIdpais()+extensao;
		String arquivo = servletContext.getRealPath("/arquivos/");
		String nomeArquivoFile = arquivo + nomeArquivoFTP;
		String caminho = servletContext.getRealPath("/resources/aws.properties");
		UploadAWSS3 s3 = new UploadAWSS3("local", caminho);
		File arquivoFile = s3.getFile(file, nomeArquivoFile);
		if (s3.uploadFile(arquivoFile, "documentovisto")) {
			msg = "Arquivo: " + nomeArquivoFTP + " enviado com sucesso";
		} else {
			msg = " Erro no nome do arquivo";
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(msg, ""));
		arquivoFile.delete();
		return true;
	}
      
   
    
    
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
    
    public boolean excluirArquivoFTP() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
			UploadAWSS3 s3 = new UploadAWSS3("local", caminho);
			S3ObjectSummary objectSummary = new S3ObjectSummary();
			objectSummary.setKey("documentovisto/" + pais.getDocumentovisto());
			if(s3.delete(objectSummary)) {
				Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
				
	        	pais.setDocumentovisto(null);
	        	pais=paisDao.salvar(pais);
				return true;
			}else {
				Mensagem.lancarMensagemInfo("Falha ao excluir", "");
				return false;
			}
	}
  	
  	public boolean habilitarDocumentosVisto() { 
		if(pais.getDocumentovisto()!=null && pais.getDocumentovisto().length()>0){
			return false;
		}
		return true;
	}
	  
}
