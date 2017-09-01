package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Pasta2Dao;
import br.com.travelmate.model.Pasta2;


public class Pasta2Facade {

	Pasta2Dao pasta2Dao;
    
    public Pasta2 salvar(Pasta2 pasta2) {
    	pasta2Dao = new Pasta2Dao();
        try {
            return pasta2Dao.salvar(pasta2);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta2Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pasta2> listar(String sql){
    	pasta2Dao = new Pasta2Dao();
        try {
            return pasta2Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta2Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idpasta2) {
    	pasta2Dao = new Pasta2Dao();
        try {
        	pasta2Dao.excluir(idpasta2);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta2Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
