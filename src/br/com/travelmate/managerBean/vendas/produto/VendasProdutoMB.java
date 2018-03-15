package br.com.travelmate.managerBean.vendas.produto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class VendasProdutoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vendascomissao vendascomissao;
	private List<Vendascomissao> listaVendasComissao;
	private Produtos produtos;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private List<Unidadenegocio> listaUnidade;
	private List<Produtos> listaProdutos;
	private Date dataInicio;
	private Date dataFinal;
	private Float totalVendas;
	private Float totalMatriz;
	private Float totalFranquia;
	private int totalRegistros;
	
	
	
	@PostConstruct
	public void init() {
		gerarListaProdutos();
		gerarListaUnidade();
		totalFranquia = 0.0f;
		totalMatriz = 0.0f;
		totalVendas = 0.0f;
		totalRegistros=0;
	}



	public Vendascomissao getVendascomissao() {
		return vendascomissao;
	}



	public void setVendascomissao(Vendascomissao vendascomissao) {
		this.vendascomissao = vendascomissao;
	}



	public List<Vendascomissao> getListaVendasComissao() {
		return listaVendasComissao;
	}



	public void setListaVendasComissao(List<Vendascomissao> listaVendasComissao) {
		this.listaVendasComissao = listaVendasComissao;
	}



	public Produtos getProdutos() {
		return produtos;
	}



	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
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



	public Date getDataInicio() {
		return dataInicio;
	}



	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}



	public Date getDataFinal() {
		return dataFinal;
	}



	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	
	
	
	public Float getTotalVendas() {
		return totalVendas;
	}



	public void setTotalVendas(Float totalVendas) {
		this.totalVendas = totalVendas;
	}



	public Float getTotalMatriz() {
		return totalMatriz;
	}



	public void setTotalMatriz(Float totalMatriz) {
		this.totalMatriz = totalMatriz;
	}



	public Float getTotalFranquia() {
		return totalFranquia;
	}



	public void setTotalFranquia(Float totalFranquia) {
		this.totalFranquia = totalFranquia;
	}



	public int getTotalRegistros() {
		return totalRegistros;
	}



	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}



	public void pesquisar() {
		VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
		String sql = "SELECT v FROM Vendascomissao v WHERE v.vendas.vendasMatriz like '%%' ";
		if (usuario != null && usuario.getIdusuario() !=null) {
			sql = sql + " and v.vendas.usuario.idusuario=" + usuario.getIdusuario();
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() !=null) {
			sql = sql + " and v.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (produtos != null && produtos.getIdprodutos() !=null) {
			sql = sql + " and v.vendas.produtos.idprodutos=" + produtos.getIdprodutos();
		}
		if (dataInicio != null && dataFinal != null) {
			sql = sql + " and v.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and v.vendas.dataVenda<='" +
					Formatacao.ConvercaoDataSql(dataFinal) + "'";
		}
		listaVendasComissao = vendasComissaoFacade.listar(sql);
		if (listaVendasComissao == null) {
			listaVendasComissao = new ArrayList<Vendascomissao>();
		}
		CalcularTotais();
	}
	
	public void CalcularTotais() {
		for (int i=0;i<listaVendasComissao.size();i++) {
			totalVendas = totalVendas + listaVendasComissao.get(i).getVendas().getValor();
			totalMatriz = totalMatriz + retornarValorMatriz(listaVendasComissao.get(i));
			totalFranquia = totalFranquia + listaVendasComissao.get(i).getLiquidofranquia();
		}
		totalRegistros = listaVendasComissao.size();
	}
	
	
	
	public void gerarListaProdutos() {
		listaProdutos = GerarListas.listarProdutos("");
	}
	
	public void gerarListaUnidade() {
		listaUnidade = GerarListas.listarUnidade();
	}
	
	
	public void gerarListaConsultor() {
		if (unidadenegocio != null) {
			listaUsuario = GerarListas.listarUsuarios("SELECT u FROM Usuario u WHERE u.unidadenegocio.idunidadeNegocio=" 
					+ unidadenegocio.getIdunidadeNegocio() + " and u.situacao='Ativo'");
		}
	}
	
	
	public float retornarValorMatriz(Vendascomissao vendascomissao) {
		return vendascomissao.getComissaotm() - vendascomissao.getLiquidofranquia();
	}
	
	
	
	
	 

}
