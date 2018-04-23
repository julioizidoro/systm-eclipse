package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Videopasta1;

public class VideoPasta1Dao {

	public Videopasta1 salvar(Videopasta1 videopasta1) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		videopasta1 = manager.merge(videopasta1);
        tx.commit();
        manager.close();
        return videopasta1;
    }
    
    public List<Videopasta1> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Videopasta1> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idvideopasta1) throws SQLException {
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Videopasta1 c where c.idvideopasta1=" + idvideopasta1);
        if (q.getResultList().size()>0){
        	Videopasta1 videopasta1 = (Videopasta1) q.getResultList().get(0);
            manager.remove(videopasta1);
        }
        tx.commit();
        manager.close();
    }
}
