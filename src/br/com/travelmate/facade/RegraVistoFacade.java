package br.com.travelmate.facade;



import java.sql.SQLException;

import br.com.travelmate.dao.RegraVistoDao;
import br.com.travelmate.model.Regravisto;

public class RegraVistoFacade {
	
	private RegraVistoDao regraVistoDao;
	
	public Regravisto consultar(String sql) {
		regraVistoDao= new RegraVistoDao();
		try {
			return regraVistoDao.consultar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
	}

}
