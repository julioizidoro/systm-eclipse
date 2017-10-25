package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.LeadControleDao;
import br.com.travelmate.model.Leadcontrole;

public class LeadControleFacade {

	LeadControleDao leadControleDao;
	
	public List<Leadcontrole> lista(String sql) {
		leadControleDao = new LeadControleDao();
        try {
            return leadControleDao.lista(sql);
                    } catch (SQLException ex) {
            Logger.getLogger(LeadControleFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Leadcontrole consultar(String sql) {
    	leadControleDao = new LeadControleDao();
        try { 
            return leadControleDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(LeadControleFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Leadcontrole salvar(Leadcontrole leadcontrole) {
    	leadControleDao = new LeadControleDao();
        try {
            return leadControleDao.salvar(leadcontrole);
        } catch (SQLException ex) {
            Logger.getLogger(LeadControleFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idLead){
    	leadControleDao = new LeadControleDao();
    	try {
    		leadControleDao.excluir(idLead);
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
    }
}
