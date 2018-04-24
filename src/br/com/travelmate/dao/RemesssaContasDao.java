package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Remessacontas;

public class RemesssaContasDao {

	public Remessacontas salvar(Remessacontas remessacontas) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		remessacontas = manager.merge(remessacontas);
		tx.commit();
		
		return remessacontas;
	}

	public List<Remessacontas> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Remessacontas> lista = q.getResultList();
		
		return lista;
	}

	public void excluir(int idremessacontas) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		manager.getTransaction().begin();
		Query q = manager.createQuery("Select r from Remessacontas r where r.idremessacontas=" + idremessacontas);
		if (q.getResultList().size() > 0) {
			Remessacontas remessacontas = (Remessacontas) q.getResultList().get(0);
			manager.remove(remessacontas);
		}
		tx.commit();
		
	}
}
