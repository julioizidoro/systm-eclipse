/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Ladies;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class LadiesDao {
    
    public Ladies salvar(Ladies ladies) throws Exception{
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        ladies = manager.merge(ladies);
        tx.commit();
        manager.close();
        return ladies;
    }
    
    public List<Ladies> listar(String nome) throws Exception{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select l from Ladies l order by l.vendas.dataVenda ");
        List<Ladies> lista = q.getResultList();
        manager.close();
        return lista;
    }
}