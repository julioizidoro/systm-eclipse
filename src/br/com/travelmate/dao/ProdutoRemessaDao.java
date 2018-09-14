/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Produtoremessa;

/**
 *
 * @author Wolverine
 */
public class ProdutoRemessaDao {

    @SuppressWarnings("unchecked")
    public List<Produtoremessa> listar(int idProduto) throws Exception{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        List<Produtoremessa> listaProdutos = null;
        Query q = manager.createQuery("select p from Produtoremessa p where p.produtos.idprodutos=" + idProduto);
        listaProdutos = q.getResultList();
        manager.close();
        return listaProdutos;
    }
    
    public Produtoremessa salvar(Produtoremessa produtoRemessa) throws Exception{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        produtoRemessa = manager.merge(produtoRemessa);
        tx.commit(); 
        manager.close();
        return produtoRemessa;
    }

    public void excluir(int idProdutoRemessa) throws Exception{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Produtoremessa produtoRemessa = manager.find(Produtoremessa.class, idProdutoRemessa);
        manager.remove(produtoRemessa);
        tx.commit(); 
        manager.close();
    }
    
}
