/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Occliente;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class OcClienteDao {
    
    public Occliente salvar(Occliente ocCliente) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        ocCliente = manager.merge(ocCliente);
        tx.commit();
        return ocCliente;
    }
    
    public Occliente consultar(int idocCliente) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Occliente ocCliente = manager.find(Occliente.class, idocCliente);
        return ocCliente;
    }
    
    public Occliente consultarEmail(String email) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		Query q = manager.createQuery("select c from Occliente c where c.email='" + email + "'" );
        Occliente cliente = null;
        if (q.getResultList().size()>0){
            cliente =  (Occliente) q.getSingleResult();
        } 
        return null;
    }
    
    public List<Occliente> consultarNome(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Occliente> lista = q.getResultList();
        return lista;
    }
    
    public List<Occliente> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Occliente> lista = q.getResultList();
        return lista;
    }
    
}
