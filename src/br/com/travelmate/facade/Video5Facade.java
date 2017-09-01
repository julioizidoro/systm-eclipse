package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Video5Dao;
import br.com.travelmate.model.Video5;

public class Video5Facade {

	Video5Dao video5Dao;
	
	public Video5 salvar(Video5 video5) {
		video5Dao = new Video5Dao();
	        try {
	            return video5Dao.salvar(video5);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video4Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Video5> listar(String sql){
	    	video5Dao = new Video5Dao();
	        try {
	            return video5Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video4Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideo5) {
	    	video5Dao = new Video5Dao();
	        try {
	        	video5Dao.excluir(idvideo5);
	        } catch (SQLException ex) {
	            Logger.getLogger(Video4Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
