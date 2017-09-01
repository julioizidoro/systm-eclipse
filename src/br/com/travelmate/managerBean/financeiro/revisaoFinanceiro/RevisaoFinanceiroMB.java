package br.com.travelmate.managerBean.financeiro.revisaoFinanceiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;


@Named
@ViewScoped
public class RevisaoFinanceiroMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Vendas> listaVendaNova;
	private List<Vendas> listaVendaPendente;
	private int nPendentes = 0;
	private int nVendaNova = 0;
	private Date dataInicial;
	private Date dataFinal;
	private List<Produtos> listaProdutos;
	private Produtos produtos;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.getAttribute("listaVendaNova");
		session.getAttribute("listaVendaPendente");
		session.removeAttribute("listaVendaNova");
		session.removeAttribute("listaVendaPendente");
		if (listaVendaNova != null && listaVendaPendente != null) {
			nPendentes = listaVendaPendente.size();
			nVendaNova = listaVendaNova.size();
		}else{
			gerarListaVendas();
		}
		listaProdutos = GerarListas.listarProdutos("");
		listaUnidadeNegocio = GerarListas.listarUnidade();
	}
	
	




	public List<Vendas> getListaVendaNova() {
		return listaVendaNova;
	}






	public void setListaVendaNova(List<Vendas> listaVendaNova) {
		this.listaVendaNova = listaVendaNova;
	}






	public List<Vendas> getListaVendaPendente() {
		return listaVendaPendente;
	}






	public void setListaVendaPendente(List<Vendas> listaVendaPendente) {
		this.listaVendaPendente = listaVendaPendente;
	}






	public int getnPendentes() {
		return nPendentes;
	}






	public void setnPendentes(int nPendentes) {
		this.nPendentes = nPendentes;
	}






	public int getnVendaNova() {
		return nVendaNova;
	}






	public void setnVendaNova(int nVendaNova) {
		this.nVendaNova = nVendaNova;
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






	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}






	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}






	public Produtos getProdutos() {
		return produtos;
	}






	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}






	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}






	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}






	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}






	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}






	public String cadastroRevisaoFinanceiro(Vendas venda) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		session.setAttribute("listaVendaNova", listaVendaNova);
		session.setAttribute("listaVendaPendente", listaVendaPendente);
		return "cadRevisaoFinanceiro";
	}
	
	
	public void gerarListaVendas(){
		VendasFacade vendasFacade = new VendasFacade();
		List<Vendas> listaVendas = null;
		String data = Formatacao.ConvercaoDataPadrao(new Date());
        String mesString = data.substring(3, 5);
        String anoString = data.substring(6, 10);
        int mesInicio = Integer.parseInt(mesString);
        int anoInicio = Integer.parseInt(anoString);
        int mescInicio;
        int mescFinal;
        int anocInicio = 0;
        int anocFinal = 0;
        if (mesInicio == 1) {
            mescInicio = 12;
            anocInicio = anoInicio - 1;
        } else {
            mescInicio = mesInicio - 1;
            anocInicio = anoInicio;
        }
        if (mesInicio == 12) {
            mescFinal = 1;
            anocFinal = anoInicio + 1;
        } else {
            mescFinal = mesInicio + 1;
            anocFinal = anoInicio;
        }
        String dataInicial = anocInicio + "-" + Formatacao.retornaDataInicia(mescInicio);
        String dataTermino = anocFinal + "-" + Formatacao.retornaDataFinal(mescFinal);
		listaVendaNova = vendasFacade.lista("SELECT v FROM Vendas v WHERE v.dataVenda>='"+ dataInicial +"' and v.dataVenda<='"+ dataTermino +"' and v.situacaofinanceiro='N'"+
				" and v.situacaogerencia<>'P'");
		if (listaVendaNova == null) {
			listaVendaNova = new ArrayList<>();
		}
		listaVendaPendente = vendasFacade.lista("SELECT v FROM Vendas v WHERE v.dataVenda>='"+ dataInicial +"' and v.dataVenda<='"+ dataTermino +"' and v.situacaofinanceiro='P'"+
				" and v.situacaogerencia<>'P'");
		if (listaVendaPendente == null) {
			listaVendaPendente = new ArrayList<>();
		}
		nPendentes = listaVendaPendente.size();
		nVendaNova = listaVendaNova.size();
	}
	  
	
	public void pesquisar(){
		VendasFacade vendasFacade = new VendasFacade();
		String sql = "SELECT v FROM Vendas v WHERE v.cliente.nome like '%%' ";
		
		if (unidadenegocio != null) {
			sql = sql + " and v.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		
		if (produtos != null) {
			sql = sql + " and v.produtos.idprodutos=" + produtos.getIdprodutos();
		}
		if (dataInicial != null && dataFinal != null) {
			sql = sql + " and v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		}
		listaVendaNova = vendasFacade.lista(sql +  " and v.situacaofinanceiro='N'"+
				" and v.situacaogerencia<>'P' order by v.dataVenda DESC");
		if (listaVendaNova == null) {
			listaVendaNova = new ArrayList<>();
		}
		listaVendaPendente = vendasFacade.lista(sql + " and v.situacaofinanceiro='P'"+
				" and v.situacaogerencia<>'P' order by v.dataVenda DESC");
		if (listaVendaPendente == null) {
			listaVendaPendente = new ArrayList<>();
		}
		nPendentes = listaVendaPendente.size();
		nVendaNova = listaVendaNova.size();
	}
	

}
