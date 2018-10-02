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

import br.com.travelmate.dao.RegraVendaDao;
import br.com.travelmate.model.Regravenda;

/**
 *
 * @author Wolverine
 */
public class RegraVendaFacade {
    
    RegraVendaDao regraVendaDao;
    
    public Regravenda salvar(Regravenda regravenda){
    	regraVendaDao= new RegraVendaDao();
        try {
            return regraVendaDao.salvar(regravenda);
        } catch (SQLException ex) {
            Logger.getLogger(RegraVendaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Aupair " + ex);
            return null;
        }
    }
    
    
   
    public Regravenda consultar(String sql) {
    	regraVendaDao= new RegraVendaDao();
        try {
			return regraVendaDao.consultar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public List<Regravenda> lista(String sql) {
    	regraVendaDao= new RegraVendaDao();
        try {
            return regraVendaDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RegraVendaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
}
