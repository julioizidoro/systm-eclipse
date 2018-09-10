/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Ocurso;

/**
 *
 * @author Wolverine
 */
public class OCursoDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
    
	@Transactional
    public Ocurso salvar(Ocurso  ocurso) {
        ocurso= manager.merge(ocurso);
        return ocurso;
    }
    
    public List<Ocurso> listar(String sql){
        List<Ocurso> lista = null;
        Query q = manager.createQuery(sql);
        lista = q.getResultList();
        return lista;
    }
    
    @Transactional
    public Ocurso consultar(int idOcurso) {
        Ocurso ocurso = manager.find(Ocurso.class, idOcurso);
        return ocurso;
    }
}
