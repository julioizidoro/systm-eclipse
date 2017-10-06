package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
 
import br.com.travelmate.dao.ControleWorkEntrevistaDao; 
import br.com.travelmate.model.Controleworkentrevista; 

public class ControleWorkEntrevistaFacade {
	
	ControleWorkEntrevistaDao controleWorkEntrevistaDao;
	
	 public Controleworkentrevista salvar(Controleworkentrevista controleworkentrevista){
		 controleWorkEntrevistaDao = new ControleWorkEntrevistaDao();
        try {
            return controleWorkEntrevistaDao.salvar(controleworkentrevista);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEntrevistaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar " + ex);
            return null;
        }
    } 
    
    public Controleworkentrevista consultar(String sql) {
    	controleWorkEntrevistaDao = new ControleWorkEntrevistaDao();
        try {
            return controleWorkEntrevistaDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEntrevistaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar " + ex);
            return null;
        }
    }
    
    public void excluir(int idControleworkentrevista) {
    	controleWorkEntrevistaDao = new ControleWorkEntrevistaDao();
        try {
        	controleWorkEntrevistaDao.excluir(idControleworkentrevista);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEntrevistaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir " + ex);
        }
    }
   
    
    public List<Controleworkentrevista> lista(String sql) {
    	controleWorkEntrevistaDao = new ControleWorkEntrevistaDao();
        try {
            return controleWorkEntrevistaDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEntrevistaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
