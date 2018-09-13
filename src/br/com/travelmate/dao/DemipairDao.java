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
import br.com.travelmate.model.Controledemipair;
import br.com.travelmate.model.Demipair;
/**
 *
 * @author Wolverine
 */
public class DemipairDao {
    
    public Demipair salvar(Demipair demipair) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        demipair = manager.merge(demipair);
        manager.getTransaction().commit();
        manager.close();
        return demipair;
    }
    
    
    
    public Demipair consultar(int idVenda) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
         Query q = manager.createQuery("select d from Demipair d where d.vendas.idvendas=" + idVenda);
         Demipair demipair = null;
         if (q.getResultList().size() > 0) {
        	 demipair =  (Demipair) q.getResultList().get(0);
         }
         manager.close();
         return demipair;
     }
    
    public List<Demipair> lista(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Demipair> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    
    public Controledemipair salvar(Controledemipair controle) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        controle = manager.merge(controle);
        tx.commit();
        manager.close();
        return controle;
    }
    
    public List<Controledemipair> listaControle(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Controledemipair> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    
    public Controledemipair consultarControle(int idVenda) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controledemipair c where c.vendas.idvendas=" + idVenda);
        Controledemipair controle= null;
        if (q.getResultList().size() > 0) {
            controle = (Controledemipair) q.getResultList().get(0);
        } 
        manager.close();
        return controle;
    }
}
