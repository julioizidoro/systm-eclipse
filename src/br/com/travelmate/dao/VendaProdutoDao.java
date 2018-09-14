package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Vendaproduto; 

public class VendaProdutoDao {

	public Vendaproduto salvar(Vendaproduto vendaproduto) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		vendaproduto = manager.merge(vendaproduto);
		tx.commit();
		manager.close();
		return vendaproduto;
	}

	public Vendaproduto lista(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Vendaproduto vendaproduto = null;
		if (q.getResultList().size() > 0) {
			vendaproduto = (Vendaproduto) q.getResultList().get(0);
		}
		manager.close();
		return vendaproduto;
	}

    @SuppressWarnings("unchecked")
	public List<Vendaproduto> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Vendaproduto> lista = q.getResultList();
		manager.close();
		return lista;
	}

}
