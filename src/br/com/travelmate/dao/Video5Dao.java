package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Video5;

public class Video5Dao {

	
	public Video5 salvar(Video5 video5) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		video5 = manager.merge(video5);
        tx.commit();
        manager.close();
        return video5;
    }
    
    public List<Video5> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Video5> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idvideo5) throws SQLException {
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Video5 v where v.idvideo5=" + idvideo5);
        if (q.getResultList().size()>0){
        	Video5 video5 = (Video5) q.getResultList().get(0);
            manager.remove(video5);
        }
        tx.commit();
        manager.close();
    }
}
