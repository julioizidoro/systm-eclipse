package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Heorcamento;

public class HeorcamentoDao {

	
	public Heorcamento salvar(Heorcamento heorcamento) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		heorcamento = manager.merge(heorcamento);
        tx.commit();
        manager.close();
        return heorcamento;
    }
	
	public List<Heorcamento> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Heorcamento> lista = q.getResultList();
        manager.close();
        return lista;
    }
	
	public void remover(Heorcamento heorcamento) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
    	heorcamento = manager.find(Heorcamento.class, heorcamento);
        manager.remove(heorcamento);
        manager.close();
    } 
}
