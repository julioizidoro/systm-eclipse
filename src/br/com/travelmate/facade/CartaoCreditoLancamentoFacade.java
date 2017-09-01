/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
  
import br.com.travelmate.dao.CartaoCreditoLancamentoDao; 
import br.com.travelmate.model.Cartaocreditolancamento;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class CartaoCreditoLancamentoFacade {
    
    CartaoCreditoLancamentoDao cartaoCreditoDao;
    
    public List<Cartaocreditolancamento> listar() {
    	cartaoCreditoDao = new CartaoCreditoLancamentoDao();
        try {
            return cartaoCreditoDao.listar();
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoLancamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public List<Cartaocreditolancamento> listar(String sql) {
    	cartaoCreditoDao = new CartaoCreditoLancamentoDao();
        try {
            return cartaoCreditoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoLancamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cartaocreditolancamento salvar(Cartaocreditolancamento cartaocredito) {
    	cartaoCreditoDao = new CartaoCreditoLancamentoDao();
        try {
            return cartaoCreditoDao.salvar(cartaocredito);
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoLancamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cartaocreditolancamento consultar(String sql) {
    	cartaoCreditoDao = new CartaoCreditoLancamentoDao();
        try {
            return cartaoCreditoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoLancamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
