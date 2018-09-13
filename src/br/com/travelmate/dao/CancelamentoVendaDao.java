package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cancelamentovenda;



public class CancelamentoVendaDao {
	
	public Cancelamentovenda salvar(Cancelamentovenda cancelamentovenda)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		cancelamentovenda= manager.merge(cancelamentovenda);
		tx.commit();
		manager.close();
		return cancelamentovenda;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cancelamentovenda> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Cancelamentovenda> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}

}
