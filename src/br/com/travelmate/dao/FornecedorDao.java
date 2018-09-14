/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedor;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class FornecedorDao {
    
    public Fornecedor salvar(Fornecedor fornecedor) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        fornecedor = manager.merge(fornecedor);
        tx.commit();
        return fornecedor;
    }
    
    @SuppressWarnings("unchecked")
	public List<Fornecedor> listar(String sql) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Fornecedor> listaFornecedor = q.getResultList();
        return listaFornecedor;
    }
    
    public Fornecedor consultar(int idFornecedor) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getInstance();
         Query q = manager.createQuery("select v from Fornecedor  v where v.idfornecedor=" + idFornecedor);
         Fornecedor fornecedor = null;
         if(q.getResultList().size()>0){
             fornecedor =  (Fornecedor) q.getResultList().get(0);
         }
         return fornecedor;
     }
}
