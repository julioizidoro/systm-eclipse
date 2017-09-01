package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VendaPendenciaHistoricoDao;
import br.com.travelmate.model.Vendapendenciahistorico;

public class VendaPendenciaHistoricoFacade {

	VendaPendenciaHistoricoDao vendaPendenciaHistoricoDao;
	
	public Vendapendenciahistorico salvar(Vendapendenciahistorico vendapendenciahistorico) {
		vendaPendenciaHistoricoDao = new  VendaPendenciaHistoricoDao();
        try {
            return vendaPendenciaHistoricoDao.salvar(vendapendenciahistorico);
        } catch (SQLException ex) {
            Logger.getLogger(VendaPendenciaHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Vendapendenciahistorico consultarVendas(int idVenda) {
    	vendaPendenciaHistoricoDao = new VendaPendenciaHistoricoDao();
        try {
            return vendaPendenciaHistoricoDao.consultarVendas(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(VendaPendenciaHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
    public List<Vendapendenciahistorico> lista(String sql) {
    	vendaPendenciaHistoricoDao = new VendaPendenciaHistoricoDao();
        try {
            return vendaPendenciaHistoricoDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VendaPendenciaHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idvenda) {
    	vendaPendenciaHistoricoDao = new VendaPendenciaHistoricoDao();
        try {
        	vendaPendenciaHistoricoDao.excluir(idvenda);
        } catch (SQLException ex) {
            Logger.getLogger(VendaPendenciaHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
