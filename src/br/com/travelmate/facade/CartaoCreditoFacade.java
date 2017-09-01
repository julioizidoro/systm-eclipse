/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.CartaoCreditoDao; 
import br.com.travelmate.model.Cartaocredito;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class CartaoCreditoFacade {
    
    CartaoCreditoDao cartaoCreditoDao;
    
    public List<Cartaocredito> listar() {
    	cartaoCreditoDao = new CartaoCreditoDao();
        try {
            return cartaoCreditoDao.listar();
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public List<Cartaocredito> listar(String sql) {
    	cartaoCreditoDao = new CartaoCreditoDao();
        try {
            return cartaoCreditoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cartaocredito salvar(Cartaocredito cartaocredito) {
    	cartaoCreditoDao = new CartaoCreditoDao();
        try {
            return cartaoCreditoDao.salvar(cartaocredito);
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cartaocredito consultar(String sql) {
    	cartaoCreditoDao = new CartaoCreditoDao();
        try {
            return cartaoCreditoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
