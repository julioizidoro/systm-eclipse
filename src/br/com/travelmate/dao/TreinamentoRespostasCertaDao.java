package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Treinamentorespostacerta; 

public class TreinamentoRespostasCertaDao {
    
    public Treinamentorespostacerta salvar(Treinamentorespostacerta treinamentorespostacerta) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		treinamentorespostacerta = manager.merge(treinamentorespostacerta);
        tx.commit();
        
        return treinamentorespostacerta;
    }
    
    public List<Treinamentorespostacerta> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Treinamentorespostacerta> lista = q.getResultList();
        return lista;
    }
    
    public Treinamentorespostacerta consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Treinamentorespostacerta treinamentorespostacerta = null;
        if (q.getResultList().size() > 0) {
        	treinamentorespostacerta =   (Treinamentorespostacerta) q.getResultList().get(0);
        }
        return treinamentorespostacerta;
    }
     
}
