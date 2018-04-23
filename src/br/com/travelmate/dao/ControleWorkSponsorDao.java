package br.com.travelmate.dao;
 
import java.sql.SQLException;
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controleworksponsor;  

public class ControleWorkSponsorDao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	 
	public Controleworksponsor salvar(Controleworksponsor controleworksponsor) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		controleworksponsor = manager.merge(controleworksponsor);
        tx.commit();
        manager.close();
        return controleworksponsor; 
    } 
    
    public Controleworksponsor consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql); 
		Controleworksponsor controleworksponsor = null; 
        if (q.getResultList().size() > 0) {
        	controleworksponsor =  (Controleworksponsor) q.getResultList().get(0);
        } 
        manager.close();
        return controleworksponsor;
    }
     
    public void excluir(int idcontroleworksponsor) throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Controleworksponsor controleworksponsor = manager.find(Controleworksponsor.class, idcontroleworksponsor);
        manager.remove(controleworksponsor);  
        tx.commit();
        manager.close();
    }
     
    public List<Controleworksponsor> lista(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Controleworksponsor> lista = q.getResultList();
        manager.close();
        return lista; 
    }

}
