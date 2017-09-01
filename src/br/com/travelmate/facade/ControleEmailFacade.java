package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ControleEmailDao;
import br.com.travelmate.model.Controleemail;

public class ControleEmailFacade {

	ControleEmailDao controleEmailDao;
	
	public Controleemail salvar(Controleemail controleemail) {
		controleEmailDao = new ControleEmailDao();
        try {
            return controleEmailDao.salvar(controleemail);
        } catch (SQLException ex) {
            Logger.getLogger(ControleEmailFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Controleemail> listar(String sql){
    	controleEmailDao = new ControleEmailDao();
        try {
            return controleEmailDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ControleEmailFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idControleemail) {
    	controleEmailDao = new ControleEmailDao();
        try {
        	controleEmailDao.excluir(idControleemail);
        } catch (SQLException ex) {
            Logger.getLogger(ControleEmailFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
