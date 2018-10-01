package br.com.travelmate.managerBean.worksponsor;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.List;

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
import br.com.travelmate.facade.TipoArquivoProdutoFacade; 
import br.com.travelmate.facade.WorkSponsorArquivoFacade; 
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Tipoarquivoproduto; 
import br.com.travelmate.model.Worksponsor;
import br.com.travelmate.model.Worksponsorarquivos; 
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;

@Named
@ViewScoped
public class CadWorkSponsorArquivoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private MateRunnersMB metaRunnersMB;
	private Worksponsorarquivos worksponsorarquivos;
	private Tipoarquivoproduto tipoarquivo;
	private List<Tipoarquivoproduto> listaTipoArquivo;
	private Worksponsor worksponsor; 
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private List<Worksponsorarquivos> listaArquivos;
	private boolean arquivoEnviado = false;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		worksponsor = (Worksponsor) session.getAttribute("worksponsor");
		listaArquivos = (List<Worksponsorarquivos>) session.getAttribute("listaArquivos"); 
		session.removeAttribute("worksponsor");
		session.removeAttribute("listaArquivos");
		gerarListaTipoArquivo();
		if (worksponsorarquivos == null) {
			worksponsorarquivos = new Worksponsorarquivos();
			listaNomeArquivo = new ArrayList<String>();
		}
		if (listaArquivos == null) {
			listaArquivos = new ArrayList<Worksponsorarquivos>();
		}

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

	public Tipoarquivoproduto getTipoarquivo() {
		return tipoarquivo;
	}

	public void setTipoarquivo(Tipoarquivoproduto tipoarquivo) {
		this.tipoarquivo = tipoarquivo;
	}

	public List<Tipoarquivoproduto> getListaTipoArquivo() {
		return listaTipoArquivo;
	}

	public void setListaTipoArquivo(List<Tipoarquivoproduto> listaTipoArquivo) {
		this.listaTipoArquivo = listaTipoArquivo;
	}
 
	public List<String> getListaNomeArquivo() {
		return listaNomeArquivo;
	}

	public void setListaNomeArquivo(List<String> listaNomeArquivo) {
		this.listaNomeArquivo = listaNomeArquivo;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
 
	public List<UploadedFile> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<UploadedFile> listaFile) {
		this.listaFile = listaFile;
	}

	public MateRunnersMB getMetaRunnersMB() {
		return metaRunnersMB;
	}

	public void setMetaRunnersMB(MateRunnersMB metaRunnersMB) {
		this.metaRunnersMB = metaRunnersMB;
	}

	public Worksponsorarquivos getWorksponsorarquivos() {
		return worksponsorarquivos;
	}

	public void setWorksponsorarquivos(Worksponsorarquivos worksponsorarquivos) {
		this.worksponsorarquivos = worksponsorarquivos;
	}

	public Worksponsor getWorksponsor() {
		return worksponsor;
	}

	public void setWorksponsor(Worksponsor worksponsor) {
		this.worksponsor = worksponsor;
	}

	public List<Worksponsorarquivos> getListaArquivos() {
		return listaArquivos;
	}

	public void setListaArquivos(List<Worksponsorarquivos> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}

	public void gerarListaTipoArquivo() {
		TipoArquivoProdutoFacade tipoArquivoFacade = new TipoArquivoProdutoFacade();
		try {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				listaTipoArquivo = tipoArquivoFacade.listar("Select t from Tipoarquivoproduto t"
						+ " where t.produtos.idprodutos="+aplicacaoMB.getParametrosprodutos().getWork());
			} else
				listaTipoArquivo = tipoArquivoFacade
						.listar("Select t from Tipoarquivoproduto t where t.tipoarquivo.unidade='Sim'"
								+ " and t.produtos.idprodutos="+aplicacaoMB.getParametrosprodutos().getWork()); 
			if (listaTipoArquivo == null) {
				listaTipoArquivo = new ArrayList<Tipoarquivoproduto>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String salvar() {
		String nome = ""; 
		String obs = ""; 
		if (worksponsorarquivos != null) {
			if (worksponsorarquivos.getNome() != null) {
				try {
					nome = new String(worksponsorarquivos.getNome().getBytes(Charset.defaultCharset()), "UTF-8");
					obs = new String(worksponsorarquivos.getDescricao().getBytes(Charset.defaultCharset()), "UTF-8");
				} catch (UnsupportedEncodingException e) { 
					e.printStackTrace();
				}
			}
		}
		if (validacaoDados()) {
			for (int i = 0; i < listaNomeArquivo.size(); i++) {
				worksponsorarquivos = new Worksponsorarquivos(); 
				WorkSponsorArquivoFacade workSponsorArquivoFacade = new WorkSponsorArquivoFacade();
				worksponsorarquivos.setTipoarquivo(tipoarquivo.getTipoarquivo()); 
				worksponsorarquivos.setWorksponsor(worksponsor);
				worksponsorarquivos.setNomeftp(worksponsor.getIdworksponsor() + "_" + listaNomeArquivo.get(i));
				worksponsorarquivos.setNome(nome);
				worksponsorarquivos.setDescricao(obs);
				worksponsorarquivos = workSponsorArquivoFacade.salvar(worksponsorarquivos);
				listaArquivos.add(worksponsorarquivos); 
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("listaArquivos", listaArquivos);
			RequestContext.getCurrentInstance().closeDialog(worksponsorarquivos);
		} else {
			TipoArquivoProdutoFacade tipoArquivoProdutoFacade = new TipoArquivoProdutoFacade();
			tipoarquivo = new Tipoarquivoproduto();
			try {
				tipoarquivo = tipoArquivoProdutoFacade.consultar(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public String cancelar() {
		// para preencher a combobox tipo arquivo para não de erro de null
		TipoArquivoProdutoFacade tipoArquivoProdutoFacade = new TipoArquivoProdutoFacade();
		tipoarquivo = new Tipoarquivoproduto();
		try {
			tipoarquivo = tipoArquivoProdutoFacade.consultar(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(new WorkSponsorArquivoMB());
		return "";
	} 

	// Salvar nome do arquivo para tabela arquivos
	public String nomeArquivoSalvo() {
		nomeArquivoFTP = worksponsor.getIdworksponsor()+"";
		return nomeArquivoFTP;
	}

	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile();
		salvarArquivoFTP();
		if (arquivoEnviado) {
			String nome = e.getFile().getFileName();
			try {
				nome = new String(nome.getBytes(Charset.defaultCharset()), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			if (listaNomeArquivo == null) {
				listaNomeArquivo = new ArrayList<String>();
			}
			listaNomeArquivo.add(nome);
		}
	}
	
	public boolean salvarArquivoFTP() {
		String msg = "";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String nomeArquivoFile = worksponsor.getIdworksponsor()+"_" + file.getFileName();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
		UploadAWSS3 s3 = new UploadAWSS3("local", caminho);
		File arquivoFile = s3.getFile(file, nomeArquivoFile);
		if (s3.uploadFile(arquivoFile, "worksponsor")) {
			msg = "Arquivo: " + nomeArquivoFile + " enviado com sucesso";
			arquivoEnviado = true;
		} else {
			msg = " Erro no nome do arquivo";
			arquivoEnviado = false;
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
	
	public boolean validacaoDados() {
		if (tipoarquivo == null || tipoarquivo.getTipoarquivo() == null) {
			Mensagem.lancarMensagemInfo("Tipo de arquivo não foi selecionado", "");
			return false;
		}
		if (listaNomeArquivo == null || listaNomeArquivo.isEmpty()) {
			Mensagem.lancarMensagemInfo("Você esta tentando confirmar sem um upload de arquivo", "");
			return false;
		}
		return true;
	}
 
}
