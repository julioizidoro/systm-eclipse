package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.FornecedorArquivoDao;
import br.com.travelmate.model.Fornecedorarquivo;

public class FornecedorArquivoFacade {
	
	 	private FornecedorArquivoDao fornecedorArquivoDao;
	    
	    public Fornecedorarquivo salvar(Fornecedorarquivo arquivos) {
	    	fornecedorArquivoDao = new FornecedorArquivoDao();
	        try {
	            return fornecedorArquivoDao.salvar(arquivos);
	        } catch (SQLException ex) {
	            Logger.getLogger(FornecedorArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Fornecedorarquivo> listar(String sql) {
	    	fornecedorArquivoDao = new FornecedorArquivoDao();
	        try {
				return fornecedorArquivoDao.listar(sql);
			} catch (SQLException e) {
				  
				return null;
			}
	    }
	   
	    public Fornecedorarquivo consultar(int idArquivos) {
	    	fornecedorArquivoDao = new FornecedorArquivoDao();
	        try {
				return fornecedorArquivoDao.consultar(idArquivos);
			} catch (SQLException e) {
				  
				return null;
			}
	    }
	    
	    public void excluir(int idArquivos) {
	    	fornecedorArquivoDao = new FornecedorArquivoDao();
	        try {
	        	fornecedorArquivoDao.excluir(idArquivos);
	        } catch (SQLException ex) {
	            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
