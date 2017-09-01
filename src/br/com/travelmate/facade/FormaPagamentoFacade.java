/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.facade;

import br.com.travelmate.dao.FormaPagamentoDao;
import br.com.travelmate.model.Formapagamento;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author Wolverine
 */
public class FormaPagamentoFacade {
    
    FormaPagamentoDao formaPagamentoDao;
    
    public Formapagamento salvar(Formapagamento formaPagamento){
    	formaPagamentoDao = new FormaPagamentoDao();
        try {
            return formaPagamentoDao.salvar(formaPagamento);
        } catch (SQLException ex) {
            Logger.getLogger(FormaPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Forma de Pagameto " + ex);
            return null;
        }
    }
    
    public void excluir(int idFormaPagamento) {
    	formaPagamentoDao = new FormaPagamentoDao();
        try {
        	formaPagamentoDao.excluir(idFormaPagamento);
        } catch (SQLException ex) {
            Logger.getLogger(FormaPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Forma de pagamento "  + ex);
        }
    }
    
    public List<Formapagamento> listar(int idVenda) {
    	formaPagamentoDao = new FormaPagamentoDao();
        try {
            return formaPagamentoDao.listar(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(FormaPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Lsitar Forma de Pagameto " + ex);
            return null;
        }
    }
    
    public Formapagamento consultar(int idVenda) {
    	formaPagamentoDao = new FormaPagamentoDao();
        try {
            return formaPagamentoDao.consultar(idVenda);
        } catch (SQLException ex) {
        	 Logger.getLogger(FiltroOrcamentoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
             return null;
        }
    }
    
}
