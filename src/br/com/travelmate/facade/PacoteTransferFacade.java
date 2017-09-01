package br.com.travelmate.facade;

import br.com.travelmate.dao.PacoteTransferDao;
import br.com.travelmate.model.Pacotetransfer;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class PacoteTransferFacade {
    
    PacoteTransferDao pacotesDao;
    
    public Pacotetransfer salvar(Pacotetransfer pacote) {
        pacotesDao = new PacoteTransferDao();
        try {
            return pacotesDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteTransferFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesDao = new PacoteTransferDao();
        try {
            pacotesDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteTransferFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacotetransfer consultar(int idTrecho) {
        pacotesDao = new PacoteTransferDao();
        try {
            return pacotesDao.consultar(idTrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteTransferFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
