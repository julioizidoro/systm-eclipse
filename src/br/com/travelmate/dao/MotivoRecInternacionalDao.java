package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Motivorecinternacional;

public class MotivoRecInternacionalDao {

	 public Motivorecinternacional salvar(Motivorecinternacional motivorecinternacional) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			motivorecinternacional = manager.merge(motivorecinternacional);
	        tx.commit();
	        
	        return motivorecinternacional;
	    }
	    
	    public List<Motivorecinternacional> listar(String sql)throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
	        Query q = manager.createQuery(sql);
	        List<Motivorecinternacional> lista = q.getResultList();
	        return lista;
	    }
	    
	    public void excluir(int idmotivo) throws SQLException {
	    	EntityManager manager;
	    	manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select c from Motivorecinternacional c where c.idmotivorecinternacional=" + idmotivo);
	        if (q.getResultList().size()>0){
	        	Motivorecinternacional motivorecinternacional = (Motivorecinternacional) q.getResultList().get(0);
	            manager.remove(motivorecinternacional);
	        }
	        tx.commit();
	        
	    }
}
