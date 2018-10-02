package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VistosDao;
import br.com.travelmate.model.Vistos;

public class VistosFacade {

	private VistosDao vistosDao;
    
    public Vistos salvar(Vistos visto) {
        vistosDao = new VistosDao();
        try {
			return vistosDao.salvar(visto);
		} catch (SQLException e) { 
			  
			return null;
		}
    }
    
    public Vistos consultarVistos(int idVenda){
        vistosDao = new VistosDao();
        try {
			return vistosDao.consultarVistos(idVenda);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public List<Vistos> listar(String sql) {
    	vistosDao = new VistosDao();
        try {
            return vistosDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VistosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idVisto) {
        vistosDao = new VistosDao();
        try {
			vistosDao.excluir(idVisto);
		} catch (SQLException e) {
			  
		}

    }
    
    
    
    
   
}
