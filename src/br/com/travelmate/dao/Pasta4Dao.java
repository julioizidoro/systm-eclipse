package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pasta4;

public class Pasta4Dao {
    
    public Pasta4 salvar(Pasta4 pasta4) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pasta4 = manager.merge(pasta4);
        tx.commit();
        
        return pasta4;
    }

    @SuppressWarnings("unchecked")
    public List<Pasta4> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Pasta4> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idpasta4) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Pasta4 c where c.idpasta4=" + idpasta4);
        if (q.getResultList().size()>0){
        	Pasta4 pasta4 = (Pasta4) q.getResultList().get(0);
            manager.remove(pasta4);
        }
        tx.commit();
        
    }
}
