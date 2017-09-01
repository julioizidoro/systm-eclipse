package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Pasta5Dao;
import br.com.travelmate.model.Pasta5;


public class Pasta5Facade {

	
	Pasta5Dao pasta5Dao;
	
	public Pasta5 salvar(Pasta5 pasta5) {
		pasta5Dao = new Pasta5Dao();
        try {
            return pasta5Dao.salvar(pasta5);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta5Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pasta5> listar(String sql){
    	pasta5Dao = new Pasta5Dao();
        try {
            return pasta5Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta5Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idpasta5) {
    	pasta5Dao = new Pasta5Dao();
        try {
        	pasta5Dao.excluir(idpasta5);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta5Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
}
