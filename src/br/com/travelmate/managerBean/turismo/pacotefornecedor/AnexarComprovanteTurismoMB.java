package br.com.travelmate.managerBean.turismo.pacotefornecedor;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Date;
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

import br.com.travelmate.facade.FornecedorPacoteArquivoPagamentoFacade;
import br.com.travelmate.facade.FtpDadosFacade; 
import br.com.travelmate.facade.PacotesFornecedorFacade;  
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Fornecedorpacotearquivopagamento;
import br.com.travelmate.model.Ftpdados; 
import br.com.travelmate.model.Pacotesfornecedor; 
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class AnexarComprovanteTurismoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;  
	private Pacotesfornecedor pacotesfornecedor; 
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;  
	private Fornecedorpacotearquivopagamento fornecedorpacotearquivopagamento;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pacotesfornecedor = (Pacotesfornecedor) session.getAttribute("pacotesfornecedor");   
		session.removeAttribute("pacotesfornecedor");   
		fornecedorpacotearquivopagamento = new Fornecedorpacotearquivopagamento(); 
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

	public Pacotesfornecedor getPacotesfornecedor() {
		return pacotesfornecedor;
	}

	public void setPacotesfornecedor(Pacotesfornecedor pacotesfornecedor) {
		this.pacotesfornecedor = pacotesfornecedor;
	}

	public Fornecedorpacotearquivopagamento getFornecedorpacotearquivopagamento() {
		return fornecedorpacotearquivopagamento;
	}

	public void setFornecedorpacotearquivopagamento(Fornecedorpacotearquivopagamento fornecedorpacotearquivopagamento) {
		this.fornecedorpacotearquivopagamento = fornecedorpacotearquivopagamento;
	}

	public String salvar() {     
		pacotesfornecedor.setComprovante(true);  
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		pacotesfornecedor = pacotesFornecedorFacade.salvar(pacotesfornecedor); 
		FornecedorPacoteArquivoPagamentoFacade fornecedorPacoteArquivoPagamentoFacade = new FornecedorPacoteArquivoPagamentoFacade();
		fornecedorpacotearquivopagamento.setPacotesfornecedor(pacotesfornecedor);
		fornecedorpacotearquivopagamento.setDataanexado(new Date());
		fornecedorpacotearquivopagamento = fornecedorPacoteArquivoPagamentoFacade
				.salvar(fornecedorpacotearquivopagamento);
		Mensagem.lancarMensagemInfo("Salvo com sucesso!", ""); 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("pacotesfornecedor", fornecedorpacotearquivopagamento);  
		return "enviarEmail";
	}

	public String cancelar() { 
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	// Salvar Nome para o ftp
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
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} 
		fornecedorpacotearquivopagamento.setNomearquivo(arquivo);
		fornecedorpacotearquivopagamento.setNomeftp(nome);
		salvarArquivoFTP(); 
	}

	public boolean salvarArquivoFTP() {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(AnexarComprovanteTurismoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(AnexarComprovanteTurismoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			nomeArquivoFTP = fornecedorpacotearquivopagamento.getNomeftp();
			msg = ftp.enviarArquivo(file, nomeArquivoFTP, "/systm/turismo/comprovantes");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(AnexarComprovanteTurismoMB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(AnexarComprovanteTurismoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}
 
}
