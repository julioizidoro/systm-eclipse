/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.UnidadeNegocioDao;
import br.com.travelmate.model.Unidadenegocio;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class UnidadeNegocioFacade {
    
    UnidadeNegocioDao unidadeNegocioDao;
    
    public Unidadenegocio salvar(Unidadenegocio unidadeNegocio) {
        unidadeNegocioDao = new UnidadeNegocioDao();
        try {
            return unidadeNegocioDao.salvar(unidadeNegocio);
        } catch (SQLException ex) {
            Logger.getLogger(UnidadeNegocioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Unidadenegocio consultar(int idUnidadeNegocio) {
        unidadeNegocioDao = new UnidadeNegocioDao();
        try {
            return unidadeNegocioDao.consultar(idUnidadeNegocio);
        } catch (SQLException ex) {
            Logger.getLogger(UnidadeNegocioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Unidadenegocio> listar() {
        unidadeNegocioDao = new UnidadeNegocioDao();
        try {
            return unidadeNegocioDao.listar();
        } catch (Exception ex) {
            Logger.getLogger(UnidadeNegocioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Unidadenegocio> listar(boolean situacao) {
        unidadeNegocioDao = new UnidadeNegocioDao();
        try {
            return unidadeNegocioDao.listar(situacao);
        } catch (Exception ex) {
            Logger.getLogger(UnidadeNegocioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Unidadenegocio> listarContasPagar() {
        unidadeNegocioDao = new UnidadeNegocioDao();
        try {
            return unidadeNegocioDao.listarContasPagar();
        } catch (Exception ex) {
            Logger.getLogger(UnidadeNegocioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public List<Unidadenegocio> listar(String nome) {
        unidadeNegocioDao = new UnidadeNegocioDao();
        try {
            return unidadeNegocioDao.listar(nome);
        } catch (Exception ex) {
            Logger.getLogger(UnidadeNegocioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
