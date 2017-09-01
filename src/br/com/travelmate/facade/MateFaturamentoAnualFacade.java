package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.MetaFaturamentoAnualDao;
import br.com.travelmate.model.Metafaturamentoanual;

public class MateFaturamentoAnualFacade {
	
	private MetaFaturamentoAnualDao metaFaturamentoAnualDao;
	
	public Metafaturamentoanual salvar(Metafaturamentoanual meta) {
		metaFaturamentoAnualDao = new MetaFaturamentoAnualDao();
		try {
			return metaFaturamentoAnualDao.salvar(meta);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Metafaturamentoanual> listar(String sql){
		metaFaturamentoAnualDao = new MetaFaturamentoAnualDao();
		try {
			return metaFaturamentoAnualDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Metafaturamentoanual getMeta(String sql){
		metaFaturamentoAnualDao = new MetaFaturamentoAnualDao();
		try {
			return metaFaturamentoAnualDao.getMeta(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
