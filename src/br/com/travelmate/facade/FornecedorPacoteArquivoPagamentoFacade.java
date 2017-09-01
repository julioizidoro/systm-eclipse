/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
  
import br.com.travelmate.dao.FornecedorPacoteArquivoPagamentoDao; 
import br.com.travelmate.model.Fornecedorpacotearquivopagamento; 

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class FornecedorPacoteArquivoPagamentoFacade {
    
    FornecedorPacoteArquivoPagamentoDao fornecedorPacoteArquivoPagamentoDao;
    
    public Fornecedorpacotearquivopagamento salvar(Fornecedorpacotearquivopagamento pacotesfornecedor) {
    	fornecedorPacoteArquivoPagamentoDao = new FornecedorPacoteArquivoPagamentoDao();
        try {
            return fornecedorPacoteArquivoPagamentoDao.salvar(pacotesfornecedor);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorPacoteArquivoPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedorpacotearquivopagamento> listar(String sql) {
    	fornecedorPacoteArquivoPagamentoDao = new FornecedorPacoteArquivoPagamentoDao();
        try {
            return fornecedorPacoteArquivoPagamentoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorPacoteArquivoPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedorpacotearquivopagamento consultar(String sql){
    	fornecedorPacoteArquivoPagamentoDao = new FornecedorPacoteArquivoPagamentoDao();
        try {
            return fornecedorPacoteArquivoPagamentoDao.consulta(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorPacoteArquivoPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
}
