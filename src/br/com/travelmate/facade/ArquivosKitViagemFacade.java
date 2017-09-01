package br.com.travelmate.facade;

import java.sql.SQLException;

import br.com.travelmate.dao.ArquivosKitViagemDao;
import br.com.travelmate.model.Arquivoskitviagem;

public class ArquivosKitViagemFacade {
	
	public Arquivoskitviagem salvar(Arquivoskitviagem kitViagem) {
		ArquivosKitViagemDao arquivosKitViagemDao = new ArquivosKitViagemDao();
		try {
			return arquivosKitViagemDao.salvar(kitViagem);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
