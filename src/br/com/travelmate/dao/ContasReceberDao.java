/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Eventocontasreceber;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


/**
 * 
 *
 * @author Wolverine
 */
public class ContasReceberDao {
    
    public Contasreceber salvar(Contasreceber conta) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        conta = manager.merge(conta);
        tx.commit();
        return conta;
    }
    
    public Contasreceber consultar(int idConta) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Contasreceber conta = manager.find(Contasreceber.class, idConta);
        return conta;
    }
    
    public void excluir(int idConta) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Contasreceber conta = manager.find(Contasreceber.class, idConta);
        manager.remove(conta);
        tx.commit();
    }
    
    public List<Contasreceber> listar(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Contasreceber> lista = q.getResultList();
        return lista;
    }
    
    public List<Eventocontasreceber> listarEventosContasReceber(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Eventocontasreceber> lista = q.getResultList();
        return lista;
    }
    
    public Contasreceber consultar(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Contasreceber conta = null;
        if (q.getResultList().size()>0){
            conta = (Contasreceber) q.getResultList().get(0);
        }
        return conta;
    }
    
    
    public void excluirEventoContasReceber(int idConta) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Eventocontasreceber conta = manager.find(Eventocontasreceber.class, idConta);
        manager.remove(conta);
        tx.commit();
    }
}
