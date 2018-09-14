/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Vendas;

/**
 *
 * @author Wolverine
 */
public class VendasDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	@Transactional
    public Vendas salvar(Vendas venda) {
	    venda = manager.merge(venda);
        return venda;
    }
    
    public Vendas consultarVendas(int idVenda) {
        Vendas venda = manager.find(Vendas.class, idVenda);
        return venda;
    }
    
    @Transactional
    public void excluir(int idVenda) {
        Vendas venda = manager.find(Vendas.class, idVenda);
        manager.remove(venda);
    }
    
    public Vendas vendaCliente(int idCliente){
        Query q = manager.createQuery("Select v from Vendas v where v.cliente=" + idCliente);
        Vendas venda = null;
        if (q.getResultList().size()>0){
            venda =  (Vendas) q.getResultList().get(0);
        }
        return venda;
    }
    
    @SuppressWarnings("unchecked")
	public List<Vendas> lista(String sql) {
        Query q = manager.createQuery(sql);
        List<Vendas> lista = q.getResultList();
        return lista;
    }
    
    public Double saldoAcumulado(String sql){
    	Double valor = 0.0;
    	Query query = manager.createNativeQuery(sql);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();   
        }
    	return valor;
    	
    }
    
    public Long numVendas(String sql){
    	Long valor =  0L;
    	Query query = manager.createNativeQuery(sql); 
        if (query.getResultList().size()>0){
        	valor =  (Long) query.getResultList().get(0);        
        }
    	return valor; 
    }
    
	public BigDecimal percentualVendas(String sql)  {
		BigDecimal valor = BigDecimal.valueOf(0.0);
		Query query = manager.createNativeQuery(sql);
		if (query.getResultList().size() > 0) {
			valor = (BigDecimal) query.getResultList().get(0);
		}
		return valor;
	}
}
