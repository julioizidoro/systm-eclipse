package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.model.Leadsituacao;

public class LeadSituacaoFacade {

	LeadSituacaoDao leadSituacaoDao;
	
	public List<Leadsituacao> lista(String sql) {
		leadSituacaoDao = new LeadSituacaoDao();
        try {
            return leadSituacaoDao.lista(sql);
                    } catch (SQLException ex) {
            Logger.getLogger(LeadSituacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Leadsituacao consultar(String sql) {
    	leadSituacaoDao = new LeadSituacaoDao();
        try { 
            return leadSituacaoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(LeadSituacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Leadsituacao salvar(Leadsituacao lead) {
    	leadSituacaoDao = new LeadSituacaoDao();
        try {
            return leadSituacaoDao.salvar(lead);
        } catch (SQLException ex) {
            Logger.getLogger(LeadSituacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idLead){
    	leadSituacaoDao = new LeadSituacaoDao();
    	try {
    		leadSituacaoDao.excluir(idLead);
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
    }
}
