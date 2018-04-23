package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivo2;

public class Arquivo2Dao {

    public Arquivo2 salvar(Arquivo2 arquivo2) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        arquivo2 = manager.merge(arquivo2);
        tx.commit();
        manager.close();
        return arquivo2;
    }
    
    public List<Arquivo2> listar(String sql)throws SQLException{
    	EntityManager manager;  
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Arquivo2> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idarquivo2) throws SQLException {
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Arquivo2 c where c.idarquivo2=" + idarquivo2);
        if (q.getResultList().size()>0){
        	Arquivo2 arquivo2 = (Arquivo2) q.getResultList().get(0);
            manager.remove(arquivo2);
        }
        tx.commit();
        manager.close();
    }
}
