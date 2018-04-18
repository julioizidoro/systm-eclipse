/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;


import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Unidadenegocio;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class UnidadeNegocioDao {
    
    public Unidadenegocio salvar(Unidadenegocio unidadeNegocio) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        unidadeNegocio = manager.merge(unidadeNegocio);
        tx.commit();
        return unidadeNegocio;
    }
    
    public Unidadenegocio consultar(int idUnidadeNegocio) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Unidadenegocio unidadeNegocio = manager.find(Unidadenegocio.class, idUnidadeNegocio);
        return unidadeNegocio;
    }
    
    public List<Unidadenegocio> listar() throws Exception{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select u from Unidadenegocio u order by u.nomerelatorio");
        List<Unidadenegocio> lista = q.getResultList();
        return lista;
    }
    
    public List<Unidadenegocio> listar(boolean sitaucao) throws Exception{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select u from Unidadenegocio u where u.situacao=" + sitaucao + " order by u.nomerelatorio");
        List<Unidadenegocio> lista = q.getResultList();
        return lista;
    }
    
    public List<Unidadenegocio> listarContasPagar() throws Exception{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select u from Unidadenegocio u where u.cp=true order by u.nomeFantasia");
        List<Unidadenegocio> lista = q.getResultList();
        return lista;
    }
    
    public List<Unidadenegocio> listar(String nome) throws Exception{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select u from Unidadenegocio u where u.nomeFantasia like '%" + nome + "%' order by u.nomeFantasia");
        List<Unidadenegocio> lista = q.getResultList();
        return lista;
    }
    
    public List<Unidadenegocio> listarUnidade(String sql) throws Exception{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Unidadenegocio> lista = q.getResultList();
        return lista;
    }
    
}
