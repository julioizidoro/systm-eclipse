package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Tmstar;

public class TmStarDao {

	public List<Tmstar> lista() throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery("select t from Tmstar t");
		List<Tmstar> lista = q.getResultList();
		
		return lista;
	}

	public Tmstar consultar(int idtmstars) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Tmstar tmstars = manager.find(Tmstar.class, idtmstars);
		
		return tmstars;
	}

	public Tmstar salvar(Tmstar tmstars) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		tmstars = manager.merge(tmstars);
		tx.commit();
		
		return tmstars;
	}

	public void excluir(int idTmstar) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Query q = manager.createQuery("select t from Tmstar t where t.idtmstar=" + idTmstar);
		if (q.getResultList().size() > 0) {
			Tmstar tmstars = (Tmstar) q.getResultList().get(0);
			manager.remove(tmstars);
		}
		tx.commit();
		
	}
}
