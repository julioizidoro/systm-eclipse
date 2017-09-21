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

import br.com.travelmate.dao.WorkSponsorDao;
import br.com.travelmate.model.Worksponsor;

/**
 *
 * @author Kamila
 */
public class WorkSponsorFacade {
    
    WorkSponsorDao workSponsorDao;
    
    public Worksponsor salvar(Worksponsor worksponsor) {
    		workSponsorDao = new WorkSponsorDao();
        try {
            return workSponsorDao.salvar(worksponsor);
        } catch (SQLException ex) {
            Logger.getLogger(WorkSponsorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Worksponsor> listar(String sql) {
    		workSponsorDao = new WorkSponsorDao();
        try {
            return workSponsorDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(WorkSponsorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Worksponsor consultar(String sql){
    		workSponsorDao = new WorkSponsorDao();
        try {
            return workSponsorDao.consulta(sql);
        } catch (SQLException ex) {
            Logger.getLogger(WorkSponsorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
}
