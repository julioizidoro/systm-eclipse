package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivo3;

public class Arquivo3Dao {
    
    public Arquivo3 salvar(Arquivo3 arquivo3) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        arquivo3 = manager.merge(arquivo3);
        tx.commit();
        manager.close();
        return arquivo3;
    }
    
    public List<Arquivo3> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Arquivo3> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idarquivo3) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Arquivo3 c where c.idarquivo3=" + idarquivo3);
        if (q.getResultList().size()>0){
        	Arquivo3 arquivo3 = (Arquivo3) q.getResultList().get(0);
            manager.remove(arquivo3);
        }
        tx.commit();
        manager.close();
    }
}
