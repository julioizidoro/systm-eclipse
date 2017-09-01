package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Leadhistorico; 

public class LeadHistoricoDao {

	public List<Leadhistorico> lista(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Leadhistorico> lista = q.getResultList();
		return lista;
	}

	public Leadhistorico consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		Leadhistorico leadhistorico = null;
		if (q.getResultList().size() > 0) {
			leadhistorico = (Leadhistorico) q.getResultList().get(0);
		}
		return leadhistorico;
	}

	public Leadhistorico salvar(Leadhistorico leadhistorico) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		leadhistorico = manager.merge(leadhistorico);
		tx.commit();
		return leadhistorico;
	}
	
	public void excluir(int idLeadhistorico) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Leadhistorico leadhistorico = manager.find(Leadhistorico.class, idLeadhistorico);
        manager.remove(leadhistorico);
        tx.commit();
    }

}
