package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Arquivo3Dao;
import br.com.travelmate.model.Arquivo3;


public class Arquivo3Facade {

	Arquivo3Dao arquivo3Dao;
    
    public Arquivo3 salvar(Arquivo3 arquivo3) {
    	arquivo3Dao = new Arquivo3Dao();
        try {
            return arquivo3Dao.salvar(arquivo3);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo3Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Arquivo3> listar(String sql){
    	arquivo3Dao = new Arquivo3Dao();
        try {
            return arquivo3Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo3Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idarquivo3) {
    	arquivo3Dao = new Arquivo3Dao();
        try {
        	arquivo3Dao.excluir(idarquivo3);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo3Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
