package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.LancamentoCartaoCreditoDao;
import br.com.travelmate.model.Lancamentocartaocredito;;

public class LancamentoCartaoCreditoFacade {

	LancamentoCartaoCreditoDao lancamentoCartaoCreditoDao;
    
    public Lancamentocartaocredito salvar(Lancamentocartaocredito lancamentocartaocredito) {
    	lancamentoCartaoCreditoDao = new LancamentoCartaoCreditoDao();
        try {
            return lancamentoCartaoCreditoDao.salvar(lancamentocartaocredito);
        } catch (SQLException ex) {
            Logger.getLogger(LancamentoCartaoCreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Lancamentocartaocredito> listar(String sql){
    	lancamentoCartaoCreditoDao = new LancamentoCartaoCreditoDao();
        try {
			return lancamentoCartaoCreditoDao.listar(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
   
    public Lancamentocartaocredito consultar(int idlancamentocartaocredito) throws SQLException{
    	lancamentoCartaoCreditoDao = new LancamentoCartaoCreditoDao();
        return lancamentoCartaoCreditoDao.consultar(idlancamentocartaocredito);
    }
    
    public Lancamentocartaocredito consultarVenda(int idvenda){
    	lancamentoCartaoCreditoDao = new LancamentoCartaoCreditoDao();
        try {
			return lancamentoCartaoCreditoDao.consultarVenda(idvenda);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    public void excluir(int idlancamentocartaocredito) {
    	lancamentoCartaoCreditoDao = new LancamentoCartaoCreditoDao();
        try {
        	lancamentoCartaoCreditoDao.excluir(idlancamentocartaocredito);
        } catch (SQLException ex) {
            Logger.getLogger(LancamentoCartaoCreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
