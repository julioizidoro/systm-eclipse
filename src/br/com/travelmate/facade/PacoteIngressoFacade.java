package br.com.travelmate.facade;

import br.com.travelmate.dao.PacotesIngressoDao;
import br.com.travelmate.model.Pacoteingresso;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
  

public class PacoteIngressoFacade {
    
    private PacotesIngressoDao pacotesIngressoDao;
    
    public Pacoteingresso salvar(Pacoteingresso pacote) {
        pacotesIngressoDao = new PacotesIngressoDao();
        try {
            return pacotesIngressoDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteIngressoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesIngressoDao = new PacotesIngressoDao();
        try {
            pacotesIngressoDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteIngressoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacoteingresso consultar(int idTrecho)  {
        pacotesIngressoDao = new PacotesIngressoDao();
        try {
            return pacotesIngressoDao.consultar(idTrecho);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteIngressoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pacoteingresso> listar(String sql) {
        pacotesIngressoDao = new PacotesIngressoDao();
        try {
            return pacotesIngressoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteIngressoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
