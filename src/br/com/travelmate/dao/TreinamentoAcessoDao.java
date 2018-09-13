package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Treinamentoacesso;

public class TreinamentoAcessoDao {
    
    public Treinamentoacesso salvar(Treinamentoacesso treinamentoacesso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		treinamentoacesso = manager.merge(treinamentoacesso);
        tx.commit();
        
        return treinamentoacesso;
    }
    
    @SuppressWarnings("unchecked")
	public List<Treinamentoacesso> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Treinamentoacesso> lista = q.getResultList();
        return lista;
    }
    
    public Treinamentoacesso consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Treinamentoacesso treinamentoacesso = null;
        if (q.getResultList().size() > 0) {
        	treinamentoacesso =   (Treinamentoacesso) q.getResultList().get(0);
        }
        return treinamentoacesso;
    }
     
}
