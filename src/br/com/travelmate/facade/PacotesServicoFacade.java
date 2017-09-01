/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.PacotesServicoDao;
import br.com.travelmate.model.Pacoteservico;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class PacotesServicoFacade {
    
    PacotesServicoDao pacotesDao;
    
    public Pacoteservico salvar(Pacoteservico pacote) {
        pacotesDao = new PacotesServicoDao();
        try {
            return pacotesDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesServicoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesDao = new PacotesServicoDao();
        try {
            pacotesDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesServicoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacoteservico consultar(int idTrecho) {
        pacotesDao = new PacotesServicoDao();
        try {
            return pacotesDao.consultar(idTrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesServicoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
