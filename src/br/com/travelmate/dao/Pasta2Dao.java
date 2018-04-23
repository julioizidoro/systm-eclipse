package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pasta2;

public class Pasta2Dao {
    
    public Pasta2 salvar(Pasta2 pasta2) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pasta2 = manager.merge(pasta2);
        tx.commit();
        manager.close();
        return pasta2;
    }
    
    public List<Pasta2> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pasta2> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idpasta2) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        tx.begin();
        Query q = manager.createQuery("Select c from Pasta2 c where c.idpasta2=" + idpasta2);
        if (q.getResultList().size()>0){
        	Pasta2 pasta2 = (Pasta2) q.getResultList().get(0);
            manager.remove(pasta2);
        }
        tx.commit();
        manager.close();
    }
}
