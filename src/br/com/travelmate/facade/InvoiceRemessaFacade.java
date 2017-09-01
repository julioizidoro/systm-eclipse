package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.InvoiceRemessaDao;
import br.com.travelmate.model.Invoiceremessa;

public class InvoiceRemessaFacade {
	
	public Invoiceremessa salvar(Invoiceremessa invoiceRemessa){
		InvoiceRemessaDao invoiceRemessaDao = new InvoiceRemessaDao();
		return invoiceRemessaDao.salvar(invoiceRemessa);
	}
	
	public List<Invoiceremessa> listar(String sql)throws SQLException{
		InvoiceRemessaDao invoiceRemessaDao = new InvoiceRemessaDao();
		return invoiceRemessaDao.listar(sql);
	}
	
	public void excluir(Invoiceremessa invoiceRemessa){
		InvoiceRemessaDao invoiceRemessaDao = new InvoiceRemessaDao();
		invoiceRemessaDao.excluir(invoiceRemessa);
	}

}
