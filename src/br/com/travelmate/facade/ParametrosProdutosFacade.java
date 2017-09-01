/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.ParametrosProdutosDao;
import br.com.travelmate.model.Parametrosprodutos;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class ParametrosProdutosFacade {
    
    ParametrosProdutosDao parametrosProdutosDao;
    
    public Parametrosprodutos salvar(Parametrosprodutos parametrosprodutos) {
        parametrosProdutosDao = new ParametrosProdutosDao();
        try {
            return parametrosProdutosDao.salvar(parametrosprodutos);
        } catch (SQLException ex) {
            Logger.getLogger(ParametrosProdutosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Parametrosprodutos consultar() {
        parametrosProdutosDao = new ParametrosProdutosDao();
        try {
            return parametrosProdutosDao.consultar();
        } catch (SQLException ex) {
            Logger.getLogger(ParametrosProdutosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
