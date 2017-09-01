package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.DadosAlteracoesDao;
import br.com.travelmate.model.Dadosalteracoes;

public class DadosAlteracoesFacade {
	
	DadosAlteracoesDao dadosAlteracoesDao;
	
	public Dadosalteracoes salvar(Dadosalteracoes dados) {
		dadosAlteracoesDao = new DadosAlteracoesDao();
		try {
			return dadosAlteracoesDao.salvar(dados);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Dadosalteracoes> listar(String sql){
		dadosAlteracoesDao = new DadosAlteracoesDao();
		try {
			return dadosAlteracoesDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
