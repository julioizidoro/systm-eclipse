package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Complementoacomodacao; 

public class ComplementoAcomodacaoDao {
	
	public Complementoacomodacao salvar(Complementoacomodacao complemento) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		complemento = manager.merge(complemento);
		tx.commit();
		manager.close();
		return complemento;
	}
	
	public Complementoacomodacao consultar(int idComplemento) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Complementoacomodacao complemento = manager.find(Complementoacomodacao.class, idComplemento); 
		tx.commit();
		manager.close();
		return complemento;
	}
	
	public Complementoacomodacao consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Complementoacomodacao complementoacomodacao = null;
        if (q.getResultList().size()>0){
        	complementoacomodacao =  (Complementoacomodacao) q.getResultList().get(0);
        }
        manager.close();
        return complementoacomodacao;
    }
    
	public void excluir(int id) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Complementoacomodacao complementoacomodacao = manager.find(Complementoacomodacao.class, id);
        manager.remove(complementoacomodacao);
        tx.commit();
        manager.close();
    }
}
