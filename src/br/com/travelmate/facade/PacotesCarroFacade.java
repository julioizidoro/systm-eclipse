/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.PacotesCarroDao;
import br.com.travelmate.model.Pacotecarro;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class PacotesCarroFacade {
    
    PacotesCarroDao pacotesDao;
    
    public Pacotecarro salvar(Pacotecarro pacote) {
        pacotesDao = new PacotesCarroDao();
        try {
            return pacotesDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesCarroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesDao = new PacotesCarroDao();
        try {
            pacotesDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesCarroFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacotecarro consultar(int idTrecho)  {
        pacotesDao = new PacotesCarroDao();
        try {
            return pacotesDao.consultar(idTrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesCarroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
