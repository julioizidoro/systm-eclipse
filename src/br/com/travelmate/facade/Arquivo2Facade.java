package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Arquivo2Dao;
import br.com.travelmate.model.Arquivo2;


public class Arquivo2Facade {

	Arquivo2Dao arquivo2Dao;
    
    public Arquivo2 salvar(Arquivo2 arquivo2) {
    	arquivo2Dao = new Arquivo2Dao();
        try {
            return arquivo2Dao.salvar(arquivo2);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo2Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Arquivo2> listar(String sql){
    	arquivo2Dao = new Arquivo2Dao();
        try {
            return arquivo2Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo2Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idarquivo2) {
    	arquivo2Dao = new Arquivo2Dao();
        try {
        	arquivo2Dao.excluir(idarquivo2);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo2Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
