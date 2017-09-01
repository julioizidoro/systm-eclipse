package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pontuacaovendas;

public class PontuacaoVendasDao {
    
    public Pontuacaovendas salvar(Pontuacaovendas pontuacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        manager.getTransaction().begin();
        pontuacao = manager.merge(pontuacao);
        tx.commit();
        manager.close();
        return pontuacao;
    }
    
    public List<Pontuacaovendas> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pontuacaovendas> lista = q.getResultList();
        manager.close();
        return lista;
    }

}
