package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.MtpDao;
import br.com.travelmate.model.Mtp;

public class MtpFacade {

	 MtpDao mtpDao;
	
	 public Mtp salvar(Mtp mtp) {
		 mtpDao = new  MtpDao();
	        try {
	            return mtpDao.salvar(mtp);
	        } catch (SQLException ex) {
	            Logger.getLogger(MtpFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public Mtp consultarMtp(int idMtp) {
	    	mtpDao = new MtpDao();
	        try {
	            return mtpDao.consultarMtp(idMtp);
	        } catch (SQLException ex) {
	            Logger.getLogger(MtpFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	   
	    public List<Mtp> lista(String sql) {
	    	mtpDao = new MtpDao();
	        try {
	            return mtpDao.lista(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(MtpFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
	    public void excluir(int idMtp) {
	    	mtpDao = new MtpDao();
	        try {
	        	mtpDao.excluir(idMtp);
	        } catch (SQLException ex) {
	            Logger.getLogger(MtpFacade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    
	
}
