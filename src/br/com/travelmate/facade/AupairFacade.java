/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.AupairDao;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Controleaupair;

/**
 *
 * @author Wolverine
 */
public class AupairFacade {
    
    AupairDao aupairDao;
    
    public Aupair salvar(Aupair aupair){
    	aupairDao= new AupairDao();
        try {
            return aupairDao.salvar(aupair);
        } catch (SQLException ex) {
            Logger.getLogger(AupairFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Aupair " + ex);
            return null;
        }
    }
    
    
   
    public Aupair consultar(int idVenda) {
    	aupairDao= new AupairDao();
        try {
			return aupairDao.consultar(idVenda);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public List<Aupair> lista(String sql) {
    	aupairDao= new AupairDao();
        try {
            return aupairDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AupairFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Controleaupair salvar(Controleaupair controle){
    	aupairDao= new AupairDao();
        try {
            return aupairDao.salvar(controle);
        } catch (SQLException ex) {
            Logger.getLogger(AupairFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Controle Aupair " + ex);
            return null;
        }
    }
    

    public List<Controleaupair> listaControle(String sql) {
    	aupairDao= new AupairDao();
        try {
            return aupairDao.listaControle(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AupairFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Controleaupair consultarControle(int idVenda)  {
    	aupairDao= new AupairDao();
        try {
			return aupairDao.consultarControle(idVenda);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
}
