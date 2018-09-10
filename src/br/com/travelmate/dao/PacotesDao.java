/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacotes;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class PacotesDao {
    
    public Pacotes salvar(Pacotes pacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        tx.begin();
        pacote = manager.merge(pacote);
        tx.commit();
        manager.close();
        return pacote;
    }
    
     public void excluir(int idPacote) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacotes pacote = manager.find(Pacotes.class, idPacote);
        manager.remove(pacote);
        tx.commit();
        manager.close();
    }
    
    public Pacotes consultar(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Pacotes p where p.vendas.idvendas=" + idVenda);
        Pacotes pacote = null;
        if (q.getResultList().size() > 0) {
            pacote =   (Pacotes) q.getResultList().get(0);
        }
        manager.close();
        return pacote;
    }
    
    
    public List<Pacotes> consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pacotes> lista = q.getResultList();
        manager.close();
        return lista;
    }
}
