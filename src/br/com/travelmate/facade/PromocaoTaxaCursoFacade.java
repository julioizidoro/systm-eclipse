package br.com.travelmate.facade;
 
import br.com.travelmate.dao.PromocaoTaxaCursoDao; 
import br.com.travelmate.model.Promocaotaxacurso;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class PromocaoTaxaCursoFacade {
    
    private PromocaoTaxaCursoDao promocaoTaxaCursoDao;
    
    public Promocaotaxacurso salvar(Promocaotaxacurso promocao){
       promocaoTaxaCursoDao = new PromocaoTaxaCursoDao();
        try {
            return promocaoTaxaCursoDao.salvar(promocao);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoTaxaCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Promocaotaxacurso> listar(String sql){
       promocaoTaxaCursoDao = new PromocaoTaxaCursoDao();
        try {
            return promocaoTaxaCursoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoTaxaCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
   public Promocaotaxacurso consultar(String sql)  {
	  promocaoTaxaCursoDao = new PromocaoTaxaCursoDao();
       try {
           return promocaoTaxaCursoDao.consultar(sql);
       }  catch (SQLException ex) {
           Logger.getLogger(PromocaoTaxaCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   
   public void excluir(Promocaotaxacurso promocao) {
	  promocaoTaxaCursoDao = new PromocaoTaxaCursoDao();
       try {
    	   promocaoTaxaCursoDao.excluir(promocao);
       } catch (SQLException ex) {
           Logger.getLogger(PromocaoTaxaCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Excluir promocao " + ex);
       }
   }
}
