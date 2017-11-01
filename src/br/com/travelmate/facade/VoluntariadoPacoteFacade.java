package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
 
import br.com.travelmate.dao.VoluntariadoPacoteDao; 
import br.com.travelmate.model.Voluntariadopacote; 

public class VoluntariadoPacoteFacade {
	
	VoluntariadoPacoteDao voluntariadoPacoteDao;
	
	 public Voluntariadopacote salvar(Voluntariadopacote voluntariadopacote){
		 voluntariadoPacoteDao = new VoluntariadoPacoteDao();
        try {
            return voluntariadoPacoteDao.salvar(voluntariadopacote);
        } catch (SQLException ex) {
            Logger.getLogger(VoluntariadoPacoteFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Voluntariadopacote " + ex);
            return null;
        }
    } 
    
    public Voluntariadopacote consultar(String sql) {
    	voluntariadoPacoteDao = new VoluntariadoPacoteDao();
        try {
            return voluntariadoPacoteDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VoluntariadoPacoteFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar Voluntariadopacote " + ex);
            return null;
        }
    }
    
    public void excluir(int idVoluntariadopacote) {
    	voluntariadoPacoteDao = new VoluntariadoPacoteDao();
        try {
        	voluntariadoPacoteDao.excluir(idVoluntariadopacote);
        } catch (SQLException ex) {
            Logger.getLogger(VoluntariadoPacoteFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Voluntariadopacote " + ex);
        }
    }
   
    
    public List<Voluntariadopacote> lista(String sql) {
    	voluntariadoPacoteDao = new VoluntariadoPacoteDao();
        try {
            return voluntariadoPacoteDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VoluntariadoPacoteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
