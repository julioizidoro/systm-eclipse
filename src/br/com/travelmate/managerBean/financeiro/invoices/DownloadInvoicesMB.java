package br.com.travelmate.managerBean.financeiro.invoices;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Arquivos;


@Named
@ViewScoped
public class DownloadInvoicesMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Arquivos> listarArquivos;
	
	private String urlArquivo = "";
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listarArquivos = (List<Arquivos>) session.getAttribute("listarArquivos");
		if (listarArquivos != null) {
			urlArquivo = "https://arquivos.systm.com.br";
		}
	}

	public List<Arquivos> getListarArquivos() {
		return listarArquivos;
	}


	public void setListarArquivos(List<Arquivos> listarArquivos) {
		this.listarArquivos = listarArquivos;
	}

	

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}
}
