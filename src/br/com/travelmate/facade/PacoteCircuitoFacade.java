package br.com.travelmate.facade;

import br.com.travelmate.dao.PacoteCircuitoDao;
import br.com.travelmate.model.Pacotecircuito;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacoteCircuitoFacade {
    
    PacoteCircuitoDao pacotesDao;
    
    public Pacotecircuito salvar(Pacotecircuito pacote) {
        pacotesDao = new PacoteCircuitoDao();
        try {
            return pacotesDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteCircuitoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesDao = new PacoteCircuitoDao();
        try {
            pacotesDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteCircuitoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacotecircuito consultar(int idTrecho) {
        pacotesDao = new PacoteCircuitoDao();
        try {
            return pacotesDao.consultar(idTrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteCircuitoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
