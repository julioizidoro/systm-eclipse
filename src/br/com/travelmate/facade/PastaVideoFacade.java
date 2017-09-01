package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.PastaVideoDao;
import br.com.travelmate.model.Pastavideo;

public class PastaVideoFacade {

	PastaVideoDao pastaVideoDao;
	
	public Pastavideo salvar(Pastavideo pastavideo) {
		pastaVideoDao = new PastaVideoDao();
        try {
            return pastaVideoDao.salvar(pastavideo);
        } catch (SQLException ex) {
            Logger.getLogger(PastaVideoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pastavideo> listar(String sql){
    	pastaVideoDao = new PastaVideoDao();
        try {
            return pastaVideoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PastaVideoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idpastavideo) {
    	pastaVideoDao = new PastaVideoDao();
        try {
        	pastaVideoDao.excluir(idpastavideo);
        } catch (SQLException ex) {
            Logger.getLogger(PastaVideoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
}
