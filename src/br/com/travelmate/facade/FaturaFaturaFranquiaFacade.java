/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade; 

import br.com.travelmate.dao.FaturaFaturaFranquiasDao; 
import br.com.travelmate.model.Faturafaturafraquias;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Wolverine
 */
public class FaturaFaturaFranquiaFacade {
    
    private FaturaFaturaFranquiasDao faturaDao;
    
    public Faturafaturafraquias salvar(Faturafaturafraquias fatura){
    	faturaDao = new FaturaFaturaFranquiasDao();
        try {
			return faturaDao.salvar(fatura);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Faturafaturafraquias getFatura(String sql){
    	faturaDao = new FaturaFaturaFranquiasDao();
        try {
			return faturaDao.getFatura(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idFaturafaturafraquias){
    	faturaDao = new FaturaFaturaFranquiasDao();
    	try {
			faturaDao.excluir(idFaturafaturafraquias);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public List<Faturafaturafraquias> listar(String sql){
    	faturaDao = new FaturaFaturaFranquiasDao();
        try {
			return faturaDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
        return null;
    }
}
