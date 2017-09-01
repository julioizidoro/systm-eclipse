package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controleemail;

public class ControleEmailDao {

	
	 public Controleemail salvar(Controleemail controleemail) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			controleemail = manager.merge(controleemail);
	        tx.commit();
	        
	        return controleemail;
	    }
	    
	    public List<Controleemail> listar(String sql)throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
	        Query q = manager.createQuery(sql);
	        List<Controleemail> lista = q.getResultList();
	        return lista;
	    }
	    
	    public void excluir(int idControleemail) throws SQLException {
	    	EntityManager manager;
	    	manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select c from Controleemail c where c.idcontroleemail=" + idControleemail);
	        if (q.getResultList().size()>0){
	        	Controleemail controleemail = (Controleemail) q.getResultList().get(0);
	            manager.remove(controleemail);
	        }
	        tx.commit();
	        
	    }
}
