package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.RemessaArquivoDao;
import br.com.travelmate.model.Remessaarquivo;

public class RemessaArquivoFacade {

	RemessaArquivoDao remessaArquivoDao;
	
	public Remessaarquivo salvar(Remessaarquivo remessaarquivo) {
		remessaArquivoDao = new RemessaArquivoDao();
        try {
            return remessaArquivoDao.salvar(remessaarquivo);
        } catch (SQLException ex) {
            Logger.getLogger(RemessaArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Remessaarquivo> listar(String sql){
    	remessaArquivoDao = new RemessaArquivoDao();
        try {
            return remessaArquivoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RemessaArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idremessaarquivo) {
    	remessaArquivoDao = new RemessaArquivoDao();
        try {
        	remessaArquivoDao.excluir(idremessaarquivo);
        } catch (SQLException ex) {
            Logger.getLogger(RemessaArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
}
