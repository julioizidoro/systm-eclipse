/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.vendas;

import br.com.travelmate.bean.comissao.CalcularComissao;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class VendasComissaoMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vendascomissao vendascomissao;
    private List<Vendascomissao> listaVendasComissao;
    private String tituloDialog;
    private Date dataInicial;
    private Date dataFinal;
    private CalcularComissao calcularComissao;
    private Produtos produtos;
    private String paga;
    private Unidadenegocio unidadenegocio;
    private Usuario usuario;
    private List<Usuario> listaUsuario;
    private List<Unidadenegocio> listaUnidade;
    private List<Produtos> listaProdutos;

    public VendasComissaoMB() {
    }
    
    @PostConstruct
    public void init(){
        this.vendascomissao = new Vendascomissao();
        String sql = "Select v from Vendascomissao v order by v.vendas.dataVenda";
        gerarListaVendaComissao(sql);
        gerarListaProdutos();
        gerarListaUnidadeNegocio();
        gerarListaUsuario();
    }
    
    public List<Vendascomissao> getListaVendasComissao() {
        return listaVendasComissao;
    }

    public void setListaVendasComissao(List<Vendascomissao> listaVendasComissao) {
        this.listaVendasComissao = listaVendasComissao;
    }
    
    public Vendascomissao getVendascomissao() {
        return vendascomissao;
    }

    public void setVendascomissao(Vendascomissao vendascomissao) {
        this.vendascomissao = vendascomissao;
    }

    public String getTituloDialog() {
        tituloDialog();
        return tituloDialog;
    }

    public void setTituloDialog(String tituloDialog) {
        this.tituloDialog = tituloDialog;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public CalcularComissao getCalcularComissao() {
        return calcularComissao;
    }

    public void setCalcularComissao(CalcularComissao calcularComissao) {
        this.calcularComissao = calcularComissao;
    }
    
    public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}
	
	public String getPaga() {
		return paga;
	}

	public void setPaga(String paga) {
		this.paga = paga;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	
	
	public void gerarListaVendaComissao(String sql){
        VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
        listaVendasComissao = vendasComissaoFacade.listar(sql);
        vendascomissao = listaVendasComissao.get(0);
        if (listaVendasComissao==null){
            listaVendasComissao = new ArrayList<Vendascomissao>();
        }
    }
    public String editar(){
        return "editarComissaovendas";
    }
    public String visualizar(Vendascomissao vendascomissao){
        this.vendascomissao = vendascomissao;
        return null;
    }
    
    public String tituloDialog(){
        tituloDialog = "Venda No. " + vendascomissao.getVendas().getIdvendas();
        return tituloDialog;
    }
    
    public String calculoComissao(){
        calcularComissao = new CalcularComissao(Formatacao.ConvercaoDataSql(dataInicial),
            Formatacao.ConvercaoDataSql(dataFinal));
        return "";
    }
    
    public void Pesquisar(){
    	String usouAnd = "";
        boolean executarFiltro = false;
    	String sql = "Select v from Vendascomissao v where";
    	if(dataInicial!=null && dataInicial!=null){
    		sql = sql + " v.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial) +
                    "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
    		usouAnd = " and";
            executarFiltro = true;
    	}
    	if (unidadenegocio!=null){
            sql = sql + usouAnd + " v.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
            usouAnd = " and";
            executarFiltro = true;
        }	
    	if (produtos!=null){
            sql = sql + usouAnd + " v.vendas.produtos.idprodutos=" + produtos.getIdprodutos();
            usouAnd = " and";
            executarFiltro = true;
        }
    	if (usuario!=null){
            sql = sql + usouAnd + " v.vendas.usuario.idusuario=" + usuario.getIdusuario();
            usouAnd = " and";
            executarFiltro = true;
        }
    	if (paga.equalsIgnoreCase("Sim")){
            sql = sql + usouAnd + " v.paga='Sim'";
            usouAnd = " and";
            executarFiltro = true;
        }
    	if (paga.equalsIgnoreCase("Nao")){
            sql = sql + usouAnd + " v.paga='Nao'";
            executarFiltro = true;
        }
    	sql = sql + " order by v.vendas.dataVenda, v.vendas.idvendas";	
    	VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
        listaVendasComissao = vendasComissaoFacade.listar(sql);
        vendascomissao = listaVendasComissao.get(0);
        if (listaVendasComissao==null){
            listaVendasComissao = new ArrayList<Vendascomissao>();
        }
    }
    
    private void gerarListaProdutos(){
        ProdutoFacade produtoFacade = new ProdutoFacade();
        listaProdutos = produtoFacade.listarProdutos("");
        if (listaProdutos==null){
            listaProdutos = new ArrayList<Produtos>();
        }
    }
    
    private void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidade = unidadeNegocioFacade.listar();
        if (listaUnidade==null){
            listaUnidade = new ArrayList<Unidadenegocio>();
        }
    }
    
    private void gerarListaUsuario(){
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        listaUsuario = usuarioFacade.listar("select u from Usuario u order by u.nome");
        if (listaUsuario==null){
        	listaUsuario = new ArrayList<Usuario>();
        }
    }
    
    
}
