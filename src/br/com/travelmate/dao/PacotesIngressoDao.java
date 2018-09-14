package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacoteingresso;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class PacotesIngressoDao {
    
    public Pacoteingresso salvar(Pacoteingresso pacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        //abrindo uma transação
		tx.begin();
        pacote = manager.merge(pacote);
        //fechando uma transação
        tx.commit();
        return pacote;
    }
    
    public Pacoteingresso consultar(int idTrecho) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Pacoteingresso p where p.pacotetrecho.idpacotetrecho=" + idTrecho);
        Pacoteingresso pacote = null;
        if (q.getResultList().size() > 0) {
            pacote = (Pacoteingresso) q.getResultList().get(0);
        } 
        manager.close();
        return pacote;
    }
    
    public void excluir(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacoteingresso pacote = manager.find(Pacoteingresso.class, idPacote);
        manager.remove(pacote);
        tx.commit();
        manager.close();
    }

    @SuppressWarnings("unchecked")
     public List<Pacoteingresso> listar(String sql) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pacoteingresso> lista = null;
        if (q.getResultList().size()>0){
            lista = q.getResultList();
        }
        manager.close();
        return lista;
    }
}
