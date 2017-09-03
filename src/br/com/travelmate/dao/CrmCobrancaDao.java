package br.com.travelmate.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.pool.Transactional;
import br.com.travelmate.model.Crmcobranca;

public class CrmCobrancaDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	@Transactional
	public Crmcobranca salvar(Crmcobranca crmcobranca) {
    	crmcobranca = manager.merge(crmcobranca);
        return crmcobranca;
    }
    
   
	@Transactional
    public Crmcobranca consultar(int idcrm)  {
        Query q = manager.createQuery("SELECT c FROM Crmcobranca c WHERE c.idcrmcobranca=" + idcrm);
        Crmcobranca crmcobranca = null; 
        if (q.getResultList().size() > 0) {
        	crmcobranca =  (Crmcobranca) q.getResultList().get(0);
        } 
        return crmcobranca;
    }
    
	@Transactional
    public void excluir(int idcrm) {
		Crmcobranca crmcobranca = manager.find(Crmcobranca.class, idcrm);
        manager.remove(crmcobranca);
    }
    
	@Transactional
    public List<Crmcobranca> lista(String sql){
        Query q = manager.createQuery(sql);
        List<Crmcobranca> lista = q.getResultList();
        return lista;
    }

}
