package br.com.travelmate.facade;
 
import br.com.travelmate.dao.PromocaoBrindeCursoDao; 
import br.com.travelmate.model.Promocaobrindecurso; 

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class PromocaoBrindeCursoFacade {
    
    private PromocaoBrindeCursoDao promocaoBrindeCursoDao;
    
    public Promocaobrindecurso salvar(Promocaobrindecurso promocao){
    	promocaoBrindeCursoDao = new PromocaoBrindeCursoDao();
        try {
            return promocaoBrindeCursoDao.salvar(promocao);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoBrindeCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Promocaobrindecurso> listar(String sql){
       promocaoBrindeCursoDao = new PromocaoBrindeCursoDao();
        try {
            return promocaoBrindeCursoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoBrindeCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
   public Promocaobrindecurso consultar(String sql)  {
	  promocaoBrindeCursoDao = new PromocaoBrindeCursoDao();
       try {
           return promocaoBrindeCursoDao.consultar(sql);
       }  catch (SQLException ex) {
           Logger.getLogger(PromocaoBrindeCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   
   public void excluir(Promocaobrindecurso promocao) {
	  promocaoBrindeCursoDao = new PromocaoBrindeCursoDao();
       try {
    	   promocaoBrindeCursoDao.excluir(promocao);
       } catch (SQLException ex) {
           Logger.getLogger(PromocaoBrindeCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Excluir promocao " + ex);
       }
   }
}
