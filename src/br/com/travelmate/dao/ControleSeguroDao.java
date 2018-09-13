package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controleseguro;

public class ControleSeguroDao {

	public Controleseguro controleSeguro(int idVendas) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Query q = manager.createQuery("select c from Controleseguro c where c.seguroviagem.vendas.idvendas=" + idVendas);
		Controleseguro controle = null;
		if (q.getResultList().size() > 0) {
			controle = (Controleseguro) q.getResultList().get(0);
		}
		tx.commit();
		manager.close();
		return controle;
	}

	@SuppressWarnings("unchecked")
	public List<Controleseguro> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Controleseguro> lista = q.getResultList();
		manager.close();
		return lista;
	}

	public Controleseguro salvarControle(Controleseguro controle) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		controle = manager.merge(controle);
		tx.commit();
		manager.close();
		return controle;
	}
}
