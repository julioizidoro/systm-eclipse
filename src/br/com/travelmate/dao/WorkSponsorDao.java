/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;   
import br.com.travelmate.model.Worksponsor;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class WorkSponsorDao {
    
    public Worksponsor salvar(Worksponsor worksponsor) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		worksponsor = manager.merge(worksponsor);
        tx.commit();
        return worksponsor;
    }
    
    public List<Worksponsor> listar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Worksponsor> lista = q.getResultList();
        return lista;
    }
    
    public Worksponsor consulta(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Worksponsor worksponsor = null;
        if (q.getResultList().size()>0){
        	worksponsor = (Worksponsor) q.getResultList().get(0);
        }
        return worksponsor;
    } 
}
