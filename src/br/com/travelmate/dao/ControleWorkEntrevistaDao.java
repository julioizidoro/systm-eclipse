package br.com.travelmate.dao;
 
import java.sql.SQLException;
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controleworkentrevista; 

public class ControleWorkEntrevistaDao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	 
	public Controleworkentrevista salvar(Controleworkentrevista controleworkentrevista) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		controleworkentrevista = manager.merge(controleworkentrevista);
        tx.commit();
        manager.close();
        return controleworkentrevista; 
    } 
    
    public Controleworkentrevista consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql); 
		Controleworkentrevista controleworkentrevista = null; 
        if (q.getResultList().size() > 0) {
        	controleworkentrevista =  (Controleworkentrevista) q.getResultList().get(0);
        } 
        return controleworkentrevista;
    }
     
    public void excluir(int idcontroleworkentrevista) throws SQLException  {
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Controleworkentrevista controleworkentrevista = manager.find(Controleworkentrevista.class, idcontroleworkentrevista);
        manager.remove(controleworkentrevista);  
        tx.commit();
        manager.close();
    }
     
    public List<Controleworkentrevista> lista(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Controleworkentrevista> lista = q.getResultList();
        return lista; 
    }

}
