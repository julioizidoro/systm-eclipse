package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Passagempassageiro;

public class PassagemPassageirosDao {
	
	public List<Passagempassageiro> lista(String sql) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        @SuppressWarnings("unchecked")
        List<Passagempassageiro> lista= q.getResultList();
        manager.close();
        return lista;
    }
	
	public void excluir(int idPassageiro) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Passagempassageiro passageiro = manager.find(Passagempassageiro.class, idPassageiro);
        manager.remove(passageiro);
        tx.commit();
        manager.close();
    }
     
	
	public Passagempassageiro salvar(Passagempassageiro passagempassageiro) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        passagempassageiro = manager.merge(passagempassageiro);
        tx.commit();
        manager.close();
        return passagempassageiro;
    }

}
