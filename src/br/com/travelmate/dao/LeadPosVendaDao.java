package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Leadposvenda;

/**
 *
 * @author Kamila
 */
public class LeadPosVendaDao implements Serializable {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	@Transactional
	public Leadposvenda salvar(Leadposvenda leadposvenda)  {
		leadposvenda = manager.merge(leadposvenda);
		return leadposvenda;
	}

	public Leadposvenda consultar(String sql) {
		Query q = manager.createQuery(sql);
		Leadposvenda leadposvenda = null;
		if (q.getResultList().size() > 0) {
			leadposvenda = (Leadposvenda) q.getResultList().get(0);
		}
		return leadposvenda;
	}  

	@Transactional
	public void excluir(int idleadposvenda)  {
		Leadposvenda leadposvenda = manager.find(Leadposvenda.class, idleadposvenda);
		manager.remove(leadposvenda);
	}

	public List<Leadposvenda> listar(String sql)  {
		Query q = manager.createQuery(sql);
		List<Leadposvenda> lista = null;
		if (q.getResultList().size() > 0) {
			lista = q.getResultList();
		}
		return lista;
	}

}
