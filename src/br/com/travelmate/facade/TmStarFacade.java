package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.TmStarDao;
import br.com.travelmate.model.Tmstar;

public class TmStarFacade {

	TmStarDao tmStarDao;
	
	public List<Tmstar> lista() {
		tmStarDao = new TmStarDao();
        try {
            return tmStarDao.lista();
                    } catch (SQLException ex) {
            Logger.getLogger(TmStarFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Tmstar consultar(int idTmStar) {
    	tmStarDao = new TmStarDao();
        try {
            return tmStarDao.consultar(idTmStar);
        } catch (SQLException ex) {
            Logger.getLogger(TmStarFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Tmstar salvar(Tmstar tmstar) {
    	tmStarDao = new TmStarDao();
        try {
            return tmStarDao.salvar(tmstar);
        } catch (SQLException ex) {
            Logger.getLogger(TmStarFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idTmStar) {
    	tmStarDao = new TmStarDao();
        try {
        	tmStarDao.excluir(idTmStar);
        } catch (SQLException ex) {
            Logger.getLogger(TmStarFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
