package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Lead; 

public class LeadDao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	
	

	@SuppressWarnings("unchecked")
	public List<Lead> lista(String sql)  {
		Query q = manager.createQuery(sql);
		List<Lead> lista = null;
		if (q.getResultList().size() > 0) {
			lista = q.getResultList();
		}
		return lista;
	}

	public Lead consultar(String sql)  {
		Query q = manager.createQuery(sql);
		Lead lead = null;
		if (q.getResultList().size() > 0) {
			lead = (Lead) q.getResultList().get(0);
		}
		return lead;
	}

	@Transactional
	public Lead salvar(Lead lead)  {
		lead = manager.merge(lead);
		return lead;
	}

	@Transactional
	public void excluir(int idlead) {
    	Lead lead = manager.find(Lead.class, idlead);
        manager.remove(lead);
    }
	
	
	public Integer consultarNumLead(String sql)  {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Integer numero = null;
		Long numLong = null;
		if (q.getResultList().size() > 0) {
			numLong = (Long) q.getResultList().get(0);
		}
		numero = numLong.intValue();
		manager.close();
		return numero;
	}
}
