package br.com.travelmate.facade;


import br.com.travelmate.dao.FornecedorCidadeIdiomaDao;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FornecedorCidadeIdiomaFacade {
    
    private FornecedorCidadeIdiomaDao fornecedorCidadeIdiomaDao;
    
    public Fornecedorcidadeidioma salvar(Fornecedorcidadeidioma fornecedorcidadeidioma){
        fornecedorCidadeIdiomaDao = new FornecedorCidadeIdiomaDao();
        try {
            return fornecedorCidadeIdiomaDao.salvar(fornecedorcidadeidioma);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeIdiomaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Fornecedorcidadeidioma> listar(String sql){
        fornecedorCidadeIdiomaDao = new FornecedorCidadeIdiomaDao();
        try {
            return fornecedorCidadeIdiomaDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeIdiomaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public void excluir(int idFornecedorcidadeidioma) {
	   fornecedorCidadeIdiomaDao = new FornecedorCidadeIdiomaDao();
       try {
    	   fornecedorCidadeIdiomaDao.excluir(idFornecedorcidadeidioma);
       } catch (SQLException ex) {
           Logger.getLogger(FornecedorCidadeIdiomaFacade.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public Fornecedorcidadeidioma consultar(String sql) {
	   fornecedorCidadeIdiomaDao = new FornecedorCidadeIdiomaDao();
        try {
            return fornecedorCidadeIdiomaDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeIdiomaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
