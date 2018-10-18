package br.com.travelmate.managerBean.curso.controle;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.FornecedorApplicationFacade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorapplication;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;

@Named
@ViewScoped
public class UploadApplicationMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fornecedorapplication fornecedorapplication;
	private Fornecedor fornecedor;
	private Pais pais;
	private Produtosorcamento produtosorcamento;
	private List<Produtosorcamento> listaProdutosSelecionados;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private boolean upload = false;
	
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fornecedor = (Fornecedor) session.getAttribute("fornecedor");
		pais = (Pais) session.getAttribute("pais");
		listaProdutosSelecionados = (List<Produtosorcamento>) session.getAttribute("listaProdutosSelecionados");
		session.removeAttribute("fornecedor");
		session.removeAttribute("pais");
		session.removeAttribute("listaProdutosSelecionados");
		fornecedorapplication  = new Fornecedorapplication();
	}


	public Fornecedorapplication getFornecedorapplication() {
		return fornecedorapplication;
	}


	public void setFornecedorapplication(Fornecedorapplication fornecedorapplication) {
		this.fornecedorapplication = fornecedorapplication;
	}


	public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}


	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
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


	public boolean isUpload() {
		return upload;
	}


	public void setUpload(boolean upload) {
		this.upload = upload;
	}


	public List<Produtosorcamento> getListaProdutosSelecionados() {
		return listaProdutosSelecionados;
	}


	public void setListaProdutosSelecionados(List<Produtosorcamento> listaProdutosSelecionados) {
		this.listaProdutosSelecionados = listaProdutosSelecionados;
	}


	public void salvar() {
		if (validarDados()) {
			for (int i = 0; i < listaProdutosSelecionados.size(); i++) {
				FornecedorApplicationFacade fornecedorApplicationFacade = new FornecedorApplicationFacade();
				fornecedorapplication = fornecedorApplicationFacade.consultar("SELECT f FROM Fornecedorapplication f WHERE f.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor() 
						+ " AND f.produtosorcamento.idprodutosOrcamento=" + listaProdutosSelecionados.get(i).getIdprodutosOrcamento() + " AND f.pais.idpais=" + pais.getIdpais());
				if (fornecedorapplication == null) {
					fornecedorapplication = new Fornecedorapplication();
				}
				if (upload) {
					fornecedorapplication.setNomearquivo(nomeArquivoFTP);
				}else {

					fornecedorapplication.setNomearquivo("");
				}
				fornecedorapplication.setFornecedor(fornecedor);
				fornecedorapplication.setPais(pais);
				fornecedorapplication.setProdutosorcamento(listaProdutosSelecionados.get(i));
				fornecedorapplication = fornecedorApplicationFacade.salvar(fornecedorapplication);
			}
			RequestContext.getCurrentInstance().closeDialog(fornecedorapplication);
		}
	}
	
	public boolean validarDados() {
		if (fornecedor == null || fornecedor.getIdfornecedor() == null) {
			Mensagem.lancarMensagemInfo("Fornecedor não informado", "");
			return false;
		}
		
		if (pais == null || pais.getIdpais() == null) {
			Mensagem.lancarMensagemInfo("Pais não informado", "");
			return false;
			
		}
		
		if (listaProdutosSelecionados == null || listaProdutosSelecionados.size() == 0) {
			Mensagem.lancarMensagemInfo("Programa não informado", "");
			return false;
		}
		return true;
	}
	
	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog(new Fornecedorapplication());
	}
	
	
	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile();
		salvarArquivoFTP();
		upload = true;
	}

	public boolean salvarArquivoFTP() {
		String msg = "";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String nomeArquivo = nomeArquivoSalvo();
			nomeArquivo = nomeArquivo + "_" + new String(file.getFileName());
		nomeArquivoFTP = nomeArquivo;
		String arquivo = servletContext.getRealPath("/arquivos/");
		String nomeArquivoFile = arquivo + nomeArquivo;
		String caminho = servletContext.getRealPath("/resources/aws.properties");
		UploadAWSS3 s3 = new UploadAWSS3("local", caminho);
		File arquivoFile = s3.getFile(file, nomeArquivoFile);
		if (s3.uploadFile(arquivoFile, "application")) {
			msg = "Arquivo: " + nomeArquivoFTP + " enviado com sucesso";
			upload = true;
		} else {
			msg = " Erro no nome do arquivo";
			upload = false;
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(msg, ""));
		arquivoFile.delete();
		return true;
	}
	
	
	public String nomeArquivoSalvo() {
		nomeArquivoFTP = pais.getIdpais() + "_" + fornecedor.getIdfornecedor() + "_";
		return nomeArquivoFTP;
	}
	
	

}
