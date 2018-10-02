/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package br.com.travelmate.managerBean.financeiro.faturafranquia;

import br.com.travelmate.facade.FaturaComprovanteFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.arquivo.CadArquivoMB;
import br.com.travelmate.model.Fatura;
import br.com.travelmate.model.Faturacomprovante;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class FaturaComprovanteMB implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Fatura fatura;
	private Faturacomprovante faturacomprovante;
	private List<Faturacomprovante> listaFaturaComprovante;
	private boolean alteracao;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private Ftpdados ftpdados;
	private String urlArquivo = "";

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fatura = (Fatura) session.getAttribute("fatura");
		alteracao = (boolean) session.getAttribute("alteracao");
		session.removeAttribute("alteracao");
		session.removeAttribute("fatura");
		if (fatura != null) {
			faturacomprovante = new Faturacomprovante();
			gerarListaComprovante();
		}
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		ftpdados = new Ftpdados();
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
			if (ftpdados != null) {
				urlArquivo = ftpdados.getProtocolo() + "://" + ftpdados.getHost() + ":" + ftpdados.getWww();
			}
		} catch (SQLException e) {
			  
		}
	}

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public boolean isAlteracao() {
		return alteracao;
	}

	public void setAlteracao(boolean alteracao) {
		this.alteracao = alteracao;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Faturacomprovante getFaturacomprovante() {
		return faturacomprovante;
	}

	public void setFaturacomprovante(Faturacomprovante faturacomprovante) {
		this.faturacomprovante = faturacomprovante;
	}

	public List<Faturacomprovante> getListaFaturaComprovante() {
		return listaFaturaComprovante;
	}

	public void setListaFaturaComprovante(List<Faturacomprovante> listaFaturaComprovante) {
		this.listaFaturaComprovante = listaFaturaComprovante;
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

	public Ftpdados getFtpdados() {
		return ftpdados;
	}

	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public String salvarFaturaComprovante() {
		FaturaComprovanteFacade faturaComprovanteFacade = new FaturaComprovanteFacade();
		faturacomprovante.setFatura(fatura);
		faturacomprovante.setData(new Date());
		faturacomprovante.setNome(nomeArquivoFTP);
		faturacomprovante.setUsuario(usuarioLogadoMB.getUsuario());
		faturacomprovante = faturaComprovanteFacade.salvar(faturacomprovante);
		Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	// Salvar Nome para o ftp
	public String nomeArquivo() {
		nomeArquivoFTP = fatura.getIdfatura() + "_" + fatura.getUnidadenegocio().getIdunidadeNegocio() + "_"
				+ fatura.getMes() + "_" + fatura.getAno() + "_" + file.getFileName().trim();
		return nomeArquivoFTP;
	}

	// Salvar nome do arquivo para tabela arquivos
	public String nomeArquivoSalvo(String nome) {
		nomeArquivoFTP = fatura.getIdfatura() + "_" + fatura.getUnidadenegocio().getIdunidadeNegocio() + "_"
				+ fatura.getMes() + "_" + fatura.getAno() + "_" + nome;
		return nomeArquivoFTP;
	}

	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile();
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
		salvarFaturaComprovante();
	}

	public boolean salvarArquivoFTP() {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			nomeArquivoFTP = nomeArquivoSalvo(file.getFileName().trim());
			msg = ftp.enviarArquivo(file, nomeArquivoFTP, "/systm/faturacomprovante");
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

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public void gerarListaComprovante() {
		FaturaComprovanteFacade faturaComprovanteFacade = new FaturaComprovanteFacade();
		listaFaturaComprovante = faturaComprovanteFacade
				.listar("select f from Faturacomprovante f where f.fatura.idfatura=" + fatura.getIdfatura());
		if (listaFaturaComprovante == null) {
			listaFaturaComprovante = new ArrayList<>();
		}
	}
}
