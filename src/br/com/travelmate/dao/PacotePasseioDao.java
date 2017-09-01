package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacotepasseio;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PacotePasseioDao {
    
    public Pacotepasseio salvar(Pacotepasseio pacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pacote = manager.merge(pacote);
        tx.commit();
        manager.close();
        return pacote;
    }
    
    public Pacotepasseio consultar(int idTrecho) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Pacotepasseio p where p.pacotetrecho.idpacotetrecho=" + idTrecho);
        Pacotepasseio pacote = null;
        if (q.getResultList().size() > 0) {
            pacote = (Pacotepasseio) q.getResultList().get(0);
        }
        manager.close();
        return pacote;
    }
    
    public void excluir(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacotepasseio pacote = manager.find(Pacotepasseio.class, idPacote);
        manager.remove(pacote);
        tx.commit();
        manager.close();
    }
    
     public List<Pacotepasseio> listar(String sql) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pacotepasseio> lista = null;
        if (q.getResultList().size()>0){
            lista = q.getResultList();
        }
        manager.close();
        return lista;
    }
}
