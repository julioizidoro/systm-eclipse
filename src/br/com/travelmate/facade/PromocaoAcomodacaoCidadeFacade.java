package br.com.travelmate.facade;
 
 
import br.com.travelmate.dao.PromocaoAcomodacaoCidadeDao;
import br.com.travelmate.model.Promocaoacomodacaocidade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class PromocaoAcomodacaoCidadeFacade {
    
    private PromocaoAcomodacaoCidadeDao promocaoAcomodacaoDao;
    
    public Promocaoacomodacaocidade salvar(Promocaoacomodacaocidade promocaoacamodacaocidade){
        promocaoAcomodacaoDao = new PromocaoAcomodacaoCidadeDao();
        try {
            return promocaoAcomodacaoDao.salvar(promocaoacamodacaocidade);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoAcomodacaoCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Promocaoacomodacaocidade> listar(String sql){
        promocaoAcomodacaoDao = new PromocaoAcomodacaoCidadeDao();
        try {
            return promocaoAcomodacaoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoAcomodacaoCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
   public Promocaoacomodacaocidade consultar(String sql)  {
	   promocaoAcomodacaoDao = new PromocaoAcomodacaoCidadeDao();
       try {
           return promocaoAcomodacaoDao.consultar(sql);
       }  catch (SQLException ex) {
           Logger.getLogger(PromocaoAcomodacaoCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   
   public void excluir(Promocaoacomodacaocidade promocao) {
	   promocaoAcomodacaoDao = new PromocaoAcomodacaoCidadeDao();
       try {
    	   promocaoAcomodacaoDao.excluir(promocao);
       } catch (SQLException ex) {
           Logger.getLogger(PromocaoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Excluir promocao " + ex);
       }
   }
}
