package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivo1;

public class Arquivo1Dao {
    
    public Arquivo1 salvar(Arquivo1 arquivo1) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        arquivo1 = manager.merge(arquivo1);
        tx.commit();
        
        return arquivo1;
    }
    
    public List<Arquivo1> listar(String sql)throws SQLException{
    		EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        @SuppressWarnings("unchecked")
		List<Arquivo1> lista = q.getResultList();
        return lista;
    }
    
    public void excluir(int idarquivo1) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Arquivo1 c where c.idarquivo1=" + idarquivo1);
        if (q.getResultList().size()>0){
        	Arquivo1 arquivo1 = (Arquivo1) q.getResultList().get(0);
            manager.remove(arquivo1);
        }
        tx.commit();
        
    }
}
