package br.com.travelmate.managerBean.financeiro.revisaoFinanceiro;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.ArquivosFacade;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Vendas;

@Named
@ViewScoped
public class DocumentosRFMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Arquivos> listaArquivos;
	private Vendas venda;
	private List<Vendas> listaVendaPendente;
	private List<Vendas> listaVendaNova;
	private String urlArquivo = "";
	
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		venda = (Vendas) session.getAttribute("venda");
		session.removeAttribute("venda");
		gerarListaDocumentos();
	
	}




	public List<Arquivos> getListaArquivos() {
		return listaArquivos;
	}




	public void setListaArquivos(List<Arquivos> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}




	public Vendas getVenda() {
		return venda;
	}




	public void setVenda(Vendas venda) {
		this.venda = venda;
	}




	public List<Vendas> getListaVendaPendente() {
		return listaVendaPendente;
	}




	public void setListaVendaPendente(List<Vendas> listaVendaPendente) {
		this.listaVendaPendente = listaVendaPendente;
	}




	public List<Vendas> getListaVendaNova() {
		return listaVendaNova;
	}




	public void setListaVendaNova(List<Vendas> listaVendaNova) {
		this.listaVendaNova = listaVendaNova;
	}
	

	public String getUrlArquivo() {
		return urlArquivo;
	}




	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}




	public void gerarListaDocumentos(){
		ArquivosFacade arquivosFacade = new ArquivosFacade();
		try {
			listaArquivos = arquivosFacade.listar("SELECT a FROM Arquivos a WHERE a.vendas.idvendas=" + venda.getIdvendas() + " and a.tipoarquivo.pertencefinanceiro=true");
			if (listaArquivos == null) {
				listaArquivos = new ArrayList<>();
			}
		} catch (SQLException e) {
			
			  
		}
	}

}
