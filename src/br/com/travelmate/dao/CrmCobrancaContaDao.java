package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.pool.Transactional;
import br.com.travelmate.model.Crmcobrancaconta;

public class CrmCobrancaContaDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	@Transactional
	public Crmcobrancaconta salvar(Crmcobrancaconta crmcobranca) {
		crmcobranca = manager.merge(crmcobranca);
        return crmcobranca;
    }
   
	@Transactional
    public Crmcobrancaconta consultar(int idcrm)  {
        Query q = manager.createQuery("SELECT c FROM Crmcobrancaconta c WHERE c.idcrmcobrancaconta=" + idcrm);
        Crmcobrancaconta crmcobranca = null; 
        if (q.getResultList().size() > 0) {
        	crmcobranca =  (Crmcobrancaconta) q.getResultList().get(0);
        } 
        return crmcobranca;
    }
    
	@Transactional
	public void excluir(int idcrm) {
		Crmcobrancaconta crmcobranca = manager.find(Crmcobrancaconta.class, idcrm);
        manager.remove(crmcobranca);
    }
    
	@Transactional
    public List<Crmcobrancaconta> lista(String sql) {
        Query q = manager.createQuery(sql);
        List<Crmcobrancaconta> lista = q.getResultList();
        return lista;
    }

}
