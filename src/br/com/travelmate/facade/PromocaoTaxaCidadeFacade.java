package br.com.travelmate.facade;
  
import br.com.travelmate.dao.PromocaoTaxaCidadeDao; 
import br.com.travelmate.model.Promocaotaxacidade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class PromocaoTaxaCidadeFacade {
    
    private PromocaoTaxaCidadeDao promocaoTaxaCidadeDao;
    
    public Promocaotaxacidade salvar(Promocaotaxacidade promocaotaxacidade){
       promocaoTaxaCidadeDao = new PromocaoTaxaCidadeDao();
        try {
            return promocaoTaxaCidadeDao.salvar(promocaotaxacidade);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoTaxaCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Promocaotaxacidade> listar(String sql){
       promocaoTaxaCidadeDao = new PromocaoTaxaCidadeDao();
        try {
            return promocaoTaxaCidadeDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoTaxaCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
   public Promocaotaxacidade consultar(String sql)  {
	  promocaoTaxaCidadeDao = new PromocaoTaxaCidadeDao();
       try {
           return promocaoTaxaCidadeDao.consultar(sql);
       }  catch (SQLException ex) {
           Logger.getLogger(PromocaoTaxaCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   
   public void excluir(Promocaotaxacidade promocao) {
	  promocaoTaxaCidadeDao = new PromocaoTaxaCidadeDao();
       try {
    	   promocaoTaxaCidadeDao.excluir(promocao);
       } catch (SQLException ex) {
           Logger.getLogger(PromocaoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Excluir promocao " + ex);
       }
   }
}
