package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Crmcobrancaconta;

public class CrmCobrancaContaDao {
	
	public Crmcobrancaconta salvar(Crmcobrancaconta crmcobranca) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		crmcobranca = manager.merge(crmcobranca);
        tx.commit();
        manager.close();
        return crmcobranca;
    }
    
   
    
    public Crmcobrancaconta consultar(int idcrm) throws SQLException {
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("SELECT c FROM Crmcobrancaconta c WHERE c.idcrmcobrancaconta=" + idcrm);
        Crmcobrancaconta crmcobranca = null; 
        if (q.getResultList().size() > 0) {
        	crmcobranca =  (Crmcobrancaconta) q.getResultList().get(0);
        } 
        manager.close();
        return crmcobranca;
    }
    
    public void excluir(int idcrm) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Crmcobrancaconta crmcobranca = manager.find(Crmcobrancaconta.class, idcrm);
        manager.remove(crmcobranca);
        tx.commit();
        manager.close();
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Crmcobrancaconta> lista(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Crmcobrancaconta> lista = q.getResultList();
        manager.close();
        return lista;
    }

}
