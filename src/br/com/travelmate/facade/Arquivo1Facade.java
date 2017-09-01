package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Arquivo1Dao;
import br.com.travelmate.model.Arquivo1;

public class Arquivo1Facade {

	Arquivo1Dao arquivo1Dao;
	
	public Arquivo1 salvar(Arquivo1 arquivo1) {
		arquivo1Dao = new Arquivo1Dao();
        try {
            return arquivo1Dao.salvar(arquivo1);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Arquivo1> listar(String sql){
    	arquivo1Dao = new Arquivo1Dao();
        try {
            return arquivo1Dao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idarquivo1) {
    	arquivo1Dao = new Arquivo1Dao();
        try {
        	arquivo1Dao.excluir(idarquivo1);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
