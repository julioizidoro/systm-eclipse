package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.dao.FornecedorCidadeIdiomaProdutoDao;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;

public class FornecedorCidadeIdiomaProdutoFacade {

	private FornecedorCidadeIdiomaProdutoDao fornecedorCidadeIdiomaProdutoDao;

	public Fornecedorcidadeidiomaproduto salvar(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		fornecedorCidadeIdiomaProdutoDao = new FornecedorCidadeIdiomaProdutoDao();
		try {
			return fornecedorCidadeIdiomaProdutoDao.salvar(fornecedorcidadeidiomaproduto);
		} catch (SQLException ex) {
			Logger.getLogger(FornecedorCidadeIdiomaProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public List<Fornecedorcidadeidiomaproduto> listar(String sql) {
		fornecedorCidadeIdiomaProdutoDao = new FornecedorCidadeIdiomaProdutoDao();

		try {
			return fornecedorCidadeIdiomaProdutoDao.listar(sql);
		} catch (SQLException ex) {
			Logger.getLogger(FornecedorCidadeIdiomaProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public void excluir(int idFornecedorcidadeidiomaproduto) {
		fornecedorCidadeIdiomaProdutoDao = new FornecedorCidadeIdiomaProdutoDao();
		try {
			fornecedorCidadeIdiomaProdutoDao.excluir(idFornecedorcidadeidiomaproduto);
		} catch (SQLException ex) {
			Logger.getLogger(FornecedorCidadeIdiomaProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public Fornecedorcidadeidiomaproduto consultar(String sql) {
		fornecedorCidadeIdiomaProdutoDao = new FornecedorCidadeIdiomaProdutoDao();
		try {
			return fornecedorCidadeIdiomaProdutoDao.consultar(sql);
		} catch (SQLException ex) {
			Logger.getLogger(FornecedorCidadeIdiomaProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public Fornecedorcidadeidiomaproduto consultarID(int idFornecedorcidadeidiomaproduto) {
		fornecedorCidadeIdiomaProdutoDao = new FornecedorCidadeIdiomaProdutoDao();
        try {
            return fornecedorCidadeIdiomaProdutoDao.consultar(idFornecedorcidadeidiomaproduto);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeIdiomaProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
