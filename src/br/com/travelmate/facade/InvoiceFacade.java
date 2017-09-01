/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.facade;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import br.com.travelmate.dao.InvoiceDao;
import br.com.travelmate.model.Invoice;

/**
 *
 * @author Wolverine
 */
public class InvoiceFacade {
    
    InvoiceDao invoicesDao;
    
    public Invoice salvar(Invoice invoice) {
    	invoicesDao = new InvoiceDao();
        try {
            return invoicesDao.salvar(invoice);
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Invoice " +  ex);
            return null;
        }
    }
    
    
    public Invoice consultarVenda(int idVenda, int idProduto, int idControle) {
    	invoicesDao = new InvoiceDao();
        try {
            return invoicesDao.consultarVenda(idVenda, idProduto, idControle);
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Invoices " +  ex);
            return null;
        }
    }
    
    public Invoice consultarVenda(int idVenda) {
    	invoicesDao = new InvoiceDao();
        try {
            return invoicesDao.consultarVenda(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Invoices " +  ex);
            return null;
        }
    }
    
    public Invoice consultar(int idInvoice)  {
    	invoicesDao = new InvoiceDao();
        try {
            return invoicesDao.consultar(idInvoice);
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Invoice " +  ex);
            return null;
        }
    }
    
    public List<Invoice> listar(String sql)  {
    	invoicesDao = new InvoiceDao();
        try {
            return invoicesDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Invoice " +  ex);
            return null;
        }
    }
    
    public ResultSet ExportarExcel(String nomeRelatorio, String local, String porta, String senha, String banco, String usuario, String caminhoSalvarExcel, int idUnidade)  {
    	invoicesDao = new InvoiceDao();
        try {
            return invoicesDao.ExportarExcel(nomeRelatorio, local, porta, senha, banco, usuario, caminhoSalvarExcel, idUnidade);
        } catch (IOException ex) {
            Logger.getLogger(InvoiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Exportar Invoices");
            return null;
        }
    }
    
}
