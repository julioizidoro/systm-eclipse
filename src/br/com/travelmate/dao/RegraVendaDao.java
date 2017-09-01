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
import br.com.travelmate.model.Regravenda;

public class RegraVendaDao {
    
    public Regravenda salvar(Regravenda regra) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        regra = manager.merge(regra);
        tx.commit();
        manager.close();
        return regra;
    }
    
    public Regravenda consultar(String sql) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
         Query q = manager.createQuery(sql);
         Regravenda regravenda = null;
         if (q.getResultList().size() > 0) {
        	 regravenda =  (Regravenda) q.getResultList().get(0);
         }
         manager.close();
         return regravenda;
     }
    
    public List<Regravenda> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Regravenda> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
}
