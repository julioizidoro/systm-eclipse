package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VideoPasta3Dao;
import br.com.travelmate.model.Videopasta3;

public class VideoPasta3Facade {
	
	VideoPasta3Dao videoPasta3Dao;
	
	
	public Videopasta3 salvar(Videopasta3 videopasta3) {
		videoPasta3Dao = new VideoPasta3Dao();
	        try {
	            return videoPasta3Dao.salvar(videopasta3);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta3Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Videopasta3> listar(String sql){
	    	videoPasta3Dao = new VideoPasta3Dao();
	        try {
	            return videoPasta3Dao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta3Facade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idvideopasta3) {
	    	videoPasta3Dao = new VideoPasta3Dao();
	        try {
	        	videoPasta3Dao.excluir(idvideopasta3);
	        } catch (SQLException ex) {
	            Logger.getLogger(VideoPasta3Facade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
