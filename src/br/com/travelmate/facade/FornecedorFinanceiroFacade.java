/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.FornecedorFinanceiroDao; 
import br.com.travelmate.model.Fornecedorfinanceiro;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class FornecedorFinanceiroFacade {
    
    FornecedorFinanceiroDao fornecedorFinanceiroDao;
    
    public Fornecedorfinanceiro salvar(Fornecedorfinanceiro fornecedorfinanceiro) {
    	fornecedorFinanceiroDao = new FornecedorFinanceiroDao();
        try {
            return fornecedorFinanceiroDao.salvar(fornecedorfinanceiro);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorFinanceiroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedorfinanceiro> listar(String sql) {
    	fornecedorFinanceiroDao = new FornecedorFinanceiroDao();
        try {
            return fornecedorFinanceiroDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorFinanceiroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedorfinanceiro consultar(int idFornecedor){
    	fornecedorFinanceiroDao = new FornecedorFinanceiroDao();
        try {
            return fornecedorFinanceiroDao.consulta(idFornecedor);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorFinanceiroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
}
