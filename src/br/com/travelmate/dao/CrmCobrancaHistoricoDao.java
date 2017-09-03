package br.com.travelmate.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.pool.Transactional;
import br.com.travelmate.model.Crmcobrancahistorico;

public class CrmCobrancaHistoricoDao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	@Transactional
	public Crmcobrancahistorico salvar(Crmcobrancahistorico crmcobranca){
    	crmcobranca = manager.merge(crmcobranca);
        return crmcobranca;
    }
    
	@Transactional
    public Crmcobrancahistorico consultar(int idcrm) {
    	Query q = manager.createQuery("SELECT c FROM Crmcobrancahistorico c WHERE c.idcrmcobrancahistorico=" + idcrm);
        Crmcobrancahistorico crmcobranca = null; 
        if (q.getResultList().size() > 0) {
        	crmcobranca =  (Crmcobrancahistorico) q.getResultList().get(0);
        } 
        return crmcobranca;
    }
    
	@Transactional
    public void excluir(int idcrm) {
		Crmcobrancahistorico crmcobranca = manager.find(Crmcobrancahistorico.class, idcrm);
        manager.remove(crmcobranca);
        manager.close();
    }
    
	@Transactional
    public List<Crmcobrancahistorico> lista(String sql) {
        Query q = manager.createQuery(sql);
        List<Crmcobrancahistorico> lista = q.getResultList();
        return lista;
    }

}
