/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Passagemaerea;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class PassagemDao {
    
    public Passagemaerea salvar(Passagemaerea passageAerea) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        passageAerea = manager.merge(passageAerea);
        tx.commit();
        manager.close();
        return passageAerea;
    }
    
     public void excluir(int idPassagem) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Passagemaerea passagem = manager.find(Passagemaerea.class, idPassagem);
        manager.remove(passagem);
        tx.commit();
        manager.close();
    }
     
     public Passagemaerea consultar(int idVenda) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Passagemaerea p where p.vendas.idvendas=" + idVenda);
        Passagemaerea passagem = null;
        if (q.getResultList().size() > 0) {
            passagem =   (Passagemaerea) q.getResultList().get(0);
        }
        manager.close();
        return passagem;
    }
     
     public Passagemaerea consultarPassagem(int idPassagem) throws SQLException{
    	 EntityManager manager;
         manager = ConectionFactory.getConnection();
         Query q = manager.createQuery("select p from Passagemaerea p where p.idpassagemAerea=" + idPassagem);
         Passagemaerea passagem = null;
         if (q.getResultList().size() > 0) {
             passagem =   (Passagemaerea) q.getResultList().get(0);
         }
         manager.close();
         return passagem;
     }
     
     
     public List<Passagemaerea> lista(String sql) throws SQLException{
    	 EntityManager manager;
         manager = ConectionFactory.getConnection();
         Query q = manager.createQuery(sql);
         List<Passagemaerea> lista = q.getResultList();
         manager.close();
         return lista;
     }
    
}
