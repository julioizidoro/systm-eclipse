/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Workempregador; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class WorkEmpregadorDao {
    
    public Workempregador salvar(Workempregador workempregador) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		workempregador = manager.merge(workempregador);
        tx.commit();
        return workempregador;
    }

    @SuppressWarnings("unchecked")
    public List<Workempregador> listar(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Workempregador> lista = q.getResultList();
        return lista;
    }
    
    public Workempregador consulta(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Workempregador workempregador = null;
        if (q.getResultList().size()>0){
        	workempregador = (Workempregador) q.getResultList().get(0);
        }
        return workempregador;
    } 
}
