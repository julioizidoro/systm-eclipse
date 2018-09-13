package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cartaocreditolancamentocontas;

public class CartaoCreditoLancamentoContasDao {
	
	    
	    
	    @SuppressWarnings("unchecked")
		public List<Cartaocreditolancamentocontas> listar(String sql) throws SQLException{
	    	EntityManager manager = ConectionFactory.getInstance();
	        Query q = manager.createQuery(sql);
	        List<Cartaocreditolancamentocontas> lista = q.getResultList();
	        return lista;
	    }
	    
	    public Cartaocreditolancamentocontas consultar(String sql) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
	        Query q = manager.createQuery(sql);
	        Cartaocreditolancamentocontas cartaocreditolancamentocontas = null;
	        if (q.getResultList().size()>0){
	        	cartaocreditolancamentocontas = (Cartaocreditolancamentocontas) q.getResultList().get(0);
	        }
	        return cartaocreditolancamentocontas;
	    }
	    
	    
	    
	    public Cartaocreditolancamentocontas salvar(Cartaocreditolancamentocontas cartaocreditolancamentocontas) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			cartaocreditolancamentocontas = manager.merge(cartaocreditolancamentocontas);
	        tx.commit();
	        return cartaocreditolancamentocontas;
	    }
	    
	    
	    public void excluir(int idCartaoCreditoLancamento) throws SQLException  {
			EntityManager manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			Cartaocreditolancamentocontas cartaocreditolancamentocontas = manager.find(Cartaocreditolancamentocontas.class, idCartaoCreditoLancamento);
	        manager.remove(cartaocreditolancamentocontas);  
	        tx.commit();
	        manager.close();
	    }

}
