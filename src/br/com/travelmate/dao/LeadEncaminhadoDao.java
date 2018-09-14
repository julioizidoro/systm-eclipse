package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Leadencaminhado;

/**
 *
 * @author Kamila
 */
public class LeadEncaminhadoDao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	
	
	@Transactional
	public Leadencaminhado salvar(Leadencaminhado leadencaminhado) {
		leadencaminhado = manager.merge(leadencaminhado);
		return leadencaminhado;
	}

	public Leadencaminhado consultar(String sql)  {
		Query q = manager.createQuery(sql);
		Leadencaminhado leadencaminhado = null;
		if (q.getResultList().size() > 0) {
			leadencaminhado = (Leadencaminhado) q.getResultList().get(0);
		}
		return leadencaminhado;
	}  

	@Transactional
	public void excluir(int idleadencaminhado)  {
		Leadencaminhado leadencaminhado = manager.find(Leadencaminhado.class, idleadencaminhado);
		manager.remove(leadencaminhado);
	}

	@SuppressWarnings("unchecked")
	public List<Leadencaminhado> listar(String sql) {
		Query q = manager.createQuery(sql);
		List<Leadencaminhado> lista = null;
		if (q.getResultList().size() > 0) {
			lista = q.getResultList();
		}
		return lista;
	}

}
