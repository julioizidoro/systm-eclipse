package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Crmcobranca;

public class CrmCobrancaDao {
	
	public Crmcobranca salvar(Crmcobranca crmcobranca) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		crmcobranca = manager.merge(crmcobranca);
        tx.commit();
        manager.close();
        return crmcobranca;
    }
    
   
    
    public Crmcobranca consultar(int idcrm) throws SQLException {
    		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("SELECT c FROM Crmcobranca c WHERE c.idcrmcobranca=" + idcrm);
        Crmcobranca crmcobranca = null; 
        if (q.getResultList().size() > 0) {
        	crmcobranca =  (Crmcobranca) q.getResultList().get(0);
        } 
        manager.close();
        return crmcobranca;
    }
    
    public void excluir(int idcrm) throws SQLException{
    		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Crmcobranca crmcobranca = manager.find(Crmcobranca.class, idcrm);
        manager.remove(crmcobranca);
        tx.commit();
        manager.close();
    }
    
    
    public List<Crmcobranca> lista(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Crmcobranca> lista = q.getResultList();
        manager.close();
        return lista;
    }

}
