/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.VoluntariadoProjetoAcomodacaoDao; 
import br.com.travelmate.model.Voluntariadoprojetoacomodacao;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Wolverine
 */
public class VoluntariadoProjetoAcomodacaoFacade {
    
    private VoluntariadoProjetoAcomodacaoDao voluntariadoProjetoAcomodacaoDao;
    
    public Voluntariadoprojetoacomodacao salvar(Voluntariadoprojetoacomodacao voluntariadoprojetoacomodacao){
    	voluntariadoProjetoAcomodacaoDao = new VoluntariadoProjetoAcomodacaoDao();
        try {
			return voluntariadoProjetoAcomodacaoDao.salvar(voluntariadoprojetoacomodacao);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
        return null;
    }
    
    public Voluntariadoprojetoacomodacao consultar(String sql){
    	voluntariadoProjetoAcomodacaoDao = new VoluntariadoProjetoAcomodacaoDao();
        try {
			return voluntariadoProjetoAcomodacaoDao.consultar(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int id){
    	voluntariadoProjetoAcomodacaoDao = new VoluntariadoProjetoAcomodacaoDao();
    	try {
			voluntariadoProjetoAcomodacaoDao.excluir(id);
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
    }
    
    public List<Voluntariadoprojetoacomodacao> listar(String sql){
    	voluntariadoProjetoAcomodacaoDao = new VoluntariadoProjetoAcomodacaoDao();
        try {
			return voluntariadoProjetoAcomodacaoDao.listar(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
		}   
        return null;
    }
}
