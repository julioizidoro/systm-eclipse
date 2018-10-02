package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.FornecedorArquivoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Fornecedorarquivo;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class FornecedorArquivosMB implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private Fornecedorarquivo arquivo;
	private List<Fornecedorarquivo> listaFornecedorArquivo;
	private FornecedorArquivoBean arquivoBean;
	private List<FornecedorArquivoBean> listaArquivoBean;
	private Fornecedorcidade fornecedorCidade;
	private Ftpdados ftpDados;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean semArquivo;
	private boolean HabilitarBtnNovo;
	private String urlArquivo = "";

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			fornecedorCidade = (Fornecedorcidade) session.getAttribute("fornecedorCidade");
			session.removeAttribute("fornecedorCidade");
			gerarArquivosFornecedor();
			ftpDados = new Ftpdados();
			FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
			try {
				ftpDados = ftpDadosFacade.getFTPDados();
				if (ftpDados != null) {
					urlArquivo = ftpDados.getProtocolo() + "://" + ftpDados.getHost() + ":" + ftpDados.getWww();
				}
			} catch (SQLException e) {
				  
			}
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9) {
				HabilitarBtnNovo = true;
			} else
				HabilitarBtnNovo = false;
			// Verificar se existe pastas ou arquivos na tela
			semConteudo();
		}
	}

	public Fornecedorarquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Fornecedorarquivo arquivo) {
		this.arquivo = arquivo;
	}

	public List<Fornecedorarquivo> getListaFornecedorArquivo() {
		return listaFornecedorArquivo;
	}

	public void setListaFornecedorArquivo(List<Fornecedorarquivo> listaFornecedorArquivo) {
		this.listaFornecedorArquivo = listaFornecedorArquivo;
	}

	public FornecedorArquivoBean getArquivoBean() {
		return arquivoBean;
	}

	public void setArquivoBean(FornecedorArquivoBean arquivoBean) {
		this.arquivoBean = arquivoBean;
	}

	public List<FornecedorArquivoBean> getListaArquivoBean() {
		return listaArquivoBean;
	}

	public void setListaArquivoBean(List<FornecedorArquivoBean> listaArquivoBean) {
		this.listaArquivoBean = listaArquivoBean;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public Ftpdados getFtpDados() {
		return ftpDados;
	}

	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public boolean isSemArquivo() {
		return semArquivo;
	}

	public void setSemArquivo(boolean semArquivo) {
		this.semArquivo = semArquivo;
	}

	public boolean isHabilitarBtnNovo() {
		return HabilitarBtnNovo;
	}

	public void setHabilitarBtnNovo(boolean habilitarBtnNovo) {
		HabilitarBtnNovo = habilitarBtnNovo;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public void gerarArquivosFornecedor() {
		FornecedorArquivoFacade fornecedorArquivoFacade = new FornecedorArquivoFacade();
		listaFornecedorArquivo = fornecedorArquivoFacade
				.listar("Select f from Fornecedorarquivo f where f.fornecedorcidade.idfornecedorcidade="
						+ fornecedorCidade.getIdfornecedorcidade() + " and (f.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or f.datavalidade is null)");
		if (listaFornecedorArquivo == null) {
			listaFornecedorArquivo = new ArrayList<Fornecedorarquivo>();
		} else {
			listaArquivoBean = new ArrayList<FornecedorArquivoBean>();
		}
		for (int i = 0; i < listaFornecedorArquivo.size(); i++) {
			arquivoBean = new FornecedorArquivoBean();
			arquivoBean.setArquivo1(listaFornecedorArquivo.get(i));
			if ((i + 1) < listaFornecedorArquivo.size()) {
				arquivoBean.setArquivo2(listaFornecedorArquivo.get(i + 1));
				i++;
				if ((i + 1) < listaFornecedorArquivo.size()) {
					arquivoBean.setArquivo3(listaFornecedorArquivo.get(i + 1));
					i++;
					if ((i + 1) < listaFornecedorArquivo.size()) {
						arquivoBean.setArquivo4(listaFornecedorArquivo.get(i + 1));
						i++;
					} else {
						arquivoBean.setArquivo4(null);
					}
				} else {
					arquivoBean.setArquivo3(null);
					arquivoBean.setArquivo4(null);
				}
			} else {
				arquivoBean.setArquivo2(null);
				arquivoBean.setArquivo3(null);
				arquivoBean.setArquivo4(null);
			}
			listaArquivoBean.add(arquivoBean);
		}
	}

	public String pegarNomeArquivo(Arquivo1 arquivo1) {
		if (arquivo1 != null) {
			String nome = arquivo1.getNome();
			return nome;
		}
		return "";
	}

	public String alterarNomeArquivo(Arquivo1 arquivo1) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("arquivo1", arquivo1);
		RequestContext.getCurrentInstance().openDialog("editarArquivo1", options, null);
		return "";
	}

	public String identificarExtensao(String nomeArquivo) {
		String extensao = "";
		for (int i = 0; i < nomeArquivo.length(); i++) {
			if (nomeArquivo.charAt(nomeArquivo.length() - i) != '.') {
				extensao = nomeArquivo.charAt(nomeArquivo.length() - i) + extensao;
			} else {
				i = nomeArquivo.length() + 100;
			}
		}
		if (extensao.length() > 4) {
			extensao = "pdf";
		}
		if (extensao.equalsIgnoreCase("jpg") || extensao.equalsIgnoreCase("jpeg") || extensao.equalsIgnoreCase("png")) {
			return "../../resources/img/icone_jpg.png";
		} else if (extensao.equalsIgnoreCase("pdf")) {
			return "../../resources/img/icone_pdf.png";
		} else if (extensao.equalsIgnoreCase("docx")) {
			return "../../resources/img/icone_docx.png";
		} else if (extensao.equalsIgnoreCase("xls")) {
			return "../../resources/img/icone_xls.png";
		}
		return "";
	}

	public String retornaIconeArquivo(String nomeArquivo) {
		if (nomeArquivo == "") {
			return "";
		}
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		if (extensao.length() > 4) {
			extensao = ".pdf";
		}
		if (extensao.equalsIgnoreCase(".jpg") || extensao.equalsIgnoreCase(".jpeg")
				|| extensao.equalsIgnoreCase(".png")) {
			return "../../resources/img/icone_jpg.png";
		} else if (extensao.equalsIgnoreCase(".pdf")) {
			return "../../resources/img/icone_pdf.png";
		} else if (extensao.equalsIgnoreCase(".docx") || extensao.equalsIgnoreCase(".doc")) {
			return "../../resources/img/icone_docx.png";
		} else if (extensao.equalsIgnoreCase(".xls") || extensao.equalsIgnoreCase(".xlsx")) {
			return "../../resources/img/icone_xls.png";
		} else if (extensao.equalsIgnoreCase(".txt")) {
			return "../../resources/img/icone_txt.png";
		} else if (extensao.equalsIgnoreCase(".cdr")) {
			return "../../resources/img/icone_cdr.png";
		} else if (extensao.equalsIgnoreCase(".eps")) {
			return "../../resources/img/icone_eps.png";
		} else if (extensao.equalsIgnoreCase(".bmp")) {
			return "../../resources/img/icone_bmp.png";
		} else if (extensao.equalsIgnoreCase(".pptx")) {
			return "../../resources/img/icone_pptx.png";
		}
		return "";
	}

	public String novoArquivo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		session.setAttribute("fornecedorCidade", fornecedorCidade);
		RequestContext.getCurrentInstance().openDialog("cadArquivoFornecedor", options, null);
		return "";
	}

	public void retornoDialogNovoArquivo(SelectEvent event) {
		Fornecedorarquivo fornecedorarquivo = (Fornecedorarquivo) event.getObject();
		if (fornecedorarquivo.getIdfornecedorarquivo() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarArquivosFornecedor();
		semConteudo();
	}

	// Verificar se existe arquivos na tela
	public void semConteudo() {
		if (listaFornecedorArquivo == null || listaFornecedorArquivo.isEmpty()) {
			semArquivo = true;
		} else {
			semArquivo = false;

		}
	}

	public boolean excluirArquivoFTP(Fornecedorarquivo fornecedorarquivo) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(FornecedorArquivosMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHost(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(FornecedorArquivosMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = fornecedorarquivo.getNomearquivo();
			msg = ftp.excluirArquivo(nomeArquivoFTP, "/fornecedorcidade/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			return true;
		} catch (IOException ex) {
			Logger.getLogger(FornecedorArquivosMB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(FornecedorArquivosMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String excluirArquivo(Fornecedorarquivo arquivo) {
		excluirArquivoFTP(arquivo);
		FornecedorArquivoFacade fornecedorArquivoFacade = new FornecedorArquivoFacade();
		fornecedorArquivoFacade.excluir(arquivo.getIdfornecedorarquivo());
		gerarArquivosFornecedor();
		return "";
	}

	public boolean renderedExcluir() {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
				|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2
				|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9) {
			return true;
		} else
			return false;
	}

	public String renderedIconeExcluir(Fornecedorarquivo fornecedorarquivo) {
		if (fornecedorarquivo != null) {
			return "fa fa-trash-o";
		} else
			return "";
	}

	public String voltar() {
		return "consEscolasCadastradas";
	}

	
	public boolean habilitarBtnNovo(){
		if(HabilitarBtnNovo==true){
			return false;
		}else return true;
	}
}
