package br.com.travelmate.managerBean.vendas.relatoriometas;

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
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class RelatorioVendasGerentesMB implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Date dataVendaIncial;
	private Date dataVendaFinal;
	private List<Usuario> listaConsultor;
	private Usuario consultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private boolean desabilitarUnidade = false;
	private String nomeUnidade = "Todos";
	private List<Produtos> listaProgramas;
	private Produtos programas;
	private List<Vendas> listaVendas;
	
	@PostConstruct
	public void init(){
		gerarListaUnidadeNegocio();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			desabilitarUnidade = true;
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaConsultor();
			consultor = usuarioLogadoMB.getUsuario();
		}
		listaProgramas = GerarListas.listarProdutos("");
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Date getDataVendaIncial() {
		return dataVendaIncial;
	}

	public void setDataVendaIncial(Date dataVendaIncial) {
		this.dataVendaIncial = dataVendaIncial;
	}

	public Date getDataVendaFinal() {
		return dataVendaFinal;
	}

	public void setDataVendaFinal(Date dataVendaFinal) {
		this.dataVendaFinal = dataVendaFinal;
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
	
	public List<Vendas> getListaVendas() {
		return listaVendas;
	}

	public void setListaVendas(List<Vendas> listaVendas) {
		this.listaVendas = listaVendas;
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
	
	public void gerarPesquisa() {
		String sql = "SELECT v FROM Vendas v WHERE v.situacao<>'PROCESSO' ";
		
		if (dataVendaIncial != null && dataVendaFinal != null) {
			sql = sql + " and v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataVendaIncial) + "' and v.dataVenda<='"
					+ Formatacao.ConvercaoDataSql(dataVendaFinal) + "'";
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and v.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}

		if (consultor != null && consultor.getIdusuario() != null) {
			sql = sql + " and v.usuario.idusuario=" + consultor.getIdusuario();
		}

		if (programas != null && programas.getIdprodutos() != null) {
			sql = sql + " and v.produtos.idprodutos=" + programas.getIdprodutos();
		}

		listaVendas = vendasDao.lista(sql);
		if (listaVendas == null) {
			listaVendas = new ArrayList<Vendas>();
		}
	}
	
	public void limpar() {
		dataVendaFinal = null;
		dataVendaIncial = null;
		unidadenegocio = null;
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			listaConsultor = new ArrayList<Usuario>();
			unidadenegocio = null;
		}
		programas = null;
		listaVendas = new ArrayList<Vendas>();
	}
	
	public Float calcularDescontos(Vendas venda) {
		float valorDesconto = 0.0f;
		if(venda!=null) {
			if (venda.getVendascomissao()!=null) {
				valorDesconto = venda.getVendascomissao().getDescontotm() + venda.getVendascomissao().getDescontoloja();
			}
		}
		return valorDesconto;
	}

}
