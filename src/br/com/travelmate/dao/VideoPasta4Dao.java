package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Videopasta4;

public class VideoPasta4Dao {

	
	public Videopasta4 salvar(Videopasta4 videopasta4) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		videopasta4 = manager.merge(videopasta4);
        tx.commit();
        
        return videopasta4;
    }
    
    @SuppressWarnings("unchecked")
	public List<Videopasta4> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Videopasta4> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idvideopasta4) throws SQLException {
        EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Videopasta4 v where v.idvideopasta4=" + idvideopasta4);
        if (q.getResultList().size()>0){
        	Videopasta4 videopasta4 = (Videopasta4) q.getResultList().get(0);
            manager.remove(videopasta4);
        }
        tx.commit();
        
    }
}
