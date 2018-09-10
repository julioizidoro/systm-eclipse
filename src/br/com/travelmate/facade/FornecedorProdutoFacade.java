/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.FornecedorProdutoDao;
import br.com.travelmate.model.Fornecedorproduto;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamila
 */
public class FornecedorProdutoFacade {
    
    FornecedorProdutoDao fornecedorProdutoDao;
    
    public Fornecedorproduto salvar(Fornecedorproduto fornecedor) {
    	fornecedorProdutoDao = new FornecedorProdutoDao();
        try {
            return fornecedorProdutoDao.salvar(fornecedor);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedorproduto> listar(String sql) {
    	fornecedorProdutoDao = new FornecedorProdutoDao();
        try {
            return fornecedorProdutoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedorproduto consultar(int id)  {
    	fornecedorProdutoDao = new FornecedorProdutoDao();  
        try {
            return fornecedorProdutoDao.consultar(id);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
