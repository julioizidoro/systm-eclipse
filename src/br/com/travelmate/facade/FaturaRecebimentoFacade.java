package br.com.travelmate.facade;

import java.sql.SQLException;

import br.com.travelmate.dao.FaturaRecebimentoDao;
import br.com.travelmate.model.Faturarecebimento;

public class FaturaRecebimentoFacade {
	
	private FaturaRecebimentoDao faturaRecebimentoDao;
	
	public Faturarecebimento salvar(Faturarecebimento faturaRecebimento) {
		faturaRecebimentoDao = new FaturaRecebimentoDao();
		try {
			return faturaRecebimentoDao.salvar(faturaRecebimento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
