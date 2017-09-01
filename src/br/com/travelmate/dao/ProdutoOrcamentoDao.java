/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Produtosorcamento;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class ProdutoOrcamentoDao {
    
    public Produtosorcamento salvar(Produtosorcamento produto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        produto = manager.merge(produto);
        tx.commit();
        manager.close();
        return produto;
    }
    
    public List<Produtosorcamento> listarProdutosOrcamento(String descricao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Produtosorcamento p  where p.descricao like '" + descricao + "%' order by p.descricao" );
        List<Produtosorcamento> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Produtosorcamento> listarProdutosOrcamentoSql(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Produtosorcamento> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Produtosorcamento consultar(int idProdutoOrcamento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Produtosorcamento p where p.idprodutosOrcamento=" + idProdutoOrcamento + " order by p.descricao" );
        Produtosorcamento produto = null;
        if (q.getResultList().size()>0){
            produto =  (Produtosorcamento) q.getResultList().get(0);
        }
        manager.close();
        return produto;
    }
}
