/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcidade;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class FornecedorCidadeDao {
    
    public Fornecedorcidade salvar(Fornecedorcidade fornecedorcidade) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        fornecedorcidade = manager.merge(fornecedorcidade);
        tx.commit();
        return fornecedorcidade;
    }
    
    public List<Fornecedorcidade> listar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Fornecedorcidade> listaFornecedorCidade = q.getResultList();
        return listaFornecedorCidade;
    }
    
    public Fornecedorcidade getFonecedorCidade(int idFornecedor, int idCidade) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("Select f from Fornecedorcidade f where f.fornecedor.idfornecedor=" + idFornecedor + " and f.cidade.idcidade=" + idCidade);
        Fornecedorcidade fornecedorcidade = null;
        if (q.getResultList().size()>0){
            fornecedorcidade = (Fornecedorcidade) q.getResultList().get(0);
        }
        return fornecedorcidade;
    }
    
    public Fornecedorcidade getFornecedorCidade(int idFornecedorCidade) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Fornecedorcidade fornecedorcidade = manager.find(Fornecedorcidade.class, idFornecedorCidade);
        return fornecedorcidade;
    }
    
    
    public Fornecedorcidade getFonecedorCidade(int idCodigo) throws SQLException{
    	EntityManager manager;
        if (idCodigo > 0) {
            manager = ConectionFactory.getInstance();
            Query q = manager.createQuery("Select f from Fornecedorcidade f where f.codigo=" + idCodigo);
            Fornecedorcidade fornecedorcidade = null;
            if (q.getResultList().size() > 0) {
                fornecedorcidade = (Fornecedorcidade) q.getResultList().get(0);
            }
            return fornecedorcidade;
        }
        return null;
    }
}
