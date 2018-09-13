/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Voluntariadoprojetoacomodacao;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class VoluntariadoProjetoAcomodacaoDao {
    
    public Voluntariadoprojetoacomodacao salvar(Voluntariadoprojetoacomodacao voluntariadoprojetoacomodacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		voluntariadoprojetoacomodacao = manager.merge(voluntariadoprojetoacomodacao);
        tx.commit();
        return voluntariadoprojetoacomodacao;
    }
    
    public Voluntariadoprojetoacomodacao consultar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
 		EntityTransaction tx = manager.getTransaction();
 		tx.begin();
        Query q = manager.createQuery(sql);
        Voluntariadoprojetoacomodacao voluntariadoprojetoacomodacao = null;
        if (q.getResultList().size()>0){
            voluntariadoprojetoacomodacao = (Voluntariadoprojetoacomodacao) q.getResultList().get(0);
        }
        tx.commit();
        return voluntariadoprojetoacomodacao;
    }
    
    public void excluir(int idVoluntariadoprojetoacomodacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Voluntariadoprojetoacomodacao voluntariadoprojetoacomodacao = manager.find(Voluntariadoprojetoacomodacao.class, idVoluntariadoprojetoacomodacao);
        manager.remove(voluntariadoprojetoacomodacao);
        tx.commit();
    }
    
    @SuppressWarnings("unchecked")
	public List<Voluntariadoprojetoacomodacao> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Voluntariadoprojetoacomodacao> lista = null;
        if (q.getResultList().size()>0){
        	lista =  q.getResultList();
        }  
        return lista;
    }
    
    
    
}
