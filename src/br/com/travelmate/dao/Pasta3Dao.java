package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pasta3;

public class Pasta3Dao {
    
    public Pasta3 salvar(Pasta3 pasta3) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pasta3 = manager.merge(pasta3);
        tx.commit();
        manager.close();
        return pasta3;
    }
    
    public List<Pasta3> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pasta3> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idpasta3) throws SQLException {
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Pasta3 c where c.idpasta3=" + idpasta3);
        if (q.getResultList().size()>0){
        	Pasta3 pasta3 = (Pasta3) q.getResultList().get(0);
            manager.remove(pasta3);
        }
        tx.commit();
        manager.close();
    }
}
