package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivo5;

public class Arquivo5Dao {
    
    public Arquivo5 salvar(Arquivo5 arquivo5) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        arquivo5 = manager.merge(arquivo5);
        tx.commit();
        manager.close();
        return arquivo5;
    }
    
    public List<Arquivo5> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Arquivo5> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idarquivo5) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        tx.begin();
        Query q = manager.createQuery("Select c from Arquivo5 c where c.idarquivo5=" + idarquivo5);
        if (q.getResultList().size()>0){
        	Arquivo5 arquivo5 = (Arquivo5) q.getResultList().get(0);
            manager.remove(arquivo5);
        }
        tx.commit();
        manager.close();
    }
}
