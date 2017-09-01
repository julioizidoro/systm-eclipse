package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Docs;



public class DocsDao {
	
	public Docs salvar(Docs docs) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        docs = manager.merge(docs);
        tx.commit();
        manager.close();
        return docs;
    }
	
	public Docs consultarVenda(int idVenda) throws SQLException{
		EntityManager manager;
    	manager =  ConectionFactory.getConnection();
        Query q = manager.createQuery("Select d from Docs d where d.vendas.idvendas=" + idVenda);
        Docs docs = null;
        if (q.getResultList().size()>0){
        	docs = (Docs) q.getResultList().get(0);
        }
        manager.close();
        return docs;
    }
}
