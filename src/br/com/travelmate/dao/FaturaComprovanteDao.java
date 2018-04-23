package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Faturacomprovante;

public class FaturaComprovanteDao {
	
	
	   public Faturacomprovante salvar(Faturacomprovante faturacomprovante) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			faturacomprovante = manager.merge(faturacomprovante);
	        tx.commit();
	        manager.close();
	        return faturacomprovante;
	    }
	    
	    public List<Faturacomprovante> listar(String sql)throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
	        Query q = manager.createQuery(sql);
	        List<Faturacomprovante> lista = q.getResultList();
	        manager.close();
	        return lista;
	    }
	    
	    public void excluir(int idfaturacomprovante) throws SQLException {
	    	EntityManager manager;
	    	manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select c from Faturacomprovante c where c.idfaturacomprovante=" + idfaturacomprovante);
	        if (q.getResultList().size()>0){
	        	Faturacomprovante faturacomprovante = (Faturacomprovante) q.getResultList().get(0);
	            manager.remove(faturacomprovante);
	        }
	        tx.commit();
	        manager.close();
	    }

}
