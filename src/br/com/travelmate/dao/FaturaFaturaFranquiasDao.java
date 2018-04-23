/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Faturafaturafraquias;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class FaturaFaturaFranquiasDao {
    
    public Faturafaturafraquias salvar(Faturafaturafraquias fatura) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fatura = manager.merge(fatura);
        tx.commit();
        manager.close();
        return fatura;
    }
    
    public Faturafaturafraquias getFatura(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Faturafaturafraquias fatura = null;
        if (q.getResultList().size()>0){
            fatura = (Faturafaturafraquias) q.getResultList().get(0);
        }
        manager.close();
        return fatura;
    }
    
    public void excluir(int idFatura) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Faturafaturafraquias fatura = manager.find(Faturafaturafraquias.class, idFatura);
        manager.remove(fatura);
        tx.commit();
        manager.close();
    }
    
    public List<Faturafaturafraquias> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Faturafaturafraquias> fatura = null;
        if (q.getResultList().size()>0){
            fatura =  q.getResultList();
        }  
        return fatura;
    }
    
    
    
}
