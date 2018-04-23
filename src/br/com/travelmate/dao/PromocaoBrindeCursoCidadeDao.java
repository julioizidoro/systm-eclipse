package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Promocaobrindecursocidade; 
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PromocaoBrindeCursoCidadeDao {
    
    public Promocaobrindecursocidade salvar(Promocaobrindecursocidade promocaobrindecursocidade) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaobrindecursocidade = manager.merge(promocaobrindecursocidade);
        tx.commit();
        
        return promocaobrindecursocidade;
    }
    
    
    public List<Promocaobrindecursocidade> listar(String sql)throws SQLException{
    	 EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Promocaobrindecursocidade> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    
    public Promocaobrindecursocidade consultar(String sql) throws SQLException{
    	 EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Promocaobrindecursocidade Promocaobrindecursocidade = null;
        if(q.getResultList().size()>0){
        	Promocaobrindecursocidade =  (Promocaobrindecursocidade) q.getResultList().get(0);
        }
        manager.close();
        return Promocaobrindecursocidade;
    }
    
    
    public void excluir(Promocaobrindecursocidade promocaobrindecursocidade) throws SQLException{
    	 EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaobrindecursocidade = manager.find(Promocaobrindecursocidade.class, promocaobrindecursocidade.getIdpromocaobrindecursocidade());
        manager.remove(promocaobrindecursocidade);
        tx.commit();
        manager.close();
    }
}
