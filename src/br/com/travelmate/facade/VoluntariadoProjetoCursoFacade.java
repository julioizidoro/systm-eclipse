/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.VoluntariadoProjetoCursoDao;
import br.com.travelmate.model.Voluntariadoprojetocurso;


/**
 *
 * @author Kamila 
 */
public class VoluntariadoProjetoCursoFacade {
    
    private VoluntariadoProjetoCursoDao voluntariadoProjetoCursoDao;
    
    public Voluntariadoprojetocurso salvar(Voluntariadoprojetocurso voluntariadoprojetocurso){
    	voluntariadoProjetoCursoDao = new VoluntariadoProjetoCursoDao();
        try {
			return voluntariadoProjetoCursoDao.salvar(voluntariadoprojetocurso);
		} catch (SQLException e) {
			  
		}
        return null;
    }
    
    public Voluntariadoprojetocurso consultar(String sql){
    	voluntariadoProjetoCursoDao = new VoluntariadoProjetoCursoDao();
        try {
			return voluntariadoProjetoCursoDao.consultar(sql);
		} catch (SQLException e) { 
			  
		}
        return null;
    }
    
    public void excluir(int id){
    	voluntariadoProjetoCursoDao = new VoluntariadoProjetoCursoDao();
    	try {
    		voluntariadoProjetoCursoDao.excluir(id);
		} catch (SQLException e) {
			  
		} 
    }
    
    public List<Voluntariadoprojetocurso> listar(String sql){
    	voluntariadoProjetoCursoDao = new VoluntariadoProjetoCursoDao();
        try {
			return voluntariadoProjetoCursoDao.listar(sql);
		} catch (SQLException e) { 
			  
		}   
        return null;
    }
}
