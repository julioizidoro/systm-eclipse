
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Voluntariadoprojetocurso;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class VoluntariadoProjetoCursoDao {
    
    public Voluntariadoprojetocurso salvar(Voluntariadoprojetocurso voluntariadoprojetocurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		voluntariadoprojetocurso = manager.merge(voluntariadoprojetocurso);
        tx.commit();
        return voluntariadoprojetocurso;
    }
    
    public Voluntariadoprojetocurso consultar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
 		EntityTransaction tx = manager.getTransaction();
 		tx.begin();
        Query q = manager.createQuery(sql);
        Voluntariadoprojetocurso voluntariadoprojetocurso = null;
        if (q.getResultList().size()>0){
            voluntariadoprojetocurso = (Voluntariadoprojetocurso) q.getResultList().get(0);
        }
        tx.commit();
        return voluntariadoprojetocurso;
    }
    
    public void excluir(int idVoluntariadoprojetocurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Voluntariadoprojetocurso voluntariadoprojetocurso = manager.find(Voluntariadoprojetocurso.class, idVoluntariadoprojetocurso);
        manager.remove(voluntariadoprojetocurso);
        tx.commit();
    }
    
    @SuppressWarnings("unchecked")
	public List<Voluntariadoprojetocurso> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Voluntariadoprojetocurso> lista = null;
        if (q.getResultList().size()>0){
        	lista =  q.getResultList();
        }  
        return lista;
    }
    
    
    
}
