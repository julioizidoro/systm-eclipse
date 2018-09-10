/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;


import br.com.travelmate.dao.ProdutoOrcamentoDao;
import br.com.travelmate.model.Produtosorcamento;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class ProdutoOrcamentoFacade {
    
    ProdutoOrcamentoDao produtoOrcamentoDao;
    
    public Produtosorcamento salvar(Produtosorcamento produto){
        produtoOrcamentoDao = new ProdutoOrcamentoDao();
        try {
            return produtoOrcamentoDao.salvar(produto);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public List<Produtosorcamento> listarProdutosOrcamento(String descricao) {
        produtoOrcamentoDao = new ProdutoOrcamentoDao();
        try {
            return produtoOrcamentoDao.listarProdutosOrcamento(descricao);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Produtosorcamento> listarProdutosOrcamentoSql(String sql) {
        produtoOrcamentoDao = new ProdutoOrcamentoDao();
        try {
            return produtoOrcamentoDao.listarProdutosOrcamentoSql(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Produtosorcamento consultar(int idProdutoOrcamento) {
        produtoOrcamentoDao = new ProdutoOrcamentoDao();
        try {
            return produtoOrcamentoDao.consultar(idProdutoOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Produtosorcamento consultar(String sql) {
        produtoOrcamentoDao = new ProdutoOrcamentoDao();
        try {
            return produtoOrcamentoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
