package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Recinternacional;

public class RecInternanionalDao {

	
	public Recinternacional salvar(Recinternacional recinternacional) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		recinternacional = manager.merge(recinternacional);
        tx.commit();
        
        return recinternacional;
    }
    
    public List<Recinternacional> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Recinternacional> lista = q.getResultList();
        return lista;
    }
    
    public void excluir(int idrecinternacional) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Recinternacional c where c.idrecinternacional=" + idrecinternacional);
        if (q.getResultList().size()>0){
        	Recinternacional recinternacional = (Recinternacional) q.getResultList().get(0);
            manager.remove(recinternacional);
        }
        tx.commit();
        
    }
}
