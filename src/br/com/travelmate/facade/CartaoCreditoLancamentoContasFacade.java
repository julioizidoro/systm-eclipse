package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.CartaoCreditoLancamentoContasDao;
import br.com.travelmate.model.Cartaocreditolancamentocontas;

public class CartaoCreditoLancamentoContasFacade {

	CartaoCreditoLancamentoContasDao cartaoCreditoLancamentoContasDao;
     
    
    public List<Cartaocreditolancamentocontas> listar(String sql) {
    	cartaoCreditoLancamentoContasDao = new CartaoCreditoLancamentoContasDao();
        try {
            return cartaoCreditoLancamentoContasDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoLancamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cartaocreditolancamentocontas salvar(Cartaocreditolancamentocontas cartaocredito) {
    	cartaoCreditoLancamentoContasDao = new CartaoCreditoLancamentoContasDao();
        try {
            return cartaoCreditoLancamentoContasDao.salvar(cartaocredito);
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoLancamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cartaocreditolancamentocontas consultar(String sql) {
    	cartaoCreditoLancamentoContasDao = new CartaoCreditoLancamentoContasDao();
        try {
            return cartaoCreditoLancamentoContasDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CartaoCreditoLancamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idCargo) {
    	cartaoCreditoLancamentoContasDao = new CartaoCreditoLancamentoContasDao();
	    try {
	    	cartaoCreditoLancamentoContasDao.excluir(idCargo);
	    } catch (SQLException ex) {
	        Logger.getLogger(CartaoCreditoLancamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, "Erro Excluir Cargo " + ex);
	    }
    }
}
