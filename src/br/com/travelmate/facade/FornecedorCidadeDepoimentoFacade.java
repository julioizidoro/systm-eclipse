package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.FornecedorCidadeDepoimentoDao;
import br.com.travelmate.model.Fornecedorcidadedepoimento; 

public class FornecedorCidadeDepoimentoFacade {

	FornecedorCidadeDepoimentoDao fornecedorCidadeDepoimentoDao;
	
	public List<Fornecedorcidadedepoimento> lista(String sql) {
		fornecedorCidadeDepoimentoDao = new FornecedorCidadeDepoimentoDao();
        try {
            return fornecedorCidadeDepoimentoDao.lista(sql);
                    } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeDepoimentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Fornecedorcidadedepoimento consultar(String sql) {
    	fornecedorCidadeDepoimentoDao = new FornecedorCidadeDepoimentoDao();
        try { 
            return fornecedorCidadeDepoimentoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeDepoimentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedorcidadedepoimento salvar(Fornecedorcidadedepoimento fornecedorcidadedepoimento) {
    	fornecedorCidadeDepoimentoDao = new FornecedorCidadeDepoimentoDao();
        try {
            return fornecedorCidadeDepoimentoDao.salvar(fornecedorcidadedepoimento);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeDepoimentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idDepoimento) {
    	fornecedorCidadeDepoimentoDao = new FornecedorCidadeDepoimentoDao();
        try {
        	fornecedorCidadeDepoimentoDao.excluir(idDepoimento);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeDepoimentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir depoimento " + ex);
        }
    }
     
}
