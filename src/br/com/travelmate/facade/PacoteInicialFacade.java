/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.PacoteInicialDao; 
import br.com.travelmate.model.Pacotesinicial;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class PacoteInicialFacade {
    
    private PacoteInicialDao pacoteInicialDao;
     
    public Pacotesinicial consultar(String sql){
    	pacoteInicialDao = new PacoteInicialDao();
        try {
			return pacoteInicialDao.consultar(sql);
		} catch (SQLException e) {
			  
		}
        return null;
    } 
    
    public List<Pacotesinicial> listar(String sql){
    	pacoteInicialDao = new PacoteInicialDao();
        try {
			return pacoteInicialDao.listar(sql);
		} catch (SQLException e) {
			  
		}   
        return null;
    }
}
