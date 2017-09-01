package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Video;

public class VideoDao {
    
    public Video salvar(Video video) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        video = manager.merge(video);
        tx.commit();
        
        return video;
    }
    
    public List<Video> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Video> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idvideo) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Video v where v.idvideo=" + idvideo);
        if (q.getResultList().size()>0){
        	Video video = (Video) q.getResultList().get(0);
            manager.remove(video);
        }
        tx.commit();
        
    }

}
