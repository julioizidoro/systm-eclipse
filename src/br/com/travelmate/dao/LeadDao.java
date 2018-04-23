package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Lead; 

public class LeadDao {

	
	
	
	public List<Lead> lista(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Lead> lista = null;
		if (q.getResultList().size() > 0) {
			lista = q.getResultList();
		}
		return lista;
	}

	public Lead consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Lead lead = null;
		if (q.getResultList().size() > 0) {
			lead = (Lead) q.getResultList().get(0);
		}
		manager.close();
		return lead;
	}

	public Lead salvar(Lead lead) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		lead = manager.merge(lead);
		tx.commit();
		manager.close();
		return lead;
	}

	public void excluir(int idlead) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Lead lead = manager.find(Lead.class, idlead);
        manager.remove(lead);
        tx.commit();
        manager.close();
    }
	
	
	public Integer consultarNumLead(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Integer numero = null;
		Long numLong = null;
		if (q.getResultList().size() > 0) {
			numLong = (Long) q.getResultList().get(0);
		}
		numero = numLong.intValue();
		manager.close();
		return numero;
	}
}
