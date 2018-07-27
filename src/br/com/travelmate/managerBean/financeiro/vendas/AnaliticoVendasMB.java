package br.com.travelmate.managerBean.financeiro.vendas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AnaliticoVendasMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private VendasDao vendasDao;
	private Unidadenegocio unidadenegocio;
    private Produtos produto;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private List<Produtos> listaProdutos;
    private Date dataInical; 
	private Date dataFinal;
    private String sql;
    private List<Vendas> listaVendas;
    private List<Usuario> listaUsuario;
    private Usuario usuario;
    
    @PostConstruct
    public void inti(){
	    gerarListaUnidadeNegocio();
	    gerarListaProdutos();
	    gerarListaUsuario();
	    usuario = new Usuario();
    }
    
    public List<Vendas> getListaVendas() {
		return listaVendas;
	}

	public void setListaVendas(List<Vendas> listaVendas) {
		this.listaVendas = listaVendas;
	}
	
    public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public Produtos getProduto() {
		return produto;
	}


	public void setProduto(Produtos produto) {
		this.produto = produto;
	}


	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}


	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}


	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}


	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}


	public Date getDataInical() {
		return dataInical;
	}


	public void setDataInical(Date dataInical) {
		this.dataInical = dataInical;
	}


	public Date getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar();
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
    
    private void gerarListaProdutos(){
        ProdutoFacade produtoFacade = new ProdutoFacade();
        listaProdutos = produtoFacade.listarProdutos("");
        if (listaProdutos==null){
            listaProdutos = new ArrayList<Produtos>();
        }
    }
    
    
    public String limparFiltroVendas(){
    	sql = null;
        unidadenegocio = null;
        produto = null;
        dataFinal=null; 
        usuario=null;
        dataInical=null;
        
        listaVendas = vendasDao.lista(sql);
        return "";
    }
    
    public String filtarVendas(){
        sql = "Select v From Vendas v where (v.situacao='FINALIZADA' or v.situacao='ANDAMENTO') ";
        if ((dataInical!=null) && (dataFinal!=null)){
            sql = sql + " and v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInical) +
                    "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        if (unidadenegocio!=null){
            sql = sql + " and v.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        }
        if(usuario!=null && usuario.getIdusuario()!=null){
        	sql = sql + " and v.usuario.idusuario=" + usuario.getIdusuario();
        }
        if (produto!=null){
            sql = sql + " and v.produtos.idprodutos=" + produto.getIdprodutos();
        }
        sql = sql + " and v.vendasMatriz='S' ";
        sql = sql + " order by v.dataVenda DESC";
        
        listaVendas = vendasDao.lista(sql);
        if (listaVendas!=null){
        	for (int i = 0; i < listaVendas.size(); i++) {
        		if (listaVendas.get(i).getFormapagamento()!=null){
        			if (listaVendas.get(i).getFormapagamento().getParcelamentopagamentoList()!=null){
        				for (int j = 0; j < listaVendas.get(i).getFormapagamento().getParcelamentopagamentoList().size(); j++) {
							if (listaVendas.get(i).getFormapagamento().getParcelamentopagamentoList().get(j).getFormaPagamento().equalsIgnoreCase("Financiamento banco")){
								listaVendas.get(i).getFormapagamento().setValorJuros(0.0f);
							}
						}
        			}
        		}
			}
        }
        return "";
    }

    public void gerarListaUsuario(){
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        String sql ="select u from Usuario u where u.situacao='Ativo'";
        if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
        	sql = sql + " and u.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio();
        }
        sql = sql + " order by u.nome";
        listaUsuario = usuarioFacade.listar(sql);
        if (listaUsuario==null){
        	listaUsuario = new ArrayList<Usuario>();
        }
    }
}
