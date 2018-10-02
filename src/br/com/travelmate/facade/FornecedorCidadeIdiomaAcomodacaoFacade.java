package br.com.travelmate.facade;
 
import br.com.travelmate.dao.FornecedorCidadeIdiomaAcomodacaoDao; 
import br.com.travelmate.model.Fornecedorcidadeidiomaacomodacao; 

import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Kamila
 */
public class FornecedorCidadeIdiomaAcomodacaoFacade {
    
    private FornecedorCidadeIdiomaAcomodacaoDao fornecedorCidadeIdiomaAcomodacaoDao;
    
    public Fornecedorcidadeidiomaacomodacao salvar(Fornecedorcidadeidiomaacomodacao fornecedorcidadeidiomaacomodacao){
    	fornecedorCidadeIdiomaAcomodacaoDao = new FornecedorCidadeIdiomaAcomodacaoDao();
        try {
			return fornecedorCidadeIdiomaAcomodacaoDao.salvar(fornecedorcidadeidiomaacomodacao);
		} catch (SQLException e) { 
			  
		}
        return null;
    }
    
    public Fornecedorcidadeidiomaacomodacao consultar(String sql){
    	fornecedorCidadeIdiomaAcomodacaoDao = new FornecedorCidadeIdiomaAcomodacaoDao();
        try {
			return fornecedorCidadeIdiomaAcomodacaoDao.consultar(sql);
		} catch (SQLException e) { 
			  
		}
        return null;
    }
    
    public void excluir(int id){
    	fornecedorCidadeIdiomaAcomodacaoDao = new FornecedorCidadeIdiomaAcomodacaoDao();
    	try {
    		fornecedorCidadeIdiomaAcomodacaoDao.excluir(id);
		} catch (SQLException e) { 
			  
		} 
    }
    
    public List<Fornecedorcidadeidiomaacomodacao> listar(String sql){
    	fornecedorCidadeIdiomaAcomodacaoDao = new FornecedorCidadeIdiomaAcomodacaoDao();
        try {
			return fornecedorCidadeIdiomaAcomodacaoDao.listar(sql);
		} catch (SQLException e) { 
			  
		}   
        return null;
    }
}
