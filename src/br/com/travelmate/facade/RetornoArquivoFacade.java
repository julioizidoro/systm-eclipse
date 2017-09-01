package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.RetornoArquivoDao;
import br.com.travelmate.model.Retornoarquivo;


public class RetornoArquivoFacade {

	RetornoArquivoDao retornoArquivoDao;
	
	public Retornoarquivo salvar(Retornoarquivo retornoarquivo) {
		retornoArquivoDao = new RetornoArquivoDao();
        try {
            return retornoArquivoDao.salvar(retornoarquivo);
        } catch (SQLException ex) {
            Logger.getLogger(RetornoArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Retornoarquivo> listar(String sql){
    	retornoArquivoDao = new RetornoArquivoDao();
        try {
            return retornoArquivoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RetornoArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idretornoarquivo) {
    	retornoArquivoDao = new RetornoArquivoDao();
        try {
        	retornoArquivoDao.excluir(idretornoarquivo);
        } catch (SQLException ex) {
            Logger.getLogger(RetornoArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
}
