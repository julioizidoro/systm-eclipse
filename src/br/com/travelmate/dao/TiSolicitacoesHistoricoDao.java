package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Tisolicitacoeshistorico;

public class TiSolicitacoesHistoricoDao {

	
	 public Tisolicitacoeshistorico salvar(Tisolicitacoeshistorico tisolicitacoeshistorico) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			tisolicitacoeshistorico = manager.merge(tisolicitacoeshistorico);
	        tx.commit();
	        manager.close();
	        return tisolicitacoeshistorico;
	    }
	    
	    public List<Tisolicitacoeshistorico> listar(String sql)throws SQLException{
	    		EntityManager manager;
	        manager = ConectionFactory.getConnection();
	        Query q = manager.createQuery(sql);
	        List<Tisolicitacoeshistorico> lista = q.getResultList();
	        manager.close();
	        return lista;
	    }
	    
	    public void excluir(int idtisolicitacoeshistorico) throws SQLException {
	    	EntityManager manager;
	    	manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select t from Tisolicitacoeshistorico t where t.idtisolicitacoeshistorico=" + idtisolicitacoeshistorico);
	        if (q.getResultList().size()>0){
	        	Tisolicitacoeshistorico tisolicitacoeshistorico = (Tisolicitacoeshistorico) q.getResultList().get(0);
	            manager.remove(tisolicitacoeshistorico);
	        }
	        tx.commit();
	        manager.close();
	    }
}
