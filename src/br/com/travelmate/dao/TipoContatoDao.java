package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Tipocontato; 

public class TipoContatoDao {

	public List<Tipocontato> lista(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Tipocontato> lista = q.getResultList();
		manager.close();
		return lista;
	}

	public Tipocontato consultar(int idTipocontato) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Tipocontato tipocontato = manager.find(Tipocontato.class, idTipocontato);
		manager.close();
		return tipocontato;
	}
	
	public Tipocontato consultar(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Tipocontato tipocontato= null;
        if (q.getResultList().size()>0){
        	tipocontato =  (Tipocontato) q.getSingleResult();
        } 
        manager.close();
        return tipocontato;
    }

	public Tipocontato salvar(Tipocontato tipocontato) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		tipocontato = manager.merge(tipocontato);
		tx.commit();
		manager.close();
		return tipocontato;
	}
 
}
