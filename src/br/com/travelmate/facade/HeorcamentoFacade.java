package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.HeorcamentoDao;
import br.com.travelmate.model.Heorcamento;

public class HeorcamentoFacade {

	
	HeorcamentoDao heorcamentoDao;
	
	public Heorcamento salvar(Heorcamento heorcamento) {
		heorcamentoDao = new HeorcamentoDao();
		try {
			return heorcamentoDao.salvar(heorcamento);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	public List<Heorcamento> listar(String sql){
		heorcamentoDao = new HeorcamentoDao();
		try {
			return heorcamentoDao.listar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	public void remover(Heorcamento heorcamento) {
		heorcamentoDao = new HeorcamentoDao();
		try {
			heorcamentoDao.remover(heorcamento);
		} catch (SQLException e) {
			  
		}
	}
}
