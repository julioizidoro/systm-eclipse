package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Video2;

public class Video2Dao {

	
	public Video2 salvar(Video2 video2) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		video2 = manager.merge(video2);
        tx.commit();
        manager.close();
        return video2;
    }
    
    public List<Video2> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Video2> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idvideo2) throws SQLException {
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Video2 v where v.idvideo2=" + idvideo2);
        if (q.getResultList().size()>0){
        	Video2 video2 = (Video2) q.getResultList().get(0);
            manager.remove(video2);
        }
        tx.commit();
        manager.close();
    }
}
