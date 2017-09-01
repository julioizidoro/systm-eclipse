
package br.com.travelmate.facade;

import br.com.travelmate.dao.PacotePasseioDao;
import br.com.travelmate.model.Pacotepasseio;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacotePasseioFacade {
    
    private PacotePasseioDao pacotesPasseioDao;
    
    public Pacotepasseio salvar(Pacotepasseio pacote) {
        pacotesPasseioDao = new PacotePasseioDao();
        try {
            return pacotesPasseioDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotePasseioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesPasseioDao = new PacotePasseioDao();
        try {
            pacotesPasseioDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotePasseioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacotepasseio consultar(int idTrecho)  {
        pacotesPasseioDao = new PacotePasseioDao();
        try {
            return pacotesPasseioDao.consultar(idTrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacotePasseioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pacotepasseio> listar(String sql) {
        pacotesPasseioDao = new PacotePasseioDao();
        try {
            return pacotesPasseioDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PacotePasseioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
