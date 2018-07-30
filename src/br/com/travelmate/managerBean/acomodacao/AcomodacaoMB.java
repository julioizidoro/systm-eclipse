package br.com.travelmate.managerBean.acomodacao;

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
import br.com.travelmate.facade.AcomodacaoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Acomodacao;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AcomodacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LeadDao leadDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Acomodacao acomodacao;
	private String cliente = "";
	private Date dataInicio;
	private Date dataFinal;
	private Unidadenegocio unidadenegocio;
	private int idvenda = 0;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private List<Acomodacao> listaAcomodacao;
	
	
	@PostConstruct
	public void init() {
		gerarListaUnidadeNegocio();
		carregarListaVendasAcomodacao();
	}


	public Acomodacao getAcomodacao() {
		return acomodacao;
	}


	public void setAcomodacao(Acomodacao acomodacao) {
		this.acomodacao = acomodacao;
	}


	public String getCliente() {
		return cliente;
	}


	public void setCliente(String cliente) {
		this.cliente = cliente;
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


	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public int getIdvenda() {
		return idvenda;
	}


	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}


	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}


	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}
	
	
	
	
	public List<Acomodacao> getListaAcomodacao() {
		return listaAcomodacao;
	}


	public void setListaAcomodacao(List<Acomodacao> listaAcomodacao) {
		this.listaAcomodacao = listaAcomodacao;
	}


	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar();
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}
	
	
	public void carregarListaVendasAcomodacao() {
		if (usuarioLogadoMB.getUsuario() != null || usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
			String sql = "Select a from Acomodacao a where a.vendas.dataVenda>='" + dataConsulta  + 
					"'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sql = sql + " and  c.vendas.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
				if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
					if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
						sql = sql + " and a.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}
			sql = sql + " order by a.vendas.dataVenda desc, a.vendas.cliente.nome";
			AcomodacaoFacade acomodacaoFacade = new AcomodacaoFacade();
			listaAcomodacao = acomodacaoFacade.lista(sql);
			if (listaAcomodacao == null) {
				listaAcomodacao = new ArrayList<Acomodacao>();
			}
		} else {
			if (listaAcomodacao == null) {
				listaAcomodacao = new ArrayList<Acomodacao>();
			}
		}
	}
	
	public String editar(Acomodacao acomodacao) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("acomodacao", acomodacao);
		session.setAttribute("cliente", acomodacao.getVendas().getCliente());
		session.setAttribute("lead", leadDao.consultar("SELECT l FROM Lead l WHERE l.cliente.idcliente=" + acomodacao.getVendas().getCliente().getIdcliente()));
		return "cadAcomodacao";
	}
	
	
	public void pesquisar() {
		boolean pesquisaSemParametro = true;
		String sql = null;
		sql = "Select a from Acomodacao a where "
				+ " a.vendas.cliente.nome like '%" + cliente + "%'";
		if (idvenda > 0) {
			sql = sql + " and a.vendas.idvendas=" + idvenda;
			pesquisaSemParametro = false;
		}
		if ((dataInicio != null) && (dataFinal != null)) {
			sql = sql + " and a.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and a.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
			pesquisaSemParametro = false;
		}
		if ((unidadenegocio != null) && (unidadenegocio.getIdunidadeNegocio() != null)) {
			sql = sql + " and a.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			if ((dataInicio == null) && (dataFinal == null)) {
				Date dataconsulta = new Date();
				try {
					dataconsulta = Formatacao.SomarDiasDatas(dataconsulta, -30);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sql = sql + " and a.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			}
			pesquisaSemParametro = false;
		}
		if (pesquisaSemParametro){
			if ((dataInicio == null) && (dataFinal == null)) {
				Date dataconsulta = new Date();
				try {
					dataconsulta = Formatacao.SomarDiasDatas(dataconsulta, -30);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sql = sql + " and a.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			}
		}
		sql = sql + "  order by a.vendas.dataVenda desc";
		AcomodacaoFacade acomodacaoFacade = new AcomodacaoFacade();
		listaAcomodacao = acomodacaoFacade.lista(sql);
		if (listaAcomodacao == null) {
			listaAcomodacao = new ArrayList<Acomodacao>();
		}
	}

	public void limparPesquisa() {
		carregarListaVendasAcomodacao();
		cliente = "";
		dataFinal = null;
		dataInicio = null;
		unidadenegocio = null;
		idvenda = 0;
	}
	

}