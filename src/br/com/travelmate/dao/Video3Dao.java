package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Video3;

public class Video3Dao {

	
	public Video3 salvar(Video3 video3) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		video3 = manager.merge(video3);
        tx.commit();
        
        return video3;
    }
    
    public List<Video3> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Video3> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idvideo3) throws SQLException {
        EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Video3 v where v.idvideo3=" + idvideo3);
        if (q.getResultList().size()>0){
        	Video3 video3 = (Video3) q.getResultList().get(0);
            manager.remove(video3);
        }
        tx.commit();
        
    }
}
