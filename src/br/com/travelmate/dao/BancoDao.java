package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Banco; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
@SuppressWarnings("unchecked")
public class BancoDao {
    
    public List<Banco> listar() throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select b from Banco b order by b.nome");
        List<Banco> lista = q.getResultList();
        return lista;
    }
    
    
    public List<Banco> listar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Banco> lista = q.getResultList();
        return lista;
    }
    
    public Banco getBanco(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Banco banco = null;
        if (q.getResultList().size()>0){
        	banco = (Banco) q.getResultList().get(0);
        }
        return banco;
    }
    
    
    
    public Banco salvar(Banco banco) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        banco = manager.merge(banco);
        tx.commit();
        return banco;
    }
    
}
