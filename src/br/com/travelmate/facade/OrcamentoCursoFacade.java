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

import br.com.travelmate.dao.OrcamentoCursoDao;
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.model.Orcamentocursoformapagamento;
import br.com.travelmate.model.Produtoorcamentocurso;
import br.com.travelmate.model.Produtosorcamento;
/**
 *
 * @author Wolverine
 */
public class OrcamentoCursoFacade {
    
    OrcamentoCursoDao orcamentoCursoDao;
    
    public Orcamentocurso salvar(Orcamentocurso orcamento) {
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.salvar(orcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Orcamentocurso consultar(int idOrcamento) {
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.consultar(idOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Orcamentocurso> listarOrcamento(String sql){
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.listarOrcamento(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Orcamento Curso " + ex);
            return null;
        }
    }
    
    public Produtoorcamentocurso salvar(Produtoorcamentocurso produtoOrcamentoCurso) {
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.salvar(produtoOrcamentoCurso);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Orcamento Curso Produto " + ex);
            return null;
        }
    }
    
    public List<Produtoorcamentocurso> listarProdutoOrcamentoCurso(int idOrcamento){
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.listarProdutoOrcamentoCurso(idOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Listar Produto Orcamento Curso " + ex);
            return null;
        }
    }
    
    public List<Produtoorcamentocurso> listarProdutoOrcamentoCurso(String sql){
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.listarProdutoOrcamentoCurso(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Listar Produto Orcamento Curso " + ex);
            return null;
        }
    }
    
    public Produtosorcamento consultarProdutoOrcamentoCurso(int idProdutoOrcamentoCurso){
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.consultarProdutoOrcamentoCurso(idProdutoOrcamentoCurso);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Produto Orcamento Curso " + ex);
            return null;
        }
    }
    
    public void excluirProdutoOrcamentoCurso(int idProdutoOrcamentoCurso) {
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
        	orcamentoCursoDao.excluirProdutoOrcamentoCurso(idProdutoOrcamentoCurso);
       } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Produto Orcamento Curso " + ex);
        }
    }
    
    
    public Orcamentocurso consultarCliente(int idCliente) {
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.consultarCliente(idCliente);
       } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Produtoorcamentocurso consultarProdutoOrcamentoCuros(int idProdutoOrcamentoCurso) {
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.consultarProdutoOrcamentoCuros(idProdutoOrcamentoCurso);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
             return null;
        }
    }
    
    public void excluirOrcamentoCuros(int idOrcamento) {
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
        	orcamentoCursoDao.excluirOrcamentoCuros(idOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Or�amento " + ex);
        }
    }
    
    public List<Orcamentocurso> listarOrcamentoCurso(){
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.listarOrcamentoCurso();
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    

    public Orcamentocursoformapagamento salvarFormaPagamento(Orcamentocursoformapagamento orcamentocursoformapagamento) {
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.salvarFormaPagamento(orcamentocursoformapagamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Orcamentocursoformapagamento consultarFormaPagamento(int idOrcamento) {
    	orcamentoCursoDao = new OrcamentoCursoDao();
        try {
            return orcamentoCursoDao.consultarFormaPagamento(idOrcamento);
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
