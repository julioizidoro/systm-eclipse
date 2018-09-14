/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacoteseguro;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class PacoteSeguroDao {
    
    public Pacoteseguro salvar(Pacoteseguro pacoteseguro) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pacoteseguro = manager.merge(pacoteseguro);
        tx.commit(); 
        manager.close();
        return pacoteseguro;
    }
    
     public void excluir(int idPacoteSeguro) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacoteseguro pacoteseguro = manager.find(Pacoteseguro.class, idPacoteSeguro);
        manager.remove(pacoteseguro);
        tx.commit();
        manager.close();
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Pacoteseguro> consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pacoteseguro> lista = q.getResultList();
        manager.close();
        return lista;
    }
}
