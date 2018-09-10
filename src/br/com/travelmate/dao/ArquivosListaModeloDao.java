package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.model.Arquivoslistamodelo;

public class ArquivosListaModeloDao implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	public List<Arquivoslistamodelo> lista(String sql) {
        Query q = manager.createQuery(sql);
        List<Arquivoslistamodelo> lista = q.getResultList();
        return lista;
    }

}
