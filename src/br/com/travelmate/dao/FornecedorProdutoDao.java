/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Fornecedorproduto;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class FornecedorProdutoDao {
    
    public Fornecedorproduto salvar(Fornecedorproduto fornecedor) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        fornecedor = manager.merge(fornecedor);
        tx.commit();
        manager.close();
        return fornecedor;
    }
    
    public List<Fornecedorproduto> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Fornecedorproduto> listaFornecedor = q.getResultList();
        manager.close();
        return listaFornecedor;
    }
    
    public Fornecedorproduto consultar(int idFornecedor) throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
         Query q = manager.createQuery("select f from Fornecedorproduto  f where f.idfornecedorproduto=" + idFornecedor);
         Fornecedorproduto fornecedor = null;
         if(q.getResultList().size()>0){
             fornecedor =  (Fornecedorproduto) q.getResultList().get(0);
         }
         manager.close();
         return fornecedor;  
     }
}
