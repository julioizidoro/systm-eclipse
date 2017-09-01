/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Seguroplanos;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class SeguroPlanosDao {
    
    public Seguroplanos salvar(Seguroplanos seguroplanos) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		seguroplanos = manager.merge(seguroplanos);
        tx.commit();
        return seguroplanos;
    }
    
    public List<Seguroplanos> listar(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Seguroplanos> listaSeguroplanos = q.getResultList();
        return listaSeguroplanos;
    }
    
    public Seguroplanos consulta(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Seguroplanos seguroplanos = null;
        if (q.getResultList().size()>0){
        	seguroplanos = (Seguroplanos) q.getResultList().get(0);
        }
        return seguroplanos;
    } 
    
    public void excluir(int idSeguroplanos) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Seguroplanos seguroplanos = manager.find(Seguroplanos.class, idSeguroplanos);
        manager.remove(seguroplanos);
        tx.commit();
    }
}
