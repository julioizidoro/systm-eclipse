/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fatura; 
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class FaturaDao {
    
    public Fatura salvar(Fatura fatura) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fatura = manager.merge(fatura);
        tx.commit();
        manager.close();
        return fatura;
    }
    
    public Fatura getFatura(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Fatura fatura = null;
        if (q.getResultList().size()>0){
            fatura = (Fatura) q.getResultList().get(0);
        }
        manager.close();
        return fatura;
    }
    
    public void excluir(int idFatura) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fatura fatura = manager.find(Fatura.class, idFatura);
        manager.remove(fatura);
        tx.commit();
        manager.close();
    }
    
    @SuppressWarnings("unchecked")
	public List<Fatura> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Fatura> fatura = null;
        if (q.getResultList().size()>0){
            fatura =  q.getResultList();
        }  
        manager.close();
        return fatura;
    }
    
    
    
}
