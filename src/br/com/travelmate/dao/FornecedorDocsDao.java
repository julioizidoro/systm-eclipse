/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Fornecedordocs;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class FornecedorDocsDao {
    
    public Fornecedordocs salvar(Fornecedordocs fornecedordocs) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fornecedordocs = manager.merge(fornecedordocs);
        tx.commit();
        manager.close();
        return fornecedordocs;
    }
    
    public Fornecedordocs consultar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Fornecedordocs fornecedordocs = null;
        if (q.getResultList().size()>0){
        	fornecedordocs = (Fornecedordocs) q.getResultList().get(0);
        }
        manager.close();
        return fornecedordocs;
    }
    
    public void excluir(int idFornecedordocs) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedordocs fornecedordocs = manager.find(Fornecedordocs.class, idFornecedordocs);
        manager.remove(fornecedordocs);
        tx.commit();
        manager.close();
    }
    
    public List<Fornecedordocs> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Fornecedordocs> fornecedordocs = null;
        if (q.getResultList().size()>0){
        	fornecedordocs =  q.getResultList();
        }  
        manager.close();
        return fornecedordocs;  
    }
    
    
    
}
