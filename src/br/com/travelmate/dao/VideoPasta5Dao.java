package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Videopasta5;

public class VideoPasta5Dao {

	
	public Videopasta5 salvar(Videopasta5 videopasta5) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		videopasta5 = manager.merge(videopasta5);
        tx.commit();
        manager.close();
        return videopasta5;
    }
    
    public List<Videopasta5> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Videopasta5> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idvideopasta5) throws SQLException {
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Videopasta5 v where v.idvideopasta5=" + idvideopasta5);
        if (q.getResultList().size()>0){
        	Videopasta5 videopasta5 = (Videopasta5) q.getResultList().get(0);
            manager.remove(videopasta5);
        }
        tx.commit();
        manager.close();
    }
}
