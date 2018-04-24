/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cliente;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class ClienteDao {
    
    public Cliente salvar(Cliente cliente) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        cliente = manager.merge(cliente);
        tx.commit();
        return cliente;
    }
    
    public Cliente consultar(int idCliente) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Cliente cliente = manager.find(Cliente.class, idCliente);
        return cliente;
    }
    
    public Cliente consultarEmail(String email) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
	    Query q = manager.createQuery("select c from Cliente c where c.email='" + email + "'" );
        Cliente cliente = null;
        if (q.getResultList().size()>0){
            cliente = (Cliente) q.getResultList().get(0);
        } 
        return cliente;
    }
    
    public Cliente consultarCpf(String cpf) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
	    Query q = manager.createQuery("select c from Cliente c where c.cpf='" + cpf + "'" );
        Cliente cliente = null;
        if (q.getResultList().size()>0){
            cliente =  (Cliente) q.getSingleResult();
        } 
        return cliente;
    }
       
    
    public Cliente consultarCpfLista(String cpf) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
	    Query q = manager.createQuery("select c from Cliente c where c.cpf='" + cpf + "'" );
        Cliente cliente = null;
        if (q.getResultList().size()>0){
            cliente =  (Cliente) q.getResultList().get(0);
        } 
        return cliente;
    }
    
    public List<Cliente> consultarNome(String nome) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select c from Cliente c where c.nome like '%" + nome + "%'" );
        List<Cliente> lista = q.getResultList();
        return lista;
    }
    
    public List<Cliente> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Cliente> lista = q.getResultList();
        return lista;
    }
    
}
