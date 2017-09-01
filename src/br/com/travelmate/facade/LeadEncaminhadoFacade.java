/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.LeadEncaminhadoDao;
import br.com.travelmate.model.Leadencaminhado;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class LeadEncaminhadoFacade {
    
    private LeadEncaminhadoDao leadEncaminhadoDao;
    
    public Leadencaminhado salvar(Leadencaminhado leadencaminhado){
    	leadEncaminhadoDao = new LeadEncaminhadoDao();
        try {
			return leadEncaminhadoDao.salvar(leadencaminhado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Leadencaminhado consultar(String sql){
    	leadEncaminhadoDao = new LeadEncaminhadoDao();
        try {
			return leadEncaminhadoDao.consultar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idLeadencaminhado){
    	leadEncaminhadoDao = new LeadEncaminhadoDao();
    	try {
    		leadEncaminhadoDao.excluir(idLeadencaminhado);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public List<Leadencaminhado> listar(String sql){
    	leadEncaminhadoDao = new LeadEncaminhadoDao();
        try {
			return leadEncaminhadoDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
        return null;
    }
}
