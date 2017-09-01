package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VideoPasta1Dao;
import br.com.travelmate.model.Videopasta1;

public class VideoPasta1Facade {
	
	VideoPasta1Dao videoPasta1Dao;
	
	
	public Videopasta1 salvar(Videopasta1 videopasta1) {
		videoPasta1Dao = new VideoPasta1Dao();
	        try {
	            return videoPasta1Dao.salvar(videopasta1);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta1Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Videopasta1> listar(String sql){
	    	videoPasta1Dao = new VideoPasta1Dao();
	        try {
	            return videoPasta1Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta1Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideopasta1) {
	    	videoPasta1Dao = new VideoPasta1Dao();
	        try {
	        	videoPasta1Dao.excluir(idvideopasta1);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta1Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
