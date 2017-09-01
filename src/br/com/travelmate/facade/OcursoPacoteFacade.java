/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.OcursoPacoteDao;
import br.com.travelmate.model.Ocursopacote;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class OcursoPacoteFacade {
    
    private OcursoPacoteDao ocursoPacoteDao;
    
    public Ocursopacote salvar(Ocursopacote ocursopacote){
    	ocursoPacoteDao = new OcursoPacoteDao();
        try {
			return ocursoPacoteDao.salvar(ocursopacote);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Ocursopacote consultar(String sql){
    	ocursoPacoteDao = new OcursoPacoteDao();
        try {
			return ocursoPacoteDao.consultar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idOcursopacote){
    	ocursoPacoteDao = new OcursoPacoteDao();
    	try {
    		ocursoPacoteDao.excluir(idOcursopacote);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public List<Ocursopacote> listar(String sql){
    	ocursoPacoteDao = new OcursoPacoteDao();
        try {
			return ocursoPacoteDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
        return null;
    }
}
