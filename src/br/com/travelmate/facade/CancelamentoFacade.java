package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.CancelamentoDao;
import br.com.travelmate.model.Cancelamento;

public class CancelamentoFacade {
	
	CancelamentoDao cancelamentoDao;
	
	public Cancelamento salvar(Cancelamento cancelamento){
		cancelamentoDao = new CancelamentoDao();
		try {
			return cancelamentoDao.salvar(cancelamento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Cancelamento> listar(String sql){
		cancelamentoDao = new CancelamentoDao();
		try {
			return cancelamentoDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
