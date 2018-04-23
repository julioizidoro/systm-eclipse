package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Promocaocursocidade; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PromocaoCursoFornecedorCidadeDao {
    
    public Promocaocursocidade salvar(Promocaocursocidade Promocaocursocidade) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Promocaocursocidade = manager.merge(Promocaocursocidade);
        tx.commit();
        manager.close();
        return Promocaocursocidade;
    }
    
    
    public List<Promocaocursocidade> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Promocaocursocidade> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    
    public Promocaocursocidade consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Promocaocursocidade Promocaocursocidade = null;
        if(q.getResultList().size()>0){
           Promocaocursocidade =  (Promocaocursocidade) q.getResultList().get(0);
        }
        manager.close();
        return Promocaocursocidade;
    }
    
    
    public void excluir(Promocaocursocidade promocao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocao = manager.find(Promocaocursocidade.class, promocao.getIdpromocaocursocidade());
        manager.remove(promocao);
        tx.commit();
        manager.close();
    }
}
