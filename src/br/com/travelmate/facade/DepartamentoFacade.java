package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.DepartamentoDao;
import br.com.travelmate.model.Departamento;


public class DepartamentoFacade {


	DepartamentoDao departamentoDao;
    
    public Departamento salvar(Departamento departamento) {
    	departamentoDao = new DepartamentoDao();
        try {
            return departamentoDao.salvar(departamento);
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Departamento consultar(int idDepartamento) {
    	departamentoDao = new DepartamentoDao();
    	try {
			return departamentoDao.consultar(idDepartamento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public List<Departamento> listar(String sql){
    	departamentoDao = new DepartamentoDao();
        try {
            return departamentoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int iddepartamento) {
    	departamentoDao = new DepartamentoDao();
        try {
        	departamentoDao.excluir(iddepartamento);
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
