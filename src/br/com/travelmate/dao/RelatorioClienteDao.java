package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Relatoriocliente;

public class RelatorioClienteDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	@Transactional
	public void salvar(Relatoriocliente relatorio) {
		manager.merge(relatorio);
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatoriocliente> listar(){
		String sql = "SELECT r FROM Relatoriocliente r";
		Query q = manager.createQuery(sql); 
		return q.getResultList();
	}

}
