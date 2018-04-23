package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Vendaspacote;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class VendasPacoteDao {

	public Vendaspacote salvar(Vendaspacote vendaspacote) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		vendaspacote = manager.merge(vendaspacote);
		tx.commit();
		manager.close();
		return vendaspacote;
	}

	public Vendaspacote consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Vendaspacote vendaspacote = null;
		if (q.getResultList().size() > 0) {
			vendaspacote = (Vendaspacote) q.getResultList().get(0);
		}
		manager.close();
		return vendaspacote;
	}  

	public void excluir(int idvendaspacote) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Vendaspacote vendaspacote = manager.find(Vendaspacote.class, idvendaspacote);
		manager.remove(vendaspacote);
		manager.close();
	}

	public List<Vendaspacote> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Vendaspacote> vendaspacote = null;
		if (q.getResultList().size() > 0) {
			vendaspacote = q.getResultList();
		}
		manager.close();
		return vendaspacote;
	}

}
