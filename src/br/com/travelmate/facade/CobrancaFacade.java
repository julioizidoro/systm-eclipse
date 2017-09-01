package br.com.travelmate.facade;

import br.com.travelmate.dao.CobrancaDao;
import br.com.travelmate.model.Cobranca;
import br.com.travelmate.model.Historicocobranca;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Wolverine
 */
public class CobrancaFacade {
    
    private CobrancaDao cobrancaDao;
    
    public Cobranca salvar(Cobranca cobranca) {
        cobrancaDao = new CobrancaDao();
        try {
            return cobrancaDao.salvar(cobranca);
        } catch (SQLException ex) {
            Logger.getLogger(CobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Historicocobranca salvar(Historicocobranca historicocobranca) {
        cobrancaDao = new CobrancaDao();
        try {
            return cobrancaDao.salvar(historicocobranca);
        } catch (SQLException ex) {
            Logger.getLogger(CobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cobranca consultar(String sql){
        cobrancaDao = new CobrancaDao();
        try {
            return cobrancaDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Cobranca> listar(String sql){
    	cobrancaDao = new CobrancaDao();
    	try {
			return cobrancaDao.listar(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    
    
}
