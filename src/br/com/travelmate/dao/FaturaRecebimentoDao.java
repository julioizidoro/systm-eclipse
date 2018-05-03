package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Faturarecebimento;

public class FaturaRecebimentoDao {
	
	public Faturarecebimento salvar(Faturarecebimento faturaRecebimento) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		faturaRecebimento = manager.merge(faturaRecebimento);
        tx.commit();
        manager.close();
        return faturaRecebimento;
    }

}
