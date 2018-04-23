package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Ocursopacote;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class OcursoPacoteDao {

	public Ocursopacote salvar(Ocursopacote ocursopacote) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		ocursopacote = manager.merge(ocursopacote);
		tx.commit();
		return ocursopacote;
	}

	public Ocursopacote consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Ocursopacote ocursopacote = null;
		if (q.getResultList().size() > 0) {
			ocursopacote = (Ocursopacote) q.getResultList().get(0);
		}
		manager.close();
		return ocursopacote;
	}  

	public void excluir(int idOcursopacote) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Ocursopacote ocursopacote = manager.find(Ocursopacote.class, idOcursopacote);
		manager.remove(ocursopacote);
		tx.commit();
		manager.close();
	}

	public List<Ocursopacote> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Ocursopacote> ocursopacote = null;
		if (q.getResultList().size() > 0) {
			ocursopacote = q.getResultList();
		}
		manager.close();
		return ocursopacote;
	}

}
