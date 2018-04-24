package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Leadencaminhado;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class LeadEncaminhadoDao {

	
	
	
	public Leadencaminhado salvar(Leadencaminhado leadencaminhado) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		leadencaminhado = manager.merge(leadencaminhado);
		tx.commit();
		manager.clear();
		manager.close();
		return leadencaminhado;
	}

	public Leadencaminhado consultar(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Leadencaminhado leadencaminhado = null;
		if (q.getResultList().size() > 0) {
			leadencaminhado = (Leadencaminhado) q.getResultList().get(0);
		}
		manager.clear();
		manager.close();
		return leadencaminhado;
	}  

	public void excluir(int idleadencaminhado) throws SQLException {
		EntityManager manager= ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Leadencaminhado leadencaminhado = manager.find(Leadencaminhado.class, idleadencaminhado);
		manager.remove(leadencaminhado);
		tx.commit();
		manager.clear();
		manager.close();
	}

	public List<Leadencaminhado> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Leadencaminhado> lista = null;
		if (q.getResultList().size() > 0) {
			lista = q.getResultList();
		}
		manager.clear();
		manager.close();
		return lista;
	}

}
