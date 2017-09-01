/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.TerceirosDao;
import br.com.travelmate.model.Terceiros;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class TerceirosFacade {
    
    TerceirosDao terceirosDao;
    
    public List<Terceiros> lista() {
        terceirosDao = new TerceirosDao();
        try {
            return terceirosDao.lista();
                    } catch (SQLException ex) {
            Logger.getLogger(TerceirosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Terceiros consultar(int idUsuario) {
    	terceirosDao = new TerceirosDao();
        try {
            return terceirosDao.consultar(idUsuario);
        } catch (SQLException ex) {
            Logger.getLogger(TerceirosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Terceiros salvar(Terceiros terceiros) {
    	terceirosDao = new TerceirosDao();
        try {
            return terceirosDao.salvar(terceiros);
        } catch (SQLException ex) {
            Logger.getLogger(TerceirosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idTerceiros) {
    	terceirosDao = new TerceirosDao();
        try {
        	terceirosDao.excluir(idTerceiros);
        } catch (SQLException ex) {
            Logger.getLogger(TerceirosFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
