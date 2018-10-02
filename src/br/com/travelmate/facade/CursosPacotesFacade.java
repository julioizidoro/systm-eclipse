/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.CursosPacoteDao;
import br.com.travelmate.model.Cursospacote;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class CursosPacotesFacade {
    
    private CursosPacoteDao cursosPacoteDao;
    
    public Cursospacote salvar(Cursospacote cursospacote){
    	cursosPacoteDao = new CursosPacoteDao();
        try {
			return cursosPacoteDao.salvar(cursospacote);
		} catch (SQLException e) {
			  
		}
        return null;
    }
    
    public Cursospacote consultar(String sql){
    	cursosPacoteDao = new CursosPacoteDao();
        try {
			return cursosPacoteDao.consultar(sql);
		} catch (SQLException e) {
			  
		}
        return null;
    }
    
    public void excluir(int idCursospacote){
    	cursosPacoteDao = new CursosPacoteDao();
    	try {
    		cursosPacoteDao.excluir(idCursospacote);
		} catch (SQLException e) {
			  
		} 
    }
    
    public List<Cursospacote> listar(String sql){
    	cursosPacoteDao = new CursosPacoteDao();
        try {
			return cursosPacoteDao.listar(sql);
		} catch (SQLException e) {
			  
		}   
        return null;
    }
}
