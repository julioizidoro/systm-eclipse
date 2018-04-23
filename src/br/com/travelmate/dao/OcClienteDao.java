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
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        ocCliente = manager.merge(ocCliente);
        tx.commit();
        manager.close();
        return ocCliente;
    }
    
    public Occliente consultar(int idocCliente) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Occliente ocCliente = manager.find(Occliente.class, idocCliente);
        manager.close();
        return ocCliente;
    }
    
    public Occliente consultarEmail(String email) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery("select c from Occliente c where c.email='" + email + "'" );
        Occliente cliente = null;
        if (q.getResultList().size()>0){
            cliente =  (Occliente) q.getSingleResult();
        } 
        manager.close();
        return cliente;
    }
    
    public List<Occliente> consultarNome(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Occliente> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Occliente> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Occliente> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
}
