package br.com.travelmate.ti;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import br.com.travelmate.dao.AcessoUnidadeDao;
import br.com.travelmate.dao.VendasDao;

import br.com.travelmate.facade.MateFaturamentoAnualFacade;
import br.com.travelmate.facade.MetaFaturamentoMensalFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.VendaProdutoFacade;

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

public class TiBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VendasDao vendasDao;
	@Inject 
	private AcessoUnidadeDao acessoUnidadeDao;

	private Parametrosprodutos parametros;

	public TiBean(Parametrosprodutos parametros, VendasDao vendasDao) {
		this.parametros = parametros;
		this.vendasDao = vendasDao;
	}

	

	public void salvarAcessoUnidade() {
		List<Usuario> listaUsuario = GerarListas
				.listarUsuarios("SELECT u FROM Usuario u WHERE u.situacao='Ativo' ORDER BY u.nome");
		for (int i = 0; i < listaUsuario.size(); i++) {
			Acessounidade acessounidade = acessoUnidadeDao.consultar(
					"SELECT a FROM Acessounidade a WHERE a.usuario.idusuario=" + listaUsuario.get(i).getIdusuario());
			if (acessounidade == null) {
				acessounidade = new Acessounidade();
				acessounidade.setComissaoparceiros(true);
				acessounidade.setConsultaorcamento(true);
				acessounidade.setCrm(true);
				acessounidade.setDashboard(true);
				acessounidade.setEmissaoconsulta(true);
				acessounidade.setUsuario(listaUsuario.get(i));
				acessounidade = acessoUnidadeDao.salvar(acessounidade);
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
		
		List<Vendas> lista = vendasDao.lista(sql);
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
			lista = vendasDao.lista(sql);
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
					|| (idProduto == parametros.getTraducaojuramentada()) || (idProduto == parametros.getPassagem()))) {
				vendaproduto.setProduto(vendaproduto.getProduto() + 1);
			} else if ((idProduto == parametros.getPacotes())) {
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
		
		List<Vendas> lista = vendasDao.lista(sql);
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
