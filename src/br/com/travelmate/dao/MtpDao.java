package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Mtp;

public class MtpDao {

	
	 public Mtp salvar(Mtp mtp) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			if (!tx.isActive()) {
				tx.begin();
			}
	        mtp = manager.merge(mtp);
	        tx.commit();
	        return mtp;
	    }
	    
	    public Mtp consultarMtp(int idMtp) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
	        Mtp mtp = manager.find(Mtp.class, idMtp);
	        return mtp;
	    }
	    
	    public void excluir(int idMtp) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			if (!tx.isActive()) {
				tx.begin();
			}
			Mtp mtp = manager.find(Mtp.class, idMtp);
	        manager.remove(mtp);
	        tx.commit();
	    }
	    
	 
	    
	    public List<Mtp> lista(String sql) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
	        Query q = manager.createQuery(sql);
	        List<Mtp> lista = q.getResultList();
	        return lista;
	    }
	    
	
	    
	 
	 
}
