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

import br.com.travelmate.dao.ValoresAupairDao;
import br.com.travelmate.model.Valoresaupair;
/**
 *
 * @author Wolverine
 */
public class ValorAupairFacade {

    private ValoresAupairDao valoresAupairDao;

    

    public Valoresaupair salvar(Valoresaupair valor) {
    	valoresAupairDao = new ValoresAupairDao();
        try {
            return valoresAupairDao.salvar(valor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresAupairDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Valoresaupair consultar(int idvalor)  {
    	valoresAupairDao = new ValoresAupairDao();
        try {
            return valoresAupairDao.consultar(idvalor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresAupairDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Valoresaupair> listar(String sql) {
    	valoresAupairDao = new ValoresAupairDao();
        try {
            return valoresAupairDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresAupairDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
