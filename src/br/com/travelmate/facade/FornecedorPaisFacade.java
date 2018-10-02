package br.com.travelmate.facade;

import java.sql.SQLException;

import br.com.travelmate.dao.FornecedorPaisDao;
import br.com.travelmate.model.Fornecedorpais;

public class FornecedorPaisFacade {
	
	private FornecedorPaisDao fornecedorPaisDao;
	
	public Fornecedorpais salvar(Fornecedorpais fornecedorpais) {
		fornecedorPaisDao= new FornecedorPaisDao();
		try {
			return fornecedorPaisDao.salvar(fornecedorpais);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	public Fornecedorpais buscar(String sql){
		fornecedorPaisDao = new FornecedorPaisDao();
		try {
			return fornecedorPaisDao.buscar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
	}

}
