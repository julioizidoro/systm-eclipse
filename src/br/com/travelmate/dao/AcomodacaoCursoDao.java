package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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

}