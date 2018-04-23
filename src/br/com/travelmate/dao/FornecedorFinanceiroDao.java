/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Fornecedorfinanceiro;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class FornecedorFinanceiroDao {
    
    public Fornecedorfinanceiro salvar(Fornecedorfinanceiro fornecedorfinanceiro) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fornecedorfinanceiro = manager.merge(fornecedorfinanceiro);
        tx.commit();
        manager.close();
        return fornecedorfinanceiro;
    }
    
    public List<Fornecedorfinanceiro> listar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Fornecedorfinanceiro> listaFornecedorfinanceiro = q.getResultList();
        manager.close();
        return listaFornecedorfinanceiro;
    }
    
    public Fornecedorfinanceiro consulta(int idFornecedor) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("Select f from Fornecedorfinanceiro f where f.fornecedor.idfornecedor=" + idFornecedor);
        Fornecedorfinanceiro fornecedorfinanceiro = null;
        if (q.getResultList().size()>0){
        	fornecedorfinanceiro = (Fornecedorfinanceiro) q.getResultList().get(0);
        }
        manager.close();
        return fornecedorfinanceiro;
    } 
}
