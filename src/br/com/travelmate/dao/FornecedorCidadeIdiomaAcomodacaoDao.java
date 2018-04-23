/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcidadeidiomaacomodacao; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class FornecedorCidadeIdiomaAcomodacaoDao {
    
    public Fornecedorcidadeidiomaacomodacao salvar(Fornecedorcidadeidiomaacomodacao fornecedorcidadeidiomaacomodacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fornecedorcidadeidiomaacomodacao = manager.merge(fornecedorcidadeidiomaacomodacao);
        tx.commit();
        manager.close();
        return fornecedorcidadeidiomaacomodacao;
    }
    
    public Fornecedorcidadeidiomaacomodacao consultar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
 		EntityTransaction tx = manager.getTransaction();
 		tx.begin();
        Query q = manager.createQuery(sql);
        Fornecedorcidadeidiomaacomodacao fornecedorcidadeidiomaacomodacao = null;
        if (q.getResultList().size()>0){
        	fornecedorcidadeidiomaacomodacao = (Fornecedorcidadeidiomaacomodacao) q.getResultList().get(0);
        }
        tx.commit();
        manager.close();
        return fornecedorcidadeidiomaacomodacao;
    }
    
    public void excluir(int idFornecedorcidadeidiomaacomodacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorcidadeidiomaacomodacao voluntariadoprojetoacomodacao = manager.find(Fornecedorcidadeidiomaacomodacao.class, idFornecedorcidadeidiomaacomodacao);
        manager.remove(voluntariadoprojetoacomodacao);
        tx.commit();
        manager.close();
    }
    
    public List<Fornecedorcidadeidiomaacomodacao> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Fornecedorcidadeidiomaacomodacao> lista = null;
        if (q.getResultList().size()>0){
        	lista =  q.getResultList();
        }  
        manager.close();
        return lista;
    }
    
    
    
}
