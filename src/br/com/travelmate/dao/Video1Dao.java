package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Video1;

public class Video1Dao {

	public Video1 salvar(Video1 video1) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		video1 = manager.merge(video1);
        tx.commit();
        
        return video1;
    }
    
    public List<Video1> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Video1> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idvideo1) throws SQLException {
        EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Video1 v where v.idvideo1=" + idvideo1);
        if (q.getResultList().size()>0){
        	Video1 video1 = (Video1) q.getResultList().get(0);
            manager.remove(video1);
        }
        tx.commit();
        
    }
}
