package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Funcao;

public class FuncaoDao {
	
	 public Funcao salvar(Funcao funcao) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			funcao = manager.merge(funcao);
	        tx.commit();
	        manager.close();
	        return funcao;
	    }
	    
	    public Funcao consultar(int idfuncao) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
			Funcao funcao = manager.find(Funcao.class, idfuncao);
			manager.close();
	        return funcao;
	    }
	    
	    public List<Funcao> listar(String sql)throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
	        Query q = manager.createQuery(sql);
	        List<Funcao> lista = q.getResultList();
	        manager.close();
	        return lista;
	    }
	    
	    public void excluir(int idfuncao) throws SQLException {
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select f from Funcao f where f.idfuncao=" + idfuncao);
	        if (q.getResultList().size()>0){
	        	Funcao funcao = (Funcao) q.getResultList().get(0);
	            manager.remove(funcao);
	        }
	        tx.commit();
	        manager.close();
	    }
	    
	    
	    public Funcao consultar(String sql) throws SQLException {
	    	Funcao funcao = null;
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
	        Query q = manager.createQuery(sql);
	        if (q.getResultList().size()>0){
	        	 funcao = (Funcao) q.getResultList().get(0);
	        }
	        manager.close();
	        return funcao;
	    }
	    
	    

}
