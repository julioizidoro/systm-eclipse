/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.VoluntariadoProjetoDao; 
import br.com.travelmate.model.Voluntariadoprojeto;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Wolverine
 */
public class VoluntariadoProjetoFacade {
    
    private VoluntariadoProjetoDao voluntariadoProjetoDao;
    
    public Voluntariadoprojeto salvar(Voluntariadoprojeto voluntariadoprojeto){
    	voluntariadoProjetoDao = new VoluntariadoProjetoDao();
        try {
			return voluntariadoProjetoDao.salvar(voluntariadoprojeto);
		} catch (SQLException e) { 
			  
		}
        return null;
    }
    
    public Voluntariadoprojeto consultar(String sql){
    	voluntariadoProjetoDao = new VoluntariadoProjetoDao();
        try {
			return voluntariadoProjetoDao.consultar(sql);
		} catch (SQLException e) {
			  
		}
        return null;
    }
    
    public void excluir(int id){
    	voluntariadoProjetoDao = new VoluntariadoProjetoDao();
    	try {
    		voluntariadoProjetoDao.excluir(id);
		} catch (SQLException e) { 
			  
		} 
    }
    
    public List<Voluntariadoprojeto> listar(String sql){
    	voluntariadoProjetoDao = new VoluntariadoProjetoDao();
        try {
			return voluntariadoProjetoDao.listar(sql);
		} catch (SQLException e) { 
			  
		}   
        return null;
    }
}
