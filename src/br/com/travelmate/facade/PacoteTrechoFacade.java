/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.PacoteTrechoDao;
import br.com.travelmate.model.Pacotetrecho;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class PacoteTrechoFacade {
    
    
    PacoteTrechoDao pacoteTrechoDao;
    
    public Pacotetrecho salvar(Pacotetrecho pacotetrecho) {
        pacoteTrechoDao = new PacoteTrechoDao();
        try {
            return pacoteTrechoDao.salvar(pacotetrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteTrechoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pacotetrecho> listar(String sql) {
        pacoteTrechoDao = new PacoteTrechoDao();
        try {
            return pacoteTrechoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteTrechoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Pacotetrecho consultarTrecho(int idPacote)  {
        pacoteTrechoDao = new PacoteTrechoDao();
        try {
            return pacoteTrechoDao.consultarTrecho(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
