package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Heorcamentopais;

public class HeorcamentopaisDao {

	
	public Heorcamentopais salvar(Heorcamentopais heorcamentopais) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		heorcamentopais = manager.merge(heorcamentopais);
        tx.commit();
        manager.close();
        return heorcamentopais;
    }
	
	public List<Heorcamentopais> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Heorcamentopais> lista = q.getResultList();
        manager.close();
        return lista;
    }
	
	public void remover(int idheorcamentopais) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
    	Heorcamentopais heorcamentopais = manager.find(Heorcamentopais.class, idheorcamentopais);
        manager.remove(heorcamentopais);
        manager.close();
    } 
}
