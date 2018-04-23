package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pasta1;

public class Pasta1Dao {
    
    public Pasta1 salvar(Pasta1 pasta1) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pasta1 = manager.merge(pasta1);
        tx.commit();
        manager.close();
        return pasta1;
    }
    
    public List<Pasta1> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pasta1> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idpasta1) throws SQLException {
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Pasta1 c where c.idpasta1=" + idpasta1);
        if (q.getResultList().size()>0){
        	Pasta1 pasta1 = (Pasta1) q.getResultList().get(0);
            manager.remove(pasta1);
        }
        tx.commit();
        manager.close();
    }
}
