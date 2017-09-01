package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VersoesDao;
import br.com.travelmate.model.Versoes;

public class VersoesFacade {

	private VersoesDao versoesDao;
	
	public Versoes salvar(Versoes versoes) {
		versoesDao = new VersoesDao();
        try {
            return versoesDao.salvar(versoes);
        } catch (SQLException ex) {
            Logger.getLogger(VersoesFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Versoes consultar(int idversao)  {
    	versoesDao = new VersoesDao();
        try {
            return versoesDao.consultar(idversao);
        } catch (SQLException ex) {
            Logger.getLogger(VersoesFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Versoes> listar(String sql) {
    	versoesDao = new VersoesDao();
        try {
            return versoesDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VersoesFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
