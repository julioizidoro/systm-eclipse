package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Videopasta2;

public class VideoPasta2Dao {

	public Videopasta2 salvar(Videopasta2 videopasta2) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		videopasta2 = manager.merge(videopasta2);
        tx.commit();
        manager.close();
        return videopasta2;
    }
    
    public List<Videopasta2> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Videopasta2> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idvideopasta2) throws SQLException {
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Videopasta2 v where v.idvideopasta2=" + idvideopasta2);
        if (q.getResultList().size()>0){
        	Videopasta2 videopasta2 = (Videopasta2) q.getResultList().get(0);
            manager.remove(videopasta2);
        }
        tx.commit();
        manager.close();
    }
}
