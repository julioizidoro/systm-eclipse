/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Voluntariadoprojeto;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class VoluntariadoProjetoDao {
    
    public Voluntariadoprojeto salvar(Voluntariadoprojeto voluntariadoprojeto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		voluntariadoprojeto = manager.merge(voluntariadoprojeto);
        tx.commit();
        manager.close();
        return voluntariadoprojeto;
    }
    
    public Voluntariadoprojeto consultar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
 	    Query q = manager.createQuery(sql);
        Voluntariadoprojeto voluntariadoprojeto = null;
        if (q.getResultList().size()>0){
        	voluntariadoprojeto = (Voluntariadoprojeto) q.getResultList().get(0);
        }
        manager.close();
        return voluntariadoprojeto;
    }
    
    public void excluir(int idVoluntariadoprojeto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Voluntariadoprojeto fatura = manager.find(Voluntariadoprojeto.class, idVoluntariadoprojeto);
        manager.remove(fatura);
        tx.commit();
        manager.close();
    }
    
    public List<Voluntariadoprojeto> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Voluntariadoprojeto> lista = null;
        if (q.getResultList().size()>0){
        	lista =  q.getResultList();
        }  
        manager.close();
        return lista;
    }
    
    
    
}
