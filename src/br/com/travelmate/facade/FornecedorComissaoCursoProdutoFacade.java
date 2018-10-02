/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.FornecedorComissaoCursoProdutoDao;
import br.com.travelmate.model.Fornecedorcomissaocursoproduto;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class FornecedorComissaoCursoProdutoFacade {
    
    FornecedorComissaoCursoProdutoDao fornecedorComissaoCursoProdutoDao;

    
    public Fornecedorcomissaocursoproduto salvar(Fornecedorcomissaocursoproduto fornecedor) {
        fornecedorComissaoCursoProdutoDao = new FornecedorComissaoCursoProdutoDao();
        try {
            return fornecedorComissaoCursoProdutoDao.salvar(fornecedor);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorComissaoCursoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedorcomissaocursoproduto> listar(int idFornecedorcomissaocurso) {
        fornecedorComissaoCursoProdutoDao = new FornecedorComissaoCursoProdutoDao();
        try {
            return fornecedorComissaoCursoProdutoDao.listar(idFornecedorcomissaocurso);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorComissaoCursoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedorcomissaocursoproduto> listar(String sql) {
        fornecedorComissaoCursoProdutoDao = new FornecedorComissaoCursoProdutoDao();
        try {
            return fornecedorComissaoCursoProdutoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorComissaoCursoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idFornecedorcomissaocursoproduto) {
    	fornecedorComissaoCursoProdutoDao = new FornecedorComissaoCursoProdutoDao();
        try {
        	fornecedorComissaoCursoProdutoDao.excluir(idFornecedorcomissaocursoproduto);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorComissaoCursoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Fornecedorcomissaocursoproduto pesquisar(int idfornecedorcomissaocurso, int idprodutoOrcamento) {
    	fornecedorComissaoCursoProdutoDao = new FornecedorComissaoCursoProdutoDao();
    	try {
			return fornecedorComissaoCursoProdutoDao.pesquisar(idfornecedorcomissaocurso, idprodutoOrcamento);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
}
