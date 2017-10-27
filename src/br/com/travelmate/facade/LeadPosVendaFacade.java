/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
  
import br.com.travelmate.dao.LeadPosVendaDao; 
import br.com.travelmate.model.Leadposvenda;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class LeadPosVendaFacade {
    
    private LeadPosVendaDao leadPosVendaDao;
    
    public Leadposvenda salvar(Leadposvenda leadposvenda){
    	leadPosVendaDao = new LeadPosVendaDao();
        try {
			return leadPosVendaDao.salvar(leadposvenda);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Leadposvenda consultar(String sql){
    	leadPosVendaDao = new LeadPosVendaDao();
        try {
			return leadPosVendaDao.consultar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idLeadposvenda){
    	leadPosVendaDao = new LeadPosVendaDao();
    	try {
    		leadPosVendaDao.excluir(idLeadposvenda);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public List<Leadposvenda> listar(String sql){
    	leadPosVendaDao = new LeadPosVendaDao();
        try {
			return leadPosVendaDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
        return null;
    }
}
