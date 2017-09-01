/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacotecarro;
import br.com.travelmate.model.Pacotehotel;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class PacotesHotelDao {
    
    public Pacotehotel salvar(Pacotehotel pacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pacote = manager.merge(pacote);
        tx.commit();
        manager.close();
        return pacote;
    }
    
    public Pacotehotel consultar(int idTrecho) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Pacotehotel p where p.pacotetrecho.idpacotetrecho=" + idTrecho);
        Pacotehotel pacote = null;
        if (q.getResultList().size() > 0) {
            pacote = (Pacotehotel) q.getResultList().get(0);
        } 
        manager.close();
        return pacote;
    }
    
    public void excluir(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacotehotel pacote = manager.find(Pacotehotel.class, idPacote);
        manager.remove(pacote);
        tx.commit();
        manager.close();
    }
    
}
