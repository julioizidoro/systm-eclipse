package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.CidadePaisProdutoDao;
import br.com.travelmate.dao.PaisProdutoDao;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Paisproduto;

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
	
	public Cidadepaisproduto consultar(String sql) {
		cidadePaisProdutoDao = new CidadePaisProdutoDao();
        try {
            return cidadePaisProdutoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CidadePaisProdutosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
