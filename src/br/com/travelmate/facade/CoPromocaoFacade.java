package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.CoPromocaoDao;
import br.com.travelmate.model.Copromocao;

public class CoPromocaoFacade {
	
	private CoPromocaoDao coPromocaoDao;
	
	public Copromocao salvar(Copromocao copromocao){
		coPromocaoDao = new CoPromocaoDao();
        try {
            return coPromocaoDao.salvar(copromocao);
        } catch (SQLException ex) {
            Logger.getLogger(CoPromocaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
	
	public List<Copromocao> listar(String sql){
		coPromocaoDao = new CoPromocaoDao();
        try {
            return coPromocaoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CoPromocaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
	
	
	public Copromocao consultar(String sql)  {
		coPromocaoDao = new CoPromocaoDao();
        try {
            return coPromocaoDao.consultar(sql);
        }  catch (SQLException ex) {
            Logger.getLogger(CoPromocaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
	
	public void excluir(Copromocao coPromocao) {
		coPromocaoDao = new CoPromocaoDao();
        try {
            coPromocaoDao.excluir(coPromocao);
        }  catch (SQLException ex) {
            Logger.getLogger(CoPromocaoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	

}
