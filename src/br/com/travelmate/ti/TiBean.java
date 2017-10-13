package br.com.travelmate.ti;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.travelmate.facade.AcessoUnidadeFacade;
import br.com.travelmate.facade.MateFaturamentoAnualFacade;
import br.com.travelmate.facade.MetaFaturamentoMensalFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.VendaProdutoFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.model.Acessounidade;
import br.com.travelmate.model.Metafaturamentoanual;
import br.com.travelmate.model.Metasfaturamentomensal;
import br.com.travelmate.model.Parametrosprodutos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendaproduto;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;

public class TiBean {

	private Parametrosprodutos parametros;

	public TiBean(Parametrosprodutos parametros) {
		this.parametros = parametros;
	}

	/*
	 * public void gerarCobrancaNova() { CobrancaFacade cobrancaFacade = new
	 * CobrancaFacade(); List<Cobranca> lista =
	 * cobrancaFacade.listar("SELECT c FROM Cobranca c"); ContasReceberFacade
	 * contasReceberFacade = new ContasReceberFacade(); if (lista != null) { for
	 * (int i = 0; i < lista.size(); i++) { if (lista.get(i).getVendas() != null) {
	 * String dataVencimento = Formatacao.SubtarirDatas(new Date(), 5,
	 * "yyyy-MM-dd"); String sql =
	 * "SELECT c FROM Contasreceber c where c.datavencimento<='" + dataVencimento +
	 * "' and c.situacao<>'cc' and c.datapagamento is NULL and c.valorpago=0 and c.vendas.idvendas="
	 * + lista.get(i).getVendas().getIdvendas(); List<Contasreceber> listaContas =
	 * contasReceberFacade.listar(sql); if (listaContas != null) { if
	 * (listaContas.size() > 0) { Crmcobranca crmCobranca = new Crmcobranca();
	 * crmCobranca.setPrioridade("1"); crmCobranca.setNota("Inserido pelo SysTM");
	 * crmCobranca.setProximocontato(new Date());
	 * crmCobranca.setSituacao("COBRANCA");
	 * crmCobranca.setVendas(lista.get(i).getVendas());
	 * crmCobranca=crmCobrancaDao.salvar(crmCobranca); CrmCobrancaBean
	 * crmCobrancaBean = new CrmCobrancaBean(); for (int
	 * c=0;c<listaContas.size();c++) { crmCobrancaBean.criar(listaContas.get(c)); }
	 * 
	 * HistoricoCobrancaFacade hitoricoCobrancaFacade = new
	 * HistoricoCobrancaFacade(); List<Historicocobranca> listaHistorico =
	 * hitoricoCobrancaFacade
	 * .listar("SELECT h FROM Historicocobranca h where h.cobranca.idcobranca=" +
	 * lista.get(i).getIdcobranca()); if (listaHistorico != null) { for (int h = 0;
	 * h < listaHistorico.size(); h++) { Crmcobrancahistorico crmCobrancaHistoricio
	 * = new Crmcobrancahistorico();
	 * crmCobrancaHistoricio.setCliente(lista.get(i).getVendas().getCliente());
	 * crmCobrancaHistoricio.setData(listaHistorico.get(h).getData());
	 * crmCobrancaHistoricio.setHistorico(listaHistorico.get(h).getAssunto());
	 * crmCobrancaHistoricio.setProximocontato(new Date());
	 * crmCobrancaHistoricio.setTipocontato("Telefone");
	 * crmCobrancaHistoricio.setUsuario(listaHistorico.get(h).getUsuario());
	 * crmCobrancaHistoricoDao.salvar(crmCobrancaHistoricio); } } } } } } }
	 * 
	 * }
	 */

	public void salvarAcessoUnidade() {
		AcessoUnidadeFacade acessoUnidadeFacade = new AcessoUnidadeFacade();
		List<Usuario> listaUsuario = GerarListas
				.listarUsuarios("SELECT u FROM Usuario u WHERE u.situacao='Ativo' ORDER BY u.nome");
		for (int i = 0; i < listaUsuario.size(); i++) {
			Acessounidade acessounidade = acessoUnidadeFacade.consultar(
					"SELECT a FROM Acessounidade a WHERE a.usuario.idusuario=" + listaUsuario.get(i).getIdusuario());
			if (acessounidade == null) {
				acessounidade = new Acessounidade();
				acessounidade.setComissaoparceiros(true);
				acessounidade.setConsultaorcamento(true);
				acessounidade.setCrm(true);
				acessounidade.setDashboard(true);
				acessounidade.setEmissaoconsulta(true);
				acessounidade.setUsuario(listaUsuario.get(i));
				acessounidade = acessoUnidadeFacade.salvar(acessounidade);
			}
		}
	}

