/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Terceiros;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class TerceirosDao {
    
    @SuppressWarnings("unchecked")
	public List<Terceiros> lista() throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select t from Terceiros t order by t.nome");
        List<Terceiros> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    
    public Terceiros consultar(int idTerceiros) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Terceiros terceiros = manager.find(Terceiros.class, idTerceiros);
        manager.close();
        return terceiros;
    }
    
    public Terceiros salvar(Terceiros terceiros) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        terceiros = manager.merge(terceiros);
        tx.commit();
        manager.close();
        return terceiros;
    }
    
    public void excluir(int idTerceiros) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("select t from Terceiros t where t.idterceiros=" + idTerceiros);
        if (q.getResultList().size()>0){
            Terceiros terceiros = (Terceiros) q.getResultList().get(0);
            manager.remove(terceiros);
        }
        tx.commit();
        manager.close();
    }
}
