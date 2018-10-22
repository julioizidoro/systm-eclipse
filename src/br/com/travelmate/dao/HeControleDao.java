package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Hecontrole;

public class HeControleDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	@Transactional
	public Hecontrole salvar(Hecontrole controle) {
		controle = manager.merge(controle);
        return controle; 
	}
	
	public Hecontrole consultar(String sql)    {
		Query q = manager.createQuery(sql); 
        Hecontrole controle = null; 
        if (q.getResultList().size() > 0) {
        		controle =  (Hecontrole) q.getResultList().get(0);
        } 
        return controle;
	}
	
	@SuppressWarnings("unchecked")
	public List<Hecontrole> listar(String sql)    {
		Query q = manager.createQuery(sql); 
        List<Hecontrole> lista = null; 
        if (q.getResultList().size() > 0) {
        		lista =  q.getResultList();
        } 
        return lista;
	}

	

}
