package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.CancelamentoCreditoDao;
import br.com.travelmate.model.Cancelamentocredito;


public class CancelamentoCreditoFacade {
	
	CancelamentoCreditoDao cancelamentoCreditoDao;
	
	public Cancelamentocredito salvar(Cancelamentocredito cancelamentocredito) {
		cancelamentoCreditoDao = new CancelamentoCreditoDao();
        try {
            return cancelamentoCreditoDao.salvar(cancelamentocredito);
        } catch (SQLException ex) {
            Logger.getLogger(CancelamentoCreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Cancelamentocredito> listar(String sql){
    	cancelamentoCreditoDao = new CancelamentoCreditoDao();
        try {
            return cancelamentoCreditoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CancelamentoCreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idcredito) {
    	cancelamentoCreditoDao = new CancelamentoCreditoDao();
        try {
        	cancelamentoCreditoDao.excluir(idcredito);
        } catch (SQLException ex) {
            Logger.getLogger(CancelamentoCreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
