package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.EventoContasPagarDao;
import br.com.travelmate.model.Eventocontaspagar;


public class EventoContasPagarFacade {
	
	private EventoContasPagarDao eventoContasPagarDao;
	
	public List<Eventocontaspagar> listar(int idcontaspagar) {
		eventoContasPagarDao = new EventoContasPagarDao();
		try {
			return eventoContasPagarDao.listar(idcontaspagar);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Eventocontaspagar salvar(Eventocontaspagar evento) {
		eventoContasPagarDao = new EventoContasPagarDao();
		try {
			return eventoContasPagarDao.salvar(evento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
