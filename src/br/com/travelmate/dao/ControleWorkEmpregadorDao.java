package br.com.travelmate.dao;
 
import java.sql.SQLException;
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Controleworkempregaor;  

public class ControleWorkEmpregadorDao {
	
	 
	public Controleworkempregaor salvar(Controleworkempregaor Controleworkempregador) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Controleworkempregador = manager.merge(Controleworkempregador);
        tx.commit();
        manager.close();
        return Controleworkempregador; 
    } 
    
    public Controleworkempregaor consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql); 
		Controleworkempregaor Controleworkempregador = null; 
        if (q.getResultList().size() > 0) {
        		Controleworkempregador =  (Controleworkempregaor) q.getResultList().get(0);
        } 
        return Controleworkempregador;
    }
     
    public void excluir(int idControleworkempregador) throws SQLException  {
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Controleworkempregaor Controleworkempregador = manager.find(Controleworkempregaor.class, idControleworkempregador);
        manager.remove(Controleworkempregador);  
        tx.commit();
        manager.close();
    }
     
    public List<Controleworkempregaor> lista(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Controleworkempregaor> lista = q.getResultList();
        return lista; 
    }

}
