/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import br.com.travelmate.dao.OrcamentoDao;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Produtosorcamento;

/**
 *
 * @author Wolverine
 */
public class OrcamentoFacade {
    
    OrcamentoDao orcamentoDao;
    
    public Orcamento salvar(Orcamento orcamento){
    	orcamentoDao = new OrcamentoDao();
        try {
            return orcamentoDao.salvar(orcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar orcamento " + ex);
            return null;
        }
    }
    
    public Orcamento consultar(int idVenda){
    	orcamentoDao = new OrcamentoDao();
        try {
            return orcamentoDao.consultar(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar orcamento " + ex);
            return null;
        }
    }
        
      public Orcamentoprodutosorcamento salvar(Orcamentoprodutosorcamento orcamentoprodutosorcamento) {
    	  orcamentoDao = new OrcamentoDao();
        try {
            return orcamentoDao.salvar(orcamentoprodutosorcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Orcamento Produto  " + ex);
            return null;
        }
    }
      
      public List<Orcamentoprodutosorcamento> listarOrcamentoProdutoOrcamento(int idOrcamento) {
    	  orcamentoDao = new OrcamentoDao();
        try {
            return orcamentoDao.listarOrcamentoProdutoOrcamento(idOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Listar Orcamento Produto  " + ex);
            return null;
        }
      }
      
      public Produtosorcamento consultarProdutoOrcamento(int idProdutoOrcamento) {
    	  orcamentoDao = new OrcamentoDao();
        try {
            return orcamentoDao.consultarProdutoOrcamento(idProdutoOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Orcamento Produto  " + ex);
            return null;
        }
      }
      
      public void excluirOrcamentoProdutoOrcamento(int idOrcamentoprodutosorcamento) {
    	  orcamentoDao = new OrcamentoDao();
        try {
        	orcamentoDao.excluirOrcamentoProdutoOrcamento(idOrcamentoprodutosorcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Orcamento Produto  " + ex);
        }
      }
      
      public void excluirOrcamento(int idOrcamento) {
    	  orcamentoDao = new OrcamentoDao();
        try {
        	orcamentoDao.excluirOrcamento(idOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Orcamento " + ex);
        }
      }
      
      public Orcamentoprodutosorcamento consultarOrcamentoProdutoOrcamento(int idIrcamentoProdutosOrcamento) {
    	  orcamentoDao = new OrcamentoDao();
        try {
            return orcamentoDao.consultarOrcamentoProdutoOrcamento(idIrcamentoProdutosOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar  Orcamento Produto Orcamento " + ex);
            return null;
        } 
      }
      
      public List<Orcamentoprodutosorcamento> listarOrcamentoProdutoOrcamento() {
    	  orcamentoDao = new OrcamentoDao();
    	  try {
			return orcamentoDao.listarOrcamentoProdutoOrcamento();
		} catch (SQLException e) {
			  
			return null;
		}
      }
}
