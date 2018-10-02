/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.MotivoCancelamentoDao;
import br.com.travelmate.model.Motivocancelamento; 

/**
 *
 * @author Wolverine
 */
public class MotivoCancelamentoFacade {
    
    MotivoCancelamentoDao motivoCancelamentoDao;
    
    public Motivocancelamento salvar(Motivocancelamento motivo){
    	motivoCancelamentoDao= new MotivoCancelamentoDao();
        try {
            return motivoCancelamentoDao.salvar(motivo);
        } catch (SQLException ex) {
            Logger.getLogger(MotivoCancelamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Motivo de cancelamento " + ex);
            return null;
        }
    }
    
    
   
    public Motivocancelamento consultar(String sql) {
    	motivoCancelamentoDao= new MotivoCancelamentoDao();
        try {
			return motivoCancelamentoDao.consultar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public List<Motivocancelamento> lista(String sql) {
    	motivoCancelamentoDao= new MotivoCancelamentoDao();
        try {
            return motivoCancelamentoDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MotivoCancelamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
}
