/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
  
import br.com.travelmate.dao.FornecedorFinanceiroProdutoDao;
import br.com.travelmate.model.Fornecedorfinanceiroproduto;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class FornecedorFinanceiroProdutoFacade {
    
    FornecedorFinanceiroProdutoDao fornecedorFinanceiroDao;
    
    public Fornecedorfinanceiroproduto salvar(Fornecedorfinanceiroproduto fornecedorfinanceiro) {
    		fornecedorFinanceiroDao = new FornecedorFinanceiroProdutoDao();
        try {
            return fornecedorFinanceiroDao.salvar(fornecedorfinanceiro);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorFinanceiroProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedorfinanceiroproduto> listar(String sql) {
    		fornecedorFinanceiroDao = new FornecedorFinanceiroProdutoDao();
        try {
            return fornecedorFinanceiroDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorFinanceiroProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedorfinanceiroproduto consultar(String sql){
    		fornecedorFinanceiroDao = new FornecedorFinanceiroProdutoDao();
        try {
            return fornecedorFinanceiroDao.consulta(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorFinanceiroProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
    
    public void excluir(int idFornecedorfinanceiroproduto){
    		fornecedorFinanceiroDao = new FornecedorFinanceiroProdutoDao();
    		try {
    			fornecedorFinanceiroDao.excluir(idFornecedorfinanceiroproduto);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
}
