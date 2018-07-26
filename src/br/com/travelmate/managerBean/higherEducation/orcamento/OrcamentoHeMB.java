package br.com.travelmate.managerBean.higherEducation.orcamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadHistoricoDao;
import br.com.travelmate.facade.HeorcamentoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;

import br.com.travelmate.model.Heorcamento;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class OrcamentoHeMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LeadDao leadDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Heorcamento> listaOrcamento;
	private boolean habilitarUnidade;
	private String nomeCliente = "";
	private Date dataInicio;
	private Date dataTermino;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	
	
	@PostConstruct
	public void init() {
		listaUnidadeNegocio = GerarListas.listarUnidade();
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
			gerarListaOrcamento();
		}
	}


	public List<Heorcamento> getListaOrcamento() {
		return listaOrcamento;
	}


	public void setListaOrcamento(List<Heorcamento> listaOrcamento) {
		this.listaOrcamento = listaOrcamento;
	}


	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}


	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}


	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	public Date getDataTermino() {
		return dataTermino;
	}


	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
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
	
	
	
	public void gerarListaOrcamento() {
		String sql = null;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = "Select o from Heorcamento o where o.cliente.nome like '%" + nomeCliente
					+ "%'";
		} else {
			sql = "Select o from Heorcamento o where o.usuario.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()
					+ " and o.cliente.nome like '%" + nomeCliente + "%'"; 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isConsultaorcamento()) {
					sql = sql + " and o.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		HeorcamentoFacade heorcamentoFacade = new HeorcamentoFacade();
		listaOrcamento = heorcamentoFacade.listar(sql);
		if (listaOrcamento == null) {
			listaOrcamento = new ArrayList<Heorcamento>();
		} 
	}
	
	
	public void pesquisar() {
		String sql = null;
		sql = "Select o from Heorcamento o where o.cliente.nome like '%" + nomeCliente + "%'";
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and o.usuario.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (dataInicio != null && dataTermino != null) {
			sql = sql + " and o.dataemissao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and o.dataemissao<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} 
		if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
			if(!usuarioLogadoMB.getUsuario().getAcessounidade().isConsultaorcamento()) {
				sql = sql + " and o.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
			}
		} 
		sql = sql + " order by o.dataemissao desc";
		HeorcamentoFacade heorcamentoFacade = new HeorcamentoFacade();
		listaOrcamento = heorcamentoFacade.listar(sql);
		if (listaOrcamento == null) {
			listaOrcamento = new ArrayList<Heorcamento>();
		} 
	}

	public void limparPesquisa() {
		// carregarOCliente();
		nomeCliente = "";
		dataInicio = null;
		dataTermino = null;
		unidadenegocio = null;
		gerarListaOrcamento();
	}
	
	
	public String editar(Heorcamento heorcamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("heorcamento", heorcamento);
		Lead lead = leadDao.consultar("SELECT l FROM Lead l WHERE l.cliente.idcliente=" + heorcamento.getCliente().getIdcliente());
		session.setAttribute("lead", lead);
		return "cadOrcamentoHe";
	}
	
	
	
	public String imprimirOrcamento(Heorcamento heorcamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("heorcamento", heorcamento);
		return "orcamentoHePdf";
	}
	

}
