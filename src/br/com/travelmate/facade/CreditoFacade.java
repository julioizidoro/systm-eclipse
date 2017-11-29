package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.CreditoDao;
import br.com.travelmate.model.Credito;

public class CreditoFacade {
	
	CreditoDao creditoDao;
	
	public Credito salvar(Credito credito) {
		creditoDao = new CreditoDao();
        try {
            return creditoDao.salvar(credito);
        } catch (SQLException ex) {
            Logger.getLogger(CreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Credito> listar(String sql){
    	creditoDao = new CreditoDao();
        try {
            return creditoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idcredito) {
    	creditoDao = new CreditoDao();
        try {
        	creditoDao.excluir(idcredito);
        } catch (SQLException ex) {
            Logger.getLogger(CreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
