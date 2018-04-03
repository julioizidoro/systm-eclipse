/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;


import br.com.travelmate.dao.IdiomaDao;
import br.com.travelmate.model.Idioma;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamila Rodrigues
 */
public class IdiomaFacade {
    
    private IdiomaDao idiomaDao;
    
     public Idioma salvar(Idioma idioma){
        idiomaDao = new IdiomaDao();
        try {
            return idiomaDao.salvar(idioma);
        } catch (SQLException ex) {
            Logger.getLogger(IdiomaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Idioma> listar(String sql){
        idiomaDao = new IdiomaDao();
        try {
            return idiomaDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(IdiomaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
   
   
   public Idioma consultar(String sql) {
   	idiomaDao = new IdiomaDao();
       try { 
           return idiomaDao.consultar(sql);
       } catch (SQLException ex) {
           Logger.getLogger(IdiomaDao.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
    
}
