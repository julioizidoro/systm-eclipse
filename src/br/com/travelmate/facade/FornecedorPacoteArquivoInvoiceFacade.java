/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
  
import br.com.travelmate.dao.FornecedorPacoteArquivoInvoiceDao; 
import br.com.travelmate.model.Fornecedorpacotearquivoinvoice; 

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class FornecedorPacoteArquivoInvoiceFacade {
    
    FornecedorPacoteArquivoInvoiceDao fornecedorPacoteArquivoInvoiceDao;
    
    public Fornecedorpacotearquivoinvoice salvar(Fornecedorpacotearquivoinvoice pacotesfornecedor) {
    	fornecedorPacoteArquivoInvoiceDao = new FornecedorPacoteArquivoInvoiceDao();
        try {
            return fornecedorPacoteArquivoInvoiceDao.salvar(pacotesfornecedor);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorPacoteArquivoInvoiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedorpacotearquivoinvoice> listar(String sql) {
    	fornecedorPacoteArquivoInvoiceDao = new FornecedorPacoteArquivoInvoiceDao();
        try {
            return fornecedorPacoteArquivoInvoiceDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorPacoteArquivoInvoiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedorpacotearquivoinvoice consultar(String sql){
    	fornecedorPacoteArquivoInvoiceDao = new FornecedorPacoteArquivoInvoiceDao();
        try {
            return fornecedorPacoteArquivoInvoiceDao.consulta(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorPacoteArquivoInvoiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
}
