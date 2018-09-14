/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.dao;


import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Formapagamento;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
@SuppressWarnings("unchecked")
public class FormaPagamentoDao {
    
	public Formapagamento salvar(Formapagamento formaPagamento) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        formaPagamento = manager.merge(formaPagamento);
        tx.commit();
        manager.close();
        return formaPagamento;
    }
    
	public void excluir(int idFormaPagamento) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Formapagamento formaPagamento = manager.find(Formapagamento.class, idFormaPagamento);
        manager.remove(formaPagamento);
        tx.commit();
        manager.close();
    }
    
	public List<Formapagamento> listar(int idVenda) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select f from Formapagamento f where f.vendas=" + idVenda);
        List<Formapagamento> lista = q.getResultList();
        manager.close();
        return lista;
    } 
    
    public Formapagamento consultar(int idVenda) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select f from Formapagamento f where f.vendas.idvendas=" + idVenda);
        Formapagamento forma = null;
        if (q.getResultList().size() > 0) {
            forma = (Formapagamento) q.getResultList().get(0);
        }
        manager.close();
        return forma;
    }
}
