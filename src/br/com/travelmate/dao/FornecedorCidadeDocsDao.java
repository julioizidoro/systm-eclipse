/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcidadedocs; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class FornecedorCidadeDocsDao {
    
    public Fornecedorcidadedocs salvar(Fornecedorcidadedocs fornecedorcidadedocs) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fornecedorcidadedocs = manager.merge(fornecedorcidadedocs);
        tx.commit();
        manager.close();
        return fornecedorcidadedocs;
    }
    
    public Fornecedorcidadedocs consultar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Fornecedorcidadedocs fornecedorcidadedocs = null;
        if (q.getResultList().size()>0){
        	fornecedorcidadedocs = (Fornecedorcidadedocs) q.getResultList().get(0);
        }
        manager.close();
        return fornecedorcidadedocs;
    }
    
    public void excluir(int idfornecedorcidadedocs) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorcidadedocs fornecedorcidadedocs = manager.find(Fornecedorcidadedocs.class, idfornecedorcidadedocs);
        manager.remove(fornecedorcidadedocs);
        tx.commit();
        manager.close();
    }
    
    public List<Fornecedorcidadedocs> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Fornecedorcidadedocs> lista = null;
        if (q.getResultList().size()>0){
        	lista =  q.getResultList();
        }  
        manager.close();
        return lista;  
    }
    
    
    
}
