package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.HeorcamentopaisDao;
import br.com.travelmate.model.Heorcamentopais;

public class HeorcamentopaisFacade {
	
	HeorcamentopaisDao heorcamentopaisDao;
	
	public Heorcamentopais salvar(Heorcamentopais heorcamentopais) {
		heorcamentopaisDao = new HeorcamentopaisDao();
		try {
			return heorcamentopaisDao.salvar(heorcamentopais);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Heorcamentopais> listar(String sql){
		heorcamentopaisDao = new HeorcamentopaisDao();
		try {
			return heorcamentopaisDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void remover(int idheorcamentopais) {
		heorcamentopaisDao = new HeorcamentopaisDao();
		try {
			heorcamentopaisDao.remover(idheorcamentopais);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
