package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.LeadResponsavelDao;
import br.com.travelmate.model.Leadresponsavel; 

public class LeadResponsavelFacade {
	
	LeadResponsavelDao leadResponsavelDao;
	
	 public Leadresponsavel salvar(Leadresponsavel leadresponsavel){
		 leadResponsavelDao = new LeadResponsavelDao();
        try {
            return leadResponsavelDao.salvar(leadresponsavel);
        } catch (SQLException ex) {
            Logger.getLogger(LeadResponsavelFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar " + ex);
            return null;
        }
    } 
    
    public Leadresponsavel consultar(String sql) {
    	leadResponsavelDao = new LeadResponsavelDao();
        try {
            return leadResponsavelDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(LeadResponsavelFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar " + ex);
            return null;
        }
    }
    
    public void excluir(int idleadresponsavel) {
    	leadResponsavelDao = new LeadResponsavelDao();
        try {
        	leadResponsavelDao.excluir(idleadresponsavel);
        } catch (SQLException ex) {
            Logger.getLogger(LeadResponsavelFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir " + ex);
        }
    }
   
    
    public List<Leadresponsavel> lista(String sql) {
    	leadResponsavelDao = new LeadResponsavelDao();
        try {
            return leadResponsavelDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(LeadResponsavelFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Leadresponsavel> lista(int idunidade) {
    	leadResponsavelDao = new LeadResponsavelDao();
        try {
            return leadResponsavelDao.lista(idunidade);
        } catch (SQLException ex) {
            Logger.getLogger(LeadResponsavelFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
