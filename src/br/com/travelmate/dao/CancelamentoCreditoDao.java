package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Cancelamentocredito;

public class CancelamentoCreditoDao {
	
	public Cancelamentocredito salvar(Cancelamentocredito cancelamentocredito) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		cancelamentocredito = manager.merge(cancelamentocredito);
		tx.commit();

		return cancelamentocredito;
	}

	@SuppressWarnings("unchecked")
	public List<Cancelamentocredito> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Cancelamentocredito> lista = q.getResultList();
		return lista;
	}

	public void excluir(int idcancelamentocredito) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Query q = manager.createQuery(
				"Select c from Cancelamentocredito c where c.idcancelamentocredito=" + idcancelamentocredito);
		if (q.getResultList().size() > 0) {
			Arquivo1 arquivo1 = (Arquivo1) q.getResultList().get(0);
			manager.remove(arquivo1);
		}
		tx.commit();

	}

}
