package br.com.travelmate.facade;

import br.com.travelmate.dao.PacotesCruzeiroDao;
import br.com.travelmate.model.Pacotecruzeiro;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PacoteCruzeiroFacade {
    
    PacotesCruzeiroDao pacotesDao;
    
    public Pacotecruzeiro salvar(Pacotecruzeiro pacote) {
        pacotesDao = new PacotesCruzeiroDao();
        try {
            return pacotesDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteCruzeiroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesDao = new PacotesCruzeiroDao();
        try {
            pacotesDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteCruzeiroFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacotecruzeiro consultar(int idTrecho) {
        pacotesDao = new PacotesCruzeiroDao();
        try {
            return pacotesDao.consultar(idTrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteCruzeiroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
