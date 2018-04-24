package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Tisolicitacoes;

public class TiSolicitacoesDao {
	
	
	 public Tisolicitacoes salvar(Tisolicitacoes tisolicitacoes) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			tisolicitacoes = manager.merge(tisolicitacoes);
	        tx.commit();
	        
	        return tisolicitacoes;
	    }
	    
	    public List<Tisolicitacoes> listar(String sql)throws SQLException{
	    		EntityManager manager;
	        manager = ConectionFactory.getInstance();
	        Query q = manager.createQuery(sql);
	        List<Tisolicitacoes> lista = q.getResultList();
	        return lista;
	    }
	    
	    public void excluir(int idtisolicitacoes) throws SQLException {
	    	EntityManager manager;
	    	manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select t from Tisolicitacoes t where t.idtisolicitacoes=" + idtisolicitacoes);
	        if (q.getResultList().size()>0){
	        	Tisolicitacoes tisolicitacoes = (Tisolicitacoes) q.getResultList().get(0);
	            manager.remove(tisolicitacoes);
	        }
	        tx.commit();
	        
	    }

}
