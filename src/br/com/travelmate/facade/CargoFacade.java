package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.CargoDao;
import br.com.travelmate.model.Cargo; 

public class CargoFacade {
	
	CargoDao cargoDao;
	
	 public Cargo salvar(Cargo cargo){
		 cargoDao = new CargoDao();
        try {
            return cargoDao.salvar(cargo);
        } catch (SQLException ex) {
            Logger.getLogger(CargoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Cargo " + ex);
            return null;
        }
    } 
    
    public Cargo consultar(String sql) {
    		cargoDao = new CargoDao();
        try {
            return cargoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CargoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar Cargo " + ex);
            return null;
        }
    }
    
    public void excluir(int idCargo) {
    		cargoDao = new CargoDao();
        try {
        	cargoDao.excluir(idCargo);
        } catch (SQLException ex) {
            Logger.getLogger(CargoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Cargo " + ex);
        }
    }
   
    
    public List<Cargo> lista(String sql) {
    		cargoDao = new CargoDao();
        try {
            return cargoDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CargoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
