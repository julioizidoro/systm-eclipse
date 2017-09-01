package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Arquivo5Dao;
import br.com.travelmate.model.Arquivo5;


public class Arquivo5Facade {

	
	Arquivo5Dao arquivo5Dao;
    
    public Arquivo5 salvar(Arquivo5 arquivo5) {
    	arquivo5Dao = new Arquivo5Dao();
        try {
            return arquivo5Dao.salvar(arquivo5);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo5Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Arquivo5> listar(String sql){
    	arquivo5Dao = new Arquivo5Dao();
        try {
            return arquivo5Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo5Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idarquivo5) {
    	arquivo5Dao = new Arquivo5Dao();
        try {
        	arquivo5Dao.excluir(idarquivo5);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo5Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
