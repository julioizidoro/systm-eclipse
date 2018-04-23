/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorpacotearquivoinvoice; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class FornecedorPacoteArquivoInvoiceDao {
    
    public Fornecedorpacotearquivoinvoice salvar(Fornecedorpacotearquivoinvoice pacotesfornecedor) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		pacotesfornecedor = manager.merge(pacotesfornecedor);
        tx.commit();
        manager.close();
        return pacotesfornecedor;
    }
    
    public List<Fornecedorpacotearquivoinvoice> listar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Fornecedorpacotearquivoinvoice> listaPacotesfornecedor = q.getResultList();
        manager.close();
        return listaPacotesfornecedor;
    }
    
    public Fornecedorpacotearquivoinvoice consulta(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Fornecedorpacotearquivoinvoice pacotesfornecedor = null;
        if (q.getResultList().size()>0){
        	pacotesfornecedor = (Fornecedorpacotearquivoinvoice) q.getResultList().get(0);
        }
        manager.close();
        return pacotesfornecedor;
    } 
}
