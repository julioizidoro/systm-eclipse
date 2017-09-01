/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade; 

import br.com.travelmate.dao.FaturaFaturaAjusteDao; 
import br.com.travelmate.model.Faturafaturaajuste; 

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class FaturaFaturaAjusteFacade {
    
    private FaturaFaturaAjusteDao faturaDao;
    
    public Faturafaturaajuste salvar(Faturafaturaajuste fatura){
    	faturaDao = new FaturaFaturaAjusteDao();
        try {
			return faturaDao.salvar(fatura);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Faturafaturaajuste getFatura(String sql){
    	faturaDao = new FaturaFaturaAjusteDao();
        try {
			return faturaDao.getFatura(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idFaturafaturaajuste){
    	faturaDao = new FaturaFaturaAjusteDao();
    	try {
			faturaDao.excluir(idFaturafaturaajuste);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public List<Faturafaturaajuste> listar(String sql){
    	faturaDao = new FaturaFaturaAjusteDao();
        try {
			return faturaDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
        return null;
    }
}
