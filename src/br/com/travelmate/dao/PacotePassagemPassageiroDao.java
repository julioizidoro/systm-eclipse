package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacotepassagempassageiro;

public class PacotePassagemPassageiroDao {
	public List<Pacotepassagempassageiro> lista(String sql) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pacotepassagempassageiro> lista = q.getResultList();
        manager.close();
        return lista;
    }
	
	public void excluir(int idPassageiro) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacotepassagempassageiro passageiro = manager.find(Pacotepassagempassageiro.class, idPassageiro);
        manager.remove(passageiro);
        tx.commit();
        manager.close();
    }
     
	
	public Pacotepassagempassageiro salvar(Pacotepassagempassageiro passagempassageiro) throws SQLException{
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
