package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Promocaobrindecurso; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PromocaoBrindeCursoDao {
    
    public Promocaobrindecurso salvar(Promocaobrindecurso promocaobrindecurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaobrindecurso = manager.merge(promocaobrindecurso);
        tx.commit();
        
        return promocaobrindecurso;
    }
    
    
    public List<Promocaobrindecurso> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Promocaobrindecurso> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        
        return lista;
    }
    
    
    public Promocaobrindecurso consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Promocaobrindecurso promocaobrindecurso = null;
        if(q.getResultList().size()>0){
        	promocaobrindecurso =  (Promocaobrindecurso) q.getResultList().get(0);
        }
        
        return null;
    }
    
    public void excluir(Promocaobrindecurso promocaobrindecurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        promocaobrindecurso = manager.find(Promocaobrindecurso.class, promocaobrindecurso.getIdpromocaobrindecurso());
        manager.remove(promocaobrindecurso);
        tx.commit();
        
    }
}
