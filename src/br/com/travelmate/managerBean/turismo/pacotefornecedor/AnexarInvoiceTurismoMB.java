package br.com.travelmate.managerBean.turismo.pacotefornecedor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;  
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.FornecedorPacoteArquivoInvoiceFacade;
import br.com.travelmate.facade.FtpDadosFacade; 
import br.com.travelmate.facade.PacotesFornecedorFacade;  
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Fornecedorpacotearquivoinvoice;
import br.com.travelmate.model.Ftpdados; 
import br.com.travelmate.model.Pacotesfornecedor; 
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;

@Named
@ViewScoped
public class AnexarInvoiceTurismoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;  
	private Pacotesfornecedor pacotesfornecedor; 
	private Fornecedorpacotearquivoinvoice fornecedorpacotearquivoinvoice;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;  

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pacotesfornecedor = (Pacotesfornecedor) session.getAttribute("pacotesfornecedor");   
		session.removeAttribute("pacotesfornecedor");  
		fornecedorpacotearquivoinvoice = new Fornecedorpacotearquivoinvoice();
	}

	public FileUploadEvent getEx() {
		return ex;
	}

	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getNomeArquivoFTP() {
		return nomeArquivoFTP;
	}

	public void setNomeArquivoFTP(String nomeArquivoFTP) {
		this.nomeArquivoFTP = nomeArquivoFTP;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	} 

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String salvar() {      
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		pacotesfornecedor = pacotesFornecedorFacade.salvar(pacotesfornecedor);  
		FornecedorPacoteArquivoInvoiceFacade fornecedorPacoteArquivoInvoiceFacade = new FornecedorPacoteArquivoInvoiceFacade();
		fornecedorpacotearquivoinvoice.setPacotesfornecedor(pacotesfornecedor);
		fornecedorpacotearquivoinvoice = fornecedorPacoteArquivoInvoiceFacade.salvar(fornecedorpacotearquivoinvoice);
		Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");  
		RequestContext.getCurrentInstance().closeDialog(pacotesfornecedor); 
		return "";
	}

	public String cancelar() { 
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	} 
	
	public String nomeArquivo() {
		nomeArquivoFTP = pacotesfornecedor.getIdpacotesfornecedor()+"";
		return nomeArquivoFTP;
	} 
	
	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile(); 
		String nome = pacotesfornecedor.getIdpacotesfornecedor()+"_"+e.getFile().getFileName();
		String arquivo = e.getFile().getFileName();
		try {
			nome = new String(nome.getBytes(Charset.defaultCharset()), "UTF-8"); 
			arquivo = new String(arquivo.getBytes(Charset.defaultCharset()), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} 
		fornecedorpacotearquivoinvoice.setNomearquivo(arquivo);
		fornecedorpacotearquivoinvoice.setNomeftp(nome);
		salvarArquivoFTP(); 
	}
	
	public boolean salvarArquivoFTP() {
		String msg = "";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String nomeArquivo = fornecedorpacotearquivoinvoice.getNomeftp();
			nomeArquivo = nomeArquivo + "_" + new String(file.getFileName());
		String arquivo = servletContext.getRealPath("/arquivos/");
		String nomeArquivoFile = arquivo + nomeArquivo;
		String caminho = servletContext.getRealPath("/resources/aws.properties");
		UploadAWSS3 s3 = new UploadAWSS3("local", caminho);
		File arquivoFile = s3.getFile(file, nomeArquivoFile);
		if (s3.uploadFile(arquivoFile, "turismo")) {
			msg = "Arquivo: " + nomeArquivo + " enviado com sucesso";
		} else {
			msg = " Erro no nome do arquivo";
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(msg, ""));
		arquivoFile.delete();
		return true;
	}

	

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}
 
}
