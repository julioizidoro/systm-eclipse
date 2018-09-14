package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.model.Vendas;

public class HeexportarDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<Vendas> lista(String sql) {
		Query q = manager.createQuery(sql);
		List<Vendas> lista = q.getResultList();
		return lista;
	}

}
