package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.ControleAlteracoesDao;
import br.com.travelmate.model.Controlealteracoes;

public class ControleAlteracoesFacade {
	
	ControleAlteracoesDao controleAlteracoesDao;
	
	public Controlealteracoes salvar(Controlealteracoes alteracoes) {
		controleAlteracoesDao = new ControleAlteracoesDao();
		try {
			return controleAlteracoesDao.salvar(alteracoes);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Controlealteracoes> listar(String sql){
		controleAlteracoesDao = new ControleAlteracoesDao();
		try {
			return controleAlteracoesDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
