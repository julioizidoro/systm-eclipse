/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.coprodutos;

import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class EditarValoresCoProdutosMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
    private Date dataInicialAntiga;
    private Date dataFinalAntiga; 
    private Date dataInicialNova;
    private Date dataFinalNova; 
    private String tipodata;
    private String tipodataNovo;
    private String produto;
    private Fornecedorcidadeidioma fornecedorcidadeidioma;
    
    @PostConstruct
    public void init(){ 
    	 FacesContext fc = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
         fornecedorcidadeidioma = (Fornecedorcidadeidioma) session.getAttribute("fornecedorcidadeidioma");
    }

    public Date getDataInicialAntiga() {
		return dataInicialAntiga;
	}

	public void setDataInicialAntiga(Date dataInicialAntiga) {
		this.dataInicialAntiga = dataInicialAntiga;
	}

	public Date getDataFinalAntiga() {
		return dataFinalAntiga;
	}

	public void setDataFinalAntiga(Date dataFinalAntiga) {
		this.dataFinalAntiga = dataFinalAntiga;
	}

	public Date getDataInicialNova() {
		return dataInicialNova;
	}

	public void setDataInicialNova(Date dataInicialNova) {
		this.dataInicialNova = dataInicialNova;
	}

	public Date getDataFinalNova() {
		return dataFinalNova;
	}

	public void setDataFinalNova(Date dataFinalNova) {
		this.dataFinalNova = dataFinalNova;
	}

	public String getTipodata() {
		return tipodata;
	}

	public void setTipodata(String tipodata) {
		this.tipodata = tipodata;
	}

	public String getTipodataNovo() {
		return tipodataNovo;
	}

	public void setTipodataNovo(String tipodataNovo) {
		this.tipodataNovo = tipodataNovo;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}

	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}

	public String salvar() {
		if(fornecedorcidadeidioma!=null && fornecedorcidadeidioma.getIdfornecedorcidadeidioma()!=null) {
			 String sql = "Select v from Valorcoprodutos v where v.coprodutos.fornecedorcidadeidioma."
			 		 + "fornecedorcidade.fornecedor.idfornecedor="
					 + fornecedorcidadeidioma.getFornecedorcidade().getFornecedor().getIdfornecedor()
		             + " and v.produtosuplemento='valor' and v.datafinal='"+Formatacao.ConvercaoDataSql(dataFinalAntiga)
		             +"'  and v.datainicial='"+Formatacao.ConvercaoDataSql(dataInicialAntiga)+"'"
		             + " and v.tipodata='"+tipodata+"'";
			 if(produto!=null && !produto.equalsIgnoreCase("sn")) {
				 sql = sql + " and v.coprodutos.tipo='"+produto+"'";
			 }
		     ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		     List<Valorcoprodutos> listaValores = valorCoProdutosFacade.listar(sql);
		     if(listaValores!=null && listaValores.size()>0) {
		    	 for (int i = 0; i < listaValores.size(); i++) {
		    		 listaValores.get(i).setDatainicial(dataInicialNova);
		    		 listaValores.get(i).setDatafinal(dataFinalNova);
		    		 listaValores.get(i).setTipodata(tipodataNovo);
		    		 valorCoProdutosFacade.salvar(listaValores.get(i));
				 }
		     }
		}
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
    
    public String cancelar() {
    	RequestContext.getCurrentInstance().closeDialog(null);
		return "";
    }
    
}
