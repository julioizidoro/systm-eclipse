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
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
        tx.begin();
        arquivo4 = manager.merge(arquivo4);
        tx.commit();
        
        return arquivo4;
    }
    
    @SuppressWarnings("unchecked")
	public List<Arquivo4> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Arquivo4> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idarquivo4) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Arquivo4 c where c.idarquivo4=" + idarquivo4);
        if (q.getResultList().size()>0){
        	Arquivo4 arquivo4 = (Arquivo4) q.getResultList().get(0);
            manager.remove(arquivo4);
        }
        tx.commit();
        
    }
}
