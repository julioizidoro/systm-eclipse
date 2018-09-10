package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;


import br.com.travelmate.dao.PagamentoInvoiceDao;
import br.com.travelmate.model.Pagamentoinvoice;

public class PagamentoInvoiceFacade {
	
	private PagamentoInvoiceDao pagamentoInvoiceDao;
	
	public Pagamentoinvoice salvar(Pagamentoinvoice pagamentoinvoice) {
		pagamentoInvoiceDao = new PagamentoInvoiceDao();
		try {
			return pagamentoInvoiceDao.salvar(pagamentoinvoice);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Pagamentoinvoice> listar(String sql){
		pagamentoInvoiceDao = new PagamentoInvoiceDao();
		try {
			return pagamentoInvoiceDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
