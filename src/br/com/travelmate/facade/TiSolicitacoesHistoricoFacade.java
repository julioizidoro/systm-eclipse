package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.TiSolicitacoesHistoricoDao;
import br.com.travelmate.model.Tisolicitacoeshistorico;

public class TiSolicitacoesHistoricoFacade {

	TiSolicitacoesHistoricoDao tiSolicitacoesHistoricoDao;
	
	public Tisolicitacoeshistorico salvar(Tisolicitacoeshistorico tisolicitacoeshistorico) {
		tiSolicitacoesHistoricoDao = new TiSolicitacoesHistoricoDao();
        try {
            return tiSolicitacoesHistoricoDao.salvar(tisolicitacoeshistorico);
        } catch (SQLException ex) {
            Logger.getLogger(TiSolicitacoesHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Tisolicitacoeshistorico> listar(String sql){
    	tiSolicitacoesHistoricoDao = new TiSolicitacoesHistoricoDao();
        try {
            return tiSolicitacoesHistoricoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TiSolicitacoesHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idtisolicitacoeshistorico) {
    	tiSolicitacoesHistoricoDao = new TiSolicitacoesHistoricoDao();
        try {
        	tiSolicitacoesHistoricoDao.excluir(idtisolicitacoeshistorico);
        } catch (SQLException ex) {
            Logger.getLogger(TiSolicitacoesHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
