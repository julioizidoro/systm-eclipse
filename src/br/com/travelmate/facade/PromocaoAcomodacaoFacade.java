package br.com.travelmate.facade;

 
import br.com.travelmate.dao.PromocaoAcomodacaoDao;
import br.com.travelmate.model.Promocaoacomodacao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class PromocaoAcomodacaoFacade {
    
    private PromocaoAcomodacaoDao promocaoAcomodacaoDao;
    
    public Promocaoacomodacao salvar(Promocaoacomodacao coobrigatorio){
        promocaoAcomodacaoDao = new PromocaoAcomodacaoDao();
        try {
            return promocaoAcomodacaoDao.salvar(coobrigatorio);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Promocaoacomodacao> listar(String sql){
        promocaoAcomodacaoDao = new PromocaoAcomodacaoDao();
        try {
            return promocaoAcomodacaoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
   public Promocaoacomodacao consultar(String sql)  {
	   promocaoAcomodacaoDao = new PromocaoAcomodacaoDao();
       try {
           return promocaoAcomodacaoDao.consultar(sql);
       }  catch (SQLException ex) {
           Logger.getLogger(PromocaoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   
   public void excluir(Promocaoacomodacao promocao) {
	   promocaoAcomodacaoDao = new PromocaoAcomodacaoDao();
       try {
    	   promocaoAcomodacaoDao.excluir(promocao);
       } catch (SQLException ex) {
           Logger.getLogger(PromocaoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Excluir promocao " + ex);
       }
   }
}
