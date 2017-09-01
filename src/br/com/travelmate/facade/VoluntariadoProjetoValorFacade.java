/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.VoluntariadoProjetoValorDao;
import br.com.travelmate.model.Voluntariadoprojetovalor; 

/**
 *
 * @author Kamila
 */
public class VoluntariadoProjetoValorFacade {
    
    private VoluntariadoProjetoValorDao voluntariadoProjetoValorDao;
    
    public Voluntariadoprojetovalor salvar(Voluntariadoprojetovalor voluntariadoprojetovalor){
    	voluntariadoProjetoValorDao = new VoluntariadoProjetoValorDao();
        try {
			return voluntariadoProjetoValorDao.salvar(voluntariadoprojetovalor);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
        return null;
    }
    
    public Voluntariadoprojetovalor consultar(String sql){
    	voluntariadoProjetoValorDao = new VoluntariadoProjetoValorDao();
        try {
			return voluntariadoProjetoValorDao.consultar(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idFatura){
    	voluntariadoProjetoValorDao = new VoluntariadoProjetoValorDao();
    	try {
			voluntariadoProjetoValorDao.excluir(idFatura);
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
    }
    
    public List<Voluntariadoprojetovalor> listar(String sql){
    	voluntariadoProjetoValorDao = new VoluntariadoProjetoValorDao();
        try {
			return voluntariadoProjetoValorDao.listar(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
		}   
        return null;
    }
}
