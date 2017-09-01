package br.com.travelmate.bean;



import java.util.Date;


import br.com.travelmate.facade.MateFaturamentoAnualFacade;
import br.com.travelmate.facade.MetaFaturamentoMensalFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Metafaturamentoanual;
import br.com.travelmate.model.Metasfaturamentomensal;
import br.com.travelmate.util.Formatacao;

public class MetasFaturamentoBean {
	
	
	
	public Metafaturamentoanual getMetaAnual(UsuarioLogadoMB usuarioLogadoMB){
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		MateFaturamentoAnualFacade meFacade =new MateFaturamentoAnualFacade();
		String sql = "SELECT m FROM Metafaturamentoanual m where m.ano=" + ano;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("unidade")){
			sql = sql + " and m.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		
		}else {
			sql = sql + " and m.unidadenegocio.idunidadeNegocio=6";
		}
		Metafaturamentoanual meta = meFacade.getMeta(sql);
		if(meta==null){
			meta = new Metafaturamentoanual();
			meta.setAno(ano);
			meta.setMes(mes);
			meta.setMetaalcancada(0.0f);
			meta.setPercentualalcancado(0.0f);
			meta.setValormeta(0.0f);
			meta.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
		}
		return meta;
		
	}
	
	public Metasfaturamentomensal getMetaMensal(UsuarioLogadoMB usuarioLogadoMB){
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		MetaFaturamentoMensalFacade metaFaturamentoMensalFacade = new MetaFaturamentoMensalFacade();
		String sql = "SELECT m FROM Metasfaturamentomensal m where m.mes=" + mes + " and m.ano=" + ano;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("unidade")){
			sql = sql + " and m.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		
		}else {
			sql = sql + " and m.unidadenegocio.idunidadeNegocio=6";
		}
		Metasfaturamentomensal meta = metaFaturamentoMensalFacade.getMeta(sql);
		if(meta==null){
			meta = new Metasfaturamentomensal();
			meta.setAno(ano); 
			meta.setMes(mes);
			meta.setValoralcancado(0.0f);
			meta.setPercentualalcancado(0.0f);
			meta.setValormeta(0.0f);
			meta.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
		}
		return meta;
		
	}
	
	public Double getFaturamentoMensal(UsuarioLogadoMB usuarioLogadoMB){
		int mes = Formatacao.getMesData(new Date());
		int ano = Formatacao.getAnoData(new Date());
		int dia = Formatacao.getAnoData(new Date());
		int primeiro = Formatacao.getPrimeiroDiaMes(ano, mes, dia);
		mes = mes +1;
		String dataInicial = String.valueOf(ano) + "-" + String.valueOf(mes) + "-" + String.valueOf(primeiro);
		String sqlacumulado = "Select distinct sum(valor) as valor " +
		        "From Vendas where (situacao='FINALIZADA' OR situacao='ANDAMENTO') and vendasMatriz='S' and dataVenda>='" + dataInicial + "'";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("unidade")){
			sqlacumulado = sqlacumulado + " and unidadeNegocio_idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		VendasFacade vendasFacade = new VendasFacade();
		return vendasFacade.saldoAcumulado(sqlacumulado);
	}
	
	public Float getFaturamentoSemana(UsuarioLogadoMB usuarioLogadoMB){
		Date data = Formatacao.getPrimeiroDiaSemana(new Date());
		String dataInicial = Formatacao.ConvercaoDataSql(data); 
		String sqlacumulado = "Select distinct sum(valor) as valor " +
		        "From Vendas where (situacao='FINALIZADA' OR situacao='ANDAMENTO') and vendasMatriz='S' and dataVenda>='" + dataInicial + "'";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("unidade")){
			sqlacumulado = sqlacumulado + " and unidadeNegocio_idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		VendasFacade vendasFacade = new VendasFacade();
		return vendasFacade.saldoAcumulado(sqlacumulado).floatValue();
	}

}
