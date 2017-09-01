package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VendaMotivoPendenciaDao;
import br.com.travelmate.model.Vendamotivopendencia;

public class VendaMotivoPendenciaFacade {
	
	VendaMotivoPendenciaDao vendaMotivoPendenciaDao;
	
	public Vendamotivopendencia salvar(Vendamotivopendencia vendamotivopendencia) {
		vendaMotivoPendenciaDao = new  VendaMotivoPendenciaDao();
        try {
            return vendaMotivoPendenciaDao.salvar(vendamotivopendencia);
        } catch (SQLException ex) {
            Logger.getLogger(VendaMotivoPendenciaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Vendamotivopendencia consultarVendas(int idVenda) {
    	vendaMotivoPendenciaDao = new VendaMotivoPendenciaDao();
        try {
            return vendaMotivoPendenciaDao.consultarVendas(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(VendaMotivoPendenciaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
    public List<Vendamotivopendencia> lista(String sql) {
    	vendaMotivoPendenciaDao = new VendaMotivoPendenciaDao();
        try {
            return vendaMotivoPendenciaDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VendaMotivoPendenciaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idvenda) {
    	vendaMotivoPendenciaDao = new VendaMotivoPendenciaDao();
        try {
        	vendaMotivoPendenciaDao.excluir(idvenda);
        } catch (SQLException ex) {
            Logger.getLogger(VendaMotivoPendenciaFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
