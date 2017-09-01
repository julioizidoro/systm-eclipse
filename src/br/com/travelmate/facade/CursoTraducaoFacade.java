package br.com.travelmate.facade;

import br.com.travelmate.dao.CursoTraducaoDao;
import br.com.travelmate.model.Cursotraducao;

import java.sql.SQLException; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamila
 */
public class CursoTraducaoFacade {
    
    CursoTraducaoDao cursoTraducaoDao;

    
    public Cursotraducao salvar(Cursotraducao cursotraducao) {
    	cursoTraducaoDao = new CursoTraducaoDao();
        try {
            return cursoTraducaoDao.salvar(cursotraducao);
        } catch (SQLException ex) {
            Logger.getLogger(CursoTraducaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
    
    public Cursotraducao consultar(int idcurso) {
    	cursoTraducaoDao = new CursoTraducaoDao();
    	try {
			return cursoTraducaoDao.consultar(idcurso);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
}
