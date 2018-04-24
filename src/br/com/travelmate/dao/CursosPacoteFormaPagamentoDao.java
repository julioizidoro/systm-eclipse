package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cursopacoteformapagamento;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class CursosPacoteFormaPagamentoDao {

	public Cursopacoteformapagamento salvar(Cursopacoteformapagamento formapagamento) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		formapagamento = manager.merge(formapagamento);
		tx.commit();
		return formapagamento;
	}

	public Cursopacoteformapagamento consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		Cursopacoteformapagamento formapagamento = null;
		if (q.getResultList().size() > 0) {
			formapagamento = (Cursopacoteformapagamento) q.getResultList().get(0);
		}
		return formapagamento;
	}

	public void excluir(int idformapagamento) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Cursopacoteformapagamento formapagamento = manager.find(Cursopacoteformapagamento.class, idformapagamento);
		manager.remove(formapagamento);
		tx.commit();
	}

	public List<Cursopacoteformapagamento> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Cursopacoteformapagamento> formapagamento = null;
		if (q.getResultList().size() > 0) {
			formapagamento = q.getResultList();
		}
		return formapagamento;
	}

}
