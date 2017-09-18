package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
 
import br.com.travelmate.dao.TraducaoJuramentadaDao; 
import br.com.travelmate.model.Traducaojuramentada;

public class TraducaoJuramentadaFacade {
	
	TraducaoJuramentadaDao traducaoJuramentadaDao;
	
	 public Traducaojuramentada salvar(Traducaojuramentada traducaojuramentada){
		traducaoJuramentadaDao = new TraducaoJuramentadaDao();
        try {
            return traducaoJuramentadaDao.salvar(traducaojuramentada);
        } catch (SQLException ex) {
            Logger.getLogger(TraducaoJuramentadaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar " + ex);
            return null;
        }
    }
    
    
    
    public Traducaojuramentada consultar(String sql) {
    		traducaoJuramentadaDao = new TraducaoJuramentadaDao();
        try {
            return traducaoJuramentadaDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TraducaoJuramentadaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar " + ex);
            return null;
        }
    }
    
    public void excluir(int id) {
    		traducaoJuramentadaDao = new TraducaoJuramentadaDao();
        try {
        	traducaoJuramentadaDao.excluir(id);
        } catch (SQLException ex) {
            Logger.getLogger(TraducaoJuramentadaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir " + ex);
        }
    }
   
    
    public List<Traducaojuramentada> lista(String sql) {
    		traducaoJuramentadaDao = new TraducaoJuramentadaDao();
        try {
            return traducaoJuramentadaDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TraducaoJuramentadaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
