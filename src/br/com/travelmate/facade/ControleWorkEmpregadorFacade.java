package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.ControleWorkEmpregadorDao;
import br.com.travelmate.model.Controleworkempregaor;

public class ControleWorkEmpregadorFacade {
	
	ControleWorkEmpregadorDao controleWorkEmpregadorDao;
	
	 public Controleworkempregaor salvar(Controleworkempregaor controleworkempregaor){
		 controleWorkEmpregadorDao = new ControleWorkEmpregadorDao();
        try {
            return controleWorkEmpregadorDao.salvar(controleworkempregaor);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEmpregadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar " + ex);
            return null;
        }
    } 
    
    public Controleworkempregaor consultar(String sql) {
    	controleWorkEmpregadorDao = new ControleWorkEmpregadorDao();
        try {
            return controleWorkEmpregadorDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEmpregadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar " + ex);
            return null;
        }
    }
    
    public void excluir(int idControleworkempregaor) {
    	controleWorkEmpregadorDao = new ControleWorkEmpregadorDao();
        try {
        	controleWorkEmpregadorDao.excluir(idControleworkempregaor);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEmpregadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir " + ex);
        }
    }
   
    
    public List<Controleworkempregaor> lista(String sql) {
    	controleWorkEmpregadorDao = new ControleWorkEmpregadorDao();
        try {
            return controleWorkEmpregadorDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEmpregadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
