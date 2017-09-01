package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.MotivoRecInternacionalDao;
import br.com.travelmate.model.Motivorecinternacional;

public class MotivoRecInternacionalFacade {

	MotivoRecInternacionalDao motivoRecInternacionalDao;
	
	public Motivorecinternacional salvar(Motivorecinternacional motivorecinternacional) {
		motivoRecInternacionalDao = new MotivoRecInternacionalDao();
        try {
            return motivoRecInternacionalDao.salvar(motivorecinternacional);
        } catch (SQLException ex) {
            Logger.getLogger(MotivoRecInternacionalFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Motivorecinternacional> listar(String sql){
    	motivoRecInternacionalDao = new MotivoRecInternacionalDao();
        try {
            return motivoRecInternacionalDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MotivoRecInternacionalFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idmotivo) {
    	motivoRecInternacionalDao = new MotivoRecInternacionalDao();
        try {
        	motivoRecInternacionalDao.excluir(idmotivo);
        } catch (SQLException ex) {
            Logger.getLogger(MotivoRecInternacionalFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
