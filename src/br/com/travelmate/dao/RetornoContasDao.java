package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Retornocontas;

public class RetornoContasDao {

	
	public Retornocontas salvar(Retornocontas retornocontas) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		retornocontas = manager.merge(retornocontas);
		tx.commit();
		
		return retornocontas;
	}

    @SuppressWarnings("unchecked")
	public List<Retornocontas> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Retornocontas> lista = q.getResultList();
		
		return lista;
	}

	public void excluir(int idretornocontas) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Query q = manager.createQuery("Select r from Retornocontas r where r.idretornocontas=" + idretornocontas);
		if (q.getResultList().size() > 0) {
			Retornocontas retornocontas = (Retornocontas) q.getResultList().get(0);
			manager.remove(retornocontas);
		}
		tx.commit();
		
	}
}
