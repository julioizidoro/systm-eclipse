package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VideoPasta2Dao;
import br.com.travelmate.model.Videopasta2;

public class VideoPasta2Facade {
	
	VideoPasta2Dao videoPasta2Dao;

	
	public Videopasta2 salvar(Videopasta2 videopasta2) {
		videoPasta2Dao = new VideoPasta2Dao();
	        try {
	            return videoPasta2Dao.salvar(videopasta2);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta2Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Videopasta2> listar(String sql){
	    	videoPasta2Dao = new VideoPasta2Dao();
	        try {
	            return videoPasta2Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta2Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideopasta2) {
	    	videoPasta2Dao = new VideoPasta2Dao();
	        try {
	        	videoPasta2Dao.excluir(idvideopasta2);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta2Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
}
