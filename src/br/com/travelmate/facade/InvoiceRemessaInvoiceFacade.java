package br.com.travelmate.facade;

import br.com.travelmate.dao.InvoiceRemssaInvoiceDao;
import br.com.travelmate.model.Invoiceremessainvoice;

public class InvoiceRemessaInvoiceFacade {
	
	public Invoiceremessainvoice salvar(Invoiceremessainvoice invoiceRemessaInvoice){
		InvoiceRemssaInvoiceDao invoiceRemssaInvoiceDao = new InvoiceRemssaInvoiceDao();
		return invoiceRemssaInvoiceDao.salvar(invoiceRemessaInvoice);
	}
	
	public void excluir(Invoiceremessainvoice invoiceRemessaInvoice){
		InvoiceRemssaInvoiceDao invoiceRemssaInvoiceDao = new InvoiceRemssaInvoiceDao();
		invoiceRemssaInvoiceDao.excluir(invoiceRemessaInvoice);
	}

}
