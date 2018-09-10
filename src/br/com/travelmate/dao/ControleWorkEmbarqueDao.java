package br.com.travelmate.dao;
 
import java.sql.SQLException;
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Controleworkembarque; 

public class ControleWorkEmbarqueDao {
	
	 
	public Controleworkembarque salvar(Controleworkembarque controleworkembarque) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		controleworkembarque = manager.merge(controleworkembarque);
        tx.commit();
        manager.close();
        return controleworkembarque; 
    } 
    
    public Controleworkembarque consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql); 
		Controleworkembarque controleworkembarque = null; 
        if (q.getResultList().size() > 0) {
        		controleworkembarque =  (Controleworkembarque) q.getResultList().get(0);
        } 
        return controleworkembarque;
    }
     
    public void excluir(int idcontroleworkembarque) throws SQLException  {
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Controleworkembarque controleworkembarque = manager.find(Controleworkembarque.class, idcontroleworkembarque);
        manager.remove(controleworkembarque);  
        tx.commit();
        manager.close();
    }
     
    public List<Controleworkembarque> lista(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Controleworkembarque> lista = q.getResultList();
        return lista; 
    }

}
