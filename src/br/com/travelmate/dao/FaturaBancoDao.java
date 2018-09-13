/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Faturabanco; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class FaturaBancoDao {
    
    public Faturabanco salvar(Faturabanco fatura) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fatura = manager.merge(fatura);
        tx.commit();
        manager.close();
        return fatura;
    }
    
    public Faturabanco getFatura(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Faturabanco fatura = null;
        if (q.getResultList().size()>0){
            fatura = (Faturabanco) q.getResultList().get(0);
        }
        manager.close();
        return fatura;
    }
    
    public void excluir(int idFatura) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Faturabanco fatura = manager.find(Faturabanco.class, idFatura);
        manager.remove(fatura);
        tx.commit();
        manager.close();
    }
    
    @SuppressWarnings("unchecked")
	public List<Faturabanco> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Faturabanco> fatura = null;
        if (q.getResultList().size()>0){
            fatura =  q.getResultList();
        }  
        return fatura;
    }
    
    
    
}
