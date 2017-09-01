package br.com.travelmate.facade;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.FaturaComprovanteDao;
import br.com.travelmate.model.Faturacomprovante;


public class FaturaComprovanteFacade{
	
	FaturaComprovanteDao faturaComprovanteDao;
	
	public Faturacomprovante salvar(Faturacomprovante faturacomprovante) {
		faturaComprovanteDao = new FaturaComprovanteDao();
        try {
            return faturaComprovanteDao.salvar(faturacomprovante);
        } catch (SQLException ex) {
            Logger.getLogger(FaturaComprovanteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Faturacomprovante> listar(String sql){
    	faturaComprovanteDao = new FaturaComprovanteDao();
        try {
            return faturaComprovanteDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FaturaComprovanteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idfatura) {
    	faturaComprovanteDao = new FaturaComprovanteDao();
        try {
        	faturaComprovanteDao.excluir(idfatura);
        } catch (SQLException ex) {
            Logger.getLogger(FaturaComprovanteFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
