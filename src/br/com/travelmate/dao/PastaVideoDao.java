package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pastavideo;

public class PastaVideoDao {

	public Pastavideo salvar(Pastavideo pastavideo) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		pastavideo = manager.merge(pastavideo);
		tx.commit();
		
		return pastavideo;
	}

	public List<Pastavideo> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Pastavideo> lista = q.getResultList();
		
		return lista;
	}

	public void excluir(int idpastavideo) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		manager.getTransaction().begin();
		Query q = manager.createQuery("Select p from Pastavideo p where p.idpastavideo=" + idpastavideo);
		if (q.getResultList().size() > 0) {
			Pastavideo pastavideo = (Pastavideo) q.getResultList().get(0);
			manager.remove(pastavideo);
		}
		tx.commit();
		
	}

}
