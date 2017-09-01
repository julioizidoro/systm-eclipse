package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VideoPasta4Dao;
import br.com.travelmate.model.Videopasta4;

public class VideoPasta4Facade {
	
	VideoPasta4Dao videoPasta4Dao;
	
	public Videopasta4 salvar(Videopasta4 videopasta4) {
		videoPasta4Dao = new VideoPasta4Dao();
	        try {
	            return videoPasta4Dao.salvar(videopasta4);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta4Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Videopasta4> listar(String sql){
	    	videoPasta4Dao = new VideoPasta4Dao();
	        try {
	            return videoPasta4Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta4Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideopasta4) {
	    	videoPasta4Dao = new VideoPasta4Dao();
	        try {
	        	videoPasta4Dao.excluir(idvideopasta4);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta4Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
