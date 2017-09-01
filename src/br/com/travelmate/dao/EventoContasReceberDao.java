package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Eventocontasreceber;

public class EventoContasReceberDao {
	
	public List<Eventocontasreceber> listar(int idcontasreceber) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("Select e Eventocontasreceber e where e.contasreceber.idcontasreceber=" + idcontasreceber);
        List<Eventocontasreceber> lista = q.getResultList();
        manager.close();
        return lista;
    }
	
	public Eventocontasreceber salvar(Eventocontasreceber evento) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		manager.getTransaction().begin();
        evento = manager.merge(evento);
        manager.getTransaction().commit();
        manager.close();
        return evento;
    }

}
