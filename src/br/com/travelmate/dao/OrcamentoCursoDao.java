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
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.model.Orcamentocursoformapagamento;
import br.com.travelmate.model.Produtoorcamentocurso;
import br.com.travelmate.model.Produtosorcamento;
/**
 *
 * @author Wolverine
 */
public class OrcamentoCursoDao {
    
    public Orcamentocurso salvar(Orcamentocurso orcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        orcamento = manager.merge(orcamento);
        tx.commit();
        manager.close();
        return orcamento;
    }
    
    public Orcamentocurso consultar(int idOrcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Orcamentocurso o where o.idorcamentoCurso=" + idOrcamento);
        Orcamentocurso orcamento = null;
        if (q.getResultList().size() > 0) {
            orcamento = (Orcamentocurso) q.getResultList().get(0);
        } 
        manager.close();
        return orcamento;
    }
    
    public List<Orcamentocurso> listarOrcamento(String sql) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Orcamentocurso> lista = null;
        if (q.getResultList().size() > 0) {
            lista = q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public Produtoorcamentocurso salvar(Produtoorcamentocurso produtoOrcamentoCurso) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        produtoOrcamentoCurso = manager.merge(produtoOrcamentoCurso);
        tx.commit();
        manager.close();
        return produtoOrcamentoCurso;
    }
    
    public List<Produtoorcamentocurso> listarProdutoOrcamentoCurso(int idOrcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Produtoorcamentocurso o where o.orcamentocurso=" + idOrcamento);
        List<Produtoorcamentocurso> lista = null;
        if (q.getResultList().size() > 0) {
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public List<Produtoorcamentocurso> listarProdutoOrcamentoCurso(String sql) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Produtoorcamentocurso> lista = null;
        if (q.getResultList().size() > 0) {
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public Produtosorcamento consultarProdutoOrcamentoCurso(int idProdutoOrcamentoCurso) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Produtosorcamento p where p.idprodutosOrcamento=" + idProdutoOrcamentoCurso);
        Produtosorcamento produtoorcamento = null;
        if (q.getResultList().size() > 0) {
            produtoorcamento =  (Produtosorcamento) q.getResultList().get(0);
        }
        manager.close();
        return produtoorcamento;
    }
    
    public void excluirProdutoOrcamentoCurso(int idProdutoOrcamentoCurso) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Produtoorcamentocurso produtoorcamentocurso = manager.find(Produtoorcamentocurso.class, idProdutoOrcamentoCurso);
        manager.remove(produtoorcamentocurso);
        tx.commit();
        manager.close();
    }
    
    
    public Orcamentocurso consultarCliente(int idCliente) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Orcamentocurso o where o.cliente=" + idCliente +
                " and o.idCurso=0");
        Orcamentocurso orcamentocurso=null;
        if (q.getResultList().size() > 0) {
            orcamentocurso=  (Orcamentocurso) q.getResultList().get(0);
        }
        manager.close();
        return orcamentocurso;
    }
    
    public Produtoorcamentocurso consultarProdutoOrcamentoCuros(int idProdutoOrcamentoCurso) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Produtoorcamentocurso produtoOrcamentoCurso = manager.find(Produtoorcamentocurso.class, idProdutoOrcamentoCurso);
        manager.close();
        return produtoOrcamentoCurso;
    }
    
    public void excluirOrcamentoCuros(int idOrcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Orcamentocurso orcamento = manager.find(Orcamentocurso.class, idOrcamento);
        manager.remove(orcamento);
        tx.commit();
        manager.close();
    }
    
    public List<Orcamentocurso> listarOrcamentoCurso() throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("Select o from Orcamentocurso o where o.fornecedorcidade.idfornecedorcidade=1");
        List<Orcamentocurso> lista = null;
        if (q.getResultList().size() > 0) {
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public Orcamentocursoformapagamento salvarFormaPagamento(Orcamentocursoformapagamento orcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        orcamento = manager.merge(orcamento);
        tx.commit();
        manager.close();
        return orcamento;
    }
     
     public Orcamentocursoformapagamento consultarFormaPagamento(int idOrcamento) throws SQLException{
    	 EntityManager manager;
    	 manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Orcamentocursoformapagamento o where o.orcamentocurso.idorcamentoCurso=" + idOrcamento);
        Orcamentocursoformapagamento forma = null;
        if (q.getResultList().size() > 0) {
            forma =  (Orcamentocursoformapagamento) q.getResultList().get(0);
        }
        manager.close();
        return forma;
    }
}
