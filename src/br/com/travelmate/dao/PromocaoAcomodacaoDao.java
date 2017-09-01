package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Promocaoacomodacao;


import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PromocaoAcomodacaoDao {
    
    public Promocaoacomodacao salvar(Promocaoacomodacao promocaoacomodacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        promocaoacomodacao = manager.merge(promocaoacomodacao);
        tx.commit();
        
        return promocaoacomodacao;
    }
    
    
    public List<Promocaoacomodacao> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Promocaoacomodacao> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        
        return lista;
    }
    
    
    public Promocaoacomodacao consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Promocaoacomodacao Promocaoacomodacao = null;
        if(q.getResultList().size()>0){
            Promocaoacomodacao =  (Promocaoacomodacao) q.getResultList().get(0);
        }
        
        return null;
    }
    
    public void excluir(Promocaoacomodacao promocaoacomodacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaoacomodacao = manager.find(Promocaoacomodacao.class, promocaoacomodacao.getIdpromoacaoacomodacao());
        manager.remove(promocaoacomodacao);
        tx.commit();
        
    }
}
