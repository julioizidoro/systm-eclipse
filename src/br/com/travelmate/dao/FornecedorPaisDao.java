package br.com.travelmate.dao;

import java.sql.SQLException;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorpais;

public class FornecedorPaisDao {
 	
	public Fornecedorpais salvar(Fornecedorpais fornecedorpais) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fornecedorpais = manager.merge(fornecedorpais);
		tx.commit();
		manager.close();
		return fornecedorpais;
	}
	
	public Fornecedorpais buscar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Fornecedorpais fornecedorpais = null;
		if (q.getResultList().size()>0){
			fornecedorpais =  (Fornecedorpais) q.getSingleResult();
		}
		manager.close();
		return fornecedorpais;
	}

}
