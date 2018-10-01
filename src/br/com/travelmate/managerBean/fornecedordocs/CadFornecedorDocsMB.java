package br.com.travelmate.managerBean.fornecedordocs;

import br.com.travelmate.facade.FornecedorDocsFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.cloud.midia.CadVideoMB;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedordocs;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class CadFornecedorDocsMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Fornecedor fornecedor;
	private List<Fornecedor> listaFornecedor;
	private Fornecedordocs fornecedordocs;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fornecedordocs = (Fornecedordocs) session.getAttribute("fornecedordocs");
		session.removeAttribute("fornecedordocs");
		gerarListaFornecedor();
		if (fornecedordocs == null) {
			fornecedordocs = new Fornecedordocs();
			fornecedordocs.setDatainicio(new Date());
		} else {
			fornecedor = fornecedordocs.getFornecedor();
		}
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Fornecedordocs getFornecedordocs() {
		return fornecedordocs;
	}

	public void setFornecedordocs(Fornecedordocs fornecedordocs) {
		this.fornecedordocs = fornecedordocs;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String getNomeArquivoFTP() {
		return nomeArquivoFTP;
	}

	public void setNomeArquivoFTP(String nomeArquivoFTP) {
		this.nomeArquivoFTP = nomeArquivoFTP;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<String> getListaNomeArquivo() {
		return listaNomeArquivo;
	}

	public void setListaNomeArquivo(List<String> listaNomeArquivo) {
		this.listaNomeArquivo = listaNomeArquivo;
	}

	public FileUploadEvent getEx() {
		return ex;
	}

	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}

	public void gerarListaFornecedor() {
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		listaFornecedor = fornecedorFacade
				.listar("select f from Fornecedor f where f.idfornecedor>1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}

	public String cancelar() {
		return "fornecedorDocs";
	}

	public String salvar() {
		if (validarDados()) {
			FornecedorDocsFacade fornecedorDocsFacade = new FornecedorDocsFacade();
			fornecedordocs.setFornecedor(fornecedor);
			fornecedordocs.setDataupload(new Date());
			fornecedordocs.setHora(retornarHoraAtual());
			fornecedordocs = fornecedorDocsFacade.salvar(fornecedordocs);
			Mensagem.lancarMensagemInfo("Documento salvo com sucesso!", "");
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("fornecedordocs", fornecedordocs);
			return "cadFornecedorCidadeDocs";
		} else {
			FacesMessage mensagem = new FacesMessage("Faltam informações! ", "");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public boolean validarDados() {
		if (fornecedor == null || fornecedor.getIdfornecedor() == null) {
			return false;
		}
		if (fornecedordocs.getTipo() == null || fornecedordocs.getTipo().length() == 0) {
			return false;
		}
		if (fornecedordocs.getDescricao() == null || fornecedordocs.getDescricao().length() == 0) {
			return false;
		}
		if (fornecedordocs.getNome() == null || fornecedordocs.getNome().length() == 0) {
			return false;
		}
		if (fornecedordocs.getDatainicio() == null) {
			Mensagem.lancarMensagemErro("Data de início obrigatória!", "");
			return false;
		}
		return true;
	}

	public void fileUploadListener(FileUploadEvent e) {
		if (validarDados()) {
			this.file = e.getFile();
			salvarArquivoFTP();
			fornecedordocs.setNomeftp(nomeArquivoFTP);
			if (listaNomeArquivo == null) {
				listaNomeArquivo = new ArrayList<>();
			}
			listaNomeArquivo.add(file.getFileName());
			Mensagem.lancarMensagemInfo("Salvo com sucesso.", "");
		} else {
			FacesMessage mensagem = new FacesMessage("Faltam informações! ", "");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
	}
	
	public boolean salvarArquivoFTP(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String nomeArquivo = nomeArquivo(file.getFileName());
		String pasta = "";
		nomeArquivo = nomeArquivo + "_" + new String(file.getFileName());
		String arquivo = servletContext.getRealPath("/arquivos/");
		String destino ="";
		if (fornecedordocs.getTipo().equalsIgnoreCase("Vídeo")) {
			int pos = nomeArquivoFTP.indexOf(".");
			String nomevideo = nomeArquivoFTP.substring(0, pos);
			String videoConverter = String.format(nomevideo + ".mov", file);
			nomeArquivoFTP = videoConverter;
			pasta = "/videos";
			destino = "treinamento";
		} else {
			nomeArquivoFTP = nomeArquivo(file.getFileName());
			pasta = "/cloud/departamentos";
			destino = "docs";
		}
		//String nomeArquivoFile = arquivo + nomeArquivo;
		String caminho = servletContext.getRealPath("/resources/aws.properties");
		UploadAWSS3 s3 = new UploadAWSS3(destino, caminho);
		File arquivoFile = s3.getFile(file, nomeArquivoFTP);
		String msg = "";
		if (s3.uploadFile(arquivoFile, pasta)) {
			msg = "Arquivo: " + nomeArquivo + " enviado com sucesso";
		} else {
			msg = " Erro no nome do arquivo";
		}
         FacesContext context = FacesContext.getCurrentInstance();
         context.addMessage(null, new FacesMessage(msg, ""));
         arquivoFile.delete();
        return false;
    }

//	public boolean salvarArquivoFTP() {
//		String msg = "";
//		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
//		Ftpdados dadosFTP = null;
//		String pasta = "";
//		try {
//			dadosFTP = ftpDadosFacade.getFTPDados();
//		} catch (SQLException ex) {
//			Logger.getLogger(CadVideoMB.class.getName()).log(Level.SEVERE, null, ex);
//			mostrarMensagem(ex, "Erro", "");
//		}
//		if (dadosFTP == null) {
//			return false;
//		}
//		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
//		try {
//			if (!ftp.conectar()) {
//				mostrarMensagem(null, "Erro conectar FTP", "");
//				return false;
//			}
//		} catch (IOException ex) {
//			Logger.getLogger(CadVideoMB.class.getName()).log(Level.SEVERE, null, ex);
//			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
//		}
//		try {
//			nomeArquivoFTP = nomeArquivo(file.getFileName());
//			if (fornecedordocs.getTipo().equalsIgnoreCase("Vídeo")) {
//				int pos = nomeArquivoFTP.indexOf(".");
//				String nomevideo = nomeArquivoFTP.substring(0, pos);
//				String videoConverter = String.format(nomevideo + ".mov", file);
//				nomeArquivoFTP = videoConverter;
//				pasta = "/videos/";
//			} else {
//				nomeArquivoFTP = nomeArquivo(file.getFileName().trim());
//				pasta = "/cloud/departamentos/";
//			}
//			msg = ftp.enviarArquivo(file, nomeArquivoFTP, pasta);
//			FacesContext context = FacesContext.getCurrentInstance();
//			context.addMessage(null, new FacesMessage(msg, ""));
//			ftp.desconectar();
//			return true;
//		} catch (IOException ex) {
//			Logger.getLogger(CadVideoMB.class.getName()).log(Level.SEVERE, null, ex);
//			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
//		}
//		return false;
//	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String nomeArquivo(String nome) {
		nomeArquivoFTP = fornecedor.getIdfornecedor() + "_" + nome;
		return nomeArquivoFTP;
	}
	
	
	public String retornarHoraAtual(){
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    Date hora = Calendar.getInstance().getTime();
	    return sdf.format(hora);
	}

}