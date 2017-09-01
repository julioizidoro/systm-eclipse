/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.FornecedorDao;
import br.com.travelmate.model.Fornecedor;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamila
 */
public class FornecedorFacade {
    
    FornecedorDao fornecedorDao;
    
    public Fornecedor salvar(Fornecedor fornecedor) {
        fornecedorDao = new FornecedorDao();
        try {
            return fornecedorDao.salvar(fornecedor);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedor> listar(String sql) {
        fornecedorDao = new FornecedorDao();
        try {
            return fornecedorDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedor consultar(int id)  {
    	fornecedorDao = new FornecedorDao();
        try {
            return fornecedorDao.consultar(id);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
