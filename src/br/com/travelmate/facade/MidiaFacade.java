package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.MidiaDao;
import br.com.travelmate.model.Midias;


public class MidiaFacade {
	
	MidiaDao midiaDao;
	
	public List<Midias> listar(String tipo) {
		midiaDao = new MidiaDao();
        try {
            return midiaDao.listar(tipo);
        } catch (SQLException ex) {
            Logger.getLogger(MidiaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

	public List<Midias> listarSql(String sql) {
		midiaDao = new MidiaDao();
        try {
            return midiaDao.listarSql(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MidiaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
