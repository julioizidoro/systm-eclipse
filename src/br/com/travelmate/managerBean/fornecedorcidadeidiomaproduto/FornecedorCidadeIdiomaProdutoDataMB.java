package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped; 
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoDataFacade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Fornecedorcidadeidiomaprodutodata; 

@Named
@ViewScoped
public class FornecedorCidadeIdiomaProdutoDataMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto;
	private Date datainicio;
	private String sql;
	private Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto; 
	private Fornecedorcidadeidiomaprodutodata fornecedorcidadeidiomaprodutodata;
	private List<Fornecedorcidadeidiomaprodutodata> listaDatas;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		sql = (String) session.getAttribute("sql");
		fornecedorcidadeidiomaproduto = (Fornecedorcidadeidiomaproduto) session.getAttribute("fornecedorcidadeidiomaproduto");
		session.removeAttribute("sql");
		session.removeAttribute("fornecedorcidadeidiomaproduto");
		fornecedorcidadeidiomaprodutodata = new Fornecedorcidadeidiomaprodutodata();
		gerarListaFornecedorCidadeIdiomaProdutoData();
	}

	public List<Fornecedorcidadeidiomaproduto> getListaFornecedorCidadeIdiomaProduto() {
		return listaFornecedorCidadeIdiomaProduto;
	}

	public void setListaFornecedorCidadeIdiomaProduto(
			List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto) {
		this.listaFornecedorCidadeIdiomaProduto = listaFornecedorCidadeIdiomaProduto;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Fornecedorcidadeidiomaproduto getFornecedorcidadeidiomaproduto() {
		return fornecedorcidadeidiomaproduto;
	}

	public void setFornecedorcidadeidiomaproduto(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		this.fornecedorcidadeidiomaproduto = fornecedorcidadeidiomaproduto;
	}

	public Fornecedorcidadeidiomaprodutodata getFornecedorcidadeidiomaprodutodata() {
		return fornecedorcidadeidiomaprodutodata;
	}

	public void setFornecedorcidadeidiomaprodutodata(Fornecedorcidadeidiomaprodutodata fornecedorcidadeidiomaprodutodata) {
		this.fornecedorcidadeidiomaprodutodata = fornecedorcidadeidiomaprodutodata;
	}

	public List<Fornecedorcidadeidiomaprodutodata> getListaDatas() {
		return listaDatas;
	}

	public void setListaDatas(List<Fornecedorcidadeidiomaprodutodata> listaDatas) {
		this.listaDatas = listaDatas;
	}

	public void cadastrarDatasIniciais() {
		fornecedorcidadeidiomaprodutodata.setDatainicio(datainicio);
		fornecedorcidadeidiomaprodutodata.setFornecedorcidadeidiomaproduto(fornecedorcidadeidiomaproduto);
		FornecedorCidadeIdiomaProdutoDataFacade fornecedorCidadeIdiomaProdutoDataFacade = new FornecedorCidadeIdiomaProdutoDataFacade();
		fornecedorCidadeIdiomaProdutoDataFacade.salvar(fornecedorcidadeidiomaprodutodata);
		fornecedorcidadeidiomaprodutodata = new Fornecedorcidadeidiomaprodutodata();
		datainicio=null;
		gerarListaFornecedorCidadeIdiomaProdutoData();
	} 
	
	public void excluir(Fornecedorcidadeidiomaprodutodata fornecedorcidadeidiomaprodutodata) { 
		FornecedorCidadeIdiomaProdutoDataFacade fornecedorCidadeIdiomaProdutoDataFacade = new FornecedorCidadeIdiomaProdutoDataFacade();
		fornecedorCidadeIdiomaProdutoDataFacade.excluir(fornecedorcidadeidiomaprodutodata.getIdfornecedorcidadeidiomaprodutodata()); 
		gerarListaFornecedorCidadeIdiomaProdutoData();
	} 

    public String cancelarCadastro(){
    	FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("sql", sql);
        return "fornecedorCidadeIdiomaProduto";
    }
    
    public void gerarListaFornecedorCidadeIdiomaProdutoData(){
    	String sql = "SELECT f FROM Fornecedorcidadeidiomaprodutodata f WHERE f.fornecedorcidadeidiomaproduto.idfornecedorcidadeidiomaproduto="
    			+fornecedorcidadeidiomaproduto.getIdfornecedorcidadeidiomaproduto()+ " ORDER BY f.datainicio";
    	FornecedorCidadeIdiomaProdutoDataFacade fornecedorCidadeIdiomaProdutoDataFacade = new FornecedorCidadeIdiomaProdutoDataFacade();
    	listaDatas = fornecedorCidadeIdiomaProdutoDataFacade.listar(sql);
    	if(listaDatas==null){
    		listaDatas = new ArrayList<>();
    	}
    }
}
