package br.com.travelmate.dao;
 
import java.sql.SQLException;
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;  
import br.com.travelmate.model.Voluntariadopacote; 

public class VoluntariadoPacoteDao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	 
	public Voluntariadopacote salvar(Voluntariadopacote voluntariadopacote) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		voluntariadopacote = manager.merge(voluntariadopacote);
        tx.commit();
        manager.close();
        return voluntariadopacote; 
    } 
    
    public Voluntariadopacote consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql); 
		Voluntariadopacote voluntariadopacote = null; 
        if (q.getResultList().size() > 0) {
        	voluntariadopacote =  (Voluntariadopacote) q.getResultList().get(0);
        } 
        return voluntariadopacote;
    }
     
    public void excluir(int idvoluntariadopacote) throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Voluntariadopacote voluntariadopacote = manager.find(Voluntariadopacote.class, idvoluntariadopacote);
        manager.remove(voluntariadopacote);  
        tx.commit();
        manager.close();
    }
     
    public List<Voluntariadopacote> lista(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Voluntariadopacote> lista = q.getResultList();
        return lista; 
    }

}
