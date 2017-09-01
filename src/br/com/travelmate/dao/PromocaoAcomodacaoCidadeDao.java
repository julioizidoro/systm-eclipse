package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Promocaoacomodacaocidade;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PromocaoAcomodacaoCidadeDao {
    
    public Promocaoacomodacaocidade salvar(Promocaoacomodacaocidade Promocaoacomodacaocidade) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Promocaoacomodacaocidade = manager.merge(Promocaoacomodacaocidade);
        tx.commit();
        
        return Promocaoacomodacaocidade;
    }
    
    
    public List<Promocaoacomodacaocidade> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Promocaoacomodacaocidade> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        
        return lista;
    }
    
    
    public Promocaoacomodacaocidade consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Promocaoacomodacaocidade Promocaoacomodacaocidade = null;
        if(q.getResultList().size()>0){
           Promocaoacomodacaocidade =  (Promocaoacomodacaocidade) q.getResultList().get(0);
        }
        
        if(Promocaoacomodacaocidade!=null){
        	return Promocaoacomodacaocidade;
        }
        return null;
    }
    
    
    public void excluir(Promocaoacomodacaocidade promocao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocao = manager.find(Promocaoacomodacaocidade.class, promocao.getIdpromocaoacomodacaocidade());
        manager.remove(promocao);
        tx.commit();
        
    }
}
