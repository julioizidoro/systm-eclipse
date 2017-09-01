/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade; 
 
import br.com.travelmate.dao.FaturaBancoDao; 
import br.com.travelmate.model.Faturabanco;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class FaturaBancoFacade {
    
    private FaturaBancoDao faturaBancoDao;
    
    public Faturabanco salvar(Faturabanco fatura){
    	faturaBancoDao = new FaturaBancoDao();
        try {
			return faturaBancoDao.salvar(fatura);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Faturabanco getFatura(String sql){
    	faturaBancoDao = new FaturaBancoDao();
        try {
			return faturaBancoDao.getFatura(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idFaturabanco){
    	faturaBancoDao = new FaturaBancoDao();
    	try {
    		faturaBancoDao.excluir(idFaturabanco);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public List<Faturabanco> listar(String sql){
    	faturaBancoDao = new FaturaBancoDao();
        try {
			return faturaBancoDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
        return null;
    }
}
