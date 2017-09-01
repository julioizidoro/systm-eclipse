/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.OCursoProdutoDao;
import br.com.travelmate.model.Ocrusoprodutos;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class OCursoProdutoFacade {
    
    private OCursoProdutoDao oCursoProdutoDao;
    
    public Ocrusoprodutos salvar(Ocrusoprodutos produto) throws SQLException{
        oCursoProdutoDao = new OCursoProdutoDao();
        return oCursoProdutoDao.salvar(produto);
    }
    
    public Ocrusoprodutos consultar(int idOcurso) {
    	oCursoProdutoDao = new OCursoProdutoDao();
        try {
             return oCursoProdutoDao.consultar(idOcurso);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Ocrusoprodutos> listar(int idOcurso) {
    	oCursoProdutoDao = new OCursoProdutoDao();
        try {
            return oCursoProdutoDao.lista(idOcurso);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Ocrusoprodutos> listar(String sql) {
    	oCursoProdutoDao = new OCursoProdutoDao();
        try {
            return oCursoProdutoDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idOcursoprodutos) {
    	oCursoProdutoDao = new OCursoProdutoDao();
        try {
        	oCursoProdutoDao.excluir(idOcursoprodutos);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
