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

import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.facade.PublicidadeFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Publicidade;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class RelatorioLeadsDetalhadoMB implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LeadDao leadDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Date dataUltContatoInicial;
	private Date dataUltContatoFinal;
	private List<Usuario> listaConsultor;
	private Usuario consultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String situacao;
	private Date datarecebimentoInicial;
	private Date datarecebimentoFinal;
	private boolean desabilitarUnidade = false;
	private String nomeUnidade = "Todos";
	private List<Publicidade> listaPublicidades;
	private Publicidade publicidade;
	private String nomeCliente = "";
	private List<Lead> listaLeads;
	private List<Produtos> listaProgramas;
	private Produtos programas;
	
	
	@PostConstruct
	public void init(){
		gerarListaUnidadeNegocio();
		gerarListaPublicidade();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			desabilitarUnidade = true;
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaConsultor();
			consultor = usuarioLogadoMB.getUsuario();
		}
		listaProgramas = GerarListas.listarProdutos("");
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
	

	public String getSituacao() {
		return situacao;
	}


	public void setSituacao(String situacao) {
		this.situacao = situacao;
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


	


	public List<Produtos> getListaProgramas() {
		return listaProgramas;
	}


	public void setListaProgramas(List<Produtos> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}


	public Produtos getProgramas() {
		return programas;
	}


	public void setProgramas(Produtos programas) {
		this.programas = programas;
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
		if ((datarecebimentoInicial != null && datarecebimentoFinal != null) || (dataUltContatoInicial != null && dataUltContatoFinal != null)) {
			if ((datarecebimentoInicial != null) && (datarecebimentoFinal != null)) {
				sql = sql + " and l.datarecebimento>='" + Formatacao.ConvercaoDataSql(datarecebimentoInicial) + "' and l.datarecebimento<='"
						+ Formatacao.ConvercaoDataSql(datarecebimentoFinal) + "'";
			}
			if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
				sql = sql + " and l.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
			
			if (publicidade != null && publicidade.getIdpublicidade() != null) {
				sql = sql + " and l.publicidade.idpublicidade=" +  publicidade.getIdpublicidade();
			}
			
			if (situacao != null && situacao.length() > 0 && !situacao.equals("0")) {
				sql = sql + " and l.situacao='" + situacao + "'";
			}
			
			if ((dataUltContatoInicial != null) && (dataUltContatoFinal != null)) {
				sql = sql + " and l.dataultimocontato>='" + Formatacao.ConvercaoDataSql(dataUltContatoFinal) + "' and l.dataultimocontato<='"
						+ Formatacao.ConvercaoDataSql(dataUltContatoFinal) + "'";
			}
			
			if (consultor != null && consultor.getIdusuario() != null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			
			if (programas != null && programas.getIdprodutos() != null) {
				sql = sql + " and l.produtos.idprodutos=" + programas.getIdprodutos(); 
			}
			listaLeads = leadDao.lista(sql);
			if (listaLeads == null) {
				listaLeads = new ArrayList<Lead>();
			}
			
		}else {
			Mensagem.lancarMensagemInfo("", "Informe a data de Recebimento ou Último Contato");
		}
		
	}
	
	public void gerarListaPublicidade() {
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade(); 
		try {
			listaPublicidades = publicidadeFacade.listar();
		} catch (SQLException e) {
			  
		}
		if (listaPublicidades == null) {
			listaPublicidades = new ArrayList<Publicidade>();
		} 
	}
	
	
	public void limpar() {
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			unidadenegocio = null;
			consultor = null;
		}
		publicidade = null;
		datarecebimentoFinal = null;
		datarecebimentoInicial = null;
		dataUltContatoFinal = null;
		dataUltContatoInicial = null;
		situacao = "";
		listaLeads = new ArrayList<Lead>();
		programas = null;
	}
	
	
	public String retornarCoresSituacao(int numeroSituacao) {
		if (numeroSituacao == 1) {
			return "#1E90FF;";
		} else if (numeroSituacao == 2) {
			return "#2E5495;";
		} else if (numeroSituacao == 3) {
			return "#9ACD32;";
		} else if (numeroSituacao == 4) {
			return "#FF8C00;";
		} else if (numeroSituacao == 5) {
			return "#B22222;";
		} else if (numeroSituacao == 6) {
			return "#228B22;";
		} else if (numeroSituacao == 7) {
			return "#8B8989;";
		} else if (numeroSituacao == 8) {
			return "#9400D3;";
		}
		return "";
	}

}
