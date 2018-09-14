package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pasta5;

public class Pasta5Dao {
    
    public Pasta5 salvar(Pasta5 pasta5) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pasta5 = manager.merge(pasta5);
        tx.commit();
        
        return pasta5;
    }

    @SuppressWarnings("unchecked")
    public List<Pasta5> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Pasta5> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idpasta5) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Pasta5 c where c.idpasta5=" + idpasta5);
        if (q.getResultList().size()>0){
        	Pasta5 pasta5 = (Pasta5) q.getResultList().get(0);
            manager.remove(pasta5);
        }
        tx.commit();
        
    }
}
