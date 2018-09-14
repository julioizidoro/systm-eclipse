package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Acesso; 
import br.com.travelmate.model.Grupoacesso;

public class GrupoAcessoDao {
   
    @SuppressWarnings("unchecked")
	public List<Grupoacesso> listar(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Grupoacesso> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Grupoacesso salvar(Grupoacesso grupoacesso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        grupoacesso = manager.merge(grupoacesso);
        tx.commit();
        manager.close();
        return grupoacesso;
    }
    
    
    public Acesso salvar(Acesso acesso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        acesso = manager.merge(acesso);
        tx.commit();
        manager.close();
        return acesso;
    }
	
}
