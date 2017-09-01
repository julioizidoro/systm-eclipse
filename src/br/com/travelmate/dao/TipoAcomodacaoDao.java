/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Tipoacomodacao;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class TipoAcomodacaoDao {
    
    public Tipoacomodacao salvar(Tipoacomodacao tipoacomodacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        tipoacomodacao = manager.merge(tipoacomodacao);
        tx.commit();
        manager.close();
        return tipoacomodacao;
    }
    
    public List<Tipoacomodacao> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Tipoacomodacao> lista = q.getResultList();
        manager.close();
        return lista;
    } 
    
    public Tipoacomodacao consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Tipoacomodacao tipoacomodacao = null;
        if (q.getResultList().size()>0){
        	tipoacomodacao =  (Tipoacomodacao) q.getResultList().get(0);
        }
        manager.close();
        return tipoacomodacao;
    }
    
    
    public void excluir(int id) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Tipoacomodacao tipo = manager.find(Tipoacomodacao.class, id);
        manager.remove(tipo);
        tx.commit();
        manager.close();
    }
}
