/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.LadiesDao;
import br.com.travelmate.model.Ladies;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

   

public class LadiesFacade {
    
     LadiesDao ladiesDao;
     
    public Ladies salvar(Ladies ladies){
        ladiesDao = new LadiesDao();
        try {
            return ladiesDao.salvar(ladies);
        } catch (Exception ex) {
            Logger.getLogger(LadiesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Ladies> listar(String nome){
        ladiesDao = new LadiesDao();
        try {
           return ladiesDao.listar(nome);
        } catch (Exception ex) {
             Logger.getLogger(LadiesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
