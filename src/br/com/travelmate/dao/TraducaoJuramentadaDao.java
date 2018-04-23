package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Traducaojuramentada;

public class TraducaoJuramentadaDao {
	
	public Traducaojuramentada salvar(Traducaojuramentada traducaojuramentada) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		traducaojuramentada = manager.merge(traducaojuramentada);
        tx.commit();
        manager.close();
        return traducaojuramentada;
    } 
    
    public Traducaojuramentada consultar(String sql) throws SQLException {
    		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Traducaojuramentada traducaojuramentada = null; 
        if (q.getResultList().size() > 0) {
        	traducaojuramentada =  (Traducaojuramentada) q.getResultList().get(0);
        } 
        manager.close();
        return traducaojuramentada;
    }
    
    public void excluir(int id) throws SQLException{
    		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Traducaojuramentada traducaojuramentada = manager.find(Traducaojuramentada.class, id);
        manager.remove(traducaojuramentada);
        tx.commit();
        manager.close();
    }
    
    
    public List<Traducaojuramentada> lista(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Traducaojuramentada> lista = q.getResultList();
        manager.close();
        return lista;
    }

}
