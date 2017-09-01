package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ValoresWorkDao;
import br.com.travelmate.model.Valoreswork;

public class ValoresWorkFacade {
	private ValoresWorkDao valoresWorkDao;

    
    public Valoreswork salvar(Valoreswork valor) {
    	valoresWorkDao = new ValoresWorkDao();
        try {
            return valoresWorkDao.salvar(valor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresWorkFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Valoreswork consultar(int idvalor)  {
    	valoresWorkDao = new ValoresWorkDao();
        try {
            return valoresWorkDao.consultar(idvalor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresWorkFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Valoreswork> listar(String sql) {
    	valoresWorkDao = new ValoresWorkDao();
        try {
            return valoresWorkDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresWorkFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
