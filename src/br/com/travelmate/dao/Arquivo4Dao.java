package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivo4;

public class Arquivo4Dao {
    
    public Arquivo4 salvar(Arquivo4 arquivo4) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        tx.begin();
        arquivo4 = manager.merge(arquivo4);
        tx.commit();
        manager.close();
        return arquivo4;
    }
    
    public List<Arquivo4> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Arquivo4> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idarquivo4) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Arquivo4 c where c.idarquivo4=" + idarquivo4);
        if (q.getResultList().size()>0){
        	Arquivo4 arquivo4 = (Arquivo4) q.getResultList().get(0);
            manager.remove(arquivo4);
        }
        tx.commit();
        manager.close();
    }
}
