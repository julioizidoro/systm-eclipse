/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Invoice;

/**
 *
 * @author Wolverine
 */
public class InvoiceDao {
    
    public Invoice salvar(Invoice invoice) throws SQLException{
    	EntityManager manager;
        manager =  ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        invoice = manager.merge(invoice);
        tx.commit();
        manager.close();
        return invoice;
    }
    
   
    public Invoice consultarVenda(int idVenda, int idProduto, int idControle) throws SQLException{
    	EntityManager manager;
    	manager =  ConectionFactory.getConnection();
        Query q = manager.createQuery("Select i from Invoice i where i.vendas.idvendas=" + idVenda + " and i.produtos.idprodutos=" + idProduto + " and i.controle=" + idControle);
        Invoice invoice = null;
        if (q.getResultList().size()>0){
            invoice = (Invoice) q.getResultList().get(0);
        }
        manager.close();
        return invoice;
    }
    
    public Invoice consultarVenda(int idVenda) throws SQLException{
    	EntityManager manager;
    	manager =  ConectionFactory.getConnection();
        Query q = manager.createQuery("Select i from Invoice i where i.vendas=" + idVenda);
        Invoice invoice = null;
        if (q.getResultList().size()>0){
            invoice = (Invoice) q.getResultList().get(0);
        }
        manager.close();
        return invoice;
    }
    
    public Invoice consultar(int idInvoice) throws SQLException{
    	EntityManager manager;
    	manager =  ConectionFactory.getConnection();
        Invoice invoice = manager.find(Invoice.class, idInvoice);
        manager.close();
        return invoice;
    }
    
    public List<Invoice> listar(String sql) throws SQLException {
    	EntityManager manager;
    	manager =  ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Invoice> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public ResultSet ExportarExcel(String nomeRelatorio, String local, String porta, String senha, String banco, String usuario, String caminhoSalvarExcel, int idUnidade) throws IOException {
        try {
            ResultSet rs;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try {String conexao = "jdbc:mysql://" + local + ":" + porta + "/" + banco;
                com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(conexao, usuario, senha);
                com.mysql.jdbc.Statement stm = (com.mysql.jdbc.Statement) conn.createStatement();

                stm.execute("USE systm"); //Nome do DATABASE A SER ACESSADO  
                String sql = "Select * from Viewconsultainvoice order by dataPrevistaPagamento";
                rs = stm.executeQuery(sql);
                //GiroProdutoController giroProdutoController = new GiroProdutoController();
                
                    StringBuffer contenu; //// acho que seria melhor usar o StringBuilder
                    contenu = new StringBuffer("");
                    ResultSetMetaData rsMeta = rs.getMetaData();
                    for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
                    contenu.append(rsMeta.getColumnLabel(i) + "\t"); /// nesta linha imprime somente os nome dos campos da tabela  
                }
                contenu.append("\n"); // e temos que colocar todos os dados no StringBuffer  
                rs.beforeFirst();
                while (rs.next()) {
                    for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
                        contenu.append(rs.getString(i) + "\t"); /// aqui mostra todos os dados  
                    }
                    contenu.append("\n");

                } //fim do while  
                //agora, salvando o StringBuffer no arquivo  
                FileWriter excelFile = new FileWriter(caminhoSalvarExcel); // nome do arquivo  
                excelFile.write(new String(contenu)); //aqui ele passa a String para salvar  
                excelFile.close();
                JOptionPane.showMessageDialog(null, "Invoice Exportado com Sucesso");
                return rs;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    
    public void excluir(int idInvoice) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Invoice invoice = manager.find(Invoice.class, idInvoice);
		manager.remove(invoice);
		tx.commit();
		manager.close();
	}
    
}
