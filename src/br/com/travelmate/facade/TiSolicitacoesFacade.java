package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.TiSolicitacoesDao;
import br.com.travelmate.model.Tisolicitacoes;

public class TiSolicitacoesFacade {

	TiSolicitacoesDao tiSolicitacoesDao;
	
	public Tisolicitacoes salvar(Tisolicitacoes tisolicitacoes) {
		tiSolicitacoesDao = new TiSolicitacoesDao();
        try {
            return tiSolicitacoesDao.salvar(tisolicitacoes);
        } catch (SQLException ex) {
            Logger.getLogger(TiSolicitacoesFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Tisolicitacoes> listar(String sql){
    	tiSolicitacoesDao = new TiSolicitacoesDao();
        try {
            return tiSolicitacoesDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TiSolicitacoesFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idtisolicitacoes) {
    	tiSolicitacoesDao = new TiSolicitacoesDao();
        try {
        	tiSolicitacoesDao.excluir(idtisolicitacoes);
        } catch (SQLException ex) {
            Logger.getLogger(TiSolicitacoesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
