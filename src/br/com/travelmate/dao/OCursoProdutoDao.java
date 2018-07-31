/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Ocrusoprodutos;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class OCursoProdutoDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
    
    @Transactional
	public Ocrusoprodutos salvar(Ocrusoprodutos produto){
        produto = manager.merge(produto);
        return produto;
    }
    
    public Ocrusoprodutos consultar(int idOcurso){
    	Query q = manager.createQuery("select p from Ocursoprodutos p where p.ocurso.idocurso=" + idOcurso);
        Ocrusoprodutos produto = null;
        if (q.getResultList().size() > 0) {
            produto =   (Ocrusoprodutos) q.getResultList().get(0);
        }
        return produto;
    }
    
    public List<Ocrusoprodutos> listar(int idOcurso){
    	Query q = manager.createQuery("select p from Ocrusoprodutos p where p.ocurso.idocurso=" + idOcurso);
        List<Ocrusoprodutos> lista = q.getResultList();
        return lista;
    }
    
    public List<Ocrusoprodutos> lista(String sql) {
    	Query q = manager.createQuery(sql);
        List<Ocrusoprodutos> lista = q.getResultList();
        return lista;
    }
    
    public void excluir(int idOcursoprodutos)  {
    	Query q = manager.createQuery("Select c from Ocrusoprodutos c where c.idocrusoprodutos=" + idOcursoprodutos);
        if (q.getResultList().size()>0){
        	Ocrusoprodutos ocrusoprodutos = (Ocrusoprodutos) q.getResultList().get(0);   
            manager.remove(ocrusoprodutos);
        }    
    }
}
