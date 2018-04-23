package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivoskitviagem;

public class ArquivosKitViagemDao {
	
	public Arquivoskitviagem salvar(Arquivoskitviagem kitViagem) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection(); 
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		kitViagem = manager.merge(kitViagem); 
		tx.commit();
		manager.close();
		return kitViagem;
	}

}
