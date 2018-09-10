package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.VendaProdutoDao;
import br.com.travelmate.model.Vendaproduto;

public class VendaProdutoFacade {
	
	private VendaProdutoDao vendaProdutoDao;
	
	public Vendaproduto salvar(Vendaproduto vendaproduto) {
		vendaProdutoDao = new VendaProdutoDao();
		try {
			return vendaProdutoDao.salvar(vendaproduto);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Vendaproduto lista(String sql) {
		vendaProdutoDao = new VendaProdutoDao();
		try {
			return vendaProdutoDao.lista(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Vendaproduto> listar(String sql) {
		vendaProdutoDao = new VendaProdutoDao();
        try {
            return vendaProdutoDao.listar(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
