package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Pacotesinicial;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager; 
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class PacoteInicialDao { 

	public Pacotesinicial consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		Pacotesinicial pacotesinicial = null;
		if (q.getResultList().size() > 0) {
			pacotesinicial = (Pacotesinicial) q.getResultList().get(0);
		}
		return pacotesinicial;
	}   

	@SuppressWarnings("unchecked")
	public List<Pacotesinicial> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Pacotesinicial> lista = null;
		if (q.getResultList().size() > 0) {
			lista = q.getResultList();
		}
		return lista;
	}

}
