package br.com.travelmate.facade;
  
import br.com.travelmate.dao.PromocaoBrindeCursoCidadeDao; 
import br.com.travelmate.model.Promocaobrindecursocidade; 
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class PromocaoBrindeCursoCidadeFacade {
    
    private PromocaoBrindeCursoCidadeDao promocaoBrindeCursoCidadeDao;
    
    public Promocaobrindecursocidade salvar(Promocaobrindecursocidade promocaobrindecursocidade){
    	promocaoBrindeCursoCidadeDao = new PromocaoBrindeCursoCidadeDao();
        try {
            return promocaoBrindeCursoCidadeDao.salvar(promocaobrindecursocidade);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoBrindeCursoCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Promocaobrindecursocidade> listar(String sql){
       promocaoBrindeCursoCidadeDao = new PromocaoBrindeCursoCidadeDao();
        try {
            return promocaoBrindeCursoCidadeDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoBrindeCursoCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
   public Promocaobrindecursocidade consultar(String sql)  {
	  promocaoBrindeCursoCidadeDao = new PromocaoBrindeCursoCidadeDao();
       try {
           return promocaoBrindeCursoCidadeDao.consultar(sql);
       }  catch (SQLException ex) {
           Logger.getLogger(PromocaoBrindeCursoCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   
   public void excluir(Promocaobrindecursocidade promocao) {
	  promocaoBrindeCursoCidadeDao = new PromocaoBrindeCursoCidadeDao();
       try {
    	   promocaoBrindeCursoCidadeDao.excluir(promocao);
       } catch (SQLException ex) {
           Logger.getLogger(PromocaoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Excluir promocao " + ex);
       }
   }
}
