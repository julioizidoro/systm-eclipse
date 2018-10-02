/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.CursosPacoteFormaPagamentoDao;
import br.com.travelmate.model.Cursopacoteformapagamento;


/**
 *
 * @author Kamila
 */
public class CursosPacotesFormaPagamentoFacade {
    
    private CursosPacoteFormaPagamentoDao cursosPacoteDao;
    
    public Cursopacoteformapagamento salvar(Cursopacoteformapagamento cursospacote){
    	cursosPacoteDao = new CursosPacoteFormaPagamentoDao();
        try {
			return cursosPacoteDao.salvar(cursospacote);
		} catch (SQLException e) { 
			  
		}
        return null;
    }
    
    public Cursopacoteformapagamento consultar(String sql){
    	cursosPacoteDao = new CursosPacoteFormaPagamentoDao();
        try {
			return cursosPacoteDao.consultar(sql);
		} catch (SQLException e) { 
			  
		}
        return null;
    }
    
    public void excluir(int idCursospacote){
    	cursosPacoteDao = new CursosPacoteFormaPagamentoDao();
    	try {
    		cursosPacoteDao.excluir(idCursospacote);
		} catch (SQLException e) { 
			  
		} 
    }
    
    public List<Cursopacoteformapagamento> listar(String sql){
    	cursosPacoteDao = new CursosPacoteFormaPagamentoDao();
        try {
			return cursosPacoteDao.listar(sql);
		} catch (SQLException e) { 
			  
		}   
        return null;
    }
}
