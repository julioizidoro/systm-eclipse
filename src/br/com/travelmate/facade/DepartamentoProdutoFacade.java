package br.com.travelmate.facade;

import java.sql.SQLException;

import br.com.travelmate.dao.DepartamentoProdutoDao;
import br.com.travelmate.model.Departamentoproduto;

public class DepartamentoProdutoFacade {
	
	private DepartamentoProdutoDao departamentoProdutoDao;
	
	public Departamentoproduto consultar(int idProduto) {
		departamentoProdutoDao = new DepartamentoProdutoDao();
		try {
			return departamentoProdutoDao.consultar(idProduto);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
