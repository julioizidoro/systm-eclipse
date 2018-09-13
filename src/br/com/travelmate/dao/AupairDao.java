/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Controleaupair;
/**
 *
 * @author Wolverine
 */
public class AupairDao {
    
    
    public Aupair salvar(Aupair aupair) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        aupair = manager.merge(aupair);
        tx.commit();
        manager.close();
        return aupair;
    }
    
    
    
    public Aupair consultar(int idVenda) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
         Query q = manager.createQuery("select a from Aupair a where a.vendas.idvendas=" + idVenda);
         Aupair aupair = null;
         if (q.getResultList().size() > 0) {
        	 aupair =  (Aupair) q.getResultList().get(0);
         }
         tx.commit();
         manager.close();
         return aupair;
     }
    
    @SuppressWarnings("unchecked")
	public List<Aupair> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Aupair> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    
    public Controleaupair salvar(Controleaupair controle) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        controle = manager.merge(controle);
        tx.commit();
        manager.close();
        return controle;
    }
    
    @SuppressWarnings("unchecked")
	public List<Controleaupair> listaControle(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Controleaupair> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    
    public Controleaupair consultarControle(int idVenda) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("select c from Controleaupair c where c.vendas.idvendas=" + idVenda);
        Controleaupair aupair= null;
        if (q.getResultList().size() > 0) {
        	aupair = (Controleaupair) q.getResultList().get(0);
        } 
        tx.commit();
        manager.close();
        return aupair;
    }
}
