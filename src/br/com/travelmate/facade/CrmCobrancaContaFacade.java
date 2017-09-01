package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.CrmCobrancaContaDao;
import br.com.travelmate.model.Crmcobrancaconta;

public class CrmCobrancaContaFacade {

	CrmCobrancaContaDao crmCobrancaContaDao;
	
	 public Crmcobrancaconta salvar(Crmcobrancaconta crmcobranca){
		 crmCobrancaContaDao = new CrmCobrancaContaDao();
        try {
            return crmCobrancaContaDao.salvar(crmcobranca);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaContaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Cursos " + ex);
            return null;
        }
    }
    
    
    
    public Crmcobrancaconta consultar(int idcrm) {
    	crmCobrancaContaDao = new CrmCobrancaContaDao();
        try {
            return crmCobrancaContaDao.consultar(idcrm);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaContaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar Curso " + ex);
            return null;
        }
    }
    
    public void excluir(int idCurso) {
    	crmCobrancaContaDao = new CrmCobrancaContaDao();
        try {
        	crmCobrancaContaDao.excluir(idCurso);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaContaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Curso " + ex);
        }
    }
   
    
    public List<Crmcobrancaconta> lista(String sql) {
    	crmCobrancaContaDao = new CrmCobrancaContaDao();
        try {
            return crmCobrancaContaDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CrmCobrancaContaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
