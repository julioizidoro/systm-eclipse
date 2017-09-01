package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.MetaFaturamentoMensalDao;
import br.com.travelmate.model.Metasfaturamentomensal;

public class MetaFaturamentoMensalFacade {
	
private MetaFaturamentoMensalDao metaFaturamentoMensalDao;
	
	public Metasfaturamentomensal salvar(Metasfaturamentomensal meta) {
		metaFaturamentoMensalDao = new MetaFaturamentoMensalDao();
		try {
			return metaFaturamentoMensalDao.salvar(meta);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Metasfaturamentomensal> listar(String sql){
		metaFaturamentoMensalDao = new MetaFaturamentoMensalDao();
		try {
			return metaFaturamentoMensalDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Metasfaturamentomensal getMeta(String sql){
		metaFaturamentoMensalDao = new MetaFaturamentoMensalDao();
		try {
			return metaFaturamentoMensalDao.getMeta(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}

