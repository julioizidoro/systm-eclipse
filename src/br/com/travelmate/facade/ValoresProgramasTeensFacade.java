package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ValoresProgramasTeensDao;
import br.com.travelmate.model.Valoresprogramasteens;

public class ValoresProgramasTeensFacade {
	private ValoresProgramasTeensDao valoresProgramasTeensDao;

    
    public Valoresprogramasteens salvar(Valoresprogramasteens valor) {
    	valoresProgramasTeensDao = new ValoresProgramasTeensDao();
        try {
            return valoresProgramasTeensDao.salvar(valor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresProgramasTeensFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Valoresprogramasteens consultar(int idvalor)  {
    	valoresProgramasTeensDao = new ValoresProgramasTeensDao();
        try {
            return valoresProgramasTeensDao.consultar(idvalor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresProgramasTeensFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Valoresprogramasteens> listar(String sql) {
    	valoresProgramasTeensDao = new ValoresProgramasTeensDao();
        try {
            return valoresProgramasTeensDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresProgramasTeensFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
