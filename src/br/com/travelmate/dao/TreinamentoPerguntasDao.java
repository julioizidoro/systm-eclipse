package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;  
import br.com.travelmate.model.Treinamentoperguntas; 

public class TreinamentoPerguntasDao {
    
    public Treinamentoperguntas salvar(Treinamentoperguntas treinamentoperguntas) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		treinamentoperguntas = manager.merge(treinamentoperguntas);
        tx.commit();
        
        return treinamentoperguntas;
    }
    
    public List<Treinamentoperguntas> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Treinamentoperguntas> lista = q.getResultList();
        return lista;
    }
    
    public Treinamentoperguntas consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Treinamentoperguntas treinamentoperguntas = null;
        if (q.getResultList().size() > 0) {
        	treinamentoperguntas =   (Treinamentoperguntas) q.getResultList().get(0);
        }
        return treinamentoperguntas;
    }
     
}
