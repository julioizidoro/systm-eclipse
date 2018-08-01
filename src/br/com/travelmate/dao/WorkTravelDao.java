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
import br.com.travelmate.model.Controlework;
import br.com.travelmate.model.Worktravel;

/**
 *
 * @author Wolverine
 */
public class WorkTravelDao {
    
    public Worktravel salvar(Worktravel work) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        work = manager.merge(work);
        tx.commit();
        return work;
    }
   
    
    public Worktravel consultarWork(int idVenda) throws SQLException {
    	EntityManager manager;
       manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select w from Worktravel w where w.vendas.idvendas=" + idVenda);
        Worktravel work = null;
        if (q.getResultList().size() > 0) {
            work =  (Worktravel) q.getResultList().get(0);
        }
        return work;
    }
    
    public List<Worktravel> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Worktravel> lista = q.getResultList();
        return lista;
    }
    
    
    public Controlework salvar(Controlework controle) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        controle = manager.merge(controle);
        tx.commit();
        return controle;
    }
    
    public List<Controlework> listaControle(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Controlework> lista = q.getResultList();
        return lista;
    }
    
    
    public Controlework consultarControle(int idVenda) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controlework c where c.vendas.idvendas=" + idVenda);
        Controlework work= null;
        if (q.getResultList().size() > 0) {
            work = (Controlework) q.getResultList().get(0);
        } 
        return work;
    }
}
