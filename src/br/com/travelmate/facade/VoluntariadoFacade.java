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

import javax.swing.JOptionPane;


import br.com.travelmate.dao.VoluntariadoDao;
import br.com.travelmate.model.Controlevoluntariado;
import br.com.travelmate.model.Voluntariado;


/**
 *
 * @author Wolverine
 */
public class VoluntariadoFacade {
    
    VoluntariadoDao voluntariadoDao;
    
    public Voluntariado salvar(Voluntariado voluntariado) {
    	voluntariadoDao=new VoluntariadoDao();
        try {
			return voluntariadoDao.salvar(voluntariado);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public Voluntariado consultar(int idVenda)  {
    	voluntariadoDao = new VoluntariadoDao();
        try {
			return voluntariadoDao.consultar(idVenda);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public void excluir(int idvoluntariado) {
    	voluntariadoDao = new VoluntariadoDao();
        try {
        	voluntariadoDao.excluir(idvoluntariado);
		} catch (SQLException e) {
			  
		}
    }
    
    public List<Voluntariado> lista(String sql) {
    	voluntariadoDao = new VoluntariadoDao();
        try {
            return voluntariadoDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VoluntariadoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
       
    
    public Controlevoluntariado salvar(Controlevoluntariado controle){
    	voluntariadoDao = new VoluntariadoDao();
        try {
            return voluntariadoDao.salvar(controle);
        } catch (SQLException ex) {
            Logger.getLogger(VoluntariadoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Controle voluntariado " + ex);
            return null;
        }
    }
    

    public List<Controlevoluntariado> listaControle(String sql) {
    	voluntariadoDao = new VoluntariadoDao();
        try {
            return voluntariadoDao.listaControle(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VoluntariadoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Controlevoluntariado consultarControle(int idVenda)  {
    	voluntariadoDao = new VoluntariadoDao();
        try {
			return voluntariadoDao.consultarControle(idVenda);
		} catch (SQLException e) {
			  
			return null;
		}
    }
}
