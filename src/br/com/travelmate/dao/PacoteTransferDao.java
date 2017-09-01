package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacotetransfer;

import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PacoteTransferDao {
    
    public Pacotetransfer salvar(Pacotetransfer pacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pacote = manager.merge(pacote);
        tx.commit();
        manager.close();
        return pacote;
    }
    
    public Pacotetransfer consultar(int idTrecho) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
       Query q = manager.createQuery("select p from Pacotetransfer p where p.pacotetrecho.idpacotetrecho=" + idTrecho);
       Pacotetransfer pacote = null;
       if (q.getResultList().size() > 0) {
           pacote = (Pacotetransfer) q.getResultList().get(0);
       } 
       manager.close();
       return pacote;
    }
    
    public void excluir(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacotetransfer pacote = manager.find(Pacotetransfer.class, idPacote);
        manager.remove(pacote);
        tx.commit();
        manager.close();
    }
}
