package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.PontuacaoVendasDao;
import br.com.travelmate.model.Pontuacaovendas;

public class PontuacaoVendasFacade {
	
	private PontuacaoVendasDao pontuacaoVendasDao;
	
	public Pontuacaovendas salvar1(Pontuacaovendas pontuacao) {
		pontuacaoVendasDao = new PontuacaoVendasDao();
		try {
			return pontuacaoVendasDao.salvar(pontuacao);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	public List<Pontuacaovendas> listar(String sql) {
		pontuacaoVendasDao = new PontuacaoVendasDao();
		try {
			return pontuacaoVendasDao.listar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
	}

}
