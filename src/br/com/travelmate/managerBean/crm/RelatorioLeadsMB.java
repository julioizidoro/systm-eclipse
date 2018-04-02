package br.com.travelmate.managerBean.crm;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.PublicidadeFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Publicidade;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class RelatorioLeadsMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Date dataUltContatoInicial;
	private Date dataUltContatoFinal;
	private Date dataProxContatoInicial;
	private Date dataProxContatoFinal;
	private List<Usuario> listaConsultor;
	private Usuario consultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String status;
	private Date datarecebimentoInicial;
	private Date datarecebimentoFinal;
	private boolean desabilitarUnidade = false;
	private String nomeUnidade = "Todos";
	private List<Publicidade> listaPublicidades;
	private Publicidade publicidade;
	private String nomeCliente = "";
	private List<Lead> listaLeads;
	private boolean habilitarGroupBy = false;
	
	
	@PostConstruct
	public void init(){
		gerarListaUnidadeNegocio();
		gerarListaPublicidade();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			desabilitarUnidade = true;
			gerarListaConsultor();
		}
	}


	public Date getDataUltContatoInicial() {
		return dataUltContatoInicial;
	}


	public void setDataUltContatoInicial(Date dataUltContatoInicial) {
		this.dataUltContatoInicial = dataUltContatoInicial;
	}


	public Date getDataUltContatoFinal() {
		return dataUltContatoFinal;
	}


	public void setDataUltContatoFinal(Date dataUltContatoFinal) {
		this.dataUltContatoFinal = dataUltContatoFinal;
	}


	public Date getDataProxContatoInicial() {
		return dataProxContatoInicial;
	}


	public void setDataProxContatoInicial(Date dataProxContatoInicial) {
		this.dataProxContatoInicial = dataProxContatoInicial;
	}


	public Date getDataProxContatoFinal() {
		return dataProxContatoFinal;
	}


	public void setDataProxContatoFinal(Date dataProxContatoFinal) {
		this.dataProxContatoFinal = dataProxContatoFinal;
	}


	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}


	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}


	public Usuario getConsultor() {
		return consultor;
	}


	public void setConsultor(Usuario consultor) {
		this.consultor = consultor;
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
	
	
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getDatarecebimentoInicial() {
		return datarecebimentoInicial;
	}


	public void setDatarecebimentoInicial(Date datarecebimentoInicial) {
		this.datarecebimentoInicial = datarecebimentoInicial;
	}


	public Date getDatarecebimentoFinal() {
		return datarecebimentoFinal;
	}


	public void setDatarecebimentoFinal(Date datarecebimentoFinal) {
		this.datarecebimentoFinal = datarecebimentoFinal;
	}


	public boolean isDesabilitarUnidade() {
		return desabilitarUnidade;
	}


	public void setDesabilitarUnidade(boolean desabilitarUnidade) {
		this.desabilitarUnidade = desabilitarUnidade;
	}


	public String getNomeUnidade() {
		return nomeUnidade;
	}


	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}


	public Publicidade getPublicidade() {
		return publicidade;
	}


	public void setPublicidade(Publicidade publicidade) {
		this.publicidade = publicidade;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}


	public List<Publicidade> getListaPublicidades() {
		return listaPublicidades;
	}


	public void setListaPublicidades(List<Publicidade> listaPublicidades) {
		this.listaPublicidades = listaPublicidades;
	}


	public List<Lead> getListaLeads() {
		return listaLeads;
	}


	public void setListaLeads(List<Lead> listaLeads) {
		this.listaLeads = listaLeads;
	}


	public boolean isHabilitarGroupBy() {
		return habilitarGroupBy;
	}


	public void setHabilitarGroupBy(boolean habilitarGroupBy) {
		this.habilitarGroupBy = habilitarGroupBy;
	}


	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}
	
	
	public void gerarListaConsultor() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaConsultor = usuarioFacade
					.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
			nomeUnidade = unidadenegocio.getNomerelatorio();
		}else{
			nomeUnidade = "Todos";
		}
		if (listaConsultor == null) {
			listaConsultor = new ArrayList<Usuario>();
		}
	}
	
	
	
	
	public void gerarPesquisaLeads() {
		String sql = "SELECT l FROM Lead l WHERE l.cliente.nome like '%"+ nomeCliente +"%' ";
		
		if ((datarecebimentoInicial != null) && (datarecebimentoFinal != null)) {
			sql = sql + " and l.dataenvio>='" + Formatacao.ConvercaoDataSql(datarecebimentoInicial) + "' and l.dataenvio<='"
					+ Formatacao.ConvercaoDataSql(datarecebimentoFinal) + "'";
		}
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			sql = sql + " and l.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		
		if (publicidade != null && publicidade.getIdpublicidade() != null) {
			sql = sql + " and l.publicidade.idpublicidade=" +  publicidade.getIdpublicidade();
		}
		if (habilitarGroupBy) {
			sql = sql + " Group by l.unidadenegocio, l.publicidade";
		}
		LeadFacade leadFacade = new LeadFacade();
		listaLeads = leadFacade.lista(sql);
		if (listaLeads == null) {
			listaLeads = new ArrayList<Lead>();
		}
	}
	
	
	public void gerarListaPublicidade() {
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade(); 
		try {
			listaPublicidades = publicidadeFacade.listar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (listaPublicidades == null) {
			listaPublicidades = new ArrayList<Publicidade>();
		} 
	}

}
