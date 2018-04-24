package br.com.travelmate.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Invoiceremessainvoice;

public class InvoiceRemssaInvoiceDao {
	
	public Invoiceremessainvoice salvar(Invoiceremessainvoice invoiceRemessaInvoice){
		EntityManager manager;
	    manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
	    invoiceRemessaInvoice = manager.merge(invoiceRemessaInvoice);
	    tx.commit();
	    return invoiceRemessaInvoice;
	}
	
	public void excluir(Invoiceremessainvoice invoiceRemessaInvoice){
		EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		invoiceRemessaInvoice = manager.find(Invoiceremessainvoice.class, invoiceRemessaInvoice.getIdinvoiceremessainvoice());
        manager.remove(invoiceRemessaInvoice);
        tx.commit();
	}
	

}
