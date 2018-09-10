package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Acomodacaocurso;

public class AcomodacaoCursoDao {
	
	public Acomodacaocurso salvar(Acomodacaocurso acomodacaoCurso) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		acomodacaoCurso = manager.merge(acomodacaoCurso);
        tx.commit();
        manager.close();
        return acomodacaoCurso; 
    } 
	
	public void excluir(int idAcomodacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Acomodacaocurso acomodacaocurso = manager.find(Acomodacaocurso.class, idAcomodacao);
        manager.remove(acomodacaocurso);
        tx.commit();
        manager.close();
    }
	
	public Acomodacaocurso consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql); 
		Acomodacaocurso acomodacao = null; 
        if (q.getResultList().size() > 0) {
        		acomodacao =  (Acomodacaocurso) q.getResultList().get(0);
        } 
        manager.close();
        return acomodacao;
    }


}
