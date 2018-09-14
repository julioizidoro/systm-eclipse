package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Corridaprodutoano;

public class CorridaProdutoAnoDao {

	
	public Corridaprodutoano salvar(Corridaprodutoano corridaprodutoano) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance(); 
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		corridaprodutoano = manager.merge(corridaprodutoano); 
		tx.commit();
		return corridaprodutoano;
	}
	    
	public Corridaprodutoano consultar(int idcorrida) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
        Corridaprodutoano corridaprodutoano = manager.find(Corridaprodutoano.class, idcorrida);
        
        return corridaprodutoano;
    }
	
	public Corridaprodutoano consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Corridaprodutoano corridaprodutoano = null;
        if (q.getResultList().size()>0){
        	corridaprodutoano = (Corridaprodutoano) q.getResultList().get(0);
        }
        return corridaprodutoano;
    }
	    
	public void excluir(int idcorrida) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Corridaprodutoano corridaprodutoano = manager.find(Corridaprodutoano.class, idcorrida);
		manager.remove(corridaprodutoano);
		tx.commit();
		
	}
	    
	@SuppressWarnings("unchecked")
	public List<Corridaprodutoano> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Corridaprodutoano> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		
		return lista;
	}
}
