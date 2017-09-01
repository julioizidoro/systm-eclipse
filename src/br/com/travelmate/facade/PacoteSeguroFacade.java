/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.PacoteSeguroDao;
import br.com.travelmate.model.Pacoteseguro;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PacoteSeguroFacade {
    
    PacoteSeguroDao pacoteSeguroDao;
    
    public Pacoteseguro salvar(Pacoteseguro pacoteSeguro) {
    	pacoteSeguroDao = new PacoteSeguroDao();
        try {
            return pacoteSeguroDao.salvar(pacoteSeguro);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacoteSeguro) {
       pacoteSeguroDao = new PacoteSeguroDao();
        try {
        	pacoteSeguroDao.excluir(idPacoteSeguro);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public List<Pacoteseguro> consultar(String sql) {
    	 pacoteSeguroDao = new PacoteSeguroDao();
        try {
            return pacoteSeguroDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PacoteSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
