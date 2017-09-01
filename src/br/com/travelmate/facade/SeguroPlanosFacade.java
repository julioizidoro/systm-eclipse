/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
   
import br.com.travelmate.dao.SeguroPlanosDao;
import br.com.travelmate.model.Seguroplanos;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamila
 */
public class SeguroPlanosFacade {
    
    SeguroPlanosDao seguroPlanosDao;
    
    public Seguroplanos salvar(Seguroplanos seguroplanos) {
    		seguroPlanosDao = new SeguroPlanosDao();
        try {
            return seguroPlanosDao.salvar(seguroplanos);
        } catch (SQLException ex) {
            Logger.getLogger(SeguroPlanosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Seguroplanos> listar(String sql) {
    		seguroPlanosDao = new SeguroPlanosDao();
        try {
            return seguroPlanosDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SeguroPlanosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Seguroplanos consultar(String sql){
    		seguroPlanosDao = new SeguroPlanosDao();
        try {
            return seguroPlanosDao.consulta(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SeguroPlanosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
    
    public void excluir(int idSeguroplanos){
    		seguroPlanosDao = new SeguroPlanosDao();
    		try {
    			seguroPlanosDao.excluir(idSeguroplanos);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
}
