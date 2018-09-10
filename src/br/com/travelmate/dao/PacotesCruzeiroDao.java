package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacotecruzeiro;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PacotesCruzeiroDao {
    
    public Pacotecruzeiro salvar(Pacotecruzeiro pacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pacote = manager.merge(pacote);
        tx.commit();
        manager.close();
        return pacote;
    }
    
    public Pacotecruzeiro consultar(int idTrecho) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Pacotecruzeiro p where p.pacotetrecho.idpacotetrecho=" + idTrecho);
        Pacotecruzeiro pacote = null;
        if (q.getResultList().size() > 0) {
            pacote = (Pacotecruzeiro) q.getResultList().get(0);
        } 
        manager.close();
        return pacote;
    }
    
    public void excluir(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacotecruzeiro pacote = manager.find(Pacotecruzeiro.class, idPacote);
        manager.remove(pacote);
        tx.commit();
        manager.close();
    }
}
