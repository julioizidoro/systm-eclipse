package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadcontrole;

public class LeadControleDao {
	
	
	
	
	public List<Leadcontrole> lista(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Leadcontrole> lista = q.getResultList();

		return lista;
	}

	public Leadcontrole consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		Leadcontrole lead = null;
		if (q.getResultList().size() > 0) {
			lead = (Leadcontrole) q.getResultList().get(0);
		}
		return lead;
	}

	public Leadcontrole salvar(Leadcontrole leadcontrole) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		leadcontrole = manager.merge(leadcontrole);
		tx.commit();

		return leadcontrole;
	}

	public void excluir(int idlead) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Lead lead = manager.find(Lead.class, idlead);
        manager.remove(lead);
        tx.commit();
    }
	

}
