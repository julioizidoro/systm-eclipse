package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.CrmCobrancaHistoricoDao;
import br.com.travelmate.model.Crmcobrancahistorico;

public class CrmCobrancaHistoricoFacade {
	
	CrmCobrancaHistoricoDao crmCobrancaHistoricoDao;
	
	 public Crmcobrancahistorico salvar(Crmcobrancahistorico crmcobranca){
		 crmCobrancaHistoricoDao = new CrmCobrancaHistoricoDao();
        try {
            return crmCobrancaHistoricoDao.salvar(crmcobranca);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Cursos " + ex);
            return null;
        }
    }
    
    
    
    public Crmcobrancahistorico consultar(int idcrm) {
    	crmCobrancaHistoricoDao = new CrmCobrancaHistoricoDao();
        try {
            return crmCobrancaHistoricoDao.consultar(idcrm);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar Curso " + ex);
            return null;
        }
    }
    
    public void excluir(int idCurso) {
    	crmCobrancaHistoricoDao = new CrmCobrancaHistoricoDao();
        try {
        	crmCobrancaHistoricoDao.excluir(idCurso);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Curso " + ex);
        }
    }
   
    
    public List<Crmcobrancahistorico> lista(String sql) {
    	crmCobrancaHistoricoDao = new CrmCobrancaHistoricoDao();
        try {
            return crmCobrancaHistoricoDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaHistoricoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
