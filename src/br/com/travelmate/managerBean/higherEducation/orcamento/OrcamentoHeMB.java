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
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		nomePrograma = (String) session.getAttribute("nomePrograma");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		listaOrcamento = (List<Heorcamento>) session.getAttribute("listaOrcamento");
		session.removeAttribute("pesquisar");
		session.removeAttribute("nomePrograma");
		session.removeAttribute("chamadaTela");
		session.removeAttribute("listaOrcamento");
		listaUnidadeNegocio = GerarListas.listarUnidade();
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("OrcamentoHe")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				gerarListaOrcamento();
			}
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
		pesquisar = "Sim";
	}

	public void limparPesquisa() {
		// carregarOCliente();
		nomeCliente = "";
		dataInicio = null;
		dataTermino = null;
		unidadenegocio = null;
		gerarListaOrcamento();
		pesquisar = "Nao";
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
		session.setAttribute("listaOrcamento", listaOrcamento);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "OrcamentoHe");
		session.setAttribute("chamadaTela", "OrcamentoHe");
		return "orcamentoHePdf";
	}
	
	public String notificarEfetuarOrcamentoCrm(){
		return "followUp";
	}
	

}
