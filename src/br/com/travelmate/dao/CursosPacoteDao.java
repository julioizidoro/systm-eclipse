package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cursospacote;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class CursosPacoteDao {

	public Cursospacote salvar(Cursospacote cursospacote) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		cursospacote = manager.merge(cursospacote);
		tx.commit();
		return cursospacote;
	}

	public Cursospacote consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Cursospacote cursospacote = null;
		if (q.getResultList().size() > 0) {
			cursospacote = (Cursospacote) q.getResultList().get(0);
		}
		return cursospacote;
	}  

	public void excluir(int idcursospacote) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Cursospacote cursospacote = manager.find(Cursospacote.class, idcursospacote);
		manager.remove(cursospacote);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Cursospacote> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Cursospacote> fatura = null;
		if (q.getResultList().size() > 0) {
			fatura = q.getResultList();
		}
		return fatura;
	}

}
