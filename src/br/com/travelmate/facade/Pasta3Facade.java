package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Pasta3Dao;
import br.com.travelmate.model.Pasta3;


public class Pasta3Facade {

	Pasta3Dao pasta3Dao;
    
    public Pasta3 salvar(Pasta3 pasta3) {
    	pasta3Dao = new Pasta3Dao();
        try {
            return pasta3Dao.salvar(pasta3);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta3Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pasta3> listar(String sql){
    	pasta3Dao = new Pasta3Dao();
        try {
            return pasta3Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta3Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idpasta3) {
    	pasta3Dao = new Pasta3Dao();
        try {
        	pasta3Dao.excluir(idpasta3);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta3Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
}
