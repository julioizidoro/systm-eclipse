/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cursotraducao; 
import java.sql.SQLException; 
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
/**
 *
 * @author Kamila
 */
public class CursoTraducaoDao {
    
    public Cursotraducao salvar(Cursotraducao cursotraducao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		cursotraducao = manager.merge(cursotraducao);
        tx.commit();
        manager.close();
        return cursotraducao;
    } 
     
    public Cursotraducao consultar(int idcurso) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Cursotraducao c where c.curso.idcursos="+idcurso);
        Cursotraducao cursotraducao = null;
        if (q.getResultList().size()>0){
        	cursotraducao = (Cursotraducao) q.getResultList().get(0);
        }
        manager.close();
        return cursotraducao;
    }
    
    
}
