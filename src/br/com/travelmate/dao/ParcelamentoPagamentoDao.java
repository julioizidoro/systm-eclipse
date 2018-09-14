/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Parcelamentopagamento;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class ParcelamentoPagamentoDao {
    
    public Parcelamentopagamento salvar(Parcelamentopagamento parcelamentopagamento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        parcelamentopagamento = manager.merge(parcelamentopagamento);
        tx.commit();
        manager.close();
        return parcelamentopagamento;
    }
    
     public void excluir(int idParcelamentopagamento) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Parcelamentopagamento passagem = manager.find(Parcelamentopagamento.class, idParcelamentopagamento);
        manager.remove(passagem);
        tx.commit();
        manager.close();
    }
     
     @SuppressWarnings("unchecked")
	public List<Parcelamentopagamento> listar(int idFormaPagamento) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Parcelamentopagamento p where p.formapagamento.idformaPagamento=" + idFormaPagamento);
        List<Parcelamentopagamento> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
}
