package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
 
import br.com.travelmate.dao.ControleWorkEmbarqueDao;
import br.com.travelmate.model.Controleworkembarque; 

public class ControleWorkEmbarqueFacade {
	
	ControleWorkEmbarqueDao controleWorkEmbarqueDao;
	
	 public Controleworkembarque salvar(Controleworkembarque controleworkembarque){
		 controleWorkEmbarqueDao = new ControleWorkEmbarqueDao();
        try {
            return controleWorkEmbarqueDao.salvar(controleworkembarque);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEmbarqueFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Salvar " + ex);
            return null;
        }
    } 
    
    public Controleworkembarque consultar(String sql) {
    	controleWorkEmbarqueDao = new ControleWorkEmbarqueDao();
        try {
            return controleWorkEmbarqueDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEmbarqueFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar " + ex);
            return null;
        }
    }
    
    public void excluir(int idControleworkembarque) {
    	controleWorkEmbarqueDao = new ControleWorkEmbarqueDao();
        try {
        	controleWorkEmbarqueDao.excluir(idControleworkembarque);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEmbarqueFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir " + ex);
        }
    }
   
    
    public List<Controleworkembarque> lista(String sql) {
    	controleWorkEmbarqueDao = new ControleWorkEmbarqueDao();
        try {
            return controleWorkEmbarqueDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControleWorkEmbarqueFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
