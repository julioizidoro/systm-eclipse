/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.FornecedorCidadeGuiaDao;
import br.com.travelmate.model.Fornecedorcidadeguia;

/**
 *
 * @author Kamila
 */
public class FornecedorCidadeGuiaFacade {
    
    FornecedorCidadeGuiaDao fornecedorCidadeGuiaDao;
     
    
    public List<Fornecedorcidadeguia> listar(String sql) {
    	fornecedorCidadeGuiaDao = new FornecedorCidadeGuiaDao();
        try {
            return fornecedorCidadeGuiaDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeGuiaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
     
}
