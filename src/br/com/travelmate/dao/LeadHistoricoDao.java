package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Leadhistorico; 

public class LeadHistoricoDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	
	@SuppressWarnings("unchecked")
	public List<Leadhistorico> lista(String sql)  {
		Query q = manager.createQuery(sql);
		List<Leadhistorico> lista = q.getResultList();
		return lista;
	}

	public Leadhistorico consultar(String sql)  {
		Query q = manager.createQuery(sql);
		Leadhistorico leadhistorico = null;
		if (q.getResultList().size() > 0) {
			leadhistorico = (Leadhistorico) q.getResultList().get(0);
		}
		return leadhistorico;
	}

	@Transactional
	public Leadhistorico salvar(Leadhistorico leadhistorico) {
		leadhistorico = manager.merge(leadhistorico);
		return leadhistorico;
	}
	
	@Transactional
	public void excluir(int idLeadhistorico) {
 		Leadhistorico leadhistorico = manager.find(Leadhistorico.class, idLeadhistorico);
        manager.remove(leadhistorico);
    }

}
