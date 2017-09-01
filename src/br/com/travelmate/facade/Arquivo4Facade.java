package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Arquivo4Dao;
import br.com.travelmate.model.Arquivo4;


public class Arquivo4Facade {

	
	Arquivo4Dao arquivo4Dao;
    
    public Arquivo4 salvar(Arquivo4 arquivo4) {
    	arquivo4Dao = new Arquivo4Dao();
        try {
            return arquivo4Dao.salvar(arquivo4);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo4Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Arquivo4> listar(String sql){
    	arquivo4Dao = new Arquivo4Dao();
        try {
            return arquivo4Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo4Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idarquivo4) {
    	arquivo4Dao = new Arquivo4Dao();
        try {
        	arquivo4Dao.excluir(idarquivo4);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo4Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
