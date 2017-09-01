package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ValoresHeDao;
import br.com.travelmate.model.Valoreshe;  

public class ValoresHeFacade {
	
	private ValoresHeDao valoresHeDao; 
    
    public Valoreshe salvar(Valoreshe valor) {
    	valoresHeDao = new ValoresHeDao();
        try {
            return valoresHeDao.salvar(valor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresHeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Valoreshe consultar(int idvalor)  {
    	valoresHeDao = new ValoresHeDao();
        try {
            return valoresHeDao.consultar(idvalor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresHeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Valoreshe> listar(String sql) {
    	valoresHeDao = new ValoresHeDao();
        try {
            return valoresHeDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresHeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
