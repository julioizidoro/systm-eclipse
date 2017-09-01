/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.OcClienteDao;
import br.com.travelmate.model.Occliente;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Wolverine
 */
public class OcClienteFacade {
    
    OcClienteDao ocClienteDao;
    
    public Occliente salvar(Occliente ocCliente){
    	ocClienteDao = new OcClienteDao();
        try {
            return ocClienteDao.salvar(ocCliente);
        } catch (SQLException ex) {
            Logger.getLogger(OcClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Occliente consultar(int idocCliente) {
    	ocClienteDao = new OcClienteDao();
        try {
            return ocClienteDao.consultar(idocCliente);
        } catch (SQLException ex) {
            Logger.getLogger(OcClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Occliente consultarEmail(String email) {
    	ocClienteDao = new OcClienteDao();
        try {
            return ocClienteDao.consultarEmail(email);
        } catch (SQLException ex) {
            Logger.getLogger(OcClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Occliente> consultarNome(String sql) {
    	ocClienteDao = new OcClienteDao();
        try {
            return ocClienteDao.consultarNome(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OcClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Occliente> listar(String sql) {
    	ocClienteDao = new OcClienteDao();
        try {
            return ocClienteDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OcClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
