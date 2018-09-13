/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controlevoluntariado;
import br.com.travelmate.model.Voluntariado;

/**
 *
 * @author Wolverine
 */
public class VoluntariadoDao {
    
    public Voluntariado salvar(Voluntariado voluntariado) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        voluntariado = manager.merge(voluntariado);
        tx.commit();
        manager.close();
        return voluntariado;
    }
    
   
    
    public Voluntariado consultar(int idVenda) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select v from Voluntariado v where v.vendas.idvendas=" + idVenda);
        Voluntariado voluntariado= null;
        if (q.getResultList().size() > 0) {
        	voluntariado = (Voluntariado) q.getResultList().get(0);
        } 
       manager.close();
        return voluntariado;
    }
    
    public void excluir(int idvoluntariado) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Voluntariado voluntariado = manager.find(Voluntariado.class, idvoluntariado);
        manager.remove(voluntariado);
        tx.commit();
        manager.close();
    }
    
    @SuppressWarnings("unchecked")
	public List<Voluntariado> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Voluntariado> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Controlevoluntariado salvar(Controlevoluntariado controle) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        controle = manager.merge(controle);
        tx.commit();
        manager.close();
        return controle;
    }
    
    @SuppressWarnings("unchecked")
	public List<Controlevoluntariado> listaControle(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Controlevoluntariado> lista = q.getResultList();
        manager.close();
        return lista;
    }
  
    public Controlevoluntariado consultarControle(int idVenda) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controlevoluntariado c where c.vendas.idvendas=" + idVenda);
        Controlevoluntariado voluntariado= null;
        if (q.getResultList().size() > 0) {
        	voluntariado = (Controlevoluntariado) q.getResultList().get(0);
        } 
        manager.close();
        return voluntariado;
    }
}
