/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorfinanceiroproduto;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class FornecedorFinanceiroProdutoDao {
    
    public Fornecedorfinanceiroproduto salvar(Fornecedorfinanceiroproduto fornecedorfinanceiro) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fornecedorfinanceiro = manager.merge(fornecedorfinanceiro);
        tx.commit();
        return fornecedorfinanceiro;
    }
    
    public List<Fornecedorfinanceiroproduto> listar(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Fornecedorfinanceiroproduto> listaFornecedorfinanceiro = q.getResultList();
        return listaFornecedorfinanceiro;
    }
    
    public Fornecedorfinanceiroproduto consulta(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Fornecedorfinanceiroproduto fornecedorfinanceiro = null;
        if (q.getResultList().size()>0){
        	fornecedorfinanceiro = (Fornecedorfinanceiroproduto) q.getResultList().get(0);
        }
        return fornecedorfinanceiro;
    } 
    
    public void excluir(int idFornecedorfinanceiroproduto) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorfinanceiroproduto fornecedorfinanceiroproduto = manager.find(Fornecedorfinanceiroproduto.class, idFornecedorfinanceiroproduto);
        manager.remove(fornecedorfinanceiroproduto);
        tx.commit();
    }
}
