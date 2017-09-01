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

import br.com.travelmate.dao.DemipairDao;
import br.com.travelmate.model.Controledemipair;
import br.com.travelmate.model.Demipair;

/**
 *
 * @author Wolverine
 */
public class DemipairFacade {
    
    DemipairDao demipairDao;
    
    public Demipair salvar(Demipair aupair){
    	demipairDao= new DemipairDao();
        try {
            return demipairDao.salvar(aupair);
        } catch (SQLException ex) {
            Logger.getLogger(DemipairFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Demipair " + ex);
            return null;
        }
    }
    
    public Demipair consultar(int idVenda) {
    	demipairDao= new DemipairDao();
        try {
			return demipairDao.consultar(idVenda);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public List<Demipair> lista(String sql) {
    	demipairDao= new DemipairDao();
        try {
            return demipairDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DemipairFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    

    public Controledemipair salvar(Controledemipair controle){
    	demipairDao= new DemipairDao();
        try {
            return demipairDao.salvar(controle);
        } catch (SQLException ex) {
            Logger.getLogger(DemipairFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Controle Demipair " + ex);
            return null;
        }
    }
    

    public List<Controledemipair> listaControle(String sql) {
    	demipairDao= new DemipairDao();
        try {
            return demipairDao.listaControle(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DemipairFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Controledemipair consultarControle(int idVenda)  {
    	demipairDao= new DemipairDao();
        try {
			return demipairDao.consultarControle(idVenda);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
}
