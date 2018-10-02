/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade; 

import br.com.travelmate.dao.FaturaAjusteDao; 
import br.com.travelmate.model.Faturaajustes; 

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class FaturaAjusteFacade {
    
    private FaturaAjusteDao faturaDao;
    
    public Faturaajustes salvar(Faturaajustes fatura){
    	faturaDao = new FaturaAjusteDao();
        try {
			return faturaDao.salvar(fatura);
		} catch (SQLException e) {
			  
		}
        return null;
    }
    
    public Faturaajustes getFatura(String sql){
    	faturaDao = new FaturaAjusteDao();
        try {
			return faturaDao.getFatura(sql);
		} catch (SQLException e) {
			  
		}
        return null;
    }
    
    public void excluir(int idFaturaajuste){
    	faturaDao = new FaturaAjusteDao();
    	try {
			faturaDao.excluir(idFaturaajuste);
		} catch (SQLException e) {
			  
		} 
    }
    
    public List<Faturaajustes> listar(String sql){
    	faturaDao = new FaturaAjusteDao();
        try {
			return faturaDao.listar(sql);
		} catch (SQLException e) {
			  
		}   
        return null;
    }
}
