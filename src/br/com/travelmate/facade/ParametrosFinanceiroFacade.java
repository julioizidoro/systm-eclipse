package br.com.travelmate.facade;

import java.sql.SQLException;

import br.com.travelmate.dao.ParametrosFinanceiroDao;
import br.com.travelmate.model.Parametrosfinanceiro;

public class ParametrosFinanceiroFacade {
	
	public Parametrosfinanceiro consultar() {
		ParametrosFinanceiroDao parametrosFinanceiroDao = new ParametrosFinanceiroDao();
		try {
			return parametrosFinanceiroDao.consultar();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public Parametrosfinanceiro salvar(Parametrosfinanceiro parametrosfinanceiro) {
		ParametrosFinanceiroDao parametrosFinanceiroDao = new ParametrosFinanceiroDao();
		try {
			return parametrosFinanceiroDao.salvar(parametrosfinanceiro);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}

}
