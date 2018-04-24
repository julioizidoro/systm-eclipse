package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Crmcobranca;

public class CancelamentoDao {
	
	public Cancelamento salvar(Cancelamento cancelamento)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		cancelamento= manager.merge(cancelamento);
		tx.commit();
		manager.close();
		return cancelamento;
	}
	
	public List<Cancelamento> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Cancelamento> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
	
	
	public Cancelamento consultar(int idcancelamento) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance();
		Query q = manager.createQuery("SELECT c FROM Cancelamento c WHERE c.vendas.idvendas=" + idcancelamento);
		Cancelamento cancelamento = null;
		if (q.getResultList().size() > 0) {
			cancelamento = (Cancelamento) q.getResultList().get(0);
		}
		return cancelamento;
	}

}
