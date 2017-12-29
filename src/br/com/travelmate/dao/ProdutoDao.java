/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Produtos;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class ProdutoDao {
    
    public Produtos salvar(Produtos produto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        produto = manager.merge(produto);
        tx.commit();
        manager.close();
        return produto;
    }
    
    public List<Produtos> listarProdutos(String descricao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Produtos p where p.descricao like '" + descricao + "%' and p.mostrar=1 order by p.descricao" );
        List<Produtos> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Produtos> listarProdutos() throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Produtos p order by p.descricao" );
        List<Produtos> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Produtos> listarProdutosSql(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Produtos> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Produtos consultar(int idProduto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Produtos produto = manager.find(Produtos.class, idProduto);
        manager.close();
        return produto;
    }
    
}
