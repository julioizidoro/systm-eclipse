package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VideoPasta5Dao;
import br.com.travelmate.model.Videopasta5;

public class VideoPasta5Facade {
	
	VideoPasta5Dao videoPasta5Dao;
	
	public Videopasta5 salvar(Videopasta5 videopasta5) {
		videoPasta5Dao = new VideoPasta5Dao();
	        try {
	            return videoPasta5Dao.salvar(videopasta5);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta5Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Videopasta5> listar(String sql){
	    	videoPasta5Dao = new VideoPasta5Dao();
	        try {
	            return videoPasta5Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta5Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideopasta5) {
	    	videoPasta5Dao = new VideoPasta5Dao();
	        try {
	        	videoPasta5Dao.excluir(idvideopasta5);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta5Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
