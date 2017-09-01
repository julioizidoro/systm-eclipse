package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Video2Dao;
import br.com.travelmate.model.Video2;

public class Video2Facade {

	Video2Dao video2Dao;
	
	public Video2 salvar(Video2 video2) {
		video2Dao = new Video2Dao();
	        try {
	            return video2Dao.salvar(video2);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video2Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Video2> listar(String sql){
	    	video2Dao = new Video2Dao();
	        try {
	            return video2Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video2Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideo2) {
	    	video2Dao = new Video2Dao();
	        try {
	        	video2Dao.excluir(idvideo2);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video2Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
}
