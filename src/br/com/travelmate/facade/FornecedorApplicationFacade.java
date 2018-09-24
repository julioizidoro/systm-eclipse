package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.FornecedorApplicationDao;
import br.com.travelmate.model.Fornecedorapplication;


public class FornecedorApplicationFacade {

	FornecedorApplicationDao fornecedorApplicationDao;
	
	 public Fornecedorapplication salvar(Fornecedorapplication Fornecedorapplication){
		 fornecedorApplicationDao = new FornecedorApplicationDao();
	        try {
	            return fornecedorApplicationDao.salvar(Fornecedorapplication);
	        } catch (SQLException ex) {
	            Logger.getLogger(FornecedorApplicationFacade.class.getName()).log(Level.SEVERE, null, ex);
	            JOptionPane.showMessageDialog(null, "Erro Salvar Forma de Pagameto " + ex);
	            return null;
	        }
	    }
	    
	    public void excluir(int idFornecedorApplcation) {
	    	fornecedorApplicationDao = new FornecedorApplicationDao();
	        try {
	        	fornecedorApplicationDao.excluir(idFornecedorApplcation);
	        } catch (SQLException ex) {
	            Logger.getLogger(FornecedorApplicationFacade.class.getName()).log(Level.SEVERE, null, ex);
	            JOptionPane.showMessageDialog(null, "Erro Excluir Forma de pagamento "  + ex);
	        }
	    }
	    
	    public List<Fornecedorapplication> listar(String sql) {
	    	fornecedorApplicationDao = new FornecedorApplicationDao();
	        try {
	            return fornecedorApplicationDao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(FornecedorApplicationFacade.class.getName()).log(Level.SEVERE, null, ex);
	            JOptionPane.showMessageDialog(null, "Erro Lsitar Forma de Pagameto " + ex);
	            return null;
	        }
	    }
	    
	    public Fornecedorapplication consultar(String sql) {
	    	fornecedorApplicationDao = new FornecedorApplicationDao();
	        try {
	            return fornecedorApplicationDao.consultar(sql);
	        } catch (SQLException ex) {
	        	 Logger.getLogger(FornecedorApplicationFacade.class.getName()).log(Level.SEVERE, null, ex);
	             return null;
	        }
	    }
}
