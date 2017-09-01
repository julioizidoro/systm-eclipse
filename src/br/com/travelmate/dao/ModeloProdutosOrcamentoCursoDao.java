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
import br.com.travelmate.model.Modeloorcamentocurso;
import br.com.travelmate.model.Modeloorcamentocursoforma;
import br.com.travelmate.model.Modeloprodutoorcamentocurso;

/**
 *
 * @author Wolverine
 */
public class ModeloProdutosOrcamentoCursoDao {
    
    public Modeloorcamentocurso salvar(Modeloorcamentocurso orcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        orcamento = manager.merge(orcamento);
        tx.commit();
        manager.close();
        return orcamento;
    }
    
    public Modeloorcamentocurso consultar(int idOrcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Modeloorcamentocurso o where o.idorcamentoCurso=" + idOrcamento);
        Modeloorcamentocurso modelo = null;
        if (q.getResultList().size() > 0) {
            modelo=  (Modeloorcamentocurso) q.getResultList().get(0);
        }
        manager.close();
        return modelo;
    }
    
    public Modeloprodutoorcamentocurso salvar(Modeloprodutoorcamentocurso modeloProdutoOrcamentoCurso) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        modeloProdutoOrcamentoCurso = manager.merge(modeloProdutoOrcamentoCurso);
        tx.commit();
        manager.close();
        return modeloProdutoOrcamentoCurso;
    }
    
    public List<Modeloprodutoorcamentocurso> listarProdutoOrcamentoCurso(int idOrcamento) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Modeloprodutoorcamentocurso o where o.modeloOrcamentoCurso=" + idOrcamento);
        List<Modeloprodutoorcamentocurso> lista = q.getResultList();
        manager.close();
        return lista;
    }
        
    public void excluirModleoProdutoOrcamentoCurso(int idProdutoOrcamentoCurso) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Modeloprodutoorcamentocurso produtoorcamentocurso = manager.find(Modeloprodutoorcamentocurso.class, idProdutoOrcamentoCurso);
        manager.remove(produtoorcamentocurso);
        tx.commit();
        manager.close();
    }
    
    
    
    public Modeloprodutoorcamentocurso consultarProdutoOrcamentoCuros(int idProdutoOrcamentoCurso) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Modeloprodutoorcamentocurso produtoOrcamentoCurso = manager.find(Modeloprodutoorcamentocurso.class, idProdutoOrcamentoCurso);
        manager.close();
        return produtoOrcamentoCurso;
    }
    
    public Modeloorcamentocursoforma salvar(Modeloorcamentocursoforma modeloForma) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        modeloForma = manager.merge(modeloForma);
        manager.getTransaction().commit();
        manager.close();
        return modeloForma;
    }
     
     public Modeloorcamentocursoforma consultarFormaPagamento(int idOrcamento) throws SQLException{
    	 EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select o from Modeloorcamentocursoforma o where o.modeloOrcamentoCurso=" + idOrcamento);
        Modeloorcamentocursoforma forma = null;
        if (q.getResultList().size() > 0) {
            forma =  (Modeloorcamentocursoforma) q.getResultList().get(0);
        } 
        manager.close();
        return forma;
    }
     
    
    public List<Modeloorcamentocurso> listarModeloOrcamentoCurso(String sql) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Modeloorcamentocurso> lista = null;
        lista = q.getResultList();
        manager.close();
        return lista;
    }
}
