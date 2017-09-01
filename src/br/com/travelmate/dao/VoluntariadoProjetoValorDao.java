/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Voluntariadoprojetovalor;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class VoluntariadoProjetoValorDao {
    
    public Voluntariadoprojetovalor salvar(Voluntariadoprojetovalor voluntariadoprojetovalor) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		voluntariadoprojetovalor = manager.merge(voluntariadoprojetovalor);
        tx.commit();
        return voluntariadoprojetovalor;
    }
    
    public Voluntariadoprojetovalor consultar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
 		EntityTransaction tx = manager.getTransaction();
 		tx.begin();
        Query q = manager.createQuery(sql);
        Voluntariadoprojetovalor voluntariadoprojetovalor = null;
        if (q.getResultList().size()>0){
            voluntariadoprojetovalor = (Voluntariadoprojetovalor) q.getResultList().get(0);
        }
        tx.commit();
        return voluntariadoprojetovalor;
    }
    
    public void excluir(int idVoluntariadoprojetovalor) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Voluntariadoprojetovalor voluntariadoprojetovalor = manager.find(Voluntariadoprojetovalor.class, idVoluntariadoprojetovalor);
        manager.remove(voluntariadoprojetovalor);
        tx.commit();
    }
    
    public List<Voluntariadoprojetovalor> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Voluntariadoprojetovalor> voluntariadoprojetovalors = null;
        if (q.getResultList().size()>0){
            voluntariadoprojetovalors =  q.getResultList();
        }  
        return voluntariadoprojetovalors;
    }
    
    
    
}
