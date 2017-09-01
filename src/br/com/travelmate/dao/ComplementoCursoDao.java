package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Complementocurso;

public class ComplementoCursoDao {
	
	public Complementocurso salvar(Complementocurso complemento) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		complemento = manager.merge(complemento);
		tx.commit();
		return complemento;
	}
	
	public Complementocurso consultar(int idComplemento) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Complementocurso complemento = manager.find(Complementocurso.class, idComplemento); 
		tx.commit();
		return complemento;
	}

	public Complementocurso consultar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery(sql);
        Complementocurso complementocurso = null;
        if (q.getResultList().size()>0){
        	complementocurso =  (Complementocurso) q.getResultList().get(0);
        }
        tx.commit();
        return complementocurso;
    }
    
}
