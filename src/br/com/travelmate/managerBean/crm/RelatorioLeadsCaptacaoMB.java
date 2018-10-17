package br.com.travelmate.managerBean.crm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cancelamento.RelatorioCancelamentoBean;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class RelatorioLeadsCaptacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LeadDao leadDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String status;
	private Date datarecebimentoInicial;
	private Date datarecebimentoFinal;
	private boolean desabilitarUnidade = false;
	private String nomeUnidade = "Todos";
	private String captacao;
	private List<Lead> listaLeads;
	private List<RelatorioLeadsCaptacaoBean> listaBean;
	private boolean habilitarGroupBy = false;
	private boolean habilitarNumeroPub = false;
	private int numTotal = 0;
	
	
	@PostConstruct
	public void init(){
		gerarListaUnidadeNegocio();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			desabilitarUnidade = true;
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


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}



	public String getCaptacao() {
		return captacao;
	}



	public void setCaptacao(String captacao) {
		this.captacao = captacao;
	}



	public boolean getHabilitarGroupBy() {
		return habilitarGroupBy;
	}



	public void setHabilitarGroupBy(boolean habilitarGroupBy) {
		this.habilitarGroupBy = habilitarGroupBy;
	}



	public boolean  getHabilitarNumeroPub() {
		return habilitarNumeroPub;
	}



	public void setHabilitarNumeroPub(boolean habilitarNumeroPub) {
		this.habilitarNumeroPub = habilitarNumeroPub;
	}



	public int getNumTotal() {
		return numTotal;
	}



	public void setNumTotal(int numTotal) {
		this.numTotal = numTotal;
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


		public List<Lead> getListaLeads() {
		return listaLeads;
	}


	public void setListaLeads(List<Lead> listaLeads) {
		this.listaLeads = listaLeads;
	}


	

	

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}
	
	
	
	
	public void gerarPesquisaLeads() {
		String sql="";
		numTotal=0;
		if (habilitarGroupBy) {
			sql = "SELECT l.captacao, l.unidadenegocio, count(l.captacao) as numcapacao FROM Lead l WHERE l.cliente.nome like '%"+ " " +"%' ";
			habilitarNumeroPub = true;
		}else {
			sql = "SELECT l.captacao, l.unidadenegocio FROM Lead l WHERE l.cliente.nome like '%"+ " " +"%' ";
			habilitarNumeroPub = false;
		}
		
		
		if ((datarecebimentoInicial != null) && (datarecebimentoFinal != null)) {
			sql = sql + " and l.dataenvio>='" + Formatacao.ConvercaoDataSql(datarecebimentoInicial) + "' and l.dataenvio<='"
					+ Formatacao.ConvercaoDataSql(datarecebimentoFinal) + "'";
		}
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			sql = sql + " and l.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		
		if (captacao != null && !captacao.equalsIgnoreCase("0")) {
			sql = sql + " and l.captacao='" + captacao + "' " ;
		}
		if (habilitarGroupBy) {
			sql = sql + " Group by l.unidadenegocio,  l.captacao";
		}
		listaLeads = leadDao.lista(sql);
		if (listaLeads == null) {
			listaLeads = new ArrayList<Lead>();
		}
		listaBean = new ArrayList<RelatorioLeadsCaptacaoBean>();
		for (Object p : listaLeads) {
		     Object[] objeto = (Object[])p;
		     RelatorioLeadsCaptacaoBean captacaoBean = new RelatorioLeadsCaptacaoBean();
		     captacaoBean.setCaptacao(((String) objeto[0]));
		     captacaoBean.setUnidadenegocio((Unidadenegocio) objeto[1] );
		     if (habilitarGroupBy) {
		    	 captacaoBean.setTotal(((Long)objeto[2]));
		     }else {
		    	 captacaoBean.setTotal(0l);
		     }
		     
		     listaBean.add(captacaoBean);
		     
		}
		if (habilitarGroupBy) {
			for (int i=0;i<listaBean.size();i++) {
				numTotal = (int) (numTotal + listaBean.get(i).getTotal());
			}
		}else {
			numTotal = listaLeads.size();
		}
		
	}
	
	
	
	public List<RelatorioLeadsCaptacaoBean> getListaBean() {
		return listaBean;
	}



	public void setListaBean(List<RelatorioLeadsCaptacaoBean> listaBean) {
		this.listaBean = listaBean;
	}



	public void limpar() {
		unidadenegocio = null;
		captacao="0";
		datarecebimentoFinal = null;
		datarecebimentoInicial = null;
		listaLeads = new ArrayList<Lead>();
		habilitarGroupBy = false;
		habilitarNumeroPub = false;
		numTotal = 0;
	}

}
