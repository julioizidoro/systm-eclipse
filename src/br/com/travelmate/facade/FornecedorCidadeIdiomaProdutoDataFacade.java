package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.FornecedorCidadeIdiomaProdutoDataDao;
import br.com.travelmate.model.Fornecedorcidadeidiomaprodutodata;

public class FornecedorCidadeIdiomaProdutoDataFacade {
	
	private FornecedorCidadeIdiomaProdutoDataDao fornecedorCidadeIdiomaProdutoDataDao;

	public Fornecedorcidadeidiomaprodutodata salvar(Fornecedorcidadeidiomaprodutodata fornecedorcidadeidiomaprodutodata) {
		fornecedorCidadeIdiomaProdutoDataDao = new FornecedorCidadeIdiomaProdutoDataDao();
		try {
			return fornecedorCidadeIdiomaProdutoDataDao.salvar(fornecedorcidadeidiomaprodutodata);
		} catch (SQLException ex) {
			Logger.getLogger(FornecedorCidadeIdiomaProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public List<Fornecedorcidadeidiomaprodutodata> listar(String sql) {
		fornecedorCidadeIdiomaProdutoDataDao = new FornecedorCidadeIdiomaProdutoDataDao();

		try {
			return fornecedorCidadeIdiomaProdutoDataDao.listar(sql);
		} catch (SQLException ex) {
			Logger.getLogger(FornecedorCidadeIdiomaProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public void excluir(int idFornecedorcidadeidiomaproduto) {
		fornecedorCidadeIdiomaProdutoDataDao = new FornecedorCidadeIdiomaProdutoDataDao();
		try {
			fornecedorCidadeIdiomaProdutoDataDao.excluir(idFornecedorcidadeidiomaproduto);
		} catch (SQLException ex) {
			Logger.getLogger(FornecedorCidadeIdiomaProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	
	
	
}
