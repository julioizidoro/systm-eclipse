/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Ocrusoprodutos;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class OCursoProdutoDao {
    
    public Ocrusoprodutos salvar(Ocrusoprodutos produto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        produto = manager.merge(produto);
        tx.commit();
        manager.close();
        return produto;
    }
    
    public Ocrusoprodutos consultar(int idOcurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Ocursoprodutos p where p.ocurso.idocurso=" + idOcurso);
        Ocrusoprodutos produto = null;
        if (q.getResultList().size() > 0) {
            produto =   (Ocrusoprodutos) q.getResultList().get(0);
        }
        manager.close();
        return produto;
    }
    
    public List<Ocrusoprodutos> lista(int idOcurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Ocrusoprodutos p where p.ocurso.idocurso=" + idOcurso);
        List<Ocrusoprodutos> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Ocrusoprodutos> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Ocrusoprodutos> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idOcursoprodutos) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        tx.begin();
        Query q = manager.createQuery("Select c from Ocrusoprodutos c where c.idocrusoprodutos=" + idOcursoprodutos);
        if (q.getResultList().size()>0){
        	Ocrusoprodutos ocrusoprodutos = (Ocrusoprodutos) q.getResultList().get(0);   
            manager.remove(ocrusoprodutos);
        }    
        tx.commit();
        manager.close();
    }
}
