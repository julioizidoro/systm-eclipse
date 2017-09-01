package br.com.travelmate.facade;

 
import br.com.travelmate.dao.PromocaoCursoDao; 
import br.com.travelmate.model.Promocaocurso; 

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class PromocaoCursoFacade {
    
    private PromocaoCursoDao promocaoCursoDao;
    
    public Promocaocurso salvar(Promocaocurso coobrigatorio){
        promocaoCursoDao = new PromocaoCursoDao();
        try {
            return promocaoCursoDao.salvar(coobrigatorio);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Promocaocurso> listar(String sql){
        promocaoCursoDao = new PromocaoCursoDao();
        try {
            return promocaoCursoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
   public Promocaocurso consultar(String sql)  {
	   promocaoCursoDao = new PromocaoCursoDao();
       try {
           return promocaoCursoDao.consultar(sql);
       }  catch (SQLException ex) {
           Logger.getLogger(PromocaoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   
   public void excluir(Promocaocurso promocao) {
	   promocaoCursoDao = new PromocaoCursoDao();
       try {
    	   promocaoCursoDao.excluir(promocao);
       } catch (SQLException ex) {
           Logger.getLogger(PromocaoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Excluir promocao " + ex);
       }
   }
}
