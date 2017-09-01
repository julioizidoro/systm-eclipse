package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import br.com.travelmate.dao.LeadDao; 
import br.com.travelmate.model.Lead; 

public class LeadFacade {

	LeadDao leadDao;
	
	public List<Lead> lista(String sql) {
		leadDao = new LeadDao();
        try {
            return leadDao.lista(sql);
                    } catch (SQLException ex) {
            Logger.getLogger(LeadFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Lead consultar(String sql) {
    	leadDao = new LeadDao();
        try { 
            return leadDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(LeadFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Lead salvar(Lead lead) {
    	leadDao = new LeadDao();
        try {
            return leadDao.salvar(lead);
        } catch (SQLException ex) {
            Logger.getLogger(LeadFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idLead){
    	leadDao = new LeadDao();
    	try {
    		leadDao.excluir(idLead);
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
    }
     
}
