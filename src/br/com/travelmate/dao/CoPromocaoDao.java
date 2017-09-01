package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Copromocao;

public class CoPromocaoDao {
    
    public Copromocao salvar(Copromocao coPromocao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        coPromocao = manager.merge(coPromocao);
        tx.commit();
        
        return coPromocao;
    }
    
    public List<Copromocao> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
       Query q = manager.createQuery(sql);
       List<Copromocao> lista = null;
       if (q.getResultList().size()>0){
           lista =  q.getResultList();
       }
       
       return lista;
   }
    
    public Copromocao consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Copromocao copromocao = null;
        if(q.getResultList().size()>0){
            copromocao =  (Copromocao) q.getResultList().get(0);
        }
        
        return copromocao;
    }
    
    public void excluir(Copromocao coPromocao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        coPromocao = manager.find(Copromocao.class, coPromocao.getIdcopromocao());
        if (coPromocao!=null){
        	manager.remove(coPromocao);
        }
        tx.commit();
        
    }
}
