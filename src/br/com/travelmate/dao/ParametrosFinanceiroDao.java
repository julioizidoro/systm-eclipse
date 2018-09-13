package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Parametrosfinanceiro;

public class ParametrosFinanceiroDao {
	
	
	public Parametrosfinanceiro salvar(Parametrosfinanceiro parametrosfinanceiro) throws SQLException {
		EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        parametrosfinanceiro = manager.merge(parametrosfinanceiro);
        tx.commit();
        return parametrosfinanceiro;
	}
	
	public Parametrosfinanceiro consultar() throws SQLException {
		EntityManager manager;
        manager = ConectionFactory.getInstance();
		Query q = manager.createQuery("select p from Parametrosfinanceiro p");
        return (Parametrosfinanceiro) q.getSingleResult();
	}

}
