package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Cartaocredito;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class CartaoCreditoDao {
    
    
    
    public List<Cartaocredito> listar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Cartaocredito> lista = q.getResultList();
        return lista;
    }
    
    public Cartaocredito consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Cartaocredito cartaocredito = null;
        if (q.getResultList().size()>0){
        	cartaocredito = (Cartaocredito) q.getResultList().get(0);
        }
        return cartaocredito;
    }
    
    
    
    public Cartaocredito salvar(Cartaocredito cartaocredito) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		cartaocredito = manager.merge(cartaocredito);
        tx.commit();
        return cartaocredito;
    }
    
}
