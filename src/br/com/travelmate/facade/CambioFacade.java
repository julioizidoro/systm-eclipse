/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class CambioFacade {
    
    CambioDao cambioDao;
    
    public Cambio salvar(Cambio cambio) {
        cambioDao = new CambioDao();
        try {
            return cambioDao.salvar(cambio);
        } catch (SQLException ex) {
            Logger.getLogger(CambioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(Cambio cambio) {
        cambioDao = new CambioDao();
        try {
            cambioDao.excluir(cambio);
        } catch (SQLException ex) {
            Logger.getLogger(CambioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Moedas> listaMoedas() {
        cambioDao = new CambioDao();
        try {
            return cambioDao.listaMoedas();
        } catch (SQLException ex) {
            Logger.getLogger(CambioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cambio consultar(int idCambio) {
        cambioDao = new CambioDao();
        try {
            return cambioDao.consultar(idCambio);
        } catch (SQLException ex) {
            Logger.getLogger(CambioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cambio consultarSigla(String data,String sigla) {
        cambioDao = new CambioDao();
        try {
            return cambioDao.consultarSigla(data, sigla);
        } catch (SQLException ex) {
            Logger.getLogger(CambioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cambio consultarCambioMoeda(String data, int idMoeda) {
        cambioDao = new CambioDao();
        try {
            return cambioDao.consultarCambioMoeda(data, idMoeda);
        } catch (SQLException ex) {
            Logger.getLogger(CambioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Moedas consultarMoeda(int idMoeda) {
        cambioDao = new CambioDao();
        try {
            return cambioDao.consultarMoeda(idMoeda);
        } catch (SQLException ex) {
            Logger.getLogger(CambioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Cambio> listar(String data) {
        cambioDao = new CambioDao();
        try {
            return cambioDao.listar(data);
        } catch (SQLException ex) {
            Logger.getLogger(CambioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Cambio> listarCambioPais(String data, Pais pais) {
        cambioDao = new CambioDao();
        try {
            return cambioDao.listarCambioPais(data, pais);
        } catch (SQLException ex) {
            Logger.getLogger(CambioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
