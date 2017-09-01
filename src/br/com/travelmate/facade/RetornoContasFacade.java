package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.RetornoContasDao;
import br.com.travelmate.model.Retornocontas;

public class RetornoContasFacade {

	RetornoContasDao retornoContasDao;
	
	public Retornocontas salvar(Retornocontas retornocontas) {
		retornoContasDao = new RetornoContasDao();
        try {
            return retornoContasDao.salvar(retornocontas);
        } catch (SQLException ex) {
            Logger.getLogger(RetornoContasFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Retornocontas> listar(String sql){
    	retornoContasDao = new RetornoContasDao();
        try {
            return retornoContasDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RetornoContasFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idretornocontas) {
    	retornoContasDao = new RetornoContasDao();
        try {
        	retornoContasDao.excluir(idretornocontas);
        } catch (SQLException ex) {
            Logger.getLogger(RetornoContasFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
}
