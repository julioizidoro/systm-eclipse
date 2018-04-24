package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;  
import br.com.travelmate.model.Promocaotaxacurso;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PromocaoTaxaCursoDao {
    
    public Promocaotaxacurso salvar(Promocaotaxacurso promocaotaxacurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaotaxacurso = manager.merge(promocaotaxacurso);
        tx.commit();
        
        return promocaotaxacurso;
    }
    
    
    public List<Promocaotaxacurso> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Promocaotaxacurso> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        
        return lista;
    }
    
    
    public Promocaotaxacurso consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Promocaotaxacurso Promocaotaxacurso = null;
        if(q.getResultList().size()>0){
        	Promocaotaxacurso =  (Promocaotaxacurso) q.getResultList().get(0);
        }
        
        return null;
    }
    
    public void excluir(Promocaotaxacurso promocaotaxacurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaotaxacurso = manager.find(Promocaotaxacurso.class, promocaotaxacurso.getIdpromocaotaxacurso());
        manager.remove(promocaotaxacurso);
        tx.commit();
        
    }
}
