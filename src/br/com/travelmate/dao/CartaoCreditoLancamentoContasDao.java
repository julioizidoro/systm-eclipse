package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cartaocreditolancamentocontas;

public class CartaoCreditoLancamentoContasDao {
	
	    
	    
	    public List<Cartaocreditolancamentocontas> listar(String sql) throws SQLException{
	    	EntityManager manager = ConectionFactory.getConnection();
	        Query q = manager.createQuery(sql);
	        List<Cartaocreditolancamentocontas> lista = q.getResultList();
	        manager.close();
	        return lista;
	    }
	    
	    public Cartaocreditolancamentocontas consultar(String sql) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
	        Query q = manager.createQuery(sql);
	        Cartaocreditolancamentocontas cartaocreditolancamentocontas = null;
	        if (q.getResultList().size()>0){
	        	cartaocreditolancamentocontas = (Cartaocreditolancamentocontas) q.getResultList().get(0);
	        }
	        manager.close();
	        return cartaocreditolancamentocontas;
	    }
	    
	    
	    
	    public Cartaocreditolancamentocontas salvar(Cartaocreditolancamentocontas cartaocreditolancamentocontas) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			cartaocreditolancamentocontas = manager.merge(cartaocreditolancamentocontas);
	        tx.commit();
	        manager.close();
	        return cartaocreditolancamentocontas;
	    }
	    
	    
	    public void excluir(int idCartaoCreditoLancamento) throws SQLException  {
			EntityManager manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			Cartaocreditolancamentocontas cartaocreditolancamentocontas = manager.find(Cartaocreditolancamentocontas.class, idCartaoCreditoLancamento);
	        manager.remove(cartaocreditolancamentocontas);  
	        tx.commit();
	        manager.close();
	    }

}
