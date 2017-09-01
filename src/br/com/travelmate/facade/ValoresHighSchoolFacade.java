package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ValoresHighSchoolDao;
import br.com.travelmate.model.Valoreshighschool;

public class ValoresHighSchoolFacade {
	private ValoresHighSchoolDao valoresHighSchoolDao;

    
    public Valoreshighschool salvar(Valoreshighschool valor) {
    	valoresHighSchoolDao = new ValoresHighSchoolDao();
        try {
            return valoresHighSchoolDao.salvar(valor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresHighSchoolFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Valoreshighschool consultar(int idvalor)  {
    	valoresHighSchoolDao = new ValoresHighSchoolDao();
        try {
            return valoresHighSchoolDao.consultar(idvalor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresHighSchoolFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Valoreshighschool> listar(String sql) {
    	valoresHighSchoolDao = new ValoresHighSchoolDao();
        try {
            return valoresHighSchoolDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresHighSchoolFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
