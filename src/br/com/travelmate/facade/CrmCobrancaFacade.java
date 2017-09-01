package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.CrmCobrancaDao;
import br.com.travelmate.model.Crmcobranca;

public class CrmCobrancaFacade {
	
	CrmCobrancaDao crmCobrancaDao;
	
	 public Crmcobranca salvar(Crmcobranca crmcobranca){
		 crmCobrancaDao = new CrmCobrancaDao();
        try {
            return crmCobrancaDao.salvar(crmcobranca);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Cursos " + ex);
            return null;
        }
    }
    
    
    
    public Crmcobranca consultar(int idcrm) {
    	crmCobrancaDao = new CrmCobrancaDao();
        try {
            return crmCobrancaDao.consultar(idcrm);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar Curso " + ex);
            return null;
        }
    }
    
    public void excluir(int idCurso) {
    	crmCobrancaDao = new CrmCobrancaDao();
        try {
        	crmCobrancaDao.excluir(idCurso);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Curso " + ex);
        }
    }
   
    
    public List<Crmcobranca> lista(String sql) {
    	crmCobrancaDao = new CrmCobrancaDao();
        try {
            return crmCobrancaDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
