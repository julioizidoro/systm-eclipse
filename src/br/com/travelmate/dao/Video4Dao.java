package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Video4;

public class Video4Dao {

	public Video4 salvar(Video4 video4) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		video4 = manager.merge(video4);
        tx.commit();
        
        return video4;
    }

    @SuppressWarnings("unchecked")
    public List<Video4> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Video4> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idvideo4) throws SQLException {
        EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Video4 v where v.idvideo4=" + idvideo4);
        if (q.getResultList().size()>0){
        	Video4 video4 = (Video4) q.getResultList().get(0);
            manager.remove(video4);
        }
        tx.commit();
        
    }
}
