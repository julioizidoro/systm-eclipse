/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.PacotesHotelDao;
import br.com.travelmate.model.Pacotehotel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class PacotesHotelFacade {
    
    PacotesHotelDao pacotesDao;
    
    public Pacotehotel salvar(Pacotehotel pacote) {
        pacotesDao = new PacotesHotelDao();
        try {
            return pacotesDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesHotelFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesDao = new PacotesHotelDao();
        try {
            pacotesDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesHotelFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacotehotel consultar(int idTrecho) {
        pacotesDao = new PacotesHotelDao();
        try {
            return pacotesDao.consultar(idTrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesHotelFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
