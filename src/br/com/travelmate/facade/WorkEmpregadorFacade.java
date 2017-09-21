/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
   

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.WorkEmpregadorDao; 
import br.com.travelmate.model.Workempregador; 

/**
 *
 * @author Kamila
 */
public class WorkEmpregadorFacade {
    
    WorkEmpregadorDao workEmpregadorDao;
    
    public Workempregador salvar(Workempregador workempregador) {
    		workEmpregadorDao = new WorkEmpregadorDao();
        try {
            return workEmpregadorDao.salvar(workempregador);
        } catch (SQLException ex) {
            Logger.getLogger(WorkEmpregadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Workempregador> listar(String sql) {
    		workEmpregadorDao = new WorkEmpregadorDao();
        try {
            return workEmpregadorDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(WorkEmpregadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Workempregador consultar(String sql){
    		workEmpregadorDao = new WorkEmpregadorDao();
        try {
            return workEmpregadorDao.consulta(sql);
        } catch (SQLException ex) {
            Logger.getLogger(WorkEmpregadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
}
