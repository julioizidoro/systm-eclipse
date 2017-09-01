package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Pasta4Dao;
import br.com.travelmate.model.Pasta4;


public class Pasta4Facade {

	Pasta4Dao pasta4Dao;
	
	public Pasta4 salvar(Pasta4 pasta4) {
		pasta4Dao = new Pasta4Dao();
        try {
            return pasta4Dao.salvar(pasta4);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta4Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pasta4> listar(String sql){
    	pasta4Dao = new Pasta4Dao();
        try {
            return pasta4Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta4Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idpasta4) {
    	pasta4Dao = new Pasta4Dao();
        try {
        	pasta4Dao.excluir(idpasta4);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta4Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
}
