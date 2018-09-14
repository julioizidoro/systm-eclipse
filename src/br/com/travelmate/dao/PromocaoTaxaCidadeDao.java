package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;  
import br.com.travelmate.model.Promocaotaxacidade;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PromocaoTaxaCidadeDao {
    
    public Promocaotaxacidade salvar(Promocaotaxacidade promocaotaxacidade) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaotaxacidade = manager.merge(promocaotaxacidade);
        tx.commit();
        
        return promocaotaxacidade;
    }
    

    @SuppressWarnings("unchecked")
    public List<Promocaotaxacidade> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Promocaotaxacidade> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        
        return lista;
    }
    
    
    public Promocaotaxacidade consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Promocaotaxacidade Promocaotaxacidade = null;
        if(q.getResultList().size()>0){
        	Promocaotaxacidade =  (Promocaotaxacidade) q.getResultList().get(0);
        }
        
        if(Promocaotaxacidade!=null){
        	return Promocaotaxacidade;
        }
        return null;
    }
    
    
    public void excluir(Promocaotaxacidade promocaotaxacidade) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaotaxacidade = manager.find(Promocaotaxacidade.class, promocaotaxacidade.getIdpromocaotaxacidade());
        manager.remove(promocaotaxacidade);
        tx.commit();
        
    }
}
