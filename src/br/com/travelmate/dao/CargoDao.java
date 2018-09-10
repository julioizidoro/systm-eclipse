package br.com.travelmate.dao;
 
import java.sql.SQLException;
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cargo;  

public class CargoDao {
	
	 
	public Cargo salvar(Cargo cargo) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		cargo = manager.merge(cargo);
        tx.commit();
        manager.close();
        return cargo; 
    } 
    
    public Cargo consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql); 
		Cargo cargo = null; 
        if (q.getResultList().size() > 0) {
        	cargo =  (Cargo) q.getResultList().get(0);
        } 
        return cargo;
    }
     
    public void excluir(int idCargo) throws SQLException  {
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Cargo cargo = manager.find(Cargo.class, idCargo);
        manager.remove(cargo);  
        tx.commit();
        manager.close();
    }
     
    public List<Cargo> lista(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Cargo> lista = q.getResultList();
        return lista; 
    }

}
