package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Eventocontaspagar;


public class EventoContasPagarDao {
	
	@SuppressWarnings("unchecked")
	public List<Eventocontaspagar> listar(int idcontaspagar) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("Select e Eventocontasreceber e where e.contasreceber.idcontasreceber=" + idcontaspagar);
        List<Eventocontaspagar> lista = q.getResultList();
        manager.close();
        return lista;
    }
	
	public Eventocontaspagar salvar(Eventocontaspagar evento) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        evento = manager.merge(evento);
        tx.commit();
        manager.close();
        return evento;
    }

}
