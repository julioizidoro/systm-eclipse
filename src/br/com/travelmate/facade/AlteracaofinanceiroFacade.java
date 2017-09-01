package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.AlteracaofinanceiroDao;
import br.com.travelmate.model.Alteracaofinanceiro;

public class AlteracaofinanceiroFacade {
	
	AlteracaofinanceiroDao alteracaofinanceiroDao;
	
	public Alteracaofinanceiro salvar(Alteracaofinanceiro alteracaofinanceiro) {
		alteracaofinanceiroDao = new AlteracaofinanceiroDao();
		try {
			return alteracaofinanceiroDao.salvar(alteracaofinanceiro);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Alteracaofinanceiro> listar(String sql){
		alteracaofinanceiroDao = new AlteracaofinanceiroDao();
		try {
			return alteracaofinanceiroDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
