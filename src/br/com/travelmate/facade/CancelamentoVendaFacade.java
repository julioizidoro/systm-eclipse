package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.CancelamentoVendaDao;
import br.com.travelmate.model.Cancelamentovenda;



public class CancelamentoVendaFacade {
	
	CancelamentoVendaDao cancelamentoVendaDao;
	
	public Cancelamentovenda salvar(Cancelamentovenda cancelamentovenda){
		cancelamentoVendaDao = new CancelamentoVendaDao();
		try {
			return cancelamentoVendaDao.salvar(cancelamentovenda);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	
	public List<Cancelamentovenda> listar(String sql){
		cancelamentoVendaDao = new CancelamentoVendaDao();
		try {
			return cancelamentoVendaDao.listar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
		
	}

}
