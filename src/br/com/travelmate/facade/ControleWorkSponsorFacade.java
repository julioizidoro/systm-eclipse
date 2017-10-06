package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.ControleWorkSponsorDao;
import br.com.travelmate.model.Controleworksponsor; 

public class ControleWorkSponsorFacade {
	
	ControleWorkSponsorDao controleWorkSponsorDao;
	
	 public Controleworksponsor salvar(Controleworksponsor controleworksponsor){
		 controleWorkSponsorDao = new ControleWorkSponsorDao();
        try {
            return controleWorkSponsorDao.salvar(controleworksponsor);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkSponsorFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar " + ex);
            return null;
        }
    } 
    
    public Controleworksponsor consultar(String sql) {
    	controleWorkSponsorDao = new ControleWorkSponsorDao();
        try {
            return controleWorkSponsorDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkSponsorFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar " + ex);
            return null;
        }
    }
    
    public void excluir(int idControleworksponsor) {
    	controleWorkSponsorDao = new ControleWorkSponsorDao();
        try {
        	controleWorkSponsorDao.excluir(idControleworksponsor);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkSponsorFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir " + ex);
        }
    }
   
    
    public List<Controleworksponsor> lista(String sql) {
    	controleWorkSponsorDao = new ControleWorkSponsorDao();
        try {
            return controleWorkSponsorDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkSponsorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
