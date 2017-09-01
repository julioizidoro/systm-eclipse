/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.FiltroOrcamentoProdutoDao;
import br.com.travelmate.model.Filtroorcamentoproduto;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class FiltroOrcamentoProdutoFacade {
    
    FiltroOrcamentoProdutoDao filtroOrcamentoProdutoDao;
    
    public Filtroorcamentoproduto salvar(Filtroorcamentoproduto filtro) {
        filtroOrcamentoProdutoDao = new FiltroOrcamentoProdutoDao();
        try {
            return filtroOrcamentoProdutoDao.salvar(filtro);
        } catch (SQLException ex) {
            Logger.getLogger(FiltroOrcamentoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idFiltro) {
        filtroOrcamentoProdutoDao = new FiltroOrcamentoProdutoDao();
        try {
            filtroOrcamentoProdutoDao.excluir(idFiltro);
        } catch (SQLException ex) {
            Logger.getLogger(FiltroOrcamentoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Filtroorcamentoproduto pesquisar(int idProduto, int idProdutoOrcamento) {
        filtroOrcamentoProdutoDao = new FiltroOrcamentoProdutoDao();
        try {
            return filtroOrcamentoProdutoDao.pesquisar(idProduto, idProdutoOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(FiltroOrcamentoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public Filtroorcamentoproduto pesquisar(int idProduto, String nome) {
        filtroOrcamentoProdutoDao = new FiltroOrcamentoProdutoDao();
        try {
            return filtroOrcamentoProdutoDao.pesquisar(idProduto, nome);
        } catch (SQLException ex) {
            Logger.getLogger(FiltroOrcamentoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public List<Filtroorcamentoproduto> pesquisar(String sql) {
        filtroOrcamentoProdutoDao = new FiltroOrcamentoProdutoDao();
        try {
            return filtroOrcamentoProdutoDao.pesquisar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FiltroOrcamentoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Filtroorcamentoproduto> listarProdutosOrcamento(int idProduto) {
    	filtroOrcamentoProdutoDao = new FiltroOrcamentoProdutoDao();
        try {
            return filtroOrcamentoProdutoDao.listarProdutosOrcamento(idProduto);
        } catch (SQLException ex) {
            Logger.getLogger(FiltroOrcamentoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
}
