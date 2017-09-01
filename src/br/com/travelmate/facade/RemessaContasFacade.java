package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.RemesssaContasDao;
import br.com.travelmate.model.Remessacontas;

public class RemessaContasFacade {

	RemesssaContasDao remesssaContasDao;
	
	public Remessacontas salvar(Remessacontas remessacontas) {
		remesssaContasDao = new RemesssaContasDao();
        try {
            return remesssaContasDao.salvar(remessacontas);
        } catch (SQLException ex) {
            Logger.getLogger(RemessaContasFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Remessacontas> listar(String sql){
    	remesssaContasDao = new RemesssaContasDao();
        try {
            return remesssaContasDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RemessaContasFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idremessacontas) {
    	remesssaContasDao = new RemesssaContasDao();
        try {
        	remesssaContasDao.excluir(idremessacontas);
        } catch (SQLException ex) {
            Logger.getLogger(RemessaContasFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
}
