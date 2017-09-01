package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.CursoDao;
import br.com.travelmate.dao.LeadHistoricoDao; 
import br.com.travelmate.model.Leadhistorico; 

public class LeadHistoricoFacade {

	LeadHistoricoDao leadHistoricoDao;
	
	public List<Leadhistorico> lista(String sql) {
		leadHistoricoDao = new LeadHistoricoDao();
        try {
            return leadHistoricoDao.lista(sql);
                    } catch (SQLException ex) {
            Logger.getLogger(LeadHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Leadhistorico consultar(String sql) {
    	leadHistoricoDao = new LeadHistoricoDao();
        try { 
            return leadHistoricoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(LeadHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Leadhistorico salvar(Leadhistorico leadhistorico) {
    	leadHistoricoDao = new LeadHistoricoDao();
        try {
            return leadHistoricoDao.salvar(leadhistorico);
        } catch (SQLException ex) {
            Logger.getLogger(LeadHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idCurso) {
    	leadHistoricoDao = new LeadHistoricoDao();
        try {
        	leadHistoricoDao.excluir(idCurso);
        } catch (SQLException ex) {
            Logger.getLogger(LeadHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir historico " + ex);
        }
    }
     
}
