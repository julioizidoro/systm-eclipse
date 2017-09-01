package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Pasta1Dao;
import br.com.travelmate.model.Pasta1;

public class Pasta1Facade {

	Pasta1Dao pasta1Dao;
    
    public Pasta1 salvar(Pasta1 pasta1) {
    	pasta1Dao = new Pasta1Dao();
        try {
            return pasta1Dao.salvar(pasta1);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pasta1> listar(String sql){
    	pasta1Dao = new Pasta1Dao();
        try {
            return pasta1Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idpasta1) {
    	pasta1Dao = new Pasta1Dao();
        try {
        	pasta1Dao.excluir(idpasta1);
        } catch (SQLException ex) {
            Logger.getLogger(Pasta1Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
