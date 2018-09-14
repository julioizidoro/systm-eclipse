/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pacotetrecho;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PacoteTrechoDao {
    
    public Pacotetrecho salvar(Pacotetrecho pacotetrecho) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pacotetrecho = manager.merge(pacotetrecho);
        tx.commit();
        manager.close();
        return pacotetrecho;
    }
    

    @SuppressWarnings("unchecked")
    public List<Pacotetrecho> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Pacotetrecho> lista = null;
        if (q.getResultList().size()>0){
            lista = (List<Pacotetrecho>) q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public Pacotetrecho consultarTrecho(int idPacote) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Pacotetrecho p where p.pacotes.idpacotes=" + idPacote);
        Pacotetrecho pacote = null;
        if (q.getResultList().size() > 0) {
            pacote = (Pacotetrecho) q.getResultList().get(0);
        } 
        manager.close();
        return pacote;
    }
    
}
