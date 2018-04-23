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
import br.com.travelmate.model.Motivocancelamento; 

public class MotivoCancelamentoDao {
    
    public Motivocancelamento salvar(Motivocancelamento motivo) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		motivo = manager.merge(motivo);
        tx.commit();
        manager.close();
        return motivo;
    }
    
    public Motivocancelamento consultar(String sql) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
         Query q = manager.createQuery(sql);
         Motivocancelamento motivo = null;
         if (q.getResultList().size() > 0) {
        	 motivo =  (Motivocancelamento) q.getResultList().get(0);
         }
         manager.close();
         return motivo;
     }
    
    public List<Motivocancelamento> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Motivocancelamento> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
}
