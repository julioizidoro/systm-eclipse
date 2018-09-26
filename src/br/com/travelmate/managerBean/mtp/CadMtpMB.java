package br.com.travelmate.managerBean.mtp;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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

import br.com.travelmate.dao.AvisosDao;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.MtpFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Mtp;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadMtpMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AvisosDao avisosDao;
	private Departamento departamento;
	private Mtp mtp;
	private Pais pais;
	private List<Departamento> listaDepartamento;
	private List<Pais> listaPais;
	private String notificacoesValidarDados;
	private List<Usuario> listaUsuario;
	private Mtp mtpAlteracao;
	private List<AlteracoesMtpBean> listaAlteracao;
	private AlteracoesMtpBean alteracoesMtp;
	private String horaMTp;
	private Date dataMtp;
	private boolean isNovoMtp = true;
	private String tipoTreinamento = "";
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex; 
	private List<UploadedFile> listaFile;
	private List<Arquivos> listaArquivos;
	private String nomeArquivo;
	private boolean arquivoEnviado = false;

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

	public List<UploadedFile> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<UploadedFile> listaFile) {
		this.listaFile = listaFile;
	}

	public List<Arquivos> getListaArquivos() {
		return listaArquivos;
	}

	public void setListaArquivos(List<Arquivos> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		mtp = (Mtp) session.getAttribute("mtp");
		session.removeAttribute("mtp");
		if (mtp == null) {
			mtp = new Mtp();
		} else {
			departamento = mtp.getDepartamento();
			pais = mtp.getPais();
			dataMtp = mtp.getData();
			horaMTp = mtp.getHora();
		}
		listarDepartamentos();
		listarPais();
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Mtp getMtp() {
		return mtp;
	}

	public void setMtp(Mtp mtp) {
		this.mtp = mtp;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}

	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Mtp getMtpAlteracao() {
		return mtpAlteracao;
	}

	public void setMtpAlteracao(Mtp mtpAlteracao) {
		this.mtpAlteracao = mtpAlteracao;
	}

	public List<AlteracoesMtpBean> getListaAlteracao() {
		return listaAlteracao;
	}

	public void setListaAlteracao(List<AlteracoesMtpBean> listaAlteracao) {
		this.listaAlteracao = listaAlteracao;
	}

	public AlteracoesMtpBean getAlteracoesMtp() {
		return alteracoesMtp;
	}

	public void setAlteracoesMtp(AlteracoesMtpBean alteracoesMtp) {
		this.alteracoesMtp = alteracoesMtp;
	}

	public String getHoraMTp() {
		return horaMTp;
	}

	public void setHoraMTp(String horaMTp) {
		this.horaMTp = horaMTp;
	}

	public Date getDataMtp() {
		return dataMtp;
	}

	public void setDataMtp(Date dataMtp) {
		this.dataMtp = dataMtp;
	}

	public String getNotificacoesValidarDados() {
		return notificacoesValidarDados;
	}

	public void setNotificacoesValidarDados(String notificacoesValidarDados) {
		this.notificacoesValidarDados = notificacoesValidarDados;
	}

	public boolean isNovoMtp() {
		return isNovoMtp;
	}

	public void setNovoMtp(boolean isNovoMtp) {
		this.isNovoMtp = isNovoMtp;
	}

	public String getTipoTreinamento() {
		return tipoTreinamento;
	}

	public void setTipoTreinamento(String tipoTreinamento) {
		this.tipoTreinamento = tipoTreinamento;
	}

	public void listarDepartamentos() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamento = departamentoFacade.listar("Select d From Departamento d");
		if (listaDepartamento == null) {
			listaDepartamento = new ArrayList<Departamento>();
		}
	}

	public void listarPais() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		if (listaPais == null) {
			listaPais = new ArrayList<Pais>();
		}
	}

	public boolean validarDados() {
		notificacoesValidarDados = "";
		if (departamento == null) {
			notificacoesValidarDados = notificacoesValidarDados + " Departamento nao informado \r\n";
			return false;
		}

		if (pais == null) {
			notificacoesValidarDados = notificacoesValidarDados + " Pais nao informado \r\n";
			return false;
		}

		if (mtp.getIdmtp() != null) {
			isNovoMtp = false;
		}

		if (tipoTreinamento == null || tipoTreinamento.length() < 1) {
			notificacoesValidarDados = notificacoesValidarDados + " Tipo de Treinamento não informado \r\n";
			return false;
		}

		return true;
	}

	public void salvar() {
		MtpFacade mtpFacade = new MtpFacade();
		if (validarDados()) {
			mtp.setHost("https://www.gotomeet.me/TravelMate");
			mtp.setDepartamento(departamento);
			mtp.setPais(pais);
			mtp.setTipo(tipoTreinamento);
			if(nomeArquivoFTP!=null){
				
				mtp.setNomearquivo(nomeArquivoFTP + "_" + nomeArquivo);
			}
			mtp = mtpFacade.salvar(mtp);
			if (isNovoMtp) {
				RequestContext.getCurrentInstance().closeDialog(mtp);
			} else {
				verificarAlteracoes();
				RequestContext.getCurrentInstance().closeDialog(listaAlteracao);
			}
		} else {
			Mensagem.lancarMensagemInfo(notificacoesValidarDados, "");
		}
	}

	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog(new Mtp());
	}

	public void gerarAvisos() {
		listarUsuario();
		Avisousuario avisousuario;
		Avisos avisos;
		for (int i = 0; i < listaUsuario.size(); i++) {
			avisos = new Avisos();
			avisousuario = new Avisousuario();
			avisos.setData(new Date());
			avisos.setDepartamento(departamento.getNome());
			avisos.setIdunidade(0);
			avisos.setLiberar(true);
			avisos.setUsuario(listaUsuario.get(i));
			avisos.setImagem("aviso");
			avisos.setTexto("Teremos um novo treinamento do departamento: " + departamento.getNome()
					+ ", verifique nas consultas de MTP, " + " data e horario do treinamento.");
			avisos = avisosDao.salvar(avisos);
			avisousuario.setAvisos(avisos);
			avisousuario.setUsuario(listaUsuario.get(i));
			avisousuario.setVisto(true);
			avisousuario = avisosDao.salvar(avisousuario);
		}
	}

	public void listarUsuario() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaUsuario = usuarioFacade.listar("Select u From Usuario u Where u.situacao='Ativo'");
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
	}

	public void verificarAlteracoes() {
		alteracoesMtp = new AlteracoesMtpBean();
		listaAlteracao = new ArrayList<>();
		if (!mtp.getData().equals(dataMtp)) {
			alteracoesMtp.setAlteracao(" Data alterada de " + Formatacao.ConvercaoDataPadrao(dataMtp) + " para "
					+ Formatacao.ConvercaoDataPadrao(mtp.getData()) + ";");
			alteracoesMtp.setDepartamento(departamento.getNome());
			listaAlteracao.add(alteracoesMtp);
			alteracoesMtp = new AlteracoesMtpBean();
		}

		if (!mtp.getHora().equalsIgnoreCase(horaMTp)) {
			alteracoesMtp.setAlteracao(" Hora alterada de " + horaMTp + " para " + mtp.getHora() + ";");
			alteracoesMtp.setDepartamento(departamento.getNome());
			listaAlteracao.add(alteracoesMtp);
			alteracoesMtp = new AlteracoesMtpBean();
		}
	}

	// Salvar Nome para o ftp
	public String nomeArquivo() {
		nomeArquivoFTP = mtp.getInstituicao() + "_" + file.getFileName().trim();
		return nomeArquivoFTP;
	}

	// Salvar nome do arquivo para tabela arquivos
	public String nomeArquivoSalvo() {
		nomeArquivoFTP = "" +  departamento.getIddepartamento();
		return nomeArquivoFTP;
	}

	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile();
		salvarArquivoFTP();
		if (arquivoEnviado) {
			String nome = e.getFile().getFileName();
			try {
				nomeArquivo = new String(nome.getBytes(Charset.defaultCharset()), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} 
		}
	}

	public boolean salvarArquivoFTP() {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadMtpMB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(CadMtpMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			nomeArquivoFTP = nomeArquivoSalvo();
			arquivoEnviado = ftp.enviarArquivoDOCS(file, nomeArquivoFTP, "/systm/mtparquivo");
			if (arquivoEnviado) {
				msg = "Arquivo: " + nomeArquivoFTP + " enviado com sucesso";
			}else{
				msg = "Erro ao salvar arquivo";
			}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(CadMtpMB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(CadMtpMB.class.getName()).log(Level.SEVERE, null, ex);
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
