/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.FornecedorCidadeDocsDao;
import br.com.travelmate.model.Fornecedorcidadedocs;

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class FornecedorCidadeDocsFacade {
    
    private FornecedorCidadeDocsDao fornecedorCidadeDocsDao;
    
    public Fornecedorcidadedocs salvar(Fornecedorcidadedocs fornecedorcidadedocs){
    	fornecedorCidadeDocsDao = new FornecedorCidadeDocsDao();
        try {
			return fornecedorCidadeDocsDao.salvar(fornecedorcidadedocs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public Fornecedorcidadedocs consultar(String sql){
    	fornecedorCidadeDocsDao = new FornecedorCidadeDocsDao();
        try {
			return fornecedorCidadeDocsDao.consultar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void excluir(int idFornecedordocs){
    	fornecedorCidadeDocsDao = new FornecedorCidadeDocsDao();
    	try {
    		fornecedorCidadeDocsDao.excluir(idFornecedordocs);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public List<Fornecedorcidadedocs> listar(String sql){
    	fornecedorCidadeDocsDao = new FornecedorCidadeDocsDao();
        try {
			return fornecedorCidadeDocsDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
        return null;
    }
}
