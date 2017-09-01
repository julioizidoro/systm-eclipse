package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

import br.com.travelmate.facade.FornecedorArquivoFacade;
import br.com.travelmate.facade.FornecedorArquivoTipoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Fornecedorarquivo;
import br.com.travelmate.model.Fornecedorarquivotipo;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadFornecedorArquivoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fornecedorcidade fornecedorCidade;
	private Fornecedorarquivo arquivo;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private Fornecedorarquivotipo fornecedorTipoArquivo;
	private List<Fornecedorarquivotipo> listaTipo;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fornecedorCidade = (Fornecedorcidade) session.getAttribute("fornecedorCidade");
		if (arquivo == null) {
			arquivo = new Fornecedorarquivo();
			fornecedorTipoArquivo = new Fornecedorarquivotipo();
			gerarListaTipo();
		}
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public Fornecedorarquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Fornecedorarquivo arquivo) {
		this.arquivo = arquivo;
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

	public FileUploadEvent getEx() {
		return ex;
	}

	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}

	public List<String> getListaNomeArquivo() {
		return listaNomeArquivo;
	}

	public void setListaNomeArquivo(List<String> listaNomeArquivo) {
		this.listaNomeArquivo = listaNomeArquivo;
	}

	public List<UploadedFile> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<UploadedFile> listaFile) {
		this.listaFile = listaFile;
	}

	public String nomeArquivo(String nome) {
		nomeArquivoFTP = fornecedorCidade.getIdfornecedorcidade() + "_" + nome;
		return nomeArquivoFTP;
	}

	public Fornecedorarquivotipo getFornecedorTipoArquivo() {
		return fornecedorTipoArquivo;
	}

	public void setFornecedorTipoArquivo(Fornecedorarquivotipo fornecedorTipoArquivo) {
		this.fornecedorTipoArquivo = fornecedorTipoArquivo;
	}

	public List<Fornecedorarquivotipo> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List<Fornecedorarquivotipo> listaTipo) {
		this.listaTipo = listaTipo;
	}

	public boolean salvarArquivoFTP() {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadFornecedorArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(CadFornecedorArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			for (int i = 0; i < listaFile.size(); i++) {
				nomeArquivoFTP = nomeArquivo(listaFile.get(i).getFileName().trim());
				msg = ftp.enviarArquivo(listaFile.get(i), nomeArquivoFTP, "/fornecedorcidade/");
			}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			return true;
		} catch (IOException ex) {
			Logger.getLogger(CadFornecedorArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(CadFornecedorArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile();
		if (listaFile == null) {
			listaFile = new ArrayList<UploadedFile>();
		}
		listaFile.add(file);
		salvarArquivoFTP();
		String nome = e.getFile().getFileName();
		try {
			nome = new String(nome.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (listaNomeArquivo == null) {
			listaNomeArquivo = new ArrayList<String>();
		}
		listaNomeArquivo.add(nome);
	}

	public String salvar() {
		if (listaNomeArquivo == null || listaNomeArquivo.isEmpty()) {
			Mensagem.lancarMensagemInfo("Atenção", "você não fez nenhum upload de arquivo!");
		} else {
			for (int j = 0; j < listaNomeArquivo.size(); j++) {
				arquivo = new Fornecedorarquivo();
				FornecedorArquivoFacade fornecedorArquivoFacade = new FornecedorArquivoFacade();
				arquivo.setFornecedorcidade(fornecedorCidade);
				arquivo.setFornecedorarquivotipo(fornecedorTipoArquivo);
				arquivo.setNomearquivo(nomeArquivoFTP);
				arquivo = fornecedorArquivoFacade.salvar(arquivo);
			}
			RequestContext.getCurrentInstance().closeDialog(arquivo);
		}
		return "";
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(new Arquivo1());
		return "";
	}

	public void gerarListaTipo() {
		FornecedorArquivoTipoFacade fornecedorArquivoTipoFacade = new FornecedorArquivoTipoFacade();
		String sql = "select f from Fornecedorarquivotipo f order by f.nome";
		listaTipo = fornecedorArquivoTipoFacade.listar(sql);
	}

}
