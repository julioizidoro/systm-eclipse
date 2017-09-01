package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacotecircuito;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class PacoteCircuitoDao {
    
    public Pacotecircuito salvar(Pacotecircuito pacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        manager.getTransaction().begin();
        pacote = manager.merge(pacote);
        tx.commit();
        manager.close();
        return pacote;
    }
    
    public Pacotecircuito consultar(int idTrecho) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
       Query q = manager.createQuery("select p from Pacotecircuito p where p.pacotetrecho.idpacotetrecho=" + idTrecho);
       Pacotecircuito pacote = null;
       if (q.getResultList().size() > 0) {
           pacote = (Pacotecircuito) q.getResultList().get(0);
       } 
       manager.close();
       return pacote;
    }
    
    public void excluir(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacotecircuito pacote = manager.find(Pacotecircuito.class, idPacote);
        manager.remove(pacote);
        tx.commit();
        manager.close();
    }
    
}
