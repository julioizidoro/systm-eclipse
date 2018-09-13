package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Promocaocurso; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PromocaoCursoDao {
    
    public Promocaocurso salvar(Promocaocurso promocaoCurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaoCurso = manager.merge(promocaoCurso);
        tx.commit();
        
        return promocaoCurso;
    }
    
    
    public List<Promocaocurso> listar(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Promocaocurso> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        
        return lista;
    }
    
    
    public Promocaocurso consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Promocaocurso Promocaocurso = null;
        if(q.getResultList().size()>0){
            Promocaocurso =  (Promocaocurso) q.getResultList().get(0);
            return Promocaocurso;
        }
        
        return null;
    }
    
    public void excluir(Promocaocurso promocaocurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaocurso = manager.find(Promocaocurso.class, promocaocurso.getIdpromoacaocurso());
        manager.remove(promocaocurso);
        tx.commit();
        
    }
}
