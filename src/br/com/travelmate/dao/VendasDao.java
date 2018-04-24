/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Vendas;

import java.math.BigDecimal;
import java.sql.SQLException; 
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class VendasDao {
    
    public Vendas salvar(Vendas venda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        venda = manager.merge(venda);
        tx.commit();
        return venda;
    }
    
    public Vendas consultarVendas(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Vendas venda = manager.find(Vendas.class, idVenda);
        return venda;
    }
    
    public void excluir(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Vendas venda = manager.find(Vendas.class, idVenda);
        manager.remove(venda);
        manager.close();
    }
    
    public Vendas vendaCliente(int idCliente) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("Select v from Vendas v where v.cliente=" + idCliente);
        Vendas venda = null;
        if (q.getResultList().size()>0){
            venda =  (Vendas) q.getResultList().get(0);
        }
        return venda;
    }
    
    public List<Vendas> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Vendas> lista = q.getResultList();
        return lista;
    }
    
    public Double saldoAcumulado(String sql)throws SQLException{
    	EntityManager manager;
    	Double valor = 0.0;
    	manager = ConectionFactory.getInstance();
        Query query = manager.createNativeQuery(sql);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();   
        }
    	return valor;
    	
    }
    
    public Long numVendas(String sql)throws SQLException{
    	EntityManager manager;
    	Long valor =  0L;
    	manager = ConectionFactory.getInstance();
		Query query = manager.createNativeQuery(sql); 
        if (query.getResultList().size()>0){
        	valor =  (Long) query.getResultList().get(0);        
        }
    	return valor; 
    }
    
	public BigDecimal percentualVendas(String sql) throws SQLException {
		EntityManager manager;
		BigDecimal valor = BigDecimal.valueOf(0.0);
		manager = ConectionFactory.getInstance();
		Query query = manager.createNativeQuery(sql);
		if (query.getResultList().size() > 0) {
			valor = (BigDecimal) query.getResultList().get(0);
		}
		return valor;
	}
}
