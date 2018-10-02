package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.FuncaoDao;
import br.com.travelmate.model.Funcao;

public class FuncaoFacade {

	FuncaoDao funcaoDao;
	
	public Funcao salvar(Funcao funcao) {
		funcaoDao = new FuncaoDao();
        try {
            return funcaoDao.salvar(funcao);
        } catch (SQLException ex) {
            Logger.getLogger(FuncaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Funcao consultar(int idfuncao) {
    	funcaoDao = new FuncaoDao();
    	try {
			return funcaoDao.consultar(idfuncao);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    
    public Funcao consultar(String sql) {
    	funcaoDao = new FuncaoDao();
    	try {
			return funcaoDao.consultar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public List<Funcao> listar(String sql){
    	funcaoDao = new FuncaoDao();
        try {
            return funcaoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FuncaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idfuncao) {
    	funcaoDao = new FuncaoDao();
        try {
        	funcaoDao.excluir(idfuncao);
        } catch (SQLException ex) {
            Logger.getLogger(FuncaoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
