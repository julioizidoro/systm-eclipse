package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pagamentoinvoice;

public class PagamentoInvoiceDao {
	
	public Pagamentoinvoice salvar(Pagamentoinvoice pagamentoinvoice) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pagamentoinvoice = manager.merge(pagamentoinvoice);
        tx.commit();
        manager.close();
        return pagamentoinvoice;
    }
	
	public List<Pagamentoinvoice> listar(String sql) throws SQLException {
		EntityManager manager;
    	manager =  ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pagamentoinvoice> lista = q.getResultList();
        manager.close();
        return lista;
    }

}
