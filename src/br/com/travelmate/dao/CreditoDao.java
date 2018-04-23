package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Credito;

public class CreditoDao {

	public Credito salvar(Credito credito) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		credito = manager.merge(credito);
		tx.commit();
		manager.close();
		return credito;
	}

	public List<Credito> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Credito> lista = q.getResultList();
		manager.close();
		return lista;
	}

	public void excluir(int idcredito) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Query q = manager.createQuery("Select c from Credito c where c.idcredito=" + idcredito);
		if (q.getResultList().size() > 0) {
			Credito credito = (Credito) q.getResultList().get(0);
			manager.remove(credito);
		}
		tx.commit();
		manager.close();
	}

}
