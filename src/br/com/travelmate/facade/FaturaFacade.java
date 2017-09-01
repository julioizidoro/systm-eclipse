/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.FaturaDao; 
import br.com.travelmate.model.Fatura; 
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Wolverine
 */
public class FaturaFacade {
    
    private FaturaDao faturaDao;
    
    public Fatura salvar(Fatura fatura){
    	faturaDao = new FaturaDao();
        try {
			return faturaDao.salvar(fatura);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Fatura getFatura(String sql){
    	faturaDao = new FaturaDao();
        try {
			return faturaDao.getFatura(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idFatura){
    	faturaDao = new FaturaDao();
    	try {
			faturaDao.excluir(idFatura);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public List<Fatura> listar(String sql){
    	faturaDao = new FaturaDao();
        try {
			return faturaDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
        return null;
    }
}
