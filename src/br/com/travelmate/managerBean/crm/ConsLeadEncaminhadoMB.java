package br.com.travelmate.managerBean.crm;
 
import java.io.Serializable;
import java.util.Date; 
import java.util.List; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession; 

import br.com.travelmate.facade.LeadEncaminhadoFacade; 
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Leadencaminhado; 
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas; 

@Named
@ViewScoped
public class ConsLeadEncaminhadoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB; 
	private List<Leadencaminhado> listaLead; 
	private Date dataEnvioInicio;
	private Date dataEnvioFinal;  
	private Usuario usuarioDe;
	private List<Usuario> listaUsuarioDe;
	private Usuario usuarioPara;
	private List<Usuario> listaUsuarioPara;   
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;  
	private String sql;  
	private boolean habilitarComboUnidade;
	
	@PostConstruct()
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			PesquisaBean pesquisa = (PesquisaBean) session.getAttribute("pesquisa");
			session.removeAttribute("pesquisa"); 
			listaUnidade = GerarListas.listarUnidade();
			if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialcrm()) {  
				habilitarComboUnidade = false; 
			}  
			if(pesquisa!=null){
				dataEnvioInicio = pesquisa.getDataProxFinal();
				dataEnvioFinal = pesquisa.getDataProxInicio(); 
				unidadenegocio = pesquisa.getUnidadenegocio();
				gerarListaConsultor();
				usuarioDe = pesquisa.getUsuario(); 
				usuarioPara = pesquisa.getUsuarioPara();
				pesquisar();  
			}  
			if(sql!=null && sql.length()>0){
				gerarListaLead(sql);
			}
		}
	}
 

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
 
	public List<Leadencaminhado> getListaLead() {
		return listaLead;
	}

	public void setListaLead(List<Leadencaminhado> listaLead) {
		this.listaLead = listaLead;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	} 
 
	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	} 

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
 
	public void setHabilitarComboUnidade(boolean habilitarComboUnidade) {
		this.habilitarComboUnidade = habilitarComboUnidade;
	} 
	
	public Date getDataEnvioInicio() {
		return dataEnvioInicio;
	}

	public void setDataEnvioInicio(Date dataEnvioInicio) {
		this.dataEnvioInicio = dataEnvioInicio;
	}

	public Date getDataEnvioFinal() {
		return dataEnvioFinal;
	}

	public void setDataEnvioFinal(Date dataEnvioFinal) {
		this.dataEnvioFinal = dataEnvioFinal;
	}

	public Usuario getUsuarioDe() {
		return usuarioDe;
	}

	public void setUsuarioDe(Usuario usuarioDe) {
		this.usuarioDe = usuarioDe;
	}

	public List<Usuario> getListaUsuarioDe() {
		return listaUsuarioDe;
	}

	public void setListaUsuarioDe(List<Usuario> listaUsuarioDe) {
		this.listaUsuarioDe = listaUsuarioDe;
	}

	public Usuario getUsuarioPara() {
		return usuarioPara;
	}

	public void setUsuarioPara(Usuario usuarioPara) {
		this.usuarioPara = usuarioPara;
	}

	public List<Usuario> getListaUsuarioPara() {
		return listaUsuarioPara;
	}

	public void setListaUsuarioPara(List<Usuario> listaUsuarioPara) {
		this.listaUsuarioPara = listaUsuarioPara;
	}

	public boolean isHabilitarComboUnidade() {
		return habilitarComboUnidade;
	}  

	public void gerarListaLead(String sql) {
		LeadEncaminhadoFacade leadFacade = new LeadEncaminhadoFacade();
		listaLead = leadFacade.listar(sql);
	}

	public String retornarCoresSituacao(int numeroSituacao) {
		if (numeroSituacao == 1) {
			return "#1E90FF;";
		} else if (numeroSituacao == 2) {
			return "#9ACD32;";
		} else if (numeroSituacao == 3) {
			return "#FF8C00;";
		} else if (numeroSituacao == 4) {
			return "#B22222;";
		} else if (numeroSituacao == 5) {
			return "#228B22;";
		} else if (numeroSituacao == 6) {
			return "#8B8989;";
		} else if (numeroSituacao == 7) {
			return "#9400D3;";
		}
		return "";
	}

	public void gerarListaConsultor() {
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaUsuarioDe = GerarListas.listarUsuarios(
					"SELECT u FROM Usuario u WHERE u.situacao='Ativo'" + " AND u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " ORDER BY u.nome");
			listaUsuarioPara = GerarListas.listarUsuarios(
					"SELECT u FROM Usuario u WHERE u.situacao='Ativo'" + " AND u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " ORDER BY u.nome");
		}
	}

	public void pesquisar() {  
		sql = "SELECT l FROM Leadencaminhado l WHERE l.lead.situacao<5";  
		if (usuarioDe != null && usuarioDe.getIdusuario() != null) {
			sql = sql + " AND l.usuariode.idusuario=" + usuarioDe.getIdusuario();
		} 
		if (usuarioPara != null && usuarioPara.getIdusuario() != null) {
			sql = sql + " AND l.usuariopara.idusuario=" + usuarioPara.getIdusuario();
		} 
		if (dataEnvioInicio != null && dataEnvioFinal != null) {
			sql = sql + " AND l.data>='" + Formatacao.ConvercaoDataSql(dataEnvioInicio) + "' AND "
					+ "l.data<='" + Formatacao.ConvercaoDataSql(dataEnvioFinal) + "'";
		}  
		sql = sql + " ORDER BY l.lead.dataproximocontato";
		gerarListaLead(sql); 
	}

	public void limparPesquisa() { 
		dataEnvioInicio = null;
		dataEnvioFinal = null; 
		usuarioDe = null;
		usuarioPara = null;
		unidadenegocio = null; 
		pesquisar();
	}    
	
	public String historicoCliente(Leadencaminhado leadencaminhado) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("lead", leadencaminhado.getLead()); 
		session.setAttribute("voltar", "consLeadEncaminhado"); 
		PesquisaBean pesquisa = new PesquisaBean();
		pesquisa.setDataProxFinal(dataEnvioFinal);
		pesquisa.setDataProxInicio(dataEnvioInicio); 
		pesquisa.setUnidadenegocio(unidadenegocio);
		pesquisa.setUsuario(usuarioDe);
		pesquisa.setUsuarioPara(usuarioPara);
		session.setAttribute("pesquisa", pesquisa);
		return "historicoCliente";
	}
}
