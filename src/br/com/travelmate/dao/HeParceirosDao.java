package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Heparceiros;

public class HeParceirosDao {
	
	public Heparceiros salvar(Heparceiros heParceiros) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		heParceiros = manager.merge(heParceiros);
        tx.commit();
        manager.close();
        return heParceiros;
    }
	
	@SuppressWarnings("unchecked")
	public List<Heparceiros> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Heparceiros> lista = q.getResultList();
        manager.close();
        return lista;
    }
	
	public void remover(Heparceiros heParceiros) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
    	EntityTransaction tx = manager.getTransaction();
		tx.begin();
        heParceiros = manager.find(Heparceiros.class, heParceiros.getIdheparceiros());
        manager.remove(heParceiros);
        tx.commit();
        manager.close();
    } 

}
