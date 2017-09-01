package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Video3Dao;
import br.com.travelmate.model.Video3;

public class Video3Facade {

	Video3Dao video3Dao;
	
	
	public Video3 salvar(Video3 video3) {
		video3Dao = new Video3Dao();
	        try {
	            return video3Dao.salvar(video3);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video3Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Video3> listar(String sql){
	    	video3Dao = new Video3Dao();
	        try {
	            return video3Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video3Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideo3) {
	    	video3Dao = new Video3Dao();
	        try {
	        	video3Dao.excluir(idvideo3);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video3Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
}
