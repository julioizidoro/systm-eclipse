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
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		motivo = manager.merge(motivo);
        tx.commit();
        return motivo;
    }
    
    public Motivocancelamento consultar(String sql) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
         Query q = manager.createQuery(sql);
         Motivocancelamento motivo = null;
         if (q.getResultList().size() > 0) {
        	 motivo =  (Motivocancelamento) q.getResultList().get(0);
         }
         return motivo;
     }
    
    public List<Motivocancelamento> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Motivocancelamento> lista = q.getResultList();
        return lista;
    }
    
}
