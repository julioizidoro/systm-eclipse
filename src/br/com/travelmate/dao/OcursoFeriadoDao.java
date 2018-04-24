package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Ocursoferiado; 

public class OcursoFeriadoDao {
	
	
	public Ocursoferiado salvar(Ocursoferiado  ocursoferiado) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        ocursoferiado = manager.merge(ocursoferiado);
        tx.commit();
        
        return ocursoferiado;
    }
    
    public List<Ocursoferiado> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Ocursoferiado> lista = q.getResultList();
        
        return lista;
    }

    public void excluir(int id) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Ocursoferiado ocursoferiado = manager.find(Ocursoferiado.class, id);
        manager.remove(ocursoferiado);
        tx.commit();
        
    }
}
