/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Publicidade;

/**
 *
 * @author Wolverine
 */
public class PublicidadeDao {
    
    public Publicidade salvar(Publicidade publicidade) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        publicidade = manager.merge(publicidade);
        tx.commit();
        manager.close();
        return publicidade;
    }
    
    public Publicidade consultar(int idPublicidade) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Publicidade publicidade = manager.find(Publicidade.class, idPublicidade);
        manager.close();
        return publicidade;
    }

    @SuppressWarnings("unchecked")
    public List<Publicidade> listar() throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Publicidade  p WHERE p.mostrar=1 order by p.descricao");
        List<Publicidade> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
}
