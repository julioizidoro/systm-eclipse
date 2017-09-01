/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Ocurso;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class OCursoDao {
    
    public Ocurso salvar(Ocurso  ocurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        ocurso = manager.merge(ocurso);
        tx.commit();
        manager.close();
        return ocurso;
    }
    
    public List<Ocurso> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        List lista = null;
        Query q = manager.createQuery(sql);
        lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Ocurso consultar(int idOcurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Ocurso ocurso = manager.find(Ocurso.class, idOcurso);
        manager.close();
        return ocurso;
    }
}
