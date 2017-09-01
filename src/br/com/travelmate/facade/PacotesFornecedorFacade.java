/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
  
import br.com.travelmate.dao.PacotesFornecedorDao; 
import br.com.travelmate.model.Pacotesfornecedor;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class PacotesFornecedorFacade {
    
    PacotesFornecedorDao pacotesFornecedorDao;
    
    public Pacotesfornecedor salvar(Pacotesfornecedor pacotesfornecedor) {
    	pacotesFornecedorDao = new PacotesFornecedorDao();
        try {
            return pacotesFornecedorDao.salvar(pacotesfornecedor);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesFornecedorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pacotesfornecedor> listar(String sql) {
    	pacotesFornecedorDao = new PacotesFornecedorDao();
        try {
            return pacotesFornecedorDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesFornecedorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Pacotesfornecedor consultar(String sql){
    	pacotesFornecedorDao = new PacotesFornecedorDao();
        try {
            return pacotesFornecedorDao.consulta(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesFornecedorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
}
