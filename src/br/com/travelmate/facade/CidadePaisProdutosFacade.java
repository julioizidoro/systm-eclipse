package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.CidadePaisProdutoDao;
import br.com.travelmate.model.Cidadepaisproduto;

public class CidadePaisProdutosFacade {
	
	private CidadePaisProdutoDao cidadePaisProdutoDao;
	
	public Cidadepaisproduto salvar(Cidadepaisproduto cidade) {
		cidadePaisProdutoDao = new CidadePaisProdutoDao();
		try {
			return cidadePaisProdutoDao.salvar(cidade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Cidadepaisproduto> listar(String sql){
		cidadePaisProdutoDao = new CidadePaisProdutoDao();
		try {
			return cidadePaisProdutoDao.listar(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void excluir(int idcidade) {
		cidadePaisProdutoDao = new CidadePaisProdutoDao();
		try {
			cidadePaisProdutoDao.excluir(idcidade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
