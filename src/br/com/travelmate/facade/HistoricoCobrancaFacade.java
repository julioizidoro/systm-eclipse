package br.com.travelmate.facade;

import br.com.travelmate.dao.HistoricoCobrancaDao;
import br.com.travelmate.model.Historicocobranca;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoricoCobrancaFacade {
    
    private HistoricoCobrancaDao historicoCobrancaDao;
    
    public Historicocobranca salvar(Historicocobranca historicoCobranca) {
        historicoCobrancaDao = new HistoricoCobrancaDao();
        try {
            return historicoCobrancaDao.salvar(historicoCobranca);
        } catch (SQLException ex) {
            Logger.getLogger(HistoricoCobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Historicocobranca> listar(String sql){
        historicoCobrancaDao = new HistoricoCobrancaDao();
        try {
            return historicoCobrancaDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(HistoricoCobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Historicocobranca consultar(String sql){
        historicoCobrancaDao = new HistoricoCobrancaDao();
        try {
            return historicoCobrancaDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(HistoricoCobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
