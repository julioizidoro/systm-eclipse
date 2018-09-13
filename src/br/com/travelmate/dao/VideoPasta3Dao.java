package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Videopasta3;

public class VideoPasta3Dao {
	
	public Videopasta3 salvar(Videopasta3 videopasta3) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		videopasta3 = manager.merge(videopasta3);
        tx.commit();
        
        return videopasta3;
    }
    
    @SuppressWarnings("unchecked")
	public List<Videopasta3> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Videopasta3> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idvideopasta3) throws SQLException {
        EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select v from Videopasta3 v where v.idvideopasta3=" + idvideopasta3);
        if (q.getResultList().size()>0){
        	Videopasta3 videopasta3 = (Videopasta3) q.getResultList().get(0);
            manager.remove(videopasta3);
        }
        tx.commit();
        
    }

}
