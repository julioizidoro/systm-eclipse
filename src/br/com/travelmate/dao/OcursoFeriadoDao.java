package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Ocursoferiado; 

public class OcursoFeriadoDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	@Transactional
	public Ocursoferiado salvar(Ocursoferiado  ocursoferiado) {
	    ocursoferiado = manager.merge(ocursoferiado);
        return ocursoferiado;
    }
    
    @SuppressWarnings("unchecked")
	public List<Ocursoferiado> listar(String sql){
        Query q = manager.createQuery(sql);
        List<Ocursoferiado> lista = q.getResultList();    
        return lista;
    }

    @Transactional
    public void excluir(int id){
        Ocursoferiado ocursoferiado = manager.find(Ocursoferiado.class, id);
        manager.remove(ocursoferiado);
        
    }
}
