package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Leadsituacao;

public class LeadSituacaoDao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	
	public List<Leadsituacao> lista(String sql)  {
		Query q = manager.createQuery(sql);
		List<Leadsituacao> lista = q.getResultList();
		return lista;
	}

	public Leadsituacao consultar(String sql)  {
		Query q = manager.createQuery(sql);
		Leadsituacao lead = null;
		if (q.getResultList().size() > 0) {
			lead = (Leadsituacao) q.getResultList().get(0);
		}
		return lead;
	}

	@Transactional
	public Leadsituacao salvar(Leadsituacao lead)  {
		lead = manager.merge(lead);
			return lead;
	}

	@Transactional
	public void excluir(int idlead) {
    	Leadsituacao lead = manager.find(Leadsituacao.class, idlead);
        manager.remove(lead);
    }

}
