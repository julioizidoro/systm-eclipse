/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Ocursoseguro;
   
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class OcursoSeguroViagemDao {
   
    
    public Ocursoseguro salvar(Ocursoseguro  ocursoseguro) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        ocursoseguro = manager.merge(ocursoseguro);
        tx.commit();
        manager.close();
        return ocursoseguro;
    }
    
    public Ocursoseguro consultar(int idOcurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("SELECT o FROM Ocursoseguro o where o.ocurso.idocurso=" + idOcurso);
        Ocursoseguro seguro = null;
        if (q.getResultList().size() > 0) {
            seguro =  (Ocursoseguro) q.getResultList().get(0);
        } 
        manager.close();
        return seguro;
    }
    
    public void excluir(int idOcursoseguro) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
        tx.begin();
        Query q = manager.createQuery("Select c from Ocursoseguro c where c.idocursoseguro=" + idOcursoseguro);
        if (q.getResultList().size()>0){
        	Ocursoseguro ocursoseguro = (Ocursoseguro) q.getResultList().get(0);   
            manager.remove(ocursoseguro);
        }    
        tx.commit();
        
    }
}
