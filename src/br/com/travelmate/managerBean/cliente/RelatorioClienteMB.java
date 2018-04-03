package br.com.travelmate.managerBean.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class RelatorioClienteMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private Date dataInicial;
	private Date dataFinal;
	private String nomeCliente;
	private List<Cliente> listaCliente;
	private boolean habilitarUnidade = false;
	
	
	
	@PostConstruct
	public void init() {
		gerarListaUnidadeNegocio();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarUnidade = true;
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
		}
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



	public String getNomeCliente() {
		return nomeCliente;
	}



	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	
	
	
	
	public List<Cliente> getListaCliente() {
		return listaCliente;
	}



	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}



	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}



	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}



	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}
	
	
	public void gerarListaCliente() {
		String sql = "SELECT c FROM Cliente c WHERE c.nome like '%%' ";
		
		if ((dataInicial != null) && (dataFinal != null)) {
			sql = sql + " and c.dataCadastro>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.dataCadastro<='"
					+ Formatacao.ConvercaoDataSql(dataFinal) + "'";
		}
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			sql = sql + " and c.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		
		
		ClienteFacade clienteFacade = new ClienteFacade();
		listaCliente = clienteFacade.listar(sql);
		if (listaCliente == null) {
			listaCliente = new ArrayList<Cliente>();
		}
	}
	
	
	
	public void limpar() {
		listaCliente = new ArrayList<Cliente>();
		unidadenegocio = null;
		dataFinal = null;
		dataInicial = null;
	}
	
	
	

}
