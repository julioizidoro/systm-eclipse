package br.com.travelmate.facade;
 
import br.com.travelmate.dao.HeDao; 
import br.com.travelmate.model.He; 

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class HeFacade { 
    
	HeDao heDao;
    
    public He salvar(He he){
    	heDao = new HeDao();
        try {
            return heDao.salvar(he);
        } catch (SQLException ex) {
            Logger.getLogger(HeFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar he" + ex);
            return null;
        }
        
    }
    
    public List<He> listar(String sql) {
    	heDao = new HeDao();
        try {
            return heDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(HeFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar he" + ex);
            return null;
        }
    }
    
    public He consultar(int id)  {
    	heDao = new HeDao();
    	try {
			return heDao.consultar(id);
		} catch (SQLException e) {
			  
			return null;
		}
    } 
    
    
    public He consultarVenda(int id)  {
    	heDao = new HeDao();
    	try {
			return heDao.consultarVenda(id);
		} catch (SQLException e) {
			  
			return null;
		}
    } 
}
