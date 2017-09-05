package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.pool.Transactional;
import br.com.travelmate.model.Pacotesinicial;

/**
 *
 * @author Kamila
 */
public class PacoteInicialDao implements Serializable{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	@Transactional
	public Pacotesinicial consultar(String sql)  {
		Query q = manager.createQuery(sql);
		Pacotesinicial pacotesinicial = null;
		if (q.getResultList().size() > 0) {
			pacotesinicial = (Pacotesinicial) q.getResultList().get(0);
		}
		return pacotesinicial;
	}   

	@Transactional
	public List<Pacotesinicial> listar(String sql)  {
		Query q = manager.createQuery(sql);
		List<Pacotesinicial> lista = null;
		if (q.getResultList().size() > 0) {
			lista = q.getResultList();
		}
		return lista;
	}

}
