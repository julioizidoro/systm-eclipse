package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VendaPendenciaDao;
import br.com.travelmate.model.Vendapendencia;

public class VendaPendenciaFacade {
	
	VendaPendenciaDao vendaPendenciaDao;
	
	public Vendapendencia salvar(Vendapendencia vendapendencia) {
		vendaPendenciaDao = new  VendaPendenciaDao();
        try {
            return vendaPendenciaDao.salvar(vendapendencia);
        } catch (SQLException ex) {
            Logger.getLogger(VendaPendenciaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Vendapendencia consultarVendas(int idVenda) {
    	vendaPendenciaDao = new VendaPendenciaDao();
        try {
            return vendaPendenciaDao.consultarVendas(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(VendaPendenciaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
    public List<Vendapendencia> lista(String sql) {
    	vendaPendenciaDao = new VendaPendenciaDao();
        try {
            return vendaPendenciaDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VendaPendenciaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idvenda) {
    	vendaPendenciaDao = new VendaPendenciaDao();
        try {
        	vendaPendenciaDao.excluir(idvenda);
        } catch (SQLException ex) {
            Logger.getLogger(VendaPendenciaFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
