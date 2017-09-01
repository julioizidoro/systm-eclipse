/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Ocursoformapagamento;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class OCursoFormaPagamentoDao {
    
    public Ocursoformapagamento salvar(Ocursoformapagamento formaPagamento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        formaPagamento = manager.merge(formaPagamento);
        tx.commit();
        manager.close();
        return formaPagamento;
    }
    
    public void excluir(int idOcurso) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
    	tx.begin();
        Query q = manager.createNativeQuery("delete from Ocursoformapagamento  where ocurso_idocurso=" + idOcurso);
        q.executeUpdate();
        tx.commit();
        manager.close();
     }
    
    public List<Ocursoformapagamento> lista(int idOcurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Ocursoformapagamento p where p.ocurso.idocurso=" + idOcurso);
        List<Ocursoformapagamento> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Ocursoformapagamento consultar(int idOcurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Ocursoformapagamento p where p.ocurso.idocurso=" + idOcurso);
        Ocursoformapagamento forma = null;
        if (q.getResultList().size() > 0) {
            forma = (Ocursoformapagamento) q.getResultList().get(0);
        }
        manager.close();
        return forma;
    }
}
