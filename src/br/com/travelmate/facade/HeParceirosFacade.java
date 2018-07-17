package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.HeParceirosDao;
import br.com.travelmate.model.Heparceiros;

public class HeParceirosFacade {
	
	HeParceirosDao heParceirosDao;
	
	public Heparceiros salvar(Heparceiros heParceiros) {
		heParceirosDao = new HeParceirosDao();
		try {
			return heParceirosDao.salvar(heParceiros);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Heparceiros> listar(String sql){
		heParceirosDao = new HeParceirosDao();
		try {
			return heParceirosDao.listar(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void remover(Heparceiros heParceiros) {
		heParceirosDao = new HeParceirosDao();
		try {
			heParceirosDao.remover(heParceiros);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
