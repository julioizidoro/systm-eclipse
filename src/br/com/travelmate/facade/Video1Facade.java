package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Video1Dao;
import br.com.travelmate.model.Video1;

public class Video1Facade {

	Video1Dao video1Dao;
	
	 public Video1 salvar(Video1 video1) {
		 video1Dao = new Video1Dao();
	        try {
	            return video1Dao.salvar(video1);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video1Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Video1> listar(String sql){
	    	video1Dao = new Video1Dao();
	        try {
	            return video1Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video1Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideo1) {
	    	video1Dao = new Video1Dao();
	        try {
	        	video1Dao.excluir(idvideo1);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video1Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
}
