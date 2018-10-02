package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.AlteracaofinanceiroparcelasDao;
import br.com.travelmate.model.Alteracaofinanceiroparcelas;

public class AlteracaofinanceiroparcelasFacade {
	
	AlteracaofinanceiroparcelasDao alteracaofinanceiroparcelasDao;
	
	public Alteracaofinanceiroparcelas salvar(Alteracaofinanceiroparcelas alteracaofinanceiroparcelas) {
		alteracaofinanceiroparcelasDao = new AlteracaofinanceiroparcelasDao();
		try {
			return alteracaofinanceiroparcelasDao.salvar(alteracaofinanceiroparcelas);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	public List<Alteracaofinanceiroparcelas> listar(String sql){
		alteracaofinanceiroparcelasDao = new AlteracaofinanceiroparcelasDao();
		try {
			return alteracaofinanceiroparcelasDao.listar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
	}

}
