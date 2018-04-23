package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Remessaarquivo;

public class RemessaArquivoDao {

	
	public Remessaarquivo salvar(Remessaarquivo remessaarquivo) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		remessaarquivo = manager.merge(remessaarquivo);
		tx.commit();
		manager.close();
		return remessaarquivo;
	}

	public List<Remessaarquivo> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Remessaarquivo> lista = q.getResultList();
		manager.close();
		return lista;
	}

	public void excluir(int idremessaarquivo) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		manager.getTransaction().begin();
		Query q = manager.createQuery("Select r from Remessaarquivo r where r.idremessaarquivo=" + idremessaarquivo);
		if (q.getResultList().size() > 0) {
			Remessaarquivo remessaarquivo = (Remessaarquivo) q.getResultList().get(0);
			manager.remove(remessaarquivo);
		}
		tx.commit();
		manager.close();
	}
}
