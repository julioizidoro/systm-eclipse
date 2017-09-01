/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Produtosorcamento;

/**
 *
 * @author Wolverine
 */
public class OrcamentoDao {
    
    public Orcamento salvar(Orcamento orcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        orcamento = manager.merge(orcamento);
        tx.commit();
        manager.close();
        return orcamento;
    }
    
    public Orcamento consultar(int idVenda) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Orcamento o where o.vendas.idvendas=" + idVenda);
        Orcamento orcamento = null;
        if (q.getResultList().size() > 0) {
            orcamento =  (Orcamento) q.getResultList().get(0);
        }
        manager.close();
        return orcamento;
    }
    
    public Orcamentoprodutosorcamento salvar(Orcamentoprodutosorcamento orcamentoprodutosorcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        orcamentoprodutosorcamento = manager.merge(orcamentoprodutosorcamento);
        tx.commit();
        manager.close();
        return orcamentoprodutosorcamento;
    }
    
    public List<Orcamentoprodutosorcamento> listarOrcamentoProdutoOrcamento(int idOrcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Orcamentoprodutosorcamento o where o.orcamento.idorcamento=" + idOrcamento);
        List<Orcamentoprodutosorcamento> lista = null;
        if (q.getResultList().size() > 0) {
        	lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public Produtosorcamento consultarProdutoOrcamento(int idProdutoOrcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Produtosorcamento p where p.idprodutosOrcamento=" + idProdutoOrcamento);
        Produtosorcamento produto = null;
        if (q.getResultList().size() > 0) {
            produto =  (Produtosorcamento) q.getResultList().get(0);
        }
        manager.close();
        return produto;
    }
    
    public void excluirOrcamentoProdutoOrcamento(int idOrcamentoprodutosorcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Orcamentoprodutosorcamento orcamentoprodutosorcamento = manager.find(Orcamentoprodutosorcamento.class, idOrcamentoprodutosorcamento);
        manager.remove(orcamentoprodutosorcamento);
        tx.commit();
        manager.close();
    }
    
    public void excluirOrcamento(int idOrcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Orcamento orcamento = manager.find(Orcamento.class, idOrcamento);
        manager.remove(orcamento);
        tx.commit();
        manager.close();
    }
    
    public Orcamentoprodutosorcamento consultarOrcamentoProdutoOrcamento(int idIrcamentoProdutosOrcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Orcamentoprodutosorcamento orcamentoprodutosorcamento = manager.find(Orcamentoprodutosorcamento.class, idIrcamentoProdutosOrcamento);
        manager.close();
        return orcamentoprodutosorcamento;
    }
    
    public List<Orcamentoprodutosorcamento> listarOrcamentoProdutoOrcamento() throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Orcamentoprodutosorcamento o");
        List<Orcamentoprodutosorcamento> lista = null;
        if (q.getResultList().size() > 0) {
        	lista = q.getResultList();
        } 
        manager.close();
        return lista;
    }
    
    
}
