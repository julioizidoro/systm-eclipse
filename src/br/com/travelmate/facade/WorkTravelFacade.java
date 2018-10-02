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

import br.com.travelmate.dao.WorkTravelDao;
import br.com.travelmate.model.Controlework;
import br.com.travelmate.model.Worktravel;



/**
 *
 * @author Wolverine
 */
public class WorkTravelFacade {
    
    WorkTravelDao workTravelDao;
    
    public Worktravel salvar(Worktravel work){
    	workTravelDao = new WorkTravelDao();
        try {
            return workTravelDao.salvar(work);
        } catch (SQLException ex) {
            Logger.getLogger(WorkTravelFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Trainee " + ex);
            return null;
        }
    }
    
    
   
    public Worktravel consultarWork(int idVenda) {
        workTravelDao = new WorkTravelDao();
        try {
			return workTravelDao.consultarWork(idVenda);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public List<Worktravel> lista(String sql) {
    	workTravelDao = new WorkTravelDao();
        try {
            return workTravelDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(WorkTravelFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Controlework salvar(Controlework controle){
    	workTravelDao = new WorkTravelDao();
        try {
            return workTravelDao.salvar(controle);
        } catch (SQLException ex) {
            Logger.getLogger(WorkTravelFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Controle Trainee " + ex);
            return null;
        }
    }
    

    public List<Controlework> listaControle(String sql) {
    	workTravelDao = new WorkTravelDao();
        try {
            return workTravelDao.listaControle(sql);
        } catch (SQLException ex) {
            Logger.getLogger(WorkTravelFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Controlework consultarControle(int idVenda)  {
    	workTravelDao = new WorkTravelDao();
        try {
			return workTravelDao.consultarControle(idVenda);
		} catch (SQLException e) {
			  
			return null;
		}
    }
}
