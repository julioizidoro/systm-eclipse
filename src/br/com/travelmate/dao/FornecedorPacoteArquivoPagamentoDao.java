/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorpacotearquivopagamento; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class FornecedorPacoteArquivoPagamentoDao {
    
    public Fornecedorpacotearquivopagamento salvar(Fornecedorpacotearquivopagamento pacotesfornecedor) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		pacotesfornecedor = manager.merge(pacotesfornecedor);
        tx.commit();
        return pacotesfornecedor;
    }
    
    public List<Fornecedorpacotearquivopagamento> listar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Fornecedorpacotearquivopagamento> listaPacotesfornecedor = q.getResultList();
        return listaPacotesfornecedor;
    }
    
    public Fornecedorpacotearquivopagamento consulta(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Fornecedorpacotearquivopagamento pacotesfornecedor = null;
        if (q.getResultList().size()>0){
        	pacotesfornecedor = (Fornecedorpacotearquivopagamento) q.getResultList().get(0);
        }
        return pacotesfornecedor;
    } 
}
