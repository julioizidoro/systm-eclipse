package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Corridaprodutomes;

public class CorridaProdutoMesDao {

	
	public Corridaprodutomes salvar(Corridaprodutomes corridaprodutomes) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance(); 
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		corridaprodutomes = manager.merge(corridaprodutomes); 
		tx.commit();
		return corridaprodutomes;
	}
	    
	public Corridaprodutomes consultar(int idcorrida) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
        Corridaprodutomes corridaprodutomes = manager.find(Corridaprodutomes.class, idcorrida);
        
        return corridaprodutomes;
    }
	
	public Corridaprodutomes consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Corridaprodutomes corridaprodutomes = null;
        if (q.getResultList().size()>0){
        	corridaprodutomes = (Corridaprodutomes) q.getResultList().get(0);
        }
        return corridaprodutomes;
    }
	    
	public void excluir(int idcorrida) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Corridaprodutomes corridaprodutomes = manager.find(Corridaprodutomes.class, idcorrida);
		manager.remove(corridaprodutomes);
		tx.commit();
		
	}
	    
	public List<Corridaprodutomes> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Corridaprodutomes> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		
		return lista;
	}
}
