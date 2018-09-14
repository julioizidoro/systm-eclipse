package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Versoes;

public class VersoesDao {

	
	public Versoes salvar(Versoes versoes) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		versoes = manager.merge(versoes);
		tx.commit();
		manager.close();
		return versoes;
	}

	public Versoes consultar(int idversoes) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery("select v from Versoes  v where v.idversoes=" + idversoes);
		Versoes versoes = null;
		if (q.getResultList().size() > 0) {
			versoes = (Versoes) q.getResultList().get(0);
		}
		manager.close();
		return versoes;
	}

    @SuppressWarnings("unchecked")
	public List<Versoes> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Versoes> lista = q.getResultList();
		manager.close();
		return lista;
	}
}
