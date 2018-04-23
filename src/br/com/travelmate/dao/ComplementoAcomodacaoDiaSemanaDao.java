package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Complementoacomodacaodiasemana; 

public class ComplementoAcomodacaoDiaSemanaDao {
	
	public Complementoacomodacaodiasemana salvar(Complementoacomodacaodiasemana complemento) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		complemento = manager.merge(complemento);
		tx.commit();
		manager.close();
		return complemento;
	}
	
	public Complementoacomodacaodiasemana consultar(int idComplemento) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Complementoacomodacaodiasemana complemento = manager.find(Complementoacomodacaodiasemana.class, idComplemento); 
		tx.commit();
		manager.close();
		return complemento;
	}
	
	public Complementoacomodacaodiasemana consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Complementoacomodacaodiasemana complementoacomodacaodiasemana = null;
        if (q.getResultList().size()>0){
        	complementoacomodacaodiasemana =  (Complementoacomodacaodiasemana) q.getResultList().get(0);
        }
        manager.close();
        return complementoacomodacaodiasemana;
    }
    
	public void excluir(int id) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Complementoacomodacaodiasemana complementoacomodacaodiasemana = manager.find(Complementoacomodacaodiasemana.class, id);
        manager.remove(complementoacomodacaodiasemana);
        tx.commit();
        manager.close();
    }
}