	public void listarVendas() {
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date());
		Calendar c = new GregorianCalendar(ano, mes, 1);
		Date data = c.getTime();
		String dataConsulta = Formatacao.ConvercaoDataSql(data);
		// Matriz
		String sql = "SELECT v FROM Vendas v WHERE v.situacao<>'CANCELADA' and v.situacao<>'PROCESSO'"
				+ " and v.dataVenda>='" + dataConsulta + "'" + " and v.vendasMatriz='S'";
		VendasFacade vendasFacade = new VendasFacade();
		List<Vendas> lista = vendasFacade.lista(sql);
		if (lista != null && lista.size() > 0) {
			numeroProdutos(lista, true);
			faturamentoMensal(lista, true);
			faturamentoAnual(lista, true);
		}
		// Unidades
		List<Unidadenegocio> listaUnidade = GerarListas.listarUnidade();
		for (int i = 0; i < listaUnidade.size(); i++) {
			sql = "SELECT v FROM Vendas v WHERE v.situacao<>'CANCELADA' and v.situacao<>'PROCESSO'"
					+ " and v.dataVenda>='" + dataConsulta + "'" + " and v.unidadenegocio.idunidadeNegocio="
					+ listaUnidade.get(i).getIdunidadeNegocio();
			lista = vendasFacade.lista(sql);
			if (lista != null && lista.size() > 0) {
				numeroProdutos(lista, false);
				faturamentoMensal(lista, false);
				faturamentoAnual(lista, false);
			}
		}
	}

	public void numeroProdutos(List<Vendas> lista, boolean matriz) {
		VendaProdutoFacade vendaProdutoFacade = new VendaProdutoFacade();
		String mesano = Formatacao.gerarCopetencia(new Date());
		String sql = "SELECT v FROM Vendaproduto v where v.mesano='" + mesano + "'";
		if (!matriz) {
			sql = sql + " and v.unidadenegocio.idunidadeNegocio="
					+ lista.get(0).getUnidadenegocio().getIdunidadeNegocio();
		} else {
			sql = sql + " and v.unidadenegocio.idunidadeNegocio=6";
		}
		Vendaproduto vendaproduto = vendaProdutoFacade.lista(sql);
		if (vendaproduto == null) {
			vendaproduto = new Vendaproduto();
			vendaproduto.setMesano(mesano);
			vendaproduto.setIntercambio(0);
			vendaproduto.setTurismo(0);
			vendaproduto.setProduto(0);
			if (!matriz) {
				vendaproduto.setUnidadenegocio(lista.get(0).getUnidadenegocio());
			} else {
				UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
				Unidadenegocio unidadenegocio = unidadeNegocioFacade.consultar(6);
				vendaproduto.setUnidadenegocio(unidadenegocio);
			}
		}
		vendaproduto.setIntercambio(0);
		vendaproduto.setTurismo(0);
		vendaproduto.setProduto(0);
		for (int i = 0; i < lista.size(); i++) {
			int idProduto = lista.get(i).getProdutos().getIdprodutos();
			if ((idProduto == parametros.getVisto()) || (idProduto == parametros.getSeguroPrivado()
					|| (idProduto == parametros.getTraducaojuramentada()))) {
				vendaproduto.setProduto(vendaproduto.getProduto() + 1);
			} else if ((idProduto == parametros.getPassagem()) || (idProduto == parametros.getPacotes())) {
				vendaproduto.setTurismo(vendaproduto.getTurismo() + 1);
			} else {
				vendaproduto.setIntercambio(vendaproduto.getIntercambio() + 1);
			}
		}
		vendaproduto = vendaProdutoFacade.salvar(vendaproduto);
	}

	public void faturamentoMensal(List<Vendas> lista, boolean matriz) {
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		MetaFaturamentoMensalFacade metaFaturamentoMensalFacade = new MetaFaturamentoMensalFacade();
		String sql = "SELECT m FROM Metasfaturamentomensal m where m.mes=" + mes + " and m.ano=" + ano;
		if (!matriz) {
			sql = sql + " and m.unidadenegocio.idunidadeNegocio="
					+ lista.get(0).getUnidadenegocio().getIdunidadeNegocio();
		} else {
			sql = sql + " and m.unidadenegocio.idunidadeNegocio=6";
		}
		Metasfaturamentomensal meta = metaFaturamentoMensalFacade.getMeta(sql);
		if (meta == null) {
			meta = new Metasfaturamentomensal();
			meta.setAno(ano);
			meta.setMes(mes);
			meta.setValoralcancado(0.0f);
			meta.setPercentualalcancado(0.0f);
			meta.setValormeta(0.0f);
			if (!matriz) {
				meta.setUnidadenegocio(lista.get(0).getUnidadenegocio());
			} else {
				UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
				Unidadenegocio unidadenegocio = unidadeNegocioFacade.consultar(6);
				meta.setUnidadenegocio(unidadenegocio);
			}
		}
		meta.setValoralcancado(0.0f);
		meta.setPercentualalcancado(0.0f);
		for (int i = 0; i < lista.size(); i++) {
			float valormeta = meta.getValormeta();
			float alcancado = meta.getValoralcancado() + lista.get(i).getValor();
			float perc = (alcancado / valormeta) * 100;
			meta.setPercentualalcancado(perc);
			meta.setValoralcancado(alcancado);
		}
		meta = metaFaturamentoMensalFacade.salvar(meta);
	}

	public void faturamentoAnual(List<Vendas> listaVenda, boolean matriz) {
		int ano = Formatacao.getAnoData(new Date());
		Calendar c = new GregorianCalendar(ano, 0, 1);
		Date data = c.getTime();
		String dataConsulta = Formatacao.ConvercaoDataSql(data);
		String sql = "SELECT v FROM Vendas v WHERE v.situacao<>'CANCELADA' and v.situacao<>'PROCESSO'"
				+ " and v.dataVenda>='" + dataConsulta + "'";
		if (matriz) {
			sql = sql + " and v.vendasMatriz='S'";
		} else {
			sql = sql + " and v.unidadenegocio.idunidadeNegocio="
					+ listaVenda.get(0).getUnidadenegocio().getIdunidadeNegocio();
		}
		VendasFacade vendasFacade = new VendasFacade();
		List<Vendas> lista = vendasFacade.lista(sql);
		MateFaturamentoAnualFacade meFacade = new MateFaturamentoAnualFacade();
		sql = "SELECT m FROM Metafaturamentoanual m where m.ano=" + ano;
		if (!matriz) {
			sql = sql + " and m.unidadenegocio.idunidadeNegocio="
					+ listaVenda.get(0).getUnidadenegocio().getIdunidadeNegocio(); 
		} else {
			sql = sql + " and m.unidadenegocio.idunidadeNegocio=6";
		}
		Metafaturamentoanual meta = meFacade.getMeta(sql);
		if (meta == null) {
			meta = new Metafaturamentoanual();
			meta.setAno(ano);
			meta.setMes(0);
			meta.setMetaalcancada(0.0f);
			meta.setPercentualalcancado(0.0f);
			meta.setValormeta(0.0f);
			if (!matriz) {
				meta.setUnidadenegocio(listaVenda.get(0).getUnidadenegocio());
			} else {
				UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
				Unidadenegocio unidadenegocio = unidadeNegocioFacade.consultar(6);
				meta.setUnidadenegocio(unidadenegocio);
			}
		}
		meta.setMetaalcancada(0.0f);
		meta.setPercentualalcancado(0.0f);
		for (int i = 0; i < lista.size(); i++) {
			float valormeta = meta.getValormeta();
			float alcancado = meta.getMetaalcancada() + lista.get(i).getValor();
			float perc = (alcancado / valormeta) * 100;
			meta.setPercentualalcancado(perc);
			meta.setMetaalcancada(alcancado);
		}
		meta = meFacade.salvar(meta);
	}

}
