/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.FornecedorDocsDao; 
import br.com.travelmate.model.Fornecedordocs;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class FornecedorDocsFacade {
    
    private FornecedorDocsDao fornecedorDocsDao;
    
    public Fornecedordocs salvar(Fornecedordocs fornecedordocs){
    	fornecedorDocsDao = new FornecedorDocsDao();
        try {
			return fornecedorDocsDao.salvar(fornecedordocs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Fornecedordocs consultar(String sql){
    	fornecedorDocsDao = new FornecedorDocsDao();
        try {
			return fornecedorDocsDao.consultar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idFornecedordocs){
    	fornecedorDocsDao = new FornecedorDocsDao();
    	try {
    		fornecedorDocsDao.excluir(idFornecedordocs);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public List<Fornecedordocs> listar(String sql){
    	fornecedorDocsDao = new FornecedorDocsDao();
        try {
			return fornecedorDocsDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
        return null;
    }
}
