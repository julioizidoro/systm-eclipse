/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.ProdutoDao;
import br.com.travelmate.model.Produtos;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class ProdutoFacade {
    
    private ProdutoDao produtoDao;
    
    public Produtos salvar(Produtos produto) {
        produtoDao = new ProdutoDao();
        try {
            return produtoDao.salvar(produto);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Produtos> listarProdutos(String descricao) {
        produtoDao = new ProdutoDao();
        try {
            return produtoDao.listarProdutos(descricao);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Produtos> listarProdutosSql(String sql) {
        produtoDao = new ProdutoDao();
        try {
            return produtoDao.listarProdutosSql(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Produtos> listarProdutos() {
        produtoDao = new ProdutoDao();
        try {
            return produtoDao.listarProdutos();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Produtos consultar(int idProduto) {
        produtoDao = new ProdutoDao();
        try {
            return produtoDao.consultar(idProduto);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
