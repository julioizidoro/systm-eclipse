package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Invoiceremessa;

public class InvoiceRemessaDao {
	
	
	public Invoiceremessa salvar(Invoiceremessa invoiceRemessa){
		EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        invoiceRemessa = manager.merge(invoiceRemessa);
        tx.commit();
        return invoiceRemessa;
	}
	
	@SuppressWarnings("unchecked")
	public List<Invoiceremessa> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Invoiceremessa> lista = q.getResultList();
        return lista;
    }
	
	public void excluir(Invoiceremessa invoiceRemessa){
		EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        invoiceRemessa = manager.find(Invoiceremessa.class, invoiceRemessa.getIdinvoiceremessa());
        manager.remove(invoiceRemessa);
        tx.commit();
	}

}
