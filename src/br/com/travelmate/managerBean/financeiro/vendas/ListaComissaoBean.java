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
import br.com.travelmate.facade.UnidadeNegocioFacade;

import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ListaComissaoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject 
    private UsuarioLogadoMB usuarioLogadoMB;
    private Unidadenegocio unidadenegocio;
    private List<Unidadenegocio> listaUnidadeNegocio;
    private Date dataInicial;
    private Date dataFinal;
	private List<Vendas> listaVendas;
	private String sql;
	
	@PostConstruct
	public void init(){
		gerarListaUnidadeNegocio();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
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

	public List<Vendas> getListaVenda() {
		return listaVendas;
	}

	public void setListaVenda(List<Vendas> listaVenda) {
		this.listaVendas = listaVenda;
	}
	
	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar();
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
	
	public String filtarVendas(){
        sql = "Select v From Vendas v where (v.situacao='FINALIZADA' or v.situacao='ANDAMENTO')  and v.vendasMatriz='S' ";
        if ((dataInicial!=null) && (dataFinal!=null)){
            sql = sql + " and v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial) +
                    "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        if (unidadenegocio!=null){
            sql = sql + " and v.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        }
        gerarListaVendas();
        return "";
    }
	
	public void gerarListaVendas(){
        
        if (sql==null){
            String sData = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
            sql = "Select v From Vendas v where (v.situacao='FINALIZADA' or v.situacao='ANDAMENTO')  and v.vendasMatriz='S' and v.dataVenda>='" + sData + "'  order by v.dataVenda DESC";
        }
        listaVendas = vendasDao.lista(sql);
        if (listaVendas==null){
            listaVendas = new ArrayList<Vendas>();
        }
    }

}
