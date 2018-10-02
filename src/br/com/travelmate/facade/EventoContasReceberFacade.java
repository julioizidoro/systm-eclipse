package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.EventoContasReceberDao;
import br.com.travelmate.model.Eventocontasreceber;

public class EventoContasReceberFacade {
	
	EventoContasReceberDao eventoContasReceberDao;
	
	public List<Eventocontasreceber> listar(int idcontasreceber) {
		eventoContasReceberDao = new EventoContasReceberDao();
		try {
			return eventoContasReceberDao.listar(idcontasreceber);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	public Eventocontasreceber salvar(Eventocontasreceber evento) {
		eventoContasReceberDao = new EventoContasReceberDao();
		try {
			return eventoContasReceberDao.salvar(evento);
		} catch (SQLException e) {
			  
			return null;
		}
	}

}
