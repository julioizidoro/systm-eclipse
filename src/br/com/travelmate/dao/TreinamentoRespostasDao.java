package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;   
import br.com.travelmate.model.Treinamentorespostas; 

public class TreinamentoRespostasDao {
    
    public Treinamentorespostas salvar(Treinamentorespostas treinamentorespostas) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		treinamentorespostas = manager.merge(treinamentorespostas);
        tx.commit();
        
        return treinamentorespostas;
    }
    
    public List<Treinamentorespostas> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Treinamentorespostas> lista = q.getResultList();
        return lista;
    }
    
    public Treinamentorespostas consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Treinamentorespostas treinamentorespostas = null;
        if (q.getResultList().size() > 0) {
        	treinamentorespostas =   (Treinamentorespostas) q.getResultList().get(0);
        }
        return treinamentorespostas;
    }
     
}
