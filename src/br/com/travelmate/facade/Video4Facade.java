package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Video4Dao;
import br.com.travelmate.model.Video4;

public class Video4Facade {
	
	Video4Dao video4Dao;
	
	public Video4 salvar(Video4 video4) {
		video4Dao = new Video4Dao();
	        try {
	            return video4Dao.salvar(video4);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video4Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Video4> listar(String sql){
	    	video4Dao = new Video4Dao();
	        try {
	            return video4Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video4Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideo4) {
	    	video4Dao = new Video4Dao();
	        try {
	        	video4Dao.excluir(idvideo4);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video4Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
