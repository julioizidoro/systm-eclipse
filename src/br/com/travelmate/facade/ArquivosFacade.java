package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ArquivosDao;
import br.com.travelmate.model.Arquivos;

public class ArquivosFacade {
	
	 	ArquivosDao arquivosDao;
	    
	    public Arquivos salvar(Arquivos arquivos) {
	    	arquivosDao = new ArquivosDao();
	        try {
	            return arquivosDao.salvar(arquivos);
	        } catch (SQLException ex) {
	            Logger.getLogger(ArquivosFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Arquivos> listar(String sql) throws SQLException{
	    	arquivosDao = new ArquivosDao();
	        return arquivosDao.listar(sql);
	    }
	   
	    public Arquivos consultar(int idArquivos) throws SQLException{
	    	arquivosDao = new ArquivosDao();
	        return arquivosDao.consultar(idArquivos);
	    }
	    
	    public void excluir(int idArquivos) {
	    	arquivosDao = new ArquivosDao();
	        try {
	        	arquivosDao.excluir(idArquivos);
	        } catch (SQLException ex) {
	            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
