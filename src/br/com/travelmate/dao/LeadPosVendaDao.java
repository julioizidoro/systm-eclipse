package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Leadposvenda;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class LeadPosVendaDao {

	
	
	
	public Leadposvenda salvar(Leadposvenda leadposvenda) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		leadposvenda = manager.merge(leadposvenda);
		tx.commit();
		manager.clear();
		manager.close();
		return leadposvenda;
	}

	public Leadposvenda consultar(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Leadposvenda leadposvenda = null;
		if (q.getResultList().size() > 0) {
			leadposvenda = (Leadposvenda) q.getResultList().get(0);
		}
		manager.clear();
		manager.close();
		return leadposvenda;
	}  

	public void excluir(int idleadposvenda) throws SQLException {
		EntityManager manager= ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Leadposvenda leadposvenda = manager.find(Leadposvenda.class, idleadposvenda);
		manager.remove(leadposvenda);
		tx.commit();
		manager.clear();
		manager.close();
	}

	public List<Leadposvenda> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Leadposvenda> lista = null;
		if (q.getResultList().size() > 0) {
			lista = q.getResultList();
		}
		manager.clear();
		manager.close();
		return lista;
	}

}
