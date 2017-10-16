/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.coprodutos;

import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.model.Coprodutos;
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
public class ValorCoProdutosMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Coprodutos coprodutos;
    private List<Valorcoprodutos> listaValores;
    private Date datainicial;
    private Date datafinal;
    private int nsemanainicial;
    private int nsemanafinal;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        coprodutos = (Coprodutos) session.getAttribute("coprodutos");
        if (coprodutos!=null){
            gerarListaValores();
        }else{
            listaValores = new ArrayList<Valorcoprodutos>();
        }
    }

    public Coprodutos getCoprodutos() {
        return coprodutos;
    }

    public void setCoprodutos(Coprodutos coprodutos) {
        this.coprodutos = coprodutos;
    }
    
    public List<Valorcoprodutos> getListaValores() {
        return listaValores;
    }

    public void setListaValores(List<Valorcoprodutos> listaValores) {
        this.listaValores = listaValores;
    }
    
    public Date getDatainicial() {
		return datainicial;
	}

	public void setDatainicial(Date datainicial) {
		this.datainicial = datainicial;
	}

	public Date getDatafinal() {
		return datafinal;
	}

	public void setDatafinal(Date datafinal) {
		this.datafinal = datafinal;
	}

	public int getNsemanainicial() {
		return nsemanainicial;
	}

	public void setNsemanainicial(int nsemanainicial) {
		this.nsemanainicial = nsemanainicial;
	}

	public int getNsemanafinal() {
		return nsemanafinal;
	}

	public void setNsemanafinal(int nsemanafinal) {
		this.nsemanafinal = nsemanafinal;
	}

	public void gerarListaValores(){
        String sql = "Select v from Valorcoprodutos v where v.coprodutos.idcoprodutos=" + coprodutos.getIdcoprodutos() 
                + " and v.produtosuplemento='valor' and v.datafinal>='"+Formatacao.ConvercaoDataSql(new Date())
                +"' order by v.datainicial" ;
        ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
        listaValores = valorCoProdutosFacade.listar(sql);
        if (listaValores==null){
            listaValores = new ArrayList<Valorcoprodutos>();
        }
    }
    
    public String cadValoresProdutos(){
        RequestContext.getCurrentInstance().openDialog("cadValorCoProdutos");
        return "";
    }
    
    public String voltar(){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("fornecedor", coprodutos.getFornecedorcidadeidioma());
        return "consProdutos";
    }
    
    public String editar(Valorcoprodutos valorcoprodutos){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("valorcoprodutos", valorcoprodutos);
        RequestContext.getCurrentInstance().openDialog("cadValorCoProdutos");
        return "";
    }
    
    
    public void retornoDialogoNovo(SelectEvent event){
        Valorcoprodutos valorcoprodutos = (Valorcoprodutos) event.getObject();
        listaValores.add(valorcoprodutos);
    }
    
    public void retornoDialogoEditar(){
       gerarListaValores();
        
    } 
    
    public void pesquisar(){
        String sql = "Select v from Valorcoprodutos v where v.coprodutos.idcoprodutos=" + coprodutos.getIdcoprodutos() 
                + " and v.produtosuplemento='valor'";
        if(datainicial!=null && datafinal!=null){
        	sql = sql + " and v.datainicial>='"+Formatacao.ConvercaoDataSql(datainicial)+"'"
        			+ " and v.datafinal<='"+Formatacao.ConvercaoDataSql(datafinal)+"'";
        }
        if(nsemanainicial>0 && nsemanafinal>0){
        	sql = sql + " and v.numerosemanainicial>="+nsemanainicial+
        			" and v.numerosemanafinal<="+nsemanafinal;  
        }
        sql = sql +" order by v.datainicial" ;
        ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
        listaValores = valorCoProdutosFacade.listar(sql);
        if (listaValores==null){
            listaValores = new ArrayList<Valorcoprodutos>();
        }
    }
    
    public void limpar(){
    	datainicial=null;
    	datafinal=null;
    	nsemanainicial=0;
    	nsemanafinal=0;
    	gerarListaValores();
    }
    
    public void excluir(Valorcoprodutos valorcoprodutos) {
    	ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
    	valorCoProdutosFacade.excluir(valorcoprodutos.getIdvalorcoprodutos());
    	Mensagem.lancarMensagemInfo("Valor excluído com sucesso!", "");
    	gerarListaValores();
    }
    
}
