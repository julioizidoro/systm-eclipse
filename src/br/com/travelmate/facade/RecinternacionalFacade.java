package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.RecInternanionalDao;
import br.com.travelmate.model.Recinternacional;

public class RecinternacionalFacade {

	RecInternanionalDao recInternacionalDao;
	
	public Recinternacional salvar(Recinternacional recinternacional) {
		recInternacionalDao = new RecInternanionalDao();
        try {
            return recInternacionalDao.salvar(recinternacional);
        } catch (SQLException ex) {
            Logger.getLogger(RecinternacionalFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Recinternacional> listar(String sql){
    	recInternacionalDao = new RecInternanionalDao();
        try {
            return recInternacionalDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RecinternacionalFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idmotivo) {
    	recInternacionalDao = new RecInternanionalDao();
        try {
        	recInternacionalDao.excluir(idmotivo);
        } catch (SQLException ex) {
            Logger.getLogger(RecinternacionalFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
