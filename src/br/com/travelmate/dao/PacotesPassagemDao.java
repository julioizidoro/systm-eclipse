/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Pacotepassagem;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PacotesPassagemDao {
    
    public Pacotepassagem salvar(Pacotepassagem pacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pacote = manager.merge(pacote);
        tx.commit();
        manager.close();
        return pacote;
    }
    
    public Pacotepassagem consultar(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Pacotepassagem p where p.pacotetrecho.idpacotetrecho=" + idPacote);
        Pacotepassagem pacote = null;
        if (q.getResultList().size() > 0) {
            pacote = (Pacotepassagem) q.getResultList().get(0);
        } 
        manager.close();
        return pacote;
    }
    
    
    public void excluir(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Pacotepassagem pacote = manager.find(Pacotepassagem.class, idPacote);
        manager.remove(pacote);
        tx.commit();
        manager.close();
    }
    
    public List<Pacotepassagem> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pacotepassagem> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
}
