package br.com.travelmate.facade;

import br.com.travelmate.dao.PacoteTremDao;
import br.com.travelmate.model.Pacotetrem;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacoteTremFacade {
    
    PacoteTremDao pacotesDao;
    
    public Pacotetrem salvar(Pacotetrem pacote) {
        pacotesDao = new PacoteTremDao();
        try {
            return pacotesDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteTremFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesDao = new PacoteTremDao();
        try {
            pacotesDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteTremFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacotetrem consultar(int idTrecho) {
        pacotesDao = new PacoteTremDao();
        try {
            return pacotesDao.consultar(idTrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteTremFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
