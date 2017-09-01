package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacotetrem;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class PacoteTremDao {
    
    public Pacotetrem salvar(Pacotetrem pacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pacote = manager.merge(pacote);
        tx.commit();
        manager.close();
        return pacote;
    }
    
    public Pacotetrem consultar(int idTrecho) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
       Query q = manager.createQuery("select p from Pacotetrem p where p.pacotetrecho.idpacotetrecho=" + idTrecho);
       Pacotetrem pacote = null;
       if (q.getResultList().size() > 0) {
           pacote = (Pacotetrem) q.getResultList().get(0);
       } 
       manager.close();
       return pacote;
    }
    
    public void excluir(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacotetrem pacote = manager.find(Pacotetrem.class, idPacote);
        manager.remove(pacote);
        tx.commit();
        manager.close();
    }
    
}
