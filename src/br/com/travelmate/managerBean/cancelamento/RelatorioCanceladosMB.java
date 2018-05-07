package br.com.travelmate.managerBean.cancelamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class RelatorioCanceladosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Unidadenegocio> listaUnidade;
	private Unidadenegocio unidade;
	private Date dataInicial;
	private Date dataFinal;
	private String nomeCliente;
	private List<Cancelamento> listaCancelamento;

	@PostConstruct
	public void init() {
		gerarListaUnidadeNegocio();
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public Unidadenegocio getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidadenegocio unidade) {
		this.unidade = unidade;
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

	public List<Cancelamento> getListaCancelamento() {
		return listaCancelamento;
	}

	public void setListaCancelamento(List<Cancelamento> listaCancelamento) {
		this.listaCancelamento = listaCancelamento;
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar(true);
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<Unidadenegocio>();
		}
	}

	public void pesquisar() {
		String sql = "Select c from Cancelamento c where c.vendas.cliente.nome like '%" + nomeCliente
				+ "%' and c.situacao='FINALIZADA' ";
		if (unidade != null) {
			sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + unidade.getIdunidadeNegocio();
		}
		if (dataInicial != null && dataFinal != null) {
			sql = sql + " and c.datasolicitacao>='" + Formatacao.ConvercaoDataSql(dataInicial)
					+ "' and c.datasolicitacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		}
		sql = sql + " order by c.datasolicitacao DESC, c.vendas.cliente.nome DESC";
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		listaCancelamento = cancelamentoFacade.listar(sql);
		if (listaCancelamento == null) {
			listaCancelamento = new ArrayList<Cancelamento>();
		}
	}

	public void limpar() {
		listaCancelamento = new ArrayList<Cancelamento>();
		unidade = null;
		dataFinal = null;
		dataInicial = null;
		nomeCliente = "";
	}

	public float gerarValorLiquido(Cancelamento cancelamento) {
		float valorLiquido = 0f;
		Vendascomissao vendascomissao = cancelamento.getVendas().getVendascomissao();
		if (vendascomissao == null) {
			vendascomissao = new Vendascomissao();
			vendascomissao.setVendas(cancelamento.getVendas());
			vendascomissao.setComissaoemissor(0.0f);
			vendascomissao.setComissaofranquiabruta(0.0f);
			vendascomissao.setComissaofraquia(0.0f);
			vendascomissao.setComissaoterceiros(0.0f);
			vendascomissao.setComissaogerente(0.0f);
			vendascomissao.setComissaotm(0.0f);
			vendascomissao.setDesagio(0.0f);
			vendascomissao.setDescontoloja(0.0f);
			vendascomissao.setDescontotm(0.0f);
			vendascomissao.setIncentivo(0.0f);
			vendascomissao.setLiquidofranquia(0.0f);
			vendascomissao.setLiquidovendas(0.0f);
			vendascomissao.setPaga("Não");
			vendascomissao.setProdutos(cancelamento.getVendas().getProdutos());
			vendascomissao.setTaxatm(0.0f);
			vendascomissao.setUsuario(cancelamento.getVendas().getUsuario());
			vendascomissao.setValorbruto(0.0f);
			vendascomissao.setValorcomissionavel(0.0f);
			vendascomissao.setValorfornecedor(0.0f);
			vendascomissao.setJuros(0.0f);
		}
		valorLiquido = (cancelamento.getMultacliente() - valorLiquido - vendascomissao.getComissaotm()
				- vendascomissao.getTaxatm())
				+ (vendascomissao.getDescontotm() + vendascomissao.getDescontoloja() + cancelamento.getEstornoloja())
				- cancelamento.getMultafornecedor();
		return valorLiquido;
	}

}
