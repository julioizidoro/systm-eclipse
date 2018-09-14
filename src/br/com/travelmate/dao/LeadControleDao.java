package br.com.travelmate.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadcontrole;

public class LeadControleDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	
	@SuppressWarnings("unchecked")
	public List<Leadcontrole> lista(String sql)  {
		Query q = manager.createQuery(sql);
		List<Leadcontrole> lista = q.getResultList();
		return lista;
	}

	public Leadcontrole consultar(String sql)  {
		Query q = manager.createQuery(sql);
		Leadcontrole lead = null;
		if (q.getResultList().size() > 0) {
			lead = (Leadcontrole) q.getResultList().get(0);
		}
		return lead;
	}

	@Transactional
	public Leadcontrole salvar(Leadcontrole leadcontrole) throws SQLException {
		leadcontrole = manager.merge(leadcontrole);
		return leadcontrole;
	}

	@Transactional
	public void excluir(int idlead) throws SQLException{
    	Lead lead = manager.find(Lead.class, idlead);
        manager.remove(lead);
    }
	

}
