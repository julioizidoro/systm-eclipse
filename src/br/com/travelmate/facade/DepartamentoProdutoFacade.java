package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.DepartamentoProdutoDao;
import br.com.travelmate.model.Departamentoproduto;

public class DepartamentoProdutoFacade {
	
	private DepartamentoProdutoDao departamentoProdutoDao;
	
	public Departamentoproduto consultar(int idProduto) {
		departamentoProdutoDao = new DepartamentoProdutoDao();
		try {
			return departamentoProdutoDao.consultar(idProduto);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	public List<Departamentoproduto> listar(int iddepartamento) {
		departamentoProdutoDao = new DepartamentoProdutoDao();
		try {
			return departamentoProdutoDao.listar(iddepartamento);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	public List<Departamentoproduto> listar() {
		departamentoProdutoDao = new DepartamentoProdutoDao();
		try {
			return departamentoProdutoDao.listar();
		} catch (SQLException e) {
			  
			return null;
		}
	}

}
