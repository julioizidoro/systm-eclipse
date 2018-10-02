/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
   
import br.com.travelmate.dao.VendasPacoteDao; 
import br.com.travelmate.model.Vendaspacote;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class VendasPacoteFacade {
    
    private VendasPacoteDao vendasPacoteDao;
    
    public Vendaspacote salvar(Vendaspacote vendaspacote){
    	vendasPacoteDao = new VendasPacoteDao();
        try {
			return vendasPacoteDao.salvar(vendaspacote);
		} catch (SQLException e) {
			  
		}
        return null;
    }
    
    public Vendaspacote consultar(String sql){
    	vendasPacoteDao = new VendasPacoteDao();
        try {
			return vendasPacoteDao.consultar(sql);
		} catch (SQLException e) { 
			  
		}
        return null;
    }
    
    public void excluir(int idVendaspacote){
    	vendasPacoteDao = new VendasPacoteDao();
    	try {
    		vendasPacoteDao.excluir(idVendaspacote);
		} catch (SQLException e) { 
			  
		} 
    }
    
    public List<Vendaspacote> listar(String sql){
    	vendasPacoteDao = new VendasPacoteDao();
        try {
			return vendasPacoteDao.listar(sql);
		} catch (SQLException e) { 
			  
		}   
        return null;
    }
}
