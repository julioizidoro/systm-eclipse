package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Coprodutos;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class CoProdutosDao {
    
    public Coprodutos salvar(Coprodutos coObrigatorio) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        coObrigatorio = manager.merge(coObrigatorio);
        tx.commit();
        
        return coObrigatorio;
    }
    
    
    public List<Coprodutos> listar(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Coprodutos> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        
        return lista;
    }
    
    
    public Coprodutos consultar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Coprodutos coprodutos = null;
        if(q.getResultList().size()>0){
            coprodutos =  (Coprodutos) q.getResultList().get(0);
        }
        return coprodutos;
    }
}
