package br.com.travelmate.facade;
 
import br.com.travelmate.dao.PromocaoCursoFornecedorCidadeDao; 
import br.com.travelmate.model.Promocaocursocidade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class PromocaoCursoFornecedorCidadeFacade {
    
    private PromocaoCursoFornecedorCidadeDao promocaoCursoDao;
    
    public Promocaocursocidade salvar(Promocaocursocidade promocaocursocidade){
        promocaoCursoDao = new PromocaoCursoFornecedorCidadeDao();
        try {
            return promocaoCursoDao.salvar(promocaocursocidade);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoCursoFornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Promocaocursocidade> listar(String sql){
        promocaoCursoDao = new PromocaoCursoFornecedorCidadeDao();
        try {
            return promocaoCursoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoCursoFornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
   public Promocaocursocidade consultar(String sql)  {
	   promocaoCursoDao = new PromocaoCursoFornecedorCidadeDao();
       try {
           return promocaoCursoDao.consultar(sql);
       }  catch (SQLException ex) {
           Logger.getLogger(PromocaoCursoFornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   
   public void excluir(Promocaocursocidade promocao) {
	   promocaoCursoDao = new PromocaoCursoFornecedorCidadeDao();
       try {
    	   promocaoCursoDao.excluir(promocao);
       } catch (SQLException ex) {
           Logger.getLogger(PromocaoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Excluir promocao " + ex);
       }
   }
}
